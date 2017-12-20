package com.sociomas.core.WebService.Services;
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
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.Sugerencia.SugerenciaResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jromeromar on 22/09/2017.
 */

public interface APIAventon {
    @POST("ConsultarPreferenciasEmpleado")
    Call<ResponseBody>obtenerPreferenciasPerfil(@Body ServerRequest request);
    @POST("InsertarPreferenciasEmpleado")
    Call<ResponseBody>insertarPreferenciasPerfil(@Body EditarPreferenciasRequest request);
    @POST("ModificarPreferenciasEmpleado")
    Call<ResponseBody>modificarPreferenciasPerfil(@Body EditarPreferenciasRequest request);
    @POST("PublicarAventon")
    Call<ResponseBody>publicarAventon(@Body PublicarRequest request);
    @POST("GuardarTokenFirebase")
    Call<ServerResponse>enviarTokenFireBase(@Body TokenRequest request);
    //region ALBERTO
    @POST("AltaAutomovil")
    Call<ServerResponse> generarAltaAuto(@Body AltaAutoRequest request);
    @POST("EditarAutomovil")
    Call<ServerResponse>editarAutomovil(@Body EditarAutoRequest request);
    @POST("ObtenerListaAutomoviles")
    Call<ResponseBody>obtenerListaAuto(@Body ServerRequest requestList);
    @POST("ConsultaAventon")
    Call<ConsultaAventonesResponse>consultarAventon(@Body ConsultarAventonRequest request);
    @POST("ConsultaUsuariosPiloto")
    Call<PilotoResponse>consultarUsuarioPiloto(@Body ServerRequest request);
    @POST("ObtenerRolAventon")
    Call<RolResponse>obtenerRolAventon(@Body ConsultaRolRequest request);
    @POST("integrarQR")
    Call<ResponseBody>integrarQR(@Body AltaQR request);
    //endregion
    @POST("ObtenerCoordenadasRuta")
    Call<ResponseBody>consultarCoordenadasRuta(@Body CoordenadasRutaModelResponse request);
    @POST("ConsultarPosiciones")
    Call<SugerenciaResponse>consultarPosicionesValida(@Body PosicionRequest request);
    @POST("ReservarAsientos")
    Call<ResponseBody>reservarAsientos(@Body SolicitarAventonRequest request);
    @POST("EnlistaAventones")
    Call<ConsultaMisAventones>miListaAventones(@Body VerMisAventonesRequest request);
    @POST("CambiarEstatusAsientos")
    Call<ServerResponse>cambiarStatusAsientos(@Body CambiarStatusAsientosRequest request);
    @POST("InsertarDatosPasajeroQR")
    Call<ServerResponseAventones>insertarQR(@Body AltaQR request);
    @POST("EstatusMisAventones")
    Call<AventonesReservadosResponse>aventonesReservados(@Body AventonesReservadosRequest request);
    @POST("ObtenerSolicitudesAventon")
    Call<PasajerosResponse>obtenerPasajerosAventon(@Body PasajerosRequest request);
    @POST("InsertaUsuariosPiloto")
    Call<ServerResponse>insertarUsuarioPiloto(@Body UsuarioPilotoRequest request);
    @POST("AdministraUsuariosPiloto")
    Call<PilotoResponse>administrarUsuarioPiloto(@Body ServerRequest request);
    @POST("ConsultaOpcionesMenu")
    Observable<MenuResponse>consultarOpcionesMenu(@Body ServerRequest request);
}