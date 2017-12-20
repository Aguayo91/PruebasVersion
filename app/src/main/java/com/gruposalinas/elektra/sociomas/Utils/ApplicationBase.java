package com.gruposalinas.elektra.sociomas.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import com.facebook.stetho.Stetho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.gruposalinas.elektra.sociomas.BuildConfig;
import com.gruposalinas.elektra.sociomas.IO.FireBase.ConfigFireBase;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAsistencia;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerNomina;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerPromociones;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerSOS;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerTimezone;
import com.sociomas.core.WebService.Controllers.Promociones.ControllerPromo;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadNegocioResponse;


/**
 * Created by oemy9 on 13/03/2017.
 */
public class ApplicationBase extends MultiDexApplication {
    private static Context context;
    public static ApplicationBase instance;
    public static String IPDeviceAddress ;

    private ControllerAPI controllerAPI;
    private ControllerAsistencia controllerAsistencia;
    private ControllerPromociones controllerPromociones;
    private ControllerTimezone controllerTimezone;
    private ControllerSOS controllerSOS;
    private ControllerNomina controllerNomina;
    private ControllerAventon controllerAventon;
    private ControllerPromo controllerPromo;
    private UnidadNegocioResponse unidadNegocioResponse;
    private SessionManager managerGafete;
    private SessionManager manager;

    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    public static ApplicationBase getIntance() {
        if(instance==null){
            instance=new ApplicationBase();
            instance.initApplication();
        }
        return instance;
    }

    private void initApplication() {
        instance = this;
        context = getApplicationContext();
        controllerAsistencia = new ControllerAsistencia(context);
        controllerPromociones = new ControllerPromociones(context);
        controllerTimezone = new ControllerTimezone(context);
        controllerSOS = new ControllerSOS(context);
        controllerNomina = new ControllerNomina(context);
        controllerAventon=new ControllerAventon(context);
        controllerPromo=new ControllerPromo(context);
        controllerAPI=new ControllerAPI(context);
        manager=new SessionManager(context);
        managerGafete=new SessionManager(context, Constants.SHARED_PREFERENCES_GAFETE);
        IPDeviceAddress = com.sociomas.core.Utils.DeviceUtils.getDeviceIPAddress(true);
        Stetho.initializeWithDefaults(this);
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/LatoRegular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/LatoRegular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/LatoRegular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/LatoRegular.ttf");
    }

    public ControllerAPI getControllerAPI() {
        return controllerAPI;
    }

    public ControllerAsistencia getControllerAsistencia() {
        return controllerAsistencia;
    }

    public ControllerPromociones getControllerPromociones() {
        return controllerPromociones;
    }

    public ControllerTimezone getControllerTimezone() {
        return controllerTimezone;
    }

    public ControllerSOS getControllerSOS() {
        return controllerSOS;
    }


    public ControllerNomina getControllerNomina() {
        return controllerNomina;
    }

    public ControllerAventon getControllerAventon() {
        return controllerAventon;
    }

    public void setControllerNomina(ControllerNomina controllerNomina) {
        this.controllerNomina = controllerNomina;
    }

    public static Context getAppContext() {
        return ApplicationBase.context;
    }

    public SessionManager getManager() {
        return manager;
    }

    public void setManager(SessionManager manager) {
        this.manager = manager;
    }

    public SessionManager getManagerGafete() {return managerGafete;}

    public void setManagerGafete(SessionManager managerGafete) {this.managerGafete = managerGafete;}

    public ControllerPromo getControllerPromo() {
        return controllerPromo;
    }

    public UnidadNegocioResponse getUnidadNegocioResponse() {
        return unidadNegocioResponse;
    }

    public void setUnidadNegocioResponse(UnidadNegocioResponse unidadNegocioResponse) {
        this.unidadNegocioResponse = unidadNegocioResponse;
    }
    /*Inicio de firebase remoteconfig solo para el controller de controller API */
    private void initConfigFireBase(final Context context){
        final FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(ConfigFireBase.getDefaults());
        long cacheExpiration = 3600;
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.activateFetched();
                }
            }});
    }
}
