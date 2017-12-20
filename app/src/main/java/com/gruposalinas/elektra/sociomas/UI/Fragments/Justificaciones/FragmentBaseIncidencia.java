package com.gruposalinas.elektra.sociomas.UI.Fragments.Justificaciones;

import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias.AdapterIncidenciaPlantillaV2;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias.AdapterIncidenciaV2;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.SearchBoxDialog;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.R;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.Utils.Enums.EnumTipo;
import com.sociomas.core.WebService.CallBacks.CallBackIncidencia;
import com.sociomas.core.WebService.Model.Request.Incidencia.RootIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by oemy9 on 31/07/2017.
 */

public class FragmentBaseIncidencia extends FragmentBaseTab implements CallBackIncidencia,AdapterIncidenciaPlantillaV2.CallBackUpdateReady {
    protected GridView gridViewIncidencias;
    protected ListView listViewIncidenciasPlantilla;
    protected AdapterIncidenciaV2 adapterIncidencia;
    protected AdapterIncidenciaPlantillaV2 adapterIncidenciaPlantilla;
    protected AlphaInAnimationAdapter alphaInAnimationAdapter;
    protected ArrayList<ListadoIncidencias> listadoIncidenciasIncidencias =new ArrayList<>();
    private EnumTipo tipoConsulta;
    protected boolean isEmpty;
    protected TextView tvEmpty;
    protected SearchBoxDialog searchBoxDialog;


    protected void callWebService(EnumTipo tipo){
        customProgressBar=new CustomProgressBar(getContext());
        customProgressBar.show(getActivity());
        controllerAPI.setCallBackIncidenciaResponse(this);
        controllerAPI.ListadoIncidenciasAsync(new RootIncidencia(),tipo);
        this.tipoConsulta=tipo;
    }

    @Override
    public void OnSuccess(ResponseIncidencia incidenciaResponse, HashMap<String, EmpleadoIncidencia> hashMapEmpleados) {

        searchBoxDialog=new SearchBoxDialog(getActivity());
        searchBoxDialog.setTitle(R.string.filtro_empleado);

        if(listViewIncidenciasPlantilla !=null){

            listadoIncidenciasIncidencias =tipoConsulta==EnumTipo.plantilla?
                    incidenciaResponse.getListadoPlantilla():
                    incidenciaResponse.getListadoIncidencias();

            if(!listadoIncidenciasIncidencias.isEmpty()) {
                adapterIncidenciaPlantilla = new AdapterIncidenciaPlantillaV2(getContext(), listadoIncidenciasIncidencias);
                adapterIncidenciaPlantilla.setCallBackUpdate(this);
                alphaInAnimationAdapter = new AlphaInAnimationAdapter(this.adapterIncidenciaPlantilla);
                alphaInAnimationAdapter.setAbsListView(listViewIncidenciasPlantilla);
                listViewIncidenciasPlantilla.setVisibility(View.VISIBLE);
                listViewIncidenciasPlantilla.setAdapter(alphaInAnimationAdapter);
                listViewIncidenciasPlantilla.setItemsCanFocus(true);
                searchBoxDialog.setListEmpleados(hashMapEmpleados);
                isEmpty=false;
            }
            else{
                listViewIncidenciasPlantilla.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
                isEmpty=true;
            }

            customProgressBar.dismiss();
        }
        else if(gridViewIncidencias!=null){
            listadoIncidenciasIncidencias =incidenciaResponse.getListadoIncidencias();
            adapterIncidencia=new AdapterIncidenciaV2(getContext(), listadoIncidenciasIncidencias);
            alphaInAnimationAdapter=new AlphaInAnimationAdapter(this.adapterIncidencia);
            alphaInAnimationAdapter.setAbsListView(gridViewIncidencias);
            gridViewIncidencias.setAdapter(alphaInAnimationAdapter);
            customProgressBar.dismiss();
        }

    }
    @Override
    public void OnError(Throwable mensajeError) {
        alertaAsync.displayMensaje(mensajeError.getMessage(),getContext());
        customProgressBar.dismiss();
    }
    protected ArrayList<ListadoIncidencias> searchByNumeroEmpleado(String numeroEmpleado){
        ArrayList<ListadoIncidencias>items=new ArrayList<>();
        if(listadoIncidenciasIncidencias !=null){
            for(ListadoIncidencias item: listadoIncidenciasIncidencias) {
                if(item.getEmpleado().equals(numeroEmpleado)){
                    items.add(item);
                }
            }
        }
        return items;
    }

    @Override
    public void onReady(boolean ready) {
        this.callWebService(EnumTipo.plantilla);
    }
}
