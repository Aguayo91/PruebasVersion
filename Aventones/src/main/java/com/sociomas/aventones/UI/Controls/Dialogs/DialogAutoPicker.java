package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.AdapterAutoDialog;
import com.sociomas.aventones.UI.Adapters.AutosRecyclerAdapter;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;

import java.util.List;

/**
 * Created by oemy9 on 10/10/2017.
 */

public class DialogAutoPicker implements View.OnClickListener, RecyclerItemClickListener{

    private RecyclerView recyclerViewAuto;
    private Context context;
    private ImageView imgclose;
    private Button btnGuardar;
    private Dialog alert;
    private Vehiculo selectedVehiculo;
    public interface onVehiculoListener{
        void onVehiculoSeleccionado(Vehiculo selectedVehiculo);
    }
    private onVehiculoListener listener;


    public void setListener(onVehiculoListener listener) {
        this.listener = listener;
    }

    public DialogAutoPicker(Context context){
        this.context=context;
    }

    public void show(){
        alert = new Dialog(context);
        LayoutInflater inflater=LayoutInflater.from(context);
        final View dialogo=inflater.inflate(R.layout.dialog_auto_picker, null);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(dialogo);
        recyclerViewAuto=(RecyclerView)dialogo.findViewById(R.id.recyclerAutoDialog);
        imgclose=(ImageView)dialogo.findViewById(R.id.imgclose);
        btnGuardar=(Button)dialogo.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
        imgclose.setOnClickListener(this);
        alert.show();
    }
    public void setListAutos(List<Vehiculo> list){
        if(!list.isEmpty()) {
            AdapterAutoDialog adapter = new AdapterAutoDialog(context, list);
            adapter.setItemClickListener(this);
            recyclerViewAuto.setAdapter(adapter);
            LinearLayoutManager layoutManager=new LinearLayoutManager(context);
            recyclerViewAuto.setLayoutManager(layoutManager);
        }
        else{
            Toast.makeText(context,"Lista de vehiculo vacia",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnGuardar){
            if(alert!=null){
                alert.dismiss();
                if(selectedVehiculo!=null){
                    listener.onVehiculoSeleccionado(selectedVehiculo);
                }
            }
        }
        else if(v.getId()==R.id.imgclose){
            if(alert!=null){
                alert.dismiss();
            }
        }
    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        Vehiculo vehiculo=(Vehiculo)selectedItem;
        this.selectedVehiculo=vehiculo;
        listener.onVehiculoSeleccionado(selectedVehiculo);
        if(alert!=null){
            alert.dismiss();
        }
    }
}
