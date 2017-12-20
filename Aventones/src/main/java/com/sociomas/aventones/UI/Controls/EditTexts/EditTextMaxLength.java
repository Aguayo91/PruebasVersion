package com.sociomas.aventones.UI.Controls.EditTexts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Controls.Base.IBaseControl;

/**
 * Created by jromeromar on 20/09/2017.
 */

public class EditTextMaxLength  extends RelativeLayout implements View.OnClickListener, IBaseControl {

    private EditText txtAutocomplete;
    private ImageView imageClose;
    private ImageView imageIcon;
    private Drawable icon;
    private String hint;
    private int i;


    public EditTextMaxLength(Context context) {
        super(context);
        inflateLayouts(context,null);
    }

    public EditTextMaxLength(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context,attrs);
    }

    public EditTextMaxLength(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context,attrs);
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void  inflateLayouts(Context context, AttributeSet attrs)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.edit_max_length,this);
        if(attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextBackground);
            icon=typedArray.getDrawable(R.styleable.EditTextBackground_icono);
            hint=typedArray.getString(R.styleable.EditTextBackground_hintText);
        }
    }
    @Override
    protected void onFinishInflate() {
        txtAutocomplete=(EditText) findViewById(R.id.txtAutocomplete);
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
    public EditText getTxtAutocomplete() {
        return txtAutocomplete;
    }
    public void setText(String text){
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

}
