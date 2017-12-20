package com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.FullscreenImageActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolderBase;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.Utils.Blur;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumIncidencia;
import com.sociomas.core.WebService.CallBacks.CallBackAprobarRechazar;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Incidencia.RAprobarJustificante;
import com.sociomas.core.WebService.Model.Request.Incidencia.SolicitarJustificacion;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 16/05/2017.
 */

public class AdapterIncidenciaPlantillaV2  extends BaseAdapter implements CallBackAprobarRechazar {


    public interface CallBackUpdateReady{
        void onReady(boolean ready);
    }

    public static final String TAG="TAG_PLANTILLA";
    private SnackBarBuilder snackBar;
    private String mensajeSuccess;
    private CustomProgressBar customProgressBar;
    private ArrayList<ListadoIncidencias> listIndidencias;
    private Context context;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat dateFormatFrom;
    private ControllerAPI controllerAPI;
    private Date date;
    private CallBackUpdateReady callBackUpdate;
    public void setListIndidencias(ArrayList<ListadoIncidencias> listIndidencias) {
        this.listIndidencias=new ArrayList<>();
        this.listIndidencias=listIndidencias;
        this.controllerAPI=new ControllerAPI(this.context);
        this.date=new Date();
        this.customProgressBar=new CustomProgressBar(this.context);
        this.snackBar=new SnackBarBuilder((Activity)context);
        notifyDataSetChanged();
    }

    public void setCallBackUpdate(CallBackUpdateReady callBackUpdate) {
        this.callBackUpdate = callBackUpdate;
    }

    public AdapterIncidenciaPlantillaV2(Context context, ArrayList<ListadoIncidencias>listIndidencias){
        this.context=context;
        this.setListIndidencias(listIndidencias);
        this.layoutInflater=LayoutInflater.from(context);
        dateFormatFrom=new SimpleDateFormat(Constants.DATE_FORMAT_INCIDENCIA);
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolderBase holder;
        if(view==null){
            holder=new ViewHolderBase();
            view = layoutInflater.inflate(R.layout.item_incidencia_plantilla, viewGroup, false);
            holder.ImgJustificacion=(ImageView) view.findViewById(R.id.img_justificacion);
            holder.tvStatus=(TextView)view.findViewById(R.id.tvStatus);
            holder.tvName=(TextView)view.findViewById(R.id.tvName);
            holder.ImgEmpleado=(CircleImageView)view.findViewById(R.id.imgEmpleado);
            holder.tvIncidencia=(TextView)view.findViewById(R.id.tvIncidencia);
            holder.tvSinJustificar=(TextView)view.findViewById(R.id.tvSinJustificar);
            holder.tvAutorizado=(TextView)view.findViewById(R.id.tvAutorizados);
            holder.tvPendiente=(TextView)view.findViewById(R.id.tvPendienteJustificar);
            holder.tvDate=(TextView)view.findViewById(R.id.tvDate);
            holder.ImgIncidencia=(ImageView) view.findViewById(R.id.imgIncidencia);
            holder.fabDescarga=(FabButton) view.findViewById(R.id.fabDescarga);
            holder.tvComentario=(TextView)view.findViewById(R.id.tvComentario);
            holder.layoutImgIncidencia=(RelativeLayout)view.findViewById(R.id.layoutImgIncidencia);
            holder.layoutAutorizar=(RelativeLayout)view.findViewById(R.id.layoutAutorizar);
            holder.layoutComentarios=(RelativeLayout)view.findViewById(R.id.layoutComentarios);
            holder.txtComentarioIncidencia=(EditText)view.findViewById(R.id.txtComentarioIncidencia);
            holder.btnAutorizar=(Button)view.findViewById(R.id.btnAutorizar);
            holder.btnRechazar=(Button)view.findViewById(R.id.btnRechazar);
            holder.btnAutorizarDirecto=(Button)view.findViewById(R.id.btnAutorizarDirecto);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolderBase) view.getTag();
        }
        //ITEM
        final ListadoIncidencias listadoIncidenciasEmpleado =getItem(i);
        //TIPO DE INCIDENCIA
        final EnumIncidencia enumIncidencia=
                EnumIncidencia.getFromSting(listadoIncidenciasEmpleado.getEstatusJustificacion());

        /*¿TIENE ARCHIVO ADJUNTO?*/
        //SI TIENE UN ARCHIVO ADJUNTO PUEDO MOSTRAR EL PREVIEW DE LA IMAGEN
        holder.layoutImgIncidencia.setVisibility(
           listadoIncidenciasEmpleado.getAdjunto()==null || (listadoIncidenciasEmpleado.getAdjunto().isEmpty())? View.GONE:View.VISIBLE);

        //CARGAR IMAGENES DE EMPLEADO, TIPO DE JUSTIFICACIÓN Y EFECTO DE BLUR
        Picasso.with(context).load(R.drawable.ic_user).into(holder.ImgEmpleado);
        Picasso.with(context).load(AdapterUtils.getResourceFileByTipoJustificacion(listadoIncidenciasEmpleado.getEstatusJustificacion())).resize(60,60).into(holder.ImgJustificacion);
        Picasso.with(context).load(R.drawable.noimage).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                Bitmap blurred = Blur.fastblur(AdapterIncidenciaPlantillaV2.this.context, source, 10);
                source.recycle();
                return blurred;
            }

            @Override
            public String key() {
                return "blur";
            }
        }).resize(200,200).centerCrop()
                .into(holder.ImgIncidencia);
        if(listadoIncidenciasEmpleado.isDescargada() && listadoIncidenciasEmpleado.getSelectedBitmap()!=null){
            holder.ImgIncidencia.setImageBitmap(listadoIncidenciasEmpleado.getSelectedBitmap());
        }
        holder.txtComentarioIncidencia.setText("");
        holder.tvStatus.setText(listadoIncidenciasEmpleado.getEstatusJustificacion());
        holder.tvName.setText(listadoIncidenciasEmpleado.getNombre());
        holder.tvIncidencia.setText(listadoIncidenciasEmpleado.getIncidencia());
        holder.layoutComentarios.setVisibility(listadoIncidenciasEmpleado.getIdJustificacion()==0? View.GONE:View.VISIBLE);
        holder.layoutAutorizar.setVisibility(listadoIncidenciasEmpleado.getIdJustificacion()==0? View.VISIBLE:View.GONE);
        holder.tvComentario.setVisibility(listadoIncidenciasEmpleado.getComentarios()==null
                || (listadoIncidenciasEmpleado.getComentarios().isEmpty())? View.GONE:View.VISIBLE);
        holder.tvComentario.setText(listadoIncidenciasEmpleado.getComentarios());
        if(holder.layoutComentarios.getVisibility()==View.VISIBLE){
            Animation show= AnimationUtils.loadAnimation(context, R.anim.view_show);
            holder.layoutComentarios.startAnimation(show);
        }
        try {
            holder.tvDate.setText(AdapterUtils.getRelativeDate(
                    this.context, listadoIncidenciasEmpleado.getFechaOcurrencia(),dateFormatFrom,
                    date
            ));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.fabDescarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listadoIncidenciasEmpleado.isBotonClick()) {
                    Toast.makeText(context, context.getString(R.string.intentado_descargar), Toast.LENGTH_LONG).show();
                    return;
                }
                listadoIncidenciasEmpleado.setBotonClick(!listadoIncidenciasEmpleado.isBotonClick());
                holder.fabDescarga.showProgress(getItem(i).isBotonClick());
                final Target target=new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        listadoIncidenciasEmpleado.setDescargada(true);
                        holder.ImgIncidencia.setImageBitmap(bitmap);
                        listadoIncidenciasEmpleado.setSelectedBitmap(bitmap);
                        holder.fabDescarga.setVisibility(View.GONE);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        listadoIncidenciasEmpleado.setBotonClick(!listadoIncidenciasEmpleado.isBotonClick());
                        holder.fabDescarga.showProgress(getItem(i).isBotonClick());
                        Toast.makeText(context,context.getString(R.string.no_imagen_vinculada),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(context).load(listadoIncidenciasEmpleado.getAdjunto()).resize(300,300).into(target);
                holder.ImgIncidencia.setTag(target);
            }
        });
        holder.fabDescarga.setVisibility(listadoIncidenciasEmpleado.isDescargada()? View.GONE:View.VISIBLE);
        holder.fabDescarga.showProgress(getItem(i).isBotonClick());
        holder.ImgIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.fabDescarga.getVisibility()==View.GONE){
                    Context context=AdapterIncidenciaPlantillaV2.this.context;
                    Intent intent=new Intent(context, FullscreenImageActivity.class);
                    intent.putExtra(Constants.BITMAP_SELECTED_IMAGE, listadoIncidenciasEmpleado.getSelectedBitmap());
                    context.startActivity(intent);
                }
            }
        });
        holder.txtComentarioIncidencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start,
                                          int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence texto, int start,
                                      int count, int after) {
                listadoIncidenciasEmpleado.setComentarioRechazo(texto.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.btnAutorizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rechazarAprobarAsync(listadoIncidenciasEmpleado,true);
            }
        });
        holder.btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rechazarAprobarAsync(listadoIncidenciasEmpleado,false);
            }
        });

        holder.btnAutorizarDirecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarJustificacion(listadoIncidenciasEmpleado);
            }
        });

        return view;
    }

    @Override
    public void OnError(Throwable error) {
        Toast.makeText(this.context,error.getMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void OnSuccess(ServerResponse response) {
        if(!response.getError()){
            snackBar.showSuccess(mensajeSuccess);
        }
        if(callBackUpdate!=null){
            callBackUpdate.onReady(true);
        }
        customProgressBar.dismiss();
    }


    private void validarJustificacion(final ListadoIncidencias selectedItem){


            SolicitarJustificacion solicitarJustificacion = new SolicitarJustificacion();
            solicitarJustificacion.setIdCscIncid(selectedItem.getCSC());
            solicitarJustificacion.setExtension("JPEG");
            solicitarJustificacion.setVaComentarios(context.getString(R.string.autorizado_jefe));
            solicitarJustificacion.setArchivoAdjunto("");
            solicitarJustificacion.setNombreArchivo("imagenDroid");
            solicitarJustificacion.setTamanoArchivo("0");
            solicitarJustificacion.setIdJustificacion(6);
            solicitarJustificacion.setBitTempFija(false);
            solicitarJustificacion.setIdNumEmpleadoJustifica(selectedItem.getEmpleado());

            this.controllerAPI.agregarJustificacion(solicitarJustificacion).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().getError()) {
                            snackBar.showSuccess(context.getString(R.string.justificacion_aprobada));
                            listIndidencias.remove(selectedItem);
                            notifyDataSetChanged();
                        } else if (response.body().getMensajeError() != null) {
                            snackBar.showError(response.body().getMensajeError());
                        }
                    } else {
                        snackBar.showError(context.getString(R.string.Error_Conexion));
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    snackBar.showError(context.getString(R.string.Error_Conexion));
                }
            });

    }

    private void rechazarAprobarAsync(ListadoIncidencias selectedItem, boolean validar){
            if(selectedItem.getComentarioRechazo().isEmpty()) {
                snackBar.showError(context.getString(R.string.comentario_vacio));
                return;
            }
            customProgressBar.show((Activity) context);
            RAprobarJustificante item = new RAprobarJustificante();
            item.setEmpleado_valida(selectedItem.getEmpleado());
            item.setVa_comentarios(selectedItem.getComentarioRechazo());
            item.setId_csc_incid(selectedItem.getCSC());
            item.setIdCscJustificacion(selectedItem.getIdJustificacion());
            this.controllerAPI.rechazarValidarAsync(item, validar);
            mensajeSuccess = context.getString(validar ? R.string.justificacion_aprobada : R.string.justificacion_rechazada);
            this.controllerAPI.setCallBackAprobarRechazar(this);

    }
}
