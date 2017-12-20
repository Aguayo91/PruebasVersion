package com.gruposalinas.elektra.sociomas.UI.Activities.Inicio;

import android.util.Log;

import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Security.DecryptUtils;
import com.sociomas.core.MVP.BaseInteractor;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.Utils.Enums.EnumEstatusNotificacion;
import com.sociomas.core.Utils.Enums.EnumNotificacion;
import com.sociomas.core.Utils.Enums.EnumTipoNotificacion;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Request.Notificaciones.ConsultaNotificacionesRequest;
import com.sociomas.core.WebService.Model.Request.Registro.ModificarNotificacionRequest;
import com.sociomas.core.WebService.Model.Response.Notificaciones.CatalogoNotificaciones;
import com.sociomas.core.WebService.Model.Response.Notificaciones.ListaCatalogoNotificacionesResponse;
import com.sociomas.core.WebService.Model.Response.Registro.Empleado;
import com.sociomas.core.WebService.Model.Response.Registro.Team;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 03/11/2017.
 */

public class InicioInteractor extends BaseInteractor {

    private static final String TAG = "InicioInteractor";

    public InicioInteractor(){
        super(ApplicationBase.getIntance().getApplicationContext(),ApplicationBase.getIntance().getControllerAPI());
    }

    /**
     * Modifica el etatu de la notific
     * @param noti
     * @param idNotificacion
     */
    public void modificarEstatusNotificacion(EnumEstatusNotificacion noti, int idNotificacion){
        ModificarNotificacionRequest request = new ModificarNotificacionRequest();
        request.setId_estatus_notificacion(noti.getValue());
        request.setId_estatus_usuario_notificacion(idNotificacion);
        Utils.updateNotificacionEstatus(ApplicationBase.getIntance().getApplicationContext(),true);
        getControllerAPI().modificarEstatusNotificacion(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                     if(!response.body().getError()){
                        consultarNotificaciones();
                    }
                }
                else{
                    Log.d(TAG, "onResponse: Ocurrió un error al consultar");
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
            }
        });
    }
    /**
     * Consulta las notificaciones y actualiza el status por default
     */
    public void consultarNotificaciones(){
        ConsultaNotificacionesRequest request = new ConsultaNotificacionesRequest();
        request.setTipo_vista(EnumNotificacion.LISTA_CATALOGO_NOTIFICACIONES.getValue());
        getControllerAPI().consultarNotificaciones(request).enqueue(new Callback<ListaCatalogoNotificacionesResponse>() {
            @Override
            public void onResponse(Call<ListaCatalogoNotificacionesResponse> call, Response<ListaCatalogoNotificacionesResponse> response) {
               if(response.isSuccessful()) {
                   ListaCatalogoNotificacionesResponse listCatalogo = response.body();
                   if (!listCatalogo.getError()) {
                       if (listCatalogo.getCatalogoNotificaciones() != null) {
                           Observable.fromIterable(listCatalogo.getCatalogoNotificaciones()).filter(new Predicate<CatalogoNotificaciones>() {
                               @Override
                               public boolean test(CatalogoNotificaciones cn) throws Exception {
                                   return cn.getDescripcion().equals(EnumTipoNotificacion.Generales.toString());
                               }
                           }).subscribe(new Consumer<CatalogoNotificaciones>() {
                               @Override
                               public void accept(CatalogoNotificaciones c) throws Exception {
                                   if(c!=null){
                                       Utils.setNotificacionEstatus(ApplicationBase.getIntance().getApplicationContext(), c.getContador());
                                       Log.d(TAG, "accept: Contador actualizado");
                                   }
                               }
                           });
                       }
                   }
               }
            }
            @Override
            public void onFailure(Call<ListaCatalogoNotificacionesResponse> call, Throwable t) {
            }
        });
    }
    public void saveEmpleadoInDb(Empleado empleado){
        DBUtils db=new DBUtils(ApplicationBase.getIntance().getApplicationContext());
        String numeroEmpleado=DecryptUtils.decryptAES(empleado.getIdNumEmpleado());
        empleado.setIdNumEmpleado(numeroEmpleado);
        db.agregarEmpleado(empleado);
    }

    public void savePlantillaEmpleado(ArrayList<Team>listPlantilla){
        final DBUtils db=new DBUtils(ApplicationBase.getIntance().getApplicationContext());

        if(listPlantilla!=null){
            Observable.fromIterable(listPlantilla).subscribe(new Consumer<Team>() {
                @Override
                public void accept(Team t) throws Exception {
                    t.setNumeroEmpleado(DecryptUtils.decryptAES(t.getNumeroEmpleado()));
                    db.agregarPlantillaEmpledo(t);
                }
            });
        }
    }
}
