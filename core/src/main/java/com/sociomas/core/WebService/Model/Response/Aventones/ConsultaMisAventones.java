package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.List;

/**
 * Created by jromeromar on 16/10/2017.
 */

public class ConsultaMisAventones  extends ServerResponse{
    @SerializedName("Aventones")
    @Expose
    private List<Aventone> aventones = null;
    public List<Aventone> getAventone() {
        return aventones;
    }
}
