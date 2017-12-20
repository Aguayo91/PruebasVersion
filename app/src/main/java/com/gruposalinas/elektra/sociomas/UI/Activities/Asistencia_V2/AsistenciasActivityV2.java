package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.AsistenciaHoy.MisAsistenciasHoyActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado.JustificacionSelectionActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogEmpleadoPicker;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogPicture;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Controls.Buttons.ButtonCuadrado;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;

public class AsistenciasActivityV2 extends BaseCoreCompactActivity implements View.OnClickListener,DialogPicture.onPictureOptionSelectedListener, RecyclerItemClickListener {

    private ButtonCuadrado btnAsistencias,btnJustificaciones;
    private TextView tvNombre, tvSaludo;
    private DialogPicture dialog;
    private boolean selectedAsistencias;
    private AsistenciasActivityPresenterV2 presenter;
    private DialogEmpleadoPicker dialogEmpleadoPicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencias_v2);
        setToolBar(getString(R.string.Asistencias));
        setPresenter();
    }

    @Override
    public void setListeners() {
        btnJustificaciones.setOnClickListener(this);
        btnAsistencias.setOnClickListener(this);
        presenter.seleccionaUnSaludo(this);
    }

    @Override
    public void initView() {
        btnAsistencias=(ButtonCuadrado)findViewById(R.id.btnAsistencias);
        btnJustificaciones=(ButtonCuadrado)findViewById(R.id.btnJustificaciones);
        tvNombre = (TextView)findViewById(R.id.tvNombre);
        tvSaludo = (TextView)findViewById(R.id.tvBienvenida);
        tvNombre.setText(Utils.toUppperCaseFirst(Utils.getJustNombreEmpleado(this)));
        this.dialog=new DialogPicture(this);
        this.dialog.setOnPictureSelectedListener(this);
        this.dialog.setShowBtnDelete(false);

    }
    @Override
    public void presentEvent(ViewEvent event) {
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE:{
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.tvTitleMensaje: {
                        String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                        tvSaludo.setText(msg);
                    }
                    break;
                }
            }
            break;
        }
    }
    @Override
    public void setPresenter() {
        presenter = new AsistenciasActivityPresenterV2();
        presenter.register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAsistencias:
                navegarAsistencias();
                break;
            case R.id.btnJustificaciones:
                navegarJustificaciones();
                break;
        }
    }

    private void navegarAsistencias(){
        selectedAsistencias = true;
        if(presenter.hasPlantilla()) {
            this.dialog.show();
            this.dialog.setTextos(getString(R.string.mis_asistencias_v2), getString(R.string.mi_plantilla));
            this.dialog.setDrawable(R.drawable.ic_mis_asistencias,R.drawable.ic_mi_plantilla);
        }
        else {
            Intent i = new Intent(AsistenciasActivityV2.this,MisAsistenciasHoyActivity.class);
            i.putExtra(Constants.IS_PLANTILLA,false);
            startActivity(i);
        }
    }

    private void navegarJustificaciones(){
        selectedAsistencias = false;
        if(presenter.hasPlantilla()) {
            this.dialog.show();
            this.dialog.setTextos(getString(R.string.mis_justificaciones), getString(R.string.mi_plantilla));
            this.dialog.setDrawable(R.drawable.ic_mis_asistencias,R.drawable.ic_mi_plantilla);
        }
        else{
            Intent i = new Intent(AsistenciasActivityV2.this, JustificacionSelectionActivity.class);
            i.putExtra(Constants.IS_PLANTILLA, false);
            startActivity(i);
        }
    }

    @Override
    public void onGallerySelected() {
            dialogEmpleadoPicker=new DialogEmpleadoPicker(this);
            dialogEmpleadoPicker.setItemClickListener(this);
            dialogEmpleadoPicker.show();
            dialogEmpleadoPicker.initDialogPlantilla(getString(R.string.selecciona_consultar),false);
    }

    @Override
    public void onCameraSelected() {
        if(selectedAsistencias) {
            Intent i = new Intent(AsistenciasActivityV2.this,MisAsistenciasHoyActivity.class);
            i.putExtra(Constants.IS_PLANTILLA,false);
            startActivity(i);
        }
        else{
            Intent i = new Intent(AsistenciasActivityV2.this, JustificacionSelectionActivity.class);
            i.putExtra(Constants.IS_PLANTILLA, false);
            startActivity(i);
        }
    }

    @Override
    public void onDeleteSelected() {

    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
            if(selectedItem!=null){
                dialogEmpleadoPicker.dismiss();
                Plantilla pt=(Plantilla)selectedItem;
                Intent i = new Intent(AsistenciasActivityV2.this,
                        selectedAsistencias?  MisAsistenciasHoyActivity.class: JustificacionSelectionActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(ViewEvent.ENTRY,pt);
                bundle.putBoolean(Constants.IS_PLANTILLA,true);
                i.putExtras(bundle);
                startActivity(i);
            }
    }
}
