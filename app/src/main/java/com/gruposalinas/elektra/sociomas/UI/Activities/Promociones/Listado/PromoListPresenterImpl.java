package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Listado;

import android.content.Intent;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Categorias.PromoCatPresenterImpl;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Request.Promo.PromoRequest;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoLista;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by oemy9 on 06/11/2017.
 */

public class PromoListPresenterImpl extends BasePresenterImpl implements  PromoListPresenter, Consumer<PromoResponse>{

    private PromoLista selectedCat;
    private PromoCatPresenterImpl.PromocionesView  view;


    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(PromoCatPresenterImpl.PromocionesView)view;
    }

    @Override
    public void setArguments(Intent intent) {
        super.setArguments(intent);
        if(intent.hasExtra(ViewEvent.ENTRY)){
            selectedCat=(PromoLista)intent.getSerializableExtra(ViewEvent.ENTRY);
            this.view.changeToolbarTitle(Utils.toTitleCase(selectedCat.getCategoria()));
        }
    }

    @Override
    public void obtenerPromociones() {
        if(selectedCat!=null) {
            onShowProgress();
            ApplicationBase.getIntance().getControllerPromo()
                    .obtenerDetalleCategoria(new PromoRequest(selectedCat.getIdCategoria()))
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            onShowToast(R.string.Error_Conexion);
                        }
                    })
                    .subscribe(this);
        }
    }

    @Override
    public void accept(PromoResponse response) {
        try{
            if(!response.getError()) {
               Observable.fromIterable(response.getListDescuentosDetalle()).distinct(new Function<PromoLista, Object>() {
                    @Override
                    public Object apply(PromoLista value) throws Exception {
                        return value.getNombreEmpresa();
                    }
                }).toList().subscribe(new Consumer<List<PromoLista>>() {
                   @Override
                   public void accept(List<PromoLista> promoListas) throws Exception {
                       view.setListPromociones((ArrayList)promoListas);
                   }
               });
                onHideProgress();
            }
            else{
                onShowAlert(response.getMensajeError());
                onHideProgress();
            }
        }
        catch (Exception ex){
            onShowAlert(R.string.Error_Conexion);
        }
    }
}
