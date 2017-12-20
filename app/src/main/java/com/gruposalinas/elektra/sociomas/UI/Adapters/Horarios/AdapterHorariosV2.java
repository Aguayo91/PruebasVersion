package com.gruposalinas.elektra.sociomas.UI.Adapters.Horarios;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolders.ViewHolderHorarios;
import com.gruposalinas.elektra.sociomas.R;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumStatusHorario;
import com.sociomas.core.WebService.Model.Response.Horario.Dia;
import com.sociomas.core.WebService.Model.Response.Horario.Horario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by oemy9 on 15/06/2017.
 */

public class AdapterHorariosV2 extends BaseAdapter {


    private ArrayList<Horario>listHorario;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Dia> listDias= new ArrayList<>();
    private boolean pantallPendientes=false;
    private SimpleDateFormat horaDateFormat=new SimpleDateFormat();
    private SimpleDateFormat horaOutDateFormat=new SimpleDateFormat();
    private Date selectedDate;

    /*SIRVE PARA VEIFICAR SI EL USARIO VIENE DE LA PANTALLA DE PENDIENTES POR AUTORIZAR*/
    public boolean isPantallPendientes() {
        return pantallPendientes;
    }

    public void setPantallPendientes(boolean pantallPendientes) {
        this.pantallPendientes = pantallPendientes;
    }

    public AdapterHorariosV2(Context context, ArrayList<Horario>listHorario) {
        this.context=context;
        this.layoutInflater=LayoutInflater.from(this.context);
        this.horaDateFormat=new SimpleDateFormat("kk:mm");
        this.horaOutDateFormat=new SimpleDateFormat(Constants.HOUR_FORMAT_AM_PM);
        this.selectedDate=new Date();
        this.listHorario=listHorario;
        this.ordenarLista();
    }


    private void ordenarLista(){
        Collections.sort(listHorario, new Comparator<Horario>() {
            @Override
            public int compare(Horario horario, Horario horarioDos) {
                return ((Integer)horario.getTiDiaSemana()).compareTo(horarioDos.getTiDiaSemana());
            }
        });

        if(listHorario.get(0).getTiDiaSemana()==0){
            Horario itemDomingo=listHorario.get(0);
            listHorario.remove(0);
            listHorario.add(itemDomingo);
        }
    }



   public  void deleteIndex(int index){
       this.listHorario.remove(index);
       notifyDataSetChanged();
   }

    @Override
    public int getCount() {
        return listHorario.size();
    }

    @Override
    public Horario getItem(int i) {
        return listHorario.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolderHorarios holder;
        if(view==null) {
            holder = new ViewHolderHorarios();
            view = this.layoutInflater.inflate(R.layout.item_horarios_am_pm, viewGroup, false);
            holder.dia=(TextView)view.findViewById(R.id.dia_item);
            holder.entrada=(TextView)view.findViewById(R.id.entrada_item);
            holder.salida=(TextView)view.findViewById(R.id.salida_item);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolderHorarios)view.getTag();
        }
        Horario item=listHorario.get(position);
        holder.dia.setText(item.getNombreDia());
        EnumStatusHorario statusHorario=EnumStatusHorario.fromString(item.getEstatus());

        /*NO VIENE DE LA PANTALLA DE PENDIENTES*/
        if(!isPantallPendientes()) {
            boolean diaLibre=false;
            if(item.getTmHoraEntrada()!=null && item.getTmHoraSalida()!=null) {
                diaLibre= item.getTmHoraEntrada().equalsIgnoreCase(item.getTmHoraSalida()) && statusHorario!=EnumStatusHorario.VALIDO;
            }
            if(diaLibre || statusHorario == EnumStatusHorario.DIA_LIBRE){
                holder.entrada.setTextColor(Color.RED);
                holder.entrada.setText(context.getString(R.string.dia_libre));
                holder.salida.setText("");
            }
            else if (statusHorario == EnumStatusHorario.VALIDO || statusHorario == EnumStatusHorario.PROPUESTA) {
                holder.entrada.setText(getHoraFormato(item.getTmHoraEntrada()));
                holder.entrada.setTextColor(Color.BLACK);
                holder.salida.setText(getHoraFormato(item.getTmHoraSalida()));
                holder.salida.setTextColor(Color.BLACK);

            } else if (statusHorario == EnumStatusHorario.NO_ASIGNADO || statusHorario == statusHorario.PENDIENTE_LIBERAR) {
                holder.entrada.setTextColor(Color.RED);
                holder.entrada.setText(context.getString(R.string.horario_no_asignado));
                holder.salida.setText("");
            }
        }
        else{
                boolean diaLibre=item.getCambio()!=null && (item.getCambio().equals("Día libre"));
                holder.entrada.setText(diaLibre? context.getString(R.string.dia_libre): getHoraFormato(item.getTmHoraEntrada()));
                holder.entrada.setTextColor(diaLibre?Color.RED:Color.BLACK);
                holder.salida.setText(diaLibre? "":getHoraFormato(item.getTmHoraSalida()));
                holder.salida.setTextColor(Color.BLACK);

        }
        return view;
    }

    private String getHoraFormato(String input)  {
        try {
            selectedDate=horaDateFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  horaOutDateFormat.format(selectedDate);
    }
}
