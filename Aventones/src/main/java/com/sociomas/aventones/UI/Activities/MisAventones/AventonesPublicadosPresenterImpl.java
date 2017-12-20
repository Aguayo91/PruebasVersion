package com.sociomas.aventones.UI.Activities.MisAventones;

import android.support.v7.widget.RecyclerView;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.AdapterMisPublicados;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.WebService.Model.Request.VerMisAventonesRequest;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventone;
import com.sociomas.core.WebService.Model.Response.Aventones.ConsultaMisAventones;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jromeromar on 17/10/2017.
 */

public class AventonesPublicadosPresenterImpl extends BasePresenterImpl implements AventonesPublicadosPresenter{

    private AventonPublicadoView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(AventonPublicadoView)view;
    }

    @Override
    public void miListaAventones() {
        onShowProgress();
        ApplicationAventon.getIntance().getControllerAventon().miListaAventones(new VerMisAventonesRequest()).enqueue(new Callback<ConsultaMisAventones>() {
            @Override
            public void onResponse(Call<ConsultaMisAventones> call, Response<ConsultaMisAventones> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        view.setListAventonesPublicados(response.body().getAventone());
                    }else{
                        onShowToast(response.body().getMensaje());
                    }
                }else{
                    onShowToast(R.string.Error_Conexion);
                }
                onHideProgress();
            }

            @Override
            public void onFailure(Call<ConsultaMisAventones> call, Throwable t) {
                onShowToast(R.string.Error_Conexion);
                onHideProgress();
            }
        });
    }

    public interface AventonPublicadoView extends BaseView{
        void setListAventonesPublicados(List<Aventone> listAventones);
    }
}
