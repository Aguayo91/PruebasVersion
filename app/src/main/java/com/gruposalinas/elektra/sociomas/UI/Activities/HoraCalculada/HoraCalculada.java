package com.gruposalinas.elektra.sociomas.UI.Activities.HoraCalculada;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Security.SecureDate;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HoraCalculada extends BaseCoreCompactActivity implements Consumer<Long> {
    private TextView tvHoraActual,tvRealTime,tvRealTimeGuardado,tvHoraCalculada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hora_calculada);
        tvHoraActual=(TextView)findViewById(R.id.tvHoraActual);
        tvHoraCalculada=(TextView)findViewById(R.id.tvHoraCalculada);
        setToolBar("Hora");
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }

    @Override
    public void accept(Long aLong) throws Exception {
        SecureDate secureDate=new SecureDate(this);
        tvHoraActual.setText(secureDate.getHourDispositivoCalculadaString());
        tvHoraCalculada.setText(secureDate.getHourCalculadaString());
    }
}
