package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.sociomas.aventones.R;
import com.sociomas.core.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jmarquezu on 27/09/2017.
 */

public class DialogTimePicker extends Dialog implements TimePicker.OnTimeChangedListener{

    private TimePicker timePicker;
    private Button btnOk;
    private ImageView imgClose;
    private onTimeChangedDialogListener listener;
    private onOkDialogListener okDialogListener;
    private String hora;
    private String horaWS;
    private Calendar calendar=Calendar.getInstance();
    private int am;
    private ArrayList<Integer> horas=new ArrayList<>();
    private int hora1=9;
    private int minutos=0;

    public Calendar getCalendario() {
        this.calendario = calendar;
        return calendario;
    }

    public void setCalendario(Calendar calendario) {
        this.calendario = calendario;
    }

    public Calendar calendario;



    public interface onTimeChangedDialogListener{
        void onChanged(String hourFormat);
    }



    public void setListener(onTimeChangedDialogListener listener) {
        this.listener = listener;
    }

    public DialogTimePicker(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_time_dialog_picker_v2);
        timePicker=(TimePicker) findViewById(R.id.timePickerFrecuencia);
        btnOk = (Button)findViewById(R.id.btnGuardar);
        imgClose = (ImageView)findViewById(R.id.imgclose);
        timePicker.setCurrentHour(hora1);
        timePicker.setCurrentMinute(minutos);
        timePicker.setOnTimeChangedListener(this);

        Date date = calendar.getTime();

        calendar.setTime(date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("h:mm a");
        SimpleDateFormat dateFormatWs=new SimpleDateFormat(Constants.HOUR_FORMAT);
        horaWS=dateFormatWs.format(calendar.getTime());
        hora= dateFormat.format(calendar.getTime());
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okDialogListener!= null){
                    listener.onChanged(hora);
                    okDialogListener.onOkListener();
                }
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okDialogListener!= null){
                    okDialogListener.onOkListener();
                }
            }
        });


    }

    @Override
    public void onTimeChanged(TimePicker timePicker1, int hour, int minutes){
        calendar.set(Calendar.HOUR,hour);
        calendar.set(Calendar.MINUTE,minutes);
        SimpleDateFormat dateFormat=new SimpleDateFormat("h:mm a");
        SimpleDateFormat dateFormatWs=new SimpleDateFormat("HH:mm:ss");
        hora1 = hour;
        minutos = minutes;
        am = calendar.get(Calendar.AM_PM);
        if(hour<=11&&am==1){
            calendar.set(Calendar.AM_PM,0);
        }else if(hour>=12&&am==0){
            calendar.set(Calendar.AM_PM,1);
        }
        if(listener!=null){
            hora= dateFormat.format(calendar.getTime());
            horaWS=dateFormatWs.format(calendar.getTime());
        }
    }

    public interface onOkDialogListener {
        void onOkListener();
    }

    public void setOnOkListener (onOkDialogListener OkListener){
        this.okDialogListener = OkListener;
    }

    public String getHora() {
        return hora;
    }

    public String getHoraWS() {
        return horaWS;
    }

    public void setHoraWS(String horaWS) {
        this.horaWS = horaWS;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
