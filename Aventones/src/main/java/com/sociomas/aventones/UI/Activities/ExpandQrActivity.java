package com.sociomas.aventones.UI.Activities;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sociomas.aventones.R;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Utils;

import net.glxn.qrgen.android.QRCode;

public class ExpandQrActivity extends BaseCoreCompactActivity {

    private ImageView imgQr;
    private Context context;
    private Animation transparent;
    private ConstraintLayout coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_qr);
        imgQr=(ImageView)findViewById(R.id.imgQr);
        imgQr.setImageBitmap(QRCode.from(Utils.getQrEmpleado(this).getResultado()).bitmap());
        coordinator=(ConstraintLayout) findViewById(R.id.coorLayout);
        transparent= AnimationUtils.loadAnimation(ExpandQrActivity.this,R.anim.layout_transparent);
        coordinator.startAnimation(transparent);
    }

    public void backToFragment(View v){
        finish();
    }
}
