package com.gruposalinas.elektra.sociomas.UI.Activities.SplashScreen;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.GafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.SliderActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Login.LoginActivity;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SplashScreenActivity extends BaseAppCompactActivity implements  Consumer<Long> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreenactivity);
        Observable.timer(1, TimeUnit.SECONDS).subscribe(this);
    }
    @Override
    public void accept(Long aLong) throws Exception {
        SessionManager manager = ApplicationBase.getIntance().getManager();

        if (manager.getString(Constants.SP_HASLOGGED) == null) {
             //go to login here
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        } else {

            if(empleadoIsIvan()){
                eliminarShared();
            }

            else {
                if (!manager.get(Constants.FIRMO_PRIVACIDAD, false) || !manager.get(Constants.FIRMO_TERMINOS, false)) {
                    // go to slider activity here
                    startActivity(new Intent(SplashScreenActivity.this, SliderActivity.class));
                } else {
                    //go to main activity here
                    startActivity(new Intent(SplashScreenActivity.this, InicioActivityV2.class));
                }

            }
        }
        finish();
    }
    private boolean empleadoIsIvan(){
        return  ApplicationBase.getIntance().getManager().getString(Constants.SP_ID).equalsIgnoreCase(Constants.IVAN_NUMERO);
    }
    private void eliminarShared (){
        SharedPreferences pref = this.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        finish();
    }
}