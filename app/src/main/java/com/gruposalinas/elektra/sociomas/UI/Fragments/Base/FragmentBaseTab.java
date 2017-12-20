package com.gruposalinas.elektra.sociomas.UI.Fragments.Base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;

/**
 * Created by oemy9 on 31/07/2017.
 */

public class FragmentBaseTab extends Fragment {
    protected ControllerAPI controllerAPI;
    protected CustomProgressBar customProgressBar;
    protected Alertas alertaAsync;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.controllerAPI = new ControllerAPI(getContext());
        this.customProgressBar = new CustomProgressBar(getContext());
        this.alertaAsync = new Alertas(getContext());

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void showToast(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
}
