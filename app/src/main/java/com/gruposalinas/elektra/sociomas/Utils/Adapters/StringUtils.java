package com.gruposalinas.elektra.sociomas.Utils.Adapters;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by oemy9 on 08/08/2017.
 */

public   class StringUtils{

    private static final String TAG = "StringUtils";
    List<String> preposiciones = new ArrayList<String>();

    public StringUtils(){
        preposiciones.add("DEL");
        preposiciones.add("DE");
        preposiciones.add("LOS");
        preposiciones.add("LA");
        preposiciones.add("SAN");
        preposiciones.add("SANTA");
    }

    public String getNombreSeparado(String nombre){
        List<String> listNombres=getNombreSeparadoInternal(nombre);
        StringBuilder stringBuilderNombre=new StringBuilder();
        int count=0;
        for(String n: listNombres){
            if(count>0){stringBuilderNombre.append("\t");}
            stringBuilderNombre.append(n);
            count++;
        }
        return  stringBuilderNombre.toString();
    }

    public   List<String> getNombreSeparadoInternal(String nombre){

        if(nombre==null){
            Log.d(TAG, "getNombreSeparadoInternal:  null" );
            return null;
        }

        int contador = 0;
        List<String> texto = new ArrayList<String>();
        List<String> lista = Arrays.asList(nombre.split(" "));

        try {
            String textoAux = "";
            lista.remove("");
            if (lista.size() == 3) {
                for (String value : lista) {
                    if (contador == 0)
                        texto.add(value);
                    else {
                        textoAux = textoAux + value + " ";
                    }
                    contador++;
                    if (contador == lista.size())
                        texto.add(textoAux);
                }
            }
            else
            {
                boolean pase = false;
                for(String value: lista)
                {
                    boolean palabraPrepo = false;
                    for (String reg: preposiciones)
                    {
                        if (value.equalsIgnoreCase(reg))
                            palabraPrepo = true;
                    }

                    if (palabraPrepo)
                    {
                        if (contador < 2)
                            contador--;
                    }
                    contador++;
                    if (contador <= 2)
                    {
                        textoAux = textoAux + " " + value;
                    }
                    else
                    {
                        if (pase)
                        {
                            textoAux = textoAux + " " + value;
                        }
                        else
                        {
                            texto.add(textoAux.trim());
                            textoAux = "";
                            textoAux = textoAux + " " + value;
                            pase = true;
                        }
                    }
                }
                texto.add(textoAux.trim());
            }
        }


        catch (Exception e)
        {
            texto.add("");
        }
        return texto;
    }
}
