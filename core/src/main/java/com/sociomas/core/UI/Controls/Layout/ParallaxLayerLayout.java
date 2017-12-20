package com.sociomas.core.UI.Controls.Layout;

/**
 * Created by oemy9 on 31/10/2017.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.sociomas.core.R;

public class ParallaxLayerLayout extends RelativeLayout {

    private static final int DEFAULT_BASE_OFFSET_DP = 10;
    private static final int DEFAULT_OFFSET_INCREMENT_DP = 5;
    private static final String TAG = "ParallaxLayerLayout";


    private Interpolator interpolator = new DecelerateInterpolator();
    private int offsetIncrementPx;
    private int baseOffsetPx;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private long lastUpdate = -1;
    private TranslationUpdater translationUpdater;
    private DirectionListener directionListener;


    public void setDirectionListener(DirectionListener directionListener) {
        this.directionListener = directionListener;
    }

    public ParallaxLayerLayout(Context context) {
        super(context);
    }

    public ParallaxLayerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ParallaxLayerLayout);
        try {
            baseOffsetPx =
                    a.getDimensionPixelSize(R.styleable.ParallaxLayerLayout_parallaxOffsetBase, -1);
            if (baseOffsetPx == -1) {
                baseOffsetPx =
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_BASE_OFFSET_DP,
                                getResources().getDisplayMetrics());
            }

            offsetIncrementPx =
                    a.getDimensionPixelSize(R.styleable.ParallaxLayerLayout_parallaxOffsetIncrement, -1);
            if (offsetIncrementPx == -1) {
                offsetIncrementPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        DEFAULT_OFFSET_INCREMENT_DP, getResources().getDisplayMetrics());
            }

            scaleX = a.getFloat(R.styleable.ParallaxLayerLayout_parallaxScaleHorizontal, 1.0f);
            scaleY = a.getFloat(R.styleable.ParallaxLayerLayout_parallaxScaleVertical, 1.0f);
            if (scaleX > 1.0f || scaleX < 0.0f || scaleY > 1.0f || scaleY < 0.0f) {
                throw new IllegalArgumentException("Parallax scale must be a value between -1.0 and 1.0");
            }
        } finally {
            a.recycle();
        }
    }

    //region Offset computation
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        computeOffsets();

        if (isInEditMode()) {
            updateTranslations(new float[] { 1.0f, 1.0f });
        }
    }

    private void computeOffsets() {
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int index;
            if (lp.customIndex == -1) {
                //Reversed for parallax effect
                index = childCount - 1 - i;
            } else {
                index = lp.customIndex;
            }
            lp.offsetPx = offsetPxForIndex(index, lp.incrementMultiplier);
        }
    }

    private float offsetPxForIndex(int index, float incrementMultiplier) {
        return incrementMultiplier * baseOffsetPx + index * offsetIncrementPx;
    }
    //endregion

    //region Translation

    /**
     * @param translations X and Y translation percentage, with values from -1.0 to 1.0.
     */
    public void updateTranslations(@Size(2) float[] translations) {
        if (Math.abs(translations[0]) > 1 || Math.abs(translations[1]) > 1) {
            throw new IllegalArgumentException("Translation values must be between 1.0 and -1.0");
        }

        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = getChildAt(i);
            float[] translationsPx = calculateFinalTranslationPx(child, translations);

            child.setTranslationX((float) (translationsPx[0]*(0.3)));
            child.setTranslationY((float) (translationsPx[1]*(0.3)));
            //child.getId(R.id.);
            //child.setRotationY(translationsPx[0]*(-2));
            //child.setRotationX((float) (translationsPx[1]*(1.5)));
            //child.setR

        }
    }

    public void setTranslationUpdater(TranslationUpdater translationUpdater) {
        if (this.translationUpdater != null) {
            this.translationUpdater.unSubscribe();
        }
        this.translationUpdater = translationUpdater;
        this.translationUpdater.subscribe(this);
    }

    @Size(2)
    private float[] calculateFinalTranslationPx(View child, @Size(2) float[] translations) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (!lp.enabled) {
            return new float[] { 0f, 0f };
        }
        int xSign = translations[0] > 0 ? 1 : -1;
        int ySign = translations[1] > 0 ? 1 : -1;

        float translationX =
                xSign*lp.offsetPx*interpolator.getInterpolation(Math.abs(translations[0]))*scaleX;
        float translationY =
                ySign * lp.offsetPx * interpolator.getInterpolation(Math.abs(translations[1])) * scaleY;



        long curTime = System.currentTimeMillis();
        if ((curTime - lastUpdate) > 500) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            if (translationX > 10) {
                if (directionListener != null) {
                    directionListener.onRightListener();
                }
            } else {
                if ((translationX * -1) > 10) {
                    if (directionListener != null) {
                        directionListener.onLeftListener();
                    }
                } else {
                    if (directionListener != null) {
                        directionListener.onCenterListener();
                    }
                }
            }
        }



        return new float[] { translationX, translationY };
    }
    //endregion

    //region LayoutParams
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof RelativeLayout.LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new  LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }



    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    public interface TranslationUpdater {

        void subscribe(ParallaxLayerLayout parallaxLayerLayout);

        void unSubscribe();
    }

    public interface DirectionListener{
        void onLeftListener();
        void onRightListener();
        void onCenterListener();
    }

    //endregion

    public static class LayoutParams extends RelativeLayout.LayoutParams {

        private float offsetPx;
        private int customIndex = -1;
        private float incrementMultiplier = 2.0f;
        private boolean enabled = true;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            //gravity = Gravity.CENTER;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.ParallaxLayerLayout_LayoutParams);
            try {
                customIndex =
                        a.getInt(R.styleable.ParallaxLayerLayout_LayoutParams_layout_parallaxZIndex, -1);
                incrementMultiplier = a.getFloat(
                        R.styleable.ParallaxLayerLayout_LayoutParams_layout_parallaxIncrementMultiplier, 1.0f);
                enabled = a.getBoolean(
                        R.styleable.ParallaxLayerLayout_LayoutParams_layout_parallaxEnabled, true);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
           // gravity = Gravity.CENTER;
        }
    }
}
