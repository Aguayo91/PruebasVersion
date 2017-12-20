package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.sociomas.aventones.R;

public class DialogCars {

    private Context context;
    private AlertDialog alertDialog;
    private TextView tvTextDialog;

    public DialogCars(Context context){
        this.context=context;
    }

    public void showDialogCapacidad(){
        if(alertDialog==null) {
            final AlertDialog.Builder builderDialogo = new AlertDialog.Builder(this.context);
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            View rootView = layoutInflater.inflate(R.layout.info_placas, null);
            tvTextDialog=(TextView)rootView.findViewById(R.id.tvTextDialog);
            tvTextDialog.setText(R.string.tvCapacidad);
            builderDialogo.setView(rootView);
            builderDialogo.setNegativeButton(R.string.ok, null);
            this.alertDialog = builderDialogo.create();
            setAnimationDialog();
            this.alertDialog.show();
        }
        else{
            alertDialog.show();
        }
    }
    public void showDialogPlacas(){
        if(alertDialog==null) {
            final AlertDialog.Builder builderDialogo = new AlertDialog.Builder(this.context);
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            View rootView = layoutInflater.inflate(R.layout.info_placas, null);
            tvTextDialog=(TextView)rootView.findViewById(R.id.tvTextDialog);
            tvTextDialog.setText(R.string.introduceplacas);
            builderDialogo.setView(rootView);
            builderDialogo.setNegativeButton(R.string.ok, null);
            this.alertDialog = builderDialogo.create();
            setAnimationDialog();
            this.alertDialog.show();
        }
        else{
            alertDialog.show();
        }
    }
    public void showColor (){
        if (alertDialog==null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            LayoutInflater inflater = LayoutInflater.from(this.context);
            View rootView = inflater.inflate(R.layout.activity_recycler_color,null);
            builder.setView(rootView);
            builder.setNegativeButton(R.string.seleccionar,null);
            this.alertDialog = builder.create();
            setAnimationDialog();
            this.alertDialog.show();
        }else {
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