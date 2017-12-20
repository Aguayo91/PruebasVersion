package com.gruposalinas.elektra.sociomas.UI.Activities.Codigo.Verificar;
import android.animation.Animator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.InputBox;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.TextWatchers.TextWatcherSMS;

import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;

public class VerificarSmsActivity extends BaseAppCompactActivity implements VerificarSmsPresenterImpl.VerificarSmsView{

    private boolean isOpen;
    private VerificarSmsPresenter presenter;
    private BroadcastReceiver bReceiver;
    private LocalBroadcastManager bManager;
    private RelativeLayout layoutMain;
    private RelativeLayout layoutButtons;
    private RelativeLayout layoutContent;
    private ImageView imageOk;
    private EditText codeUnoTxt,codeDosTxt;
    private TextView tvVerificadoOk;
    private TextView tvTelefono;
    private String numeroTelefono;
    private String codigoObtenido;
    private InputBox inputBox=new InputBox(this);

    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    public void setNumeroTelefono(String numeroTelefono)
    {
        this.numeroTelefono = numeroTelefono;
    }
    public void saveNumeroTelefono(String numeroTelefono){manager.add(Constants.MY_PHONE,numeroTelefono);}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_sms);
        setNumeroTelefono(manager.getString(Constants.MY_PHONE));
        setPresenter();
        getCodigoFromSMS(getIntent());
    }

    //region animacion
    private void startAnimacionActivity(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if(!isOpen) {
                int x = layoutContent.getRight();
                int y = layoutContent.getBottom();
                int startRadius = 0;
                int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());
                Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Animation showAnimation= AnimationUtils.loadAnimation(VerificarSmsActivity.this,R.anim.fade_in);
                        imageOk.setAnimation(showAnimation);
                        tvVerificadoOk.setAnimation(showAnimation);
                        imageOk.setVisibility(View.VISIBLE);
                        tvVerificadoOk.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(VerificarSmsActivity.this, InicioActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                finish();
                            }
                        },1300);

                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
                layoutButtons.setVisibility(View.VISIBLE);
                anim.start();
                isOpen = true;
            }

        }
        else{
            Intent intent=new Intent(VerificarSmsActivity.this, InicioActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }
    }
    // endregion

    public void enviarCodigo(View v){
      presenter.validarCodigo(codeUnoTxt.getText().toString(),codeDosTxt.getText().toString(),getNumeroTelefono());
    }
    public void reenviarCodigo(View v){
        presenter.reenviarCodigo(getNumeroTelefono());
    }

    public  void llamarTelefono(View v){
       presenter.requestLlamadaTelefonica(getNumeroTelefono());
    }

    public void showDialogo(View v){
        inputBox.setTextDefault(numeroTelefono);
        inputBox.setInputType(InputType.TYPE_CLASS_PHONE);
        inputBox.setTitle(getString(R.string.editar_numero_telefonico));
        inputBox.setErrorText(getString(R.string.numero_telefonico_validar));
        inputBox.showAsync();
        inputBox.setCallBack(new InputBox.inputBoxCallBack() {
            @Override
            public void OnResult(String result) {
                setNumeroTelefono(result);
                tvTelefono.setText(getNumeroTelefono());
                reenviarCodigo(tvTelefono);
            }

            @Override
            public void OnCancel() {
            }
        });
    }
    private void getCodigoFromSMS(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra(Constants.CODIGO_VERIFICACION)) {
                String[] digitosCodigo = intent.getStringArrayExtra(Constants.CODIGO_VERIFICACION);
                if (digitosCodigo.length == 2) {
                    codeUnoTxt.setText(digitosCodigo[0]);
                    codeDosTxt.setText(digitosCodigo[1]);
                }

            }
        }
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void initView() {
        layoutButtons=(RelativeLayout)findViewById(R.id.layoutButtons);
        layoutMain=(RelativeLayout)findViewById(R.id.layoutMain);
        layoutContent=(RelativeLayout)findViewById(R.id.layoutContent);
        codeUnoTxt=(EditText)findViewById(R.id.codeUnoTxt);
        codeDosTxt=(EditText)findViewById(R.id.codeDosTxt);
        tvTelefono=(TextView)findViewById(R.id.tvNumberPhone);
        tvVerificadoOk=(TextView)findViewById(R.id.tvVerificadoOk);
        imageOk=(ImageView)findViewById(R.id.imageOk);
    }

    @Override
    public void setListeners() {
        tvTelefono.setText(getNumeroTelefono());
        TextWatcherSMS textWatcher=new TextWatcherSMS();
        textWatcher.setActivity(this)
                .setCodigoUno(codeUnoTxt)
                .setCodigoDos(codeDosTxt);

        codeUnoTxt.addTextChangedListener(textWatcher);
        codeDosTxt.addTextChangedListener(textWatcher);

    }

    @Override
    public void setPresenter() {
        presenter=new VerificarSmsPresenterImpl();
        presenter.register(this);
    }




    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public void onLlamadaExito() {
        showToast(getString(R.string.momento_llamada));
    }

    @Override
    public void reenvioExito() {
        showToast(getString(R.string.momento_sms));
    }

    @Override
    public void numeroTelefonicoValidado() {
        saveNumeroTelefono(getNumeroTelefono());
        manager.add(Constants.TELEFONO_VALIDADO,true);
        startAnimacionActivity();
    }



    @Override
    public void onCodigoIncorrecto(String mensaje) {
        codeUnoTxt.setError(mensaje);
        codeUnoTxt.requestFocus();
    }


}