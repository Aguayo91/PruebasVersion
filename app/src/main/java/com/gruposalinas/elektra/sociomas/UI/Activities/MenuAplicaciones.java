package com.gruposalinas.elektra.sociomas.UI.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Contactos.ContactosActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.Cuentanos.VistaWebView;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Log.LogActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.SOS.SosActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.DataBaseModel.DatosContacto;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Model.Request.Contacto.RootConfiguracion;
import com.sociomas.core.WebService.Model.Response.Contacto.ConfiguracionResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MenuAplicaciones extends BaseAppCompactActivity {
    private int taps =0;
    RelativeLayout layoutMenu;
    ImageButton Movilidad_GS, FamiliaSocio;
    ImageView panico, Actuliza;
    TextView actulizar,bienvenida, textoActualizar,Version;
    Alertas alertas;
    public static final String TAG="MENU_APLICACIONES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_aplicaciones);
        init();
        Utils.llamarBroadCastInicio(this);

        Movilidad_GS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAplicaciones.this, InicioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        FamiliaSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAplicaciones.this, VistaWebView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.PIE_PAGINA, true);
                startActivity(intent);
            }
        });

        panico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SessionManager manager=new SessionManager(MenuAplicaciones.this);
                if(manager.getString(Constants.SP_HASLOGGED, Constants.SP_NOTLOGGED).equals(Constants.SP_NOTLOGGED))
                {validarsesion();

                }else
                {
                    validar();
                }
            }
        });

    }

    public void init()
    {
        Movilidad_GS=(ImageButton)findViewById(R.id.imageButton);
        FamiliaSocio=(ImageButton)findViewById(R.id.imageButton2);
        actulizar=(TextView)findViewById(R.id.actualizar);
        bienvenida=(TextView)findViewById(R.id.bienvenido);
        panico=(ImageView)findViewById(R.id.imageButton3);
        Actuliza=(ImageView)findViewById(R.id.imageButton4);
        textoActualizar=(TextView)findViewById(R.id.textoactualizar);
        Version=(TextView)findViewById(R.id.version);
        Version.setText(Version.getText().toString()+":"+ getString(R.string.version_name));

        //SOLO PARA DEBUGEAR Y VER EL LOG DE UBICACIONES
        layoutMenu=(RelativeLayout)findViewById(R.id.layoutMenu);
        layoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taps++;
                if(taps ==3) {
                    startActivity(new Intent(MenuAplicaciones.this, LogActivity.class));
                    taps=0;
                }
            }
        });
        alertas= new Alertas(this);
        getConfiguracionAsync();

    }


    public void validarsesion(){
        alertas.displayMensaje("Debes iniciar sesión para activar botón de pánico",this);
    }


    public void validar()
    {
        DatosContacto datosContacto= new DatosContacto();

        if(!datosContacto.isguardar(this))
        {
            Intent intent= new Intent(MenuAplicaciones.this,ContactosActivityV2.class);
            startActivity(intent);
        }
        else{

            Intent intent= new Intent(MenuAplicaciones.this,SosActivity.class);
                intent.putExtra("Main",false);
                startActivity(intent);
        }

    }
    public void getConfiguracionAsync()
    {
        customProgressBar.show(this);
        controllerAPI.getConfiguracion(new RootConfiguracion()).enqueue(new Callback<ConfiguracionResponse>() {
            @Override
            public void onResponse(Call<ConfiguracionResponse> call, Response<ConfiguracionResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getError()){
                        Utils.checkIfFechaError(response.body().getServerUTCTime(),
                                response.body().getServerTime());
                    }
                    else {
                        callBackConfiguracion(response.body());
                    }
                    customProgressBar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ConfiguracionResponse> call, Throwable t) {
                customProgressBar.dismiss();
            }
        });

    }

    public void callBackConfiguracion(ConfiguracionResponse response){
        try {
            SimpleDateFormat dateFormat=new SimpleDateFormat(Constants.DATE_FORMAT_CONFIGURACION);
            Date date=dateFormat.parse(response.getFECHA());
            if(Utils.nuevaVersionDisponible(getString(R.string.version_name),response.getVERSION())){
                Movilidad_GS.setVisibility(View.INVISIBLE);
                FamiliaSocio.setVisibility(View.INVISIBLE);
                textoActualizar.setGravity(Gravity.START);
                bienvenida.setText(R.string.actualizar_no_acceso);
                Alertas alertas=new Alertas(this);
                alertas.displayTimerMensaje(this,R.string.actualiar_title,R.string.actualizar_description,response.getURL());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
