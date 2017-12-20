package com.sociomas.core.UI.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.sociomas.core.BuildConfig;
import com.sociomas.core.DataBaseModel.DatosContacto;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.R;
import com.sociomas.core.UI.Adapter.DrawerListAdapter;
import com.sociomas.core.UI.Controls.ActionMenuDialog;
import com.sociomas.core.UI.Controls.Model.DrawerItem;
import com.sociomas.core.UI.Controls.Model.EnumNavegacion;
import com.sociomas.core.UI.Controls.Notification.Alertas;
import com.sociomas.core.UI.Controls.Notification.CustomProgressBar;
import com.sociomas.core.UI.Controls.Notification.FooterNavegacion;
import com.sociomas.core.UI.Controls.Notification.SnackBarBuilder;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Utils;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;


public abstract class BaseCoreCompactActivity extends AppCompatActivity implements BaseView, DrawerLayout.DrawerListener, FooterNavegacion.ViewMasListener {
    protected CustomProgressBar customProgressBar;
    protected String currentTitle;
    protected Alertas alertDialog;
    protected Toolbar toolbar;
    protected SessionManager manager;
    private DrawerLayout drawer;
    private EnumNavegacion selectedItem, lastSelectedItem;
    public ListView listDrawer;
    private TextView tvHeader;
    private ImageView imageHeader;
    private TextView tvVersion;
    private View contentView;
    private static final float END_SCALE = 0.7f;
    private MenuItem itemNotificacion;
    private LayerDrawable iconNotificacion;
    public ActionBarDrawerToggle toggle;

    @Override
    public void presentEvent(ViewEvent event) {
        switch (event.getEventType()) {
            case SHOW_PROGRESS_EVENT_TYPE:
                customProgressBar.show(this);
                break;
            case HIDE_PROGRESS_EVENT_TYPE:
                customProgressBar.dismiss();
                break;

            case SHOW_SUCCESS_MESSAGE: {
                String msj = String.valueOf(event.getModel().get(ViewEvent.MESSAGE));
                alertDialog.displayMensaje(msj, this);
            }
            break;

            case SHOW_TOAST_MESSAGE: {
                String msj = String.valueOf(event.getModel().get(ViewEvent.MESSAGE));
                showToast(msj);
            }
            break;
        }
    }

    protected void reUpdateImagenPerfil() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        if (navigationView != null) {
            updateImagenPerfil(navigationView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityCompat.invalidateOptionsMenu(this);
    }

    protected void registerLocalBroadCast() {
        BroadcastReceiver bReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROAD_CAST_NOTIFICATION)) {
                    if (intent.hasExtra(Constants.DATA_SEND)) {
                        RemoteMessage message = intent.getParcelableExtra(Constants.DATA_SEND);
                        showForegroundNotification(message.getNotification().getBody(), message);
                    }
                }
            }
        };
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROAD_CAST_NOTIFICATION);
        bManager.registerReceiver(bReceiver, intentFilter);
    }

    private void showForegroundNotification(String title, final RemoteMessage message) {
        SnackBarBuilder snackBarBuilder = new SnackBarBuilder(this);
        ActivityCompat.invalidateOptionsMenu(this);
        snackBarBuilder.showAlertNotificacionPush(title, 5, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(message.getNotification().getClickAction());
                if (message.getData().containsKey(Constants.DATA_SEND)) {
                    intent.putExtra(Constants.DATA_SEND, message.getData().get(Constants.DATA_SEND));
                    //¿Contiene el id de la notificación?
                    if(message.getData().containsKey(Constants.ID_NOTIFICACION)) {
                        intent.putExtra(Constants.ID_NOTIFICACION, message.getData().get(Constants.ID_NOTIFICACION));
                    }
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void updateImagenPerfil(View headerView) {
        CircleImageView imagenPerfil = (CircleImageView) headerView.findViewById(R.id.imgPerfil);
        if (imagenPerfil != null) {
            Bitmap selectedImagen = Utils.getImagenPerfilWallpaper(this, false);
            if (selectedImagen != null && (!selectedImagen.equals(""))) {
                imagenPerfil.setImageBitmap(selectedImagen);
            } else {
                imagenPerfil.setImageResource(R.drawable.ic_avatar_fondo);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.manager = new SessionManager(this);
        this.customProgressBar = new CustomProgressBar(this);
        this.alertDialog = new Alertas(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        registerLocalBroadCast();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer != null) {
            Toolbar toolbar = setToolBar(this.currentTitle, true);
            toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);

            /*DATOS PERSONALES NOMBRE*/
            TextView tvNombre = (TextView) headerView.findViewById(R.id.tvUser);
            tvNombre.setText(Utils.toTitleCase(manager.getString(Constants.SP_NAME)));
            this.updateImagenPerfil(headerView);

            /*VERSIÓN DE LA APP*/
            TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
            if (tvVersion != null)
                tvVersion.setText(getString(R.string.version_footer, getString(R.string.version_name)));

            ImageView imageClose = (ImageView) headerView.findViewById(R.id.imageClose);
            imageClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (drawer != null) {
                        drawer.closeDrawer(Gravity.START);
                    }
                }
            });
            /*FOOTER*/
            FooterNavegacion footerNavegacion = (FooterNavegacion) findViewById(R.id.footerNavegacion);
            if (footerNavegacion != null) {
                footerNavegacion.setListener(this);
            }
            //NAVEGACIÓN DRAWER ITEM
            listDrawer = (ListView) findViewById(R.id.left_drawer);
            LayoutInflater inflater = getLayoutInflater();
            ViewGroup header = (ViewGroup) inflater.inflate(R.layout.listview_header, listDrawer, false);
            listDrawer.addHeaderView(header, null, false);
            tvHeader = (TextView) header.findViewById(R.id.tvHeader);
            imageHeader = (ImageView) header.findViewById(R.id.imageHeader);
            //NAVEGA HACIA LA PANTALLA DE INICIO
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getString(R.string.action_inicio));
                    startActivity(intent);
                    finish();
                }
            });
            this.checkLastOptionSelected();
            drawer.addDrawerListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_root, menu);
        itemNotificacion = menu.findItem(R.id.action_notification);
        iconNotificacion = (LayerDrawable) itemNotificacion.getIcon();
        // Update LayerDrawable's BadgeDrawable
        SessionManager manager = new SessionManager(getActivityInstance());
        Utils.setBadgCount(this, iconNotificacion, manager.getInt(Constants.ContadorNotificaciones));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();

        } else if (id == R.id.action_settings) {
            startActivity(new Intent(getString(R.string.action_cofiguraciones)));
        } else if (id == R.id.action_notification) {
            startActivity(new Intent(getString(R.string.action_buzon)));

        }
        return super.onOptionsItemSelected(item);
    }

    protected void setToolBar(@StringRes int title) {
        setToolBar(getString(title));
    }


    protected Toolbar setToolBar(String title, boolean draweLayout) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setOnClickListener(new View.OnClickListener() {
                int taps = 0;

                @Override
                public void onClick(View view) {
                    taps++;
                    if (taps == 10) {
                        Intent intent = new Intent(getString(R.string.action_log));
                        startActivity(intent);
                        taps = 0;
                    }
                }
            });
            setSupportActionBar(toolbar);
        }
        if (draweLayout && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView tvTitulo = (TextView) findViewById(R.id.titulo);
            if (tvTitulo != null) {
                tvTitulo.setText(title);

            } else {
                toolbar.setTitle(title);
            }
        }
        return toolbar;
    }


    protected void setToolBar(String title) {
        this.currentTitle = title;
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.flecha_cabeza);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                TextView tvTitulo = (TextView) findViewById(R.id.titulo);
                if (tvTitulo != null) {
                    tvTitulo.setText(title);
                }
            }
        } else {
            TextView tvTitulo = (TextView) findViewById(R.id.titulo);
            if (tvTitulo != null) {
                tvTitulo.setText(title);
            }
        }
    }


    protected void setToolBarDefualtHomeIndicator(String title) {
        this.currentTitle = title;
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                TextView tvTitulo = (TextView) findViewById(R.id.titulo);
                if (tvTitulo != null) {
                    tvTitulo.setText(title);
                }
            }
        } else {
            TextView tvTitulo = (TextView) findViewById(R.id.titulo);
            if (tvTitulo != null) {
                tvTitulo.setText(title);
            }
        }
    }

    protected void setToolBar(String title, @ColorRes int backgroundColor, @ColorRes int textColor) {
        this.currentTitle = title;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.flecha_cabeza);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView tvTitulo = (TextView) findViewById(R.id.titulo);
            if (tvTitulo != null) {
                tvTitulo.setText(title);
                tvTitulo.setTextColor(ContextCompat.getColor(this, textColor));
            }
        }
    }

    //region TOASTS
    protected void showToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    protected void showToastShort(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    protected void showCustomToastDuration(String mensaje, int milisegundos) {
        final Toast toast = Toast.makeText(getApplicationContext(),
                mensaje, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, milisegundos);
    }

    //endregion


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }


    private void checkLastOptionSelected() {
        if (manager != null && (manager.getString(Constants.LAST_OPTION_NAV) != null)) {
            this.selectedItem = EnumNavegacion.fromString(manager.getString(Constants.LAST_OPTION_NAV));
        } else {
            this.selectedItem = EnumNavegacion.MOVIILIDAD;
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        if (newState == DrawerLayout.STATE_SETTLING && !drawer.isDrawerOpen(Gravity.START)) {
            if (lastSelectedItem != selectedItem) {
                setAdapter(selectedItem);
            }
            else{
                setAdapter(EnumNavegacion.ADMINISTRACION);
            }
        }
    }

    public void OnOptionSelected(EnumNavegacion navegacion) {
        if(navegacion==EnumNavegacion.SOS) {
            try{
                DatosContacto dt=new DatosContacto();
                startActivity(new Intent(!dt.isguardar(this) ? getString(R.string.action_contactos) : getString(R.string.action_sos)));
            }catch (ActivityNotFoundException ex){
                showToastShort("No se pudo encontrar el action para SOS");
            }
        }
        else {
            selectedItem = navegacion;
            manager.add(Constants.LAST_OPTION_NAV, selectedItem.toString());
            if (drawer != null) {
                drawer.openDrawer(Gravity.START);
            }
        }



    }

    private void setAdapter(final EnumNavegacion navegacion) {
        ArrayList<DrawerItem> drawerItems = new ArrayList<>();

        imageHeader.setImageResource(R.drawable.ic_home);
        tvHeader.setText(getString(R.string.inicio));

        switch (navegacion) {
            case ADMINISTRACION:
                drawerItems.add(new DrawerItem(getString(R.string.inicio_fin_labores), R.drawable.ic_entradas_salidas, getString(R.string.action_jornada), true));
                drawerItems.add(new DrawerItem(getString(R.string.consulta_horario),R.drawable.ic_reloj,getString(R.string.action_jornada),false));
                drawerItems.add(new DrawerItem(getString(R.string.mis_asistencias), R.drawable.ic_asistencia_outline, getString(R.string.action_asistencias), true));
                drawerItems.add(new DrawerItem(getString(R.string.mi_gafete), R.drawable.ic_gaf, getString(R.string.action_gafete), true));
                drawerItems.add(new DrawerItem(getString(R.string.liberacion_nomina), R.drawable.ic_nomina_outline, getString(R.string.action_nomina), false)); //manager.get(Constants.IS_USUARIO_NOMINA,false)
                break;
            case MOVIILIDAD:
                drawerItems.add(new DrawerItem(getString(R.string.mi_lugar_trabajo), R.drawable.ic_job_place, getString(R.string.action_lugares), true));
                //  drawerItems.add(new DrawerItem(getString(R.string.aventones), R.drawable.ic_aventon_outline, getString(R.string.action_aventon),manager.get(Constants.IS_USUARIO_AVENTON,false)));
                drawerItems.add(new DrawerItem(getString(R.string.puntos_interes), R.drawable.ic_puntos_int, getString(R.string.action_puntos_interes), true));
                //drawerItems.add(new DrawerItem(getString(R.string.puntos_interes),R.drawable.ic_movilidad_ruta,getString(R.string.action_puntos_interes),true));
                //drawerItems.add(new DrawerItem(getString(R.string.promociones_descuentos),R.drawable.ic_promociones_outline, getString(R.string.action_promociones),true));
                ////drawerItems.add(new DrawerItem(getString(R.string.puntos_interes),R.drawable.ic_movilidad_ruta,getString(R.string.action_puntos_interes),true));
                //drawerItems.add(new DrawerItem(getString(R.string.promociones_descuentos),R.drawable.ic_promociones_outline, getString(R.string.action_promociones),true));
                //drawerItems.add(new DrawerItem(getString(R.string.mi_lugar_trabajo),R.drawable.ic_destinos,getString(R.string.action_lugares),true));
                break;
            case SEGURIDAD:
                //  drawerItems.add(new DrawerItem(getString(R.string.hora_calculada), R.drawable.ic_movilidad_ruta,getString(R.string.action_hora),true));
                drawerItems.add(new DrawerItem(getString(R.string.polizas), R.drawable.ic_poli, getString(R.string.action_polizas), true));
                drawerItems.add(new DrawerItem(getString(R.string.ficha_medica), R.drawable.ic_ficha_medica, getString(R.string.action_inicio), false));
                // drawerItems.add(new DrawerItem(getString(R.string.conf_contact), R.drawable.ic_sos_black, getString(R.string.action_contactos), true));
                drawerItems.add(new DrawerItem(getString(R.string.call_sos), R.drawable.ic_call_sos, getString(R.string.action_911), true));
                break;
            case SOS:

                break;
        }


        Observable.fromIterable(drawerItems).filter(new Predicate<DrawerItem>() {
            @Override
            public boolean test(DrawerItem drawerItem) throws Exception {
                return drawerItem.isVisible();
            }
        }).toList().subscribe(new Consumer<List<DrawerItem>>() {
            @Override
            public void accept(List<DrawerItem> drawerItems) throws Exception {
                final DrawerListAdapter draweAdapter = new DrawerListAdapter(BaseCoreCompactActivity.this, R.layout.item_drawer, drawerItems);
                ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(draweAdapter);
                scaleInAnimationAdapter.setAbsListView(listDrawer);
                listDrawer.setAdapter(draweAdapter);
                listDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        position -= listDrawer.getHeaderViewsCount();
                        DrawerItem selectedItem = draweAdapter.getItem(position);
                        if (selectedItem.getActionName().equals(getString(R.string.action_911))) {
                            Utils.callPhone(BaseCoreCompactActivity.this, "911");
                        } else if (selectedItem.getActionName().equals(getString(R.string.action_horario))) {
                            ActionMenuDialog menuDialog = new ActionMenuDialog(BaseCoreCompactActivity.this);
                            menuDialog.show(ActionMenuDialog.EnumAction.HORARIO);
                            drawer.closeDrawer(Gravity.START);
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(selectedItem.getActionName());
                            startActivity(intent);
                        }

                    }
                });
                lastSelectedItem = navegacion;
            }
        });


    }

    public void showProgressDialog(Activity activity) {
        this.customProgressBar.show(activity);
    }

    public void hideProgressDialog() {
        this.customProgressBar.dismiss();
    }

    public void showMsgDialog(Activity activity, String title, String msg) {
        try {
            if (!activity.isFinishing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                builder.setTitle(title);
                builder.setMessage(msg);
                builder.setPositiveButton(getString(R.string.aceptar),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMsgDialog(Activity activity, String title, String msg,
                              DialogInterface.OnClickListener positiveBtn, String positiveBtnTxt) {
        try {
            if (!activity.isFinishing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                builder.setTitle(title);
                builder.setMessage(msg);
                builder.setPositiveButton(positiveBtnTxt,
                        positiveBtn);
                builder.create().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setPresenter() {

    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }
}