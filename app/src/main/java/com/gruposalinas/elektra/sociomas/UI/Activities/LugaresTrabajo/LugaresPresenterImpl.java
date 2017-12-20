package com.gruposalinas.elektra.sociomas.UI.Activities.LugaresTrabajo;


import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Zonas.ExpandableGroupPosicionValida;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;
/**
 * Created by oemy9 on 18/09/2017.
 */
public class LugaresPresenterImpl extends BasePresenterImpl implements LugaresPresenter, LugaresInteractor.LugaresCallBack  {

    private LugaresView view;
    private LugaresInteractor interactor;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(LugaresView)view;
        this.interactor=new LugaresInteractor();
    }

    //CASO DE QUE QUERAMOS CONSULTAR UN EMPLEADO EN ESPECIFICO
    @Override
    public void requestLugaresTrabajo(String numeroEmpleado) {
        onShowProgress();
        ServerRequest request=new ServerRequest();
        request.setIdNumEmpleado(numeroEmpleado);
        interactor.getLugaresTrabajoAsync(request,this);
    }

    @Override
    public void requestLugaresTrabajo() {
        onShowProgress();
        ServerRequest request=new ServerRequest();
        request.setIdNumEmpleado(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        interactor.getLugaresTrabajoAsync(request,this);
    }


    @Override
    public void onAutorizarRechazar(LugarTrabajo lugarTrabajo, boolean autorizar) {
        onShowProgress();
        interactor.autorizarRechazarPosicion(lugarTrabajo,autorizar,this);
    }

    @Override
    public void requestEmpleadosPlantilla() {
        onShowProgress();
        interactor.getListadoEmpleadosPlantilla(this);
    }

    @Override
    public void onSuccess(ArrayList<ExpandableGroupPosicionValida> listLugaresTrabajo) {
        view.setListLugaresTrabajo(listLugaresTrabajo);
        onHideProgress();
    }

    @Override
    public void onSucessRechazarAutorizar(boolean autorizar, LugarTrabajo p) {
        view.onAutorizarRechazarSuccess(autorizar,p.getNombrCompleto());
        onHideProgress();
    }

    @Override
    public void onSuccessListadoEmpleados(ArrayList<SearchBoxItem> listadoPlantilla) {
        view.setListEmpleadoPlantilla(listadoPlantilla);
        onHideProgress();
    }

    @Override
    public void OnError(Throwable t) {
        if(t!=null) {
            onShowAlert(t.getMessage());
        }
        else{
            onShowAlert(R.string.Error_Conexion);
        }
        onHideProgress();
    }
    public interface  LugaresView extends BaseView {
        void setListLugaresTrabajo(ArrayList<ExpandableGroupPosicionValida>listLugares);
        void setListEmpleadoPlantilla(ArrayList<SearchBoxItem>listEmpleadoPlantilla);
        void onAutorizarRechazarSuccess(boolean autorizar,String nombreEmpleado);

    }
}
