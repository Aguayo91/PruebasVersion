package com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Enums.EnumAlarma;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by oemy9 on 21/04/2017.
 */

@SuppressWarnings("unused")
public class TimeText extends android.support.v7.widget.AppCompatEditText implements View.OnClickListener {

    private Context context;
    private Calendar calendar;

    private Calendar getCalendarByIdAlarma(EnumAlarma tipo){
        Calendar calendarTipo=Calendar.getInstance();
        switch (tipo){
            case entrada:
                calendarTipo.set(Calendar.HOUR_OF_DAY,9);
                calendarTipo.set(Calendar.MINUTE,0);
                this.setText(getTimeString(calendarTipo.getTime()));
                break;
            case salida:
                calendarTipo.set(Calendar.HOUR_OF_DAY,19);
                calendarTipo.set(Calendar.MINUTE,0);
                this.setText(getTimeString(calendarTipo.getTime()));
                break;

        }
        return calendarTipo;

    }

    public void setTipo(EnumAlarma tipo){
        this.calendar=getCalendarByIdAlarma(tipo);
    }

    public TimeText(Context context) {
        super(context);
        this.setConfiguration(context);
    }

    public TimeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setConfiguration(context);
    }

    public TimeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setConfiguration(context);
    }

    private void setConfiguration(Context context){
        this.setOnClickListener(this);
        this.setFocusableInTouchMode(false);
        this.setFocusable(false);
        getBackground().mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        this.context=context;

    }

    @Override
    public void onClick(View v) {
        final AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater inflater=LayoutInflater.from(context);

        final  View dialogView=inflater.inflate(R.layout.dialogo_time,null);
        final TimePicker timePicker=(TimePicker)dialogView.findViewById(R.id.mTimePicker);
        timePicker.setIs24HourView(true);

        if(calendar==null){
            calendar=Calendar.getInstance();
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
        }
        else{
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setTitle("Elegir hora");
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                TimeText.this.setText(getTimeString(calendar.getTime()));
            }
        });
        dialogBuilder.show();
    }

    public Calendar getCalendar(){
        return calendar;
    }

    public String   getTimeString(Date fecha){
        SimpleDateFormat dateFormat=new SimpleDateFormat("kk:mm");
        return dateFormat.format(fecha);
    }
}
