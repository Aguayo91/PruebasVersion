package com.sociomas.aventones.UI.Controls;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by jromeromar on 11/10/2017.
 */

public class SeekBarkRangoProgressListener implements  DiscreteSeekBar.OnProgressChangeListener {

    private Integer conversionNumerica,rangoBusqueda;


    public SeekBarkRangoProgressListener(){
        this.rangoBusqueda=50;
    }

    public Integer getConversionNumerica() {
        return conversionNumerica;
    }

    public Integer getRangoBusqueda() {
        return rangoBusqueda;
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

        //Usamos conversionNumerica para mostrar los datos al usuario y rango de busqueda para convertirlo a metros y pasarlo al Back
        if(value==0) {
            conversionNumerica = 50;
            rangoBusqueda = conversionNumerica;
            seekBar.setIndicatorFormatter(conversionNumerica.toString().concat("m"));

        }   else if(value==1) {
            conversionNumerica = 200;
            rangoBusqueda=conversionNumerica;
            seekBar.setIndicatorFormatter(conversionNumerica.toString().concat("m"));

        }   else if(value==2){
            conversionNumerica=500;
            rangoBusqueda=conversionNumerica;
            seekBar.setIndicatorFormatter(conversionNumerica.toString().concat("m"));

        }   else if(value==3){
            conversionNumerica=1;
            rangoBusqueda=conversionNumerica*1000;
            seekBar.setIndicatorFormatter(conversionNumerica.toString().concat(" Km"));

        }   else if(value==4){
            conversionNumerica=3;
            rangoBusqueda=conversionNumerica*1000;
            seekBar.setIndicatorFormatter(conversionNumerica.toString().concat(" Km"));

        }   else if(value==5){
            conversionNumerica=5;
            rangoBusqueda=conversionNumerica*1000;
            seekBar.setIndicatorFormatter(conversionNumerica.toString().concat(" Km"));

        }
    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }
}
