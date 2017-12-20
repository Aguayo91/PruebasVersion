package com.sociomas.core.UI.Controls.Notification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sociomas.core.R;
import com.sociomas.core.UI.Controls.Model.EnumNavegacion;

/**
 * Created by oemy9 on 27/07/2017.
 */

public class FooterNavegacion extends RelativeLayout implements View.OnClickListener {


    public interface  ViewMasListener{
         void OnOptionSelected(EnumNavegacion navegacion);
    }

    private LinearLayout layoutMovilidad,layoutAdministracion,layoutSeguridad,layoutSOS;

    ViewMasListener listener;

    public void setListener(ViewMasListener listener) {
        this.listener = listener;
    }

    public FooterNavegacion(Context context) {
        super(context);
        inflateLayout(context);
    }

    public FooterNavegacion(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context);
    }

    public FooterNavegacion(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context);
    }

    public  void inflateLayout(Context context){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.footer_navegacion,this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.layoutMovilidad=(LinearLayout)findViewById(R.id.layoutMovilidad);
        this.layoutSeguridad=(LinearLayout)findViewById(R.id.layoutSeguridad);
        this.layoutAdministracion=(LinearLayout)findViewById(R.id.layoutAdministracion);
        this.layoutSOS=(LinearLayout)findViewById(R.id.layoutSOS);
        this.layoutSOS.setOnClickListener(this);
        this.layoutAdministracion.setOnClickListener(this);
        this.layoutSeguridad.setOnClickListener(this);
        this.layoutMovilidad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
            if (i == R.id.layoutMovilidad) {
                callListener(EnumNavegacion.MOVIILIDAD);

            } else if (i == R.id.layoutSeguridad) {
                callListener(EnumNavegacion.SEGURIDAD);

            } else if (i == R.id.layoutAdministracion) {
                callListener(EnumNavegacion.ADMINISTRACION);

            } else if (i == R.id.layoutSOS) {
                callListener(EnumNavegacion.SOS);
            }
                    /*
                    DatosContacto datosContacto= new DatosContacto();
                    if(datosContacto.isguardar(getContext())){
                            if(Utils.servicioEjecutando(getContext(),SOService.class) ||
                                    Utils.servicioEjecutando(getContext(), SOSAudioService.class)) {

                                getContext().startActivity(new Intent(getContext(), DesactivarActivity.class));
                            }
                            else{
                                getContext().startActivity(new Intent(getContext(),SosActivity.class));
                            }
                    }
                    else{
                            getContext().startActivity(new Intent(getContext(),ContactosActivityV2.class));
                    }*/


    }

    private void callListener(EnumNavegacion enumNavegacion){
        if(listener!=null){
            listener.OnOptionSelected(enumNavegacion);
        }
    }

}
