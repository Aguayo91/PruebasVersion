package com.sociomas.core.WebService.Model.Request.Alta;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jromeromar on 26/09/2017.
 */

public class EditarAutoRequest {
        @SerializedName("id_num_empleado")
        @Expose
        private String idNumEmpleado;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("modelo")
        @Expose
        private String modelo;
        @SerializedName("placas")
        @Expose
        private String placas;
        @SerializedName("codigo_color")
        @Expose
        private String codigoColor;
        @SerializedName("numero_asientos")
        @Expose
        private Integer numeroAsientos;
        @SerializedName("id_automovil")
        @Expose
        private Integer idAutomovil;

        public String getIdNumEmpleado() {
            return idNumEmpleado;
        }

        public void setIdNumEmpleado(String idNumEmpleado) {
            this.idNumEmpleado = idNumEmpleado;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getModelo() {
            return modelo;
        }

        public void setModelo(String modelo) {
            this.modelo = modelo;
        }

        public String getPlacas() {
            return placas;
        }

        public void setPlacas(String placas) {
            this.placas = placas;
        }

        public String getCodigoColor() {
            return codigoColor;
        }

        public void setCodigoColor(String codigoColor) {
            this.codigoColor = codigoColor;
        }

        public Integer getNumeroAsientos() {
            return numeroAsientos;
        }

        public void setNumeroAsientos(Integer numeroAsientos) {
            this.numeroAsientos = numeroAsientos;
        }

        public Integer getIdAutomovil() {
            return idAutomovil;
        }

        public void setIdAutomovil(Integer idAutomovil) {
            this.idAutomovil = idAutomovil;
        }
}
