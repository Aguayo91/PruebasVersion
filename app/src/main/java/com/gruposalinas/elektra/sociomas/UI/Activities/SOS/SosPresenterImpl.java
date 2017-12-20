package com.gruposalinas.elektra.sociomas.UI.Activities.SOS;
import android.os.CountDownTimer;
import com.gruposalinas.elektra.sociomas.IO.Services.SOSAudioService;
import com.gruposalinas.elektra.sociomas.IO.Services.SOService;

import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Model.Response.Contacto.Contacto;

import java.util.ArrayList;
/**
 * Created by oemy9 on 07/09/2017.
 */
public class SosPresenterImpl extends BasePresenterImpl implements SosPresenter {
    private SosView view;
    private CountDownTimer countDownSOS;
    public CountDownTimer getCountDownSOS() {
        return countDownSOS;
    }

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(SosView)view;
    }

    @Override
    public void loadContactsList() {
        ArrayList<Contacto>listContactos=new ArrayList<>();
        SessionManager manager=ApplicationBase.getIntance().getManager();
        for(Integer j=1;j<=3;j++) {
            String keyNombre= Constants.nombre.concat(j.toString());
            String keyTel=Constants.tel.concat(j.toString());
            if(manager.getString(keyNombre) !=null && manager.getString(keyTel)!=null)
            {
                listContactos.add(new Contacto(manager.getString(keyNombre), manager.getString(keyTel)));
            }
            else{
                listContactos.add(new Contacto(String.format("Contacto %s",j),"No asignado"));
            }
        }
        view.setListContactos(listContactos);
    }

    public void checkIfStartTimer() {
        if (!Utils.servicioEjecutando(view.getActivityInstance(), SOService.class) && !Utils.servicioEjecutando(view.getActivityInstance(), SOSAudioService.class)) {
            countDownSOS =new CountDownTimer(Constants.SEGUNDO*6,Constants.SEGUNDO) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long segundos=millisUntilFinished/ Constants.SEGUNDO;
                    view.onTickTimer(segundos);
                }
                @Override
                public void onFinish() {
                    view.onTimerFinish(true);
                    ViewEvent event=new ViewEvent(ViewEventType.SHOW_TOAST_MESSAGE);
                    event.getModel().put(ViewEvent.MESSAGE,"El  servicio de  SOS ha sido activado");
                    notifyData(event);
                }
            };
            countDownSOS.start();
        }
    }
    public interface  SosView extends BaseView{
        void onTickTimer(long segundos);
        void onTimerFinish(boolean finished);
        void setListContactos(ArrayList<Contacto> listContactos);
        void startServiceSOS();
    }
}
