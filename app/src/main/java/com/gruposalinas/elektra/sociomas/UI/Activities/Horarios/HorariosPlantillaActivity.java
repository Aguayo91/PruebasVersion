package com.gruposalinas.elektra.sociomas.UI.Activities.Horarios;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.UI.Controls.Spinner.SpinnerPlantilla;
import com.sociomas.core.Utils.Enums.EnumConsulta;

public class HorariosPlantillaActivity extends BaseHorariosActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios_plantilla);
        getHorariosAsync(EnumConsulta.LineaDirecta);
        this.listViewHorarios=(ListView)findViewById(R.id.listvieHorarios);
        this.listViewHorarios.setOnItemClickListener(this);
        this.spinnerPlantilla=(SpinnerPlantilla)findViewById(R.id.spinnerPlantilla);
        this.spinnerPlantilla.setOnItemSelectedListener(this);
        this.setToolBar(getString(R.string.horarios_de_mi_plantilla));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Plantilla selectedItem=spinnerPlantilla.getSelectedItem();
        if(selectedItem!=null) {
            setAdapterListView(this.searchByNumeroEmpleado(selectedItem.getIdEmpleado()), false,false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
