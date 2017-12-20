package com.gruposalinas.elektra.sociomas.UI.Activities.Rifa;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioInteractor;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumEstatusNotificacion;
import com.squareup.picasso.Picasso;
public class RifaActivity extends BaseCoreCompactActivity{

    private ImageView imgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rifa);
        setToolBar(getString(R.string.ganaste));
        initView();
        checkIfNotificacion();
    }

    @Override
    public void initView(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        imgTv=(ImageView)findViewById(R.id.imgTv);
        Picasso.with(this).load(R.drawable.ganaste_tv).resize(width,height).into(imgTv);
    }

    private void checkIfNotificacion(){
        if(getIntent().hasExtra(Constants.ID_NOTIFICACION)){
           int idNotificacion=getIntent().getIntExtra(Constants.ID_NOTIFICACION,0);
            InicioInteractor inicioInteractor = new InicioInteractor();
            inicioInteractor.modificarEstatusNotificacion(EnumEstatusNotificacion.LEIDA,idNotificacion);
        }
    }
}
