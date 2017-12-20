package com.sociomas.aventones.UI.Activities.QrScan;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by oemy9 on 17/10/2017.
 */

public class ResultadoScan {
        private LatLng latLngResult;

        private double accuracy;

        private String resultadoQr;


        public LatLng getLatLngResult() {
            return latLngResult;
        }

        public void setLatLngResult(LatLng latLngResult) {
            this.latLngResult = latLngResult;
        }

        public void setValores(double[]valores){
            this.latLngResult =new LatLng(valores[0],valores[1]);
        }

        public String getResultadoQr() {
                return resultadoQr;
            }

            public void setResultadoQr(String resultadoQr) {
                this.resultadoQr = resultadoQr;
            }

            public double getAccuracy() {
                return accuracy;
            }

            public void setAccuracy(double accuracy) {
                this.accuracy = accuracy;
            }
    }
