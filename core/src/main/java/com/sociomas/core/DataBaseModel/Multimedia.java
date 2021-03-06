package com.sociomas.core.DataBaseModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Adrian Guzman on 23/06/2016.
 */
@SuppressWarnings("unused")
public class Multimedia extends BaseBean implements Serializable {

    String id_numero_empleado;
    String nombre_archivo;
    String tamano;
    String extension;
    String archivo;

    String contactos;
    String contacto2;
    String contacto3;
    ArrayList<String> listacontacos;
    ArrayList <Multimedia> multimediaArrayList;
    String coordenadas;
    ArrayList<String> codigo_internacional;

    public ArrayList<String> getCodigo_internacional() {
        return codigo_internacional;
    }

    public void setCodigo_internacional(ArrayList<String> codigo_internacional) {
        this.codigo_internacional = codigo_internacional;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public ArrayList<Multimedia> getMultimediaArrayList() {
        return multimediaArrayList;
    }

    public void setMultimediaArrayList(ArrayList<Multimedia> multimediaArrayList) {
        this.multimediaArrayList = multimediaArrayList;
    }

    public ArrayList<String> getListacontacos() {
        return listacontacos;
    }

    public void setListacontacos(ArrayList<String> listacontacos) {
        this.listacontacos = listacontacos;
    }

    public String getContactos() {
        return contactos;
    }

    public void setContactos(String contactos) {
        this.contactos = contactos;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }

    public String getContacto3() {
        return contacto3;
    }

    public void setContacto3(String contacto3) {
        this.contacto3 = contacto3;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getId_numero_empleado() {
        return id_numero_empleado;
    }

    public void setId_numero_empleado(String id_numero_empleado) {
        this.id_numero_empleado = id_numero_empleado;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
