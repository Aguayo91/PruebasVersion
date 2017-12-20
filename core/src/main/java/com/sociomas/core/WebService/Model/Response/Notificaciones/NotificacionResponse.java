package com.sociomas.core.WebService.Model.Response.Notificaciones;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GiioToledo on 27/11/17.
 */

public class NotificacionResponse implements Serializable, Comparable<NotificacionResponse>{

    @SerializedName("clickAction")
    private String clickAction;

    @SerializedName("datos_movil")
    private String datos_movil;

    @SerializedName("error_firebase")
    private String error_firebase;

    @SerializedName("fecha_hora")
    private String fecha_hora;

    @SerializedName("id_estatus_notificacion")
    private int id_estatus_notificacion;

    @SerializedName("id_estatus_usuario_notificacion")
    private int id_estatus_usuario_notificacion;

    @SerializedName("id_numero_empleado")
    private String id_numero_empleado;

    @SerializedName("id_tipo_notificacion")
    private int id_tipo_notificacion;

    @SerializedName("msg_notificacion")
    private String msg_notificacion;

    @SerializedName("titulo")
    private String titulo;

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getDatos_movil() {
        return datos_movil;
    }

    public void setDatos_movil(String datos_movil) {
        this.datos_movil = datos_movil;
    }

    public String getError_firebase() {
        return error_firebase;
    }

    public void setError_firebase(String error_firebase) {
        this.error_firebase = error_firebase;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public int getId_estatus_notificacion() {
        return id_estatus_notificacion;
    }

    public void setId_estatus_notificacion(int id_estatus_notificacion) {
        this.id_estatus_notificacion = id_estatus_notificacion;
    }

    public int getId_estatus_usuario_notificacion() {
        return id_estatus_usuario_notificacion;
    }

    public void setId_estatus_usuario_notificacion(int id_estatus_usuario_notificacion) {
        this.id_estatus_usuario_notificacion = id_estatus_usuario_notificacion;
    }

    public String getId_numero_empleado() {
        return id_numero_empleado;
    }

    public void setId_numero_empleado(String id_numero_empleado) {
        this.id_numero_empleado = id_numero_empleado;
    }

    public int getId_tipo_notificacion() {
        return id_tipo_notificacion;
    }

    public void setId_tipo_notificacion(int id_tipo_notificacion) {
        this.id_tipo_notificacion = id_tipo_notificacion;
    }

    public String getMsg_notificacion() {
        return msg_notificacion;
    }

    public void setMsg_notificacion(String msg_notificacion) {
        this.msg_notificacion = msg_notificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaDate() throws ParseException {
        SimpleDateFormat dt=new SimpleDateFormat("dd/mm/yy HH:mm:ss");
        return dt.parse(this.getFecha_hora());
    }

    @Override
    public String toString() {
        return "NotificacionResponse{" +
                "clickAction='" + clickAction + '\'' +
                ", datos_movil='" + datos_movil + '\'' +
                ", error_firebase='" + error_firebase + '\'' +
                ", fecha_hora='" + fecha_hora + '\'' +
                ", id_estatus_notificacion='" + id_estatus_notificacion + '\'' +
                ", id_estatus_usuario_notificacion=" + id_estatus_usuario_notificacion +
                ", id_numero_empleado='" + id_numero_empleado + '\'' +
                ", id_tipo_notificacion=" + id_tipo_notificacion +
                ", msg_notificacion='" + msg_notificacion + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull NotificacionResponse o) {
        try {
            return o.getFechaDate().compareTo(getFechaDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
