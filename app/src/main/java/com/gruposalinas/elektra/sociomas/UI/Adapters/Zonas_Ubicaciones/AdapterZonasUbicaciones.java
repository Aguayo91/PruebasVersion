package com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones.MapaZonaActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.R;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EliminarZonaRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.ZonaDetalleRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaList;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaPosicionResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by oemy9 on 23/06/2017.
 */

public class AdapterZonasUbicaciones extends ArrayAdapter<LugarTrabajo> implements StickyListHeadersAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<LugarTrabajo>listPosicionesZonas=new ArrayList<>();
    private ControllerAPI controllerAPI=new ControllerAPI(this.getContext());
    private SnackBarBuilder snackBarBuilder=new SnackBarBuilder((Activity)getContext());
    private String currentNumeroEmpleado;
    private boolean isPlantilla;
    private Activity activity;
    /*LISTENER PARA NOTIFICAR QUE HAY PENDIENTES POR AUTORIZAR*/
    public interface CallBackPlantillaPendientes{void onPendientesAutorizar(boolean setClickListener);}
    private CallBackPlantillaPendientes onPlantillaPendienteListener;

    public void setOnPlantillaPendienteListener(CallBackPlantillaPendientes onPlantillaPendienteListener) {
        this.onPlantillaPendienteListener = onPlantillaPendienteListener;
    }

    public static class ViewHolder{
        TextView tvZonaNombre;
        ImageView imageViewCircleNombre;
        TextView tvZonaDetalle;
        ImageView imageFlecha;
        ExpandableLayout expandableLayout;
        RelativeLayout layoutContent;
        ProgressBar progresRecarga;
        Button btnSolicitar;
    }
    public static class HeaderViewHolder {
        TextView tvHeader;
    }
    public void clearItems(){
        this.listPosicionesZonas.clear();
    }
    public void setCurrentNumeroEmpleado(String currentNumeroEmpleado) {
        this.currentNumeroEmpleado = currentNumeroEmpleado;
    }
    public String getCurrentNumeroEmpleado() {
        return currentNumeroEmpleado;
    }
    public boolean isPlantilla() {
        return isPlantilla;
    }
    public void setPlantilla(boolean plantilla) {
        isPlantilla = plantilla;
    }
    public void setPosicionesZonas(List<LugarTrabajo>listPosiciones, boolean zona){
        ArrayList<LugarTrabajo>itemsRemove=new ArrayList<>();
        if(listPosiciones!=null && (!listPosiciones.isEmpty())) {
            for (LugarTrabajo item : listPosiciones) {
                if(item.getBitValida()) {
                    item.setTipoPosicion(zona ? Constants.ZONAS : Constants.UBICACIONES);
                }
                else{
                        itemsRemove.add(item);
                }
            }
            listPosiciones.removeAll(itemsRemove);
            listPosicionesZonas.addAll(listPosiciones);
            notifyDataSetChanged();
        }

        boolean itemPendienteValido=false;
        for(LugarTrabajo item: itemsRemove) {
            if(!item.getIdEstatusPosic().equals("R")){
                itemPendienteValido=true;
                break;
            }
        }
        if(!isPlantilla && itemPendienteValido && itemsRemove.size()>0){
            if(onPlantillaPendienteListener!=null){
                onPlantillaPendienteListener.onPendientesAutorizar(false);
            }
        }
        else if(isPlantilla && itemPendienteValido && itemsRemove.size()>0){
            if(onPlantillaPendienteListener!=null){
                onPlantillaPendienteListener.onPendientesAutorizar(true);
            }
        }
    }




    public AdapterZonasUbicaciones(@NonNull Context context, @LayoutRes int resource, @NonNull List<LugarTrabajo> listPosicionesZonas) {
        super(context, resource, listPosicionesZonas);
        this.context=context;
        this.listPosicionesZonas=listPosicionesZonas;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listPosicionesZonas.size();
    }

    @Nullable
    @Override
    public LugarTrabajo getItem(int position) {
       return this.listPosicionesZonas.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_zona_ubicacion, parent, false);
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

        ColorGenerator generator=ColorGenerator.MATERIAL;
        int selectedColor=generator.getRandomColor();
        final LugarTrabajo selectedItem=getItem(position);
        final boolean isUbicacion=selectedItem.getTipoPosicion().equals(Constants.UBICACIONES);
        String title=isUbicacion? selectedItem.getVaNombrePos(): selectedItem.getVaDescripcionZnPosic();
        viewHolder.tvZonaNombre.setText(title);
        TextDrawable drawable = TextDrawable.builder().buildRound(title.substring(0, 1), selectedColor);
        viewHolder.imageViewCircleNombre.setImageDrawable(drawable);
        viewHolder.btnSolicitar.setBackgroundColor(selectedColor);
        viewHolder.expandableLayout.setExpanded(false);
        viewHolder.imageFlecha.setImageResource(isUbicacion? R.drawable.ubicacion_lista:R.drawable.flecha_lista);
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
            }
            @Override
            public void onPreClose() {
                viewHolder.tvZonaDetalle.setVisibility(View.GONE);
                viewHolder.btnSolicitar.setVisibility(View.GONE);
                viewHolder.imageFlecha.setImageResource(R.drawable.flecha_lista);

            }
            @Override
            public void onOpened() {
               getZonaDetalleAsync(getItem(position),viewHolder);
            }
            @Override
            public void onClosed() {
            }

        });
        viewHolder.btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    eliminarZonaAsync(selectedItem, viewHolder);
            }
        });
        viewHolder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(isUbicacion){
                    Intent intent=new Intent(getContext(),MapaZonaActivity.class);
                    Bundle mBundle=new Bundle();
                    mBundle.putSerializable(Constants.SELECTED_ZONA_TIENDA,getItem(position));
                    mBundle.putBoolean(Constants.ELIMINAR_UBICACION,true);
                    mBundle.putString(Constants.SELECTED_ID_EMPLEADO,getCurrentNumeroEmpleado());
                    mBundle.putBoolean(Constants.IS_PLANTILLA,isPlantilla());
                    intent.putExtras(mBundle);
                    getContext().startActivity(intent);
                }
                else{
                    viewHolder.expandableLayout.toggle();
                    /*
                      viewHolder.expandableLayout.setExpanded(!viewHolder.expandableLayout.isExpanded());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               if(!viewHolder.expandableLayout.isExpanded()){
                                   viewHolder.expandableLayout.expand();
                               }
                               else{
                                   viewHolder.expandableLayout.collapse();
                               }
                            }
                        },50);*/
                }
            }
        });
        return convertView;
    }

    public void eliminarZonaAsync(LugarTrabajo selectedItem, final  ViewHolder viewHolder){
            EliminarZonaRequest request = new EliminarZonaRequest();
            request.setIdNumEmpleado(this.getCurrentNumeroEmpleado());
            request.setId_csc_zo_pos(selectedItem.getIdCscZoPos());
            controllerAPI.eliminarZonaUsuario(request).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().getError()) {
                            viewHolder.btnSolicitar.setEnabled(false);
                            viewHolder.expandableLayout.toggle();
                            snackBarBuilder.showSuccess(context.getString(R.string.tu_solicitud_se_ha_enviado_correctamente).concat("\n").concat(context.getString(R.string.espera_la_autorizaci_n_de_tu_jefe)));
                        }
                        else {
                            snackBarBuilder.showError(response.body().getMensajeError());
                            viewHolder.expandableLayout.toggle();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    snackBarBuilder.showError(getContext().getString(R.string.Error_Conexion));
                    viewHolder.expandableLayout.toggle();
                }
            });

    }

    public void getZonaDetalleAsync(LugarTrabajo selectedItem, final ViewHolder viewHolder){
        ZonaDetalleRequest request=new ZonaDetalleRequest();
        ZonaList zonaList=new ZonaList();
        zonaList.setIdCscZoPos(selectedItem.getIdCscZoPos());
        zonaList.setVaDescripcionZnPosic(selectedItem.getVaDescripcionZnPosic());
        request.setZona(zonaList);
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
                    else{
                        snackBarBuilder.showError(response.body().getMensajeError());
                        viewHolder.expandableLayout.toggle();
                    }
                }
            }

            @Override
            public void onFailure(Call<ZonaPosicionResponse> call, Throwable t) {
                snackBarBuilder.showError(getContext().getString(R.string.Error_Conexion));
                viewHolder.expandableLayout.toggle();
            }
        });
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new HeaderViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_header_zona_ubicacion, parent, false);
            viewHolder.tvHeader = (TextView) convertView.findViewById(R.id.tvHeader);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }
        viewHolder.tvHeader.setText(getItem(position).getTipoPosicion());
        return  convertView;
    }

    @Override
    public long getHeaderId(int position){
        return  getItem(position).getTipoPosicion().hashCode();
    }
}
