package com.sociomas.aventones.UI.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.ExpandQrActivity;
import com.sociomas.aventones.UI.Activities.QrScan.QrScanActivity;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Aventones.RolResponse;
import net.glxn.qrgen.android.QRCode;

/**
 * Created by jmarquezu on 05/10/2017.
 */

public class FragmentPasajeroConductor extends Fragment {
    private ImageView btnScan;
    private ImageView imgvewQr;
    private ImageView imgMano;
    private TextView tvNotaQr;
    private RelativeLayout viewYellow;
    private RecyclerView rvQr;
    private RolResponse respuesta;
    private SessionManager manager;
    private Animation upMove;
    private RelativeLayout rlConductor;
    private int timeInit=4500;
    private int timeFinish=1000;
    private CircleProgressbar circleProgressbar;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownToolTip;
    private static final String TRANSITION_NAME="qrTransition";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            if(getArguments().containsKey(ViewEvent.ENTRY)){
                respuesta =(RolResponse)getArguments().getSerializable(ViewEvent.ENTRY);
                manager=new SessionManager(getContext());
                manager.add(Constants.SP_ID_CONDUCTOR,respuesta.getIdNumEmpleadoConductor());
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        circleProgressbar = (CircleProgressbar)getView().findViewById(R.id.circle_loading2);
        circleProgressbar.setProgress(0);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_pasajero_conductor,viewGroup,false);
        imgvewQr = (ImageView)view.findViewById(R.id.imageQr);
        btnScan = (ImageView)view.findViewById(R.id.btn_scan);
        imgMano= (ImageView)view.findViewById(R.id.imgMano);
        circleProgressbar = (CircleProgressbar)view.findViewById(R.id.circle_loading2);
        tvNotaQr=(TextView)view.findViewById(R.id.tvNotaQR);
        startAnimations();
        imgvewQr.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Intent i = new Intent(getContext(),ExpandQrActivity.class);
                startActivity(i);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgressbar.setClockwise(false);
                circleProgressbar.setProgressWithAnimation(100,1000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent =  new Intent(getActivity(),QrScanActivity.class);
                        startActivity(intent);
                    }
                },800);
            }
        });
        imgvewQr.setImageBitmap(QRCode.from(Utils.getQrEmpleado(getContext()).getResultado()).bitmap());
        return view;
    }

    private void startAnimations(){

        upMove= AnimationUtils.loadAnimation(getContext(),R.anim.hand_right);
        imgMano.startAnimation(upMove);

        countDownTimer=new CountDownTimer(timeInit,timeFinish) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                imgMano.setVisibility(View.INVISIBLE);
            }
        }.start();

        countDownToolTip=new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                com.sociomas.aventones.UI.Controls.ToolTip.ViewTooltip
                        .on(tvNotaQr)
                        .autoHide(true,3100)
                        .corner(30)
                        .textColor(Color.WHITE)
                        .color(Color.BLACK)
                        .position(com.sociomas.aventones.UI.Controls.ToolTip.ViewTooltip.Position.BOTTOM)
                        .text(getString(R.string.toca_codigo))
                        .show();
            }
        }.start();
    }


}