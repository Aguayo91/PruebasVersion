package com.sociomas.core.UI.Controls.Progress;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sociomas.core.R;


/**
 * Created by jromeromar on 29/11/2017.
 */

public class ProgressBubble extends RelativeLayout {
    public int counter=1;
    private Context context;
    private Animation Crecer;
    private ImageView Bubble1,Bubble2,Bubble3,Bubble4,Bubble5;
    private RelativeLayout rlBubble;

    public ProgressBubble (Context context){

        super(context);
        inflateLayouts(context,null);
    }

    public ProgressBubble(Context context, AttributeSet attrs){

        super (context,attrs);
        inflateLayouts(context,attrs);
    }
    private void inflateLayouts(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_bubble, this);
        this.context = context;
        startBubbleAnimation();
    }

    @Override
    protected void onFinishInflate(){

        Bubble1=(ImageView)findViewById(R.id.Bubble1);
        Bubble2=(ImageView)findViewById(R.id.Bubble2);
        Bubble3=(ImageView)findViewById(R.id.Bubble3);
        Bubble4=(ImageView)findViewById(R.id.Bubble4);
        Bubble5=(ImageView)findViewById(R.id.Bubble5);
        rlBubble=(RelativeLayout)findViewById(R.id.rlBubble);


        super.onFinishInflate();
    }

    public void startBubbleAnimation() {
        newCountDown();
    }

    public void newCountDown(){

        new CountDownTimer(50, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                if (counter == 1 ) {
                    loader1();
                } else if(counter==2) {
                    loader2();
                } else if(counter==3) {
                    loader3();
                } else if(counter==4) {
                    loader4();
                }else if(counter==5) {
                    loader5();
                }
            }

        }.start();

    }

    public void loader1(){
        Crecer = AnimationUtils.loadAnimation(getContext(),R.anim.crecer);
        Bubble1.startAnimation(Crecer);
        new CountDownTimer(50, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                newCountDown();
                counter++;
            }
        }.start();
    }

    public void loader2(){
        Crecer = AnimationUtils.loadAnimation(getContext(),R.anim.crecer);
        Bubble2.startAnimation(Crecer);

        new CountDownTimer(50, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                newCountDown();
                counter++;
            }

        }.start();

    }

    public void loader3(){
        Crecer = AnimationUtils.loadAnimation(getContext(),R.anim.crecer);
        Bubble3.startAnimation(Crecer);
        new CountDownTimer(50, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                newCountDown();
                counter++;
            }

        }.start();

    }

    public void loader4(){
        Crecer = AnimationUtils.loadAnimation(getContext(),R.anim.crecer);
        Bubble4.startAnimation(Crecer);
        new CountDownTimer(50, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                newCountDown();
                counter++;
            }
        }.start();

    }
    public void loader5(){
        Crecer = AnimationUtils.loadAnimation(getContext(),R.anim.crecer);
        Bubble5.startAnimation(Crecer);
        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                newCountDown();
                counter=1;
            }

        }.start();

    }
    public void Dismiss(){
        rlBubble.setVisibility(GONE);
    }
}
