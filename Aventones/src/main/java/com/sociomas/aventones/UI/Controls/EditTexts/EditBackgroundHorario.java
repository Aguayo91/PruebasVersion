package com.sociomas.aventones.UI.Controls.EditTexts;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

import android.widget.TimePicker;
import android.widget.Toast;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogCars;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogTimePicker;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogTimePickerV2;

/**
 * Created by jmarquezu on 27/09/2017.
 */

public class EditBackgroundHorario extends RelativeLayout implements View.OnClickListener {

    private ImageView imageIcon;
    private AutoCompleteTextView txtAutocomplete;
    private TextView tvHorario;
    private Drawable icon;
    private String hint,tvText,tipo;
    private boolean onClick;
    private String horarios=null;
    private String selectedHora;
    private Calendar date;


    private TimePickerDialog.OnTimeSetListener listener;
    public DialogTimePickerV2 dialogo = new DialogTimePickerV2(getContext());


    public EditBackgroundHorario(Context context) {
        super(context);
        inflateLayouts(context,null);
    }

    public EditBackgroundHorario(Context context, AttributeSet attrs){
        super(context,attrs);
        inflateLayouts(context,attrs);
    }

    public EditBackgroundHorario(Context context, AttributeSet attrs, int defStyle){
        super (context,attrs,defStyle);
        inflateLayouts(context,attrs);
    }

    public void setHint(String hint) {
        this.hint = hint;
    }



    public void  inflateLayouts(Context context, AttributeSet attrs)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.edit_background_horario,this);
        if(attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextBackgroundHorario);
            icon=typedArray.getDrawable(R.styleable.EditTextBackgroundHorario_showIcon);
            hint=typedArray.getString(R.styleable.EditTextBackgroundHorario_showHintText);
            tvText=typedArray.getString(R.styleable.EditTextBackgroundHorario_hora);
            onClick=typedArray.getBoolean(R.styleable.EditTextBackgroundHorario_onClickHora,false);
            tipo=typedArray.getString(R.styleable.EditTextBackgroundHorario_tipo);
        }
    }
    @Override
    protected void onFinishInflate() {
        txtAutocomplete=(AutoCompleteTextView) findViewById(R.id.txtAutocomplete);
        imageIcon=(ImageView)findViewById(R.id.imageIcon);
        tvHorario =(TextView)findViewById(R.id.tvHorario);
        tvHorario.setOnClickListener(this);
        if(icon!=null){
            imageIcon.setImageDrawable(icon);
        }
        if(hint!=null && (!hint.isEmpty())){
            txtAutocomplete.setHint(hint);
        }
        if(tvText!=null){
            tvHorario.setText(tvText);
        }
        dialogo.setTipoPicker(DialogTimePickerV2.TYPE_PICKER.fromString(tipo));
        selectedHora=dialogo.toDateFormatWebService();
        txtAutocomplete.setFocusable(false);
        txtAutocomplete.setCursorVisible(false);
        super.onFinishInflate();
    }

    @Override
    public void onClick(View view) {
        if (onClick){
            int i = view.getId();
            if (i == R.id.tvHorario) {
                dialogo.show();
                dialogo.setListener(new DialogTimePickerV2.onTimeChangedDialogListener() {
                    @Override
                    public void onChanged(String hourFormat) {
                        selectedHora=hourFormat;
                    }

                    @Override
                    public void onCompleted(String textoHorario) {
                        tvHorario.setText(textoHorario);
                    }
                });

            }
        }
    }


    public void getTimeDefault(String time){
        selectedHora=time;
    }

    public void setTipo(String tipo){
        this.tipo=tipo;
    }


    public String getSelectedHora() {

       if(selectedHora==null){
           selectedHora="09:00:00";
       }
           return selectedHora;
    }

    public Calendar getDate(){
      return   dialogo.getCalendar();
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

    public  void setError(String error) {
        txtAutocomplete.setError(error);
        txtAutocomplete.requestFocus();
    }

}
