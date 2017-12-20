package com.sociomas.core.WebService.Model.Response.Pasajeros;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

/**
 * Created by oemy9 on 18/10/2017.
 */

public class PasajerosList {
        @SerializedName("desc_estatus_solicitud")
        @Expose
        private String descEstatusSolicitud;
        @SerializedName("desc_tipo_trayecto")
        @Expose
        private String descTipoTrayecto;
        @SerializedName("id_estatus_solicitud_aventon")
        @Expose
        private Integer idEstatusSolicitudAventon;
        @SerializedName("id_num_empleado")
        @Expose
        private String idNumEmpleado;
        @SerializedName("id_rel_usuario_asientos_reservados")
        @Expose
        private Integer idRelUsuarioAsientosReservados;
        @SerializedName("id_tipo_trayecto")
        @Expose
        private Integer idTipoTrayecto;
        @SerializedName("nombre_empleado")
        @Expose
        private String nombreEmpleado;
        @SerializedName("num_tel_empleado")
        @Expose
        private String numTelEmpleado;

        public String getDescEstatusSolicitud() {
            return descEstatusSolicitud;
        }

        public void setDescEstatusSolicitud(String descEstatusSolicitud) {
            this.descEstatusSolicitud = descEstatusSolicitud;
        }

        public String getDescTipoTrayecto() {
            return descTipoTrayecto;
        }

        public void setDescTipoTrayecto(String descTipoTrayecto) {
            this.descTipoTrayecto = descTipoTrayecto;
        }

        public Integer getIdEstatusSolicitudAventon() {
            return idEstatusSolicitudAventon;
        }

        public void setIdEstatusSolicitudAventon(Integer idEstatusSolicitudAventon) {
            this.idEstatusSolicitudAventon = idEstatusSolicitudAventon;
        }

        public String getIdNumEmpleado() {
            return idNumEmpleado;
        }

        public void setIdNumEmpleado(String idNumEmpleado) {
            this.idNumEmpleado = idNumEmpleado;
        }

        public Integer getIdRelUsuarioAsientosReservados() {
            return idRelUsuarioAsientosReservados;
        }

        public void setIdRelUsuarioAsientosReservados(Integer idRelUsuarioAsientosReservados) {
            this.idRelUsuarioAsientosReservados = idRelUsuarioAsientosReservados;
        }

        public Integer getIdTipoTrayecto() {
            return idTipoTrayecto;
        }

        public void setIdTipoTrayecto(Integer idTipoTrayecto) {
            this.idTipoTrayecto = idTipoTrayecto;
        }

        public String getNombreEmpleado() {
            return nombreEmpleado;
        }

        public void setNombreEmpleado(String nombreEmpleado) {
            this.nombreEmpleado = nombreEmpleado;
        }

        public String getNumTelEmpleado() {
            return numTelEmpleado;
        }

        public void setNumTelEmpleado(String numTelEmpleado) {
            this.numTelEmpleado = numTelEmpleado;
        }
}
