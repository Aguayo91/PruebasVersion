package com.sociomas.core.Utils;

/**
 * Created by oemy9 on 21/03/2017.
 */

@SuppressWarnings("unused")
public class ConstantsV2 {

    //SERVICIOS-
    public static final String DOMAIN_URL_STAGING_AVENTONES="https://sociomas.com.mx/staging/Aventones/Servicios/ServicioEmpleados_Aventones.svc/";
    public static final String DOMAIN_URL_STAGING_PROMOCIONES="https://sociomas.com.mx/staging/ServicioEmpleados_Descuentos.svc";
    public static final String DOMAIN_URL_STAGING_MOVILIDAD="https://sociomas.com.mx/staging/ServicioEmpleados_2.svc/";
    public static final String DOMAIN_URL_NOMINA_STAGING="https://sociomas.com.mx/staging/ServicioLiberacionNomina.svc/";


    //PRODUCCIÓN
    public static final String DOMAIN_URL = "https://sociomas.com.mx/ServicioEmpleados_2.svc/";
    public static final String DOMAIN_URL_ASISTENCIA = "https://sociomas.com.mx/ServicioEmpleados_Web.svc/";
    public static final String DOMAIN_URL_PROMOCIONES = "https://portalsocio.gs/servicios/servicios.aspx/";

    //DESARROLLO
    public static final String DOMAIN_URL_DESARROLLO_GAFETE="http://10.112.48.31/GS_MovilidadServicios_2/ServicioEmpleados_2.svc/";
    public static final String DOMAIN_URL_DESARROLLO="http://10.112.48.5/GS_MovilidadServicios_2/ServicioEmpleados_2.svc/";
    public static final String DOMAIN_URL_AVENTON_DESARROLLO="http://10.63.50.108:8083/Aventones/Servicios/ServicioEmpleados_Aventones.svc/";
    public static final String DOMAIN_URL_DESARROLLO_ANA="http://10.112.48.7/GS_MovilidadServicios_2/ServicioEmpleados_2.svc/";
    //public static final  String DOMAIN_URL="http://10.112.48.25/gs_movilidadservicios_2/servicioEmpleados_2.svc/";//URL ANA
    public static final String DOMAIN_URL_ALBERTO="http://10.112.48.14/gs_movilidadservicios_2/servicioEmpleados_2.svc/";
    public static final String DOMAIN_URL_NOMINA = "http://10.112.48.32/GS_MovilidadServicios_2/ServicioLiberacionNomina.svc";//URL EDUARDO BEREA
    public static final String DOMAIN_URL_NOMINA_ROMEO = "http://10.112.48.12/GS_MovilidadServicios_2/ServicioLiberacionNomina.svc";//URL ROMEO
    public static final String DOMAIN_URL_NOMINA_EDUARDO = "http://10.112.48.11/GS_MovilidadServicios_2/ServicioLiberacionNomina.svc";//URL EDUARDO BEREA
//    public static final String DOMAIN_URL_NOMINA = "http://10.112.48.23/GS_MovilidadServicios_2/ServicioLiberacionNomina.svc";//URL EDUARDO
    public static final  String DOMAIN_URL_PROMO_DESARROLO="http://10.112.48.21/gs_movilidadservicios_2/ServicioEmpleados_Descuentos.svc";
    public static final String DOMAIN_URL_AVENTON_DESARROLLO_ANA="http://10.112.48.30/GS_MovilidadServicios_2/Aventones/Servicios/ServicioEmpleados_Aventones.svc/";

    //PRE PRODUCCIÓN
    public static final String DOMAIN_URL_PRE_PRO="http://10.63.50.108:8083/ServicioEmpleados_2.svc/";
    // public static final String DOMAIN_URL_PRE_AVENTONES="http://10.63.50.108:8083/Aventones/Servicios/ServicioEmpleados_Aventones.svc/";
    //public static final  String DOMAIN_URL="http://10.112.48.23/gs_movilidadservicios_2/servicioEmpleados_2.svc";
//    public static final String DOMAIN_URL_NOMINA = "http://10.112.48.13/GS_MovilidadServicios_2/ServicioLiberacionNomina.svc/";//URL ROMEO
//    public static final String DOMAIN_URL_NOMINA = "http://10.112.48.32/GS_MovilidadServicios_2/ServicioLiberacionNomina.svc";//URL ROMEO
//    public static final String DOMAIN_URL_NOMINA = "http://10.112.48.23/GS_MovilidadServicios_2/ServicioLiberacionNomina.svc";//URL EDUARDO
//    public static final String DOMAIN_URL_NOMINA = "http://10.112.48.31/GS_MovilidadServicios_2/ServicioLiberacionNomina.svc";//URL EDUARDO


    /*ALARMAS IDS ALEATORIOS SE PUEDEN CAMBIAR CUANDO LO DESEEN*/
    public static final String TIPO_ALARMA = "TIPO_ALARMA";
    public static final int ALARM_ID_ENTRADA = 2013600;
    public static final int ALARM_ID_DIEZ = 1012600;
    public static final int ALARM_ID_SALIDA = 2011600;
    public static final int INTERVALO_ALARMA = 2;
    public static final long INTERVALO_MINUTO = 60000;
    public static final String INCIAR_SERVICIOS_BROADCAST = "com.gruposalinas.elektra.movilidadgs.INICIAR_SERVICIOS";
    public static final String LAST_ID_FROM_GPS = "LAST_ID_FROM_GPS";
    public static final String LAST_HORA_FROM_SERVER = "LAST_HOUR_FROM_SERVER";
    public static final String LAST_REAL_TIME = "LAST_REAL_TIME";
    public static final String IS_HTTPS = "https";
    public static final String LAST_TIME_UPDATE = "last_time_update";
    public static final String LAST_TIME_GPS_UPDATE = "last_time_gps_update";
    public static final String LAST_HOUR_GPS_UPDATE = "last_hour_gps_update";
    public static final String DATE_MODIFIED = "date_modified";
    public static final String FROM_REBOOT = "from_reboot";

    //ACTIVIDADES
    public static final String CORRIENDO = "CORRIENDO";
    public static final String CAMINANDO_LENTO = "CAMINANDO LENTO";
    public static final String OFICINA_CASA = "OFICINA/CASA";
    public static final String CAMINANDO = "CAMINANDO";
    public static final String BICICLETA = "BICICLETA";
    public static final String VEHICULO = "VEHICULO";
    public static final String DESCONOCIDA = "DESCONOCIDA";

    /*DATEFORMAT INCIDENCIAS*/
    public static final String DATE_FORMAT_INCIDENCIA = "dd/MM/yyyy hh:mm:ss";


    /*TIMEZONE WEBSERVICE*/
    public static final String SECURE_DATE_FORMAT = "yyyyMMddkkmm";
    public static final String DATE_FORMAT_INCIDENCIAS_FROM = "dd/MM/yyyy hh:mm:ss";
    public static final String DATE_FORMAT_INCIDENCIAS_TO = "dd-MM-yyyy";
    public static final String DOMAIN_URL_TIMEZONE = "http://api.timezonedb.com/v2/";
    public static final String GET_TIMEZONE = "get-time-zone";
    public static final String KEY = "AYILL585DL4Q";
    public static final String FORMAT = "json";
    public static final String BY = "position";

    public static final String READY_TO_UPDATE = "READY_TO_UPDATE";
    public static final String TIME_ZONE_NAME = "time_zone_name";

    /*INTERVALO EN DÍAS DE BORRADO GPS*/
    public static final int INTERVALO_BORRADO = 2;
    public static final String ULTIMO_INTENTO_BORRADO = "ULTIMO_INTENTO_BORRADO";
    public static final String ULTIMA_VEZ_APP_FOREGROUND = "ULTIMA_VEZ_APP_FOREGROUND";
    public static final int SEGUNDO = 1000;

    /*INCIDENCIAS*/
    public static final String BITMAP_SELECTED_IMAGE = "BITMAP_SELECTED_IMAGE";
    public static final String SELECTED_ID_EMPLEADO = "SELECTED_ID_EMPLEADO";
    public static final String SELECTED_FECHA_BUSCADA = "SELECTED_FECHA_BUSCADA";
    public static final String SELECTED_INCIDENCIA = "SELECTED_INCIDENCIA";

    /*JUSTIFICACAR INCIDENCIA*/
    public static final String SCREEN_EVENTO = "SCREEN_EVENTO";
    public static final int RESULT_LOAD_IMAGE = 100;
    public static final int RESULT_LOAD_IMAGE_CAMERA = 110;
    public static final String IS_TEMP_FIJA = "IS_TEMP_FIJA";
    public static final String HAS_SHOW_TIP = "HAS_SHOW_TIP";

    /*PERMISOS*/
    public static final String IS_PLANTILLA = "IS_PLANTILLA";
    public static final String IS_RECHAZAR="IS_RECHAZAR";
    public static final String LIST_EMPLEADOS = "LIST_EMPLEADOS";

    /*HOARIOS*/
    public static final String SELECTED_HORARIO_ITEM = "SELECTED_HORARIO_ITEM";
    public static final String ELIMINAR_HORARIO_EXTRA = "eliminar";
    public static final String LIST_PROPUESTAS = "LIST_PROPUESTAS";
    public static final String SELECTED_POSITION_EMPLEADO = "SELECTED_POSITION_EMPLEADO";
    public static final String SELECTED_CONSULTA = "SELECTED_CONSULTA";
    public static final String APROBAR_HORARIO = "aprobar";
    public static final String RECHAZAR_HORARIO = "rechazar";

    /*IMAGEN DE PERFIL USUARIO*/
    public static final String USUARIO_PERFIL_IMAGE = "usuario_perfil_image";
    public static final String WALLPAPER_PERFIL_IMAGE = "wallpaper_perfil_image";
    public static final String TELEFONO_VALIDADO = "telefono_validado";

    /*ZONAS & UBICACIONES*/
    public static final String UBICACIONES = "destinos";
    public static final String ZONAS = "zonas";
    public static final String SELECTED_ZONA_TIENDA = "SELECTED_ZONA_TIENDA";
    public static final String ELIMINAR_UBICACION = "ELIMINAR_UBICACION";
    public static final String RECHAZAR_ZONA_UBICACION = "RECHAZADO";

    /*SMS VERIFICACION*/
    public static final String VERIFICAR_CODIGO = "VerificarCodigo";
    public static final String REENVIAR_CODIGO = "ReenviarCodigo";
    public static final String SOLICITAR_CODIGO = "SolicitarCodigo";
    public static final String CODIGO_VERIFICACION = "CODIGO_VERIFICACION";
    public static final String CODIGO_LLAMADA = "CodigoXLlamada";
    public static final String BROAD_CODIGO_VERIFICACION = "com.gruposalinas.elektra.movilidadgs.BROAD_CODIGO";
    /*SLIDER*/
    public static final String MOSTRAR_SLIDER = "mostrar_slider";
    /*VISTA CUENTANOS*/
    public static final String PIE_PAGINA = "pie_pagina";
    public static final String CUENTANOS_INICIO = "http://movil.gs/movil/content/ekt/secciones/cuentanos/cuentanos.html?source=Inicio-General";
    public static final String CUENTANOS_LOGIN = "http://movil.gs/login.html";
    public static final String PERMISO_INTERNO = "com.gruposalinas.elektra.movilidadgs.PermisoInterno";
    /*CREDENCIAL*/
    public static final String IMAGEN_CREDENCIAL = "IMAGEN_CREDENCIAL";
    /*TIMEZONE*/
    public static final String LAST_OPTION_NAV = "LAST_OPTION_NAV";
    public static final String LAST_TIME_ZONE_UPDATE = "LAST_TIME_ZONE";
    /*BOTÓN DE SOS*/
    public static final String SOS_RUNNING = "SOS_RUNNING";
    public static final String SOS_CURRENT_REQUEST = "SOS_CURRENT_REQUEST";
    /*ID ROL EMPLEADO*/
    public static final String ID_ROL_EMPLEADO = "ID_ROL_EMPLEADO";
    /*ID CATEGORIA PROMOCION*/
    public static final String ID_CATEGORIA_PROMOCION = "ID_CATEGORIA_PROMOCION";
    public static final String NOMBRE_CATEGORIA_PROMOCION = "NOMBRE_CATEGORIA";
    public static final String SELECTED_EMPRESA_PROMOCION = "SELECTED_EMPRESA_PROMOCION";
    /*CREDENCIALIZACIÓN*/
    public static final String USUARIO_FOTO_FRONTAL = "USUARIO_FOTO_FRONTAL";
    public static final String RECIBIR_ARCHIVO = "RecibirArchivo";
    public static final String OBTENER_CREDENCIAL = "ObtenerCredencial";
    public static final String GENERAR_CREDENCIAL = "GENERAR_CREDENCIAL";


    public static final String API_KEY = "AIzaSyAYx0OeIcXDQAzY5tMTEtTGFoLRtTVpIns";
    public static final String URL_BASE_GOOGLE = "https://maps.googleapis.com/maps/api/";
    public static final String URL_BASE_AVENTON_CORE = "http://10.51.118.169/GS_MovilidadServicios_3/Aventones/Servicios/ServicioEmpleados_Aventones.svc/";

    //url desarrollo Ana
    public static final String URL_BASE_AVENTON = "http://10.112.48.30/GS_MovilidadServicios_2/Aventones/Servicios/ServicioEmpleados_Aventones.svc/";
    //Url desarrollo Alberto
    public static final String URL_BASE_AVENTON_Alberto = "http://10.112.48.28/GS_MovilidadServicios_2/Aventones/Servicios/ServicioEmpleados_Aventones.svc/";
    //EXTRAS PARA GENERAR RUTA
    public static final String DESTINO_PLACE = "DESTINO_PLACEID";
    public static final String INICIO_PLACE = "INICIO_PLACEID";
    //BROADCAST UBICACIÓN
    public static final String BROAD_CAST_LOCATION = "com.sociomas.aventones.SERVICIO_LOCATION";
    public static final String BROAD_CAST_NOTIFICATION = "com.sociomas.aventones_FOREGROUND_NOTIFICATION";
    public static final String CURRENT_LATLONG = "CURRENT_LATLONG";
    public static final String CURRENT_ACCURACY = "CURRENT_ACCURACY";
    //PLACEPICKER REQUEST
    public static final int PLACE_PIKER_REQUEST = 200;
    //PETICIÓN PARA OBTENER LISTADO DE TIENDAS
    public static final String CONSULTAR_POSICIONES = "ConsultarPosiciones";

    /*FORMARTO DE HORAS*/
    public static final String HOUR_FORMAT_AM_PM = "hh:mm a";

    public static final String PAIS = "MX";
    //EXTRA PARA PASAR LIST DE AUTOS
    public static final String SELECTED_AUTOS = "SELECTED_AUTOS";


    //AVENTONES USUARIOS PILOTO
    public static final String IS_USUARIO_AVENTON = "USUARIO_AVENTON";
    public static final String IS_USUARIO_NOMINA="USUARIO_NOMINA";
    public static final String MOSTRAR_NUEVO_GAFETE="MOSTRAR_NUEVO_GAFETE";
    public static final String ID_ROL_EMPLEADO_AVENTON = "ID_ROL_EMPLEADO_AVENTON";

    //CLICK ACTION DE FIREBASE
    public static final String CLICK_ACTION_CONDUCTOR = "SOLICITUD_CONDUCTOR";
    public static final String SP_ID_CONDUCTOR = "SP_ID_CONDUCTOR";

    //BRILLO ACTUAL DEL DISPOSITIVO
    public static final String CURRENT_BRILLO="CURRENT_BRILLO";

    //POLITICAS DE PRIVACIDAD
    public static final String POLITICAS="POLITICAS";

    public static final String JSON_GAFETE_RESPONSE="JSON_GAFETE_RESPONSE";

    public static final String SELECTED_FECHA_INICIO="SELECTED_FECHA_INICIO";
    public static final String SELECTED_FECHA_FIN="SELECTED_FECHA_FIN";


    public static final String HAS_GAFETE ="HAS_GAFETE" ;

    // Ubicaciones del jks
    public static final String OSCARKEYSTORE="'C:/Users/oemy9/Documents/KeyStores/Socio mas/keySocioMAS.jks'";
    public static final String YISUSKEYSTORE="'E:/Users/jromeromar/Desktop/Credenciales/keySocioMAS.jks'";


 }