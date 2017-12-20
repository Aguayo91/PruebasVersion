package com.sociomas.core.WebService.Services;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.Asistencia.AsistenciaManualRequest;
import com.sociomas.core.WebService.Model.Request.Codigo.CrearCodigoRequest;
import com.sociomas.core.WebService.Model.Request.Codigo.SolicitarCodigoRequest;
import com.sociomas.core.WebService.Model.Request.Codigo.ValidarCodigoRequest;
import com.sociomas.core.WebService.Model.Request.Contacto.Evento;
import com.sociomas.core.WebService.Model.Request.Contacto.RootConfiguracion;
import com.sociomas.core.WebService.Model.Request.Contacto.RootTelefonoContacto;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteCrearRequest;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteImagenRequest;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteObtenerRequest;
import com.sociomas.core.WebService.Model.Request.Horario.EditarHorarioRequest;
import com.sociomas.core.WebService.Model.Request.Horario.HorarioRequest;
import com.sociomas.core.WebService.Model.Request.Horario.HorarioValidar;
import com.sociomas.core.WebService.Model.Request.Horario.ListaEditarHorarioRequest;
import com.sociomas.core.WebService.Model.Request.Incidencia.IncidenciaHistorial;
import com.sociomas.core.WebService.Model.Request.Incidencia.RAprobarJustificante;
import com.sociomas.core.WebService.Model.Request.Incidencia.RootIncidencia;
import com.sociomas.core.WebService.Model.Request.Incidencia.SolicitarJustificacion;
import com.sociomas.core.WebService.Model.Request.ListaEmpleado.ActualizarSupervisorRequest;
import com.sociomas.core.WebService.Model.Request.ListaEmpleado.ListaEmpleadoRequest;
import com.sociomas.core.WebService.Model.Request.ModificarTelefono.ModificarTelefonoRequest;
import com.sociomas.core.WebService.Model.Request.Permisos.RootPermiso;
import com.sociomas.core.WebService.Model.Request.Permisos.SolicitarPermiso;
import com.sociomas.core.WebService.Model.Request.Privacidad.AvisoRequest;
import com.sociomas.core.WebService.Model.Request.Registro.CheckEmpleadoRequest;
import com.sociomas.core.WebService.Model.Request.Notificaciones.ConsultaNotificacionesRequest;
import com.sociomas.core.WebService.Model.Request.Registro.ModificarNotificacionRequest;
import com.sociomas.core.WebService.Model.Request.Registro.RegistroRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.SlideInicio.ModificaUnidadNegocioRequest;
import com.sociomas.core.WebService.Model.Request.SlideInicio.ModificarPrefLugarTrabajoRequest;
import com.sociomas.core.WebService.Model.Request.Ubicaciones.LastLocation;
import com.sociomas.core.WebService.Model.Request.Ubicaciones.RootMovimiento;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.AceptarUbicacionRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.AceptarZonaRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.AsignarZonaRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EliminarTiendaRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EliminarZonaRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.ListLugaresTrabajoRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.ZonaDetalleRequest;
import com.sociomas.core.WebService.Model.Response.Configuracion.EmpleadoSettingResponse;
import com.sociomas.core.WebService.Model.Response.Contacto.ConfiguracionResponse;
import com.sociomas.core.WebService.Model.Response.Contacto.RootClave;
import com.sociomas.core.WebService.Model.Response.Gafete.ResponseGafete;
import com.sociomas.core.WebService.Model.Response.Gafete.ResponseGafeteImagen;
import com.sociomas.core.WebService.Model.Response.Horario.ResponseHorario;
import com.sociomas.core.WebService.Model.Response.Incidencia.CatalogoResponse;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.RootHistorial;
import com.sociomas.core.WebService.Model.Response.ListaEmpleado.ListaEmpleadoReponse;
import com.sociomas.core.WebService.Model.Response.Multimedia.ResponseArchivo;
import com.sociomas.core.WebService.Model.Response.Notificaciones.ListaNotificacionesResponse;
import com.sociomas.core.WebService.Model.Response.NumeroTelefono.TelefonoResponse;
import com.sociomas.core.WebService.Model.Response.Permisos.CatalogoPermisoRes;
import com.sociomas.core.WebService.Model.Response.Permisos.Exclusiones;
import com.sociomas.core.WebService.Model.Response.Permisos.ResponsePermisoIOS;
import com.sociomas.core.WebService.Model.Response.Permisos.ResponsePermisos;
import com.sociomas.core.WebService.Model.Response.Privacidad.ConsultaLegalesResponse;
import com.sociomas.core.WebService.Model.Response.Privacidad.PrivacidadResponse;
import com.sociomas.core.WebService.Model.Response.Notificaciones.ListaCatalogoNotificacionesResponse;
import com.sociomas.core.WebService.Model.Response.Registro.RegistroResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.ListaUnidadesNegocioResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.TipoHorarioEmpleadoRequest;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadEmpresaEmpleadoResponse;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.PlantillaResponse;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.RootUbicacion;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaListResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaPosicionResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaResponse;

import java.util.Calendar;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by oemy9 on 30/03/2017.
 */
@SuppressWarnings("unused")
public interface APIServiceInterface  {
    @POST(Constants.REGISTER_MOVEMENT_WS)
    Call<ResponseBody> registarMoviemientoEmpleado(@Body RootMovimiento movimiento);
    @POST(Constants.RegistrarEventoTelefono)
    Call<ResponseBody>registrarEventoTelefono(@Body Evento evento);
    @POST("ObtenerUbicaciones")
    Call<RootUbicacion> getLastUbicacion(@Body LastLocation lastLocation);
    @POST(Constants.ObtenerConfiguracionesAplicacion)
    Call<ConfiguracionResponse>getConfiguracion(@Body RootConfiguracion configuracion);
    @POST(Constants.ClavesTelefonicas)
    Call<RootClave>getClavesTelefonicas(@Body ServerRequest serverRequest);
    @POST(Constants.InsertaTelefonoContacto)
    Call<ServerResponse>insertarTelefonoContacto(@Body RootTelefonoContacto telefonoContacto) throws Exception;
    @POST(Constants.ListadoEmpleadosConIncidencias)
    Call<ResponseIncidencia>ListadoIncidencias(@Body RootIncidencia plantillaRequest) throws Exception;
    @POST(Constants.ObtenerArchivosAdjuntos)
    Call<ResponseArchivo>getArchivosAdjuntos(@Body ServerRequest request);
    @POST(Constants.ConsultarExclusionesEmpleados)
    Call<ResponsePermisos>getPermisosEmpleado(@Body RootPermiso request) throws Exception;
    @POST(Constants.ConsultarExclusionesEmpleados_IOS)
    Call<ResponsePermisoIOS>getPermisosEmpleadoIOS(@Body RootPermiso request);
    @POST(Constants.RegistraActualizaEmpleadoExclusion)
    Call<ServerResponse>registrarActualizarExclusion(@Body Exclusiones exclusiones);
    @POST(Constants.HistorialJustificacion)
    Call<RootHistorial>getHistorialIncidencias(@Body IncidenciaHistorial historialRequest);
    @POST(Constants.CatalogoTiposJustificantes)
    Call<CatalogoResponse>getCatalogoJustificaciones(@Body ServerRequest serverRequest);
    @POST(Constants.AGREGAR_JUSTIFICACION)
    Call<ServerResponse>agregarJustificacion(@Body SolicitarJustificacion solicitarJustificacion);
    @POST(Constants.ObtenerddlPermiso)
    Call<CatalogoPermisoRes>getDllPermisos(@Body ServerRequest request);
    @POST(Constants.SolicitarExclusion)
    Call<ServerResponse>solicitarPermiso(@Body SolicitarPermiso solicitarPermiso);
    @POST(Constants.RechazarJustificante)
    Call<ServerResponse>rechazarJustificante(@Body RAprobarJustificante rechazarJustificante);
    @POST(Constants.ValidarJustificante)
    Call<ServerResponse>validarJustificante(@Body RAprobarJustificante aprobarJustificante);
    @POST(Constants.ListadoHorariosEmpleadoPlantilla)
    Call<ResponseHorario>getListadoHorario(@Body ServerRequest request);
    @POST(Constants.registrarempleadoprop+"Copia")
    Call<ServerResponse>registrarPropuestaHorario(@Body HorarioRequest request);
    @POST(Constants.ELiminar_horario)
    Call<ServerResponse>editarEliminarHorario(@Body EditarHorarioRequest request);
    @POST(Constants.ValidarRechazarHorarioEmpleado)
    Call<ServerResponse>validarRechazarHorario(@Body HorarioValidar validar);
    @POST(Constants.consultarposicionesporempleado)
    Call<ZonaResponse>getPosicionesEmpleado(@Body ServerRequest request);
    @POST(Constants.ObtenerCatalogoZonas)
    Call<ZonaListResponse>getCatalogoZonas(@Body ServerRequest request);
    @POST(Constants.ConsultarDetalleZona)
    Call<ZonaPosicionResponse>getZonaDetalle(@Body ZonaDetalleRequest request);
    @POST(Constants.AsignarZonaAUsuario+"Copia")
    Call<ServerResponse>asignarZonaUsuario(@Body AsignarZonaRequest request);
    @POST(Constants.ConsultarPosiciones)
    Call<ZonaPosicionResponse>consultarPosiciones(@Body ListLugaresTrabajoRequest request);
    @POST(Constants.ConsultarPosiciones)
    Observable<ZonaPosicionResponse>consultarPosicionesRx(@Body ListLugaresTrabajoRequest request);
    @POST(Constants.EliminarUbicacion)
    Call<ServerResponse>eliminarUbicacion(@Body EliminarTiendaRequest request);
    @POST(Constants.RegistrarPropuestaUbicacion+"Copia")
    Call<ServerResponse>registrarPropuestaUbicacion(@Body EliminarTiendaRequest request);
    @POST(Constants.EliminarZonaAUsuario)
    Call<ServerResponse>eliminarZonaUsuario(@Body EliminarZonaRequest request);
    @POST(Constants.ListadoEmpleadosUbicaciones)
    Call<PlantillaResponse>listadoEmpleadoUbicaciones(@Body ServerRequest request);
    @POST(Constants.AceptarRechazarPropuestaUbicacion)
    Call<ServerResponse>aceptarRechazarUbicacion(@Body AceptarUbicacionRequest request);
    @POST(Constants.AceptarRechazarZonas)
    Call<ServerResponse>aceptarRechazarZona(@Body AceptarZonaRequest request);
    @POST(Constants.InicioRegistroEmpleado)
    Call<RegistroResponse>inicioRegistroEmpleado(@Body RegistroRequest request);
    @POST(Constants.CHECK_EMPLOYEE_WS)
    Call<RegistroResponse>checkEmpleado(@Body CheckEmpleadoRequest request);
    @POST(Constants.VERIFICAR_CODIGO)
    Call<ResponseBody>verificarCodigo(@Body ValidarCodigoRequest request);
    @POST(Constants.REENVIAR_CODIGO)
    Call<ResponseBody>reenviarCodigo(@Body CrearCodigoRequest request);
    @POST(Constants.GET_SETTINGS_WS)
    Call<EmpleadoSettingResponse>getSettingsEmpleado(@Body RegistroRequest request);
    @POST(Constants.SOLICITAR_CODIGO)
    Call<ServerResponse>solicitarCodigo(@Body SolicitarCodigoRequest request);
    @POST(Constants.CODIGO_LLAMADA)
    Call<ResponseBody>codigoPorLlamada(@Body CrearCodigoRequest request);
    @POST(Constants.RECIBIR_ARCHIVO)
    Call<ServerResponse>enviarArchivo(@Body GafeteCrearRequest request);
    @POST(Constants.OBTENER_CREDENCIAL)
    Call<ResponseGafete>obtenerGafete(@Body GafeteObtenerRequest request);
    @POST(Constants.OBTENER_CREDENCIAL)
    Observable<ResponseGafete>obtenerGafeteRx(@Body GafeteObtenerRequest request);
    @POST(Constants.OBTENER_MI_UNIDADNEGOCIO)
    Call<UnidadEmpresaEmpleadoResponse> consultaUnidadNegocio(@Body ServerRequest request);
    @POST(Constants.OBTENER_UNIDADES_NEGOCIO)
    Call<ListaUnidadesNegocioResponse> obtenerUnidadesNegocio(@Body ServerRequest request);
    @POST(Constants.GUARDAR_MI_UNIDADNEGOCIO)
    Call<ServerResponse> actualizaEmpresaEmpleado(@Body ModificaUnidadNegocioRequest request);
    @POST(Constants.DEVUELVE_AVISO)
    Call<PrivacidadResponse>devuelveAviso(@Body ServerRequest request);
    @POST(Constants.INSERTAR_POSICION_AVISO)
    Call<ServerResponse>insertarPosicionesAviso(@Body AvisoRequest request);
    @POST(Constants.MODIFICAR_TIPO_HORARIO)
    Call<ServerResponse> modificarTipoHorarioEmpleado(@Body  TipoHorarioEmpleadoRequest request);
    @POST(Constants.MODIFICA_LUGAR_TRABAJO_TIPO)
    Call<ServerResponse> modificaPrefLugarTrabajo(@Body ModificarPrefLugarTrabajoRequest request);
    @POST(Constants.REGISTRAR_ASISTENCIA_MANUAL)
    Call<ServerResponse>ingresarAsistenciaManual(@Body AsistenciaManualRequest request);
    @POST(Constants.ACTUALIZA_HORARIO_EMPLEADO)
    Call<ServerResponse> actualizarHorarioEmpleado(@Body ListaEditarHorarioRequest request);
    @POST(Constants.OBTENER_IMAGENES_GAFETE)
    Call<ResponseGafeteImagen>obtenerImagenesGafete(@Body GafeteImagenRequest request);
    @POST("ObtenerListaEmpleados")
    Call<ListaEmpleadoReponse>obtenerListaEmpleados(@Body ListaEmpleadoRequest request);
    @POST("ActualizarSupervisor")
    Call<ServerResponse>actualizaSupervisor(@Body ActualizarSupervisorRequest request);
    @POST(Constants.CONSULTAR_NOTIFICACIONES)
    Call<ListaCatalogoNotificacionesResponse> consultarNotificaciones(@Body ConsultaNotificacionesRequest request);
    @POST(Constants.LISTAR_NOTIFICACIONES)
    Call<ListaNotificacionesResponse> consultarNotificacionesGeneral(@Body ServerRequest request);
    @POST(Constants.MODIFICAR_ESTATUS_NOTIFICACION)
    Call<ServerResponse> modificarEstatusNotificacion(@Body ModificarNotificacionRequest request);
    @POST ("ModificarTelefonoEmpleado")
    Call<TelefonoResponse> ModificarTelefonoEmpleado (@Body ModificarTelefonoRequest request);
    @POST(Constants.CONSULTA_LEGALES)
    Call<ConsultaLegalesResponse> consultaLegales(@Body ServerRequest serverRequest);
    @POST("ConsultarTelefonoEmpleado")
    Call<TelefonoResponse>consultarTelefonoEmpleado(@Body ServerRequest request);
}
