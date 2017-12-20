package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Detalle;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones.AdapterContacto;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Enums.EnumContacto;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoDetalleResponse;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionContacto;

import java.util.ArrayList;


public class PromocionDetalleActivity extends BaseCoreCompactActivity implements PromoDetallePresenterImpl.PromoDetalleView, AdapterView.OnItemClickListener {

    private ImageView imageWallpaper,imgDescuento;
    private TextView tvDescripcionPromo,tvLink,tvTelefono,tvTituloEmpresa;
    private ProgressBar progressImagen;
    private CollapsingToolbarLayout toolbarLayout;
    private LinearLayout layoutContenido;
    private ListView listViewContacto;
    private Toolbar toolbar;
    private AdapterContacto adapterContacto;
    private PromoDetallePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones_detalle);
        setPresenter();
    }

    @Override
    public void initView() {
        this.imageWallpaper=(ImageView)findViewById(R.id.imageWallpaper);
        this.imgDescuento=(ImageView)findViewById(R.id.imgDescuento);
        this.tvDescripcionPromo=(TextView)findViewById(R.id.tvDescripcionPromo);
        this.listViewContacto=(ListView)findViewById(R.id.listViewContacto);
        this.tvTelefono=(TextView)findViewById(R.id.tvTelefono);
        this.tvTituloEmpresa=(TextView)findViewById(R.id.tvTituloEmpresa);
        this.tvLink=(TextView)findViewById(R.id.tvLink);
        this.toolbar=(Toolbar)findViewById(R.id.toolbar);
        this.progressImagen=(ProgressBar)findViewById(R.id.progressImagen);
        this.layoutContenido=(LinearLayout) findViewById(R.id.layoutContenido);
        this.toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
    }

    @Override
    public void setListeners() {
        super.setListeners();
    }

    @Override
    public void setPresenter() {
        presenter=new PromoDetallePresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
        presenter.mostrarInfo();
    }

    @Override
    public void showProgressImagen() {
        progressImagen.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressImagen() {
        progressImagen.setVisibility(View.GONE);
    }

    public void navegarLink(View v){
        String url=tvLink.getText().toString();
        Utils.navegarWebView(url,this);
    }

    @Override
    public void onInfoLoaded(PromoDetalleResponse promo) {
        if(promo.getColorFinal()!=null) {
            toolbarLayout.setContentScrimColor(promo.getColorFinal());
            toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        }
        else{
            toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        }

        if(promo.getSelectedBitmap()!=null){
            imageWallpaper.setImageBitmap(promo.getSelectedBitmap());
        }

        layoutContenido.setVisibility(View.VISIBLE);
        ArrayList<String> listDescuentos= AdapterUtils.getDescuento(promo.getSelectedPromo().getDescuento());
        tvTituloEmpresa.setText(promo.getSelectedPromo().getNombreEmpresa());
        tvDescripcionPromo.setText(promo.getSelectedPromo().getDescuento());
        toolbarLayout.setTitle(promo.getSelectedPromo().getNombreEmpresa());
        ArrayList<PromocionContacto>listContactoDetalle=AdapterUtils.getDetalleContacto(promo.getSelectedPromo().getDescuento(),
                promo.getSelectedPromo().getCondiciones(),
                promo.getSelectedPromo().getTelefono());
        adapterContacto=new AdapterContacto(this,listContactoDetalle);
        listViewContacto.setAdapter(adapterContacto);
        listViewContacto.setOnItemClickListener(this);
        tvLink.setText(promo.getSelectedPromo().getUrlDescuento());
        layoutContenido.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
        if(!listDescuentos.isEmpty() && (listDescuentos!=null)){
            int color=promo.getColorFinal()!=null? promo.getColorFinal(): ContextCompat.getColor(this,R.color.colorPrimary);
            TextDrawable drawable = TextDrawable.builder().buildRound(listDescuentos.get(0),color);
            imgDescuento.setImageDrawable(drawable);
        }
    }
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        PromocionContacto item=adapterContacto.getItem(position);
        if(item.getTipo()== EnumContacto.Telefono) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.getDetalle(), null));
            startActivity(intent);
        }
    }

}
