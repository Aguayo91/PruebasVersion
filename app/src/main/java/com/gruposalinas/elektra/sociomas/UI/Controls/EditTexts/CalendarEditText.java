package com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Constants;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by oemy9 on 09/06/2017.
 */

@SuppressWarnings("unused")
public class CalendarEditText extends android.support.v7.widget.AppCompatEditText implements
        DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private Calendar calendar;
    private Context context;
    public void setContext(Context context) {
        this.context = context;
    }
    public Calendar getCalendar() {
        return calendar;
    }

    public boolean isEmpty(){
        return  this.getCalendar()==null;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
    public CalendarEditText(Context context) {
        super(context);
        setContext(context);
        setConfiguration();
    }

    public CalendarEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setContext(context);
        setConfiguration();
    }

    private void setConfiguration(){
        this.setOnClickListener(this);
        this.setFocusableInTouchMode(false);
        this.setFocusable(false);
        getBackground().mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

    }


    private void showPickerAsync() {
        final Calendar calendar = isEmpty()? Calendar.getInstance(): this.getCalendar();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.context, this
                ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle(R.string.selecciona_una_fecha);
        datePickerDialog.show();

    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, final int month, int dayOfMonth) {
        if(datePicker.isShown()) {
            Calendar calendarDia = Calendar.getInstance();
            calendarDia.set(year, month, dayOfMonth);
            calendarDia.set(Calendar.MINUTE, 0);
            this.setText(new SimpleDateFormat(Constants.DAY_FORMAT).format(calendarDia.getTime()));
            this.setCalendar(calendarDia);
        }
    }

    @Override
    public void onClick(View view) {
            showPickerAsync();
    }
}
