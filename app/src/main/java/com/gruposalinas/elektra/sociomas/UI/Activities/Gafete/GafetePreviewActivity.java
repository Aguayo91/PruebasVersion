package com.gruposalinas.elektra.sociomas.UI.Activities.Gafete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.StringUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GafetePreviewActivity extends AppCompatActivity {

    private CircleImageView imgFoto;
    private SessionManager manager;
    private TextView tvNombre;
    private TextView tvApellidos;
    private TextView tvSocio;
    private TextView tvPuesto;
    private String imgBase64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gafete_preview);
        imgFoto  = (CircleImageView) findViewById(R.id.imgFoto);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvApellidos = (TextView) findViewById(R.id.tvApellidos);
        tvSocio = (TextView) findViewById(R.id.tvSocio);
        tvPuesto = (TextView) findViewById(R.id.tvPuesto);
        manager=new SessionManager(this);
        String imgBase64 = manager.getString(Constants.USUARIO_FOTO_FRONTAL);
        imgFoto.setImageBitmap(Utils.convertBase64ToBitmap(imgBase64));
        StringUtils stringUtils=new StringUtils();
        List<String> nombreSeparado=stringUtils.getNombreSeparadoInternal(com.sociomas.core.Utils.Utils.getCurrentEmpleado(this).getName());
        if(nombreSeparado!=null && !nombreSeparado.isEmpty()) {
            tvNombre.setText(nombreSeparado.get(0));
            tvApellidos.setText(nombreSeparado.get(1));
        }
        tvSocio.setText(getString(R.string.socio).concat(com.sociomas.core.Utils.Utils.getNumeroEmpleado(this)));
        if (manager.getString(Constants.PUESTO_EMPLEADO) != null) {
            tvPuesto.setText(manager.getString(Constants.PUESTO_EMPLEADO));
        } else {
            tvPuesto.setVisibility(View.GONE);
        }
//        tvPuesto.setText(g.getPuestoEmpleado());
    }
}
