package com.sociomas.core.MVP;

import android.content.Context;

import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAsistencia;

/**
 * Created by oemy9 on 20/11/2017.
 */

public  abstract class BaseInteractor {

    private  Context context;

    private SessionManager manager;
    private  ControllerAPI controllerAPI;
    private ControllerAsistencia controllerAsistencia;

    public  BaseInteractor(Context context){
        this.context=context;
    }

    public BaseInteractor(Context context,ControllerAPI controllerAPI) {
        this.context=context;
        this.controllerAPI = controllerAPI;
    }

    public BaseInteractor(Context context, ControllerAsistencia controllerAsistencia){
            this.context=context;
            this.controllerAsistencia=controllerAsistencia;
    }

    protected SessionManager getSessionManager(){
        this.manager=new SessionManager(this.context);
        return manager;
    }

    protected ControllerAPI getControllerAPI(){
        if(controllerAPI==null){
            controllerAPI=new ControllerAPI(this.context);
        }
        return  controllerAPI;
    }

    public void setControllerAPI(ControllerAPI controllerAPI) {
        this.controllerAPI = controllerAPI;
    }

    public void setManager(SessionManager manager) {
        this.manager = manager;
    }

    public ControllerAsistencia getControllerAsistencia() {
        return controllerAsistencia;
    }

    public void setControllerAsistencia(ControllerAsistencia controllerAsistencia) {
        this.controllerAsistencia = controllerAsistencia;
    }

    public Context getContext() {
        return context;
    }
}
