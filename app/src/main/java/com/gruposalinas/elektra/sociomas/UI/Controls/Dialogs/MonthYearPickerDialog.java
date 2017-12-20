package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import com.gruposalinas.elektra.sociomas.R;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by oemy9 on 16/08/2017.
 */

public class MonthYearPickerDialog extends DialogFragment {
    private String MONTH_KEY_SELECTED="MONTH_KEY_SELECTED";
    private DatePickerDialog.OnDateSetListener listener;
    private int lastMonthSelected;
    private SessionManager manager;

    public interface onDateSelectedListener{
        void onDateSelected(String dateFormatInicio, String dateFormatFin);
    }

    private onDateSelectedListener dateSelectedListener;

    public void setDateSelectedListener(onDateSelectedListener dateSelectedListener) {
        this.dateSelectedListener = dateSelectedListener;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.filtro_mes));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Calendar cal = Calendar.getInstance();
        View dialog = inflater.inflate(R.layout.date_picker_dialog, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        manager=new SessionManager(getActivity());
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setValue(manager.getInt(MONTH_KEY_SELECTED)==0?cal.get(Calendar.MONTH):manager.getInt(MONTH_KEY_SELECTED));
        monthPicker.setDisplayedValues(getActivity().getResources().getStringArray(R.array.meses));
        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(2015);
        yearPicker.setMaxValue(year);
        yearPicker.setValue(year);

        builder.setView(dialog)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        SimpleDateFormat dateFormat=new SimpleDateFormat(Constants.DAY_FORMAT);
                        /*PRIMER DÍA DEL MES*/
                        Calendar calendarPrimer=Calendar.getInstance();
                        calendarPrimer.set(Calendar.YEAR,yearPicker.getValue());
                        calendarPrimer.set(Calendar.MONTH,monthPicker.getValue());
                        calendarPrimer.set(Calendar.DAY_OF_MONTH,1);
                        /*ÚLTIMO DÍA DEL MES*/
                        Calendar calendarUltimo=Calendar.getInstance();
                        calendarUltimo.set(Calendar.YEAR,yearPicker.getValue());
                        calendarUltimo.set(Calendar.MONTH,monthPicker.getValue());
                        calendarUltimo.set(Calendar.DAY_OF_MONTH,calendarPrimer.getActualMaximum(Calendar.DAY_OF_MONTH));
                        /*LISTENER*/
                        dateSelectedListener.onDateSelected(dateFormat.format(calendarPrimer.getTime()),
                                dateFormat.format(calendarUltimo.getTime()));

                        manager.add(MONTH_KEY_SELECTED,monthPicker.getValue());

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MonthYearPickerDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
