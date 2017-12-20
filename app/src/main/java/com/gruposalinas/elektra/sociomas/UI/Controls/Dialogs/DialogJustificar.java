package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.EditTextTamMax;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.ListaEmpleado.LstEmpleado;

/**
 * Created by jmarquezu on 23/11/2017.
 */


public class DialogJustificar extends Dialog implements View.OnClickListener, DialogEditarJefe.ListenerDialogEditarJefe {
    /*ATRIBUTOS QUE SE NECESITAN PARA LA VISTA*/
    private String fecha;
    private String entrada;
    private String salida;
    private String dia;
    private String supervisor;
    private String idSupervisor;
    private String tipoIncidencia;
    private String comentario;

    private int recursoImagen;
    private String nombrePlantilla, numeroEmpleadoPlantilla;
    private int colorBackground;
    /*ATRIBUTOS COMPONENTES DEL DIALOGO*/
    private EditTextTamMax editText;
    private RelativeLayout rlHeader,rlHeaderPlantilla,rlAutocomplete;
    private TextView tvFecha,tvDia,tvHoraEntrada,tvHoraSalida, tvUserName,tvUserId,tvJefeInmediato,tvSalidaTemprano;
    private AutoCompleteTextView tvJefe;
    private ImageView imgIcono;
    private Button btnOk, btnRechazar;
    private boolean isPlantilla;
    private ImageView imgClose;
    private ImageView imgEdit;
    public JustificacionListener listener;
    private String hint;

    public DialogJustificar(@NonNull Context context, boolean isPlantilla) {
        super(context);
        this.isPlantilla=isPlantilla;
    }

    public DialogJustificar setTipoIncidencia(String tipo){
        if(tipo!=null) {
            this.tipoIncidencia = Utils.toUppperCaseFirst(tipo);
        }
        return this;
    }

    public DialogJustificar setFecha(String fecha){
        this.fecha=fecha;
        return this;
    }

    public DialogJustificar setComentario(String comentario){
        this.comentario=comentario;
        return this;
    }

    public DialogJustificar setEntrada(String entrada){
        this.entrada=entrada;
        return this;
    }

    public DialogJustificar setSalida(String salida){
        this.salida=salida;
        return this;
    }

    public DialogJustificar setDia(String dia){
        this.dia=dia;
        return this;
    }

    public DialogJustificar setRecursoImagen(int imagen){
        this.recursoImagen=imagen;
        return this;
    }

    public DialogJustificar setColorBackground(int color){
        this.colorBackground=color;
        return this;
    }

    public DialogJustificar setPlantilla(String numeroEmpleado, String nombreEmpleado){
        this.numeroEmpleadoPlantilla=numeroEmpleado;
        this.nombrePlantilla=nombreEmpleado;
        return this;
    }

    public DialogJustificar setNombreSupervisor(String supervisor){
        this.supervisor=supervisor;
        return this;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_dialog_justificaciones);
        tvSalidaTemprano=(TextView)findViewById(R.id.tvSalidaTemprano);
        imgIcono =  (ImageView)findViewById(R.id.imgIcono);
        imgClose=(ImageView)findViewById(R.id.imgClose);
        imgEdit = (ImageView)findViewById(R.id.imgEdit);
        tvFecha =  (TextView)findViewById(R.id.tvFecha);
        tvDia =  (TextView)findViewById(R.id.tvDia);
        editText = (EditTextTamMax)findViewById(R.id.etMaxTam);
        tvHoraEntrada = (TextView)findViewById(R.id.tvHoraEntrada);
        tvHoraSalida =  (TextView)findViewById(R.id.tvHoraSalida);
        tvUserId=(TextView)findViewById(R.id.tvUserId);
        tvUserName=(TextView)findViewById(R.id.tvUserName);
        tvJefe = (AutoCompleteTextView)findViewById(R.id.tvJefeName);
        tvJefeInmediato=(TextView)findViewById(R.id.tvJefeInmediato);
        rlHeader=(RelativeLayout)findViewById(R.id.rlHeader);
        rlHeaderPlantilla=(RelativeLayout)findViewById(R.id.rlHeaderPlantilla);
        rlAutocomplete=(RelativeLayout)findViewById(R.id.rlAutocomplete);
        btnOk =(Button)findViewById(R.id.btnJustificar);
        btnRechazar=(Button)findViewById(R.id.btnRechazar);
        btnRechazar.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        setDialogWindow();
        setInfoDialog();
    }

    private void setInfoDialog(){
        tvJefe.setFocusable(false);
        tvJefe.setCursorVisible(false);
        btnRechazar.setVisibility(isPlantilla? View.VISIBLE: View.GONE);
        rlHeaderPlantilla.setVisibility(isPlantilla? View.VISIBLE: View.GONE);
        tvSalidaTemprano.setText(tipoIncidencia);
        editText.setEditText(comentario);
        tvDia.setText(dia);
        tvHoraEntrada.setText(entrada);
        tvHoraSalida.setText(salida);
        tvFecha.setText(fecha);
        tvJefe.setText(supervisor);
        editText.setHintTextEditable(hint);
        imgIcono.setImageResource(recursoImagen);
        rlHeader.setBackgroundColor(ContextCompat.getColor(getContext(),colorBackground));
        if(isPlantilla){
            tvUserName.setText(nombrePlantilla);
            tvUserId.setText(numeroEmpleadoPlantilla);
            rlAutocomplete.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void setDialogWindow() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lp.gravity= Gravity.FILL;
        getWindow().setAttributes(lp);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public void setIcono(int icono){
        imgIcono.setImageResource(icono);
    }

    public void setHintDialogo(String hint){
        this.hint=hint;
        //editText.setHintTextEditable(hint);
    }

    public void setBackGround(int color){
        editText.setBackGroundEditable(color);
    }

    private boolean validarComentario(){
         boolean res=true;
        if (editText.getText().isEmpty()) {
            editText.setError(getContext().getString(R.string.comentario_vacio));
            res=false;
        }
        return res;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgClose:
                dismiss();
                break;
            case R.id.btnJustificar:
                if (listener != null) {
                   if(validarComentario()) {
                       if (isPlantilla) {
                           listener.onAutorizarRechazarIncidenciaPlantilla(true, editText.getText());
                       } else {
                           listener.onJustificarIncidencia(editText.getText());
                       }
                   }
                }
                break;

            case R.id.btnRechazar:
                if(listener!=null){
                    if(validarComentario()) {
                        if(isPlantilla){
                            listener.onAutorizarRechazarIncidenciaPlantilla(false, editText.getText());
                        }
                    }
                }
                break;
            case R.id.imgEdit:
                DialogEditarJefe dialogHorario = new DialogEditarJefe(getContext(),this);
                dialogHorario.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogHorario.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialogHorario.show();
                break;
        }
    }

    public void setDialogoJustificarListener(JustificacionListener listener){
        this.listener=listener;
    }

    @Override
    public void onJefeSelected(LstEmpleado empleado) {
            tvJefe.setText(empleado.getNombreCompleto());
            listener.onJefeSelected(empleado);

    }

    public interface JustificacionListener{
        void onJustificarIncidencia(String comentario);
        void onAutorizarRechazarIncidenciaPlantilla(boolean autorizar, String comentario);
        void onJefeSelected(LstEmpleado empleado);
    }
}
