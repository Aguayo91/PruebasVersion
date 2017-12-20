package com.sociomas.aventones.UI.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import net.glxn.qrgen.android.QRCode;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Rol.AventonRolUsuarioActivity;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Aventones.RolResponse;
import java.util.ArrayList;

/**
 * Created by jmarquezu on 05/10/2017.
 */

public class FragmentQrConductor extends Fragment {

    private ImageView imgvewQr;
    private ArrayList<String> alDestinos = new ArrayList<String>();
    private ArrayList<Bitmap> alBitMap = new ArrayList<Bitmap>();
    private RelativeLayout rlConductor;
    private RelativeLayout fragmentYellow;

    RolResponse respuesta;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AventonRolUsuarioActivity activity=(AventonRolUsuarioActivity)getActivity();
        activity.hideFloatRerservados();
        if(getArguments()!=null){
            if(getArguments().containsKey(ViewEvent.ENTRY)){
                respuesta =(RolResponse)getArguments().getSerializable(ViewEvent.ENTRY);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_conductor,viewGroup,false);
        imgvewQr = (ImageView)view.findViewById(R.id.imgQr);
        rlConductor=(RelativeLayout)view.findViewById(R.id.rlConductor);
        imgvewQr.setImageBitmap(QRCode.from(Utils.getQrEmpleado(getContext()).getResultado()).bitmap());
        return view;
    }

    public void showColor(View v){

    }
}
