package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Interactors.PrivacidadInteractor;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.PrivacidadPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.Model.Response.Privacidad.Aviso;
import com.sociomas.core.WebService.Model.Response.Privacidad.PrivacidadResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 16/11/2017.
 */

public class PrivacidadPresenterImpl extends BasePresenterImpl implements PrivacidadPresenter, Callback<PrivacidadResponse> {

    private EnumTiposAviso currentTipo;
    private Aviso currentAviso;
    private PrivacidadView view;
    private ArrayList<Aviso> listAviso;
    private PrivacidadInteractor interactor;


    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(PrivacidadView)view;
        this.interactor=new PrivacidadInteractor();
    }

    //HACE LA PETICIÓN AL WEBSERVICE PARA OBTENER EL AVISO
    @Override
    public void obtenerAvisos(EnumTiposAviso tipoAviso) {
        this.currentTipo=tipoAviso;
        onShowProgress();
        this.interactor.obtenerAvisosAsync(tipoAviso,this);
    }

    //FILTRA LA LISTA DE AVISOS DE ACUERDO AL ID QUE SE ENVÍA
    private void filtrarAviso(final EnumTiposAviso  tipoAviso){
        if(listAviso!=null && (!listAviso.isEmpty())){
            Observable.fromIterable(listAviso).filter(new Predicate<Aviso>() {
                @Override
                public boolean test(Aviso aviso) throws Exception {
                    return aviso.getIdTipoAviso().equals(tipoAviso.getValue());
                }
            }).subscribe(new Consumer<Aviso>() {
                @Override
                public void accept(Aviso aviso) throws Exception {
                    if (aviso != null) {
                        currentAviso = aviso;
                        currentTipo=tipoAviso;
                        //Guarda en la bd
                        interactor.getDbUtils().agregarTerminoLegal(currentTipo,currentAviso.getLeyenda(),currentAviso.getTitulo(),currentAviso.getSubtitulo(),
                                currentAviso.getVersion());
                        view.mostrarAviso(currentAviso, currentTipo);
                    }
                    else{

                    }

                }
            });
        }
    }

    //REVISA SI EL USUARIO ACEPTO EL AVISO Y HACE PETICIÓN  PARA ENVIAR AVISO
    @Override
    public void aceptarAvisos(boolean acepto) {
        if(currentAviso==null){
            obtenerAvisos(EnumTiposAviso.TERMINOS_CONDICIONES);
        }
        else if(currentAviso!=null && acepto){
            onShowProgress();
            final SessionManager manager=ApplicationBase.getIntance().getManager();
            interactor.aceptarAvisoAsync(currentAviso, new CallBackBaseResponse() {
                @Override
                public void onSuccess(ServerResponse response) {
                    switch (currentTipo) {
                        case POLITICAS_PRIVACIDAD:
                            manager.add(Constants.FIRMO_PRIVACIDAD,true);
                            filtrarAviso(EnumTiposAviso.TERMINOS_CONDICIONES);
                            break;
                        case TERMINOS_CONDICIONES:
                            manager.add(Constants.FIRMO_TERMINOS,true);
                            view.navegarNext();
                            break;
                    }
                    onHideProgress();
                }

                @Override
                public void OnError(Throwable error) {
                    onShowToast("Ocurrió un error al obtener los avios de privacidad");
                }
            });
        }
        else{
            onShowToast("Es necesario que aceptes");
        }
    }

    @Override
    public void onResponse(Call<PrivacidadResponse> call, Response<PrivacidadResponse> response) {
        if(response.isSuccessful()){
            PrivacidadResponse res=response.body();
            if(!res.getError()) {
                //Se comienza la navegación de acuerdo al término o politica
                if (res.getListAviso() != null) {
                    this.listAviso=res.getListAviso();
                    if(listAviso.size()>1) {
                        filtrarAviso(currentTipo);
                    }
                    else if(listAviso.size()==1){
                        Aviso av=listAviso.get(0);
                        if(av.getIdTipoAviso()==EnumTiposAviso.TERMINOS_CONDICIONES.getValue()){
                            currentTipo=EnumTiposAviso.TERMINOS_CONDICIONES;
                        }
                        else{
                            currentTipo=EnumTiposAviso.POLITICAS_PRIVACIDAD;
                        }

                        filtrarAviso(currentTipo);
                    }
                    onHideProgress();
                }
                //La lista viene nulla se guarda la info
                else{
                     saveAvisoTerminosUserResponse();
                     onHideProgress();
                     view.navegarNext();
                }
            }
            else{
                view.inHabilitarBotonNext();
                onShowToast(R.string.error_privacidad);
                onHideProgress();
            }
        }
    }
    private void saveAvisoTerminosUserResponse(){
        SessionManager manager=ApplicationBase.getIntance().getManager();
        manager.add(Constants.FIRMO_TERMINOS,true);
        manager.add(Constants.FIRMO_PRIVACIDAD,true);
    }
    @Override
    public void onFailure(Call<PrivacidadResponse> call, Throwable t) {
        onShowToast(R.string.Error_Conexion);
    }

    public interface PrivacidadView{
        void mostrarAviso(Aviso aviso, EnumTiposAviso tiposAviso);
        void inHabilitarBotonNext();
        void navegarNext();
    }
}
