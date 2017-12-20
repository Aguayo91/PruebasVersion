package com.gruposalinas.elektra.sociomas.UI.Fragments.Justificaciones;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.SearchBoxDialog;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Enums.EnumTipo;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;

import java.util.HashMap;

/**
 * Created by oemy9 on 31/07/2017.
 */

public class FragmentIncidenciasPlantilla extends FragmentBaseIncidencia   implements SwipeRefreshLayout.OnRefreshListener,
        SearchBoxDialog.SearchBoxDialogCallBack {

    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_incidencia_plantilla, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        listViewIncidenciasPlantilla = (ListView)rootView.findViewById(R.id.listViewPlantilla);
        tvEmpty=(TextView)rootView.findViewById(R.id.tvEmpty);
        refreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        callWebService(EnumTipo.plantilla);
        return rootView;
    }

    @Override
    public void OnSuccess(ResponseIncidencia incidenciaResponse, HashMap<String, EmpleadoIncidencia> hashMapEmpleados) {
        super.OnSuccess(incidenciaResponse, hashMapEmpleados);
        searchBoxDialog.setCallBack(this);
    }

    @Override
    public void onRefresh() {
        callWebService(EnumTipo.plantilla);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onResult(SearchBoxItem resultItem) {
        if(!resultItem.getValue().equals(searchBoxDialog.TODOS)){
            adapterIncidenciaPlantilla.setListIndidencias(searchByNumeroEmpleado(resultItem.getId()));
        }
        else{
            adapterIncidenciaPlantilla.setListIndidencias(listadoIncidenciasIncidencias);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_filtro, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filtro:
                if(searchBoxDialog!=null) {
                    searchBoxDialog.showAsync();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
