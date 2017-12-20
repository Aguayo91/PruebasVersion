package com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Contactos.AdapterSpinnerPais;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Model.Response.Contacto.Clave;

/**
 * Created by oemy9 on 11/05/2017.
 */

@SuppressWarnings("unused")
public class ContactoEditText extends LinearLayout implements View.OnClickListener {
    private TextView tvNoContacto;
    private EditText txtParamU, txtNumber;
    private Spinner spinnerPais;
    private AdapterSpinnerPais adapterSpinnerPais;
    private ImageView imageContacto;
    private boolean isCompleted=true;
    private Context context;
    private int noContacto;
    public static final String DATOS_GUARDADOS="guardar";
    public interface  ContactoEditTextListener{
        void onContactoChooser(boolean chooser);
    }
    private ContactoEditTextListener listener;

    public ContactoEditText(Context context) {
        super(context);
        inflateLayouts(context);
    }
    public ContactoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ContactoEditText);
        noContacto=typedArray.getInt(R.styleable.ContactoEditText_numeroContacto,1);
        inflateLayouts(context);
    }

    public ContactoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ContactoEditText);
        noContacto=typedArray.getInt(R.styleable.ContactoEditText_numeroContacto,1);
        inflateLayouts(context);
    }


    private void  inflateLayouts(Context context)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.contacto_view,this);
        this.context=context;
    }

    public void setListener(ContactoEditTextListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.tvNoContacto=(TextView)findViewById(R.id.tvContactoNo);
        this.txtNumber =(EditText)findViewById(R.id.textNumeroTelefono);
        this.txtParamU=(EditText)findViewById(R.id.txtNombre);
        this.spinnerPais=(Spinner)findViewById(R.id.spinnerPais);
        this.imageContacto=(ImageView)findViewById(R.id.imageContacto);
        this.imageContacto.setOnClickListener(this);
        tvNoContacto.setText("Contacto ".concat(String.valueOf(noContacto)).concat(":"));
    }

    public  void setAdapter(AdapterSpinnerPais adapterSpinnerPais){
        this.adapterSpinnerPais=adapterSpinnerPais;
        this.spinnerPais.setAdapter(adapterSpinnerPais);
        this.spinnerPais.setPrompt("País");
    }

    public void setParamNumero(String param, String numero){
        numero=numero.replace("+52 1","");
        numero=numero.replace("+52","");
        numero=numero.replaceAll("\\s+","");
        numero=numero.replace("-","");
        txtParamU.setText(param);
        txtNumber.setText(numero);
    }

    public String getNombre() {
        return txtParamU.getText().toString();
    }
    public void setNombre(String nombre){
        this.txtParamU.setText(nombre);
    }
    public String getTelefono() {
        return txtNumber.getText().toString();
    }
    public String getLada(){
        Clave clave=(Clave) this.spinnerPais.getSelectedItem();
        return clave.getCodigo();
    }
    public String getIdInternacional(){
        Clave clave=(Clave) this.spinnerPais.getSelectedItem();
        return String.valueOf(clave.getId());
    }
    public boolean isEmptyNombre(){
        return txtParamU.getText().toString().isEmpty();
    }
    public boolean isEmptyTelefono(){
        return txtNumber.getText().toString().isEmpty();
    }

    public boolean isTelefonoValido(){
        return txtNumber.getText().toString().length()==10;
    }

    public boolean isValido(){
        return !txtParamU.getText().toString().isEmpty() && !txtNumber.getText().toString().isEmpty();
    }

    public boolean paisSeleccionado(){
       return this.spinnerPais.getSelectedItemPosition()!=adapterSpinnerPais.getCount();
    }

    public void saveManager(){
        switch (noContacto){
            case 1:
                saveManagerInternal(Constants.tel1,Constants.nombre1,Constants.lada);
                break;
            case 2:
                saveManagerInternal(Constants.tel2,Constants.nombre2,Constants.lada1);
                break;

            case 3:
                saveManagerInternal(Constants.tel3,Constants.nombre3,Constants.lada2);
                break;
        }
    }

    public void loadContactos(){
        switch (noContacto){
            case 1:
                loadInternal(Constants.tel1,Constants.nombre1,Constants.lada);
                break;
            case 2:
                loadInternal(Constants.tel2,Constants.nombre2,Constants.lada1);
                break;

            case 3:
                loadInternal(Constants.tel3,Constants.nombre3,Constants.lada2);
                break;
        }
    }

    private void saveManagerInternal(String keyTelefono,String keyNombre,String keyLada){
        SessionManager manager=new SessionManager(this.context);
        manager.add(keyTelefono,txtNumber.getText().toString());
        manager.add(keyNombre,txtParamU.getText().toString());
        manager.add(keyLada,String.valueOf(spinnerPais.getSelectedItemPosition()));
        manager.add(this.DATOS_GUARDADOS,true);
    }

    private void loadInternal(String keyTelefono,String keyNombre,String keyLada){
        SessionManager manager=new SessionManager(this.context);
        if(manager.getString(keyTelefono)!=null){
            txtNumber.setText(manager.getString(keyTelefono));
            isCompleted=true;
        }

        if(manager.getString(keyNombre)!=null){
            txtParamU.setText(manager.getString(keyNombre));
            isCompleted=true;
        }


        if(manager.getString(keyLada)!=null) {
            spinnerPais.setSelection(Integer.valueOf(manager.getString(keyLada)), false);
            isCompleted=true;
        }
        else{
            this.spinnerPais.setSelection(adapterSpinnerPais.getCount());
            isCompleted=false;
        }
        if(manager.getString(keyTelefono)==null || manager.getString(keyNombre)==null){
            this.isCompleted=false;
        }
    }

    public boolean isCompleted(Context context) {
        SessionManager  manager=new SessionManager(context);
        return manager.get(DATOS_GUARDADOS);
    }

    public boolean validAll(Activity activity,String key){
        boolean result=false;
        Alertas alertas=new Alertas(activity);
        SessionManager manager=new SessionManager(this.context);

         if(!this.isValido()){
            alertas.showError(activity,activity.getString(R.string.contacto_aviso),
                    activity.getString(R.string.contacto_valida_nombre,key));
        }
        else if(!this.isTelefonoValido()){
            alertas.showError(activity,activity.getString(R.string.contacto_aviso),
                    activity.getString(R.string.contacto_valida_telefono,key));
        }
        else if(!this.paisSeleccionado()){
            alertas.showError(activity,activity.getString(R.string.contacto_aviso),
                   activity.getString(R.string.contacto_valida_pais,key));
        }
        else {
            result=true;
        }
        return  result;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null) {
            listener.onContactoChooser(true);
        }
    }
}
