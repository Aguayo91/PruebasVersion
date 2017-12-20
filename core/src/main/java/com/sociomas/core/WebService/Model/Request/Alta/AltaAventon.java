package com.sociomas.core.WebService.Model.Request.Alta;

/**
 * Created by jromeromar on 06/10/2017.
 */

public class AltaAventon {

    private String imagen;
    private String status;

    public AltaAventon(){

    }
    public AltaAventon(String imagen,String status){
        this.imagen=imagen;
        this.status=status;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
