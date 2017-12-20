package com.gruposalinas.elektra.sociomas.UI.Activities.Login;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.SliderActivity;
import com.gruposalinas.elektra.sociomas.Utils.SupportUtils;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Response.Registro.RegistroResponse;

public class LoginActivity extends BaseAppCompactActivity implements
        View.OnClickListener,
        LoginPresenterImpl.LoginView{

    private ImageView imgLogo;
    private TextView tvVersion;
    private EditText txtNumeroEmpleado,txtTelefono,txtLlaveMaestra;
    private Button btnLogin;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_v3);
        setToolBar(getString(R.string.bienvenido));
        setPresenter();
    }

    @Override
    public void initView() {
        this.txtNumeroEmpleado=(EditText)findViewById(R.id.txtNumeroEmpleado);
        this.txtLlaveMaestra=(EditText)findViewById(R.id.txtLlaveMaestra);
        this.imgLogo=(ImageView)findViewById(R.id.imglogo);
        this.tvVersion=(TextView)findViewById(R.id.tvVersion);
        this.btnLogin=(Button)findViewById(R.id.btnLogin);
    }

    @Override
    public void setListeners() {
        this.btnLogin.setOnClickListener(this);
        if(tvVersion!=null) {
            this.tvVersion.setText(getString(R.string.version_footer, getString(R.string.version_name)));
        }
        SupportUtils.checkIfGooglePlayServices(this);
        iniciarAnimaciones();
    }
    private void iniciarAnimaciones(){
        imgLogo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.saltar));
    }

    @Override
    public void setPresenter() {
        presenter=new LoginPresenterImpl();
        presenter.register(this);
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public void onClick(View view) {
      presenter.validarCredenciales(txtNumeroEmpleado.getText().toString(), txtLlaveMaestra.getText().toString());
    }

    @Override
    public void onEmpleadoVacio() {
        txtNumeroEmpleado.setError(getString(R.string.revisa_empleado_correcto));
        txtNumeroEmpleado.requestFocus();
        imgLogo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
    }
    @Override
    public void onLlaveMaestraVacia() {
        txtLlaveMaestra.setError(getString(R.string.revisa_llave_maestra));
        txtLlaveMaestra.requestFocus();
        imgLogo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
    }

    @Override
    public void onTelefonoVacio() {
      txtTelefono.setError(getString(R.string.revisa_numero_valido));txtTelefono.requestFocus();
      imgLogo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
    }

    @Override
    public void onSuccess(RegistroResponse serverResponse) {
        saveInDatabase(txtNumeroEmpleado.getText().toString(),serverResponse.getEmpleado());
        startActivity(new Intent(this,SliderActivity.class));
        finish();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }
}
