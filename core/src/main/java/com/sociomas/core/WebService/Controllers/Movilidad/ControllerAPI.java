package com.sociomas.core.WebService.Controllers.Movilidad;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import com.sociomas.core.R;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.UI.Controls.Notification.CustomProgressBar;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DeviceUtils;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.Utils.Enums.EnumIncidencia;
import com.sociomas.core.Utils.Enums.EnumTipo;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Security.DecryptUtils;
import com.sociomas.core.Utils.Security.SecureDate;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacks.CallBackAprobarRechazar;
import com.sociomas.core.WebService.CallBacks.CallBackCatalogo;
import com.sociomas.core.WebService.CallBacks.CallBackCatalogoPermisos;
import com.sociomas.core.WebService.CallBacks.CallBackIncidencia;
import com.sociomas.core.WebService.CallBacks.CallBackListadoPlantilla;
import com.sociomas.core.WebService.CallBacks.CallBackPermiso;
import com.sociomas.core.WebService.CallBacksAventones.CallBackPosiciones;
import com.sociomas.core.WebService.Controllers.ControllerBase;
import com.sociomas.core.WebService.Controllers.MockInterceptor;
import com.sociomas.core.WebService.Model.Request.Asistencia.AsistenciaManualRequest;
import com.sociomas.core.WebService.Model.Request.Codigo.CrearCodigoRequest;
import com.sociomas.core.WebService.Model.Request.Codigo.SolicitarCodigoRequest;
import com.sociomas.core.WebService.Model.Request.Codigo.ValidarCodigoRequest;
import com.sociomas.core.WebService.Model.Request.Contacto.Evento;
import com.sociomas.core.WebService.Model.Request.Contacto.RootConfiguracion;
import com.sociomas.core.WebService.Model.Request.Contacto.RootTelefonoContacto;
import com.sociomas.core.WebService.Model.Request.Contacto.TelefonoContacto;
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
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.RootHistorial;
import com.sociomas.core.WebService.Model.Response.ListaEmpleado.ListaEmpleadoReponse;
import com.sociomas.core.WebService.Model.Response.Multimedia.ResponseArchivo;
import com.sociomas.core.WebService.Model.Response.Notificaciones.ListaNotificacionesResponse;
import com.sociomas.core.WebService.Model.Response.NumeroTelefono.TelefonoResponse;
import com.sociomas.core.WebService.Model.Response.Permisos.CatalogoPermisoRes;
import com.sociomas.core.WebService.Model.Response.Permisos.Exclusiones;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;
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
import com.sociomas.core.WebService.Model.Response.Ubicaciones.EmpleadosPlantilla;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.PlantillaResponse;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.RootUbicacion;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaListResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaPosicionResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaResponse;
import com.sociomas.core.WebService.Services.APIServiceInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
/**
 * Created by oemy9 on 30/03/2017.
 */
public class ControllerAPI extends ControllerBase implements APIServiceInterface {
    public static final String TAG = "CONTROLLER";
    private APIServiceInterface serviceInterface;
    private CallBackIncidencia callBackIncidenciaResponse;
    private CallBackPermiso callBackPermiso;
    private CallBackCatalogo callBackCatalogo;
    private CallBackCatalogoPermisos callBackCatalogoPermisos;
    private CallBackAprobarRechazar callBackAprobarRechazar;
    private CallBackListadoPlantilla callBackListadoPlantilla;
    //region CALLBACKS
    public void setCallBackListadoPlantilla(CallBackListadoPlantilla callBackListadoPlantilla) {
        this.callBackListadoPlantilla = callBackListadoPlantilla;
    }
    public void setCallBackAprobarRechazar(CallBackAprobarRechazar callBackAprobarRechazar) {
        this.callBackAprobarRechazar = callBackAprobarRechazar;
    }
    public void setCallBackCatalogoPermisos(CallBackCatalogoPermisos callBackCatalogoPermisos) {
        this.callBackCatalogoPermisos = callBackCatalogoPermisos;
    }

    public void setCallBackCatalogo(CallBackCatalogo callBackCatalogo) {
        this.callBackCatalogo = callBackCatalogo;
    }
    public void setCallBackPermiso(CallBackPermiso callBackPermiso) {
        this.callBackPermiso = callBackPermiso;
    }
    public void setCallBackIncidenciaResponse(CallBackIncidencia callBackIncidenciaResponse) {
        this.callBackIncidenciaResponse = callBackIncidenciaResponse;
    }
    //endregion
    public ControllerAPI(Context context) {
        super(context, Constants.DOMAIN_URL , Utils.hostnameVerifier());
        this.serviceInterface = getRetrofitClient().create(APIServiceInterface.class);
    }

    public ControllerAPI(Context context, String urlBase) {
        super(context, urlBase);
        this.serviceInterface = getRetrofitClient().create(APIServiceInterface.class);
    }

    @Override
    public Retrofit generateBaseUrl(String url) {
        super.generateBaseUrl(url);
        this.serviceInterface = retrofit.create(APIServiceInterface.class);
        return retrofit;
    }

    public void consultarPosicionesAutoCompleted(ListLugaresTrabajoRequest request, final CallBackPosiciones callBackPosiciones) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        serviceInterface.consultarPosiciones(request).enqueue(new Callback<ZonaPosicionResponse>() {
            @Override
            public void onResponse(Call<ZonaPosicionResponse> call, Response<ZonaPosicionResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        callBackPosiciones.OnSuccess(response.body().getPosiciones());
                    } else {
                        callBackPosiciones.OnError(new Throwable(response.body().getMensajeError()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ZonaPosicionResponse> call, Throwable t) {
                callBackPosiciones.OnError(t);
            }
        });
    }
    //region INICIO DE SESIÓN
    @Override
    public Call<RegistroResponse> inicioRegistroEmpleado(@Body RegistroRequest request) {
        SecurityItems securityItems = new SecurityItems(request.getIdNumEmpleado(), request.getRespuesta());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setRespuestaPass(securityItems.getPasswordEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        return this.serviceInterface.inicioRegistroEmpleado(request);
    }

    @Override
    public Call<RegistroResponse> checkEmpleado(@Body CheckEmpleadoRequest request) {
        SecurityItems securityItems = new SecurityItems(request.getIdNumEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setIdEmpresa("EKT");
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        return this.serviceInterface.checkEmpleado(request);
    }
    @Override
    public Call<EmpleadoSettingResponse> getSettingsEmpleado(@Body RegistroRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        SessionManager manager = new SessionManager(this.context);
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        request.setNumeroTel(manager.getString(Constants.MY_PHONE));
        return this.serviceInterface.getSettingsEmpleado(request);
    }

    @Override
    public Call<ConfiguracionResponse> getConfiguracion(@Body RootConfiguracion configuracion) {
        SessionManager manager = new SessionManager(this.context);
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        configuracion.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        configuracion.setToken(securityItems.getTokenEncrypt());
        configuracion.setModeloCelular(DeviceUtils.getName());
        configuracion.setIdDispositivo(DeviceUtils.getIdDispositivo(context));
        configuracion.setVersionSistemaOperativo(DeviceUtils.getVersionSO());
        configuracion.setSistema(DeviceUtils.getSistema());
        configuracion.setVersionActual(DeviceUtils.getVersionApp(context));
        configuracion.setVa_numero_telefono(manager.getString(Constants.MY_PHONE));
        return serviceInterface.getConfiguracion(configuracion);
    }

    @Override
    public Call<RootClave> getClavesTelefonicas(@Body ServerRequest serverRequest) {
        SecurityItems securityItems = new SecurityItems(this.getNumeroEmpleado());
        serverRequest.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        serverRequest.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.getClavesTelefonicas(serverRequest);
    }


    //endregion
    //region CODIGO DE VERIFICACIÓN
    @Override
    public Call<ResponseBody> verificarCodigo(@Body ValidarCodigoRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        return this.serviceInterface.verificarCodigo(request);
    }

    @Override
    public Call<ResponseBody> reenviarCodigo(@Body CrearCodigoRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        return this.serviceInterface.reenviarCodigo(request);
    }

    @Override
    public Call<ResponseBody> codigoPorLlamada(@Body CrearCodigoRequest request) {
        DecryptUtils utils = new DecryptUtils();
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        return this.serviceInterface.codigoPorLlamada(request);
    }

    @Override
    public Call<ServerResponse> solicitarCodigo(@Body SolicitarCodigoRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        return this.serviceInterface.solicitarCodigo(request);
    }

    //endregion
    //region SERVICIOS EN SEGUNDO PLANO
    @Override
    public Call<ResponseBody> registarMoviemientoEmpleado(@Body RootMovimiento movimiento) {
        movimiento.setId_dispositivo(Settings.Secure.getString(this.context.getContentResolver(), Settings.Secure.ANDROID_ID));
        return this.serviceInterface.registarMoviemientoEmpleado(movimiento);
    }

    @Override
    public Call<ResponseBody> registrarEventoTelefono(@Body Evento evento) {

        SecureDate secureDate = new SecureDate(this.context);
        SessionManager manager = new SessionManager(this.context);
        Date date = manager.get(Constants.IS_ERROR_FECHA) ? secureDate.getDateCalculada() : new Date();
        SecurityItems securityItems = new SecurityItems(evento.getIdNumEmpleado(), date);

        evento.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        evento.setToken(securityItems.getTokenEncrypt());


        return this.serviceInterface.registrarEventoTelefono(evento);
    }

    //endregion
    //region ÚLTIMA UBICACIÓN & RUTA EMPLEADO
    @Override
    public Call<RootUbicacion> getLastUbicacion(@Body LastLocation lastLocation) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DAY_FORMAT);
        SecurityItems securityItems = new SecurityItems(this.getNumeroEmpleado());
        lastLocation.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        lastLocation.setToken(securityItems.getTokenEncrypt());
        lastLocation.setFechaFin(dateFormat.format(new Date()));
        lastLocation.setFechaInicio(dateFormat.format(new Date()));
        lastLocation.setHorarioLaboral(false);
        lastLocation.setUltimaUbicacion(true);
        lastLocation.setBuscaTodos(true);
        lastLocation.setHoraFin("19:00");
        lastLocation.setHoraInicio("09:00");
        return this.serviceInterface.getLastUbicacion(lastLocation);
    }

    /*TODO UNIFICAR AMBAS PETICIONES EN UNA SOLA PARA NO CONFUNDIME*/
    public Call<RootUbicacion> getTrackingUbicacion(@Body LastLocation lastLocation) {
        SecurityItems securityItems = new SecurityItems(this.getNumeroEmpleado());
        lastLocation.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        lastLocation.setToken(securityItems.getTokenEncrypt());
        lastLocation.setUltimaUbicacion(false);
        lastLocation.setBuscaTodos(false);
        lastLocation.setHoraFin("23:59");
        lastLocation.setHoraInicio("00:00");
        return serviceInterface.getLastUbicacion(lastLocation);
    }

    //endregion
    //region JUSTIFICACIONES
    public void ListadoIncidenciasAsync(RootIncidencia plantillaRequest, final EnumTipo tipo) {
        final HashMap<String, EmpleadoIncidencia> empleadoIncidencias = new HashMap<String, EmpleadoIncidencia>();
        final HashMap<String, String> nombresDecrypts = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_YEAR_FORMAT);
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        plantillaRequest.setFechaFin(dateFormat.format(new Date()));
        plantillaRequest.setFechaInicio("2017-01-01");
        plantillaRequest.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        plantillaRequest.setToken(securityItems.getTokenEncrypt());
        try {
            serviceInterface.ListadoIncidencias(plantillaRequest).enqueue(new Callback<ResponseIncidencia>() {
                @Override
                public void onResponse(Call<ResponseIncidencia> call, Response<ResponseIncidencia> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getError()) {
                            //Utils.checkIfFechaError(response.body().getServerUTCTime(),response.body().getServerTime());
                            callBackIncidenciaResponse.OnError(new Throwable(response.body().getMensajeError()));
                            return;
                        }

                        if (callBackIncidenciaResponse != null) {


                            //¿QUÉ TIPO DE LISTA SE VA A OBTENER PLANTILLA O MÍAS?
                            ArrayList<ListadoIncidencias> items = tipo == EnumTipo.plantilla ?
                                    response.body().getListadoPlantilla() :
                                    response.body().getListadoIncidencias();

                            //LISTA QUE ALMACENA LOS AUTORIZADOS PARA POSTERIORMENTE ELIMINARLOS
                            ArrayList<ListadoIncidencias> itemsToRemove = new ArrayList<ListadoIncidencias>();
                            EnumIncidencia incidenciaTipo = EnumIncidencia.sin_justificar;
                            //LA LISTA NO ES NULL
                            if (items != null) {

                                //FOREACH ITEM EN EL LISTADO
                                for (ListadoIncidencias item : items) {
                                    if (!nombresDecrypts.containsKey(item.getNombre())) {
                                        item.setEmpleado(DecryptUtils.decryptAES(item.getEmpleado()));
                                        nombresDecrypts.put(item.getNombre(), item.getEmpleado());
                                    } else {
                                        item.setEmpleado(nombresDecrypts.get(item.getNombre()));
                                    }
                                    incidenciaTipo = incidenciaTipo.getFromSting(item.getEstatusJustificacion());

                                    if (incidenciaTipo == EnumIncidencia.autorizado && tipo == EnumTipo.plantilla) {
                                        itemsToRemove.add(item);
                                    }

                                    if (!empleadoIncidencias.containsKey(item.getEmpleado())) {
                                        empleadoIncidencias.put(item.getEmpleado(), new EmpleadoIncidencia(
                                                item.getEmpleado() + " -" + item.getNombre(), item.getEstatusJustificacion()
                                        ));
                                    } else {
                                        empleadoIncidencias.get(item.getEmpleado()).
                                                setByStatus(item.getEstatusJustificacion());
                                    }
                                }
                                items.removeAll(itemsToRemove);
                            }


                            callBackIncidenciaResponse.OnSuccess(response.body(), empleadoIncidencias);
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseIncidencia> call, Throwable t) {
                    //     callBackIncidenciaResponse.OnError( context.getString(R.string.Error_Conexion));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCatalogoJustificacionesAsync(ServerRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        if (request != null) {
            serviceInterface.getCatalogoJustificaciones(request).enqueue(new Callback<CatalogoResponse>() {
                @Override
                public void onResponse(Call<CatalogoResponse> call, Response<CatalogoResponse> response) {
                    if (response.isSuccessful()) {
                        callBackCatalogo.OnSuccess(response.body());
                    }

                }

                @Override
                public void onFailure(Call<CatalogoResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public Call<ServerResponse> agregarJustificacion(@Body SolicitarJustificacion solicitarJustificacion) {
        //NÚMERO DE EMPLEADO QUE JUSTIFICA (PROPIETAIO DE LA APP)
        SecurityItems securityItemsJefe = new SecurityItems(getNumeroEmpleado());

        String numeroEmpleadoJustifica = solicitarJustificacion.getIdNumEmpleadoJustifica() == null ?
                getNumeroEmpleado() : solicitarJustificacion.getIdNumEmpleadoJustifica();

        //NÚMERO DE EMPLEADO QUE SE JUSTIFIACARÁ LA INCIDENCIA
        SecurityItems securityItemsEmpleadoJustificar = new SecurityItems(numeroEmpleadoJustifica);

        solicitarJustificacion.setIdNumEmpleado(securityItemsJefe.getIdEmployEncrypt());
        solicitarJustificacion.setIdNumEmpleadoJustifica(securityItemsEmpleadoJustificar.getIdEmployEncrypt());
        solicitarJustificacion.setToken(securityItemsJefe.getTokenEncrypt());

        return serviceInterface.agregarJustificacion(solicitarJustificacion);

    }

    @Override
    public Call<RootHistorial> getHistorialIncidencias(@Body IncidenciaHistorial historialRequest) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        historialRequest.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        historialRequest.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.getHistorialIncidencias(historialRequest);
    }

    @Override
    public Call<ResponseArchivo> getArchivosAdjuntos(@Body ServerRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.getArchivosAdjuntos(request);
    }

    //endregion
    //region PERMISOS
    @Override
    public Call<ResponsePermisoIOS> getPermisosEmpleadoIOS(@Body RootPermiso permiso) {
        return serviceInterface.getPermisosEmpleadoIOS(permiso);
    }

    public void ListadoPermisosIOSAsync(RootPermiso permiso, final EnumConsulta enumConsulta) {
        final String A_LAS = "a las";
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        permiso.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        permiso.setToken(securityItems.getTokenEncrypt());
        permiso.setTipo(enumConsulta.toString());
        permiso.setStatus(4);
        permiso.setBusqueda("");
        final ArrayList<ListExclusiones> listExclusiones = new ArrayList<>();
        this.serviceInterface.getPermisosEmpleadoIOS(permiso).enqueue(new Callback<ResponsePermisoIOS>() {
            @Override
            public void onResponse(Call<ResponsePermisoIOS> call, Response<ResponsePermisoIOS> response) {
                if (response.isSuccessful()) {

                    ArrayList<ListExclusiones> selectedList = enumConsulta == EnumConsulta.LineaDirecta ?
                            response.body().getPlantillaExclusiones() : response.body().getMisExclusiones();

                    if (selectedList != null && (!selectedList.isEmpty())) {


                        for (ListExclusiones item : selectedList) {
                                /*OBTENER LA FECHA SIN LA HORA*/
                            String fechaSinFormato = item.getFechaHoraInicial();
                            String fechaInicial = fechaSinFormato.replace(fechaSinFormato.substring(fechaSinFormato.indexOf(A_LAS)), "");
                                /*ELIMINAMOS ESPACIOS EN BLANCO*/
                            fechaInicial = fechaInicial.replaceAll("\\s", "");
                            //FORMATEO DE LA FECHA Y HORA INICIAL
                            String fechaHoraFinal = item.getFechaHoraFinal().replace(A_LAS, "");
                            String fechaHoraInicial = item.getFechaHoraInicial().replace(A_LAS, "");
                            //ENVIAMOS LA INFORMACIÓN LIMPIA AL ITEM
                            item.setFecha(fechaInicial);
                            item.setFechaHoraInicial(fechaHoraInicial);
                            item.setFechaHoraFinal(fechaHoraFinal);
                                /*DECRYPT NUMERO EMPLEADO*/
                            item.setIdNumEmpleado(DecryptUtils.decryptAES(item.getIdNumEmpleado()));

                            listExclusiones.add(item);

                        }


                    }

                    if (!response.body().getError()) {
                        callBackPermiso.OnSuccessIOS(listExclusiones);
                    } else
                        callBackPermiso.OnError(new Throwable(response.body().getMensajeError() != null ? response.body().getMensajeError() : context.getString(R.string.Error_Conexion)));
                }
            }

            @Override
            public void onFailure(Call<ResponsePermisoIOS> call, Throwable t) {

            }
        });
    }

    public void getCatalogoPermisosAsync(ServerRequest serverRequest) {
        SecurityItems securityItems = new SecurityItems(this.getNumeroEmpleado());
        serverRequest.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        serverRequest.setToken(securityItems.getTokenEncrypt());
        if (serverRequest != null) {
            this.serviceInterface.getDllPermisos(serverRequest).enqueue(new Callback<CatalogoPermisoRes>() {
                @Override
                public void onResponse(Call<CatalogoPermisoRes> call, Response<CatalogoPermisoRes> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().getError()) {
                            if (callBackCatalogoPermisos != null) {
                                callBackCatalogoPermisos.OnSuccess(response.body());
                            }
                        } else {
                            if (callBackCatalogoPermisos != null) {
                                callBackCatalogoPermisos.OnError(
                                        new Throwable(response.body().getMensajeError()));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CatalogoPermisoRes> call, Throwable t) {
                    callBackCatalogoPermisos.OnError(new Throwable(context.getString(R.string.Error_Conexion)));
                }
            });

        }
    }

    @Override
    public Call<CatalogoPermisoRes> getDllPermisos(@Body ServerRequest request) {
        return null;
    }

    @Override
    public Call<ServerResponse> solicitarPermiso(@Body SolicitarPermiso solicitarPermiso) {
        String numeroEmpleado = solicitarPermiso.getIdNumEmpleado() != null ? solicitarPermiso.getIdNumEmpleado() : Utils.getNumeroEmpleado(context);
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        solicitarPermiso.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        solicitarPermiso.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.solicitarPermiso(solicitarPermiso);
    }

    //endregion
    //region GAFETE
    @Override
    public Call<ServerResponse> enviarArchivo(@Body GafeteCrearRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.enviarArchivo(request);
    }

    @Override
    public Call<ResponseGafete> obtenerGafete(@Body GafeteObtenerRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.obtenerGafete(request);
    }

    @Override
    public Observable<ResponseGafete> obtenerGafeteRx(@Body GafeteObtenerRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.obtenerGafeteRx(request)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //end region
    @Override
    public Call<ServerResponse> registrarActualizarExclusion(@Body Exclusiones exclusiones) {
        return serviceInterface.registrarActualizarExclusion(exclusiones);
    }

    public void insertarTelefonoContactoAsync(ArrayList<TelefonoContacto> listContactos) {
        final Activity activity = (Activity) this.context;
        final CustomProgressBar customProgressBar = new CustomProgressBar(this.context);
        customProgressBar.show(activity);
        RootTelefonoContacto telefonoContacto = new RootTelefonoContacto();
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        telefonoContacto.setContactos(listContactos);
        telefonoContacto.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        telefonoContacto.setToken(securityItems.getTokenEncrypt());
        try {
            serviceInterface.insertarTelefonoContacto(telefonoContacto).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getError()) {
                            Utils.checkIfFechaError(context, response.body().getServerUTCTime(),
                                    response.body().getServerTime());
                        } else {
                            if (response.body().getServerTime() != null)
                                Utils.saveServerTime(context, response.body().getServerTime());
                        }
                    }
                    customProgressBar.dismiss();
                    activity.finish();
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Call<CatalogoResponse> getCatalogoJustificaciones(@Body ServerRequest serverRequest) {
        return null;
    }

    {
    }

    @Override
    public Call<ServerResponse> insertarTelefonoContacto(@Body RootTelefonoContacto telefonoContacto) throws Exception {
        throw new Exception("No implementado aún");
    }


    @Override
    public Call<ResponseIncidencia> ListadoIncidencias(@Body RootIncidencia plantillaRequest) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_YEAR_FORMAT);
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        plantillaRequest.setFechaFin(dateFormat.format(new Date()));
        plantillaRequest.setFechaInicio("2017-01-01");
        plantillaRequest.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        plantillaRequest.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.ListadoIncidencias(plantillaRequest);

    }

    @Override
    public Call<ResponsePermisos> getPermisosEmpleado(@Body RootPermiso request) throws Exception {
        throw new Exception("No implementado aún");
    }

    @Override
    public Call<ServerResponse> rechazarJustificante(@Body RAprobarJustificante rechazarAprobar) {
        return serviceInterface.rechazarJustificante(rechazarAprobar);
    }

    @Override
    public Call<ServerResponse> validarJustificante(@Body RAprobarJustificante aprobarJustificante) {
        return serviceInterface.validarJustificante(aprobarJustificante);
    }


    @Override
    public Call<ResponseHorario> getListadoHorario(@Body ServerRequest request) {
        SecurityItems securityItems = new SecurityItems(this.getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.getListadoHorario(request);
    }

    @Override
    public Call<ServerResponse> registrarPropuestaHorario(@Body HorarioRequest request) {
        return serviceInterface.registrarPropuestaHorario(request);
    }

    @Override
    public Call<ServerResponse> validarRechazarHorario(@Body HorarioValidar validar) {
        return serviceInterface.validarRechazarHorario(validar);
    }

    @Override
    public Call<ZonaResponse> getPosicionesEmpleado(@Body ServerRequest request) {
        String numeroEmpleado = request.getIdNumEmpleado() != null && (!request.getIdNumEmpleado().isEmpty()) ? request.getIdNumEmpleado() : getNumeroEmpleado();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.getPosicionesEmpleado(request);
    }


    @Override
    public Call<ZonaListResponse> getCatalogoZonas(@Body ServerRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.getCatalogoZonas(request);
    }

    @Override
    public Call<ZonaPosicionResponse> getZonaDetalle(@Body ZonaDetalleRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.getZonaDetalle(request);
    }

    @Override
    public Call<ServerResponse> asignarZonaUsuario(@Body AsignarZonaRequest request) {
        String numeroEmpleado = request.getIdNumEmpleado() != null && (!request.getIdNumEmpleado().isEmpty()) ? request.getIdNumEmpleado() : getNumeroEmpleado();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.asignarZonaUsuario(request);
    }

    @Override
    public Call<ZonaPosicionResponse> consultarPosiciones(@Body ListLugaresTrabajoRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.consultarPosiciones(request);
    }

    @Override
    public Observable<ZonaPosicionResponse> consultarPosicionesRx(ListLugaresTrabajoRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.consultarPosicionesRx(request).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Call<ServerResponse> eliminarUbicacion(@Body EliminarTiendaRequest request) {
        String numeroEmpleado = request.getIdNumEmpleado() != null && (!request.getIdNumEmpleado().isEmpty()) ? request.getIdNumEmpleado() : getNumeroEmpleado();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.eliminarUbicacion(request);
    }

    @Override
    public Call<ServerResponse> registrarPropuestaUbicacion(@Body EliminarTiendaRequest request) {
        String numeroEmpleado = request.getIdNumEmpleado() != null && (!request.getIdNumEmpleado().isEmpty()) ? request.getIdNumEmpleado() : getNumeroEmpleado();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.registrarPropuestaUbicacion(request);
    }

    @Override
    public Call<ServerResponse> eliminarZonaUsuario(@Body EliminarZonaRequest request) {
        String numeroEmpleado = request.getIdNumEmpleado() != null && (!request.getIdNumEmpleado().isEmpty()) ? request.getIdNumEmpleado() : getNumeroEmpleado();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.eliminarZonaUsuario(request);
    }

    @Override
    public Call<ServerResponse> aceptarRechazarUbicacion(@Body AceptarUbicacionRequest request) {
        String numeroEmpleado = request.getIdNumEmpleado() != null && (!request.getIdNumEmpleado().isEmpty()) ? request.getIdNumEmpleado() : getNumeroEmpleado();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.aceptarRechazarUbicacion(request);
    }

    @Override
    public Call<ServerResponse> aceptarRechazarZona(@Body AceptarZonaRequest request) {
        String numeroEmpleado = request.getIdNumEmpleado() != null && (!request.getIdNumEmpleado().isEmpty()) ? request.getIdNumEmpleado() : getNumeroEmpleado();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.aceptarRechazarZona(request);
    }

    @Override
    public Call<PlantillaResponse> listadoEmpleadoUbicaciones(@Body ServerRequest request) {
        SecurityItems securityItems = new SecurityItems(getNumeroEmpleado());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.listadoEmpleadoUbicaciones(request);
    }

    public void getListadoEmpleadosPlantilla() {
        final ArrayList<SearchBoxItem> listRespuesta = new ArrayList<>();
        this.listadoEmpleadoUbicaciones(new ServerRequest()).enqueue(new Callback<PlantillaResponse>() {
            @Override
            public void onResponse(Call<PlantillaResponse> call, Response<PlantillaResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        if (response.body().getEmpleadosPlantilla() != null) {
                            for (EmpleadosPlantilla item : response.body().getEmpleadosPlantilla()) {
                                listRespuesta.add(new SearchBoxItem(DecryptUtils.decryptAES(item.getIdNumEmpleado()), item.getNombreEmpleado()));
                            }
                            callBackListadoPlantilla.OnSuccess(listRespuesta);
                        }
                    } else {
                        callBackListadoPlantilla.OnError(new Throwable(response.body().getMensajeError()));
                    }
                }
            }

            @Override
            public void onFailure(Call<PlantillaResponse> call, Throwable t) {
                //  callBackListadoPlantilla.OnError(context.getString(R.string.Error_Conexion));
            }
        });
    }

    @Override
    public Call<ServerResponse> editarEliminarHorario(@Body EditarHorarioRequest request) {
        SecurityItems securityItems = new SecurityItems(request.getIdNumEmpleado());
        SecurityItems securityItemsLogeado = new SecurityItems(this.getNumeroEmpleado());
        request.setIdNumEmpleadoLogeado(securityItemsLogeado.getIdEmployEncrypt());
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItemsLogeado.getTokenEncrypt());
        return serviceInterface.editarEliminarHorario(request);
    }

    public void rechazarValidarAsync(RAprobarJustificante rechazarAprobar, boolean validar) {
        //NÚMERO DE EMPLEADO QUE JUSTIFICA (PROPIETAIO DE LA APP)
        SecurityItems securityItemsJefe = new SecurityItems(getNumeroEmpleado());
        //NÚMERO DE EMPLEADO QUE SE JUSTIFIACARÁ LA INCIDENCIA
        SecurityItems securityItemsEmpleadoJustificar = new SecurityItems(rechazarAprobar.getEmpleado_valida());
        rechazarAprobar.setIdNumEmpleado(securityItemsEmpleadoJustificar.getIdEmployEncrypt());
        rechazarAprobar.setEmpleado_valida(securityItemsJefe.getIdEmployEncrypt());
        rechazarAprobar.setToken(securityItemsJefe.getTokenEncrypt());
        rechazarAprobar.setTipo("def");

        Call<ServerResponse> endPoint = validar ? serviceInterface.validarJustificante(rechazarAprobar) :
                serviceInterface.rechazarJustificante(rechazarAprobar);

        endPoint.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        if (callBackAprobarRechazar != null) {
                            callBackAprobarRechazar.OnSuccess(response.body());
                        }
                    } else {
                        callBackAprobarRechazar.OnError(new Throwable(response.body().getMensajeError()));
                    }
                } else {
                    callBackAprobarRechazar.OnError(new Throwable(context.getString(R.string.Error_Conexion)));
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                callBackAprobarRechazar.OnError(new Throwable(context.getString(R.string.Error_Conexion)));
            }
        });
    }

    /**
     * Obtener las unidades de negocio que existen en Grupo Salinas
     * @param request
     * @return
     */
    @Override
    public Call<ListaUnidadesNegocioResponse> obtenerUnidadesNegocio(@Body ServerRequest request) {
        return serviceInterface.obtenerUnidadesNegocio(request);

    }
    /*
    * Devuelve politicas y  aviso de privacidad
    * */
    @Override
    public Call<PrivacidadResponse> devuelveAviso(ServerRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.devuelveAviso(request);
    }

    @Override
    public Call<ServerResponse> insertarPosicionesAviso(AvisoRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.insertarPosicionesAviso(request);
    }

    /**
     * Obtener la unidad de negocio a la que pertenezco
     * @param request
     * @return
     */
    @Override
    public Call<UnidadEmpresaEmpleadoResponse> consultaUnidadNegocio(@Body ServerRequest request) {
        return serviceInterface.consultaUnidadNegocio(request);
    }

    @Override
    public Call<ServerResponse> actualizaEmpresaEmpleado(@Body ModificaUnidadNegocioRequest request) {
        return serviceInterface.actualizaEmpresaEmpleado(request);
    }


    public Call<ServerResponse> modificarTipoHorarioEmpleado(@Body  TipoHorarioEmpleadoRequest request) {
        return serviceInterface.modificarTipoHorarioEmpleado(request);
    }

    public Call<ServerResponse> modificaPrefLugarTrabajo(@Body ModificarPrefLugarTrabajoRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.modificaPrefLugarTrabajo(request);
    }

    @Override
    public Call<ServerResponse> ingresarAsistenciaManual(AsistenciaManualRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.ingresarAsistenciaManual(request);
    }

    public Call<ServerResponse> actualizarHorarioEmpleado(@Body ListaEditarHorarioRequest request) {
        return serviceInterface.actualizarHorarioEmpleado(request);
    }

    @Override
    public Call<ResponseGafeteImagen> obtenerImagenesGafete(GafeteImagenRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.obtenerImagenesGafete(request);
    }

    @Override
    public Call<ListaEmpleadoReponse> obtenerListaEmpleados(ListaEmpleadoRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.obtenerListaEmpleados(request);
    }

    @Override
    public Call<ServerResponse> actualizaSupervisor(ActualizarSupervisorRequest request) {
        SecurityItems securityItems = getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.actualizaSupervisor(request);
    }
    @Override
    public Call<ListaCatalogoNotificacionesResponse> consultarNotificaciones(ConsultaNotificacionesRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        MockInterceptor.count = 3;
        return serviceInterface.consultarNotificaciones(request);
    }

    @Override
    public Call<ListaNotificacionesResponse> consultarNotificacionesGeneral(ServerRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        MockInterceptor.count = 4;
        return serviceInterface.consultarNotificacionesGeneral(request);
    }

    @Override
    public Call<ServerResponse> modificarEstatusNotificacion(ModificarNotificacionRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.modificarEstatusNotificacion(request);
    }

    @Override
    public Call<TelefonoResponse> ModificarTelefonoEmpleado(@Body ModificarTelefonoRequest request) {
        SecurityItems securityItems = getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.ModificarTelefonoEmpleado(request);
    }

    @Override
    public Call<ConsultaLegalesResponse> consultaLegales(@Body ServerRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        MockInterceptor.count = 7;
        return serviceInterface.consultaLegales(request);
    }

    @Override
    public Call<TelefonoResponse> consultarTelefonoEmpleado(ServerRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return serviceInterface.consultarTelefonoEmpleado(request);

    }
}
