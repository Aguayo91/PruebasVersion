package com.sociomas.core.WebService.Model.Response.Ubicaciones;

public class Consolidados {
    private String zonaValidaConsolidada;
    private String fechaEntradaConsolidada;
    private String fechaSalidaConsolidada;
    private boolean isConsolidado=false;

    public boolean isConsolidado() {
        return isConsolidado;
    }

    public void setConsolidado(boolean consolidado) {
        isConsolidado = consolidado;
    }

    public String getZonaValidaConsolidada() {
        return zonaValidaConsolidada;
    }

    public void setZonaValidaConsolidada(String zonaValidaConsolidada) {
        this.zonaValidaConsolidada = zonaValidaConsolidada;
    }

    public String getFechaEntradaConsolidada() {
        return fechaEntradaConsolidada;
    }

    public void setFechaEntradaConsolidada(String fechaEntradaConsolidada) {
        this.fechaEntradaConsolidada = fechaEntradaConsolidada;
    }

    public String getFechaSalidaConsolidada() {
        return fechaSalidaConsolidada;
    }

    public void setFechaSalidaConsolidada(String fechaSalidaConsolidada) {
        this.fechaSalidaConsolidada = fechaSalidaConsolidada;
    }
}

