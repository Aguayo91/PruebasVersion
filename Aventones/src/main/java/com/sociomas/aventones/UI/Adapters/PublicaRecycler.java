package com.sociomas.aventones.UI.Adapters;

/**
 * Created by jromeromar on 18/09/2017.
 */

public class PublicaRecycler {

    public PublicaRecycler(String origen, String destino, String diasem,String hsalida) {
        this.origen = origen;
        this.destino= destino;
        this.diasem=diasem;
        this.hsalida=hsalida;
    }
    private String origen;

    public PublicaRecycler() { }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    private String destino;

    public String getDestino(){return destino;}

    public void setDestino(String destino) { this.destino = destino;}

    private String diasem;

    public String getDiasem() {return diasem;}

    public void setDiasem(String diasem) {this.diasem = diasem;}

    private String hsalida;

    public String getHsalida(){return hsalida;}

    public void setHsalida(String hsalida){this.hsalida=hsalida;}
}
