package com.sociomas.core.WebService.Model.Response.Zonas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by oemy9 on 23/06/2017.
 */

@SuppressWarnings("unused")
public class LugarTrabajo implements Serializable, Parcelable {

    private  String tipoPosicion;
    private boolean isChecked=false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    protected LugarTrabajo(Parcel in) {
        tipoPosicion = in.readString();
        id_csc_zo_pos = in.readInt();
        va_descripcion_zn_posic = in.readString();
        bit_valida = in.readByte() != 0;
        dec_latitud = in.readDouble();
        dec_longitud = in.readDouble();
        id_estatus_posic = in.readString();
        int_rango_aceptado = in.readInt();
        va_comentario = in.readString();
        va_nombre_pos = in.readString();
        nombre_completo = in.readString();
        va_numero_pos = in.readString();
        id_num_empleado = in.readString();

    }

    public static final Creator<LugarTrabajo> CREATOR = new Creator<LugarTrabajo>() {
        @Override
        public LugarTrabajo createFromParcel(Parcel in) {
            return new LugarTrabajo(in);
        }

        @Override
        public LugarTrabajo[] newArray(int size) {
            return new LugarTrabajo[size];
        }
    };

    public String getTipoPosicion() {
        return tipoPosicion;
    }

    public void setTipoPosicion(String tipoPosicion) {
        this.tipoPosicion = tipoPosicion;
    }

    private int id_csc_zo_pos;

    public int getIdCscZoPos() { return this.id_csc_zo_pos; }

    public void setIdCscZoPos(int id_csc_zo_pos) { this.id_csc_zo_pos = id_csc_zo_pos; }

    private String va_descripcion_zn_posic;

    public String getVaDescripcionZnPosic() { return this.va_descripcion_zn_posic; }

    public void setVaDescripcionZnPosic(String va_descripcion_zn_posic) { this.va_descripcion_zn_posic = va_descripcion_zn_posic; }

    private boolean bit_valida;

    public boolean getBitValida() { return this.bit_valida; }

    public void setBitValida(boolean bit_valida) { this.bit_valida = bit_valida; }

    private double dec_latitud;

    public double getDecLatitud() { return this.dec_latitud; }

    public void setDecLatitud(double dec_latitud) { this.dec_latitud = dec_latitud; }

    private double dec_longitud;

    public double getDecLongitud() { return this.dec_longitud; }

    public void setDecLongitud(double dec_longitud) { this.dec_longitud = dec_longitud; }

    private String id_estatus_posic;

    public String getIdEstatusPosic() { return this.id_estatus_posic; }

    public void setIdEstatusPosic(String id_estatus_posic) { this.id_estatus_posic = id_estatus_posic; }

    private int int_rango_aceptado;

    public int getIntRangoAceptado() { return this.int_rango_aceptado; }

    public void setIntRangoAceptado(int int_rango_aceptado) { this.int_rango_aceptado = int_rango_aceptado; }

    private String va_comentario;

    public String getVaComentario() { return this.va_comentario; }

    public void setVaComentario(String va_comentario) { this.va_comentario = va_comentario; }

    private String va_nombre_pos;

    private String nombre_completo;

    private double distancia;

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getNombrCompleto() {
        return nombre_completo;
    }

    public void setNombreCompleto(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getVaNombrePos() { return this.va_nombre_pos; }

    public void setVaNombrePos(String va_nombre_pos) { this.va_nombre_pos = va_nombre_pos; }

    private String va_numero_pos;

    public String getVaNumeroPos() { return this.va_numero_pos; }

    public void setVaNumeroPos(String va_numero_pos) { this.va_numero_pos = va_numero_pos; }

    private String id_num_empleado;
    public String getIdNumEmpleado() {
        return this.id_num_empleado;
    }
    public void setIdNumEmpleado(String id_num_empleado) {
        this.id_num_empleado = id_num_empleado;
    }



    public String toJson(){
        return new Gson().toJson(this);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipoPosicion);
        dest.writeInt(id_csc_zo_pos);
        dest.writeString(va_descripcion_zn_posic);
        dest.writeByte((byte) (bit_valida ? 1 : 0));
        dest.writeDouble(dec_latitud);
        dest.writeDouble(dec_longitud);
        dest.writeString(id_estatus_posic);
        dest.writeInt(int_rango_aceptado);
        dest.writeString(va_comentario);
        dest.writeString(va_nombre_pos);
        dest.writeString(nombre_completo);
        dest.writeString(va_numero_pos);
        dest.writeString(id_num_empleado);
    }

    @Override
    public boolean equals(Object obj) {
        LugarTrabajo t=(LugarTrabajo)obj;
        return  t.getVaNombrePos().equals(getVaNombrePos());
    }
}
