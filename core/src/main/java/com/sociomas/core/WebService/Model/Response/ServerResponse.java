package com.sociomas.core.WebService.Model.Response;

/**
 * Created by oemy9 on 04/04/2017.
 */

@SuppressWarnings("unused")
public class ServerResponse {

    private int resultado;

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    private boolean error;

    public boolean getError() { return this.error; }

    public void setError(boolean error) { this.error = error; }

    private String mensajeError;

    public String getMensajeError() { return this.mensajeError; }

    public void setMensajeError(String mensajeError) { this.mensajeError = mensajeError; }

    private String serverTime;

    public String getServerTime() { return this.serverTime; }

    public void setServerTime(String serverTime) { this.serverTime = serverTime; }

    private String serverUTCTime;

    public String getServerUTCTime() { return this.serverUTCTime; }

    public void setServerUTCTime(String serverUTCTime) { this.serverUTCTime = serverUTCTime; }


    private int dispositivo_activo;

    public int getDispositivoActivo() { return this.dispositivo_activo; }

    public void setDispositivoActivo(int dispositivo_activo) { this.dispositivo_activo = dispositivo_activo; }

    private boolean exito;

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
