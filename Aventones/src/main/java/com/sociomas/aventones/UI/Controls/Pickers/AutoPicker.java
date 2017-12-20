package com.sociomas.aventones.UI.Controls.Pickers;

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
import com.sociomas.aventones.UI.Controls.Dialogs.DialogAutoPicker;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;

import java.util.List;

/**
 * Created by oemy9 on 10/10/2017.
 */

public class AutoPicker extends RelativeLayout {

    private AutoCompleteTextView txtAutocomplete;
    private ImageView imageClose;
    private ImageView imageIcon;
    private Drawable icon;
    private String hint;
    private DialogAutoPicker dialogAutoPicker;
    private List<Vehiculo>listVehiculos;


    public AutoPicker(Context context) {
        super(context);
        inflateLayouts(context,null);
    }

    public void setDialogAutoPicker(DialogAutoPicker dialogAutoPicker) {
        this.dialogAutoPicker = dialogAutoPicker;
    }

    public AutoPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context,attrs);
    }

    public AutoPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context,attrs);
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void  inflateLayouts(Context context, AttributeSet attrs)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.picker_auto,this);
    }
    @Override
    protected void onFinishInflate() {
        txtAutocomplete=(AutoCompleteTextView) findViewById(R.id.txtAutocomplete);
        txtAutocomplete.setFocusable(false);
        txtAutocomplete.setCursorVisible(false);
        txtAutocomplete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listVehiculos!=null && (!listVehiculos.isEmpty())) {
                    dialogAutoPicker.show();
                    dialogAutoPicker.setListAutos(listVehiculos);

                }
            }
        });
        imageClose=(ImageView)findViewById(R.id.imgClose);
        super.onFinishInflate();
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

    public void setListAutos(List<Vehiculo> list){
        this.listVehiculos=list;
    }

    public void clearText (){
        txtAutocomplete.setText("");
    }
}
