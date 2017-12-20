package com.sociomas.aventones.UI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.RutaDisponible.RutaAventonesDisponibles;
import com.sociomas.aventones.UI.Adapters.AdaptadorEncuentra;
import com.sociomas.aventones.UI.presenters.AventonesReservadosPresenter;
import com.sociomas.aventones.UI.presenters.impl.AventonesReservadosPresenterImpl;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import com.sociomas.core.WebService.Model.Response.Aventones.ConsultaAventonesResponse;
import java.util.List;

public class AventonesReservadosActivity extends BaseCoreCompactActivity implements AventonesReservadosPresenterImpl.AventonesReservadosView {

    public static final String TAG = AventonesReservadosActivity.class.getSimpleName();
    private ConsultaAventonesResponse consultaAventonesResponse;
    private RecyclerView recyclerView;
    Aventones aventon;
    private Aventones selectedAventon;
    List<Aventones> aventones;
    private AventonesReservadosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aventones_reservados);
        ApplicationAventon.onCreate(getApplicationContext());
        setPresenter();
        presenter.register(this);
        setToolBar(R.string.misAventonesReservados);
        presenter.getAventonesReservados();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvAventonesReservados);
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void setPresenter() {
        presenter = new AventonesReservadosPresenterImpl();
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public List<Aventones> llenarLista(List<Aventones> lista) {
        this.aventones = lista;
        AdaptadorEncuentra adaptadorEncuentra = new AdaptadorEncuentra(AventonesReservadosActivity.this, aventones);
        adaptadorEncuentra.setMostrarInfoAdicional(true);
        adaptadorEncuentra.setonLlamadaClickListener(new AdaptadorEncuentra.onLlamada() {
            @Override
            public void onLlamar(int position, Object selectedItem, String numero) {
                String numero_ok = numero.substring(5, numero.length());
                Utils.callPhone(AventonesReservadosActivity.this, numero_ok);
            }
        });
        adaptadorEncuentra.setItemClickListener(new AdaptadorEncuentra.onRutaAdapterClickListener() {

            @Override
            public void onMostrarRuta(Object selectedItem) {
                selectedAventon = (Aventones) selectedItem;
                Intent intent = new Intent(AventonesReservadosActivity.this, RutaAventonesDisponibles.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ViewEvent.ENTRY, selectedAventon);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onMostrarObservaciones(Object selectedItem) {
                selectedAventon = (Aventones) selectedItem;
              new   AlertDialog.Builder(AventonesReservadosActivity.this).setTitle("Observaciones")
                      .setMessage(selectedAventon.getObservaciones())
                      .setPositiveButton(R.string.aceptar,null)
                      .show();
            }

            @Override
            public void OnItemClickListener(int position, Object selectedItem) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorEncuentra);
        return aventones;

    }
}
