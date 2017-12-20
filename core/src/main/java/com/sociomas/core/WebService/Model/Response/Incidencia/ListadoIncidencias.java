package com.sociomas.core.WebService.Model.Response.Incidencia;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;

import java.io.Serializable;

/**
 * Created by oemy9 on 15/05/2017.
 */

@SuppressWarnings("unused")
public class ListadoIncidencias implements Serializable, Parcelable {
    /*PROPIEDADES PARA LA PRESENTACIÓN DE LAYOT*/
    private boolean botonClick;
    private boolean descargada;
    private Bitmap selectedBitmap;

    private String horaEntrada;
    private String horaSalida;
    private String fecha;
    private String diaNombre;
    private int recursoImagen;
    private int recursoColor;

    protected ListadoIncidencias(Parcel in) {
        botonClick = in.readByte() != 0;
        descargada = in.readByte() != 0;
        selectedBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        horaEntrada = in.readString();
        horaSalida = in.readString();
        fecha = in.readString();
        diaNombre = in.readString();
        recursoImagen = in.readInt();
        recursoColor = in.readInt();
        idTipoIncidencia = in.readInt();
        FechaSalidaOcurrencia = in.readString();
        nombre_supervisor = in.readString();
        id_supervisor = in.readString();
        uriImagen = in.readParcelable(Uri.class.getClassLoader());
        CSC = in.readInt();
        Empleado = in.readString();
        EstatusJustificacion = in.readString();
        FechaOcurrencia = in.readString();
        FechaReporte = in.readString();
        Incidencia = in.readString();
        Justificada = in.readByte() != 0;
        Nombre = in.readString();
        Adjunto = in.readString();
        Comentario_Rechazo = in.readString();
        Comentarios = in.readString();
        IdJustificacion = in.readInt();
        autorizados = in.readInt();
        sinJustificar = in.readInt();
        pendientes = in.readInt();
    }

    public static final Creator<ListadoIncidencias> CREATOR = new Creator<ListadoIncidencias>() {
        @Override
        public ListadoIncidencias createFromParcel(Parcel in) {
            return new ListadoIncidencias(in);
        }

        @Override
        public ListadoIncidencias[] newArray(int size) {
            return new ListadoIncidencias[size];
        }
    };

    public String getDiaNombre() {
        return diaNombre;
    }

    public void setDiaNombre(String diaNombre) {
        this.diaNombre = diaNombre;
    }

    public int getRecursoColor() {
        return recursoColor;
    }

    public void setRecursoColor(int recursoColor) {
        this.recursoColor = recursoColor;
    }

    public int getRecursoImagen() {
        return recursoImagen;
    }

    public void setRecursoImagen(int recursoImagen) {
        this.recursoImagen = recursoImagen;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @SerializedName("id_tipo_incid")
    private int idTipoIncidencia;
    @SerializedName("fecha_salida_ocurrencia")
    private String FechaSalidaOcurrencia;

    @SerializedName("nombre_supervisor")
    private String nombre_supervisor;

    @SerializedName("id_supervisor")
    private String id_supervisor;


    public String getId_supervisor() {
        return id_supervisor;
    }

    public void setId_supervisor(String id_supervisor) {
        this.id_supervisor = id_supervisor;
    }

    public String getNombreSupervisor() {
        return nombre_supervisor;
    }

    public void setNombre_supervisor(String nombre_supervisor) {
        this.nombre_supervisor = nombre_supervisor;
    }

    public String getFechaSalidaOcurrencia() {
        return FechaSalidaOcurrencia;
    }

    public void setFechaSalidaOcurrencia(String fechaSalidaOcurrencia) {
        FechaSalidaOcurrencia = fechaSalidaOcurrencia;
    }


    public Bitmap getSelectedBitmap() {
        return selectedBitmap;
    }

    public void setSelectedBitmap(Bitmap selectedBitmap) {
        this.selectedBitmap = selectedBitmap;
    }

    private Uri uriImagen;
    public Uri getUriImagen() {
        return uriImagen;
    }
    public void setUriImagen(Uri uriImagen) {
        this.uriImagen = uriImagen;
    }
    public boolean isDescargada() {
        return descargada;
    }
    public void setDescargada(boolean descargada) {
        this.descargada = descargada;
    }
    public boolean isBotonClick() {
        return botonClick;
    }
    public void setBotonClick(boolean botonClick) {
        this.botonClick = botonClick;
    }

    public int getIdTipoIncidencia() {
        return idTipoIncidencia;
    }

    public void setIdTipoIncidencia(int idTipoIncidencia) {
        this.idTipoIncidencia = idTipoIncidencia;
    }

    private int CSC;

    public int getCSC() { return this.CSC; }

    public void setCSC(int CSC) { this.CSC = CSC; }

    private String Empleado;

    public String getEmpleado() { return this.Empleado; }

    public void setEmpleado(String Empleado) { this.Empleado = Empleado; }

    private String EstatusJustificacion;

    public String getEstatusJustificacion() { return this.EstatusJustificacion; }

    public void setEstatusJustificacion(String EstatusJustificacion) { this.EstatusJustificacion = EstatusJustificacion; }

    private String FechaOcurrencia;

    public String getFechaOcurrencia() { return this.FechaOcurrencia; }

    public void setFechaOcurrencia(String FechaOcurrencia) { this.FechaOcurrencia = FechaOcurrencia; }

    private String FechaReporte;

    public String getFechaReporte() { return this.FechaReporte; }

    public void setFechaReporte(String FechaReporte) { this.FechaReporte = FechaReporte; }

    private String Incidencia;

    public String getIncidencia() {
        //return EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE.toString().equals(this.Incidencia)?
          //  EnumAsistencia.RETARDO.toString():this.Incidencia;

        return  this.Incidencia;


    }

    public void setIncidencia(String Incidencia) { this.Incidencia = Incidencia; }

    private boolean Justificada;

    public boolean getJustificada() { return this.Justificada; }

    public void setJustificada(boolean Justificada) { this.Justificada = Justificada; }

    private String Nombre;

    public String getNombre() { return this.Nombre; }

    public void setNombre(String Nombre) { this.Nombre = Nombre; }

    private String Adjunto;

    public String getAdjunto() { return this.Adjunto; }

    public void setAdjunto(String Adjunto) { this.Adjunto = Adjunto; }



    private String Comentario_Rechazo;


    public String getComentarioRechazo() { return this.Comentario_Rechazo; }

    public void setComentarioRechazo(String Comentario_Rechazo) { this.Comentario_Rechazo = Comentario_Rechazo; }

    private String Comentarios;

    public String getComentarios() { return this.Comentarios; }

    public void setComentarios(String Comentarios) { this.Comentarios = Comentarios; }


    private int IdJustificacion;

    public int getIdJustificacion() { return this.IdJustificacion; }

    public void setIdJustificacion(int IdJustificacion) { this.IdJustificacion = IdJustificacion; }


    private int autorizados;
    private int sinJustificar;
    private int pendientes;

    public int getAutorizados() {
        return autorizados;
    }

    public void setAutorizados(int autorizados) {
        this.autorizados = autorizados;
    }

    public int getSinJustificar() {
        return sinJustificar;
    }

    public void setSinJustificar(int sinJustificar) {
        this.sinJustificar = sinJustificar;
    }

    public int getPendientes() {
        return pendientes;
    }

    public void setPendientes(int pendientes) {
        this.pendientes = pendientes;
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
        dest.writeByte((byte) (botonClick ? 1 : 0));
        dest.writeByte((byte) (descargada ? 1 : 0));
        dest.writeParcelable(selectedBitmap, flags);
        dest.writeString(horaEntrada);
        dest.writeString(horaSalida);
        dest.writeString(fecha);
        dest.writeString(diaNombre);
        dest.writeInt(recursoImagen);
        dest.writeInt(recursoColor);
        dest.writeInt(idTipoIncidencia);
        dest.writeString(FechaSalidaOcurrencia);
        dest.writeString(nombre_supervisor);
        dest.writeString(id_supervisor);
        dest.writeParcelable(uriImagen, flags);
        dest.writeInt(CSC);
        dest.writeString(Empleado);
        dest.writeString(EstatusJustificacion);
        dest.writeString(FechaOcurrencia);
        dest.writeString(FechaReporte);
        dest.writeString(Incidencia);
        dest.writeByte((byte) (Justificada ? 1 : 0));
        dest.writeString(Nombre);
        dest.writeString(Adjunto);
        dest.writeString(Comentario_Rechazo);
        dest.writeString(Comentarios);
        dest.writeInt(IdJustificacion);
        dest.writeInt(autorizados);
        dest.writeInt(sinJustificar);
        dest.writeInt(pendientes);
    }
}
