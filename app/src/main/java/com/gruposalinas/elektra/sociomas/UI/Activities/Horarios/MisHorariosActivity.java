package com.gruposalinas.elektra.sociomas.UI.Activities.Horarios;

import android.os.Bundle;
import android.widget.ListView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Enums.EnumConsulta;

public class MisHorariosActivity extends BaseHorariosActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_horarios);
        this.listViewHorarios=(ListView)findViewById(R.id.listvieHorarios);
        this.listViewHorarios.setOnItemClickListener(this);
        this.setToolBar(getString(R.string.mis_horarios));
        this.getHorariosAsync(EnumConsulta.Mias);
    }


}
