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
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by oemy9 on 17/08/2017.
 */

public class AdapterPromocion extends BaseAdapter implements Filterable {

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
    private ArrayList<PromocionesResponse>lastListCategorias=new ArrayList<>();
    private ArrayList<PromocionesResponse>listCategorias;
    private Context context;
    private ItemFilter itemFilter=new ItemFilter();

    public AdapterPromocion(Context context, ArrayList<PromocionesResponse>listCategorias){
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
            view = this.layoutInflater.inflate(R.layout.item_promocion, viewGroup, false);
            holder.imgDescuento=(ImageView)view.findViewById(R.id.imgDescuento);
            holder.tvTituloPromo=(TextView)view.findViewById(R.id.tvTituloPromo);
            holder.tvDescripcionPromo=(TextView)view.findViewById(R.id.tvDescripcionPromo);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder)view.getTag();
        }
        PromocionesResponse item=getItem(position);
        ArrayList<String>listDescuentos= AdapterUtils.getDescuento(item.getPUBLDES());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int selectedColor = generator.getRandomColor();
        holder.tvTituloPromo.setText(item.getINTENOMBRE());
        if(listDescuentos!=null && listDescuentos.size()>0){
            holder.tvDescripcionPromo.setText(Html.fromHtml(listDescuentos.get(1)));
            TextDrawable drawable = TextDrawable.builder().buildRound(listDescuentos.get(0).replaceAll("\\s",""), selectedColor);
            holder.imgDescuento.setImageDrawable(drawable);
        }
        return view;
    }

    class ItemFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           FilterResults results=new FilterResults();
           ArrayList<PromocionesResponse>nList=new ArrayList<>();
           String query=charSequence.toString().toLowerCase(Locale.ENGLISH);
            for(PromocionesResponse item: listCategorias){
                String nombre=item.getINTENOMBRE().toLowerCase(Locale.ENGLISH);
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
            ArrayList<PromocionesResponse>listResults=(ArrayList<PromocionesResponse>)filterResults.values;
            if(!listResults.isEmpty()){
                listCategorias=listResults;
                notifyDataSetChanged();
            }
        }
    }

}
