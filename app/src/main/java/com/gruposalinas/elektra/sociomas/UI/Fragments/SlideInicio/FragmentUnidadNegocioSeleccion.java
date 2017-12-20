package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.UnidadNegocioSeleccionPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.UnidadNegocioSeleccionPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadNegocioResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiioToledo on 14/11/17.
 */

public class FragmentUnidadNegocioSeleccion extends FragmentSlideBase implements BaseView {
    public static final String TAG = FragmentUnidadNegocioSeleccion.class.getSimpleName();
    private View v;
    private TextView tvTitleMensaje;
    private EditText etBusqueda;
    private GridView gridUnidadesNegocio;
    private UnidadNegocioSeleccionPresenter presenter;
    private AdapterUnidadesNegocios adapter;
    List<UnidadNegocioResponse> listEmpresas = new ArrayList<>();
    private ImageView ivReload;
    private TextView tvActualizar;

    public interface empresaSeleccionada {
        void onSeleccion(boolean seleccion);
    }

    public static FragmentUnidadNegocioSeleccion getInstance(Bundle bundle) {
        FragmentUnidadNegocioSeleccion fragment = new FragmentUnidadNegocioSeleccion();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_unidad_negocio_seleccion, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void initView() {
        tvTitleMensaje = (TextView) v.findViewById(R.id.tvTitleMensaje);
        etBusqueda = (EditText) v.findViewById(R.id.etBusqueda);
        gridUnidadesNegocio = (GridView) v.findViewById(R.id.gridUnidadesNegocio);
        ivReload = (ImageView) v.findViewById(R.id.ivReload);
        tvActualizar = (TextView) v.findViewById(R.id.tvActualizar);
        adapter = new AdapterUnidadesNegocios(getActivity(), listEmpresas, presenter);
        gridUnidadesNegocio.setAdapter(adapter);
    }

    @Override
    public void setListeners() {
        etBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 2) {
                    adapter.getFilter().filter(s.toString());
                } else {
                    adapter.reset();
                }
            }
        });
        gridUnidadesNegocio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UnidadNegocioResponse unr = (UnidadNegocioResponse) (view.findViewById(R.id.llParent)).getTag();
                presenter.guardarUnidadNegocioEmpleado(getActivityInstance(), unr);
            }
        });
        ivReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivReload.setVisibility(View.GONE);
                tvActualizar.setVisibility(View.GONE);
                gridUnidadesNegocio.setVisibility(View.VISIBLE);
                presenter.loadUnidadesNegocio(getActivityInstance());
            }
        });
        ivReload.setVisibility(View.GONE);
        tvActualizar.setVisibility(View.GONE);
        gridUnidadesNegocio.setVisibility(View.VISIBLE);
        presenter.loadUnidadesNegocio(getActivityInstance());
    }

    @Override
    public void setPresenter() {
        presenter = new UnidadNegocioSeleccionPresenterImpl();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.gridUnidadesNegocio: {
                        listEmpresas = (List<UnidadNegocioResponse>) event.getModel().get(ViewEvent.ENTRIES_LIST);
                        adapter.fillUnidadesNegocio(listEmpresas);
                    }
                    break;
                    case R.id.tvTitleMensaje: {
                        boolean guardado = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                        if (guardado) {
                            presenter.sendHideProgressEvent();
                            UnidadNegocioResponse unr = (UnidadNegocioResponse) event.getModel().get(ViewEvent.ENTRY);
                            ApplicationBase.getIntance().getManager().add(Constants.CLAVE_UNIDAD,unr.getClave());
                            Bundle bundle=new Bundle();
                            bundle.putSerializable(ViewEvent.ENTRY, unr);
                            ApplicationBase.getIntance().setUnidadNegocioResponse(unr);
                            navegarFragment(FragmentLugarTrabajo.getInstance(bundle),FragmentLugarTrabajo.TAG,true);
                        }
                    }
                    break;
                }
            }
            break;
            case ERROR_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.ivReload: {
                        gridUnidadesNegocio.setVisibility(View.GONE);
                        ivReload.setVisibility(View.VISIBLE);
                        tvActualizar.setVisibility(View.VISIBLE);
                        String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                        Toast.makeText(getActivityInstance(), msg, Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case R.id.tvTitleMensaje: {
                        String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                        Toast.makeText(getActivityInstance(), msg, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
            break;
        }
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }
}
