package com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by oemy9 on 17/08/2017.
 */

public class AdapterCategorias extends BaseAdapter implements Filterable {
    public static final String TAG="ADAPTER_CATEGORIAS";
    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    public  static class ViewHolder{
        TextView tvCategoriaNombre;
        ImageView imageCategoria;
    }

    private LayoutInflater layoutInflater;
    private ArrayList<PromocionesResponse>listCategorias;
    private ArrayList<PromocionesResponse>lastListCategorias=new ArrayList<>();
    private Context context;
    private HashMap<String,Integer>hashImagenes=new HashMap<>();
    private ItemFilter itemFilter;

    public AdapterCategorias(Context context, ArrayList<PromocionesResponse>listCategorias){
        this.context=context;
        this.listCategorias=listCategorias;
        this.lastListCategorias=listCategorias;
        fillHashMaspImagenes();
        this.itemFilter=new ItemFilter();
    }
    private void fillHashMaspImagenes(){
        this.layoutInflater=LayoutInflater.from(this.context);
        this.hashImagenes.put("agencias",R.drawable.agencias);
        this.hashImagenes.put("belleza",R.drawable.esteticas);
        this.hashImagenes.put("autos",R.drawable.autos);
        this.hashImagenes.put("dentistas",R.drawable.dentistas);
        this.hashImagenes.put("deportes",R.drawable.deportes);
        this.hashImagenes.put("entretenimiento",R.drawable.entretenimiento);
        this.hashImagenes.put("escuelas",R.drawable.escuelas);
        this.hashImagenes.put("florerías",R.drawable.florerias);
        this.hashImagenes.put("funerarias",R.drawable.funerarias);
        this.hashImagenes.put("gimnasios",R.drawable.gym);
        this.hashImagenes.put("hogar",R.drawable.hogar);
        this.hashImagenes.put("honduras",R.drawable.honduras);
        this.hashImagenes.put("hotel",R.drawable.hoteles);
        this.hashImagenes.put("idiomas",R.drawable.idiomas);
        this.hashImagenes.put("laboratorios",R.drawable.laboratorios);
        this.hashImagenes.put("librerías ",R.drawable.librerias);
        this.hashImagenes.put("mascotas",R.drawable.mascotas);
        this.hashImagenes.put("médicos",R.drawable.medicos);
        this.hashImagenes.put("medicos especialistas",R.drawable.medicos);
        this.hashImagenes.put("ópticas",R.drawable.opticas);
        this.hashImagenes.put("restaurantes",R.drawable.restaurante);
        this.hashImagenes.put("ropa y accesorios",R.drawable.ropa);
        this.hashImagenes.put("salones de fiesta ",R.drawable.salones_fiesta);
        this.hashImagenes.put("servicios",R.drawable.servicios);
        this.hashImagenes.put("socio verde",R.drawable.socio_verde);
        this.hashImagenes.put("spa",R.drawable.spa);
        this.hashImagenes.put("universidades",R.drawable.universidades);
        this.hashImagenes.put("esteticas",R.drawable.esteticas);
        this.hashImagenes.put("cursos de verano",R.drawable.cursos_verano);
    }

    @Override
    public int getCount() {
        return listCategorias.size();
    }

    public void reset(){
        this.listCategorias=lastListCategorias;
    }

    @Override
    public PromocionesResponse getItem(int position) {
        return listCategorias.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null) {
            holder = new ViewHolder();
            view = this.layoutInflater.inflate(R.layout.item_promocion_categoria, viewGroup, false);
            holder.imageCategoria=(ImageView)view.findViewById(R.id.imageCategoria);
            holder.tvCategoriaNombre=(TextView)view.findViewById(R.id.tvCategoriaNombre);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder)view.getTag();
        }
        PromocionesResponse item=getItem(position);
        String categoria=item.getSCATDESC().toLowerCase(Locale.ENGLISH);
        if(hashImagenes.containsKey(categoria)){
            int imagen=hashImagenes.get(categoria);
            Picasso.with(this.context).load(imagen).into(holder.imageCategoria);
        }
        else{
            Log.i(TAG,"NO TIENE CATEGORIA:"+categoria);
        }
        holder.tvCategoriaNombre.setText(String.format("%s (%s)", Utils.toTitleCase(item.getSCATDESC()),item.getCATETOTAL()));
        return view;
    }


    class ItemFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            final ArrayList<PromocionesResponse>nList=new ArrayList<>();
            String query=charSequence.toString().toLowerCase(Locale.ENGLISH);
            for(PromocionesResponse item: listCategorias){
                String categoria= item.getSCATDESC().toLowerCase(Locale.ENGLISH);
                if(categoria!=null && (categoria.contains(query) || categoria.startsWith(query))){
                    nList.add(item);
                }
            }
            results.values=nList.isEmpty()? lastListCategorias: nList;
            results.count=nList.size()==0? lastListCategorias.size(): nList.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<PromocionesResponse>listResults=(ArrayList<PromocionesResponse>)filterResults.values;
            if(!listResults.isEmpty()){
                listCategorias=listResults;
                notifyDataSetChanged();
            }
        }
    }


}
