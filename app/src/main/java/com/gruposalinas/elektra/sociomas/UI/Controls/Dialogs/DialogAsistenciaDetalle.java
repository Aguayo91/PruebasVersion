package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias.AsistenciaContador;

/**
 * Created by oemy9 on 16/08/2017.
 */

public class DialogAsistenciaDetalle {
    private String title;
    private Context context;
    private AlertDialog alertDialog;

    private AsistenciaContador contador;

    private TextView tvAsistencias;
    private TextView tvFaltas;
    private TextView tvSalidaAntes;
    private TextView tvDespuesHr;

    public DialogAsistenciaDetalle(Context context){
        this.context=context;
    }
    public DialogAsistenciaDetalle contador(AsistenciaContador contador){
        this.contador=contador;
        return this;
    }

    public DialogAsistenciaDetalle title(String title){
        this.title=title;
        return this;
    }

    public void setContador(AsistenciaContador contador) {
        this.contador = contador;
    }

    public void showAsync(){
       if(alertDialog==null) {
           final AlertDialog.Builder builderDialogo = new AlertDialog.Builder(this.context);
           LayoutInflater layoutInflater = LayoutInflater.from(this.context);
           View rootView = layoutInflater.inflate(R.layout.dialog_asistencias, null);
           //FINDVIEWS TEXTVIEWS
           tvAsistencias=(TextView)rootView.findViewById(R.id.tvAsistencias);
           tvDespuesHr=(TextView)rootView.findViewById(R.id.tvDespuesHr);
           tvFaltas=(TextView)rootView.findViewById(R.id.tvFaltas);
           tvSalidaAntes=(TextView)rootView.findViewById(R.id.tvSalidaAntes);
           builderDialogo.setView(rootView);
           builderDialogo.setNegativeButton(R.string.cerrar, null);
           this.alertDialog = builderDialogo.create();
           setAnimationDialog();
           this.alertDialog.show();
       }
       else{
           alertDialog.show();
       }
    }


    private void setAnimationDialog(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.windowAnimations = R.style.DialogAnimation;
        lp.gravity = Gravity.CENTER;
        alertDialog.getWindow().setAttributes(lp);
    }
}
