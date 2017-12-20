package com.gruposalinas.elektra.sociomas.UI.Fragments.Permisos;

import android.content.Intent;
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

import com.gruposalinas.elektra.sociomas.UI.Activities.Permisos.AgregarPermisoV2;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Permisos.AdapterPermisosPlantilla;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.SearchBoxDialog;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.R;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.WebService.CallBacks.CallBackPermiso;
import com.sociomas.core.WebService.Model.Request.Permisos.RootPermiso;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;

import java.util.ArrayList;

/**
 * Created by oemy9 on 25/07/2017.
 */

public class FragmentPermisosPlantilla extends FragmentBaseTab implements CallBackPermiso,
        SwipeRefreshLayout.OnRefreshListener,  SearchBoxDialog.SearchBoxDialogCallBack  {

    private ArrayList<ListExclusiones>responseListPermisos;
    private SwipeRefreshLayout refreshLayout;
    private ListView listViewPermisos;
    private AdapterPermisosPlantilla adapterPermisosPlantilla;
    private SearchBoxDialog searchBoxDialog;
    private TextView tvEmpty;
    private boolean isEmpty;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_permisos_plantilla, container, false);
        this.listViewPermisos=(ListView)rootView.findViewById(R.id.listViewPermisosP);
        this.tvEmpty=(TextView)rootView.findViewById(R.id.tvEmpty);
        this.listViewPermisos.setItemsCanFocus(true);
        this.refreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refreshLayout);
        this.refreshLayout.setOnRefreshListener(this);
        this.responseListPermisos=new ArrayList<>();
        this.controllerAPI.ListadoPermisosIOSAsync(new RootPermiso(), EnumConsulta.LineaDirecta);
        this.controllerAPI.setCallBackPermiso(this);
        this.customProgressBar.show(getActivity());
        this.searchBoxDialog=new SearchBoxDialog(getContext());
        this.searchBoxDialog.setCallBack(this);
        this.searchBoxDialog.setTitle("Filtrar por empleado");

        return rootView;
    }

    @Override
    public void OnError(Throwable mensajeError) {
        alertaAsync.displayMensaje(mensajeError.getMessage(),getContext());
        this.customProgressBar.dismiss();
    }
    @Override
    public void OnSuccessIOS(ArrayList<ListExclusiones> responsePermisos) {
        if(!responsePermisos.isEmpty()){
            this.listViewPermisos.setVisibility(View.VISIBLE);
            this.responseListPermisos.clear();
            this.responseListPermisos=responsePermisos;
            setAdapterPermisosPlantilla(this.responseListPermisos);
            searchBoxDialog.setListEmpleados(this.responseListPermisos);
            isEmpty=false;
        }
        else{
            listViewPermisos.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            isEmpty=true;
        }

        this.customProgressBar.dismiss();
    }
    private void searchByNumeroEmpleado(String numeroEmpleado){
        ArrayList<ListExclusiones>responseSearch=new ArrayList<>();
        for(ListExclusiones item:this.responseListPermisos){
            if(item.getIdNumEmpleado().equals(numeroEmpleado)){
                responseSearch.add(item);
            }
        }
        setAdapterPermisosPlantilla(responseSearch);
    }
    private void setAdapterPermisosPlantilla(ArrayList<ListExclusiones> responsePermisos){
        this.adapterPermisosPlantilla=new AdapterPermisosPlantilla(getContext(),responsePermisos);
        AlphaInAnimationAdapter  alphaInAnimationAdapter=new AlphaInAnimationAdapter(this.adapterPermisosPlantilla);
        alphaInAnimationAdapter.setAbsListView(listViewPermisos);
        this.listViewPermisos.setAdapter(alphaInAnimationAdapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_filtro_permisos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filtro:
                searchBoxDialog.showAsync();
                break;
            case R.id.action_nuevo_permiso:
                if(!isEmpty) {
                    Intent intent = new Intent(getContext(), AgregarPermisoV2.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.LIST_EMPLEADOS, searchBoxDialog.getItemArrayList());
                    intent.putExtras(bundle);
                    intent.putExtra(Constants.IS_PLANTILLA, true);
                    startActivity(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRefresh() {
        controllerAPI.ListadoPermisosIOSAsync(new RootPermiso(),EnumConsulta.LineaDirecta);
        this.refreshLayout.setRefreshing(false);
    }


    @Override
    public void onResult(SearchBoxItem resultItem) {
        if(!resultItem.getValue().equals(searchBoxDialog.TODOS)) {
            searchByNumeroEmpleado(resultItem.getId());
        }
        else{
            setAdapterPermisosPlantilla(this.responseListPermisos);
        }
    }

}
