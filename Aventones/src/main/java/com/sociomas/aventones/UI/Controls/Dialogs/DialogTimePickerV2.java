package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.sociomas.aventones.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by oemy9 on 25/10/2017.
 */

public class DialogTimePickerV2 extends Dialog implements TimePicker.OnTimeChangedListener, View.OnClickListener {

    public  enum TYPE_PICKER{
        SALIDA{
            @Override
            public String toString() {
                return "Salida";
            }
        },
        LLEGADA{
            @Override
            public String toString() {
                return "Llegada";
            }
        };
        public static TYPE_PICKER fromString(String input){
            return input!=null && input.equalsIgnoreCase("salida")? SALIDA: LLEGADA;
        }
    }
    private final Button btnOk;
    private final ImageView imgClose;
    private TimePicker timePicker;
    private TYPE_PICKER tipoPicker;
    private Calendar calendar=Calendar.getInstance();
    public interface onTimeChangedDialogListener{
        void onChanged(String hourFormat);
        void onCompleted(String textoHorario);
    }
    private onTimeChangedDialogListener listener;


    public void setListener(onTimeChangedDialogListener listener) {
        this.listener = listener;
    }

    public DialogTimePickerV2(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_time_dialog_picker);
        timePicker=(TimePicker) findViewById(R.id.timePickerFrecuencia);
        btnOk = (Button)findViewById(R.id.btnGuardar);
        imgClose = (ImageView)findViewById(R.id.imgclose);
        timePicker.setOnTimeChangedListener(this);
        imgClose.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }
    public void setTipoPicker(TYPE_PICKER tipoPicker) {
        this.tipoPicker = tipoPicker;
        switch (tipoPicker){
            case SALIDA:
                setHourCalendar(8,0,0);
                break;
            case LLEGADA:
                setHourCalendar(9,0,0);
                break;
        }
    }

    private void setHourCalendar(int hour, int minutes, int seconds){
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE,minutes);
        calendar.set(Calendar.SECOND,seconds);
        int am = calendar.get(Calendar.AM_PM);
        if(hour<=11&&am==1)
            calendar.set(Calendar.AM_PM,0);
        else if(hour>=12&&am==0)
            calendar.set(Calendar.AM_PM,1);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

    }

    public String toDateFormatWebService(){
        if(calendar!=null){
            SimpleDateFormat dateFormatWs=new SimpleDateFormat("HH:mm:ss");
            return dateFormatWs.format(calendar.getTime());
        }
        return null;
    }

    public String toDateFormatDispositivo(){
        if(calendar!=null){
            SimpleDateFormat dateFormat=new SimpleDateFormat("h:mm a");
            return dateFormat.format(calendar.getTime());
        }
        return null;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        if(isShowing()) {
            setHourCalendar(hourOfDay, minute, 0);
            if (listener != null) {
                listener.onChanged(toDateFormatWebService());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnOk.getId()){
            if(listener!=null) {
                listener.onCompleted(tipoPicker.toString() + " " + toDateFormatDispositivo());
            }
            this.dismiss();
        }
        else if(v.getId()==imgClose.getId()){
            this.dismiss();
        }
    }
}
