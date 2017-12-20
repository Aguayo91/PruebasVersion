package com.sociomas.aventones.UI.Controls.Pickers;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.AdapterFrecuencia;
import com.sociomas.aventones.UI.Controls.Base.IBaseControl;
import com.sociomas.aventones.UI.Controls.Model.Dia;
import com.sociomas.aventones.Utils.Utils;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import okhttp3.internal.Util;

/**
 * Created by oemy9 on 05/09/2017.
 */
public class FrecuenciaPicker extends RelativeLayout implements IBaseControl, RecyclerItemClickListener,TimePicker.OnTimeChangedListener , View.OnClickListener{

    private TextView tvSalida;
    private TextView tvDiaSeleccionados;
    private ArrayList<Dia>listDiasSeleccionados;
    private Button boton;
    private RecyclerView recyclerFrecuencias;
    private Context context;
    public OnFrecuenciaListener listener;
    public ImageView imgClose;

    public void setListener(OnFrecuenciaListener listener ) {
        this.listener = listener;
    }

    public FrecuenciaPicker(Context context) {
        super(context);
        inflateLayouts(context,null);
    }

    public FrecuenciaPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context,attrs);
    }

    public FrecuenciaPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context,attrs);
    }

    @Override
    public void inflateLayouts(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_picker_frequencia,this);
        this.listDiasSeleccionados=new ArrayList<>();
        this.context=context;
    }

    @Override
    protected void onFinishInflate() {
        recyclerFrecuencias=(RecyclerView)findViewById(R.id.recyclerFrecuencias);
        tvDiaSeleccionados=(TextView)findViewById(R.id.tvDiaSeleccionados);
        tvSalida=(TextView)findViewById(R.id.tvSalida);
        boton = (Button)findViewById(R.id.buttonok);
        imgClose = (ImageView)findViewById(R.id.imgClose);
        boton.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        getDiasSemana();
        super.onFinishInflate();
    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        Dia selectedDia=(Dia)selectedItem;
        if(!listDiasSeleccionados.contains(selectedDia) && selectedDia.isChecked()) {
            listDiasSeleccionados.add(selectedDia);
        }
        else{
            listDiasSeleccionados.remove(selectedDia);
        }
        joinDiasSeleccionados();
    }

    private void getDiasSemana()
    {
        ArrayList<Dia>listDias= Utils.getDiasSemana();
        AdapterFrecuencia adapterFrecuencia=new AdapterFrecuencia(this.context,listDias);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.context);
        adapterFrecuencia.setItemClickListener(this);
        recyclerFrecuencias.setLayoutManager(layoutManager);
        recyclerFrecuencias.setAdapter(adapterFrecuencia);
    }

    private void joinDiasSeleccionados(){
        ArrayList<String>listDiasNombre=new ArrayList<>();
        for(Dia dia: listDiasSeleccionados)
        {
            listDiasNombre.add(dia.getNombre().substring(0,3));
        }
       tvDiaSeleccionados.setText(TextUtils.join(",",listDiasNombre));
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Calendar currentCalendar=Calendar.getInstance();
        currentCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        currentCalendar.set(Calendar.MINUTE,minute);
        SimpleDateFormat dateFormat=new SimpleDateFormat(Constants.HOUR_FORMAT_AM_PM);
        tvSalida.setText(dateFormat.format(currentCalendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonok){
            if(listener!=null){
                listener.onFinishDaySelection(listDiasSeleccionados, Utils.joinDias(listDiasSeleccionados));
            }
        }else if(view.getId()==R.id.imgClose) {
            if (listener != null) {
                listener.onCloseDaySelection();
            }
        }
    }


    public interface OnFrecuenciaListener{
        void onFinishDaySelection(ArrayList<Dia>listDias,String diasFormato);
        void onCloseDaySelection();
    }



}
