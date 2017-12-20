package com.sociomas.core.Utils;

public class Constants extends ConstantsV2 {

	//NUMEROS DE EMPLEADOS
	/* RAUL IVAN
	//171894
	* FELIPE
	  188027
	* JOSÉ LUIS
	* 10011898
	* GERARDO
	* 288508
	* */
	//Date Parsing
	public static final String DATE_FORMAT_PANICO="yyyymmddhhmmss";
	public static final String DATE_FORMAT_LOCATION="dd/MM/yy  HH:mm:ss";
	public static final String DATE_FORMAT_AVENTON="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMART_LUGAR_TRABAJO="yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_YEAR_FORMAT="yyyy-MM-dd";
	public static final String DATE_FORMAT	= "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_CONFIGURACION="yyyy-MM-dd";
	public static final String DAY_FORMAT	= "dd/MM/yyyy";
    public static final String HOUR_FORMAT	= "HH:mm:ss";
    public static final String DATE_FORMAT1	= "dd/MM/yyyy HH:mm";
	public static final String HOUR_FORMAT1	= "HH:mm";
	public static final String HOUR_FORMAT_AM_PM="hh:mm a";
	//SERVICIO CREDENCIAL
	//Webservices
	public static final String CHECK_EMPLOYEE_WS 	= "ObtenerEmpleado";
	public static final String GET_SETTINGS_WS 		= "ObtenerConfiguracionesTimeString";
	//public static final String REGISTER_EMPLOYEE_WS = "RegistrarEmpleado";
	public static final String REGISTER_MOVEMENT_WS = "RegistrarEmpleadoMovimientos_UTC";
	public  static final String AGREGAR_JUSTIFICACION="AgregarJustificacion";
    public static final String AsignarZonaAUsuario="AsignarZonaAUsuario";
	public static final String CatalogoTiposJustificantes="CatalogoTiposJustificantes";
	public  static final String validarcontrasenia="validarconñtrasenia";
	public static final String EliminarUbicacion="EliminarUbicacion";
	//public static final String obtenerhorarioempleado="obtenerhorarioempleado";
	public static final String ELiminar_horario="EditarEmpleadoHorarioServicio";
	public static final String consultarposicionesporempleado="consultarposicionesporempleado";
	public static final String ConsultarDetalleZona="ConsultarDetalleZona";
	public  static final String EliminarZonaAUsuario="EliminarZonaAUsuario";
	public static final String RegistrarPropuestaUbicacion="RegistrarPropuestaUbicacion";
	public static final String RegistrarPropuestaUbicacionCopia=RegistrarPropuestaUbicacion+"Copia";
	public static final String EnvioAlerta="EnvioAlerta";
	public static final String escribirlog="escribirlog";
	public static final String HistorialJustificacion="HistorialJustificacion";
	public static final String registrarempleadoprop="registrarempleadoprop";
	public static final String ListadoEmpleadosConIncidencias="ListadoEmpleadosConIncidencias";
	public static final String ListadoHorariosEmpleadoPlantilla="ListadoHorariosEmpleadoPlantilla";
	public static final String ConsultarPosiciones="ConsultarPosiciones";
	public static final String ObtenerArchivosAdjuntos="ObtenerArchivosAdjuntos";
	public static final String ObtenerConfiguracionesAplicacion="ObtenerConfiguracionesAplicacion";
	public static final String ObtenerEstatusAlerta="ObtenerEstatusAlerta";
	public static final String ObtenerCatalogoZonas="ObtenerCatalogoZonas";
	public static final String RechazarJustificante="RechazarJustificante";
	public static final String RegistrarEventoTelefono="RegistrarEventoTelefono";
	public static final String ValidarJustificante="ValidarJustificante";
	public static final String ValidarRechazarHorarioEmpleado="ValidarRechazarHorarioEmpleado";
	//public  static final String AgregarEmpleado_ArchivoClipMultimedia="AgregarEmpleado_ArchivoClipMultimedia";
	//public  static final String AgregarEmpleado_ArchivoClipMultimedia="ArchivoClipMultimedia";
	public  static final String AgregarEmpleado_ArchivoClipMultimedia="ArchivoClipMultimedia_Copia";

	public static  final String InsertaTelefonoContacto="InsertaTelefonoContacto";

	public static final String ConsultarExclusionesEmpleados="ConsultarExclusionesEmpleados";
	public static final String ConsultarExclusionesEmpleados_IOS="ConsultarExclusionesEmpleados_IOS";
	public static final String SolicitarExclusion="SolicitarExclusion";
	public static final String ObtenerddlPermiso="ObtenerddlPermiso";
	public static final String RegistraActualizaEmpleadoExclusion="RegistraActualizaEmpleadoExclusion";
	public static final String InicioRegistroEmpleado="InicioRegistroEmpleado";
	public static final String ListadoEmpleadosUbicaciones="ListadoEmpleadosUbicaciones";
	public static final String AceptarRechazarPropuestaUbicacion="AceptarRechazarPropuestaUbicacion";
	public static final String AceptarRechazarZonas="AceptarRechazarZonas";
	public static final String ClavesTelefonicas="ClavesTelefonicas";
	public static final String LiberacionNomina_Consulta = "EnviarInfo";

	//SharedPreferences
	public static final String SHARED_PREFERENCES	= "spgs";
	public static final String SHARED_PREFERENCES_GAFETE="spgsGafete";
	public static final String SP_NAME 				= "name";
	public static final String SP_ENTERPRISE 		= "company";
	public static final String SP_POSITION 			= "position";
	public static final String SP_HASLOGGED 		= "is_logged";
	public static final String SP_LOGGED 			= "1";
	public static final String SP_NOTLOGGED 		= "0";
	public static final String SP_ID 				= "id";
	public static final String SP_UPDATE_DATE       = "update_date";
	public static final String SP_DATE_LAST         ="date";
	public static final String MY_PHONE             ="phone";
	public static final String MY_CODIGO             ="my_codigo";
	public static final String MY_CLAVE              ="my_clave";
	
	//SharedPreferences para la configuracion de localización
	public static final String SP_TIME              = "conf_time";
	public static final String SP_RANGE             = "conf_range";
	public static final String SP_DISTANCE          = "conf_distance";
	public static final String NUMERO_JEFE          = "";
	// sharePreferences para Eventos dentro del telefono//
	public  static final String EVENTO                 ="0";
	//EmployeeLocationService
	public static final int 	LOCATION_INTERVAL = 1000;
	public static final float 	LOCATION_DISTANCE = 50f;
	
	//LoginActivity
	public static final String EMPLEADO = "EMPLEADO";
	
	//CheckEmployeesWS 
	public static final String ERROR = "error";
	
	//MainActivity
	public static final String APAGADO 		= "APAGADO";
	public static final String NINGUNO 		= "(NINGUNA)";
	public static final String WIFI    		= "WI-FI";
	public static final String ENCENDIDO	= "Activo";
	public static final String DATA_3G		= "3G";

	// MenuAplicaciones//
	public static final int tiempo         =5000;
	public static  final int TIEMPO_INTERVALO=1000;

	//EVENTOS//

	// parte de eventos
	public  static final int MODO_AVION=1;
	public  static  final int  APAGAR_GPS=2;
	public  static  final int APAGAR_DATOS=3;
	public  static  final int APAGAR_TELEFONO=4;
	public static final int PRENDERTElEFONO=5;
	public static final int PRENDERGPS=6;
	public  static final int APAGARMODOAVION=9;
	public  static  final int CAMBIO_FECHA_HORA=10;
	public  static  final int CAMBIO_ZONA=11;
	public static final int GPS_VIRTUAL=12;
	public static final int CAMBIO_SIM=13;
	public  static final String hora="HORA";
	public  static  final String minuto="minuto";
	public static final String tel1="telefono1";
	public static final String tel2="telefono2";
	public static final String tel3="telefono3";
	public static final String nombre1="Nombre1";
	public static final String nombre2="Nombre2";
	public  static final String nombre3="Nombre3";
	public static final String nombre="Nombre";
	public static final String tel="telefono";
	public static  final String provider="provider";
	/// eventos para la base de datos//
	public static final String PROVIDER="provider";
	public static final String path="path";
	public static final String lada="lada";
	public static final String lada1="lada1";
	public static final String lada2="lada2";
	public static final String Codigo="codigo";
	public  static  final String  Codigo1="codigo1";
	public static final String  Codigo2="codigo2";


	//CONSTANTES WEBSERVICES
	public static final String ID_NUM_EMPLEADO="id_num_empleado";
	public static final String IS_ERROR_FECHA="is_error_fecha";
	public static final String ERROR_SERVER="error";
	public static final String SERVER_UTC_TIME="serverUTCTime";
	public static final String SERVER_TIME="serverTime";

	//CONSTANTES SETTINGS OSCAR
	public static final String  ID_NUM_EMPLEADO_SETTING="id_num_empleado_setting";
	public static final String  SOLICITUD_DIA_DESCANSO="Solicitud de día de descanso";
	public static final String  DATA_SEND = "dataSend";
	public static final String  ID_NOTIFICACION="idNotificacion";
    public static final String ACTUALIZA_HORARIO_EMPLEADO = "ModificarHorarioEmpleado";
	public static final String CONSULTAR_NOTIFICACIONES = "listaCatalogoNotificaciones";
	public static final String LISTAR_NOTIFICACIONES = "listaNotificaciones";
	public static final String MODIFICAR_ESTATUS_NOTIFICACION = "ModificarNotificacion";
	public static final String PUESTO_EMPLEADO = "PuestoEmpleado";
	public static final String CONSULTA_LEGALES = "ConsultaLegales";
    public static final String IVAN_NUMERO ="10014644" ;
    public static String IDSistema = "ANDROIDSOCIO";
	public static final String MODIFICAR_TIPO_HORARIO = "ModificarTipoHorarioEmpleado";
	public static final String DEVUELVE_AVISO="DevuelveAviso";
	public static final String INSERTAR_POSICION_AVISO="insertaPosicionAviso";
	public static final String MODIFICA_LUGAR_TRABAJO_TIPO = "ModificarPrefLugarTrabajo";
	public static final String OBTENER_IMAGENES_GAFETE="ObtenerGafeteGenerico";
	public static final String REGISTRAR_ASISTENCIA_MANUAL="registraAsistenciaManual";
	public static final String OBTENER_UNIDADES_NEGOCIO = "ObtenerLstImgEmpresas";
	public static final String OBTENER_MI_UNIDADNEGOCIO = "ObtenerLogoEmpresaEmpleado";
	public static final String GUARDAR_MI_UNIDADNEGOCIO = "ModificarClaveEmpresaEmpleado";

	public static final String FIRMO_PRIVACIDAD="FIRMO_PRIVACIDAD";
	public static final String FIRMO_TERMINOS="FIRMO_TERMINOS";
	
	public static String ContadorNotificaciones = "ContadorNotificaciones";
    public static String CLAVE_UNIDAD="CLAVE_UNIDAD_NEGOCIO";
}