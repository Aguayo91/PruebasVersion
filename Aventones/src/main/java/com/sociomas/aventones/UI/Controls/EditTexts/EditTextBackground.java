package com.sociomas.aventones.UI.Controls.EditTexts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Controls.Base.IBaseControl;

/**
 * Created by oemy9 on 30/08/2017.
 */

public class EditTextBackground extends RelativeLayout implements View.OnClickListener, IBaseControl{

    private AutoCompleteTextView txtAutocomplete;
    private ImageView imageClose;
    private ImageView imageIcon;
    private Drawable icon;
    private String hint;


    public EditTextBackground(Context context) {
        super(context);
        inflateLayouts(context,null);
    }

    public EditTextBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context,attrs);
    }

    public EditTextBackground(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context,attrs);
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void  inflateLayouts(Context context, AttributeSet attrs)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.edit_background,this);
        if(attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextBackground);
            icon=typedArray.getDrawable(R.styleable.EditTextBackground_icono);
            hint=typedArray.getString(R.styleable.EditTextBackground_hintText);
        }
    }
    @Override
    protected void onFinishInflate() {
        txtAutocomplete=(AutoCompleteTextView) findViewById(R.id.txtAutocomplete);
        imageClose=(ImageView)findViewById(R.id.imgClose);
        imageClose.setOnClickListener(this);
        imageIcon=(ImageView)findViewById(R.id.imageIcon);
        if(icon!=null){
            imageIcon.setImageDrawable(icon);
        }
        if(hint!=null && (!hint.isEmpty())){
            txtAutocomplete.setHint(hint);
        }
        super.onFinishInflate();
    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.imgClose) {
            txtAutocomplete.setText("");

        }
    }
    public AutoCompleteTextView getTxtAutocomplete() {
        return txtAutocomplete;
    }
    public void setText(String text) {

        txtAutocomplete.setText(text);
    }
    public String getText(){

        String cadena = txtAutocomplete.getText().toString();
        return cadena;
    }

    public  void setError(String error){
        txtAutocomplete.setError(error);
        txtAutocomplete.requestFocus();

    }

    public void clearText (){
        txtAutocomplete.setText("");
    }

    public void setHintText(String hintText) {
        txtAutocomplete.setHint(hintText);
    }
}
