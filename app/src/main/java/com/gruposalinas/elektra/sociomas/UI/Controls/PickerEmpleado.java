package com.gruposalinas.elektra.sociomas.UI.Controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogEmpleadoPicker;

/**
 * Created by jmarquezu on 28/11/2017.
 */

public class PickerEmpleado extends RelativeLayout implements View.OnClickListener{
    private TextView tvOpcionesItem;
    private ImageView imgFlecha;
    String titulo = "titulo";
    public PickerEmpleado(Context context, String titulo) {
        super(context);
        this.titulo = titulo;
        inflateLayout(context,null);
    }

    public PickerEmpleado(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context,attrs);
    }

    public PickerEmpleado(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context,attrs);
    }

    public PickerEmpleado(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateLayout(context,attrs);
    }

    private void inflateLayout (Context context, AttributeSet attrs){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.item_empleado_picker,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvOpcionesItem = (TextView) findViewById(R.id.tvOpcionesItem);
        imgFlecha = (ImageView)findViewById(R.id.imageArrow);

        tvOpcionesItem.setOnClickListener(this);
        imgFlecha.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DialogEmpleadoPicker dialog = new DialogEmpleadoPicker(getContext());
        dialog.show();
        dialog.setTitulo("Justificaciones");
        dialog.initDialogPlantilla(titulo,false);

    }

    public void setText(String texto){
        tvOpcionesItem.setText(texto);
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
}
