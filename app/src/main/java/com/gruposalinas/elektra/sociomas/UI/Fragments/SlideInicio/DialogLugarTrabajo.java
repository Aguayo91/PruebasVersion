package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo.AdapterLugarTFlip;
import com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo.AdapterLugarTSlider;
import com.sociomas.core.UI.Controls.Progress.ProgressBubble;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.LugarTrabajoPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.LugarTrabajoPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

;import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by oemy9 on 31/08/2017.
 */

public  class DialogLugarTrabajo extends Dialog implements RecyclerItemClickListener,View.OnClickListener, LugarTrabajoPresenterImpl.LugarTrabajoView, AdapterLugarTFlip.NotifyListenerLugaresTrabajo {
    private static final String TAG = "DialogLugarTrabajo";
    private Context context;
    private EditText txtDestino;
    private RecyclerView recyclerUbicaciones,recyclerUbicacionesFlip;
    private Toolbar toolbar;
    private LugarTrabajoPresenter presenter;
    private ArrayList<LugarTrabajo>lugaresSelected=new ArrayList<>();
    private AdapterLugarTFlip adpt;
    private Button btnAgregar;
    private ImageView imgCancel;
    private ProgressBubble pgBubble;
    public interface  DialogoLugarTrabajoListener {
        void onLugaresTrabajoSelected(ArrayList<LugarTrabajo>listLugaresTrabajo);
    }
    private  DialogoLugarTrabajoListener listener;
    public DialogLugarTrabajo(Context context){
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.context=context;
    }

    public void setPresenter(LugarTrabajoPresenter presenter) {
        this.presenter = presenter;
    }

    public void setListener(DialogoLugarTrabajoListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_search_lugar_trabajo);
        //findViews editText
        txtDestino = (EditText)findViewById(R.id.txtDestino);
        btnAgregar=(Button)findViewById(R.id.btnAgregar);
        imgCancel=(ImageView)findViewById(R.id.ImgCancel);
        btnAgregar.setOnClickListener(this);
        imgCancel.setOnClickListener(this);
        //recycler view de autrocomplete
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        initRecycler();
        initQuery();
        setDialogParameters();
    }

    private void initRecycler(){
        pgBubble=(ProgressBubble)findViewById(R.id.pgBubble);

        recyclerUbicaciones = (RecyclerView)findViewById(R.id.recyclerUbicaciones);
        recyclerUbicacionesFlip=(RecyclerView)findViewById(R.id.recyclerUbicacionesFlip);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.context);
        recyclerUbicaciones.setLayoutManager(layoutManager);
        SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(5f));
        recyclerUbicacionesFlip.setItemAnimator(animator);
        adpt=new AdapterLugarTFlip(this.context,new ArrayList<LugarTrabajo>());
        adpt.setListenerLugaresTrabajo(this);
        recyclerUbicacionesFlip.setLayoutManager(new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
        recyclerUbicacionesFlip.setAdapter(adpt);

    }

    private void initQuery(){
        RxTextView.textChanges(txtDestino).debounce(300, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence query) throws Exception {
                if(query.length()>=4) {
                    if (presenter != null) {
                        pgBubble.setVisibility(View.VISIBLE);
                        pgBubble.startBubbleAnimation();
                        presenter.buscarLugarTrabajo(query.toString());
                    }
                }
            }
        });
    }

    private void setDialogParameters(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.softInputMode=WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        lp.gravity=Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimation;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onQueryEmpty() {
    }

    @Override
    public void clearLugaresTrabajo() {
    }

    @Override
    public void setListLugarTrabajo(ArrayList<LugarTrabajo> listLugarTrabajo) {
        if(listLugarTrabajo!=null && (!listLugarTrabajo.isEmpty())){
            AdapterLugarTSlider adpt=new AdapterLugarTSlider(this.context,listLugarTrabajo);
            recyclerUbicaciones.setLayoutManager(new LinearLayoutManager(this.context));
            AlphaInAnimationAdapter alphaInAnimationAdapter=new AlphaInAnimationAdapter(adpt);
            alphaInAnimationAdapter.setDuration(300);
            adpt.setItemClickListener(this);
            recyclerUbicaciones.setAdapter(alphaInAnimationAdapter);
        }
        pgBubble.Dismiss();
    }



    @Override
    public void showImageBitMap(Bitmap bmp) {
    }

    @Override
    public void navegarFragment() {

    }


    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        LugarTrabajo t=(LugarTrabajo)selectedItem;
        adpt.addOrRemove(t,position+1);
        Activity act=Utils.scanForActivity(this.context);
        recyclerUbicacionesFlip.setVisibility(!adpt.getListLugarTrabajo().isEmpty()? View.VISIBLE: View.GONE);
        if(act!=null) {
            Utils.ocultarTeclado(act);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i==btnAgregar.getId()){
            if(listener!=null){
                listener.onLugaresTrabajoSelected(adpt.getListLugarTrabajo());
                dismiss();
            }
        }
        else if(i==imgCancel.getId()){
            dismiss();
        }

    }


    public void changeHintText(String text){
        txtDestino.setHint(text);
    }

    @Override
    public void initView() {
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void setPresenter() {
    }

    @Override
    public void presentEvent(ViewEvent event) {
    }

    @Override
    public Activity getActivityInstance() {
        return null;
    }

    @Override
    public void onLimiteSuperado() {
       new AlertDialog.Builder(this.context).setTitle("Limite superado")
               .setMessage(Html.fromHtml("Si cuentas con más de 20 lugares de trabajo para registrar, selecciona la opción: <br/> " +
                       "<b>No tengo un lugar fijo </b>"))
               .setPositiveButton(android.R.string.ok,null)
               .show();
    }

    @Override
    public void onClear() {
        recyclerUbicacionesFlip.setVisibility(!adpt.getListLugarTrabajo().isEmpty()? View.VISIBLE: View.GONE);
    }
}
