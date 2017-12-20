package com.gruposalinas.elektra.sociomas.UI.Controls.ImageView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gruposalinas.elektra.sociomas.R;

import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.sociomas.core.Utils.Constants;

/**
 * Created by oemy9 on 06/09/2017.
 */

public class ImageProgressPanico extends RelativeLayout implements View.OnTouchListener{

    private CountDownTimer countDownProgreso;
    private CircleProgressbar circleProgressbar;
    private ImageView imageCancelar;
    private RelativeLayout layoutRoot;
    public interface  imageProgressListener{
        void onCancelSuccess(boolean success);
        void onStartTap();
        void onStopTap();
    }
    public imageProgressListener imageProgressListener;
    public void setImageProgressListener(ImageProgressPanico.imageProgressListener imageProgressListener) {
        this.imageProgressListener = imageProgressListener;
    }

    public ImageProgressPanico(Context context) {
        super(context);
        inflateLayouts(context);
    }

    public ImageProgressPanico(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context);
    }

    public ImageProgressPanico(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context);
    }

    private void  inflateLayouts(Context context)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.image_progress_sos,this);
    }

    @Override
    protected void onFinishInflate() {
        imageCancelar=(ImageView)findViewById(R.id.imageCancelar);
        layoutRoot=(RelativeLayout)findViewById(R.id.layoutRoot);
        circleProgressbar=(CircleProgressbar)findViewById(R.id.circleProgresBar);
        circleProgressbar.setClockwise(false);
        imageCancelar.setOnTouchListener(this);

        super.onFinishInflate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //PRESIONANDO PANICO
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            imageProgressListener.onStartTap();
            countDownProgreso=new CountDownTimer(Constants.SEGUNDO*1,Constants.SEGUNDO/4) {
                int progreso=0;
                @Override
                public void onTick(long millisUntilFinished) {
                    progreso+=25;
                    circleProgressbar.setProgress(progreso);
                }
                @Override
                public void onFinish() {
                    imageProgressListener.onCancelSuccess(true);
                    circleProgressbar.setProgress(100);
                }
            };
            countDownProgreso.start();
        }
        //QUITO EL DEDO DEL IMAGEVIEW
        else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            circleProgressbar.clearAnimation();
            countDownProgreso.cancel();
            circleProgressbar.setProgress(0);
            imageProgressListener.onStopTap();
        }
        return true;
    }

}
