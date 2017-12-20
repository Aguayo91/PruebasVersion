package com.gruposalinas.elektra.sociomas.UI.Activities.Perfil;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gruposalinas.elektra.sociomas.UI.Activities.CambiarTel.MiNumeroTelefonicoActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Contactos.ContactosActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.CrearGafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Legal.LegalActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Login.LoginActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Notificaciones.BuzonActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Configuracion.AdapterConfiguracionV2;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.sociomas.core.DataBaseModel.ConfiguracionItem;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class ConfiguracionActivity extends BaseCoreCompactActivity implements AdapterView.OnItemClickListener {
    private AdapterConfiguracionV2 adapterConfiguracionV2;
    private ArrayList<ConfiguracionItem>listItems=new ArrayList<>();
    private ListView listViewConfiguracion;
    private AppBarLayout appBarLayout;
    private int taps=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        this.listViewConfiguracion=(ListView)findViewById(R.id.listViewConfiguracion);
        this.appBarLayout=(AppBarLayout)findViewById(R.id.appBarLayout);
        setToolBar(getString(R.string.configuracion_sociomas));
        initConfig();
    }

    private void initConfig(){
        SessionManager manager= ApplicationBase.getIntance().getManager();
        this.listItems.add(new ConfiguracionItem(R.drawable.ic_sos_black,getString(R.string.misContactosSos),true));
        this.listItems.add(new ConfiguracionItem(R.drawable.ic_gafete, getString(R.string.miInformacionPersonal),false));
        this.listItems.add(new ConfiguracionItem(R.drawable.ic_tarjeta_azteca,getString(R.string.mi_tarjeta),false));
        this.listItems.add(new ConfiguracionItem(R.drawable.ic_cel,getString(R.string.mi_num_telefonico),true));
        this.listItems.add(new ConfiguracionItem(R.drawable.ic_gafete,getString(R.string.credencializacion),!manager.get(Constants.HAS_GAFETE,false)));
        this.listItems.add(new ConfiguracionItem(R.drawable.ic_notification_black, getString(R.string.mis_notificaciones),true));
        this.listItems.add(new ConfiguracionItem(R.drawable.ic_legales,getString(R.string.legales),true));
        this.listItems.add(new ConfiguracionItem(R.mipmap.ic_eye_log, getString(R.string.log_app),manager.getInt(Constants.ID_ROL_EMPLEADO)==1));
        Observable.fromIterable(this.listItems).filter(new Predicate<ConfiguracionItem>() {
            @Override
            public boolean test(ConfiguracionItem item) throws Exception {
                return item.isVisible();
            }
        }).toList().subscribe(new Consumer<List<ConfiguracionItem>>() {
            @Override
            public void accept(final List<ConfiguracionItem> litFilter) throws Exception {
                adapterConfiguracionV2=new AdapterConfiguracionV2(ConfiguracionActivity.this,(ArrayList)litFilter);
                ScaleInAnimationAdapter scaleInAnimationAdapter=new ScaleInAnimationAdapter(adapterConfiguracionV2);
                scaleInAnimationAdapter.setAbsListView(listViewConfiguracion);
                listViewConfiguracion.setAdapter(scaleInAnimationAdapter);
                listViewConfiguracion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String opt=litFilter.get(position).getDescripcion();

                        if(opt.equalsIgnoreCase(getString(R.string.misContactosSos))){
                            startActivity(new Intent(ConfiguracionActivity.this,ContactosActivityV2.class));
                        }
                        else if(opt.equals(getString(R.string.miInformacionPersonal))){
                        }
                        else if(opt.equalsIgnoreCase(getString(R.string.mi_tarjeta))){
                        }
                        else if(opt.equalsIgnoreCase(getString(R.string.mi_num_telefonico))){
                            startActivity(new Intent(ConfiguracionActivity.this, MiNumeroTelefonicoActivity.class));
                        }
                        else if(opt.equalsIgnoreCase(getString(R.string.mis_notificaciones))){
                            startActivity(new Intent(ConfiguracionActivity.this, BuzonActivity.class));
                        }
                        else if(opt.equalsIgnoreCase(getString(R.string.legales))){
                            startActivity(new Intent(ConfiguracionActivity.this, LegalActivity.class));
                        }
                        else if(opt.equalsIgnoreCase(getString(R.string.log_app))){
                            startActivity(new Intent(ConfiguracionActivity.this, LoginActivity.class));
                        }
                        else if(opt.equalsIgnoreCase(getString(R.string.credencializacion))){
                            startActivity(new Intent(ConfiguracionActivity.this, CrearGafeteActivity.class));
                            finish();
                        }
                    }
                });

            }
        });
    }

    //Elimina en icono de configuración en el toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

    }
}
