package com.sociomas.aventones.UI.Activities.Preferencias;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Publicar.PublicaActivity;
import com.sociomas.aventones.UI.Controls.OnCustomProgressListener;
import com.sociomas.aventones.UI.Controls.ProhibitedImage;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Controls.EditTextTamMax;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CaractEmpleado;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CatPreferenciasEmpleado;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreferencesActivity extends BaseCoreCompactActivity implements
        PreferenciasPresenterImpl.PreferenciasView, RadioGroup.OnCheckedChangeListener {
    private boolean isEditar=false;
    private PreferenciasPresenter presenter;
    private EditTextTamMax etObservaciones;
    private RadioGroup rg1;
    private RadioButton rbMen,rbWomen,rbIndistinto;
    private TextView textViewSeekBar, tvTitle;
    private ProhibitedImage piFumar, piAlimentos,piBultos,piMascotas,piNinios,piArmas,piDiscapacitados,piBasura;
    private List<ProhibitedImage> listImagenes;
    private DiscreteSeekBar discreteSeekBar;
    private int idRelCaracteristicaUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        this.setToolBar(getString(R.string.miperfil));
        ApplicationAventon.onCreate(getApplicationContext());
        setPresenter();
    }

    @Override
    public void initView() {
        discreteSeekBar = (DiscreteSeekBar) findViewById(R.id.discretSeekbar1);
        textViewSeekBar=(TextView)findViewById(R.id.tvTitleTime);
        tvTitle=(TextView)findViewById(R.id.txtSelecciona);
        piFumar=(ProhibitedImage)findViewById(R.id.imgProhibidoFumar);
        piAlimentos =(ProhibitedImage)findViewById(R.id.imgProhibidoComer);
        piBultos=(ProhibitedImage)findViewById(R.id.imgProhibidoBultos);
        piMascotas=(ProhibitedImage)findViewById(R.id.imgProhibidoAnimales);
        piNinios = (ProhibitedImage)findViewById(R.id.imgProhibidoNinios);
        piArmas = (ProhibitedImage)findViewById(R.id.imgProhibidoArmas);
        piDiscapacitados = (ProhibitedImage)findViewById(R.id.imgProhibidoDiscapacitados);
        piBasura = (ProhibitedImage)findViewById(R.id.imgProhibidoBasura);
        etObservaciones=(EditTextTamMax) findViewById(R.id.etObservaciones);
        rbMen=(RadioButton)findViewById(R.id.rbMen);
        rbWomen=(RadioButton)findViewById(R.id.rbWomen);
        rbIndistinto=(RadioButton)findViewById(R.id.rbNoimporta);
        rg1=(RadioGroup)findViewById(R.id.RadioGroup);
        listImagenes= Arrays.asList(piFumar,piAlimentos,piBultos,piMascotas);
        discreteSeekBar.setMax(30);
    }

    @Override
    public void setListeners() {
        //SeekBar que controla el tiempo de tolerancia
        discreteSeekBar.setOnProgressChangeListener(new OnCustomProgressListener(this,textViewSeekBar));
        rg1.setOnCheckedChangeListener(this);
    }

    @Override
    public void setPresenter() {
        presenter=new PreferenciasPresenterImpl();
        presenter.register(this);
        presenter.obtenerPreferencias();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }


    public void Next(View view){
        ArrayList<CatPreferenciasEmpleado>listPeferencias=new ArrayList<>();
        for(ProhibitedImage item: listImagenes){
            listPeferencias.add(new CatPreferenciasEmpleado(item.getSelectedId(),item.isSelectedValor(),item.getIdRelacion()));
        }
        presenter.insertarEditarPreferencias(this.isEditar,
                etObservaciones.getEditText().toString(),
                Utils.getTiempoToleranciaFormato(discreteSeekBar.getProgress()),idRelCaracteristicaUsuario,listPeferencias);
    }

    @Override
    public void setPreferencias(CaractEmpleado caractEmpleado, ArrayList<CatPreferenciasEmpleado> listPreferencias) {
        etObservaciones.setEditText(caractEmpleado.getObservaciones());
        discreteSeekBar.setProgress(Utils.getTiempoTolerancia(caractEmpleado.getTiempoTolerancia()));
        this.idRelCaracteristicaUsuario=caractEmpleado.getIdRelCaracteristicasUsuario();
        if(listPreferencias!=null &&(!listPreferencias.isEmpty())) {
            for (CatPreferenciasEmpleado item : listPreferencias) {
                this.isEditar=true;
                this.tvTitle.setText(item.getTituloGrupo());
                String description = item.getDescPreferencia();
                if (description.equalsIgnoreCase(getString(R.string.fumar))) {
                    piFumar.setIsChecked(item.getValor(), item.getIdPreferencia(), item.getIdRelacionPreferenciaUsuario());

                } else if (description.equalsIgnoreCase(getString(R.string.alimentos))) {
                    piAlimentos.setIsChecked(item.getValor(), item.getIdPreferencia(),item.getIdRelacionPreferenciaUsuario());

                } else if (description.equalsIgnoreCase(getString(R.string.equipaje))) {
                    piBultos.setIsChecked(item.getValor(), item.getIdPreferencia(),item.getIdRelacionPreferenciaUsuario());

                } else if (description.equalsIgnoreCase(getString(R.string.mascotas))) {
                    piMascotas.setIsChecked(item.getValor(), item.getIdPreferencia(), item.getIdRelacionPreferenciaUsuario());
                }/*
                //////Descomentar despues de que existan los servicios, falta ajustar la cadena////////
                else if(description.equalsIgnoreCase(getString(R.string.ninios))){
                   piNinios.setIsChecked(item.getValor(),item.getIdPreferencia(),item.getIdRelacionPreferenciaUsuario());
                } else if (description.equalsIgnoreCase(getString(R.string.armas))){
                    piArmas.setIsChecked(item.getValor(),item.getIdPreferencia(),item.getIdRelacionPreferenciaUsuario());
                }else if(description.equalsIgnoreCase(getString(R.string.minusvalidos))){
                    piDiscapacitados.setIsChecked(item.getValor(),item.getIdPreferencia(),item.getIdRelacionPreferenciaUsuario());
                }else if (description.equalsIgnoreCase(getString(R.string.basura))){
                    piBasura.setIsChecked(item.getValor(),item.getIdPreferencia(),item.getIdRelacionPreferenciaUsuario());
                }*/
            }
        }
        else{
            piFumar.setSelectedId(1);

            piAlimentos.setSelectedId(2);

            piBultos.setSelectedId(3);

            piMascotas.setSelectedId(4);

            /*Descomentar cuando estén los servicios
            piNinios.setSelectedId(5);

            piArmas.setSelectedId(6);

            piDiscapacitados.setSelectedId(7);

            piBasura.setSelectedId(8);
            */

            if(caractEmpleado!=null){
                isEditar=caractEmpleado.getTiempoTolerancia()!=null &&(!caractEmpleado.getTiempoTolerancia().isEmpty());
            }
            else{
                isEditar=false;
            }
        }
    }

    @Override
    public void navegarPublicar() {
        Intent intent=new Intent(this, PublicaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        //Se utiliza para aparecer un backgro
        //rbMen.setBackgrouund en Hombres, Mujeres, Indistinto y homologar con IOS
        if(checkedId==rbMen.getId()){
            rbMen.setBackground(ContextCompat.getDrawable(PreferencesActivity.this,R.drawable.circle_yellow));
        }
        else{
            rbMen.setBackgroundResource(0);
        }
        if(checkedId==rbWomen.getId()){

            rbWomen.setBackground(ContextCompat.getDrawable(PreferencesActivity.this,R.drawable.circle_yellow));
        }
        else{
            rbWomen.setBackgroundResource(0);
        }
        if(checkedId==rbIndistinto.getId()){

            rbIndistinto.setBackground(ContextCompat.getDrawable(PreferencesActivity.this,R.drawable.circle_yellow));
        }
        else{
            rbIndistinto.setBackgroundResource(0);
        }
    }
}