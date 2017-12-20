package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterSearchSpinner;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.WebService.Model.Response.Permisos.ExclusionesEmpleado;

import java.util.ArrayList;

/**
 * Created by oemy9 on 24/05/2017.
 */

public class ChooserBox {
    private AlertDialog alertDialog;
    private ArrayList<ExclusionesEmpleado>listEmpleados;
    public void setListEmpleados(ArrayList<ExclusionesEmpleado> listEmpleados,boolean addTodos) {
        if(this.listEmpleados==null){
            this.listEmpleados=new ArrayList<>();
        }
        this.listEmpleados.clear();
        if(addTodos){
            ExclusionesEmpleado exclusionesEmpleado=new ExclusionesEmpleado();
            exclusionesEmpleado.setIdNumEmpleado(AdapterSearchSpinner.TODOS);
            exclusionesEmpleado.setVaNombreCompleto(AdapterSearchSpinner.TODOS);
            this.listEmpleados.add(exclusionesEmpleado);
        }

        for(ExclusionesEmpleado empleado: listEmpleados){
            this.listEmpleados.add(empleado);
        }
    }
    private CharSequence[]toArrayCharSequence() throws Exception {
        if(listEmpleados==null){
            throw  new Exception("El listado de empleados no puede ser nullo");
        }
        CharSequence[]items=new CharSequence[listEmpleados.size()];
        int count=0;
        for(ExclusionesEmpleado empleado: listEmpleados){
            items[count]=empleado.getVaNombreCompleto();
            count++;
        }
        return  items;
    }

    private String title;
    public void setTitle(String title) {
        this.title = title;
    }

    public interface ChooserBoxCallBack{
        void onResult(String result);
    }

    private ChooserBoxCallBack chooserBoxCallBack;

    public void setChooserBoxCallBack(ChooserBoxCallBack chooserBoxCallBack) {
        this.chooserBoxCallBack = chooserBoxCallBack;
    }

    private Context context;

    public ChooserBox(Context context){
        this.context=context;
    }
    public void showAsync(){
        try {
            final CharSequence[]arrayCharSequence=toArrayCharSequence();
            final AlertDialog.Builder builderDialogo=new AlertDialog.Builder(this.context);
            builderDialogo.setTitle(title);
            builderDialogo.setSingleChoiceItems(arrayCharSequence,1,null);
            builderDialogo.setPositiveButton(context.getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(chooserBoxCallBack!=null && alertDialog!=null){
                        int selectedPosition=alertDialog.getListView().getCheckedItemPosition();
                        chooserBoxCallBack.onResult(arrayCharSequence[selectedPosition].toString());
                    }
                }
            });
            builderDialogo.setNegativeButton(context.getString(R.string.cancelar),null);
            alertDialog=builderDialogo.create();
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
