package com.sociomas.core.UI.Controls;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sociomas.core.R;

/**
 * Created by jmarquezu on 24/10/2017.
 */

public class EditTextTamMax extends RelativeLayout {
    private EditText etTexto;
    private TextView tvContador;
    private int i=200;
    private Editable cadena;
    private Context context;

    public EditTextTamMax(Context context) {
        super(context);
        inflateLayouts(context);
    }
    public EditTextTamMax(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context);
    }

    public EditTextTamMax(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context);
    }



    private void  inflateLayouts(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.edit_text_tam_max,this);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        etTexto = (EditText)findViewById(R.id.etTexto);
        tvContador = (TextView)findViewById(R.id.tvContador);
        //etTexto.requestFocus();
        tvContador.setText("0/"+i);
        setEditTextMaxLength(etTexto,i);
        etTexto.addTextChangedListener(new TextWatcher() {
            //Editable cadena;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cadena =etTexto.getText();
                tvContador.setText(cadena.length()+"/"+i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        super.onFinishInflate();
    }


    public void setEditTextMaxLength(EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }

    public void setTamanio(int i) {
        this.i = i;
    }

    public Editable getEditText (){
        Editable edit = this.cadena;
        return edit;
    }


    public String getText(){
        return  etTexto.getText().toString();
    }

    public void setError(String error){
        etTexto.setError(error);
        etTexto.requestFocus();
    }

    public void setHintTextEditable(String hint){
        etTexto.setHint(hint);
    }
    public void setTextColorEditable(int color){
        etTexto.setTextColor(color);
      //  tvContador.setTextColor(color);
    }
    public void setBackGroundEditable (int color){
        etTexto.setBackgroundColor(color);
    }
    public void setBackGroundEditable(Drawable drawable){
        etTexto.setBackground(drawable);
    }
    public void setEditText (String texto){
        etTexto.setText(texto);
    }
    public void setHint(String hint){
        etTexto.setHint(hint);
    }
}
