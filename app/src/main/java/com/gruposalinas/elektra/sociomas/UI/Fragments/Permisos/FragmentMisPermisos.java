package com.gruposalinas.elektra.sociomas.UI.Fragments.Permisos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gruposalinas.elektra.sociomas.UI.Activities.Permisos.AgregarPermisoV2;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Permisos.AdapterPermisoV2;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.R;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.WebService.CallBacks.CallBackPermiso;
import com.sociomas.core.WebService.Model.Request.Permisos.RootPermiso;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;

import java.util.ArrayList;

/**
 * Created by oemy9 on 25/07/2017.
 */

public class FragmentMisPermisos extends FragmentBaseTab implements CallBackPermiso {

    private ArrayList<ListExclusiones> responseListPermisos;
    private GridView gridviewPermisos;
    private AdapterPermisoV2 adapterPermisos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mis_permisos, container, false);
        super.onCreateView(inflater,container,savedInstanceState);
        this.responseListPermisos=new ArrayList<>();
        this.controllerAPI.ListadoPermisosIOSAsync(new RootPermiso(), EnumConsulta.Mias);
        this.controllerAPI.setCallBackPermiso(this);
        this.customProgressBar=new CustomProgressBar(getContext());
        this.gridviewPermisos=(GridView)rootView.findViewById(R.id.gridViewPermisos);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       getActivity().getMenuInflater().inflate(R.menu.menu_permisos, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_nuevo_permiso:
                Intent i = new Intent(getContext(),AgregarPermisoV2.class);
                i.putExtra(Constants.IS_PLANTILLA,false);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void OnSuccessIOS(ArrayList<ListExclusiones> responsePermisos) {
        if(!responsePermisos.isEmpty()){
            this.responseListPermisos.clear();
            this.responseListPermisos=responsePermisos;
            setAdapterPermisosPlantilla(this.responseListPermisos);
        }
        this.customProgressBar.dismiss();
    }

    @Override
    public void OnError(Throwable mensajeError) {
        this.customProgressBar.dismiss();
    }

    private void setAdapterPermisosPlantilla(ArrayList<ListExclusiones> responsePermisos){
        this.adapterPermisos=new AdapterPermisoV2(getContext(),responsePermisos);
        AlphaInAnimationAdapter alphaInAnimationAdapter=new AlphaInAnimationAdapter(this.adapterPermisos);
        alphaInAnimationAdapter.setAbsListView(gridviewPermisos);
        this.gridviewPermisos.setAdapter(alphaInAnimationAdapter);
    }
}
