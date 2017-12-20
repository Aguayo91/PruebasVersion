package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Listado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Categorias.PromoCatPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Detalle.PromocionDetalleActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones.AdapterPromocion;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones.AdapterPromocionV2;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoLista;

import java.util.ArrayList;

public class PromocionesListadoV2 extends BaseCoreCompactActivity implements PromoCatPresenterImpl.PromocionesView, AdapterView.OnItemClickListener {

    private ListView listViewPromocion;
    private PromoListPresenter presenter;
    private AdapterPromocionV2 adapterPromocion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocion_listado);
        setPresenter();
    }

    @Override
    public void initView() {
        this.listViewPromocion=(ListView)findViewById(R.id.listViewPromocion);
    }

    @Override
    public void setListeners() {
        this.listViewPromocion.setOnItemClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter=new PromoListPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
        presenter.obtenerPromociones();
    }

    @Override
    public void setListPromociones(ArrayList<PromoLista> listPromociones) {
        if(listPromociones!=null && (!listPromociones.isEmpty())){
            adapterPromocion=new AdapterPromocionV2(this,listPromociones);
            ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapterPromocion);
            scaleInAnimationAdapter.setAbsListView(listViewPromocion);
            listViewPromocion.setAdapter(scaleInAnimationAdapter);
        }
        customProgressBar.dismiss();
    }

    @Override
    public void changeToolbarTitle(String title) {
        setToolBar(title);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,PromocionDetalleActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(ViewEvent.ENTRY,adapterPromocion.getItem(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
