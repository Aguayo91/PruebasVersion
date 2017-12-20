package com.sociomas.core.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by oemy9 on 07/12/2017.
 */
@DatabaseTable(tableName = "legal")
public class Legal
{
    public static final String ID="_id";
    public static final String PRIVACIDAD="privacidad";
    public static final String TERMINOS="terminos";
    public static final String VESION_TERMINOS="version";
    public static final String TITULO_PRIVACIDAD="titulo_privacidad";
    public static final String TITULO_TERMINOS="titulo_terminos";
    public static final String SUBTITULO="subtitulo";

    @DatabaseField(columnName = ID, unique = true, generatedId = true)
    private int id;
    @DatabaseField(columnName =PRIVACIDAD)
    private String pivacidad;
    @DatabaseField(columnName = TERMINOS)
    private String terminos;
    @DatabaseField(columnName = VESION_TERMINOS)
    private double version;
    @DatabaseField(columnName = TITULO_PRIVACIDAD)
    private String tituloPrivacidad;
    @DatabaseField(columnName =TITULO_TERMINOS)
    private String tituloTerminos;
    @DatabaseField(columnName = SUBTITULO)
    private String subtitulo;



    public String getTituloPrivacidad() {
        return tituloPrivacidad;
    }

    public void setTituloPrivacidad(String tituloPrivacidad) {
        this.tituloPrivacidad = tituloPrivacidad;
    }

    public String getTituloTerminos() {
        return tituloTerminos;
    }

    public void setTituloTerminos(String tituloTerminos) {
        this.tituloTerminos = tituloTerminos;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPivacidad() {
        return pivacidad;
    }

    public void setPivacidad(String pivacidad) {
        this.pivacidad = pivacidad;
    }

    public String getTerminos() {
        return terminos;
    }

    public void setTerminos(String terminos) {
        this.terminos = terminos;
    }
}
