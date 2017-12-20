package com.gruposalinas.elektra.sociomas.UI.Activities.Notificaciones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.AdapterBuzon;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Nomina.ItemDecorations.ItemDecorationLineDashed;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogConfirmarNotificaciones;
import com.gruposalinas.elektra.sociomas.UI.Presenters.BuzonPresenter;
import com.gruposalinas.elektra.sociomas.UI.Presenters.impl.BuzonPresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumEstatusNotificacion;
import com.sociomas.core.WebService.Model.Response.Notificaciones.NotificacionResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BuzonActivity extends BaseCoreCompactActivity implements BaseView {

    RecyclerView rv;
    private BuzonPresenter presenter;
    public static boolean refreshView = false;
    private SwipeRefreshLayout swipeRefreshBuzon;
    private View layoutEmpty;
    private ImageView imgBuzon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzon);
        setToolBar(R.string.notificaciones);
        setPresenter();
        presenter.register(this);
    }

    public ArrayList<String> mensajes() {
        ArrayList mensaje1 = new ArrayList();
        mensaje1.add("Tu jefe ha aceptado tu justificación");
        mensaje1.add("Tu jefe ha rechazado tu justificación");
        mensaje1.add("Recuerda Realizar la configuración de tu avatar Socio MAS");
        mensaje1.add("Tienes una Justificacion pendiente");
        mensaje1.add("Tienes una nueva justificacion pendiente");
        return mensaje1;
    }

    public ArrayList<Calendar> calendars() {
        ArrayList calendario = new ArrayList();
        calendario.add(Calendar.getInstance());
        calendario.add(Calendar.getInstance());
        calendario.add(Calendar.getInstance());
        calendario.add(Calendar.getInstance());
        calendario.add(Calendar.getInstance());
        return calendario;
    }

    public ArrayList<Boolean> leido() {
        ArrayList leido = new ArrayList();
        leido.add(true);
        leido.add(false);
        leido.add(false);
        leido.add(true);
        leido.add(true);
        return leido;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(getString(com.sociomas.core.R.string.action_cofiguraciones)));
                break;
        }
        return true;
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case PRESENT_RESULTSET_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.rvBuzon: {
                        List<NotificacionResponse> list = (List<NotificacionResponse>) event.getModel().get(ViewEvent.ENTRIES_LIST);
                        setDataRecycler(list);
                        swipeRefreshBuzon.setRefreshing(false);
                        refreshView = false;
                        rv.setVisibility(View.VISIBLE);
                        layoutEmpty.setVisibility(View.GONE);
                    }
                    break;
                }
            }
           break;
            case SHOW_LAYOUT_ELEMENT:
            {
                rv.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.VISIBLE);
            }
            break;

            case PRESENT_OBJECT_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.rvBuzon : {
                        boolean borrada = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                        if (borrada) {
                            presenter.consultarNotificacionesUsuario(getActivityInstance());
                        } else {
                            String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                            if (msg != null) {
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;
                    case R.id.tvTitle: {
                        NotificacionResponse nr = (NotificacionResponse) event.getModel().get(ViewEvent.ENTRY);
                        if (nr != null) {
                            refreshView = nr.getId_estatus_notificacion() == EnumEstatusNotificacion.LEIDA.getValue() ? false : true;
                            Intent detalleNotify = new Intent(nr.getClickAction());
                            detalleNotify.putExtra(Constants.DATA_SEND, nr.getDatos_movil());
                            Bundle bundle = new Bundle();
                            bundle.putString("titulo", nr.getTitulo());
                            bundle.putString("mensaje", nr.getMsg_notificacion());
                            bundle.putSerializable(ViewEvent.ENTRY, nr);
                            detalleNotify.putExtras(bundle);
                            getActivityInstance().startActivity(detalleNotify);
                        } else {
                            refreshView = false;
                            String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                            showToast(msg);
                        }
                    }
                    break;

                }
            }
            break;
            case ERROR_EVENT_TYPE: {
                String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                swipeRefreshBuzon.setRefreshing(false);
            }
            break;


        }
    }

    @Override
    public void initView() {
        super.initView();
        rv = (RecyclerView) findViewById(R.id.rvBuzon);
        swipeRefreshBuzon = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshBuzon);
        imgBuzon=(ImageView)findViewById(R.id.imgBuzon);
        layoutEmpty=findViewById(R.id.layoutEmpty);
        presenter.consultarNotificacionesUsuario(this);
    }

    public void setDataRecycler(List<NotificacionResponse> list) {
        AdapterBuzon adapter = new AdapterBuzon(this, list, presenter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void setListeners() {
        super.setListeners();
        swipeRefreshBuzon.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshBuzon.setRefreshing(true);
                presenter.consultarNotificacionesUsuario(getActivityInstance());
            }
        });
    }

    @Override
    public void setPresenter() {
        super.setPresenter();
        presenter = new BuzonPresenterImpl();
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refreshView) {
            presenter.consultarNotificacionesUsuario(getActivityInstance());
        }
    }
}
