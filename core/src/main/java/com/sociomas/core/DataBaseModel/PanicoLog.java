package com.sociomas.core.DataBaseModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by oemy9 on 04/08/2017.
 */
@DatabaseTable(tableName = "panicoLog")
public class PanicoLog {
    public static final String ID           = "id";
    public static final String SUCCESS = "success";
    public static final String FECHA		= "fecha";
    public static final String HORA="hora";
    public static final String PATH="path";
    public static final String MENSAJE_ERROR="mensaje_error";
    public static final String MENSAJE_ERROR_APP="mensaje_error_app";


    @DatabaseField(generatedId = true, columnName =ID)
    private int id;
    @DatabaseField(columnName = SUCCESS)
    private boolean success;
    @DatabaseField(columnName = PATH)
    private String path;
    @DatabaseField(columnName = FECHA)
    private String fecha;
    @DatabaseField(columnName = MENSAJE_ERROR)
    private String mensajeError;
    @DatabaseField(columnName = MENSAJE_ERROR_APP)
    private String mensajeErrorApp;
    @DatabaseField(columnName = HORA)
    private String hora;

    public String getHora() {return hora;
    }
    public void setHora(String hora) {this.hora = hora;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getMensajeError() {
        return mensajeError;
    }
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    public String getMensajeErrorApp() {return mensajeErrorApp;}
    public void setMensajeErrorApp(String mensajeErrorApp) {this.mensajeErrorApp = mensajeErrorApp;}
    private boolean isPlaying=false;
    public boolean isPlaying() {return isPlaying;}
    public void setPlaying(boolean playing) {isPlaying = playing;}

}
