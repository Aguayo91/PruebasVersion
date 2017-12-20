package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.SliderInicio.AdapterHorariosTrabajo;
import com.gruposalinas.elektra.sociomas.UI.Controls.ControlHorarioPicker;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.AtencionHorariosDialog;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogHorario;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.FelicidadesDialogConfig;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.HorariosTrabajoPresenter;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.HorariosTrabajoPresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Request.Alta.Dias;
import com.sociomas.core.WebService.Model.Request.Alta.Horarios;
import com.sociomas.core.WebService.Model.Request.Horario.EditarHorarioRequest;
import com.sociomas.core.WebService.Model.Response.Horario.Horario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import vn.luongvo.widget.iosswitchview.SwitchView;

public class FragmentHorariosTrabajo extends FragmentSlideBase implements BaseView {

    public static final String TAG = FragmentHorariosTrabajo.class.getSimpleName();
    private SwitchView switchIOS;
    private int counter = 1;
    private FloatingActionButton fabAdd;//, fabGrey;
    private TextView tvAddHorario;
    private ControlHorarioPicker ctrPicker, ctrPicker2, ctrPicker3, ctrPicker4, ctrPicker5, ctrPicker6, ctrPicker7;
    private RecyclerView recyclerView;
    private LinearLayout linearList;
    private ArrayList<Horarios> horariosRecycler = new ArrayList<>();
    private Horarios selectedHorarios;
    private List<Horarios> horarios = new ArrayList<>();
    private List<Dias> diasDefault = new ArrayList<>();
    private View v;
    private HorariosTrabajoPresenter presenter;
    private RecyclerView Recycler;
    private ImageView btnAgregar;
    private AdapterHorariosTrabajo adapter;
    private int lunes = 0, martes = 0, miercoles = 0, jueves = 0, viernes = 0, sabado = 0, domingo = 0;
    private Button btnContinuar;
    Map<String, EditarHorarioRequest> mapHorario1 = new HashMap<>();
    Map<String, EditarHorarioRequest> mapHorario2 = new HashMap<>();
    private Button btnSiguiente;
    private ImageView imgInformation;
    private int Counter=0;

    public static FragmentHorariosTrabajo getInstance(Bundle bundle) {
        FragmentHorariosTrabajo fragment = new FragmentHorariosTrabajo();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_selecciona_horario, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void initView() {
        super.initView();
        switchIOS = (SwitchView) v.findViewById(R.id.switchviewIOS);
//        fabAdd = (FloatingActionButton) v.findViewById(R.id.btnAgregar);
//        fabGrey = (FloatingActionButton) v.findViewById(R.id.btnAgregarGrey);
        tvAddHorario = (TextView) v.findViewById(R.id.tvAddHorario);
        ctrPicker = (ControlHorarioPicker) v.findViewById(R.id.ctrPicker);
        ctrPicker2 = (ControlHorarioPicker) v.findViewById(R.id.ctrPicker2);
        Recycler = (RecyclerView) v.findViewById(R.id.Recycler);
        btnSiguiente = (Button) v.findViewById(R.id.btnSiguiente);
        imgInformation = (ImageView) v.findViewById(R.id.imgInformation);
        btnAgregar = (ImageView) v.findViewById(R.id.btnAgregar);
        btnContinuar = (Button) v.findViewById(R.id.btnContinuar);
        ctrPicker.ctrLunes.noCheck();
        ctrPicker.ctrMartes.noCheck();
        ctrPicker.ctrMiercoles.noCheck();
        ctrPicker.ctrJueves.noCheck();
        ctrPicker.ctrViernes.noCheck();
        ctrPicker.ctrSabado.noCheck();
        ctrPicker.ctrDomingo.noCheck();
    }

    public void activarClick() {
//        fabAdd.setVisibility(View.VISIBLE);
//        fabGrey.setVisibility(View.GONE);
        btnAgregar.setImageResource(R.mipmap.add_badge);
        tvAddHorario.setTextColor(getResources().getColor(R.color.negro));
        ctrPicker2.setVisibility(View.GONE);
        ctrPicker.activarClick();
        ctrPicker2.activarClick();
        btnAgregar.setClickable(true);
        btnContinuar.setEnabled(true);

    }

    public void desactivarClick() {

        ctrPicker2.setVisibility(View.GONE);
        btnAgregar.setImageResource(R.drawable.add_badge_inactive);
        btnAgregar.setClickable(false);
        tvAddHorario.setTextColor(getResources().getColor(R.color.colorGrisInfo));
        ctrPicker.desactivarClick();
        ctrPicker2.desactivarClick();
        btnContinuar.setEnabled(true);
    }

    public void setListPicker(View v) {
        if (counter == 1) {
            ctrPicker2.setVisibility(View.VISIBLE);
        }
        counter++;
    }

    @Override
    public void setListeners() {
        super.setListeners();

        imgInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogHorario dialogHorario = new DialogHorario(getContext());
                dialogHorario.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogHorario.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialogHorario.show();
            }

        });

        switchIOS.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchView switchView, boolean isChecked) {
                if (isChecked) {
                    desactivarClick();
                    presenter.notificaHorarioVariable(getActivityInstance(), 1);
                 //   ctrPicker2.setVisibility(View.GONE);
                 //   btnAgregar.setImageResource(R.mipmap.add_badge);
                    tvAddHorario.setText(getActivityInstance().getString(R.string.horario_btnEliminarTexto));
                    Counter++;
                } else {
                    activarClick();
                    presenter.notificaHorarioVariable(getActivityInstance(), 2);
                    Counter++;
                }
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrPicker2.getVisibility() == View.GONE) {
                    btnAgregar.setImageResource(R.mipmap.delete_bagage_red_51_51);
                    tvAddHorario.setText(getActivityInstance().getString(R.string.horario_btnAgregarTexto));
                    ctrPicker2.setVisibility(View.VISIBLE);
                    ctrPicker2.ctrLunes.noCheck();
                    ctrPicker2.ctrMartes.noCheck();
                    ctrPicker2.ctrMiercoles.noCheck();
                    ctrPicker2.ctrJueves.noCheck();
                    ctrPicker2.ctrViernes.noCheck();
                    ctrPicker2.ctrSabado.noCheck();
                    ctrPicker2.ctrDomingo.noCheck();
                } else {
                    ctrPicker2.setVisibility(View.GONE);
                    btnAgregar.setImageResource(R.mipmap.add_badge);
                    tvAddHorario.setText(getActivityInstance().getString(R.string.horario_btnEliminarTexto));
                }
            }
        });

        setListenersDias();
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Counter==0){
                    presenter.notificaHorarioVariable(getActivityInstance(), 2);
                }
                if (switchIOS.isChecked()) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    AtencionHorariosDialog dialog = new AtencionHorariosDialog(getContext(), ft,getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                } else {
                    if (ctrPicker2.getVisibility() == View.VISIBLE) {
                        if (mapHorario1.size() > 0 && mapHorario2.size() > 0) {
                            presenter
                                    .ModificarListaHorarioEmpleado(getActivityInstance(), mapHorario1, ctrPicker.obtenerHoraEntrada()
                                            , ctrPicker.obtenerHoraSalida(),
                                            mapHorario2, ctrPicker2.obtenerHoraEntrada()
                                            , ctrPicker2.obtenerHoraSalida()
                                    );
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivityInstance());
                            builder.setTitle("Aviso");
                            builder.setMessage("Agrega un horario para continuar");
                            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                    } else {
                        if (mapHorario1.size() > 0) {
                            presenter
                                    .ModificarListaHorarioEmpleado(getActivityInstance(), mapHorario1, ctrPicker.obtenerHoraEntrada()
                                            , ctrPicker.obtenerHoraSalida());
                        } else  {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivityInstance());
                            builder.setTitle("Aviso");
                            builder.setMessage("Agrega un horario para continuar");
                            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                    }

                }
            }
        });
        presenter.cargarHorariosEmpleado(getActivityInstance());
    }


    private void setListenersDias() {
        //region Lunes
        ctrPicker.ctrLunes.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrPicker2.getVisibility() == View.VISIBLE) {
                    if (!ctrPicker.ctrLunes.isChecked() && ctrPicker2.ctrLunes.isChecked()) {
                        ctrPicker2.ctrLunes.noCheck();
                        ctrPicker.ctrLunes.check();
                        mapHorario1.put("Lunes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 1));
                    } else if (ctrPicker.ctrLunes.isChecked() && !ctrPicker2.ctrLunes.isChecked()) {
                        ctrPicker.ctrLunes.noCheck();
                        ctrPicker2.ctrLunes.noCheck();
                        mapHorario1.remove("Lunes");
                    } else {
                        ctrPicker.ctrLunes.check();
                        ctrPicker2.ctrLunes.noCheck();
                        mapHorario1.put("Lunes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 1));
                    }
                } else {
                    if (!ctrPicker.ctrLunes.isChecked()) {
                        ctrPicker.ctrLunes.check();
                        mapHorario1.put("Lunes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 1));
                    } else {
                        ctrPicker.ctrLunes.noCheck();
                        mapHorario1.remove("Lunes");
                    }
                }


            }
        });
        ctrPicker2.ctrLunes.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ctrPicker2.ctrLunes.isChecked() && ctrPicker.ctrLunes.isChecked()) {
                    ctrPicker.ctrLunes.noCheck();
                    ctrPicker2.ctrLunes.check();
                    mapHorario2.put("Lunes", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 1));
                } else if (ctrPicker2.ctrLunes.isChecked() && !ctrPicker.ctrLunes.isChecked()) {
                    ctrPicker2.ctrLunes.noCheck();
                    ctrPicker.ctrLunes.noCheck();
                    mapHorario2.remove("Lunes");
                } else {
                    ctrPicker2.ctrLunes.check();
                    ctrPicker.ctrLunes.noCheck();
                    mapHorario2.put("Lunes", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 1));
                }
            }
        });
        //endregion

        //region Martes
        ctrPicker.ctrMartes.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrPicker2.getVisibility() == View.VISIBLE) {
                    if (!ctrPicker.ctrMartes.isChecked() && ctrPicker2.ctrMartes.isChecked()) {
                        ctrPicker2.ctrMartes.noCheck();
                        ctrPicker.ctrMartes.check();
                        mapHorario1.put("Martes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 2));
                    } else if (ctrPicker.ctrMartes.isChecked() && !ctrPicker2.ctrMartes.isChecked()) {
                        ctrPicker.ctrMartes.noCheck();
                        ctrPicker2.ctrMartes.noCheck();
                        mapHorario1.remove("Martes");
                    } else {
                        ctrPicker.ctrMartes.check();
                        ctrPicker2.ctrMartes.noCheck();
                        mapHorario1.put("Martes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 2));
                    }
                } else {
                    if (ctrPicker.ctrMartes.isChecked()) {
                        ctrPicker.ctrMartes.noCheck();
                        mapHorario1.remove("Martes");
                    } else {
                        ctrPicker.ctrMartes.check();
                        mapHorario1.put("Martes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 2));
                    }
                }
            }
        });
        ctrPicker2.ctrMartes.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ctrPicker2.ctrMartes.isChecked() && ctrPicker.ctrMartes.isChecked()) {
                    ctrPicker.ctrMartes.noCheck();
                    ctrPicker2.ctrMartes.check();
                    mapHorario2.put("Martes", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 2));
                } else if (ctrPicker2.ctrMartes.isChecked() && !ctrPicker.ctrMartes.isChecked()) {
                    ctrPicker2.ctrMartes.noCheck();
                    ctrPicker.ctrMartes.noCheck();
                    mapHorario2.remove("Martes");
                } else {
                    ctrPicker2.ctrMartes.check();
                    ctrPicker.ctrMartes.noCheck();
                    mapHorario2.put("Martes", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 2));
                }
            }
        });
        //endregion

        //region Miercoles
        ctrPicker.ctrMiercoles.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrPicker2.getVisibility() == View.VISIBLE) {
                    if (!ctrPicker.ctrMiercoles.isChecked() && ctrPicker2.ctrMiercoles.isChecked()) {
                        ctrPicker2.ctrMiercoles.noCheck();
                        ctrPicker.ctrMiercoles.check();
                        mapHorario1.put("Miercoles", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 3));
                    } else if (ctrPicker.ctrMiercoles.isChecked() && !ctrPicker2.ctrMiercoles.isChecked()) {
                        ctrPicker.ctrMiercoles.noCheck();
                        ctrPicker2.ctrMiercoles.noCheck();
                        mapHorario1.remove("Miercoles");
                    } else {
                        ctrPicker.ctrMiercoles.check();
                        ctrPicker2.ctrMiercoles.noCheck();
                        mapHorario1.put("Miercoles", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 3));
                    }
                } else {
                    if (ctrPicker.ctrMiercoles.isChecked()) {
                        ctrPicker.ctrMiercoles.noCheck();
                        mapHorario1.remove("Miercoles");
                    } else {
                        ctrPicker.ctrMiercoles.check();
                        mapHorario1.put("Miercoles", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 3));
                    }
                }
            }
        });
        ctrPicker2.ctrMiercoles.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ctrPicker2.ctrMiercoles.isChecked() && ctrPicker.ctrMiercoles.isChecked()) {
                    ctrPicker.ctrMiercoles.noCheck();
                    ctrPicker2.ctrMiercoles.check();
                    mapHorario2.put("Miercoles", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 3));
                } else if (ctrPicker2.ctrMiercoles.isChecked() && !ctrPicker.ctrMiercoles.isChecked()) {
                    ctrPicker2.ctrMiercoles.noCheck();
                    ctrPicker.ctrMiercoles.noCheck();
                    mapHorario2.remove("Miercoles");
                } else {
                    ctrPicker2.ctrMiercoles.check();
                    ctrPicker.ctrMiercoles.noCheck();
                    mapHorario2.put("Miercoles", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 3));
                }
            }
        });
        //endregion

        //region Jueves
        ctrPicker.ctrJueves.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrPicker2.getVisibility() == View.VISIBLE) {
                    if (!ctrPicker.ctrJueves.isChecked() && ctrPicker2.ctrJueves.isChecked()) {
                        ctrPicker2.ctrJueves.noCheck();
                        ctrPicker.ctrJueves.check();
                        mapHorario1.put("Jueves", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 4));
                    } else if (ctrPicker.ctrJueves.isChecked() && !ctrPicker2.ctrJueves.isChecked()) {
                        ctrPicker.ctrJueves.noCheck();
                        ctrPicker2.ctrJueves.noCheck();
                        mapHorario1.remove("Jueves");
                    } else {
                        ctrPicker.ctrJueves.check();
                        ctrPicker2.ctrJueves.noCheck();
                        mapHorario1.put("Jueves", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 4));
                    }
                } else {
                    if (ctrPicker.ctrJueves.isChecked()) {
                        ctrPicker.ctrJueves.noCheck();
                        mapHorario1.remove("Jueves");
                    } else {
                        ctrPicker.ctrJueves.check();
                        mapHorario1.put("Jueves", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 4));
                    }
                }
            }
        });

        ctrPicker2.ctrJueves.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ctrPicker2.ctrJueves.isChecked() && ctrPicker.ctrJueves.isChecked()) {
                    ctrPicker.ctrJueves.noCheck();
                    ctrPicker2.ctrJueves.check();
                    mapHorario2.put("Jueves", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 4));
                } else if (ctrPicker2.ctrJueves.isChecked() && !ctrPicker.ctrJueves.isChecked()) {
                    ctrPicker2.ctrJueves.noCheck();
                    ctrPicker.ctrJueves.noCheck();
                    mapHorario2.remove("Jueves");
                } else {
                    ctrPicker2.ctrJueves.check();
                    ctrPicker.ctrJueves.noCheck();
                    mapHorario2.put("Jueves", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 4));
                }
            }
        });
        //endregion

        //region Viernes
        ctrPicker.ctrViernes.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrPicker2.getVisibility() == View.VISIBLE) {
                    if (!ctrPicker.ctrViernes.isChecked() && ctrPicker2.ctrViernes.isChecked()) {
                        ctrPicker2.ctrViernes.noCheck();
                        ctrPicker.ctrViernes.check();
                        mapHorario1.put("Viernes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 5));
                    } else if (ctrPicker.ctrViernes.isChecked() && !ctrPicker2.ctrViernes.isChecked()) {
                        ctrPicker.ctrViernes.noCheck();
                        ctrPicker2.ctrViernes.noCheck();
                        mapHorario1.remove("Viernes");
                    } else {
                        ctrPicker.ctrViernes.check();
                        ctrPicker2.ctrViernes.noCheck();
                        mapHorario1.put("Viernes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 5));
                    }
                } else {
                    if (ctrPicker.ctrViernes.isChecked()) {
                        ctrPicker.ctrViernes.noCheck();
                        mapHorario1.remove("Viernes");
                    } else {
                        ctrPicker.ctrViernes.check();
                        mapHorario1.put("Viernes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 5));
                    }
                }
            }
        });
        ctrPicker2.ctrViernes.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ctrPicker2.ctrViernes.isChecked() && ctrPicker.ctrViernes.isChecked()) {
                    ctrPicker.ctrViernes.noCheck();
                    ctrPicker2.ctrViernes.check();
                    mapHorario2.put("Viernes", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 5));
                } else if (ctrPicker2.ctrViernes.isChecked() && !ctrPicker.ctrViernes.isChecked()) {
                    ctrPicker2.ctrViernes.noCheck();
                    ctrPicker.ctrViernes.noCheck();
                    mapHorario2.remove("Viernes");
                } else {
                    ctrPicker2.ctrViernes.check();
                    ctrPicker.ctrViernes.noCheck();
                    mapHorario2.put("Viernes", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 5));
                }
            }
        });

        //endregion
        //region Sabado
        ctrPicker.ctrSabado.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrPicker2.getVisibility() == View.VISIBLE) {
                    if (!ctrPicker.ctrSabado.isChecked() && ctrPicker2.ctrSabado.isChecked()) {
                        ctrPicker2.ctrSabado.noCheck();
                        ctrPicker.ctrSabado.check();
                        mapHorario1.put("Sabado", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 6));
                    } else if (ctrPicker.ctrSabado.isChecked() && !ctrPicker2.ctrSabado.isChecked()) {
                        ctrPicker.ctrSabado.noCheck();
                        ctrPicker2.ctrSabado.noCheck();
                        mapHorario1.remove("Sabado");
                    } else {
                        ctrPicker.ctrSabado.check();
                        ctrPicker2.ctrSabado.noCheck();
                        mapHorario1.put("Sabado", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 6));
                    }
                } else {
                    if (ctrPicker.ctrSabado.isChecked()) {
                        ctrPicker.ctrSabado.noCheck();
                        mapHorario1.remove("Sabado");
                    } else {
                        ctrPicker.ctrSabado.check();
                        mapHorario1.put("Sabado", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 6));
                    }
                }
            }
        });
        ctrPicker2.ctrSabado.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ctrPicker2.ctrSabado.isChecked() && ctrPicker.ctrSabado.isChecked()) {
                    ctrPicker.ctrSabado.noCheck();
                    ctrPicker2.ctrSabado.check();
                    mapHorario2.put("Sabado", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 6));
                } else if (ctrPicker2.ctrSabado.isChecked() && !ctrPicker.ctrSabado.isChecked()) {
                    ctrPicker2.ctrSabado.noCheck();
                    ctrPicker.ctrSabado.noCheck();
                    mapHorario2.remove("Sabado");
                } else {
                    ctrPicker2.ctrSabado.check();
                    ctrPicker.ctrSabado.noCheck();
                    mapHorario2.put("Sabado", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 6));
                }
            }
        });
        //endregion

        //region Domingo
        ctrPicker.ctrDomingo.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrPicker2.getVisibility() == View.VISIBLE) {
                    if (!ctrPicker.ctrDomingo.isChecked() && ctrPicker2.ctrDomingo.isChecked()) {
                        ctrPicker2.ctrDomingo.noCheck();
                        ctrPicker.ctrDomingo.check();
                        mapHorario1.put("Domingo", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 0));
                    } else if (ctrPicker.ctrDomingo.isChecked() && !ctrPicker2.ctrDomingo.isChecked()) {
                        ctrPicker.ctrDomingo.noCheck();
                        ctrPicker2.ctrDomingo.noCheck();
                        mapHorario1.remove("Domingo");
                    } else {
                        ctrPicker.ctrDomingo.check();
                        ctrPicker2.ctrDomingo.noCheck();
                        mapHorario1.put("Domingo", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 0));
                    }
                } else {
                    if (ctrPicker.ctrDomingo.isChecked()) {
                        ctrPicker.ctrDomingo.noCheck();
                        mapHorario1.remove("Domingo");
                    } else {
                        ctrPicker.ctrDomingo.check();
                        mapHorario1.put("Domingo", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 0));
                    }
                }
            }
        });
        ctrPicker2.ctrDomingo.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ctrPicker2.ctrDomingo.isChecked() && ctrPicker.ctrDomingo.isChecked()) {
                    ctrPicker.ctrDomingo.noCheck();
                    ctrPicker2.ctrDomingo.check();
                    mapHorario2.put("Domingo", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 0));
                } else if (ctrPicker2.ctrDomingo.isChecked() && !ctrPicker.ctrDomingo.isChecked()) {
                    ctrPicker2.ctrDomingo.noCheck();
                    ctrPicker.ctrDomingo.noCheck();
                    mapHorario2.remove("Domingo");
                } else {
                    ctrPicker2.ctrDomingo.check();
                    ctrPicker.ctrDomingo.noCheck();
                    mapHorario2.put("Domingo", new EditarHorarioRequest(ctrPicker2.obtenerHoraEntrada(), ctrPicker2.obtenerHoraSalida(), 0));
                }
            }
        });
        //endregion
    }


    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.btnContinuar: {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityInstance());
                        builder.setTitle("Aviso");
                        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        if(ctrPicker2.getVisibility() == View.VISIBLE){
                            if(ctrPicker.validar() && ctrPicker2.validar()){
                                sendData(event);
                                break;
                            }else if(ctrPicker.validar() && !ctrPicker2.validar()) {
                                //Toast.makeText(getContext(),,Toast.LENGTH_LONG).show();
                                builder.setMessage(getString(R.string.ErrorHora1).concat(getString(R.string.ErrorHora)));
                                builder.create().show();
                                break;
                            }else if(!ctrPicker.validar() && ctrPicker2.validar()){
                                //Toast.makeText(getContext(),getString(R.string.ErrorHora2).concat(getString(R.string.ErrorHora)), Toast.LENGTH_LONG).show();
                                builder.setMessage(getString(R.string.ErrorHora2).concat(getString(R.string.ErrorHora)));
                                builder.create().show();
                                break;
                            }else{
                                //Toast.makeText(getContext(),getString(R.string.ErrorHora3).concat(getString(R.string.ErrorHora)), Toast.LENGTH_LONG).show();
                                builder.setMessage(getString(R.string.ErrorHora3).concat(getString(R.string.ErrorHora)));
                                builder.create().show();
                                break;
                            }
                        }else if (ctrPicker.validar()) {
//                            //Toast.makeText(getContext(), "validar true", Toast.LENGTH_SHORT).show();
//                            /*boolean booleano = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
//                            if (!booleano) {
//                                FelicidadesDialogConfig dialog = new FelicidadesDialogConfig(getContext());
//                                dialog.setAct(getActivity());
//                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                dialog.show();
//                            } else {
//                                String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
//                                Toast.makeText(getActivityInstance(), msg, Toast.LENGTH_SHORT).show();
//                            }*/
                            sendData(event);

                            break;
                        }
                        else {
                            //Toast.makeText(getContext(),R.string.ErrorHora, Toast.LENGTH_SHORT).show();
                            builder.setMessage(getString(R.string.ErrorHora));
                            builder.create().show();
                            break;
                        }
                    }
                    case R.id.switchviewIOS: {
                        boolean checkSwitch = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                      //  switchIOS.setChecked(checkSwitch);
                    }
                    break;
                }
            }
            break;
            case PRESENT_RESULTSET_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.ctrPicker: {
                        List<Horario> horarios = (List<Horario>) event.getModel().get(ViewEvent.ENTRIES_LIST);
//                        setHorariosCargados();
                        for (Horario h : horarios) {
                            if (h.getBitValido()) {
                                if (h.getTiDiaSemana() == 0 && h.getEstatus().toUpperCase(Locale.ENGLISH).contentEquals("VALIDO")) {
                                    ctrPicker.ctrDomingo.check();
                                    mapHorario1.put("Domingo", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 0));
                                } else if (h.getTiDiaSemana() == 1 && h.getEstatus().toUpperCase(Locale.ENGLISH).contentEquals("VALIDO")) {
                                    ctrPicker.ctrLunes.check();
                                    mapHorario1.put("Lunes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 1));
                                } else if (h.getTiDiaSemana() == 2 && h.getEstatus().toUpperCase(Locale.ENGLISH).contentEquals("VALIDO")) {
                                    ctrPicker.ctrMartes.check();
                                    mapHorario1.put("Martes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 2));
                                } else if (h.getTiDiaSemana() == 3 && h.getEstatus().toUpperCase(Locale.ENGLISH).contentEquals("VALIDO")) {
                                    ctrPicker.ctrMiercoles.check();
                                    mapHorario1.put("Miercoles", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 3));
                                } else if (h.getTiDiaSemana() == 4 && h.getEstatus().toUpperCase(Locale.ENGLISH).contentEquals("VALIDO")) {
                                    ctrPicker.ctrJueves.check();
                                    mapHorario1.put("Jueves", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 4));
                                } else if (h.getTiDiaSemana() == 5 && h.getEstatus().toUpperCase(Locale.ENGLISH).contentEquals("VALIDO")) {
                                    ctrPicker.ctrViernes.check();
                                    mapHorario1.put("Viernes", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 5));
                                } else if (h.getTiDiaSemana() == 6 && h.getEstatus().toUpperCase(Locale.ENGLISH).contentEquals("VALIDO")) {
                                    ctrPicker.ctrSabado.check();
                                    mapHorario1.put("Sabado", new EditarHorarioRequest(ctrPicker.obtenerHoraEntrada(), ctrPicker.obtenerHoraSalida(), 6));
                                }
                            }
                        }
                    }
                    break;
                }
            }
            break;
            case ERROR_EVENT_TYPE: {
                String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                Toast.makeText(getActivityInstance(), msg, Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private void sendData (ViewEvent event){
        boolean booleano = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
        if (!booleano) {
            FelicidadesDialogConfig dialog = new FelicidadesDialogConfig(getContext());
            dialog.setAct(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        } else {
            String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
            Toast.makeText(getActivityInstance(), msg, Toast.LENGTH_SHORT).show();
        }
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