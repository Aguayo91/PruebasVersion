package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jmarquezu on 18/10/2017.
 */

public class AventonesReservadosResponse extends ServerResponse implements Serializable{

        @SerializedName("Aventones")
        @Expose
        private List<Aventones> Aventones;

        public List<Aventones> getAventones() { return this.Aventones; }

        public void setAventones(List<Aventones> Aventones) { this.Aventones = Aventones; }

}
