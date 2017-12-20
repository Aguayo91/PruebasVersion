package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Categorias;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.WebService.Model.Request.Promo.PromoRequest;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoLista;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoResponse;
import java.util.ArrayList;
import io.reactivex.functions.Consumer;

/**
 * Created by oemy9 on 06/11/2017.
 */

public class PromoCatPresenterImpl extends BasePresenterImpl implements PromoCatPresenter , Consumer<PromoResponse>{

    private PromocionesView view;


    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(PromocionesView)view;
    }

    @Override
    public void obtenerCategorias() {
        onShowProgress();
        if(Utils.hasInternet(ApplicationBase.getIntance().getApplicationContext())) {
            ApplicationBase.getIntance().getControllerPromo().obtenerCategorias(new PromoRequest()).subscribe(this);
        }
        else{
            onShowAlert(R.string.Error_Conexion);
        }
    }

    @Override
    public void accept(PromoResponse response) {
        try {
            if (response != null) {
                if(!response.getError()) {
                    view.setListPromociones(response.getListaDescuentosCategoria());
                    onHideProgress();
                }
                else{
                    onShowAlert(response.getMensajeError());
                    onHideProgress();
                }
            }
        }
        catch (Exception ex){
            onShowAlert(R.string.Error_Conexion);
        }
    }

    public interface PromocionesView extends BaseView{
        void setListPromociones(ArrayList<PromoLista>listPromociones);
        void changeToolbarTitle(String title);
    }
}

