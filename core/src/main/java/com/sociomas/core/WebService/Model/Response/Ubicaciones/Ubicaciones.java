package com.sociomas.core.WebService.Model.Response.Ubicaciones;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by oemy9 on 25/04/2017.
 */

@SuppressWarnings("unused")
public class Ubicaciones extends Consolidados implements Serializable {



        private String Accuracy;

        public String getAccuracy() { return this.Accuracy; }

        public void setAccuracy(String Accuracy) { this.Accuracy = Accuracy; }

        private String Actividad;

        public String getActividad() { return this.Actividad; }

        public void setActividad(String Actividad) { this.Actividad = Actividad; }

        private String Bateria;

        public String getBateria() { return this.Bateria; }

        public void setBateria(String Bateria) { this.Bateria = Bateria; }

        private String FechaHora;

        public String getFechaHora() { return FechaHora; }

        public void setFechaHora(String FechaHora) { this.FechaHora = FechaHora; }

        private String HoraEntrada;

        public String getHoraEntrada() { return this.HoraEntrada; }

        public void setHoraEntrada(String HoraEntrada) { this.HoraEntrada = HoraEntrada; }

        private String HoraSalida;

        public String getHoraSalida() { return this.HoraSalida; }

        public void setHoraSalida(String HoraSalida) { this.HoraSalida = HoraSalida; }

        private String Id_Num_Empleado;

        public String getIdNumEmpleado() { return this.Id_Num_Empleado; }

        public void setIdNumEmpleado(String Id_Num_Empleado) { this.Id_Num_Empleado = Id_Num_Empleado; }

        private double Latitud;

        public double getLatitud() { return this.Latitud; }

        public void setLatitud(double Latitud) { this.Latitud = Latitud; }

        private double Longitud;

        public double getLongitud() { return this.Longitud; }

        public void setLongitud(double Longitud) { this.Longitud = Longitud; }

        private int MasDeUnDiaSinReportar;

        public int getMasDeUnDiaSinReportar() { return this.MasDeUnDiaSinReportar; }

        public void setMasDeUnDiaSinReportar(int MasDeUnDiaSinReportar) { this.MasDeUnDiaSinReportar = MasDeUnDiaSinReportar; }

        private String Nombre;

        public String getNombre() { return this.Nombre; }

        public void setNombre(String Nombre) { this.Nombre = Nombre; }

        private String NombrePosicionValida;

        public String getNombrePosicionValida() { return this.NombrePosicionValida; }

        public void setNombrePosicionValida(String NombrePosicionValida) { this.NombrePosicionValida = NombrePosicionValida; }

        private int PosicionValida;

        public int getPosicionValida() { return this.PosicionValida; }

        public void setPosicionValida(int PosicionValida) { this.PosicionValida = PosicionValida; }

        private int Proveedor;

        public int getProveedor() { return this.Proveedor; }

        public void setProveedor(int Proveedor) { this.Proveedor = Proveedor; }

        private String TipoDispositivo;

        public String getTipoDispositivo() { return this.TipoDispositivo; }

        public void setTipoDispositivo(String TipoDispositivo) { this.TipoDispositivo = TipoDispositivo; }

        private String Velocidad;

        public String getVelocidad() { return this.Velocidad; }

        public void setVelocidad(String Velocidad) { this.Velocidad = Velocidad; }

        public String toJson(){
                return new Gson().toJson(this);
        }


}
