package com.sociomas.core.WebService.Model.Response.Promociones;

import com.sociomas.core.Utils.Enums.EnumContacto;

/**
 * Created by oemy9 on 19/08/2017.
 */

public class PromocionContacto {
    private String detalle;
    private EnumContacto tipo;

    public PromocionContacto(String detalle,EnumContacto tipo) {
        this.tipo = tipo;
        this.detalle=detalle;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public EnumContacto getTipo() {
        return tipo;
    }

    public void setTipo(EnumContacto tipo) {
        this.tipo = tipo;
    }
}
