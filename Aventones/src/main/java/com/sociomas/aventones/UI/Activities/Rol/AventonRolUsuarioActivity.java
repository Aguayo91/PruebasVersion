package com.sociomas.aventones.UI.Activities.Rol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.AventonesReservadosActivity;
import com.sociomas.aventones.UI.Activities.Carros.CarsActivity;
import com.sociomas.aventones.UI.Activities.Encuentra.EncuentraTuAventonActivity;
import com.sociomas.aventones.UI.Activities.MisAventones.AventonesPublicados;
import com.sociomas.aventones.UI.Activities.UsuarioPiloto.ActivityUsuarioPiloto;
import com.sociomas.aventones.UI.Adapters.AdapterFragmentUsuario;
import com.sociomas.aventones.UI.Fragments.DefaultRol.FragmentDefaultRol;
import com.sociomas.aventones.UI.Fragments.FragmentPasajeroConductor;
import com.sociomas.aventones.UI.Fragments.FragmentQrConductor;
import com.sociomas.aventones.UI.Fragments.FragmentScanPasajero;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Enums.EnumPerfilConductor;
import com.sociomas.core.WebService.Model.Response.Aventones.RolResponse;

public class AventonRolUsuarioActivity extends BaseCoreCompactActivity implements AventonRolPresenterImpl.AventonesInicioView {

    private AdapterFragmentUsuario adapterFragmentUsuario;
    private AventonRolPresenter presenter;
    private FloatingActionButton fabAgregarPiloto, fabEncuentra, fabPublicados, fabAsientos, fabMiPerfil;
    private FloatingActionMenu fabMenu;
    private ImageView imgQr;
    private FrameLayout flPrincipal;
    private RelativeLayout flAmarillo;
    private RelativeLayout viewYellow;
    private static final String TAG = "ROL_USUARIO";
    public static final String TRANSITION_FAB = "fabEncuentra";
    public static final String TRANSITION_PUB = "fabPublicados";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aventones_inicio);
        ApplicationAventon.onCreate(this);
        setPresenter();
    }

    @Override
    public void initView() {
        fabAgregarPiloto = (FloatingActionButton) findViewById(R.id.fabAgregarPiloto);
        fabEncuentra = (FloatingActionButton) findViewById(R.id.fabEncuentra);
        fabPublicados = (FloatingActionButton) findViewById(R.id.fabPublicados);
        fabAsientos = (FloatingActionButton) findViewById(R.id.fabAsientos);
        fabMiPerfil = (FloatingActionButton) findViewById(R.id.fabMiPerfil);
        fabMenu = (FloatingActionMenu) findViewById(R.id.btnMenu);
        flPrincipal = (FrameLayout) findViewById(R.id.flPrincipal);
        flAmarillo = (RelativeLayout) findViewById(R.id.frameAmarillo);
        fabMenu.setVisibility(View.GONE);
    }

    @Override
    public void setListeners() {
        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {

                    flAmarillo.setVisibility(View.VISIBLE);

                } else {

                    flAmarillo.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void setPresenter() {
        presenter = new AventonRolPresenterImpl();
        presenter.register(this);
        presenter.obtenerRolAventon();
        presenter.revisarAgregarPiloto();
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public void navigateAventonRol(RolResponse rolResponse) {
        if (!rolResponse.getError()) {
            int rol = rolResponse.getIdRolAventon();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ViewEvent.ENTRY, rolResponse);
            navegacionFragmento(rol, bundle);

        } else {
            navegacionFragmento(EnumPerfilConductor.NINGUNO.getValue(), null);
        }
    }

    @Override
    public void navegacionFragmento(int idStatus, Bundle data) {

        if (!isFinishing()) {
            try {
                fabMenu.setVisibility(View.VISIBLE);
                fabMenu.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
                Fragment fragmentStatus = new FragmentDefaultRol();
                switch (EnumPerfilConductor.getPerfil(idStatus)) {
                    case NINGUNO:
                        fragmentStatus = new FragmentDefaultRol();
                        setToolBar(R.string.elige_una_opcion);
                        fabMenu.setVisibility(View.GONE);
                        break;
                    case CONDUCTOR:
                        fragmentStatus = new FragmentQrConductor();
                        setToolBar(R.string.perfil_conductor);

                        break;
                    case PASAJERO:
                        fragmentStatus = new FragmentScanPasajero();
                        setToolBar(R.string.perfil_pasajero);
                        break;
                    case PASAJERO_CONDUCTOR:
                        fragmentStatus = new FragmentPasajeroConductor();
                        setToolBar(R.string.perfil_mixto);
                        break;

                }
                if (data != null) {
                    fragmentStatus.setArguments(data);
                }
                if (!isFinishing()) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    ft.replace(R.id.flPrincipal, fragmentStatus);
                    ft.commit();
                }
            } catch (Exception ex) {
                Log.i(TAG, "ERROR AL NAVEGAR AL ROL");
            }
        }

    }

    @Override
    public void mostrarOpcionAgregarPiloto(boolean mostrar) {
        if (fabAgregarPiloto != null) {
            fabAgregarPiloto.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        }
    }

    public void btnEncuentra(View view) {
        Intent i = new Intent(this, EncuentraTuAventonActivity.class);
        startActivity(i);
    }

    public void btnPublicados(View v) {
        Intent i = new Intent(this, AventonesPublicados.class);
        startActivity(i);
    }

    public void btnMisReservados(View v) {
        Intent i = new Intent(this, AventonesReservadosActivity.class);
        startActivity(i);

    }

    public void btnMiPerfil(View v) {
        Intent i = new Intent(this, CarsActivity.class);
        startActivity(i);
    }

    public void btnUsuarioPiloto(View v) {
        Intent i = new Intent(this, ActivityUsuarioPiloto.class);
        startActivity(i);
    }

    public void hideFloatActionsNinguno() {
        presenter.revisarAgregarPiloto();
        fabEncuentra.setVisibility(View.GONE);
        fabPublicados.setVisibility(View.GONE);
        fabAsientos.setVisibility(View.GONE);
        fabMiPerfil.setVisibility(View.GONE);
    }

    public void hideFloatActionsPasajero() {
        fabPublicados.setVisibility(View.GONE);
    }

    public void hideFloatRerservados() {
        fabAsientos.setVisibility(View.GONE);
    }
}