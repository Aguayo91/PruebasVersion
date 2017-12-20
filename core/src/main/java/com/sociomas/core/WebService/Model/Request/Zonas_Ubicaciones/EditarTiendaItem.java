package com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones;

/**
 * Created by oemy9 on 30/06/2017.
 */

@SuppressWarnings("unused")
public class EditarTiendaItem {
    private String va_numero_pos;
    private String va_comentario;

    public EditarTiendaItem(String va_numero_pos, String va_comentario) {
        this.va_numero_pos = va_numero_pos;
        this.va_comentario=va_comentario;
    }

    public String getVa_numero_pos() {
        return va_numero_pos;
    }

    public void setVa_numero_pos(String va_numero_pos) {
        this.va_numero_pos = va_numero_pos;
    }

    public String getVa_comentario() {
        return va_comentario;
    }

    public void setVa_comentario(String va_comentario) {
        this.va_comentario = va_comentario;
    }
}
