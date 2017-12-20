package com.sociomas.core.WebService.Model.Request.Contacto;

/**
 * Created by oemy9 on 10/05/2017.
 */

@SuppressWarnings("unused")
public class RootConfiguracion {

        private String version_sistema_operativo;

        public String getVersionSistemaOperativo() { return this.version_sistema_operativo; }

        public void setVersionSistemaOperativo(String version_sistema_operativo) { this.version_sistema_operativo = version_sistema_operativo; }

        private String sistema;

        public String getSistema() { return this.sistema; }

        public void setSistema(String sistema) { this.sistema = sistema; }

        private String id_dispositivo;

        public String getIdDispositivo() { return this.id_dispositivo; }

        public void setIdDispositivo(String id_dispositivo) { this.id_dispositivo = id_dispositivo; }

        private String id_num_empleado;

        public String getIdNumEmpleado() { return this.id_num_empleado; }

        public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }

        private String version_actual;

        public String getVersionActual() { return this.version_actual; }

        public void setVersionActual(String version_actual) { this.version_actual = version_actual; }

        private String token;

        public String getToken() { return this.token; }

        public void setToken(String token) { this.token = token; }

        private String modelo_celular;

        public String getModeloCelular() { return this.modelo_celular; }

        public void setModeloCelular(String modelo_celular) { this.modelo_celular = modelo_celular; }

        private String va_numero_telefono;

        public String getVa_numero_telefono() {
                return va_numero_telefono;
        }

        public void setVa_numero_telefono(String va_numero_telefono) {
                this.va_numero_telefono = va_numero_telefono;
        }
}
