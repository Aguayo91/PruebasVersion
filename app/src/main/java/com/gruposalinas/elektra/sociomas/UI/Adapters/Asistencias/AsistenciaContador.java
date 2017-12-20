package com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias;
import com.gruposalinas.elektra.sociomas.R;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by oemy9 on 16/08/2017.
 */

public class AsistenciaContador {

    private static final String SALIDA_ANTES="salidaantes";
    private static final String DESPUES_HR_LIMITE="despueshrlimite";
    private static final String ASISTIO="asistio";
    private static final String NO_ASISTIO="noasistio";
    private static final String NO_TERMINA="notermina";
    private static final String RETARDO="retardo";
    private HashMap<String,Integer>hashIconos=new HashMap<>();

    /*CONTADORES DE STATUS*/
    private int salidaAntes;
    private int despuesHrLimite;
    private int asistio;
    private int noAsistio;
    private int noTermina;
    private int retardo;

    public HashMap<String, Integer> getHashIconos() {
        return hashIconos;
    }

    public AsistenciaContador(){
        hashIconos.put(SALIDA_ANTES,R.drawable.ic_leave);
        hashIconos.put(RETARDO,R.drawable.retardo);
        hashIconos.put(DESPUES_HR_LIMITE,R.drawable.ic_timelimit);
        hashIconos.put(ASISTIO,R.drawable.ic_permisos);
        hashIconos.put(NO_ASISTIO,R.drawable.ic_lack);
        hashIconos.put(NO_TERMINA, R.drawable.notermina);
    }

    public void   countStatus(String status){
        switch (status.toLowerCase(Locale.ENGLISH)){
            case SALIDA_ANTES:
                salidaAntes++;
                break;
            case RETARDO:
                retardo++;
                break;
            case DESPUES_HR_LIMITE:
                despuesHrLimite++;
                break;
            case ASISTIO:
                asistio++;
                break;
            case NO_ASISTIO:
                noAsistio++;
                break;
            case NO_TERMINA:
                noTermina++;
                break;
        }
    }

    public int getSalidaAntes() {
        return salidaAntes;
    }

    public int getDespuesHrLimite() {
        return despuesHrLimite;
    }

    public int getAsistio() {
        return asistio;
    }

    public int getNoAsistio() {
        return noAsistio;
    }

    public int getNoTermina() {
        return noTermina;
    }

    public int getRetardo() {
        return retardo;
    }

    public void setRetardo(int retardo) {
        this.retardo = retardo;
    }
}

