package com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by oemy9 on 23/06/2017.
 */

@SuppressWarnings("unused")
public class AdapterTiendasCatalogo extends ArrayAdapter<LugarTrabajo> implements StickyListHeadersAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<LugarTrabajo>listCatalogoZona=new ArrayList<>();
    private ItemFilterTienda mFilter;
    private SnackBarBuilder snackBarBuilder=new SnackBarBuilder((Activity)getContext());


    @SuppressWarnings("unused")
    public static class ViewHolder{
        TextView tvZonaNombre;
        ImageView imageViewCircleNombre;
        ImageView imageFlecha;

    }
    public static class HeaderViewHolder {
        TextView tvHeader;
    }

    public AdapterTiendasCatalogo(@NonNull Context context, @LayoutRes int resource, @NonNull List<LugarTrabajo> listCatalogoZona) {
        super(context, resource, listCatalogoZona);
        this.context=context;
        this.mFilter=new ItemFilterTienda();
        this.layoutInflater=LayoutInflater.from(context);
        setListCatalogoTienda(listCatalogoZona);
    }

    public void setListCatalogoTienda(List<LugarTrabajo>listCatalogoZona){
        this.listCatalogoZona=listCatalogoZona;
        Collections.sort(listCatalogoZona, new Comparator<LugarTrabajo>() {
            @Override
            public int compare(LugarTrabajo zonaPosicion, LugarTrabajo zonaPosicionDos) {
                String nombreUno=zonaPosicion.getVaNombrePos().trim();
                String nombreDos=zonaPosicionDos.getVaNombrePos().trim();
                return nombreUno.compareTo(nombreDos);
            }
        });
    }



    @NonNull
    @Override
    public Filter getFilter() {
        return  mFilter;
    }

    @Override
    public int getCount() {
        return listCatalogoZona!=null? listCatalogoZona.size():0;
    }

    @Nullable
    @Override
    public LugarTrabajo getItem(int position) {
       return this.listCatalogoZona.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_tienda_catalogo, parent, false);
            viewHolder.tvZonaNombre=(TextView)convertView.findViewById(R.id.tvZonaNombre);
            viewHolder.imageViewCircleNombre=(ImageView)convertView.findViewById(R.id.imageCircleNombre);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final LugarTrabajo selectedItem=getItem(position);
            String primeraLetra = !selectedItem.getVaNombrePos().isEmpty()? selectedItem.getVaNombrePos().substring(0, 1): "a";
           // ColorGenerator generator = ColorGenerator.MATERIAL;
            viewHolder.tvZonaNombre.setText(getItem(position).getVaNombrePos());
            int selectedColor = ContextCompat.getColor(context,R.color.colorPrimary);
            TextDrawable drawable = TextDrawable.builder().buildRound(primeraLetra, selectedColor);
            viewHolder.imageViewCircleNombre.setImageDrawable(drawable);

        return convertView;

    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new HeaderViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_header_catalogo_zona, parent, false);
            viewHolder.tvHeader = (TextView) convertView.findViewById(R.id.tvHeader);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }
        if(getItem(position).getVaNombrePos().length()>=1) {
            String primeraLetra = getItem(position).getVaNombrePos().trim().substring(0, 1).toUpperCase(Locale.ENGLISH);
            viewHolder.tvHeader.setText(checkIfDigit(primeraLetra));
        }
        else{
            Log.i("CATALOGO INDEXOUT EN",String.valueOf(position));
        }
        return  convertView;
    }

    @Override
    public long getHeaderId(int position){
        long retorno=0;
        try {
          retorno=checkIfDigit(getItem(position).getVaNombrePos().substring(0, 1).toUpperCase(Locale.ENGLISH)).hashCode();
        }
        catch (StringIndexOutOfBoundsException e){
          retorno="#".hashCode();
        }
        return retorno;
    }
    public String checkIfDigit(String texto){
         return Character.isDigit(texto.charAt(0))? "#": texto;
    }

    class ItemFilterTienda extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase(Locale.ENGLISH);
            FilterResults results = new FilterResults();
            final ArrayList<LugarTrabajo> nlist = new ArrayList<LugarTrabajo>();
            for(LugarTrabajo item: listCatalogoZona){
                if(item.getVaNombrePos().toLowerCase(Locale.ENGLISH).startsWith(filterString)||
                        item.getVaNombrePos().toLowerCase(Locale.ENGLISH).contains(filterString)){
                    nlist.add(item);
                }
            }
            results.values = nlist.size()>0? nlist: listCatalogoZona;
            results.count = nlist.size()>0? nlist.size(): listCatalogoZona.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<LugarTrabajo> filteredData = (ArrayList<LugarTrabajo>) results.values;
            listCatalogoZona=filteredData;
            notifyDataSetChanged();
        }
    }
}
