package com.sociomas.core;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gruposalinas.elektra.sociomas.UI.Activities.Login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.DataBaseModel.Empleado;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Utils;

/**
 * Instrumentation test, which will execute on an Android device.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    public static final String EMPLEADO_TEST="10012992";
    public static final String LLAVE_TEST="Jeshuaromero221207";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.gruposalinas.elektra.sociomas", appContext.getPackageName());
    }
    public Context getContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        return appContext;
    }
    @Rule
    public ActivityTestRule<LoginActivity> loginRule = new ActivityTestRule<>(LoginActivity.class, true, true);


    public void checkEmpleadoBeforeLogin(Context context){
        SessionManager manager=new SessionManager(context);
        assertNotEquals("El usuario tiene número de empleado error:"+manager.getString(Constants.SP_ID),manager.getString(Constants.SP_ID),"");
    }

    @Test
    public void initLogin() {
        loginRule.launchActivity(new Intent());
        checkEmpleadoBeforeLogin(loginRule.getActivity().getActivityInstance());
        onView(withId(R.id.txtNumeroEmpleado)).perform(clearText(), typeText(EMPLEADO_TEST));
        onView(withId(R.id.txtNumeroEmpleado)).perform(closeSoftKeyboard());
        pauseTestFor(TimeUnit.SECONDS.toMillis(1));
        onView(withId(R.id.txtLlaveMaestra)).perform(clearText(), typeText(LLAVE_TEST));
        onView(withId(R.id.txtLlaveMaestra)).perform(closeSoftKeyboard());
        pauseTestFor(TimeUnit.SECONDS.toMillis(1));
        onView(withId(R.id.btnLogin)).perform(click());
        pauseTestFor(TimeUnit.SECONDS.toSeconds(5));
        assertTrue(loginRule.getActivity().isFinishing());
        testNumeroEmpleadoAfterLogin(loginRule.getActivity().getActivityInstance());
    }
    public void testNumeroEmpleadoAfterLogin(Context context){
        DBUtils dbUtils=new DBUtils(context);
        if(dbUtils.obtenerEmpleado()!=null){
            if(dbUtils.obtenerEmpleado().size()>0){
                //Empleado de bd
                Empleado empleado=dbUtils.obtenerEmpleado().get(0);
                //Empleado de sharedPreferences
                Empleado empleadoShared=Utils.getCurrentEmpleado(getContext());
                assertNotEquals("Los números de empleados no son iguales",empleado.getIdEmployee(),empleadoShared.getIdEmployee());
            }
            else{
                assertNull("El objeto empleado de la bd esta nullo");
            }
        }
    }


    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}