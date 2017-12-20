package com.sociomas.core.WebService.Model.Response.Ubicaciones;

/**
 * Created by oemy9 on 21/06/2017.
 */

public class IndicadorUbicacion {
    private int totalSocios;
    private int validos;
    private int noActualizados;
    private int otros;

    public void setValidos(int validos) {
        this.validos = validos;
    }

    public void setTotalSocios(int totalSocios) {
        this.totalSocios = totalSocios;
    }

    public void setNoActualizados(int noActualizados) {
        this.noActualizados = noActualizados;
    }

    public void setOtros(int otros) {
        this.otros = otros;
    }

    public void setByMovimiento(Ubicaciones movimiento){
        if(movimiento.getMasDeUnDiaSinReportar()==1){
            noActualizados++;
        }
        else if(movimiento.getPosicionValida()==1 && movimiento.getMasDeUnDiaSinReportar()==0){
            validos++;
        }
        else if(movimiento.getPosicionValida()!=1  && movimiento.getMasDeUnDiaSinReportar()==0){
            otros++;
        }
    }

    public int getTotalSocios() {
        return totalSocios;
    }

    public int getValidos() {
        return validos;
    }

    public int getNoActualizados() {
        return noActualizados;
    }

    public int getOtros() {
        return otros;
    }
}
