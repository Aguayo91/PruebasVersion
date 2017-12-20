package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo.AdapterLugarTFlip;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogLugarFijo;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.LugarTrabajoPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.LugarTrabajoPresenter;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Controls.Notification.CustomProgressBar;
import com.sociomas.core.Utils.Enums.EnumTipoLugarTrabajo;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import java.util.ArrayList;

import vn.luongvo.widget.iosswitchview.SwitchView;

/**
 * Created by oemy9 on 14/11/2017.
 */

public class FragmentLugarTrabajo extends FragmentSlideBase implements LugarTrabajoPresenterImpl.LugarTrabajoView,
        View.OnClickListener, DialogLugarTrabajo.DialogoLugarTrabajoListener {

    public static final String TAG = "FragmentLugarTrabajo";
    private View rootView;
    private LugarTrabajoPresenter presenter;
    private ImageView imgUnidadNegocio;
    private EditText txtBusqueda;
    private TextView tvSeleccionados;
    private Button btnSiguiente;
    private ImageView imgInformation;
    private RecyclerView recyclerUbicacionesFlip;
    private DialogLugarTrabajo dialogLugarTrabajo;
    private SwitchView swSinLugarFijo;
    private AdapterLugarTFlip adpt;
    private CustomProgressBar customProgressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_lugar_trabajo,container,false);
        setPresenter();
        return rootView;
    }
    public static  FragmentLugarTrabajo getInstance(@Nullable Bundle args){
        FragmentLugarTrabajo fragmentLugarTrabajo=new FragmentLugarTrabajo();
        if(args!=null){
            fragmentLugarTrabajo.setArguments(args);
        }
        return fragmentLugarTrabajo;
    }


    @Override
    public void initView() {
        btnSiguiente=(Button)rootView.findViewById(R.id.btnSiguiente);
        txtBusqueda=(EditText)rootView.findViewById(R.id.txtBusqueda);
        imgInformation=(ImageView)rootView.findViewById(R.id.imgInformation);
        tvSeleccionados=(TextView)rootView.findViewById(R.id.tvSeleccionados);
        imgUnidadNegocio=(ImageView)rootView.findViewById(R.id.imgUnidadNegocio);
        swSinLugarFijo = (SwitchView) rootView.findViewById(R.id.swSinLugarFijo);
        recyclerUbicacionesFlip=(RecyclerView)rootView.findViewById(R.id.recyclerUbicacionesFlip);
        dialogLugarTrabajo =new DialogLugarTrabajo(getContext());

    }

    @Override
    public void setListeners() {
        imgInformation.setOnClickListener(this);
        txtBusqueda.setFocusable(false);
        txtBusqueda.setCursorVisible(false);
        txtBusqueda.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        dialogLugarTrabajo.setListener(this);
        dialogLugarTrabajo.setPresenter(presenter);
        swSinLugarFijo.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchView switchView, boolean checked) {
                txtBusqueda.setEnabled(!checked);
                txtBusqueda.setBackgroundColor(!checked? Color.WHITE: ContextCompat.getColor(getContext(),R.color.gris_claro));
                presenter.cambiarLugarTrabajo(getActivityInstance(), checked? EnumTipoLugarTrabajo.VARIABLE: EnumTipoLugarTrabajo.FIJO);
            }
        });
    }
    @Override
    public Activity getActivityInstance() {
        return this.getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.txtBusqueda:
                dialogLugarTrabajo.setPresenter(presenter);
                dialogLugarTrabajo.setListener(this);
                dialogLugarTrabajo.show();
                break;

            case R.id.imgInformation:
                DialogLugarFijo dialogFijo = new DialogLugarFijo(getContext());
                dialogFijo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogFijo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialogFijo.show();
                break;

            case R.id.btnSiguiente:
                presenter.cambiarLugarTrabajo(getActivityInstance(), swSinLugarFijo.isChecked()? EnumTipoLugarTrabajo.VARIABLE: EnumTipoLugarTrabajo.FIJO);
                presenter.enviarLugaresSeleccionadosAsync(!swSinLugarFijo.isChecked());
                break;
        }
    }
    @Override
    public void setPresenter() {
        this.presenter=new LugarTrabajoPresenterImpl();
        this.presenter.register(this);
        this.presenter.setArguments(getArguments());
        this.presenter.consultaUnidadNegocio();
    }

    @Override
    public void setListLugarTrabajo(ArrayList<LugarTrabajo> listLugarTrabajo) {
        dialogLugarTrabajo.setListLugarTrabajo(listLugarTrabajo);

    }
    @Override
    public void showImageBitMap(Bitmap bmp) {
        AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        imgUnidadNegocio.setImageBitmap(bmp);
    }
    //CALLBACK QUE PROVIENE DEL DIALOGO @DIALOGOLUGARTRABAJO
    @Override
    public void onLugaresTrabajoSelected(final ArrayList<LugarTrabajo> listLugaresTrabajo) {
        if(listLugaresTrabajo!=null && (!listLugaresTrabajo.isEmpty())){
            adpt=new AdapterLugarTFlip(getContext(),listLugaresTrabajo);
            adpt.setShowDescription(false);
            recyclerUbicacionesFlip.setVisibility(View.VISIBLE);
            recyclerUbicacionesFlip.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerUbicacionesFlip.setAdapter(adpt);
            presenter.setListLugaresSeleccionados(listLugaresTrabajo);
        }
    }

    @Override
    public void navegarFragment() {
       navegarFragment(FragmentHorariosTrabajo.getInstance(null),FragmentHorariosTrabajo.TAG,true);
    }
    @Override
    public void onQueryEmpty() {
    }

    @Override
    public void clearLugaresTrabajo() {
        if(adpt!=null) {
            adpt.clearItems();
        }
    }
}
