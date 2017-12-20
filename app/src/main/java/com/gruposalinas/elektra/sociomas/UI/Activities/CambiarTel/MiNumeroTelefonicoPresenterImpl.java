package com.gruposalinas.elektra.sociomas.UI.Activities.CambiarTel;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.ModificarTelefono.ModificarTelefonoRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.NumeroTelefono.TelefonoResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jmarquezu on 07/12/2017.
 */

public class MiNumeroTelefonicoPresenterImpl extends BasePresenterImpl implements MiNumeroTelefonicoPresenter, Callback<TelefonoResponse> {


    private viewMinumeroTel view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(viewMinumeroTel)view;
    }

    @Override
    public void validarDatos(final String numero) {
        if(!TextUtils.isEmpty(numero)){
            view.showDialog();
            view.setMessageDialog(R.string.registrando_numero_tel);
            Observable.timer(600,TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    ApplicationBase.getIntance().getControllerAPI().ModificarTelefonoEmpleado(new ModificarTelefonoRequest(numero))
                            .enqueue(MiNumeroTelefonicoPresenterImpl.this);
                }
            });
        }
        else{
            view.validarNumero();
        }
    }

    @Override
    public void consultarNumeroTel() {
        onShowProgress();
        ApplicationBase.getIntance().getControllerAPI().consultarTelefonoEmpleado(new ServerRequest()).enqueue(new Callback<TelefonoResponse>() {
            @Override
            public void onResponse(Call<TelefonoResponse> call, Response<TelefonoResponse> response) {
                if(response.isSuccessful()){
                    if(!TextUtils.isEmpty(response.body().getNumeroTelefono())) {
                        view.setNumeroTelfono(response.body().getNumeroTelefono());
                    }
                }
                else{
                    onShowToast(R.string.Error_Conexion);
                }
                onHideProgress();
            }

            @Override
            public void onFailure(Call<TelefonoResponse> call, Throwable t) {
                onShowToast(R.string.Error_Conexion);
            }
        });
    }


    @Override
    public void onResponse(Call<TelefonoResponse> call, Response<TelefonoResponse> response) {
        if(response.isSuccessful()){
            TelefonoResponse telefonoResponse = response.body();
            if(telefonoResponse.getError()){
                view.hideDialog();
            }
            else{
                ApplicationBase.getIntance().getManager().add(Constants.MY_PHONE,response.body().getNumeroTelefono());
                view.setMessageDialog(R.string.concluyendo_registro);
                hideDialogTimer();
            }
        }
    }

    private void hideDialogTimer(){
        Observable.timer(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                view.hideDialog();
                view.finishResponse();
            }
        });
    }

    @Override
    public void onFailure(Call<TelefonoResponse> call, Throwable t) {
        view.hideDialog();
    }

    public interface viewMinumeroTel extends BaseView{
        void showDialog();
        void hideDialog();
        void validarNumero();
        void setMessageDialog(@StringRes int res);
        void finishResponse();
        void setNumeroTelfono(String numeroTelfono);
    }
}
