package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.ControlDiaSem;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.HorariosTrabajoPresenter;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.HorariosTrabajoPresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;

/**
 * Created by GiioToledo on 22/11/17.
 */

public class FragmentHorarioEmpleadoSeleccion extends FragmentSlideBase implements BaseView {

    public static final String TAG = FragmentHorarioEmpleadoSeleccion.class.getSimpleName();

    private View v;
    private HorariosTrabajoPresenter presenter;
    private RelativeLayout headerPickerHorario;
    private ExpandableRelativeLayout expandablePickerEntrada;
    private ControlDiaSem ctrLunes;
    private ControlDiaSem ctrMartes;
    private ControlDiaSem ctrMiercoles;
    private ControlDiaSem ctrJueves;
    private ControlDiaSem ctrViernes;
    private ControlDiaSem ctrSabado;
    private ControlDiaSem ctrDomingo;
    private RelativeLayout RelativeEntrada;
    private ExpandableRelativeLayout expandablePicker2;
    private RelativeLayout RelativeSalida;
    private ExpandableRelativeLayout expandablePicker3;

    public static FragmentHorarioEmpleadoSeleccion getInstance(Bundle bundle) {
        FragmentHorarioEmpleadoSeleccion fragment = new FragmentHorarioEmpleadoSeleccion();
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
        v = inflater.inflate(R.layout.fragment_seleccion_horario_empleado, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void initView() {
        super.initView();
        headerPickerHorario = (RelativeLayout) v.findViewById(R.id.headerPickerHorario);
        expandablePickerEntrada = (ExpandableRelativeLayout) v.findViewById(R.id.expandablePickerEntrada);
        ctrLunes = (ControlDiaSem) v.findViewById(R.id.ctrLunes);
        ctrMartes = (ControlDiaSem) v.findViewById(R.id.ctrMartes);
        ctrMiercoles = (ControlDiaSem) v.findViewById(R.id.ctrMiercoles);
        ctrJueves = (ControlDiaSem) v.findViewById(R.id.ctrJueves);
        ctrViernes = (ControlDiaSem) v.findViewById(R.id.ctrViernes);
        ctrSabado = (ControlDiaSem) v.findViewById(R.id.ctrSabado);
        ctrDomingo = (ControlDiaSem) v.findViewById(R.id.ctrDomingo);
        configurarControles();

        RelativeEntrada = (RelativeLayout) v.findViewById(R.id.RelativeEntrada);
        expandablePicker2 = (ExpandableRelativeLayout) v.findViewById(R.id.expandablePicker2);

        RelativeSalida = (RelativeLayout) v.findViewById(R.id.RelativeSalida);
        expandablePicker3 = (ExpandableRelativeLayout) v.findViewById(R.id.expandablePicker3);
    }

    @Override
    public void setListeners() {
        super.setListeners();
        headerPickerHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandablePickerEntrada.toggle();
            }
        });
        RelativeEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandablePicker2.toggle();
            }
        });
        RelativeSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandablePicker3.toggle();
            }
        });
    }

    private void configurarControles() {
        ctrLunes.setDia(getActivityInstance().getString(R.string.Lunes));
        ctrMartes.setDia(getActivityInstance().getString(R.string.Martes));
        ctrMiercoles.setDia(getActivityInstance().getString(R.string.Miercoles));
        ctrJueves.setDia(getActivityInstance().getString(R.string.Jueves));
        ctrViernes.setDia(getActivityInstance().getString(R.string.Viernes));
        ctrSabado.setDia(getActivityInstance().getString(R.string.Sabado));
        ctrDomingo.setDia(getActivityInstance().getString(R.string.Domingo));
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void setPresenter() {
        super.setPresenter();
        presenter = new HorariosTrabajoPresenterImpl();
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }
}
