package com.sociomas.aventones.UI.Activities;

import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

import com.sociomas.aventones.R;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.CallBacks.CallBackTimezone;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerTimezone;

import java.util.Calendar;

public class SampleActivity extends BaseCoreCompactActivity implements CallBackTimezone {

    private TextClock tvTimeServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        tvTimeServer=(TextClock)findViewById(R.id.tvTimeServer);
        // Create menu in code
        ControllerTimezone controllerTimezone= new ControllerTimezone(this);
        controllerTimezone.revisarHora();
        controllerTimezone.setCallBackTimezone(this);

        }

    @Override
    public void OnSuccess(Calendar calendarApi) {

        String callBackTime = String.valueOf(calendarApi.getTime());
        String parts[]=callBackTime.split(" ");
        String hora=parts[3];
        tvTimeServer.setFormat24Hour("kk:mm:ss");

        tvTimeServer.setTimeZone(hora);
    }

    @Override
    public void OnError(Throwable error) {

        showToast("Error de petición al servidor");

    }
}
