package com.sociomas.core.WebService.Model.Request.SolicitarAventon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jmarquezu on 17/10/2017.
 */

public class AventonesReservadosRequest {

        @SerializedName("id_num_empleado")
        @Expose
        private String idNumEmpleado;

        @SerializedName("token")
        @Expose
        private String token;

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

    }
