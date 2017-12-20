package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.AsistenciaConsultas.AsistenciaConsultasActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado.JustificacionSelectionActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.ControlFilterAsistencias;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Controls.Spinner.SpinnerPlantilla;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class MisAsistenciasActivityV2 extends BaseCoreCompactActivity implements MisAsistenciasPresenterImpl.MisAsistenciasView, DatePickerDialog.OnDateSetListener, ControlFilterAsistencias.FilterAsistenciasListener {
    private TextView tvCalendarInicio, tvCalendarFin;
    private RelativeLayout rlCalendarInicio,rlCalendarFin;
    private SpinnerPlantilla spinnerPlantilla;
    private ControlFilterAsistencias ctrAsistenciaCorrecta, ctrFaltas, ctrSalidasTemprano, ctrLlegadasTarde, ctrFueraDeLaHora;
    private List<ControlFilterAsistencias>filtrosList;
    private boolean inicioSelected=false;
    private Context context;
    private MisAsistenciasPresenter presenter;
    private Button btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_asistencia_v2);
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
                    NavUtils.navigateUpFromSameTask(MisAsistenciasActivityV2.this);
                }
            });
        }
    }

    @Override
    public void setPresenter() {
        this.presenter=new MisAsistenciasPresenterImpl();
        this.presenter.register(this);
        this.presenter.setArguments(getIntent());
    }

    @Override
    public void initView(){
        tvCalendarInicio=(TextView)findViewById(R.id.tvCalendarInicio);
        tvCalendarFin=(TextView)findViewById(R.id.tvCalendarFin);
        rlCalendarInicio=(RelativeLayout)findViewById(R.id.rlCalendarInicio);
        rlCalendarFin=(RelativeLayout)findViewById(R.id.rlCalendarFin);
        ctrAsistenciaCorrecta=(ControlFilterAsistencias)findViewById(R.id.ctrAsistenciaCorrecta);
        ctrFaltas=(ControlFilterAsistencias)findViewById(R.id.ctrFaltas);
        ctrLlegadasTarde=(ControlFilterAsistencias)findViewById(R.id.ctrLlegadasTarde);
        ctrSalidasTemprano=(ControlFilterAsistencias)findViewById(R.id.ctrSalidasTemprano);
        ctrFueraDeLaHora=(ControlFilterAsistencias)findViewById(R.id.ctrFueraDeHora);
        spinnerPlantilla=(SpinnerPlantilla)findViewById(R.id.spinnerPlantilla);
        btnConsultar=(Button)findViewById(R.id.btnConsultar);
        filtrosList= Arrays.asList(ctrAsistenciaCorrecta,ctrFaltas,ctrLlegadasTarde,ctrSalidasTemprano,ctrFueraDeLaHora);
    }
    @Override
    public void setListeners() {
        spinnerPlantilla.initSpinnerPlantilla(getString(R.string.asistencias_de),getString(R.string.asistencias_de),false);
        ctrAsistenciaCorrecta.setListener(this);
        ctrFaltas.setListener(this);
        ctrLlegadasTarde.setListener(this);
        ctrSalidasTemprano.setListener(this);
    }
    public void setCalendar(View v){
        inicioSelected=v.getId()==rlCalendarInicio.getId();
        Calendar c=inicioSelected? presenter.getCalendarInicio(): presenter.getCalendarFin();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,this, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void Consultar(View v){
        Intent i = new Intent(this, AsistenciaConsultasActivity.class);
        startActivity(i);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(inicioSelected)
            presenter.setCalendarInicio(year, month, dayOfMonth);
        else
            presenter.setCalendarFin(year, month, dayOfMonth);
    }

    @Override
    public void showInicioFecha(String fechaInicio) {
        tvCalendarInicio.setText(fechaInicio);
    }

    @Override
    public void showFinFecha(String fechaFin) {
        tvCalendarFin.setText(fechaFin);
    }

    @Override
    public void setListAsistencias(List<ResultadoAsistencia> listAsistencias,String fechaInicio, String fechaFin) {
        Intent intent=new Intent(this,AsistenciaConsultasActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList(ViewEvent.ENTRIES_LIST,(ArrayList)listAsistencias);
        bundle.putString(Constants.SELECTED_FECHA_INICIO,fechaInicio);
        bundle.putString(Constants.SELECTED_FECHA_FIN,fechaFin);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void setVisibilitySpinnerPlantilla(boolean visible) {
        spinnerPlantilla.setVisibility(visible? View.VISIBLE: View.GONE);
    }

    @Override
    public void setSelectedItem(Plantilla item) {
        spinnerPlantilla.setSelectedItem(item);
    }

    public void consultar(View view) {
        presenter.initiBusquedaAsistencias(spinnerPlantilla.getSelectedItem().getIdEmpleado(),
                ctrAsistenciaCorrecta.isChecked(),
                ctrFaltas.isChecked(),
                ctrSalidasTemprano.isChecked(),
                ctrLlegadasTarde.isChecked(),ctrFueraDeLaHora.isChecked());
    }

    @Override
    public void onCheckedListener(boolean checked) {
        Observable.fromIterable(filtrosList).filter(new Predicate<ControlFilterAsistencias>() {
            @Override
            public boolean test(ControlFilterAsistencias ftAsistencia) throws Exception {
               return  !ftAsistencia.isChecked();
            }
        }).toList().subscribe(new Consumer<List<ControlFilterAsistencias>>() {
            @Override
            public void accept(List<ControlFilterAsistencias> ftList) throws Exception {
                boolean disabled=filtrosList.size()==ftList.size();
                if(disabled) {
                    btnConsultar.setBackgroundResource(R.drawable.shape_botton_oval_gris);
                    btnConsultar.setEnabled(false);
                }
                else{
                    btnConsultar.setBackgroundResource(R.drawable.shape_button_oval_yellow);
                    btnConsultar.setEnabled(true);
                }
            }
        });
    }
}
