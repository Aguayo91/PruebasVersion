package com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones;


import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaList;

/**
 * Created by oemy9 on 27/06/2017.
 */

@SuppressWarnings("unused")
public class ZonaDetalleRequest extends ServerRequest {

    private ZonaList zona;

    public ZonaList getZona() { return this.zona; }

    public void setZona(ZonaList zona) { this.zona = zona; }
}
