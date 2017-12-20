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
import com.sociomas.core.WebService.Model.Response.Promociones.PromoLista;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by oemy9 on 17/08/2017.
 */

public class AdapterCategoriasV2 extends BaseAdapter implements Filterable {
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
    private ArrayList<PromoLista>listCategorias;
    private ArrayList<PromoLista>lastListCategorias=new ArrayList<>();
    private Context context;
    private HashMap<String,Integer>hashImagenes=new HashMap<>();
    private ItemFilter itemFilter;

    public AdapterCategoriasV2(Context context, ArrayList<PromoLista>listCategorias){
        this.context=context;
        this.listCategorias=listCategorias;
        this.lastListCategorias=listCategorias;
        fillHashMaspImagenes();
        this.itemFilter=new ItemFilter();
    }
    private void fillHashMaspImagenes(){
        this.layoutInflater=LayoutInflater.from(this.context);
        this.hashImagenes.put(context.getString(R.string.agencias),R.drawable.agencias);
        this.hashImagenes.put(context.getString(R.string.belleza),R.drawable.esteticas);
        this.hashImagenes.put(context.getString(R.string.autos),R.drawable.autos);
        this.hashImagenes.put(context.getString(R.string.dentistas),R.drawable.dentistas);
        this.hashImagenes.put(context.getString(R.string.deportes),R.drawable.deportes);
        this.hashImagenes.put(context.getString(R.string.entretenimiento),R.drawable.entretenimiento);    
        this.hashImagenes.put(context.getString(R.string.escuelas),R.drawable.escuelas);  
        this.hashImagenes.put(context.getString(R.string.florerias),R.drawable.florerias);    
        this.hashImagenes.put(context.getString(R.string.funerarias),R.drawable.funerarias);      
        this.hashImagenes.put(context.getString(R.string.gimnasios),R.drawable.gym);  
        this.hashImagenes.put(context.getString(R.string.hogar),R.drawable.hogar);    
        this.hashImagenes.put(context.getString(R.string.honduras),R.drawable.honduras);  
        this.hashImagenes.put(context.getString(R.string.hotel),R.drawable.hoteles);
        this.hashImagenes.put(context.getString(R.string.idiomas),R.drawable.idiomas);    
        this.hashImagenes.put(context.getString(R.string.laboratorios),R.drawable.laboratorios);
        this.hashImagenes.put(context.getString(R.string.librerias) ,R.drawable.librerias);
        this.hashImagenes.put("librerías ",R.drawable.librerias);
        this.hashImagenes.put(context.getString(R.string.mascotas),R.drawable.mascotas);
        this.hashImagenes.put(context.getString(R.string.medicos),R.drawable.medicos);
        this.hashImagenes.put(context.getString(R.string.medicos_especialistas),R.drawable.medicos);
        this.hashImagenes.put(context.getString(R.string.opticas),R.drawable.opticas);
        this.hashImagenes.put(context.getString(R.string.restaurantes),R.drawable.restaurante);
        this.hashImagenes.put(context.getString(R.string.ropa_accesorios),R.drawable.ropa);
        this.hashImagenes.put(context.getString(R.string.salones_fiesta),R.drawable.salones_fiesta);
        this.hashImagenes.put(context.getString(R.string.servicios),R.drawable.servicios);
        this.hashImagenes.put(context.getString(R.string.socio_verde),R.drawable.socio_verde);
        this.hashImagenes.put(context.getString(R.string.spa),R.drawable.spa);
        this.hashImagenes.put(context.getString(R.string.universidades),R.drawable.universidades);
        this.hashImagenes.put(context.getString(R.string.esteticas),R.drawable.esteticas);
        this.hashImagenes.put(context.getString(R.string.curos_de_verano),R.drawable.cursos_verano);
        this.hashImagenes.put(context.getString(R.string.boletos_autobus),R.drawable.agencias);
    }

    @Override
    public int getCount() {
        return listCategorias.size();
    }

    public void reset(){
        this.listCategorias=lastListCategorias;
    }

    @Override
    public PromoLista getItem(int position) {
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
        PromoLista item=getItem(position);
        String categoria=item.getCategoria().toLowerCase(Locale.ENGLISH);
        if(hashImagenes.containsKey(categoria)){
            int imagen=hashImagenes.get(categoria);
            Picasso.with(this.context).load(imagen).into(holder.imageCategoria);
        }
        else{
            Log.i(TAG,"NO TIENE CATEGORIA:"+categoria);
        }
        holder.tvCategoriaNombre.setText(String.format("%s (%s)", Utils.toTitleCase(item.getCategoria()),item.getTotal()));
        return view;
    }


    class ItemFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            final ArrayList<PromoLista>nList=new ArrayList<>();
            String query=charSequence.toString().toLowerCase(Locale.ENGLISH);
            for(PromoLista item: listCategorias){
                String categoria= item.getCategoria().toLowerCase(Locale.ENGLISH);
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
            ArrayList<PromoLista>listResults=(ArrayList<PromoLista>)filterResults.values;
            if(!listResults.isEmpty()){
                listCategorias=listResults;
                notifyDataSetChanged();
            }
        }
    }


}
