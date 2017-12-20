package com.sociomas.aventones.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.QrScan.QrScanActivity;
import com.sociomas.aventones.UI.Activities.Rol.AventonRolUsuarioActivity;

/**
 * Created by jmarquezu on 05/10/2017.
 */

public class FragmentScanPasajero extends Fragment{

    ImageView imgScan;
    CircleProgressbar circleProgressbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AventonRolUsuarioActivity activity=(AventonRolUsuarioActivity)getActivity();
        activity.hideFloatActionsPasajero();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_layout_pasajero,container,false);
        imgScan = (ImageView)view.findViewById(R.id.btn_scan);
        circleProgressbar = (CircleProgressbar)view.findViewById(R.id.circle_loading2);
        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgressbar.setClockwise(false);
                circleProgressbar.setProgressWithAnimation(100,1000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), QrScanActivity.class);
                        startActivity(intent);
                    }
                },800);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        circleProgressbar.setProgress(0);
    }

}
