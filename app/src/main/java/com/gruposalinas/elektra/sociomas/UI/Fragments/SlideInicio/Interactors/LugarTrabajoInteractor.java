package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Interactors;

import android.util.Log;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BaseInteractor;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.CallBacksAventones.CallBackPosiciones;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.SlideInicio.ModificarPrefLugarTrabajoRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EditarTiendaItem;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EliminarTiendaRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.ListLugaresTrabajoRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadEmpresaEmpleadoResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaPosicionResponse;

import org.reactivestreams.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 17/11/2017.
 */

public class LugarTrabajoInteractor extends BaseInteractor {
    private static final String TAG = "LugarTrabajoInteractor";
    private boolean isSearching = false;


    public LugarTrabajoInteractor(){
        super(ApplicationBase.getIntance().getApplicationContext(),ApplicationBase.getIntance().getControllerAPI());
    }


    public void descargarImagenUnidadTrabajo(final Subscriber<UnidadEmpresaEmpleadoResponse>subscriber){
        SecurityItems securityItems = new SecurityItems(com.sociomas.core.Utils.Utils.getNumeroEmpleado(getContext()));
        ServerRequest request = new ServerRequest();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        getControllerAPI().consultaUnidadNegocio(request).enqueue(new Callback<UnidadEmpresaEmpleadoResponse>() {
            @Override
            public void onResponse(Call<UnidadEmpresaEmpleadoResponse> call, Response<UnidadEmpresaEmpleadoResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        subscriber.onNext(response.body());
                    }
                    else {
                        subscriber.onError(new Throwable(getContext().getString(R.string.Error_Conexion)));
                    }
                    subscriber.onComplete();
                }
                else{
                    subscriber.onError(new Throwable(getContext().getString(R.string.Error_Conexion)));
                    subscriber.onComplete();
                }
            }

            @Override
            public void onFailure(Call<UnidadEmpresaEmpleadoResponse> call, Throwable t) {
                subscriber.onError(new Throwable(getContext().getString(R.string.Error_Conexion)));
                subscriber.onComplete();
            }
        });
    }


    public void cambiarLugarTrabajo(int tipoLugarTrabajo, final CallBackBaseResponse callBack){
        SimpleDateFormat dtFmt=new SimpleDateFormat(Constants.DATE_FORMAT_AVENTON);
        ModificarPrefLugarTrabajoRequest request = new ModificarPrefLugarTrabajoRequest();
        request.setId_pref_lugar_trabajo(tipoLugarTrabajo);
        request.setFechaDispositivo(dtFmt.format(new Date()).concat(".200")); //IMPORTANTE SI NO CONCATENAS ESTE NÚMERO EL SERVICIO NO FUNCIONA
        getControllerAPI().modificaPrefLugarTrabajo(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()) {
                        callBack.onSuccess(response.body());
                    }
                    else{
                        callBack.OnError(new Throwable(response.body().getMensajeError()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                callBack.OnError(t);
            }
        });
    }




    public void enviarLugarTrabajo(ArrayList<LugarTrabajo>lugaresSeleccionados, Callback<ServerResponse>response){
        EliminarTiendaRequest request = new EliminarTiendaRequest();
        request.setTipo(EnumConsulta.LineaDirecta.toString());
        ArrayList<EditarTiendaItem> list = new ArrayList<>();
        for (LugarTrabajo item : lugaresSeleccionados) {
            EditarTiendaItem tiendaItem = new EditarTiendaItem(item.getVaNumeroPos(), item.getVaComentario());
            list.add(tiendaItem);
        }
        request.setPosiciones(list);
        getControllerAPI().registrarPropuestaUbicacion(request).enqueue(response);
    }

    public void consultarPosicionesRequest(String query, final CallBackPosiciones callBackPosiciones) {
     if(!isSearching) {
         isSearching=true;
         Observable<ZonaPosicionResponse> observable = ApplicationBase.getIntance().getControllerAPI()
                 .consultarPosicionesRx(new ListLugaresTrabajoRequest(query));
         observable.subscribe(new Observer<ZonaPosicionResponse>() {
             @Override
             public void onSubscribe(Disposable d) {
             }

             @Override
             public void onNext(ZonaPosicionResponse value) {
                callBackPosiciones.OnSuccess(value.getPosiciones());
                isSearching=false;
             }

             @Override
             public void onError(Throwable e) {
                 callBackPosiciones.OnError(e);
             }

             @Override
             public void onComplete() {
                 isSearching = false;
             }
         });
         }
         else{
         Log.d(TAG, "consultarPosicionesRequest: is not completed yet!" );
        }
    }
}
