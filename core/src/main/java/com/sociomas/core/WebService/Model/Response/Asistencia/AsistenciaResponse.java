package com.sociomas.core.WebService.Model.Response.Asistencia;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 11/08/2017.
 */

public class AsistenciaResponse extends ServerResponse {
    private ArrayList<ResultadoAsistencia> Resultado;
    public ArrayList<ResultadoAsistencia> getResultadoAsistencia() {return Resultado;}
    private ArrayList<ResultadoAsistencia>DiaActual;
    public ArrayList<ResultadoAsistencia> getDiaActual() {return DiaActual;}
    public void setResultado(ArrayList<ResultadoAsistencia> Resultado) { this.Resultado = Resultado; }
    public void setDiaActual(ArrayList<ResultadoAsistencia> diaActual) {DiaActual = diaActual;}

}
