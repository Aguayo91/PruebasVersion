package com.gruposalinas.elektra.sociomas.UI.Activities.Inicio;

import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumNotificacion;
import com.sociomas.core.Utils.Enums.EnumTipoNotificacion;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Contacto.RootConfiguracion;
import com.sociomas.core.WebService.Model.Request.Registro.CheckEmpleadoRequest;
import com.sociomas.core.WebService.Model.Request.Notificaciones.ConsultaNotificacionesRequest;
import com.sociomas.core.WebService.Model.Request.Registro.RegistroRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.TokenFireBase.TokenRequest;
import com.sociomas.core.WebService.Model.Response.Configuracion.EmpleadoSettingResponse;
import com.sociomas.core.WebService.Model.Response.Contacto.ConfiguracionResponse;
import com.sociomas.core.WebService.Model.Response.Menu.MenuResponse;
import com.sociomas.core.WebService.Model.Response.Notificaciones.CatalogoNotificaciones;
import com.sociomas.core.WebService.Model.Response.Notificaciones.ListaCatalogoNotificacionesResponse;
import com.sociomas.core.WebService.Model.Response.Registro.RegistroResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.net.HttpURLConnection;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 04/10/2017.
 */

public class InicioPresenterImpl extends BasePresenterImpl implements InicioPresenter {

    public static final String TAG=InicioPresenter.class.getName();

    @Override
    public void sincronizarTokenFireBase() {
        FirebaseApp.initializeApp(ApplicationBase.getIntance().getApplicationContext());
        String token = FirebaseInstanceId.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            TokenRequest tokenRequest = new TokenRequest();
            tokenRequest.setTokenDispositivo(token);
            ApplicationBase.getIntance().getControllerAventon().enviarTokenFireBase(tokenRequest).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "TOKEN FIREBASE SINCRONIZADO CORRECTMENTE");
                    }
                }
                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e(TAG, "ERROR AL SINCRONIZAR TOKEN DE FIREBASE");
                }
            });
        }
        else {
            Log.e(TAG, "El token de firebase se encuentra vacio");
        }
    }

    @Override
    public void checkIfUsuarioPilotoAventon() {

        ApplicationBase.getIntance().getControllerAventon().consultarOpcionesMenu(new ServerRequest()).subscribe(new Observer<MenuResponse>() {
            @Override
            public void onSubscribe(Disposable d) {}
            @Override
            public void onNext(final MenuResponse response) {
                if(!response.getError()){
                    final SessionManager manager=ApplicationBase.getIntance().getManager();
                    if(response.getListaOpcionesMenu()!=null) {
                        Observable.fromIterable(response.getListaOpcionesMenu()).subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer value) throws Exception {

                                switch (value){
                                    case 1:
                                        manager.add(Constants.IS_USUARIO_AVENTON,true);
                                        break;
                                    case 2:
                                        manager.add(Constants.IS_USUARIO_NOMINA,true);
                                        break;
                                    case 3:
                                        manager.add(Constants.MOSTRAR_NUEVO_GAFETE,true);
                                        break;
                                }


                            }
                        });
                    }
                    else{
                        manager.add(Constants.IS_USUARIO_AVENTON,false);
                        manager.add(Constants.IS_USUARIO_NOMINA,false);
                        manager.add(Constants.MOSTRAR_NUEVO_GAFETE,false);
                    }
                }
            }
            @Override
            public void onError(Throwable e) {}
            @Override
            public void onComplete() {}
        });
    }





    @Override
    public void obtenerConfiguracionAsync() {


        ApplicationBase.getIntance().getControllerAPI().checkEmpleado(new CheckEmpleadoRequest(Utils.getNumeroEmpleado(ApplicationBase.getIntance().getApplicationContext()))).enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        InicioInteractor interactor=new InicioInteractor();
                        interactor.savePlantillaEmpleado(response.body().getPlantilla());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {

            }
        });


        ApplicationBase.getIntance().getControllerAPI().getConfiguracion(new RootConfiguracion()).enqueue(new Callback<ConfiguracionResponse>() {
            @Override
            public void onResponse(Call<ConfiguracionResponse> call, Response<ConfiguracionResponse> response) {
                if(response.isSuccessful()) {
                    if(!response.body().getError()){
                        ApplicationBase.getIntance().getManager().add(Constants.ID_ROL_EMPLEADO,response.body().getId_rol());
                    }
                }
            }@Override
            public void onFailure(Call<ConfiguracionResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void obtenerRangosMonitoreoEmpleado() {
        ApplicationBase.getIntance().getControllerAPI().getSettingsEmpleado(new RegistroRequest()).enqueue(new Callback<EmpleadoSettingResponse>() {
            @Override
            public void onResponse(Call<EmpleadoSettingResponse> call, Response<EmpleadoSettingResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        DBUtils dbUtils=new DBUtils(ApplicationBase.getAppContext());
                        dbUtils.agregarHorarioEmpleado(response.body().getRangosMonitoreo());
                    }
                }
            }

            @Override
            public void onFailure(Call<EmpleadoSettingResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void initTracking() {
        Utils.llamarBroadCastInicio(ApplicationBase.getIntance().getApplicationContext());
    }

    @Override
    public void consultarNotificaciones() {
        ConsultaNotificacionesRequest request = new ConsultaNotificacionesRequest();
        request.setTipo_vista(EnumNotificacion.LISTA_CATALOGO_NOTIFICACIONES.getValue());
        getControllerAPI().consultarNotificaciones(request).enqueue(new Callback<ListaCatalogoNotificacionesResponse>() {
            @Override
            public void onResponse(Call<ListaCatalogoNotificacionesResponse> call, Response<ListaCatalogoNotificacionesResponse> response) {
                try {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        //TODO implementar mostra numero de notificaciones no leidas en campanita
                        if (response.body() != null) {
                            ListaCatalogoNotificacionesResponse listCatalogo = response.body();
                            if (!listCatalogo.getError()) {
                                if (listCatalogo.getCatalogoNotificaciones() != null) {
                                    for (CatalogoNotificaciones cn : listCatalogo.getCatalogoNotificaciones()) {
                                        if (cn.getDescripcion().equals(EnumTipoNotificacion.Generales.toString())) {
                                            ViewEvent eventNotificacion = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                            eventNotificacion.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvNumeroNotificaciones);
                                            eventNotificacion.getModel().put(ViewEvent.ENTRY, cn.getContador());
                                            notifyData(eventNotificacion);
                                        } else {
                                            //TODO implementar otros tipos de notificaciones
                                        }
                                    }
                                } else {
                                    ViewEvent eventNotificacion = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                    eventNotificacion.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvNumeroNotificaciones);
                                    eventNotificacion.getModel().put(ViewEvent.ENTRY, 0);
                                    notifyData(eventNotificacion);
                                }
                            } else {
                                Log.e(TAG, listCatalogo.getMensajeError());
                                ViewEvent eventNotificacion = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                eventNotificacion.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvNumeroNotificaciones);
                                eventNotificacion.getModel().put(ViewEvent.ENTRY, 0);
                                notifyData(eventNotificacion);
                            }
                        } else {
                            //TODO implementar no mostrar numero de notificaciones no leidas
                            ViewEvent eventNotificacion = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                            eventNotificacion.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvNumeroNotificaciones);
                            eventNotificacion.getModel().put(ViewEvent.ENTRY, 0);
                            notifyData(eventNotificacion);
                        }
                    } else {
                        //TODO implementar no mostrar numero de notificaciones no leidas
                        Log.e(TAG, "HTTPERROR: " + response.code());
                        ViewEvent eventNotificacion = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                        eventNotificacion.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvNumeroNotificaciones);
                        eventNotificacion.getModel().put(ViewEvent.ENTRY, 0);
                        notifyData(eventNotificacion);
                    }
                }
                catch (Exception e) {
                    Log.e(TAG, e.toString());
                    ViewEvent eventNotificacion = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                    eventNotificacion.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvNumeroNotificaciones);
                    eventNotificacion.getModel().put(ViewEvent.ENTRY, 0);
                    notifyData(eventNotificacion);
                }
            }

            @Override
            public void onFailure(Call<ListaCatalogoNotificacionesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                ViewEvent eventNotificacion = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                eventNotificacion.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvNumeroNotificaciones);
                eventNotificacion.getModel().put(ViewEvent.ENTRY, 0);
                notifyData(eventNotificacion);
            }
        });
    }

    public ControllerAPI getControllerAPI() {
        return ApplicationBase.getIntance().getControllerAPI();
    }
}
