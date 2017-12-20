package com.sociomas.core.WebService.Model.Response.Promociones;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by oemy9 on 17/08/2017.
 */

public class PromocionesResponse implements Serializable {
    private String CATE_DESC;

    public String getCATEDESC() { return this.CATE_DESC; }

    public void setCATEDESC(String CATE_DESC) { this.CATE_DESC = CATE_DESC; }

    private String CATE_LLAV_PR;

    public String getCATELLAVPR() { return this.CATE_LLAV_PR; }

    public void setCATELLAVPR(String CATE_LLAV_PR) { this.CATE_LLAV_PR = CATE_LLAV_PR; }

    private String CATE_TOTAL;

    public String getCATETOTAL() { return this.CATE_TOTAL; }

    public void setCATETOTAL(String CATE_TOTAL) { this.CATE_TOTAL = CATE_TOTAL; }

    private String DPUBL_ARCH;

    public String getDPUBLARCH() { return this.DPUBL_ARCH; }

    public void setDPUBLARCH(String DPUBL_ARCH) { this.DPUBL_ARCH = DPUBL_ARCH; }

    private String INTE_DESC;

    public String getINTEDESC() { return this.INTE_DESC; }

    public void setINTEDESC(String INTE_DESC) { this.INTE_DESC = INTE_DESC; }

    private String INTE_LLAV_PR;

    public String getINTELLAVPR() { return this.INTE_LLAV_PR; }

    public void setINTELLAVPR(String INTE_LLAV_PR) { this.INTE_LLAV_PR = INTE_LLAV_PR; }

    private String PUBL_DES;

    public String getPUBLDES() { return this.PUBL_DES; }

    public void setPUBLDES(String PUBL_DES) { this.PUBL_DES = PUBL_DES; }

    private String PUBL_IMAGEN;

    public String getPUBLIMAGEN() { return this.PUBL_IMAGEN; }

    public void setPUBLIMAGEN(String PUBL_IMAGEN) { this.PUBL_IMAGEN = PUBL_IMAGEN; }

    private String PUBL_LIGA;

    public String getPUBLLIGA() { return this.PUBL_LIGA; }

    public void setPUBLLIGA(String PUBL_LIGA) { this.PUBL_LIGA = PUBL_LIGA; }

    private String PUBL_LIGA_DESC;

    public String getPUBLLIGADESC() { return this.PUBL_LIGA_DESC; }

    public void setPUBLLIGADESC(String PUBL_LIGA_DESC) { this.PUBL_LIGA_DESC = PUBL_LIGA_DESC; }

    private String SCAT_DESC;

    public String getSCATDESC() { return this.SCAT_DESC; }

    public void setSCATDESC(String SCAT_DESC) { this.SCAT_DESC = SCAT_DESC; }

    private String SCAT_LLAV_PR;

    public String getSCATLLAVPR() { return this.SCAT_LLAV_PR; }

    public void setSCATLLAVPR(String SCAT_LLAV_PR) { this.SCAT_LLAV_PR = SCAT_LLAV_PR; }

    private String INTE_NOMBRE;

    public String getINTENOMBRE() { return this.INTE_NOMBRE; }

    public void setINTENOMBRE(String INTE_NOMBRE) { this.INTE_NOMBRE = INTE_NOMBRE; }

    private ArrayList<String> IMG_DESCUENTO;

    public ArrayList<String> getIMGDESCUENTO() { return this.IMG_DESCUENTO; }

    public void setIMGDESCUENTO(ArrayList<String> IMG_DESCUENTO) { this.IMG_DESCUENTO = IMG_DESCUENTO; }

    private String NOMBRE_ANEXO;

    public String getNOMBREANEXO() { return this.NOMBRE_ANEXO; }

    public void setNOMBREANEXO(String NOMBRE_ANEXO) { this.NOMBRE_ANEXO = NOMBRE_ANEXO; }

    private String PUBL_COND;

    public String getPUBLCOND() { return this.PUBL_COND; }

    public void setPUBLCOND(String PUBL_COND) { this.PUBL_COND = PUBL_COND; }

}
