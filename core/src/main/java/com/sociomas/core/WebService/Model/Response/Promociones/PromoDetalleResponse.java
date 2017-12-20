package com.sociomas.core.WebService.Model.Response.Promociones;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

/**
 * Created by oemy9 on 06/11/2017.
 *
 * La siguiente clase no es una respueta del WS.
 * Es una implementación para poder enviar datos del presenter a la view
 *
 */
public class PromoDetalleResponse {

    private PromoLista selectedPromo;
    private Integer colorDominante=null;
    private Integer colorVibrante=null;
    private Integer colorFinal=null;
    private Bitmap  selectedBitmap;


   public PromoDetalleResponse setPromo(PromoLista promo){
        this.selectedPromo=promo;
        return this;
    }

  public PromoDetalleResponse setColorDominante(int color){
        this.colorDominante=color;
        return this;
    }

  public PromoDetalleResponse setColorVibrante(int color){
        this.colorVibrante=color;
        return this;
    }

    public PromoDetalleResponse setBitmap(Bitmap bmp){
        this.selectedBitmap=bmp;
        return this;
    }

  public PromoDetalleResponse setColorFinal(int color){
        this.colorFinal=color;
        return this;
    }

   public  PromoDetalleResponse build(){
        return this;
    }

    public PromoLista getSelectedPromo() {
        return selectedPromo;
    }

    public Integer getColorDominante() {
        return colorDominante;
    }

    public Integer getColorVibrante() {
        return colorVibrante;
    }

    public Integer getColorFinal() {
        return colorFinal;
    }

    public Bitmap getSelectedBitmap() {
        return selectedBitmap;
    }
}

