package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Parallax;

import com.bumptech.glide.Glide;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumDensidad;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteImagenRequest;
import com.sociomas.core.WebService.Model.Response.Gafete.ResponseGafeteImagen;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oemy9 on 22/11/2017.
 */

public class ParallaxGafetePresenterImpl extends BasePresenterImpl implements ParallaxGafetePresenter, ParallaxGafeteInteractor.CallBackGafete {

    private ParallaxGafeteInteractor interactor;
    private ParallaxView view;
    private boolean gafeteAlreadyRequest;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.interactor=new ParallaxGafeteInteractor();
        this.view=(ParallaxView)view;
        if (!Utils.IsGpsEncendido(ApplicationBase.getIntance().getApplicationContext())) {
                requestGafete(0d,0d);
        }
        else{
            //El gps se encuentra encendido pero no se ha hecho la petición para obtener el gafete
            Observable.timer(1,TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    if(!gafeteAlreadyRequest) {
                        requestGafete(0d, 0d);
                        gafeteAlreadyRequest=true;
                    }
                }
            });
        }
    }

    @Override
    public void requestGafete(Double lat, Double lng) {
        onShowProgress();
        this.interactor.getGafeteAsync(new GafeteImagenRequest(lat,lng,getCurrentDensidad().toString()),this);
        this.gafeteAlreadyRequest=true;
    }

    @Override
    public EnumDensidad getCurrentDensidad() {
        return Utils.getDensityNombre(ApplicationBase.getIntance().getApplicationContext());
    }

    @Override
    public void onSuccess(ResponseGafeteImagen gafeteImagen) {
            if(gafeteImagen!=null){
                if(gafeteImagen.isExisteFoto()) {
                    view.showImagenesGafete(gafeteImagen);
                }
                else{
                    view.navigateCredencializacion();
                }
            }
            onHideProgress();
    }


    @Override
    public void OnError(Throwable error) {
        onShowToast(R.string.Error_Conexion);
        onHideProgress();
    }

    public interface ParallaxView extends BaseView {
        void showImagenesGafete(ResponseGafeteImagen gafeteImagen);
        void navigateCredencializacion();
    }
}
