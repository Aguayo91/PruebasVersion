package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Carga;
import android.text.TextUtils;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Gafete.ResponseGafete;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by oemy9 on 07/11/2017.
 */

public class CargaGafetePresenterImpl extends BasePresenterImpl implements CargarGafetePresenter, Observer<ResponseGafete> {

    private CargaGafeteView view;
    private CargaGafeteInteractor interactor;
    private static final String TAG = "CargaGafetePresenterImp";

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(CargaGafeteView)view;
        this.interactor=new CargaGafeteInteractor();
    }

    @Override
    public void initCargaImagen() {
        if(!hasImagenGafete()){
            onShowProgress();
            interactor.obtenerImagenAsync().subscribe(this);
       }

        else{
            view.setImagenGafete(getImagenGafete());
        }
    }

    @Override
    public boolean hasImagenGafete() {
        String gafete= ApplicationBase.getIntance().getManager().getString(Constants.IMAGEN_CREDENCIAL);
        return !TextUtils.isEmpty(gafete);
    }

    @Override
    public String getImagenGafete() {
        return ApplicationBase.getIntance().getManager().getString(Constants.IMAGEN_CREDENCIAL);
    }
    

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(ResponseGafete res) {if(res!=null) {
            if (!TextUtils.isEmpty(res.getFoto())) {
                ApplicationBase.getIntance().getManager().add(Constants.IMAGEN_CREDENCIAL, res.getFoto());
                view.setImagenGafete(res.getFoto());
            } else {
                view.navegarCredencializacion();
            }
        }
        onHideProgress();
    }

    @Override
    public void onError(Throwable e) {
        onShowToast(e.getMessage());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
    }

    public interface CargaGafeteView extends BaseView{
        void setImagenGafete(String base64Imagen);
        void navegarCredencializacion();
    }

}
