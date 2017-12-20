package com.gruposalinas.elektra.sociomas.UI.Controls.Vision;
/**
 * Created by oemy9 on 29/11/2017.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.bumptech.glide.util.Util;
import com.gruposalinas.elektra.sociomas.Utils.Utils;


public class ViewPortHole extends ViewGroup {

    private int theheight;
    private float thewidth;
    public int viewportMargin = 50;
    public int viewportCornerRadius = 8;
    public RectF frame;
    public RectF rect;

    public ViewPortHole(Context context) {
        super(context);
    }

    public ViewPortHole(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPortHole(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        Paint eraser = new Paint();
        eraser.setAntiAlias(true);
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        float width = (float) getWidth() - viewportMargin;
        float height = Utils.convertDipToPixel(300, getContext());
        rect = new RectF((float)viewportMargin, (float)viewportMargin, width, height);
        frame = new RectF((float)viewportMargin, (float)viewportMargin, width+4, height+4);
        Path path = new Path();
        Paint stroke = new Paint();
        stroke.setAntiAlias(true);
        stroke.setStrokeWidth(2);
        stroke.setColor(Color.WHITE);
        stroke.setStyle(Paint.Style.STROKE);
        path.addRoundRect(frame, (float) viewportCornerRadius, (float) viewportCornerRadius, Path.Direction.CW);
        canvas.drawPath(path, stroke);
        canvas.drawRoundRect(rect, (float) viewportCornerRadius, (float) viewportCornerRadius, eraser);
    }

    public int getTheheight() {
        return theheight;
    }

    public float getThewidth() {
        return thewidth;
    }
}