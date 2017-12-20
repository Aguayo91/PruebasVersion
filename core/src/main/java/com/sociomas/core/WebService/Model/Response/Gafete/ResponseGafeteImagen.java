package com.sociomas.core.WebService.Model.Response.Gafete;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

/**
 * Created by oemy9 on 22/11/2017.
 */

public class ResponseGafeteImagen extends ServerResponse {


    private boolean isGrupoSalinas;

    @SerializedName("img_background")
    @Expose
    public String imgBackground;
    @SerializedName("img_code_qr")
    @Expose
    public String imgCodeQr;
    @SerializedName("img_foto")
    @Expose
    public String imgFoto;
    @SerializedName("img_frame_pic")
    @Expose
    public String imgFramePic;
    @SerializedName("img_icono")
    @Expose
    public String imgIcono;
    @SerializedName("img_logo_bg_gafete")
    @Expose
    public String imgLogoBgGafete;
    @SerializedName("img_logo_gs")
    @Expose
    public String imgLogoGs;
    @SerializedName("img_logo_negocio")
    @Expose
    public String imgLogoNegocio;
    @SerializedName("img_pleca_baja")
    @Expose
    public String imgPlecaBaja;

    @SerializedName("img_pleca_media")
    @Expose
    public String imgPlecaMedia;
    @SerializedName("img_pleca_alta")
    public String imgPlecaAlta;
    @SerializedName("nombre_empleado")
    @Expose
    public String nombreEmpleado;
    @SerializedName("puesto_empleado")
    @Expose
    public String puestoEmpleado;

    @SerializedName("existe_foto")
    @Expose
    private boolean existeFoto;

    public boolean isExisteFoto() {
        return existeFoto;
    }

    public void setExisteFoto(boolean existeFoto) {
        this.existeFoto = existeFoto;
    }

    public String getImgBackground() {
        return imgBackground;
    }

    public void setImgBackground(String imgBackground) {
        this.imgBackground = imgBackground;
    }

    public String getImgCodeQr() {
        return imgCodeQr;
    }

    public void setImgCodeQr(String imgCodeQr) {
        this.imgCodeQr = imgCodeQr;
    }

    public String getImgFoto() {
        return imgFoto;
    }

    public void setImgFoto(String imgFoto) {
        this.imgFoto = imgFoto;
    }

    public String getImgFramePic() {
        return imgFramePic;
    }

    public void setImgFramePic(String imgFramePic) {
        this.imgFramePic = imgFramePic;
    }

    public String getImgIcono() {
        return imgIcono;
    }

    public void setImgIcono(String imgIcono) {
        this.imgIcono = imgIcono;
    }

    public String getImgLogoBgGafete() {
        return imgLogoBgGafete;
    }

    public void setImgLogoBgGafete(String imgLogoBgGafete) {
        this.imgLogoBgGafete = imgLogoBgGafete;
    }

    public String getImgLogoGs() {
        return imgLogoGs;
    }

    public void setImgLogoGs(String imgLogoGs) {
        this.imgLogoGs = imgLogoGs;
    }

    public String getImgLogoNegocio() {
        return imgLogoNegocio;
    }

    public void setImgLogoNegocio(String imgLogoNegocio) {
        this.imgLogoNegocio = imgLogoNegocio;
    }

    public String getImgPlecaBaja() {
        return imgPlecaBaja;
    }

    public void setImgPlecaBaja(String imgPlecaBaja) {
        this.imgPlecaBaja = imgPlecaBaja;
    }

    public String getImgPlecaMedia() {
        return imgPlecaMedia;
    }

    public void setImgPlecaMedia(String imgPlecaMedia) {
        this.imgPlecaMedia = imgPlecaMedia;
    }

    public String getImgPlecaAlta() {
        return imgPlecaAlta;
    }

    public void setImgPlecaAlta(String imgPlecaAlta) {
        this.imgPlecaAlta = imgPlecaAlta;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getPuestoEmpleado() {
        return puestoEmpleado;
    }

    public void setPuestoEmpleado(String puestoEmpleado) {
        this.puestoEmpleado = puestoEmpleado;
    }

    public boolean isGrupoSalinas() {
        if(TextUtils.isEmpty(getImgLogoGs())|| TextUtils.isEmpty(getImgLogoNegocio())){
            return  false;
        }
        else {
           return  getImgLogoGs().substring(0, 20).equals(getImgLogoNegocio().substring(0, 20));
        }
    }

    public void setGrupoSalinas(boolean grupoSalinas) {
        isGrupoSalinas = grupoSalinas;
    }
}
