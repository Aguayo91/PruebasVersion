package com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by oemy9 on 15/05/2017.
 */


public class AdapterIncidenciaV2 extends BaseAdapter {

    public static class  ViewHolder  {
        public TextView tvTipoJustificacion;
        public TextView tvFechaJustificacion;
        public ImageView ImgJustificacion;
        public LinearLayout linearContenedor;
    }

    private ArrayList<ListadoIncidencias> listIndidencias;
    private Context context;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat dateFormatFrom;
    private SimpleDateFormat dateFormatTo;
    private Date date;
    private HashMap<Integer,Boolean> hashFechas =new HashMap<>();

    public AdapterIncidenciaV2(Context context,ArrayList<ListadoIncidencias>listIndidencias){
        this.context=context;
        this.listIndidencias=listIndidencias;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        dateFormatFrom=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        dateFormatTo=new SimpleDateFormat("dd-MM-yyyy");
    }

    @Override
    public int getCount() {
        return  listIndidencias!=null? listIndidencias.size():0;
    }

    @Override
    public ListadoIncidencias getItem(int i) {
        return listIndidencias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_incidencia_v2, viewGroup, false);
            holder.tvTipoJustificacion=(TextView)view.findViewById(R.id.tvTipoJustificacion);
            holder.tvFechaJustificacion=(TextView)view.findViewById(R.id.tvFechaJustificacion);
            holder.ImgJustificacion=(ImageView) view.findViewById(R.id.img_justificacion);
            holder.linearContenedor=(LinearLayout)view.findViewById(R.id.contenedor);
            view.setTag(holder);
        }
        else{
         holder=(ViewHolder)view.getTag();
        }
        if(position%2!=0){
           holder.linearContenedor.setBackgroundColor(Color.WHITE);
        }
        if(!hashFechas.containsKey(position)) {
            try {
                getItem(position).setFechaOcurrencia(this.toStringDate(getItem(position).getFechaOcurrencia()));
                hashFechas.put(position,true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Picasso.with(this.context).load(AdapterUtils.getResourceFileByTipoJustificacion(getItem(position).getEstatusJustificacion())).resize(60,60).into(holder.ImgJustificacion);
        holder.tvFechaJustificacion.setText(this.getItem(position).getFechaOcurrencia());
        holder.tvTipoJustificacion.setText(getItem(position).getIncidencia());
        return view;
    }


    public String toStringDate(String input) throws ParseException {
        date=dateFormatFrom.parse(input);
        return dateFormatTo.format(date);
    }

}
