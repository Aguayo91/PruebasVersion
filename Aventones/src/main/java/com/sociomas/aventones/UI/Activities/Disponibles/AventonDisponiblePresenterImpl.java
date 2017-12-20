package com.sociomas.aventones.UI.Activities.Disponibles;
import android.content.Intent;
import com.sociomas.aventones.R;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import com.sociomas.core.WebService.Model.Response.Aventones.ConsultaAventonesResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.List;

/**
 * Created by oemy9 on 13/10/2017.
 */

public class AventonDisponiblePresenterImpl extends BasePresenterImpl  implements AventonDisponiblePresenter, CallBackBaseResponse {

    private AventonDisponibleView view;
    private ConsultaAventonesResponse consultaAventones;
    private AventonDisponibleInteractor interactor;


    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(AventonDisponibleView)view;
        this.interactor=new AventonDisponibleInteractor();
    }

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(ViewEvent.ENTRY)){
            consultaAventones = (ConsultaAventonesResponse)intent.getSerializableExtra(ViewEvent.ENTRY);
            view.setListAventonesDisponibles(consultaAventones.getListAventones());
        }
    }



    @Override
    public void obtenerListadoAventones() {
        if(consultaAventones!=null){
            view.setListAventonesDisponibles(consultaAventones.getListAventones());
        }
    }

    @Override
    public void solicitarAventon(Aventones selectedAventon) {
        if(selectedAventon!=null){
            onShowProgress();
            interactor.solicitarAventonAsync(selectedAventon,this);
        }
    }

    @Override
    public void onSuccess(ServerResponse response) {
        onHideProgress();
        if(!response.getError()){
            view.navegarSuccess();
        }
        else{
            view.hideDialogoConfirmarAventon();
            onShowAlert(response.getMensaje());

        }
    }

    @Override
    public void OnError(Throwable error) {
        onShowAlert(R.string.Error_Conexion);
        onHideProgress();
    }


    public interface  AventonDisponibleView extends BaseView{
        void setListAventonesDisponibles(List<Aventones> listAventones);
        void hideDialogoConfirmarAventon();
        void navegarSuccess();
    }
}
