package com.sociomas.core.WebService.Controllers.Aventon;
import android.content.Context;
import com.google.gson.Gson;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacksAventones.CallBackPreferencias;
import com.sociomas.core.WebService.CallBacksAventones.CallBackVehiculo;
import com.sociomas.core.WebService.Controllers.ControllerBase;
import com.sociomas.core.WebService.Model.Request.Alta.AltaAutoRequest;
import com.sociomas.core.WebService.Model.Request.Alta.AltaQR;
import com.sociomas.core.WebService.Model.Request.Alta.EditarAutoRequest;
import com.sociomas.core.WebService.Model.Request.Alta.ServerResponseAventones;
import com.sociomas.core.WebService.Model.Request.CambiarStatusAsientos.CambiarStatusAsientosRequest;
import com.sociomas.core.WebService.Model.Request.Consulta.ConsultaRolRequest;
import com.sociomas.core.WebService.Model.Request.Consulta.ConsultarAventonRequest;
import com.sociomas.core.WebService.Model.Request.Pasajeros.PasajerosRequest;
import com.sociomas.core.WebService.Model.Request.Perfil.EditarPreferenciasRequest;
import com.sociomas.core.WebService.Model.Request.PosicionCercana.PosicionRequest;
import com.sociomas.core.WebService.Model.Request.Publicar.PublicarRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.SolicitarAventon.AventonesReservadosRequest;
import com.sociomas.core.WebService.Model.Request.SolicitarAventon.SolicitarAventonRequest;
import com.sociomas.core.WebService.Model.Request.TokenFireBase.TokenRequest;
import com.sociomas.core.WebService.Model.Request.UsuarioPiloto.UsuarioPilotoRequest;
import com.sociomas.core.WebService.Model.Request.VerMisAventonesRequest;
import com.sociomas.core.WebService.Model.Response.Aventones.AventonesReservadosResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.ConsultaAventonesResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.ConsultaMisAventones;
import com.sociomas.core.WebService.Model.Response.Aventones.CoordenadasRutaModelResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.RolResponse;
import com.sociomas.core.WebService.Model.Response.Menu.MenuResponse;
import com.sociomas.core.WebService.Model.Response.Pasajeros.PasajerosResponse;
import com.sociomas.core.WebService.Model.Response.PilotoResponse.PilotoResponse;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.PreferenciaResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.ListadoAutos;
import com.sociomas.core.WebService.Model.Response.Sugerencia.SugerenciaResponse;
import com.sociomas.core.WebService.Services.APIAventon;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class ControllerAventon extends ControllerBase implements APIAventon {

    private APIAventon apiAventon;
    //Url Cambiar cada vez que se consuma el servicio
    public ControllerAventon(Context context) {
        super(context, Constants.DOMAIN_URL_STAGING_AVENTONES);
        this.apiAventon = retrofit.create(APIAventon.class);
    }

    //Alta automóviles
    @Override
    public Call<ServerResponse> generarAltaAuto(@Body AltaAutoRequest request) {
        SecurityItems securityItems= getInstanceToken(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.generarAltaAuto(request);
    }

    //Alta perfíl
    @Override
    public Call<ResponseBody> insertarPreferenciasPerfil(@Body EditarPreferenciasRequest request) {
        SecurityItems securityItems= getInstanceToken(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return apiAventon.insertarPreferenciasPerfil(request);
    }

    @Override
    public Call<ResponseBody> modificarPreferenciasPerfil(@Body EditarPreferenciasRequest request) {
        SecurityItems securityItems= getInstanceToken(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return apiAventon.modificarPreferenciasPerfil(request);
    }

    @Override
    public Call<ResponseBody> publicarAventon(@Body PublicarRequest request) {
        SecurityItems securityItems= getInstanceToken(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return  apiAventon.publicarAventon(request);
    }

    @Override
    public Call<ServerResponse> enviarTokenFireBase(@Body TokenRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        return  this.apiAventon.enviarTokenFireBase(request);
    }

    //Consultar automóviles
    @Override
    public Call<ResponseBody> obtenerListaAuto(@Body ServerRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.obtenerListaAuto(request);
    }
    //Editar automóviles
    @Override
    public Call<ServerResponse> editarAutomovil(@Body EditarAutoRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.editarAutomovil(request);
    }
    //Consultar preferencias
    @Override
    public Call<ResponseBody> obtenerPreferenciasPerfil(@Body ServerRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return  this.apiAventon.obtenerPreferenciasPerfil(request);
    }
    //Obtener Rol usuario
    @Override
    public Call<RolResponse> obtenerRolAventon(@Body ConsultaRolRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.obtenerRolAventon(request);
    }

    @Override
    public Call<ResponseBody> integrarQR(@Body AltaQR request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.integrarQR(request);
    }
    @Override
    public Call<ServerResponseAventones> insertarQR(@Body AltaQR request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.insertarQR(request);
    }
    //Consultar Aventones
    @Override
    public Call<ConsultaAventonesResponse>consultarAventon(@Body ConsultarAventonRequest request){
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.consultarAventon(request);
    }

    @Override
    public Call<PilotoResponse> consultarUsuarioPiloto(@Body ServerRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.consultarUsuarioPiloto(request);
    }

    @Override

    public Call<ResponseBody> consultarCoordenadasRuta(@Body CoordenadasRutaModelResponse request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.consultarCoordenadasRuta(request);
    }

    public Call<SugerenciaResponse> consultarPosicionesValida(@Body PosicionRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.consultarPosicionesValida(request);
    }

    @Override
    public Call<ResponseBody> reservarAsientos(@Body SolicitarAventonRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.reservarAsientos(request);
    }

    @Override
     //Mis aventones publicados
    public Call<ConsultaMisAventones> miListaAventones(@Body VerMisAventonesRequest request) {

        SecurityItems securityItems = getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.miListaAventones(request);
    }

    public Call<AventonesReservadosResponse> aventonesReservados(@Body AventonesReservadosRequest request) {
        SecurityItems securityItems = getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.aventonesReservados(request);
    }

    @Override
    public Call<PasajerosResponse> obtenerPasajerosAventon(@Body PasajerosRequest request) {
        SecurityItems securityItems = getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.obtenerPasajerosAventon(request);
    }

    @Override
    public Call<ServerResponse> insertarUsuarioPiloto(@Body UsuarioPilotoRequest request) {
        SecurityItems securityItems = getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.insertarUsuarioPiloto(request);
    }

    @Override
    public Call<PilotoResponse> administrarUsuarioPiloto(@Body ServerRequest request) {
        SecurityItems securityItems = getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.administrarUsuarioPiloto(request);
    }

    @Override
    public Observable<MenuResponse> consultarOpcionesMenu(@Body ServerRequest request) {
        SecurityItems securityItems = getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return this.apiAventon.consultarOpcionesMenu(request)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }


    @Override
        public Call<ServerResponse> cambiarStatusAsientos (@Body CambiarStatusAsientosRequest request){
            SecurityItems securityItems = getInstanceToken();
            request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
            request.setToken(securityItems.getTokenEncrypt());
            return this.apiAventon.cambiarStatusAsientos(request);

    }

    public  void obtenerPreferenciasAsync(ServerRequest request, final CallBackPreferencias callBackPreferencias){
        this.obtenerPreferenciasPerfil(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        PreferenciaResponse preferenciaResponse = new Gson().fromJson(response.body().string(), PreferenciaResponse.class);
                        callBackPreferencias.onSuccess(preferenciaResponse.getCaractEmpleado(), preferenciaResponse.getCatPreferenciasEmpleado());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    callBackPreferencias.OnError(new Throwable("Error en petición"));

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
    }

    public void obtenerListadoAutoAsync(ServerRequest request, final CallBackVehiculo callBackVehiculo){

        this.obtenerListaAuto(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String jsonResponse = response.body().string();
                        Gson gson = new Gson();
                        ListadoAutos listadoAutos = gson.fromJson(jsonResponse, ListadoAutos.class);
                        callBackVehiculo.onSucces(listadoAutos.getListVehiculoEmp());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    callBackVehiculo.OnError(new Throwable("Error en la petición"));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackVehiculo.OnError(t);
            }
        });
    }

}
