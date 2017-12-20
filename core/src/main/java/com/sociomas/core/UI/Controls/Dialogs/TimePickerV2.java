package com.sociomas.core.UI.Controls.Dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.sociomas.core.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by oemy9 on 25/10/2017.
 */

public class TimePickerV2 extends RelativeLayout implements TimePicker.OnTimeChangedListener{

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

    public static final int minuto_interval = 15;
    public final static int interval = 5;
    private TimePicker timePicker;
    private TYPE_PICKER tipoPicker;
    private Calendar calendar=Calendar.getInstance();
    public interface onTimeChangedDialogListener{
        void onChanged(String hourFormat);
        void onCompleted(String textoHorario);
    }
    public boolean hasChanged;
    private onTimeChangedDialogListener listener;
    private RegisterChangeHour changeHour;


    public void setListener(onTimeChangedDialogListener listener) {
        this.listener = listener;
    }


    public TimePickerV2(@NonNull Context context) {
        super(context);
        setInflateView(context,null);
        timePicker=new TimePicker(getContext());
        //timePicker=(TimePicker) findViewById(R.id.timePickerFrecuencia);
        //timePicker.setOnTimeChangedListener(this);
    }
    public TimePickerV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInflateView(context,attrs);
    }

    public TimePickerV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setInflateView(context,attrs);
    }
    private void setInflateView(Context context,AttributeSet attrs) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.item_time_dialog_picker_v2,this);
        timePicker=(TimePicker) findViewById(R.id.timePickerFrecuencia);
        timePicker.setOnTimeChangedListener(this);
        try{
            NumberPicker numberPicker = (NumberPicker)findViewById(Resources.getSystem().getIdentifier("minute","id","android"));
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(3);
            numberPicker.setWrapSelectorWheel(true);
            numberPicker.setDisplayedValues(new String[]{"00","15","30","45"});
        }catch (Exception e){}
        timePicker.setIs24HourView(true);
    }

    public void setTipoPicker(TYPE_PICKER tipoPicker) {
        this.tipoPicker = tipoPicker;
        hasChanged =true;
        switch (tipoPicker){
            case SALIDA:
                setHourCalendar(9,0,0);
                break;
            case LLEGADA:
                setHourCalendar(19,0,0);
                hasChanged=false;
                break;
        }
    }
    public void setTipoPicker(int hour,int min,int sec){
        setHourCalendar(hour,min,sec);
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

    public int getMinutos(){
        return (getCalendar().getTime().getMinutes())*15;
    }

    public void setMinutos(int minutos){
        calendar.set(Calendar.MINUTE,minutos/15);
    }

    public void setHora(int hora){
        calendar.set(Calendar.HOUR,hora);
    }

    public int getHora(){
        return getCalendar().getTime().getHours();
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
        if(!hasChanged) {
            setHourCalendar(hourOfDay, minute, 0);
        }
        hasChanged=false;
    }

    public interface RegisterChangeHour{
        void timeChanged(boolean hasChanged);
    }

    public RegisterChangeHour getChangeHour() {
        return changeHour;
    }

    public void setChangeHour(RegisterChangeHour changeHour) {
        this.changeHour = changeHour;
    }
}
