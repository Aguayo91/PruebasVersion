package com.sociomas.core.WebService.Model.Response.AutoComplete;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by oemy9 on 04/09/2017.
 */

public class PredictionSections  implements Serializable{
    private String headerTitle;
    private ArrayList<Prediction>listPrediction;

    public PredictionSections(String headerTitle,ArrayList<Prediction>listPrediction) {
        this.headerTitle = headerTitle;
        this.listPrediction=listPrediction;
    }

    public PredictionSections(){

    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Prediction> getListPrediction() {
        return listPrediction;
    }

    public void setListPrediction(ArrayList<Prediction> listPrediction) {
        this.listPrediction = listPrediction;
    }
}
