package com.gruposalinas.elektra.sociomas.UI.Controls;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 24/09/2017.
 */

public class ExpandableListenerCustom implements ExpandableLayoutListener {


    private ImageView imageArrow;

    public ExpandableListenerCustom(ImageView imageArrow){
        this.imageArrow=imageArrow;
        imageArrow.setImageResource(R.drawable.ic_arrow_down);
    }

    @Override
    public void onAnimationStart() {

    }

    @Override
    public void onAnimationEnd() {

    }

    @Override
    public void onPreOpen() {
      imageArrow.setRotation(180);
    }

    @Override
    public void onPreClose() {
        imageArrow.setRotation(360);
    }

    @Override
    public void onOpened() {

    }

    @Override
    public void onClosed() {

    }
}
