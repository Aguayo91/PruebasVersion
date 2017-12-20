package com.gruposalinas.elektra.sociomas.UI.Activities.Notificaciones;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Notificaciones.NotificacionInfoResponse;
import com.sociomas.core.WebService.Model.Response.Notificaciones.NotificacionResponse;

public class NotificacionesExtendidas extends BaseCoreCompactActivity{

    private TextView tvTitulo, tvMensaje;
    String titulo,mensaje;
    public NotificacionesExtendidasPresenter presenter;
    private NotificacionResponse notificacionResponse;
    private NotificacionInfoResponse notificacionInfoResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones_extendidas);
        setToolBar("Notificaciones");
        setPresenter();
        presenter.register(this);
    }

    @Override
    public void initView() {
        super.initView();
        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        tvMensaje = (TextView)findViewById(R.id.tvMensaje);
        Bundle extras = getIntent().getExtras();
        if (getIntent().hasExtra(Constants.DATA_SEND)) {
            notificacionInfoResponse= new Gson().fromJson(getIntent().getStringExtra(Constants.DATA_SEND), NotificacionInfoResponse.class);
            titulo = String.valueOf(notificacionInfoResponse.getTitle());
            mensaje = String.valueOf(notificacionInfoResponse.getBody());
            notificacionResponse = new NotificacionResponse();
            if (getIntent().hasExtra(Constants.ID_NOTIFICACION)) {
                notificacionResponse.setId_estatus_usuario_notificacion(getIntent().getIntExtra(Constants.ID_NOTIFICACION,0));
                presenter.modificarEstatusNotificacion(this, notificacionResponse);
            } else {
                showMsgDialog(this, "Aviso", "Ocurrio un problema al consultar la información.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, "Aceptar");
            }
        } else {
            if (extras != null) {
                notificacionResponse = (NotificacionResponse) extras.getSerializable(ViewEvent.ENTRY);
                titulo = String.valueOf(notificacionResponse.getTitulo());
                mensaje = String.valueOf(notificacionResponse.getMsg_notificacion());
            }
        }
        if(titulo!=null) {
            tvTitulo.setText(titulo);
        }
        if(mensaje!=null){
            tvMensaje.setText(mensaje);
        }

        if (notificacionResponse != null) {
            //
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivityInstance());
            builder.setTitle("Aviso");
            builder.setMessage(getActivityInstance().getString(R.string.server_error));
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.create().show();
        }


    }

    @Override
    public void setPresenter() {
        super.setPresenter();
        presenter = new NotificacionesExtendidasPresenterImplement();
    }

    @Override
    public void setListeners() {
        super.setListeners();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.tvTitle: {
                        boolean leida = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                        BuzonActivity.refreshView = leida;
                    }
                    break;
                }
            }
            break;
        }
    }
}
