package com.gruposalinas.elektra.sociomas.UI.Controls;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by jromeromar on 22/11/2017.
 */

public class BadgeControl extends RelativeLayout {

    private Context context;
    private ImageView imgBadge;
    private TextView tvCounter;
    private static final String TAG = "BadgeControl";

    public BadgeControl (Context context){

        super (context);
        inflateLayouts(context,null);
    }

    public BadgeControl(Context context, AttributeSet attrs){

        super (context,attrs);
        inflateLayouts(context,attrs);
    }

    public BadgeControl(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        inflateLayouts(context, attrs);
    }
    private void inflateLayouts(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_badge, this);
        this.context = context;
    }

    @Override
    protected void onFinishInflate(){

        imgBadge=(ImageView)findViewById(R.id.imgCircle);
        tvCounter=(TextView)findViewById(R.id.tvCounter);
        super.onFinishInflate();

    }
    // Cambia el color del Badge Amarillo
    public void setBadgeYellow(){

      imgBadge.setBackgroundResource(R.drawable.badge_circle_primary_mini);

    }

    // Cambia el color del badge Rojo
    public void setBadgeRed(){

        imgBadge.setBackgroundResource(R.drawable.badge_circle_red_mini);
        if(context!=null) {
            tvCounter.setTextColor(ContextCompat.getColor(context, R.color.blanco));
        }
        else{
            Log.d(TAG, "setBadgeRed: contexto nullo");
        }

    }

    // Cambia el color del badge Gris
    public void setBadgeGrey(){

        imgBadge.setBackgroundResource(R.drawable.badge_circle_grey_mini);

    }

    public void setCounter(Integer counter){

     tvCounter.setText(String.valueOf(counter));

    }

}
