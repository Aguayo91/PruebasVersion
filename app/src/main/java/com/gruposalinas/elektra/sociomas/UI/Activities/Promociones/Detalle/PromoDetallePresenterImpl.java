package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Detalle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoDetalleResponse;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoLista;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.Arrays;
import java.util.List;

/**
 * Created by oemy9 on 06/11/2017.
 */
public class PromoDetallePresenterImpl extends BasePresenterImpl implements PromoDetallePresenter, Target, Palette.PaletteAsyncListener {

    private List<Integer> coloresBlancos= Arrays.asList(-1118498,-1642762,-1642770,-14079613,-1644826,-2169114,-2763307);
    private PromoLista selectedItem;
    private PromoDetalleView view;
    private PromoDetalleResponse pDetalle=new PromoDetalleResponse();

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(PromoDetalleView)view;
    }

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(ViewEvent.ENTRY)){
            selectedItem=(PromoLista)intent.getSerializableExtra(ViewEvent.ENTRY);
        }
    }

    @Override
    public void mostrarInfo() {
        Picasso.with(ApplicationBase.getIntance().getApplicationContext()).load(getUrlImagen(selectedItem.getiNTELLAVPR())).into(this);

    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if(bitmap!=null){
            Palette.from(bitmap).generate(this);
            pDetalle.setBitmap(bitmap);
        }
    }
    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        pDetalle = new PromoDetalleResponse();
        pDetalle.setPromo(selectedItem);
        onShowToast(R.string.ocurrio_error);
        view.onInfoLoaded(pDetalle);
        view.hideProgressImagen();

    }
    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {}

    @Override
    public void onGenerated(Palette palette) {
        Context ctx=ApplicationBase.getIntance().getApplicationContext();
        int colorDominante=palette.getDominantColor(ContextCompat.getColor(ctx, R.color.colorPrimary));
        int colorVibrante=palette.getDarkVibrantColor(ContextCompat.getColor(ctx,R.color.colorPrimary));
        int colorFinal=!coloresBlancos.contains(colorDominante) ?colorDominante: colorVibrante;
        if(pDetalle==null){
            pDetalle=new PromoDetalleResponse();
        }
        pDetalle.setPromo(selectedItem)
                .setColorDominante(colorDominante)
                .setColorVibrante(colorVibrante)
                .setColorFinal(colorFinal);
        view.onInfoLoaded(pDetalle.build());
        view.hideProgressImagen();
    }

    public interface  PromoDetalleView extends BaseView{
        void showProgressImagen();
        void hideProgressImagen();
        void onInfoLoaded(PromoDetalleResponse selectedPromoDetalle);
    }

    private String getUrlImagen(String idEmpresa){
        return String.format("https://portalsocio.gs/descuentos/Image.aspx?BS=%s",idEmpresa);
    }

}
