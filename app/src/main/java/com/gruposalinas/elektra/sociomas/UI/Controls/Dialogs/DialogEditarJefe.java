package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado.JustificacionSelectionInteractor;
import com.gruposalinas.elektra.sociomas.UI.Adapters.AdapterPlantillaJefe;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.UI.Controls.Notification.CustomProgressBar;
import com.sociomas.core.UI.Controls.Progress.ProgressBubble;
import com.sociomas.core.WebService.Model.Response.ListaEmpleado.ListaEmpleadoReponse;
import com.sociomas.core.WebService.Model.Response.ListaEmpleado.LstEmpleado;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by jromeromar on 28/11/2017.
 */

public class DialogEditarJefe extends Dialog implements RecyclerItemClickListener,View.OnClickListener,  Consumer<CharSequence>, Subscriber<ListaEmpleadoReponse> {

    private static final String TAG = "DialogEditarJefe";
    private Context context;
    private EditText etSearchJefe;
    private RecyclerView recyclerListaJefe;
    private ImageView imgAgregar;
    private ImageView imgCancel;
    private ProgressBubble pgBubble;
    private CustomProgressBar customProgressBar;
    private JustificacionSelectionInteractor interactor;
    public interface  ListenerDialogEditarJefe{
        void onJefeSelected(LstEmpleado empleado);
    }

    ListenerDialogEditarJefe listenerDialogEditarJefe;

    public DialogEditarJefe(@NonNull Context context, ListenerDialogEditarJefe listener) {
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        interactor=new JustificacionSelectionInteractor();
        this.customProgressBar=new CustomProgressBar(getContext());
        this.listenerDialogEditarJefe=listener;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgCancel:
                dismiss();
                break;
        }
    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        LstEmpleado empleado=(LstEmpleado)selectedItem;
        interactor.actualizaSupervisor(empleado.getIdNumEmpleado(), new Subscriber<ServerResponse>() {
            @Override
            public void onSubscribe(Subscription s) {
            }
            @Override
            public void onNext(ServerResponse response) {
                if(response.isExito()) {
                    Log.d(TAG, "onNext: ");
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });
        listenerDialogEditarJefe.onJefeSelected(empleado);
        dismiss();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_search_jefe);
        etSearchJefe = (EditText) findViewById(R.id.etSearchJefe);
        imgAgregar = (ImageView) findViewById(R.id.imgAgregar);
        imgCancel = (ImageView) findViewById(R.id.ImgCancel);
        imgCancel.setOnClickListener(this);
        recyclerListaJefe = (RecyclerView) findViewById(R.id.recyclerListaJefe);
        pgBubble=(ProgressBubble) findViewById(R.id.pgBubble);
        RxTextView.textChanges(etSearchJefe).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }
    @Override
    public void accept(CharSequence query) throws Exception {
        if(query.length()>4){
                pgBubble.setVisibility(View.VISIBLE);
                pgBubble.startBubbleAnimation();
                recyclerListaJefe.setVisibility(View.GONE);
                interactor.obtenerListaEmpleado(query.toString(),this);
        }
    }
    @Override
    public void onSubscribe(Subscription s) {
        pgBubble.Dismiss();

    }

    @Override
    public void onNext(ListaEmpleadoReponse res) {
        pgBubble.Dismiss();
        recyclerListaJefe.setVisibility(View.VISIBLE);
        if(res.getLstEmpleados()!=null && (!res.getLstEmpleados().isEmpty())){
           AdapterPlantillaJefe adpt=new AdapterPlantillaJefe(getContext(),res.getLstEmpleados());
           AlphaInAnimationAdapter alphaAdpt=new AlphaInAnimationAdapter(adpt);
           alphaAdpt.setDuration(1000);
           adpt.setItemClickListener(this);
           recyclerListaJefe.setLayoutManager(new LinearLayoutManager(getContext()));
           recyclerListaJefe.setAdapter(alphaAdpt);
           adpt.setItemClickListener(this);

       }
    }

    @Override
    public void onError(Throwable t) {
        pgBubble.Dismiss();
        recyclerListaJefe.setVisibility(View.VISIBLE);
        Toast.makeText(context, R.string.Error_Conexion, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onComplete() {
        pgBubble.Dismiss();
    }
}