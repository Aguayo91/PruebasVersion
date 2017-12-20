package com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado.JustificacionSelectionActivity;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;

public class JustificacionEnviada extends BaseCoreCompactActivity implements View.OnClickListener {
    Bundle extras;
    public JustificacionEnviadaPresenteImpl presenter;
    String jefeNombre, fecha, nombreSocio;
    private Button btnContinuar;
    private TextView tvJefeName,tvFecha,tvTitulo,tvJustificacion, tvEspera,tvFecha2, tvSocio2, tvSeenviara;
    ImageView imgManos;
    private boolean plantilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificacion_enviada);
        setPresenter();
    }

    @Override
    public void setPresenter() {
        super.setPresenter();
        presenter =  new JustificacionEnviadaPresenteImpl();
        presenter.register(this);
    }

    @Override
    public void initView() {
        super.initView();
        extras = getIntent().getExtras();
        imgManos = (ImageView)findViewById(R.id.imgManos);
        tvTitulo = (TextView)findViewById(R.id.tvFelicidades);
        tvJefeName=(TextView)findViewById(R.id.tvHasConfigurado);
        tvFecha = (TextView)findViewById(R.id.tvFecha);
        tvJustificacion = (TextView)findViewById(R.id.tvTuJustificacion);
        tvEspera = (TextView)findViewById(R.id.espera);
        tvFecha2 = (TextView)findViewById(R.id.tvFechaRechazada);
        tvSocio2 = (TextView)findViewById(R.id.tvSocioRechazado);
        tvSeenviara = (TextView)findViewById(R.id.tvSeEnviara);
        btnContinuar=(Button)findViewById(R.id.boton_continuar);
        btnContinuar.setOnClickListener(this);

        if(extras!=null){
            ListadoIncidencias item =(ListadoIncidencias)extras.getSerializable(Constants.SELECTED_INCIDENCIA);
            jefeNombre = item.getNombreSupervisor();
            fecha = item.getFecha();
            nombreSocio = item.getNombre();
            boolean rechazo=extras.getBoolean(Constants.IS_RECHAZAR);
            plantilla = extras.getBoolean(Constants.IS_PLANTILLA);
            if(plantilla) {
                if (rechazo) {
                    imgManos.setImageResource(R.drawable.ic_cara_rechazo);//Recurso de la cara con taches
                    tvTitulo.setText("Acabas de rechazar una justificación");
                    tvJefeName.setVisibility(View.INVISIBLE);
                    tvFecha.setVisibility(View.INVISIBLE);
                    tvFecha2.setVisibility(View.VISIBLE);
                    tvFecha2.setText(fecha);
                    tvJustificacion.setVisibility(View.INVISIBLE);
                    tvSocio2.setVisibility(View.VISIBLE);
                    tvSocio2.setText("Socio: "+nombreSocio);
                    tvSeenviara.setVisibility(View.VISIBLE);

                }else {
                    tvTitulo.setText("¡Justificación exitosa!");
                    tvJefeName.setText(fecha);
                    tvJefeName.setTextColor(ContextCompat.getColor(this,R.color.gris_info));
                    tvJustificacion.setText("Socio:"+nombreSocio);
                    tvFecha.setVisibility(View.INVISIBLE);
                    tvEspera.setText("Gracias");
                }
            }else {
                tvJefeName.setText(Html.fromHtml("Se ha enviado a <b>" + jefeNombre + "</b>"));
                tvFecha.setText(fecha);
            }

        }
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boton_continuar:
                Intent i=new Intent(this,JustificacionSelectionActivity.class);
                i.putExtra(Constants.IS_PLANTILLA,plantilla);
                startActivity(i);
                finish();
                break;
        }
    }
}
