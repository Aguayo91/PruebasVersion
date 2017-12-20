package com.sociomas.aventones.UI.Activities.Disponibles;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.RutaDisponible.RutaAventonesDisponibles;
import com.sociomas.aventones.UI.Activities.SolicitarAventon.SolicitarAventonScreen;
import com.sociomas.aventones.UI.Adapters.AdaptadorEncuentra;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogAceptarCancelar;
import com.sociomas.aventones.UI.Controls.ItemDecorationRecyclers.TransparentItemDecoration;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import java.util.List;

public class AventonesDisponiblesActivity extends BaseCoreCompactActivity implements
        AventonDisponiblePresenterImpl.AventonDisponibleView,
        AdaptadorEncuentra.onRutaAdapterClickListener, DialogAceptarCancelar.OnDialogConfirmarListener {

    private AventonDisponiblePresenter presenter;
    private RecyclerView recyclerView;
    private Aventones selectedAventon;
    private DialogAceptarCancelar dialogSol;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aventones_disponibles);
        setToolBar(getString(R.string.aventones_disponibles));
        setPresenter();
    }
    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
    }

    @Override
    public void setListeners() {
        super.setListeners();
    }

    @Override
    public void setPresenter() {
        presenter=new AventonDisponiblePresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
    }

    public void solAventon(View v){

    }

    @Override
    public void setListAventonesDisponibles(List<Aventones> listAventones) {
        recyclerView.setHasFixedSize(true);
        AdaptadorEncuentra adaptadorEncuentra = new AdaptadorEncuentra(AventonesDisponiblesActivity.this, listAventones);
        adaptadorEncuentra.setItemClickListener(this);
        AlphaInAnimationAdapter alphaInAnimationAdapter=new AlphaInAnimationAdapter(adaptadorEncuentra);
        alphaInAnimationAdapter.setDuration(300);
        recyclerView.addItemDecoration(new TransparentItemDecoration(AventonesDisponiblesActivity.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorEncuentra);
    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        selectedAventon=(Aventones)selectedItem;
        dialogSol=new DialogAceptarCancelar(AventonesDisponiblesActivity.this);
        dialogSol.setOnDialogoListener(this);
        dialogSol.show();
    }

    @Override
    public void hideDialogoConfirmarAventon() {
            if(dialogSol!=null){
                dialogSol.dismiss();
            }
    }


    @Override
    public void onMostrarRuta(Object selectedItem) {
        selectedAventon=(Aventones)selectedItem;
        Intent intent = new Intent(AventonesDisponiblesActivity.this, RutaAventonesDisponibles.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ViewEvent.ENTRY,selectedAventon);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onMostrarObservaciones(Object selectedItem) {
        selectedAventon=(Aventones)selectedItem;
        new   AlertDialog.Builder(AventonesDisponiblesActivity.this).setTitle("Observaciones")
                .setMessage(selectedAventon.getObservaciones())
                .setPositiveButton(R.string.aceptar,null)
                .show();
    }

    @Override
    public void onConfirmarDialog(boolean confirmado) {
        if(confirmado){
            presenter.solicitarAventon(selectedAventon);
        }
    }

    @Override
    public void navegarSuccess() {
        Intent intent=new Intent(this, SolicitarAventonScreen.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(ViewEvent.ENTRY,selectedAventon);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}