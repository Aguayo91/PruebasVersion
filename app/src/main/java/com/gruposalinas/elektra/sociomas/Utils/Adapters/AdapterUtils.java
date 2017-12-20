package com.gruposalinas.elektra.sociomas.Utils.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IntegerRes;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.JustificarIncidenciaActivity;
import com.gruposalinas.elektra.sociomas.R;

import com.gruposalinas.elektra.sociomas.Utils.Date.TimeUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.ConstantsV2;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Enums.EnumContacto;
import com.sociomas.core.Utils.Enums.EnumIncidencia;
import com.sociomas.core.Utils.Enums.EnumTipo;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionContacto;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by oemy9 on 03/05/2017.
 */

public class AdapterUtils {


    //region PROMOCIONES
    /*OBTEIENE EL  PORCENTAJE DE DESCUENTO EN EL PRIMER ELEMENTO
  Y EN EL SEGUNDO REGRESA  EL CONTENDIDO DE LA PROMOCIÓN*/
    public static ArrayList<String> getDescuento(String input){
        String descuento="";
        String descripcion="";
        String[] partes = input.split(" ");
        ArrayList<String>listRetorno=new ArrayList<>();
        if (partes.length >= 2) {
            if (partes[0].contains("%")) {
                descuento = partes[0].length()>4?getDigitos(partes[0]).concat("%"):partes[0];
            } else if (partes[0].contains("x") && partes[0].length()<4) {
                descuento = partes[0].trim();
            }
            else {
                descuento = "PE";
            }
            listRetorno.add(descuento);
            if(partes[1]!=null) {
                descripcion = android.text.Html.fromHtml(partes[1].trim()).toString();
                descripcion = descripcion.replace("\n", "");
                listRetorno.add(descripcion.length()>1?descripcion.substring(0, 1).toUpperCase() + descripcion.substring(1): descripcion);

            }else {
                listRetorno.add(input);
            }
        }
        return listRetorno;
    }
    public static String getDigitos(String input){
        String digitos="";
        int max_digitos=3;
        int total_digitos=0;
        String inputLowerCase=input.toLowerCase(Locale.ENGLISH);
        List<String> listPreposicones= Arrays.asList("del","hasta","el","al","de");
        int countDescuento=0;
        for(String prepo: listPreposicones){
            if(inputLowerCase.indexOf(prepo)!=-1){
                inputLowerCase=inputLowerCase.replace(prepo,"");
            }
        }

        if(input.toLowerCase().length()>4) {
            for (Character c : inputLowerCase.toCharArray()) {
                if (total_digitos < max_digitos && countDescuento<1) {
                    if(c=='%'){
                        countDescuento++;
                    }
                    if (Character.isDigit(c) || c == '.' || c=='%') {
                        digitos += c.toString();
                        total_digitos++;
                    }
                }
            }
        }
        else{
            digitos=inputLowerCase;
        }
        return digitos.trim().replace("%","");
    }

    public static ArrayList<PromocionContacto> getDetalleContacto(String html, String htmlCondiciones,String telefono){
        ArrayList<PromocionContacto>contactoList=new ArrayList<>();
        Document doc= Jsoup.parse(html);
        Elements elements=doc.select("a");
        //DOMICILIO
        String domicilio= html.indexOf("<br")!=-1? Utils.toUppperCaseFirst(html.substring(0,html.indexOf("<br"))): html;
        contactoList.add(new PromocionContacto(domicilio,EnumContacto.Domicilio));


        if(telefono == null){
            telefono="";
        }
        if(!telefono.isEmpty()){

                String[]telefonos=telefono.split(",");
                for (String t: telefonos) {
                    contactoList.add(new PromocionContacto(t, EnumContacto.Telefono));
                }
        }

        /*CONDICIONES*/
        String condiciones= Html.fromHtml(htmlCondiciones).toString();
        contactoList.add(new PromocionContacto(Utils.toUppperCaseFirst(condiciones), EnumContacto.Condiciones));

        return contactoList;
    }


    public static ArrayList<PromocionContacto> getDetalleContacto(String html, String htmlCondiciones){
        ArrayList<PromocionContacto>contactoList=new ArrayList<>();
        Document doc= Jsoup.parse(html);
        Elements elements=doc.select("a");
        //DOMICILI
        String domicilio= html.indexOf("<br")!=-1? Utils.toUppperCaseFirst(html.substring(0,html.indexOf("<br"))): html;
        contactoList.add(new PromocionContacto(domicilio,EnumContacto.Domicilio));
        //TELÉFONOS
        for(Element element: elements){
            String telefono=limpiarTelefono(element.text());
            if(!telefono.isEmpty())
            contactoList.add(new PromocionContacto(telefono, EnumContacto.Telefono));

        }
        /*CONDICIONES*/
        String condiciones= Html.fromHtml(htmlCondiciones).toString();
        contactoList.add(new PromocionContacto(Utils.toUppperCaseFirst(condiciones), EnumContacto.Condiciones));

        return contactoList;
    }
    private static String limpiarTelefono(String tel){
        int index=0;
        tel=tel.replaceAll("\\s","").replace("521","").replace("-","");
        char[]telArray=tel.toCharArray();
        for(int j=0; j<telArray.length;j++){
            if(!Character.isDigit(telArray[j]) || Character.isWhitespace(telArray[j])){
                index=j;
                break;
            }
        }
        tel= tel.length()>=25? "": tel;
        return index!=0 && tel.length()>0?tel.replace(tel.substring(index),""): tel;
    }


    //endregion

    public static int getIconoByActividad(String activididad){
        int icono=R.drawable.ic_human;
        switch (activididad){
            case ConstantsV2.CAMINANDO_LENTO:
            case ConstantsV2.CAMINANDO:
            case ConstantsV2.DESCONOCIDA:
                icono=R.drawable.ic_human;
             break;
            case ConstantsV2.OFICINA_CASA:
                icono=R.mipmap.ic_building_activity;
                break;
            case ConstantsV2.BICICLETA:
            case ConstantsV2.CORRIENDO:
            case ConstantsV2.VEHICULO:
                icono=R.mipmap.ic_car_activity;
                break;
            default:
                icono=R.drawable.ic_human;
            break;

        }
        return icono;
    }
    public static  int getImagenByMovimiento(Ubicaciones movimiento){
        int icono=R.drawable.ic_human;
        if(movimiento.getMasDeUnDiaSinReportar()==1){
            icono= R.mipmap.map_warning_icon;
        }
        else if(movimiento.getPosicionValida()==1 && movimiento.getMasDeUnDiaSinReportar()==0){
            icono=R.drawable.ic_marker_green;
        }
        else if(movimiento.getPosicionValida()!=1  && movimiento.getMasDeUnDiaSinReportar()==0){
            icono=R.drawable.ic_nowalk;
        }
        return icono;
    }

    public static int getImagenActionByString(String actividad){
        int retorno=R.mipmap.ic_walk_direction;
        switch (actividad){
            case Constants.CAMINANDO_LENTO:
            case Constants.CAMINANDO:
                retorno= R.mipmap.ic_walk_direction;
                break;
            case Constants.VEHICULO:
            case Constants.BICICLETA:
                retorno= R.mipmap.ic_car_direction;
                break;
            case Constants.CORRIENDO:
                retorno= R.mipmap.ic_runing;
                break;
            case Constants.OFICINA_CASA:
                retorno= R.mipmap.ic_building_action;
                break;
            case Constants.DESCONOCIDA:
                retorno= R.mipmap.ic_building_action;
                break;
        }
        return retorno;
    }

    public static int getImageBattery(double porcentaje){
        int imagen=0;
        if(porcentaje>=80) imagen=R.drawable.batery_c;
        else if(porcentaje<80)  imagen=R.drawable.battery_middle;
        else if(porcentaje<=30) imagen=R.drawable.batery_low;
        return imagen;
    }

    public static int getImageByDispositivo(String dispositivo){

      return dispositivo==null ||
              (dispositivo.toLowerCase(Locale.ENGLISH).equals("android"))
              ? R.mipmap.ic_android: R.mipmap.ic_apple;
    }

    public static String getUrlImageEmpleado(String numeroEmpleado){
        return "http://portal.socio.gs/foto/back_office/empleados/"
                .concat(numeroEmpleado).concat(".jpg");
    }

    public static HashMap<EnumAsistencia,Integer>  getHashIconosAsistencias(){
        HashMap<EnumAsistencia,Integer> hashIconos = new HashMap<>();
        hashIconos.put(EnumAsistencia.FALTA,R.drawable.ic_falta);
        hashIconos.put(EnumAsistencia.ASISTENCIA_CORRECTA, R.drawable.ic_asistencia_correcta);
        hashIconos.put(EnumAsistencia.SALIDA_ANTES_HORARIO,R.drawable.ic_salidas);
        hashIconos.put(EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE, R.drawable.ic_clock_tarde);
        hashIconos.put(EnumAsistencia.RETARDO,R.drawable.ic_clock_tarde);
        hashIconos.put(EnumAsistencia.TODAVIA_NO_TERMINA_DIA,R.drawable.notermina);
        hashIconos.put(EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE,R.drawable.img_clock_yellow);
        return hashIconos;
    }
    public static HashMap<EnumAsistencia,String>  getHashEstatusAsistencias(){
        HashMap<EnumAsistencia,String> hashEstatus = new HashMap<>();
        hashEstatus.put(EnumAsistencia.FALTA,EnumAsistencia.FALTA.toString());
        hashEstatus.put(EnumAsistencia.ASISTENCIA_CORRECTA, EnumAsistencia.ASISTENCIA_CORRECTA.toString());
        hashEstatus.put(EnumAsistencia.SALIDA_ANTES_HORARIO,"Salida temprano");
        hashEstatus.put(EnumAsistencia.RETARDO,EnumAsistencia.RETARDO.toString());
        hashEstatus.put(EnumAsistencia.TODAVIA_NO_TERMINA_DIA, EnumAsistencia.TODAVIA_NO_TERMINA_DIA.toString());
        hashEstatus.put(EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE,"Fuera de la hora límite");
        return hashEstatus;
    }
    public static  HashMap<EnumAsistencia, Integer>getHashColoresAsistencas(){
        HashMap<EnumAsistencia,Integer> hashColores = new HashMap<>();
        hashColores.put(EnumAsistencia.FALTA, R.color.rojo_falta);
        hashColores.put(EnumAsistencia.ASISTENCIA_CORRECTA,R.color.verde_llegada_temprano);
        hashColores.put(EnumAsistencia.SALIDA_ANTES_HORARIO,R.color.naranja_salida_temprano);
        hashColores.put(EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE, R.color.naranja_llegada_tarde);
        hashColores.put(EnumAsistencia.RETARDO, R.color.naranja_llegada_tarde);
        hashColores.put(EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE, R.color.colorAmarilloEntrada);
        return hashColores;
    }
    public static HashMap<EnumAsistencia, String> getHashMensajePlantillaAsistencias(){
        HashMap<EnumAsistencia,String> hashMensaje = new HashMap<>();
        hashMensaje.put(EnumAsistencia.FALTA,"Tuvo una falta");
        hashMensaje.put(EnumAsistencia.ASISTENCIA_CORRECTA, EnumAsistencia.ASISTENCIA_CORRECTA.toString());
        hashMensaje.put(EnumAsistencia.SALIDA_ANTES_HORARIO,"Salió temprano");
        hashMensaje.put(EnumAsistencia.RETARDO,"Tuvo retardo");
        hashMensaje.put(EnumAsistencia.TODAVIA_NO_TERMINA_DIA, EnumAsistencia.TODAVIA_NO_TERMINA_DIA.toString());
        hashMensaje.put(EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE,"Fuera de la hora límite");
        return hashMensaje;
    }

    public static HashMap<EnumAsistencia, String> getHashMensajeAsistencias(){
        HashMap<EnumAsistencia,String> hashMensaje = new HashMap<>();
        hashMensaje.put(EnumAsistencia.FALTA,"Tuvo una falta");
        hashMensaje.put(EnumAsistencia.ASISTENCIA_CORRECTA, EnumAsistencia.ASISTENCIA_CORRECTA.toString());
        hashMensaje.put(EnumAsistencia.SALIDA_ANTES_HORARIO,"Salida temprano");
        hashMensaje.put(EnumAsistencia.RETARDO,"Retardo");
        hashMensaje.put(EnumAsistencia.TODAVIA_NO_TERMINA_DIA, EnumAsistencia.TODAVIA_NO_TERMINA_DIA.toString());
        hashMensaje.put(EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE,"Fuera de la hora límite");
        return hashMensaje;
    }


    public static Uri getImageBase64(Context context, String input)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        try {
            bytes.write(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap=BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        bitmap.recycle();
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    public static void navegarIncidencias(Activity activity, ListadoIncidencias item, EnumTipo tipo){

        EnumIncidencia enumIncidencia=EnumIncidencia.getFromSting(item.getEstatusJustificacion());

        switch (enumIncidencia){
            case rechazado:
            case sin_justificar: {
                Intent intent = new Intent(activity, JustificarIncidenciaActivity.class);
                Bundle mBundle=new Bundle();
                mBundle.putSerializable(Constants.SELECTED_INCIDENCIA,item);
                mBundle.putBoolean(Constants.IS_TEMP_FIJA, tipo == EnumTipo.mio);
                intent.putExtras(mBundle);
                activity.startActivity(intent);
            }
            break;
            case pendiente:
                Toast.makeText(activity,"Pendiente de autorización",Toast.LENGTH_SHORT).show();
                break;
            case autorizado:
                Toast.makeText(activity,"Autorizado",Toast.LENGTH_SHORT).show();
                break;
        }


    }

    public static int getResourceFileByTipoJustificacion(String statusJustificación){
        int resource=0;
        EnumIncidencia enumIncidencia=EnumIncidencia.getFromSting(statusJustificación);
        switch (enumIncidencia){
            case autorizado:
                resource=R.drawable.icono_autorizado_x2;
                break;
            case sin_justificar:
                resource=R.drawable.ic_por_autorizar;
                break;
            case pendiente:
                resource=R.drawable.icono_pendiente_autorizar_x2;
                break;
            case rechazado:
                resource=R.drawable.icono_por_justificar_x2;
                break;
            default:
                Log.i("UTILS","NO SOPORTADO:"+ statusJustificación);
                break;
        }
        return resource;
    }

    public static String getRelativeDate(Context context,String input ,SimpleDateFormat dateFormatFrom, Date date) throws ParseException {
        TimeUtils timeUtils=new TimeUtils(context);
        date=dateFormatFrom.parse(input);
        return timeUtils.getDateDifferenceForDisplay(date);
    }



}
