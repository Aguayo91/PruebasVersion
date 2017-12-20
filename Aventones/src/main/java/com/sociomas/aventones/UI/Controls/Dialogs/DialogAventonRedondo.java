package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Controls.Base.IBaseControl;
import com.sociomas.aventones.UI.Controls.Model.Dia;
import com.sociomas.aventones.UI.Controls.Pickers.FrecuenciaPicker;

import java.util.ArrayList;

/**
 * Created by jmarquezu on 29/09/2017.
 */

public class DialogAventonRedondo extends Dialog{

    public RelativeLayout Calendar1;
    public DialogDiaPicker dialogDiaPicker;

    public DialogAventonRedondo (@NonNull Context context){
        super (context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_aventon_redond);
        Calendar1 = (RelativeLayout)findViewById(R.id.rlDias) ;

        Calendar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDias();
            }

        });
    }

    public void showDias(){
        dialogDiaPicker = new DialogDiaPicker(getContext(), new FrecuenciaPicker.OnFrecuenciaListener() {
            @Override
            public void onFinishDaySelection(ArrayList<Dia> listDias,String diasSeleccionados) {
                if(!listDias.isEmpty()){
                    //dias = listDias;
                    dialogDiaPicker.dismiss();
                    //joinDiasSeleccionados();
                }else{
                    Toast.makeText(getContext(),"Selecciona al menos un día.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCloseDaySelection() {

            }
        });
        dialogDiaPicker.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDiaPicker.show();
    }
}
