package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoIncidencia;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogOptions;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.TipsBuilder;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumIncidencia;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.CallBacks.CallBackCatalogo;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Incidencia.SolicitarJustificacion;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Incidencia.Catalogo;
import com.sociomas.core.WebService.Model.Response.Incidencia.CatalogoResponse;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import net.frederico.showtipsview.ShowTipsView;
import net.frederico.showtipsview.ShowTipsViewInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JustificarIncidenciaActivity extends BaseAppCompactActivity
        implements CallBackCatalogo, View.OnClickListener {
    private  AdapterCatalogoIncidencia adapterCatalogoIncidencia;
    private Spinner spinnerCatalogo;
    private FloatingActionButton fabCamera,fabGallery,fabCancelar;
    private ImageView imgIncidencia;
    private Alertas alerta;
    private TextView tvFechaJustificacion,tvEventoJustificacion;
    private EditText txtComentario;
    private ListadoIncidencias selectedItem;
    private Bitmap selectedBitmap=null;
    private ControllerAPI controllerAPI;
    private DialogOptions dialogOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificar_incidencia);
        spinnerCatalogo=(Spinner)findViewById(R.id.spinnerCatalogo);
        fabCamera=(FloatingActionButton)findViewById(R.id.fabCamera);
        fabGallery=(FloatingActionButton)findViewById(R.id.fabGallery);
        fabCancelar=(FloatingActionButton)findViewById(R.id.fabCancelar);
        imgIncidencia=(ImageView)findViewById(R.id.imgIncidencia);
        tvFechaJustificacion=(TextView)findViewById(R.id.tvFechaJustificacion);
        tvEventoJustificacion=(TextView)findViewById(R.id.tvEventoJustificacion);
        txtComentario=(EditText)findViewById(R.id.txtComentario);

        fabCancelar.setOnClickListener(this);
        fabCamera.setOnClickListener(this);
        fabGallery.setOnClickListener(this);

        alerta=new Alertas(this);
        dialogOptions=new DialogOptions(this);

        this.controllerAPI=new ControllerAPI(this);
        controllerAPI.getCatalogoJustificacionesAsync(new ServerRequest());
        controllerAPI.setCallBackCatalogo(this);
        setToolBar(getString(R.string.mis_justificaciones));

        if(getIntent().hasExtra(Constants.SELECTED_INCIDENCIA)){
            mostarInformacion();
        }
    }

    private  void mostarInformacion(){
        selectedItem=(ListadoIncidencias)getIntent().getSerializableExtra(Constants.SELECTED_INCIDENCIA);
        if(selectedItem!=null){
            EnumIncidencia enumIncidencia=EnumIncidencia.getFromSting(selectedItem.getEstatusJustificacion());
            tvEventoJustificacion.setText("Evento: ".concat(selectedItem.getIncidencia()));
            tvFechaJustificacion.setText("Fecha: ".concat(selectedItem.getFechaOcurrencia()));
            if(enumIncidencia==EnumIncidencia.rechazado) {
                txtComentario.setText(selectedItem.getComentarios());
                if(selectedItem.getAdjunto()!=null && (!selectedItem.getAdjunto().isEmpty())){
                    final Target target=new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                selectedBitmap=bitmap;
                                imgIncidencia.setImageBitmap(selectedBitmap);
                        }
                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    };
                    Picasso.with(JustificarIncidenciaActivity.this).load(selectedItem.getAdjunto()).resize(300,300).into(target);
                    imgIncidencia.setTag(target);
                }
            }
        }
    }

    @Override
    public void OnError(Throwable error) {
        alerta.displayMensaje(error.getMessage(),this);
    }

    @Override
    public void OnSuccess(CatalogoResponse response) {
        adapterCatalogoIncidencia=new AdapterCatalogoIncidencia(this,R.layout.spinner_opciones_plantilla,response.getLista());
        spinnerCatalogo.setAdapter(adapterCatalogoIncidencia);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabCamera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Constants.RESULT_LOAD_IMAGE_CAMERA);
                break;
            case R.id.fabGallery:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Constants.RESULT_LOAD_IMAGE);
                break;
            case R.id.fabCancelar:
                selectedBitmap=null;
                imgIncidencia.setImageBitmap(null);
                imgIncidencia.destroyDrawingCache();
                fabCancelar.setVisibility(View.GONE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==Constants.RESULT_LOAD_IMAGE){
                try {
                    Picasso.with(JustificarIncidenciaActivity.this).load(data.getData()).noPlaceholder().centerCrop().fit()
                            .into(imgIncidencia);
                    selectedBitmap= Utils.getThumbnailBitmap(this,data.getData());
                    fabCancelar.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode==Constants.RESULT_LOAD_IMAGE_CAMERA){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                selectedBitmap=imageBitmap;
                imgIncidencia.setImageBitmap(imageBitmap);
                fabCancelar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_justificar_incidencia, menu);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final SessionManager sessionManager=new SessionManager(JustificarIncidenciaActivity.this);
                if(!sessionManager.get(Constants.HAS_SHOW_TIP)) {
                    View menuGuardar = (View) findViewById(R.id.action_guardar);
                    ShowTipsView tipsBuilder = TipsBuilder.show(JustificarIncidenciaActivity.this, menuGuardar, "Solicitar justificación",
                            "Presiona el siguiente botón para solicitar la justificación de tu incidencia");
                    tipsBuilder.setCallback(new ShowTipsViewInterface() {
                        @Override
                        public void gotItClicked() {
                            sessionManager.add(Constants.HAS_SHOW_TIP,true);
                        }
                    });
                }
            }
        },600);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_guardar:
                validarIncidencia();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validarIncidencia(){
        final Catalogo selectedItemSpinner=(Catalogo) spinnerCatalogo.getSelectedItem();
        if(selectedItemSpinner.getValor()==-1){
            alerta.showConfirmar(this,getString(R.string.seleccione_tipo_justificacion));
        }
        else if(txtComentario.getText().toString().isEmpty()){
            alerta.showConfirmar(this,getString(R.string.comentario_vacio));
        }
        else{
            dialogOptions.show(getString(R.string.solicitar_justificacion));
            dialogOptions.setCallBackDialgoOptions(new DialogOptions.CallBackDialgoOptions() {
                @Override
                public void OnDismiss(boolean accepted) {
                    if(accepted){
                        justificarIncidencia(selectedItemSpinner);
                    }
                }
            });

        }
    }

    private void justificarIncidencia(Catalogo selectedItemSpinner){
        final CustomProgressBar customProgressBar=new CustomProgressBar(this);
        customProgressBar.show(this);
        SolicitarJustificacion solicitarJustificacion =new SolicitarJustificacion();
        String base64 = selectedBitmap!=null?
                Utils.encodeToBase64(selectedBitmap, Bitmap.CompressFormat.JPEG, 80):null;
        solicitarJustificacion.setIdCscIncid(selectedItem.getCSC());
        solicitarJustificacion.setExtension("JPEG");
        solicitarJustificacion.setVaComentarios(txtComentario.getText().toString());
        solicitarJustificacion.setArchivoAdjunto(base64);
        solicitarJustificacion.setNombreArchivo("imagenDroid");
        solicitarJustificacion.setTamanoArchivo("0");
        solicitarJustificacion.setIdJustificacion(selectedItemSpinner.getValor());
        solicitarJustificacion.setBitTempFija(getIntent().getBooleanExtra(Constants.IS_TEMP_FIJA,false));
        solicitarJustificacion.setIdNumEmpleadoJustifica(selectedItem.getEmpleado());
        controllerAPI.agregarJustificacion(solicitarJustificacion).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        dialogOptions.showExito();
                        dialogOptions.setCallBackDialgoOptions(new DialogOptions.CallBackDialgoOptions() {
                            @Override
                            public void OnDismiss(boolean accepted) {
                                if(accepted){
                                    boolean isTempFIja=getIntent().getBooleanExtra(Constants.IS_TEMP_FIJA,false);
                                    Intent intent= new Intent(JustificarIncidenciaActivity.this,IncidenciaTabActivity.class);
                                    startActivity(intent);

                                }
                            }
                        });
                    }
                    else{
                        alerta.displayMensaje(getString(R.string.Error_Conexion),JustificarIncidenciaActivity.this);
                    }

                    customProgressBar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

}
