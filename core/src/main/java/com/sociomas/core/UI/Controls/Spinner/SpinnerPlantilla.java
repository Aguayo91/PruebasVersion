package com.sociomas.core.UI.Controls.Spinner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.sociomas.core.DataBaseModel.Empleado;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.R;
import com.sociomas.core.UI.Adapter.AdapterSpinnerPlantilla;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.Utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * Created by oemy9 on 02/11/2017.
 */

public class SpinnerPlantilla extends LinearLayout {

    private Spinner spPlantilla;
    private DBUtils dbUtils;
    private List<Plantilla> listPlantilla=new ArrayList<>();
    private AdapterSpinnerPlantilla adptPlantilla;
    private int currentPosition=0;

    public SpinnerPlantilla(Context context) {
        super(context);
        inflateLayouts(context);
    }

    public SpinnerPlantilla(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context);
    }

    public SpinnerPlantilla(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context);
    }

    private void  inflateLayouts(Context context){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.spinner_plantilla,this);
    }

    @Override
    protected void onFinishInflate()   {
        super.onFinishInflate();
        dbUtils=new DBUtils(getContext());
        spPlantilla=(Spinner)findViewById(R.id.spPlantilla);
    }

    public void initSpinnerPlantilla(@StringRes int tituloDialogo, @StringRes int delimitador,boolean mostrarMiUsuario){
            initSpinnerPlantilla(getContext().getString(tituloDialogo),getContext().getString(delimitador),mostrarMiUsuario);
    }

    public void initSpinnerPlantilla(String tituloDialogo, String delimitador, boolean mostrarMiUsuario){
        listPlantilla=dbUtils.obtenerEmpleadoPlantilla();
        //Usuario logeado
        Empleado empleado=Utils.getCurrentEmpleado(getContext());
        Plantilla pt=new Plantilla(empleado.getIdEmployee(),empleado.getName());
        //TIENE PLANTILLA
        if(!listPlantilla.isEmpty()){
            if(mostrarMiUsuario) {
                listPlantilla.add(pt);
            }
            Collections.reverse(listPlantilla);
            adptPlantilla =new AdapterSpinnerPlantilla(getContext(),R.layout.spinner_plantilla,listPlantilla);
            adptPlantilla.setTituloDialogo(tituloDialogo);
            adptPlantilla.setDelimitador(delimitador);
            spPlantilla.setAdapter(adptPlantilla);
        }
        //NO TIENE PLANTILLA
        else{
            adptPlantilla=new AdapterSpinnerPlantilla(getContext(),R.layout.spinner_plantilla, Arrays.asList(pt));
            adptPlantilla.setTituloDialogo(tituloDialogo);
            adptPlantilla.setDelimitador(delimitador);
            spPlantilla.setAdapter(adptPlantilla);
        }
    }


    public Spinner getSp() {
        return spPlantilla;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener){
        if(spPlantilla!=null){
            spPlantilla.setOnItemSelectedListener(listener);
        }
    }

    public Plantilla getSelectedItem() {
        return spPlantilla!=null? (Plantilla)spPlantilla.getSelectedItem(): null;
    }

    public int getSelectedItemAtPosition(){
        return spPlantilla!=null? spPlantilla.getSelectedItemPosition(): 0;
    }

    public void setSpPlantilla(Spinner spPlantilla) {
        this.spPlantilla = spPlantilla;
    }

    public List<Plantilla> getListPlantilla() {
        return listPlantilla;
    }

    public void setSelectedItem(Plantilla p){
      if(p!=null) {
          int index = listPlantilla.indexOf(p);
          if (index != -1) {
              spPlantilla.setSelection(index);
          }
      }
    }

    public void setSelection(int selection) {
        if(spPlantilla!=null){
            spPlantilla.setSelection(selection);
        }
    }
}
