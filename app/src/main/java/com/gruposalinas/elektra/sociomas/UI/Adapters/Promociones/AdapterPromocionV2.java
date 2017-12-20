package com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoLista;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by oemy9 on 17/08/2017.
 */

public class AdapterPromocionV2 extends BaseAdapter implements Filterable {

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    public  static class ViewHolder{
        TextView tvTituloPromo;
        TextView tvDescripcionPromo;
        TextView tvVigenciaPromo;
        ImageView imgDescuento;
    }

    private LayoutInflater layoutInflater;
    private ArrayList<PromoLista>lastListCategorias=new ArrayList<>();
    private ArrayList<PromoLista>listCategorias;
    private Context context;
    private ItemFilter itemFilter=new ItemFilter();

    public AdapterPromocionV2(Context context, ArrayList<PromoLista>listCategorias){
        this.context=context;
        this.listCategorias=listCategorias;
        this.lastListCategorias=listCategorias;
        this.layoutInflater=LayoutInflater.from(this.context);
    }

    public void reset(){
        this.listCategorias=lastListCategorias;
    }


    @Override
    public int getCount() {
        return listCategorias.size();
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
            view = this.layoutInflater.inflate(R.layout.item_promocion, viewGroup, false);
            holder.imgDescuento=(ImageView)view.findViewById(R.id.imgDescuento);
            holder.tvTituloPromo=(TextView)view.findViewById(R.id.tvTituloPromo);
            holder.tvDescripcionPromo=(TextView)view.findViewById(R.id.tvDescripcionPromo);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder)view.getTag();
        }
        PromoLista item=getItem(position);
        ArrayList<String>listDescuentos= AdapterUtils.getDescuento(item.getDescuento());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int selectedColor = generator.getRandomColor();
        holder.tvTituloPromo.setText(item.getNombreEmpresa());
        holder.tvDescripcionPromo.setText(item.getDescuento());
        if(listDescuentos!=null && listDescuentos.size()>0){
            TextDrawable drawable = TextDrawable.builder().buildRound(listDescuentos.get(0).replaceAll("\\s",""), selectedColor);
            holder.imgDescuento.setImageDrawable(drawable);
        }
        return view;
    }

    class ItemFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           FilterResults results=new FilterResults();
           ArrayList<PromoLista>nList=new ArrayList<>();
           String query=charSequence.toString().toLowerCase(Locale.ENGLISH);
            for(PromoLista item: listCategorias){
                String nombre=item.getCategoria().toLowerCase(Locale.ENGLISH);
                if(nombre.contains(query) || nombre.startsWith(query)){
                    nList.add(item);
                }
            }
            results.values=nList;
            results.count=nList.size();
            return  results;
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
