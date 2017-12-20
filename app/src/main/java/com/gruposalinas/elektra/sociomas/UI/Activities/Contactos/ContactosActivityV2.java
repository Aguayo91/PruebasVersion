package com.gruposalinas.elektra.sociomas.UI.Activities.Contactos;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado.JustificacionSelectionActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.ContactoEditText;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Contactos.AdapterSpinnerPais;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Contacto.TelefonoContacto;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Contacto.RootClave;
import net.frederico.showtipsview.ShowTipsBuilder;
import net.frederico.showtipsview.ShowTipsView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ContactosActivityV2 extends BaseAppCompactActivity implements ContactoEditText.ContactoEditTextListener {
    public static final String TAG="ACTIVITY_CONTACTO";
    private View menuAdd;
    private ArrayList<TelefonoContacto>telefonoContactos=new ArrayList<TelefonoContacto>();;
    private ControllerAPI controllerAPI;
    private static final int RESULT_PICK_CONTACT = 80;
    private ContactoEditText contactoEditUno,contactoEditDos,contactoEditTres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_v2);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        this.setToolBar(getString(R.string.contactos_title));
        contactoEditUno=(ContactoEditText)findViewById(R.id.ContactoEditUno);
        contactoEditDos=(ContactoEditText)findViewById(R.id.ContactoEditDos);
        contactoEditTres=(ContactoEditText)findViewById(R.id.ContactoEditTres);
        contactoEditUno.setListener(this);
        contactoEditDos.setListener(this);
        contactoEditTres.setListener(this);
        doPeticion();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(ContactosActivityV2.this);
                }
            });
        }
    }

    private void doPeticion(){
        final CustomProgressBar progressBar=new CustomProgressBar(this);
        progressBar.show(this);
        controllerAPI=new ControllerAPI(this);
        controllerAPI.getClavesTelefonicas(new ServerRequest()).enqueue(new Callback<RootClave>() {
            @Override
            public void onResponse(Call<RootClave> call, Response<RootClave> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        callBackResponse(response.body());
                        progressBar.dismiss();
                    }
                }
            }
            @Override
            public void onFailure(Call<RootClave> call, Throwable t) {

            }
        });
    }

    private void callBackResponse(RootClave rootClave){
        AdapterSpinnerPais adapterSpinnerPais= new AdapterSpinnerPais(
                ContactosActivityV2.this,
                R.layout.item_pais,
                rootClave.getClaves());

        contactoEditUno.setAdapter(adapterSpinnerPais);
        contactoEditDos.setAdapter(adapterSpinnerPais);
        contactoEditTres.setAdapter(adapterSpinnerPais);
        contactoEditUno.loadContactos();
        contactoEditDos.loadContactos();
        contactoEditTres.loadContactos();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_PICK_CONTACT) {
            contactPicked(data);
        }

    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            Uri.Builder builder=data.getData().buildUpon();
            Uri datUriDos=builder.build();
            cursor = getContentResolver().query(datUriDos, null, null, null, null);
            cursor.moveToFirst();
            int  telefonoIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int  nombreIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            insertContacto(cursor.getString(nombreIndex),cursor.getString(telefonoIndex));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertContacto(String nombre,String telefono){
            if(contactoEditUno.isEmptyNombre() && contactoEditUno.isEmptyTelefono()){
                contactoEditUno.setParamNumero(nombre,telefono);
            }
            else if(contactoEditDos.isEmptyNombre() && contactoEditDos.isEmptyTelefono()){
                contactoEditDos.setParamNumero(nombre,telefono);
            }
            else if(contactoEditTres.isEmptyNombre() && contactoEditTres.isEmptyTelefono()){
                contactoEditTres.setParamNumero(nombre,telefono);
            }

    }

    private void saveContactos(){
        boolean saveInWebService=false;

       if(!contactoEditUno.isEmptyTelefono() || !contactoEditUno.isEmptyNombre()) {
           if (contactoEditUno.validAll(this, "uno")) {
               contactoEditUno.saveManager();
               telefonoContactos.add(new TelefonoContacto(contactoEditUno.getTelefono(),contactoEditUno.getIdInternacional()));
               saveInWebService=true;
           }
           else{
               saveInWebService=false;
           }
       }
        if(!contactoEditDos.isEmptyTelefono() || !contactoEditDos.isEmptyNombre()){
           if (contactoEditDos.validAll(this, "dos")) {
               contactoEditDos.saveManager();
               telefonoContactos.add(new TelefonoContacto(contactoEditDos.getTelefono(),contactoEditDos.getIdInternacional()));
               saveInWebService=true;
           }
            else{
               saveInWebService=false;
           }
        }
        if(!contactoEditTres.isEmptyTelefono()|| !contactoEditTres.isEmptyNombre()){
            if (contactoEditTres.validAll(this, "tres")) {
                contactoEditTres.saveManager();
                telefonoContactos.add(new TelefonoContacto(contactoEditTres.getTelefono(),contactoEditTres.getIdInternacional()));
                saveInWebService=true;
            }
            else{
                saveInWebService=false;
            }
        }
        if(saveInWebService){
            controllerAPI.insertarTelefonoContactoAsync(telefonoContactos);
            telefonoContactos.clear();
        }

    }

    public void onClick(View view) {
        saveContactos();
    }

    @Override
    public void onContactoChooser(boolean chooser) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }
}
