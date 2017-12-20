package com.sociomas.core.WebService.Model.Response.Asistencia;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oemy9 on 11/08/2017.
 */

public class ResultadoAsistencia implements Parcelable {

    /*Implementado por mi OSCAR*/
    private String FechaReporte;


    public ResultadoAsistencia(){

    }

    public ResultadoAsistencia(boolean header) {
        this.header = header;
    }

    protected ResultadoAsistencia(Parcel in) {
        FechaReporte = in.readString();
        ColorClase = in.readString();
        Comentario = in.readString();
        HoraEntrada = in.readString();
        HoraEntradaCompromiso = in.readString();
        HoraEntradaDate = in.readString();
        HoraSalida = in.readString();
        HoraSalidaCompromiso = in.readString();
        IdEstado = in.readInt();
        IdTipoDispositivo = in.readString();
        Imagen = in.readString();
        ModeloDispositivo = in.readString();
        NombreEmpleado = in.readString();
        NumeroEmpleado = in.readString();
        PosicionEntrada = in.readString();
        PosicionSalida = in.readString();
        header = in.readByte() != 0;
    }

    public static final Creator<ResultadoAsistencia> CREATOR = new Creator<ResultadoAsistencia>() {
        @Override
        public ResultadoAsistencia createFromParcel(Parcel in) {
            return new ResultadoAsistencia(in);
        }

        @Override
        public ResultadoAsistencia[] newArray(int size) {
            return new ResultadoAsistencia[size];
        }
    };

    public String getFechaReporte() {return FechaReporte;}

    public void setFechaReporte(String fechaReporte) {
        FechaReporte = fechaReporte;
    }

    private String ColorClase;

    public String getColorClase() { return this.ColorClase; }

    public void setColorClase(String ColorClase) { this.ColorClase = ColorClase; }

    private String Comentario;

    public String getComentario() { return this.Comentario; }

    public void setComentario(String Comentario) { this.Comentario = Comentario; }

    private String HoraEntrada;

    public String getHoraEntrada() { return this.HoraEntrada; }

    public void setHoraEntrada(String HoraEntrada) { this.HoraEntrada = HoraEntrada; }

    private String HoraEntradaCompromiso;

    public String getHoraEntradaCompromiso() { return this.HoraEntradaCompromiso; }

    public void setHoraEntradaCompromiso(String HoraEntradaCompromiso) { this.HoraEntradaCompromiso = HoraEntradaCompromiso; }

    private String HoraEntradaDate;

    public String getHoraEntradaDate() { return this.HoraEntradaDate; }

    public void setHoraEntradaDate(String HoraEntradaDate) { this.HoraEntradaDate = HoraEntradaDate; }

    private String HoraSalida;

    public String getHoraSalida() { return this.HoraSalida; }

    public void setHoraSalida(String HoraSalida) { this.HoraSalida = HoraSalida; }

    private String HoraSalidaCompromiso;

    public String getHoraSalidaCompromiso() { return this.HoraSalidaCompromiso; }

    public void setHoraSalidaCompromiso(String HoraSalidaCompromiso) { this.HoraSalidaCompromiso = HoraSalidaCompromiso; }

    private int IdEstado;

    public int getIdEstado() { return this.IdEstado; }

    public void setIdEstado(int IdEstado) { this.IdEstado = IdEstado; }

    private String IdTipoDispositivo;

    public String getIdTipoDispositivo() { return this.IdTipoDispositivo; }

    public void setIdTipoDispositivo(String IdTipoDispositivo) { this.IdTipoDispositivo = IdTipoDispositivo; }

    private String Imagen;

    public String getImagen() { return this.Imagen; }

    public void setImagen(String Imagen) { this.Imagen = Imagen; }

    private String ModeloDispositivo;

    public String getModeloDispositivo() { return this.ModeloDispositivo; }

    public void setModeloDispositivo(String ModeloDispositivo) { this.ModeloDispositivo = ModeloDispositivo; }

    private String NombreEmpleado;

    public String getNombreEmpleado() { return this.NombreEmpleado; }

    public void setNombreEmpleado(String NombreEmpleado) { this.NombreEmpleado = NombreEmpleado; }

    private String NumeroEmpleado;

    public String getNumeroEmpleado() { return this.NumeroEmpleado; }

    public void setNumeroEmpleado(String NumeroEmpleado) { this.NumeroEmpleado = NumeroEmpleado; }

    private String PosicionEntrada;

    public String getPosicionEntrada() { return this.PosicionEntrada; }

    public void setPosicionEntrada(String PosicionEntrada) { this.PosicionEntrada = PosicionEntrada; }

    private String PosicionSalida;

    public String getPosicionSalida() { return this.PosicionSalida; }

    public void setPosicionSalida(String PosicionSalida) { this.PosicionSalida = PosicionSalida; }

    private boolean header;

    public boolean isHeader() {
        return this.header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(FechaReporte);
        dest.writeString(ColorClase);
        dest.writeString(Comentario);
        dest.writeString(HoraEntrada);
        dest.writeString(HoraEntradaCompromiso);
        dest.writeString(HoraEntradaDate);
        dest.writeString(HoraSalida);
        dest.writeString(HoraSalidaCompromiso);
        dest.writeInt(IdEstado);
        dest.writeString(IdTipoDispositivo);
        dest.writeString(Imagen);
        dest.writeString(ModeloDispositivo);
        dest.writeString(NombreEmpleado);
        dest.writeString(NumeroEmpleado);
        dest.writeString(PosicionEntrada);
        dest.writeString(PosicionSalida);
        dest.writeByte((byte)(header ? 1 : 0));
    }

    @Override
    public String toString() {
        return "ResultadoAsistencia{" +
                "FechaReporte='" + FechaReporte + '\'' +
                ", ColorClase='" + ColorClase + '\'' +
                ", Comentario='" + Comentario + '\'' +
                ", HoraEntrada='" + HoraEntrada + '\'' +
                ", HoraEntradaCompromiso='" + HoraEntradaCompromiso + '\'' +
                ", HoraEntradaDate='" + HoraEntradaDate + '\'' +
                ", HoraSalida='" + HoraSalida + '\'' +
                ", HoraSalidaCompromiso='" + HoraSalidaCompromiso + '\'' +
                ", IdEstado=" + IdEstado +
                ", IdTipoDispositivo='" + IdTipoDispositivo + '\'' +
                ", Imagen='" + Imagen + '\'' +
                ", ModeloDispositivo='" + ModeloDispositivo + '\'' +
                ", NombreEmpleado='" + NombreEmpleado + '\'' +
                ", NumeroEmpleado='" + NumeroEmpleado + '\'' +
                ", PosicionEntrada='" + PosicionEntrada + '\'' +
                ", PosicionSalida='" + PosicionSalida + '\'' +
                ", header=" + header +
                '}';
    }
}
