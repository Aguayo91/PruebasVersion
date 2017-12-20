package com.sociomas.aventones.UI.Activities.UsuarioPiloto;
import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.UsuarioPiloto.UsuarioPilotoRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 20/10/2017.
 */

public class UsuarioPilotoPresenterImpl extends BasePresenterImpl implements  UsuarioPilotoPresenter {

    private UsuarioPilotoView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(UsuarioPilotoView)view;
    }

    @Override
    public void agregarUsuario(String numeroEmpleado) {
        if(numeroEmpleado==null ||  (numeroEmpleado.isEmpty())){
            onShowAlert(R.string.ingresa_empleado);
        }
        else {
            onShowProgress();
            ApplicationAventon.getIntance().getControllerAventon().insertarUsuarioPiloto(new UsuarioPilotoRequest(numeroEmpleado)).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if(response.isSuccessful()){
                        onShowToast(response.body().getMensaje());
                        view.navegarBack();
                    }
                    else{
                        onShowToast(R.string.Error_Conexion);
                    }
                    onHideProgress();
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    onShowToast(R.string.Error_Conexion);
                    onHideProgress();
                }
            });
        }
    }
    public interface UsuarioPilotoView extends BaseView{
        void navegarBack();
    }

}
