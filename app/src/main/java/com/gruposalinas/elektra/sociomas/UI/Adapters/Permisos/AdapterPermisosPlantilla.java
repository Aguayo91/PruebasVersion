package com.gruposalinas.elektra.sociomas.UI.Adapters.Permisos;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolders.ViewHolderPermisos;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.InputBox;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Security.SecurityItems;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumExclusion;
import com.sociomas.core.Utils.Enums.EnumStatusExclusion;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Response.Permisos.Exclusiones;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 22/05/2017.
 */

public class AdapterPermisosPlantilla extends BaseAdapter implements  InputBox.inputBoxCallBack {

    private ListExclusiones selectedItem;
    private Context context;
    private ArrayList<ListExclusiones>listExclusiones;
    private LayoutInflater layoutInflater;
    private InputBox inputBox;
    private SnackBarBuilder snackBar;
    private int selectedPosition;
    public AdapterPermisosPlantilla(Context context, ArrayList<ListExclusiones>listExclusiones){
        this.context=context;
        this.listExclusiones=listExclusiones;
        this.inputBox=new InputBox(this.context);
        inputBox.setTitle("Motivo");
        inputBox.setHint("Escribe el motivo");
        inputBox.setErrorText(context.getString(R.string.escriba_motivo));
        this.layoutInflater=LayoutInflater.from(context);
        this.snackBar=new SnackBarBuilder((Activity)context);
    }


    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public int getCount() {
        return listExclusiones.size();
    }

    @Override
    public ListExclusiones getItem(int i) {
        return listExclusiones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolderPermisos holder;
        if(view==null){
            holder=new ViewHolderPermisos();
            view = layoutInflater.inflate(R.layout.item_permisos_plantilla, viewGroup, false);
            holder.tvStatus=(TextView)view.findViewById(R.id.tvStatus);
            holder.tvName=(TextView)view.findViewById(R.id.tvName);
            holder.ImgEmpleado=(CircleImageView)view.findViewById(R.id.imgActividad);
            holder.tvIncidencia=(TextView)view.findViewById(R.id.tvIncidencia);
            holder.tvComentario=(TextView)view.findViewById(R.id.tvComentario);
            holder.tvDate=(TextView)view.findViewById(R.id.tvDate);
            holder.tvDate2=(TextView)view.findViewById(R.id.tvDate2);
            holder.tvDate3=(TextView)view.findViewById(R.id.tvDate3);
            holder.tvComentario=(TextView)view.findViewById(R.id.tvComentario);
            holder.txtComentarioPermiso=(EditText)view.findViewById(R.id.txtComentarioPermiso);
            holder.tvTotalPermisos=(TextView)view.findViewById(R.id.tvTotalPermisos);
            holder.tvTotalFaltas=(TextView)view.findViewById(R.id.tvTotalFaltas);
            holder.tvTotalRetardos=(TextView)view.findViewById(R.id.tvTotalRetardos);
            holder.tvSalidaAntes=(TextView)view.findViewById(R.id.tvSalidaAntes);
            holder.tvNumeroEmpleado=(TextView)view.findViewById(R.id.tvNumeroEmpleado);
            holder.btnAutorizar=(Button)view.findViewById(R.id.btnAutorizar);
            holder.btnRechazar=(Button)view.findViewById(R.id.btnRechazar);
            holder.ImgTipo=(ImageView)view.findViewById(R.id.img_tipo);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolderPermisos) view.getTag();
        }
        selectedItem=getItem(i);
        setSelectedPosition(i);
        holder.tvName.setText(selectedItem.getVaNombreCompleto());
        holder.tvDate.setText(selectedItem.getFechaHoraInicial());
        holder.tvDate2.setText(selectedItem.getFechaHoraFinal());
        holder.tvDate3.setText(selectedItem.getFecha());
        holder.tvIncidencia.setText(selectedItem.getDescripcionExclusion());
        holder.tvComentario.setVisibility(selectedItem.getMotivo()!=null && !selectedItem.getMotivo().equals("")? View.VISIBLE:View.GONE);
        holder.tvComentario.setText(selectedItem.getMotivo());
        holder.txtComentarioPermiso.setText("");
        holder.tvNumeroEmpleado.setText(selectedItem.getIdNumEmpleado());
        holder.tvTotalPermisos.setText(String.valueOf(selectedItem.getPermisos()));
        holder.tvTotalFaltas.setText(String.valueOf(selectedItem.getFaltas()));
        holder.tvTotalRetardos.setText(String.valueOf(selectedItem.getRetardos()));
        holder.tvSalidaAntes.setText(String.valueOf(selectedItem.getSalidas_antes()));

        Picasso.with(this.context).load(AdapterUtils.getResourceFileByTipoJustificacion(selectedItem.getDescripcionEstatusExclusion()))
                .into(holder.ImgTipo);
        holder.btnAutorizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem.setEstatusRequest(EnumStatusExclusion.autorizado.getValue());
                aceptarRechazarAsync(selectedItem);
            }
        });
        holder.btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem.setEstatusRequest(EnumStatusExclusion.rechazado.getValue());
                aceptarRechazarAsync(selectedItem);
            }
        });


        holder.txtComentarioPermiso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence texto, int i, int i1, int i2) {
                selectedItem.setComentario(texto.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;

    }
    private void aceptarRechazarAsync(ListExclusiones item){
        if(selectedItem.getComentario()==null ||(selectedItem.getComentario().isEmpty())){
            snackBar.showError(context.getString(R.string.comentario_vacio));
            return;
        }
        final CustomProgressBar progressBar=new CustomProgressBar(this.context);
        progressBar.show((Activity)this.context);
        ControllerAPI controllerAPI=new ControllerAPI(context);
        String numeroEmpleado=item.getIdNumEmpleado();
        SecurityItems securityItems=new SecurityItems(numeroEmpleado);
        final Exclusiones exclusion=new Exclusiones();
        exclusion.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT1);
        try {
            exclusion.setDtFechaHoraInicial(Utils.getJsonDate(dateFormat.parse(item.getFechaHoraInicial())));
            exclusion.setDtFechaHoraFinal(Utils.getJsonDate(dateFormat.parse(item.getFechaHoraFinal())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        exclusion.setComentario(item.getComentario());
        exclusion.setToken(securityItems.getTokenEncrypt());
        EnumExclusion enumExclusion=EnumExclusion.getFromString(item.getDescripcionExclusion());
        exclusion.setTipoExclusion(enumExclusion.getValue());
        exclusion.setEstatusRequest(item.getEstatusRequest());
        exclusion.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        exclusion.setId_exclusion(item.getIdExclusion());




        controllerAPI.registrarActualizarExclusion(exclusion).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        Utils.saveServerTime(response.body().getServerTime());
                        EnumStatusExclusion tipo= EnumStatusExclusion.getByValue(exclusion.getEstatusRequest());
                        String mensaje= context.getString(tipo==EnumStatusExclusion.autorizado? R.string.permiso_aprobado: R.string.permiso_rechazado);
                        snackBar.showSuccess(mensaje);
                        listExclusiones.remove(getSelectedPosition());
                        notifyDataSetChanged();
                    }
                    else if(response.body().getMensajeError()!=null){
                        snackBar.showError(response.body().getMensajeError());
                    }
                    else{
                        snackBar.showError(context.getString(R.string.Error_Conexion));
                    }
                }
                else{
                    snackBar.showError(context.getString(R.string.Error_Conexion));
                }
                progressBar.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                    progressBar.dismiss();
                snackBar.showError(context.getString(R.string.Error_Conexion));

            }

        });
    }

    @Override
    public void OnResult(String result) {
        if(selectedItem!=null) {
            selectedItem.setComentario(result);
            aceptarRechazarAsync(selectedItem);
        }
    }

    @Override
    public void OnCancel() {

    }
}
