package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.AsistenciaHoy;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Controls.AsistenciaHoyControl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;

public class MisAsistenciasHoyActivity extends BaseCoreCompactActivity  implements MisAsistenciasHoyPresenterImpl.MisAsistenciasHoyView{

    private Button btnOtraFecha;
    private AsistenciaHoyControl miAsistencia;
    private TextView tvNombre,tvNumero;
    private MisAsistenciasHoyPresenter presenter;
    private static final String TAG = "MisAsistenciasHoyActivi";
    private ImageView imgUsuario,imgUsuarioAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_asistencias_hoy);
        setToolBar(R.string.asistencias);
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
                    NavUtils.navigateUpFromSameTask(MisAsistenciasHoyActivity.this);
                }
            });
        }
    }
    @Override
    public void initView() {
        tvNombre = (TextView)findViewById(R.id.tvNombreSocio);
        tvNumero = (TextView)findViewById(R.id.tvNumeroSocio);
        btnOtraFecha = (Button)findViewById(R.id.btnOtraFecha);
        miAsistencia = (AsistenciaHoyControl)findViewById(R.id.ControlAsistencia);
        imgUsuario=(ImageView)findViewById(R.id.imgUsuario);
        imgUsuarioAvatar=(ImageView)findViewById(R.id.imgUsuarioAvatar);
    }
    @Override
    public void setListeners() {
        btnOtraFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MisAsistenciasHoyActivity.this,MisAsistenciasActivityV2.class);
                Bundle bundle=new Bundle();
                bundle.putBoolean(Constants.IS_PLANTILLA, presenter.isPlantilla());
                bundle.putSerializable(ViewEvent.ENTRY, presenter.getSelectedEmpleado());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
    @Override
    public void setPresenter() {
        presenter=new MisAsistenciasHoyPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
        presenter.obtenerAsistenciasHoy();
    }
    @Override
    public void setDiaHoyAsistencia(ResultadoAsistencia r) {
        if(r!=null){
            tvNombre.setText(r.getNombreEmpleado());
            tvNumero.setText(getString(R.string.idEmpleadoBienvenida,r.getNumeroEmpleado()));
            miAsistencia.setAsistencia(r.getHoraEntrada(),EnumAsistencia.fromValue(r.getIdEstado()));
            loadImagenPerfil(r.getNumeroEmpleado());
        }
    }

    @Override
    public void setInfoEmpleado(String nombre, String numeroEmpleado) {
        tvNombre.setText(nombre);
        tvNumero.setText(getString(R.string.idEmpleadoBienvenida,numeroEmpleado));
        miAsistencia.setAsistencia(EnumAsistencia.TODAVIA_NO_TERMINA_DIA);
        loadImagenPerfil(numeroEmpleado);
    }

    private void loadImagenPerfil(String numeroEmpleado){
        if(numeroEmpleado.equals(Utils.getNumeroEmpleado(this))){
            Bitmap selectedImagenPerfil = com.gruposalinas.elektra.sociomas.Utils.Utils.getImagenPerfilWallpaper(this, false);
            if(selectedImagenPerfil!=null){
                imgUsuario.setImageBitmap(selectedImagenPerfil);
            }
            else{
                imgUsuario.setVisibility(View.INVISIBLE);
                imgUsuarioAvatar.setVisibility(View.VISIBLE);
            }

        }
        else{
            imgUsuario.setVisibility(View.INVISIBLE);
            imgUsuarioAvatar.setVisibility(View.VISIBLE);
        }
    }

}
