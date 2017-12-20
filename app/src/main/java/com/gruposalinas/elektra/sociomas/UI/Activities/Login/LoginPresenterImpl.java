package com.gruposalinas.elektra.sociomas.UI.Activities.Login;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioInteractor;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Security.DecryptUtils;
import com.gruposalinas.elektra.sociomas.Utils.Security.SecurityItems;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.WebService.Model.Request.Registro.CheckEmpleadoRequest;
import com.sociomas.core.WebService.Model.Request.Registro.RegistroRequest;
import com.sociomas.core.WebService.Model.Response.Registro.RegistroResponse;
import com.sociomas.core.WebService.Model.Response.Registro.Team;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 12/09/2017.
 */

public class LoginPresenterImpl extends BasePresenterImpl implements LoginPresenter, Callback<RegistroResponse> {


    private LoginView view;
    private String noEmpleado;
    private String llaveMaestra;
    private String telefono="";
    private static final String TAG = "LoginPresenterImpl";


    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(LoginView)view;
    }

    @Override
    public void validarCredenciales(String noEmpleado, String llaveMaestra) {
        if(noEmpleado.isEmpty()){
           view.onEmpleadoVacio();
        }
        else if(llaveMaestra.isEmpty()){
            view.onLlaveMaestraVacia();
        }
        else{
                onShowProgress();
                this.noEmpleado=noEmpleado;
                this.llaveMaestra=llaveMaestra;
                ApplicationBase.getIntance().getControllerAPI()
                        .inicioRegistroEmpleado(
                                new RegistroRequest(noEmpleado.trim().replaceAll("\\s",""),llaveMaestra,this.telefono)).enqueue(this);

        }
    }


    public void checkEmpleado(String noEmpleado) {
        if(noEmpleado.isEmpty()){
            view.onEmpleadoVacio();
        }
        else{
            ApplicationBase.getIntance().getControllerAPI().checkEmpleado(new CheckEmpleadoRequest(noEmpleado)).enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
        if (response.isSuccessful()) {
            InicioInteractor interactor=new InicioInteractor();
            RegistroResponse responseRegistro = response.body();
            if (responseRegistro.getError()) {
                //USUARIO REGISTRADO ANTERIORMENTE SE HACE LLAMADA AL SERVICIO DE CHECK EMPLEADO
                if (responseRegistro.getMensajeError().equals(view.getActivityInstance().getString(R.string.registrado_anteriomente))) {
                    ApplicationBase.getIntance().getControllerAPI().checkEmpleado(new CheckEmpleadoRequest(this.noEmpleado));
                    interactor.saveEmpleadoInDb(responseRegistro.getEmpleado());

                } else {
                    onShowAlert(responseRegistro.getMensajeError());
                }
            }
           else{
                interactor.savePlantillaEmpleado(response.body().getPlantilla());
                view.onSuccess(responseRegistro);
            }
        }
        else{
            onShowAlert(R.string.Error_Conexion);
        }
        onHideProgress();
    }

    @Override
    public void onFailure(Call<RegistroResponse> call, Throwable t) {
        onShowAlert(R.string.Error_Conexion);
        onHideProgress();
    }

    public interface  LoginView extends BaseView {
        void onEmpleadoVacio();
        void onTelefonoVacio();
        void onLlaveMaestraVacia();
        void onSuccess(RegistroResponse respuesta);
    }
}
