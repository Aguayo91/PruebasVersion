package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones;

import android.widget.GridView;
import android.widget.ListView;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.SearchBoxDialog;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias.AdapterIncidenciaPlantillaV2;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias.AdapterIncidenciaV2;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.Utils.Enums.EnumTipo;
import com.sociomas.core.WebService.CallBacks.CallBackIncidencia;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Incidencia.RootIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by oemy9 on 15/05/2017.
 */

public class BaseIncidenciaActivity extends BaseAppCompactActivity implements CallBackIncidencia,AdapterIncidenciaPlantillaV2.CallBackUpdateReady {
    public static final String TAG="JUSTIFICACION";
    protected GridView gridViewIncidencias;
    protected ListView listViewIncidenciasPlantilla;
    protected CustomProgressBar customProgressBar;
    protected AdapterIncidenciaV2 adapterIncidencia;
    protected AdapterIncidenciaPlantillaV2 adapterIncidenciaPlantilla;
    protected AlphaInAnimationAdapter alphaInAnimationAdapter;
    protected ArrayList<ListadoIncidencias> listadoIncidenciasIncidencias =new ArrayList<>();
    protected ControllerAPI controllerAPI=new ControllerAPI(this);
    private EnumTipo tipoConsulta;
    protected SearchBoxDialog searchBoxDialog;

    protected void callWebService(EnumTipo tipo){
        customProgressBar=new CustomProgressBar(this);
        customProgressBar.show(this);
        controllerAPI.setCallBackIncidenciaResponse(this);
        controllerAPI.ListadoIncidenciasAsync(new RootIncidencia(),tipo);
        this.tipoConsulta=tipo;
    }

    @Override
    public void OnSuccess(ResponseIncidencia incidenciaResponse, HashMap<String, EmpleadoIncidencia> hashMapEmpleados) {

               searchBoxDialog=new SearchBoxDialog(this);
               searchBoxDialog.setTitle(R.string.filtro_empleado);

                if(listViewIncidenciasPlantilla !=null){

                    listadoIncidenciasIncidencias =tipoConsulta==EnumTipo.plantilla?
                            incidenciaResponse.getListadoPlantilla():
                    incidenciaResponse.getListadoIncidencias();

                    if(!listadoIncidenciasIncidencias.isEmpty()) {
                        adapterIncidenciaPlantilla = new AdapterIncidenciaPlantillaV2(this, listadoIncidenciasIncidencias);
                        adapterIncidenciaPlantilla.setCallBackUpdate(this);
                        alphaInAnimationAdapter = new AlphaInAnimationAdapter(this.adapterIncidenciaPlantilla);
                        alphaInAnimationAdapter.setAbsListView(listViewIncidenciasPlantilla);
                        listViewIncidenciasPlantilla.setAdapter(alphaInAnimationAdapter);
                        listViewIncidenciasPlantilla.setItemsCanFocus(true);
                        searchBoxDialog.setListEmpleados(hashMapEmpleados);
                    }
                    else{
                        showToast(this.getString(R.string.no_plantilla_justificar));
                        finish();
                    }

                    customProgressBar.dismiss();
                }
             else if(gridViewIncidencias!=null){
                    listadoIncidenciasIncidencias =incidenciaResponse.getListadoIncidencias();
                    adapterIncidencia=new AdapterIncidenciaV2(this, listadoIncidenciasIncidencias);
                    alphaInAnimationAdapter=new AlphaInAnimationAdapter(this.adapterIncidencia);
                    alphaInAnimationAdapter.setAbsListView(gridViewIncidencias);
                    gridViewIncidencias.setAdapter(alphaInAnimationAdapter);
                    customProgressBar.dismiss();
             }



    }
    @Override
    public void OnError(Throwable error) {
        Alertas alertas = new Alertas(this);
        alertas.displayMensaje(error.getMessage(),this);
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
