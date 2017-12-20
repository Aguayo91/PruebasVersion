package com.gruposalinas.elektra.sociomas.UI.Controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by jromeromar on 24/11/2017.
 */

public class ControlFilterAsistencias extends RelativeLayout implements View.OnClickListener{

    private Context context;
    private ImageView imgIcon,imgIconGrey;
    private Drawable icon,iconGrey;
    private TextView tvTitle,tvTitleGrey;
    private RelativeLayout rlFilter,rlTrue,rlFalse;
    private int counter=1;
    private String title;

    public interface FilterAsistenciasListener{
        void onCheckedListener(boolean checked);
    }


    private FilterAsistenciasListener listener;

    private boolean isChecked =true;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }


    public ControlFilterAsistencias (Context context){

        super (context);
        inflateLayouts(context,null);
    }

    public ControlFilterAsistencias(Context context, AttributeSet attrs){

        super (context,attrs);
        inflateLayouts(context,attrs);
    }

    public ControlFilterAsistencias(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        inflateLayouts(context, attrs);

    }
    public void setTitle(String title) {
        this.title = title;
    }

    private void inflateLayouts(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_control_filter_asistencias,this);

        if(attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ControlFilterAsistencias);
            icon = typedArray.getDrawable(R.styleable.ControlFilterAsistencias_iconoRefFilter);
            iconGrey = typedArray.getDrawable(R.styleable.ControlFilterAsistencias_iconoRefFilterGrey);
            title=typedArray.getString(R.styleable.ControlFilterAsistencias_titleFilter);
        }

        this.context = context;

    }
    @Override
    protected void onFinishInflate(){

        imgIcon=(ImageView)findViewById(R.id.imgIcon);
        imgIconGrey=(ImageView)findViewById(R.id.imgIconGrey);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvTitleGrey=(TextView)findViewById(R.id.tvTitleGrey);
        rlFilter=(RelativeLayout)findViewById(R.id.rlFilter);
        rlTrue=(RelativeLayout)findViewById(R.id.rlTrue);
        rlFalse=(RelativeLayout)findViewById(R.id.rlFalse);
        rlFilter.setOnClickListener(this);
        if(icon!=null){
            imgIcon.setImageDrawable(icon);
            imgIconGrey.setImageDrawable(icon);
        }
        if(iconGrey!=null){
            imgIconGrey.setImageDrawable(iconGrey);
        }
        if(title!=null && (!title.isEmpty())){
            tvTitle.setText(title);
            tvTitleGrey.setText(title);
        }
        super.onFinishInflate();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (counter % 2 == 0) {
            check();
        } else {
            noCheck();
        }
        counter++;
    }
    public void check(){
      rlTrue.setVisibility(VISIBLE);
      rlFalse.setVisibility(GONE);
      setChecked(true);
      if(listener!=null) {
          listener.onCheckedListener(true);
      }
    }

    public void noCheck(){
        rlTrue.setVisibility(GONE);
        rlFalse.setVisibility(VISIBLE);
        setChecked(false);
        if(listener!=null) {
            listener.onCheckedListener(false);
        }
    }

    public void setListener(FilterAsistenciasListener listener) {
        this.listener = listener;
    }
}