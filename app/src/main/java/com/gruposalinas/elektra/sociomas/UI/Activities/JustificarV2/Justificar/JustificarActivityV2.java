package com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Justificar;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.JustificacionEnviada;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado.JustificacionSelectionActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogJustificar;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.ListaEmpleado.LstEmpleado;
import com.squareup.picasso.Picasso;

public class JustificarActivityV2 extends BaseCoreCompactActivity  implements JustificarIncidenciasPresenterImpl.JustificarIncidenciasView, View.OnClickListener, DialogJustificar.JustificacionListener {

    private ImageView imgIcono;
    private Button btnJustificar,btnRegresar;
    private TextView tvEntrada,tvSalida,tvTienes,tvDescripcion, tvHoraEntrada, tvHoraSalida,tvFecha;
    private JustificarIncidenciasPresenterImpl presenter;
    private DrawerLayout viewGroup;
    private InputMethodManager im;
    private DialogJustificar dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificar_v2);
        viewGroup= (DrawerLayout) findViewById(R.id.drawer_layout);
        setToolBar(R.string.justificaciones);
        setPresenter();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void setPresenter() {
        presenter =  new JustificarIncidenciasPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
        presenter.obtenerInfoIncidencia();
    }

    @Override
    public void initView() {
        imgIcono=(ImageView)findViewById(R.id.imgIcono);
        btnJustificar = (Button)findViewById(R.id.btnJustificar);
        btnRegresar =  (Button)findViewById(R.id.btnRegresar);
        tvEntrada = (TextView)findViewById(R.id.tvEntrada);
        tvHoraSalida=(TextView)findViewById(R.id.tvHoraSalida);
        tvHoraEntrada=(TextView)findViewById(R.id.tvHoraEntrada);
        tvFecha=(TextView)findViewById(R.id.tvFecha);
        tvSalida = (TextView)findViewById(R.id.tvSalida);
        tvTienes =  (TextView)findViewById(R.id.tvTienes);
        tvDescripcion=(TextView)findViewById(R.id.tvDescripcion);
        im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void setListeners() {
        btnRegresar.setOnClickListener(this);
        btnJustificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.btnRegresar):
                onBackPressed();
                break;
            case (R.id.btnJustificar):
                if(dialog!=null) {
                    dialog.show();
                    dialog.setBackGround(Color.WHITE);
                    dialog.setHintDialogo(getString(R.string.justificaBrevemente));
                }
                break;
        }
    }



    @Override
    public void setCurrentJustificacion(ListadoIncidencias item, boolean isPlantilla) {
        Picasso.with(this).load(item.getRecursoImagen()).into(imgIcono);
        EnumAsistencia enumAsistencia=EnumAsistencia.fromString(item.getIncidencia());
        tvHoraEntrada.setBackgroundColor(ContextCompat.getColor(this,item.getRecursoColor()));
        tvHoraSalida.setBackgroundColor(ContextCompat.getColor(this,item.getRecursoColor()));
        tvHoraEntrada.setText(item.getHoraEntrada());
        tvHoraSalida.setText(item.getHoraSalida());
        String statusAsistencia =isPlantilla? AdapterUtils.getHashMensajePlantillaAsistencias().get(enumAsistencia):
                AdapterUtils.getHashMensajeAsistencias().get(enumAsistencia);
        tvDescripcion.setText(statusAsistencia);
        tvFecha.setText(item.getFecha());
        //Creamos la instancia del dialogo
        dialog = new DialogJustificar(this, isPlantilla);
        dialog.setDia(item.getDiaNombre()).setFecha(item.getFecha())
                .setEntrada(item.getHoraEntrada())
                .setSalida(item.getHoraSalida())
                .setRecursoImagen(item.getRecursoImagen())
                .setNombreSupervisor(item.getNombreSupervisor())
                .setTipoIncidencia(AdapterUtils.getHashMensajePlantillaAsistencias().get(enumAsistencia))
                .setColorBackground(item.getRecursoColor());
        String nombreEmpleado=isPlantilla? item.getNombre(): Utils.getCurrentEmpleado(this).getName();
        String idEmpleado=isPlantilla? item.getEmpleado(): Utils.getCurrentEmpleado(this).getIdEmployee();
        dialog.setPlantilla(nombreEmpleado,idEmpleado);
        dialog.setComentario(item.getComentarioRechazo());
        dialog.setHintDialogo(isPlantilla?
                getString(R.string.justificaBrevemente).concat(" del ").concat(AdapterUtils.getHashEstatusAsistencias().get(enumAsistencia).toLowerCase()).toString():
                getString(R.string.justificaBrevemente).concat(" de tu ").concat(AdapterUtils.getHashEstatusAsistencias().get(enumAsistencia).toLowerCase()).toString());
        dialog.setDialogoJustificarListener(this);
    }

    @Override
    public void showMensajeIncidencia(String mensaje) {
            tvTienes.setText(mensaje);
    }



    @Override
    public void hideDialogJustificar() {
        if(dialog!=null) {
            dialog.dismiss();
        }
    }

    @Override
    public void sendJustificarEnviada(Bundle bundle) {
       Intent intent=new Intent(this,JustificacionEnviada.class);
       intent.putExtras(bundle);
       startActivity(intent);
    }


    @Override
    public void onJustificarIncidencia(String comentario) {
        presenter.justificarIncidencia(comentario);
    }

    @Override
    public void onAutorizarRechazarIncidenciaPlantilla(boolean autorizar, String comentario) {
            presenter.onAutorizarRechazarIncidenciaPlantilla(autorizar,comentario);
    }

    @Override
    public void onJefeSelected(LstEmpleado empleado) {
        presenter.onJefeSelected(empleado.getNombreCompleto(),empleado.getIdNumEmpleado());
    }
}
