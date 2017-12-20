package com.sociomas.core.WebService.Model.Response.AutoComplete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by oemy9 on 28/08/2017.
 */

public class ResponsePlace {
        @SerializedName("predictions")
        @Expose
        private ArrayList<Prediction> predictions = null;
        @SerializedName("status")
        @Expose
        private String status;
        public ArrayList<Prediction> getPredictions() {
            return predictions;
        }
        public void setPredictions(ArrayList<Prediction> predictions) {
            this.predictions = predictions;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
}
