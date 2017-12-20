package com.sociomas.aventones.Utils;
import android.app.Application;
import android.content.Context;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAPIGoogle;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;

/**
 * Created by oemy9 on 13/03/2017.
 */
public class ApplicationAventon  {
    private static Context context;
    public static ApplicationAventon instance;
    private ControllerAventon controllerAventon;
    private ControllerAPIGoogle controllerGoogle;
    private SessionManager manager;

    public static void onCreate(Context ctx){
        context=ctx;

    }

    public static ApplicationAventon getIntance() {
        if(instance==null){
            instance=new ApplicationAventon();
            instance.initApplication();
        }
        return instance;
    }

    private void initApplication() {
        instance = this;
        context = getAppContext();
        controllerAventon = new ControllerAventon(context);
        controllerGoogle=new ControllerAPIGoogle(context);
        manager=new SessionManager(context);
    }

    public ControllerAventon getControllerAventon() {
        return controllerAventon;
    }

    public void setControllerAventon(ControllerAventon controllerAventon) {
        this.controllerAventon = controllerAventon;
    }

    public ControllerAPIGoogle getControllerGoogle() {
        return controllerGoogle;
    }

    public void setControllerGoogle(ControllerAPIGoogle controllerGoogle) {
        this.controllerGoogle = controllerGoogle;
    }

    public static Context getAppContext() {
        return ApplicationAventon.context;
    }

    public SessionManager getManager() {
        return manager;
    }

    public void setManager(SessionManager manager) {
        this.manager = manager;
    }
}
