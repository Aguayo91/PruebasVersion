package com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.AsignarZonaRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.ZonaDetalleRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaList;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaPosicionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by oemy9 on 23/06/2017.
 */

public class AdapterZonasCatalogo extends ArrayAdapter<ZonaList> implements StickyListHeadersAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ZonaList>listCatalogoZona=new ArrayList<>();
    private SparseBooleanArray  expandState = new SparseBooleanArray();
    private SparseBooleanArray solicitados=new SparseBooleanArray();
    private ItemFilterZona mFilter;
    private ControllerAPI controllerAPI=new ControllerAPI(this.getContext());
    private String currentNumeroEmpleado;
    private boolean isPlantilla;
    private SnackBarBuilder snackBarBuilder=new SnackBarBuilder((Activity)getContext());

    public boolean isPlantilla() {
        return isPlantilla;
    }

    public void setPlantilla(boolean plantilla) {
        isPlantilla = plantilla;
    }

    public void setCurrentNumeroEmpleado(String currentNumeroEmpleado) {
        this.currentNumeroEmpleado = currentNumeroEmpleado;
    }

    public String getCurrentNumeroEmpleado() {
        return currentNumeroEmpleado;
    }


    public static class ViewHolder{
        TextView tvZonaNombre;
        TextView tvZonaDetalle;
        ImageView imageViewCircleNombre;
        ImageView imageFlecha;
        ExpandableLayout expandableLayout;
        RelativeLayout layoutContent;
        ProgressBar progresRecarga;
        Button btnSolicitar;

    }
    public static class HeaderViewHolder {
        TextView tvHeader;
    }

    public AdapterZonasCatalogo(@NonNull Context context, @LayoutRes int resource, @NonNull List<ZonaList> listCatalogoZona) {
        super(context, resource, listCatalogoZona);
        this.context=context;
        this.mFilter=new ItemFilterZona();
        this.layoutInflater=LayoutInflater.from(context);
        setListCatalogoZona(listCatalogoZona);
    }

    public void setListCatalogoZona(List<ZonaList>listCatalogoZona){
        this.listCatalogoZona=listCatalogoZona;
        Collections.sort(listCatalogoZona, new Comparator<ZonaList>() {
            @Override
            public int compare(ZonaList zonaList, ZonaList zonaListDos) {
                String nombreUno=zonaList.getVaDescripcionZnPosic();
                String nombreDos=zonaListDos.getVaDescripcionZnPosic();
                return nombreUno.compareTo(nombreDos);
            }
        });
        for(int j=0;j<listCatalogoZona.size();j++){
            expandState.append(j,false);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return  mFilter;
    }

    @Override
    public int getCount() {
        return listCatalogoZona.size();
    }

    @Nullable
    @Override
    public ZonaList getItem(int position) {
       return this.listCatalogoZona.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_zona_catalogo, parent, false);
            viewHolder.tvZonaNombre=(TextView)convertView.findViewById(R.id.tvZonaNombre);
            viewHolder.imageViewCircleNombre=(ImageView)convertView.findViewById(R.id.imageCircleNombre);
            viewHolder.imageFlecha=(ImageView)convertView.findViewById(R.id.imageFlecha);
            viewHolder.expandableLayout=(ExpandableLayout)convertView.findViewById(R.id.expandableLayout);
            viewHolder.layoutContent=(RelativeLayout)convertView.findViewById(R.id.layoutContent);
            viewHolder.progresRecarga=(ProgressBar)convertView.findViewById(R.id.progresRecarga);
            viewHolder.tvZonaDetalle=(TextView)convertView.findViewById(R.id.tvZonaDetalle);
            viewHolder.btnSolicitar=(Button)convertView.findViewById(R.id.btnSolicitar);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ZonaList selectedItem=getItem(position);
        String   primeraLetra=selectedItem.getVaDescripcionZnPosic().substring(0, 1);
        ColorGenerator generator=ColorGenerator.MATERIAL;
        viewHolder.tvZonaNombre.setText(getItem(position).getVaDescripcionZnPosic());
        int selectedColor=generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder().buildRound(primeraLetra,selectedColor);
        viewHolder.imageViewCircleNombre.setImageDrawable(drawable);
        viewHolder.btnSolicitar.setBackgroundColor(selectedColor);
        viewHolder.expandableLayout.setExpanded(false);
        viewHolder.expandableLayout.collapse();
        viewHolder.imageFlecha.setImageResource(R.drawable.flecha_lista);
        viewHolder.expandableLayout.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {
            }
            @Override
            public void onAnimationEnd() {
            }
            @Override
            public void onPreOpen() {

                        viewHolder.progresRecarga.setVisibility(View.VISIBLE);
                        viewHolder.tvZonaDetalle.setVisibility(View.GONE);
                        viewHolder.btnSolicitar.setVisibility(View.GONE);
                        viewHolder.imageFlecha.setImageResource(R.drawable.flecha_abajo);
                        Utils.ocultarTeclado((Activity) getContext());
                        expandState.put(position, true);

            }
            @Override
            public void onPreClose() {
                viewHolder.tvZonaDetalle.setVisibility(View.GONE);
                viewHolder.btnSolicitar.setVisibility(View.GONE);
                viewHolder.imageFlecha.setImageResource(R.drawable.flecha_lista);
                expandState.put(position,false);
            }
            @Override
            public void onOpened() {
                getZonaDetalleAsync(getItem(position),viewHolder);
            }
            @Override
            public void onClosed() {
            }

        });
        viewHolder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(solicitados.get(position)) {
                    snackBarBuilder.showInfo("Está zona ya fue solicitada \n espera la aprobación de tu jefe");
                }
                else {
                    viewHolder.expandableLayout.toggle();
                }

            }
        });

        viewHolder.btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asignarZonaAsync(position,selectedItem,viewHolder);
            }
        });

        return convertView;
    }

    public void asignarZonaAsync(final int position, final ZonaList selectedItem, final ViewHolder viewHolder){
        AsignarZonaRequest request=new AsignarZonaRequest();
        request.setId_csc_zo_pos(selectedItem.getIdCscZoPos());
        request.setIdNumEmpleado(getCurrentNumeroEmpleado());
        request.setTipo(isPlantilla? EnumConsulta.LineaDirecta.toString(): EnumConsulta.Mias.toString());
        request.setVa_comentario("");
        controllerAPI.asignarZonaUsuario(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError())
                    {
                            viewHolder.btnSolicitar.setEnabled(false);
                            viewHolder.expandableLayout.toggle();
                            solicitados.put(selectedItem.getIdCscZoPos(),true);
                            snackBarBuilder.showSuccess(context.getString(R.string.tu_solicitud_se_ha_enviado_correctamente).concat("\n").concat(context.getString(R.string.espera_la_autorizaci_n_de_tu_jefe)));
                    }
                    else{
                        snackBarBuilder.showError(response.body().getMensajeError());
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                snackBarBuilder.showError(context.getString(R.string.Error_Conexion));
            }
        });

    }
    public void getZonaDetalleAsync(ZonaList selectedItem, final ViewHolder viewHolder){
        ZonaDetalleRequest request=new ZonaDetalleRequest();
        request.setZona(selectedItem);
        controllerAPI.getZonaDetalle(request).enqueue(new Callback<ZonaPosicionResponse>() {
            @Override
            public void onResponse(Call<ZonaPosicionResponse> call, Response<ZonaPosicionResponse> response) {
                if(response.isSuccessful()){

                    if(!response.body().getError()) {
                        StringBuilder builder = new StringBuilder();
                        for (LugarTrabajo item: response.body().getPosiciones()){
                            builder.append(item.getVaNombrePos());
                            builder.append("\n");
                        }
                        viewHolder.progresRecarga.setVisibility(View.GONE);
                        viewHolder.tvZonaDetalle.setVisibility(View.VISIBLE);
                        viewHolder.btnSolicitar.setVisibility(View.VISIBLE);
                        viewHolder.tvZonaDetalle.setText(builder.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ZonaPosicionResponse> call, Throwable t) {
            }
        });
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
        viewHolder.tvHeader.setText(getItem(position).getVaDescripcionZnPosic().substring(0, 1));
        return  convertView;
    }

    @Override
    public long getHeaderId(int position){
        return  getItem(position).getVaDescripcionZnPosic().substring(0, 1).hashCode();
    }

    class ItemFilterZona extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase(Locale.ENGLISH);
            FilterResults results = new FilterResults();
            final ArrayList<ZonaList> nlist = new ArrayList<ZonaList>();
            for(ZonaList item: listCatalogoZona){
                if(item.getVaDescripcionZnPosic().toLowerCase(Locale.ENGLISH).startsWith(filterString)|| item.getVaDescripcionZnPosic().toLowerCase(Locale.ENGLISH).contains(filterString)
                        || item.getVaDescripcionZnPosic().startsWith(filterString)|| item.getVaDescripcionZnPosic().toLowerCase(Locale.ENGLISH).indexOf(filterString)>=0){
                    nlist.add(item);
                }
            }
            results.values = nlist.size()>0? nlist: listCatalogoZona;
            results.count = nlist.size()>0? nlist.size(): listCatalogoZona.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<ZonaList> filteredData = (ArrayList<ZonaList>) results.values;
            listCatalogoZona=filteredData;
            notifyDataSetChanged();
        }

    }
}
