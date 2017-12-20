package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.AsistenciaConsultas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias.AdapterMisAsistenciasV2;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;
import java.util.List;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class AsistenciaConsultasActivity extends BaseCoreCompactActivity implements MisAsistenciasPresenterImpl.MisAsistenciasView, View.OnClickListener {
    private AdapterMisAsistenciasV2 mAdapter;
    private AsistenciaConsultasPresenter presenter;
    private RecyclerView recyclerAsistencias;
    private Button btnOtraC;
    private TextView tvCalendarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_consultas);
        setToolBar(getString(R.string.Asistencias));
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
                    NavUtils.navigateUpFromSameTask(AsistenciaConsultasActivity.this);
                }
            });
        }
    }
    @Override
    public void setPresenter() {
        presenter=new AsistenciaConsultaPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
    }

    @Override
    public void setListeners() {

    }


    @Override
    public void initView(){
        recyclerAsistencias=(RecyclerView)findViewById(R.id.recyclerAsistencias);
        tvCalendarTitle=(TextView)findViewById(R.id.tvCalendarTitle);
        btnOtraC=(Button)findViewById(R.id.btnOtraC);
        btnOtraC.setOnClickListener(this);
    }

    @Override
    public void showInicioFecha(String fechaInicio) {

    }

    @Override
    public void showFinFecha(String fechaFin) {

    }

    @Override
    public void setListAsistencias(List<ResultadoAsistencia> listAsistencias, String fechaInicio, String fechaFin) {
        tvCalendarTitle.setText(fechaInicio.concat(" - ").concat(fechaFin));
        if(listAsistencias!=null){
            mAdapter=new AdapterMisAsistenciasV2(this,listAsistencias);
            AlphaInAnimationAdapter alphaAdpt=new AlphaInAnimationAdapter(mAdapter);
            alphaAdpt.setDuration(1000);
            recyclerAsistencias.setLayoutManager(new LinearLayoutManager(this));
            recyclerAsistencias.setAdapter(alphaAdpt);
        }
    }

    @Override
    public void setVisibilitySpinnerPlantilla(boolean visible) {
    }

    @Override
    public void setSelectedItem(Plantilla item) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOtraC:
                onBackPressed();
                break;
        }
    }
}
