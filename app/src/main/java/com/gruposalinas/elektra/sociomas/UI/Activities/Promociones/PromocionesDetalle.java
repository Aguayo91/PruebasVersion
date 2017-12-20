package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones.AdapterContacto;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumContacto;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionContacto;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PromocionesDetalle extends BaseCoreCompactActivity implements AdapterView.OnItemClickListener {

    private List<Integer> coloresBlancos= Arrays.asList(-1118498,-1642762,-1642770,-14079613,-1644826,-2169114,-2763307);
    private ImageView imageWallpaper,imgDescuento;
    private TextView tvDescripcionPromo,tvLink,tvTelefono,tvTituloEmpresa;
    private ProgressBar progressImagen;
    private CollapsingToolbarLayout toolbarLayout;
    private LinearLayout layoutContenido;
    private ListView listViewContacto;
    private Toolbar toolbar;
    private AdapterContacto adapterContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones_detalle);
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
        if(getIntent().hasExtra(Constants.SELECTED_EMPRESA_PROMOCION)) {
            PromocionesResponse item = (PromocionesResponse) getIntent().getSerializableExtra(Constants.SELECTED_EMPRESA_PROMOCION);
            this.loadImagenAsync(item);
        }
        ViewCompat.setNestedScrollingEnabled(listViewContacto, true);
    }
    private void loadImagenAsync(PromocionesResponse item){
        this.toolbarLayout.setTitle(item.getINTENOMBRE());
        this.tvTituloEmpresa.setText(item.getINTENOMBRE());
        final ArrayList<String> listDescuentos= AdapterUtils.getDescuento(item.getPUBLDES());
        final ArrayList<PromocionContacto>listContactoDetalle=AdapterUtils.getDetalleContacto(item.getINTEDESC(),item.getPUBLCOND() );
        adapterContacto=new AdapterContacto(this,listContactoDetalle);
        listViewContacto.setAdapter(adapterContacto);
        listViewContacto.setOnItemClickListener(this);
        tvDescripcionPromo.setText(Html.fromHtml(item.getPUBLDES()));
        tvLink.setText(item.getPUBLLIGA());
        Target target=new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageWallpaper.setImageBitmap(bitmap);
                imageWallpaper.setAnimation(AnimationUtils.loadAnimation(PromocionesDetalle.this,R.anim.fade_in));
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int colorDominante=palette.getDominantColor(ContextCompat.getColor(PromocionesDetalle.this,R.color.colorPrimary));
                        int colorVibrante=palette.getDarkVibrantColor(ContextCompat.getColor(PromocionesDetalle.this,R.color.colorPrimary));
                        int colorFinal=!coloresBlancos.contains(colorDominante) ?colorDominante: colorVibrante;
                        toolbarLayout.setContentScrimColor(colorFinal);
                        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                        layoutContenido.setVisibility(View.VISIBLE);
                        layoutContenido.setAnimation(AnimationUtils.loadAnimation(PromocionesDetalle.this,R.anim.fade_in));
                        if(!listDescuentos.isEmpty() && (listDescuentos!=null)){
                            TextDrawable drawable = TextDrawable.builder().buildRound(listDescuentos.get(0),colorFinal);
                            imgDescuento.setImageDrawable(drawable);
                        }
                    }
                });
                progressImagen.setVisibility(View.GONE);
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                progressImagen.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        Picasso.with(this).load(getUrlImagen(item.getINTELLAVPR())).into(target);
        imageWallpaper.setTag(target);
    }
    public void navegarLink(View v){
        String url=tvLink.getText().toString();
        Utils.navegarWebView(url,this);
    }
    private String getUrlImagen(String idEmpresa){
        return String.format("https://portalsocio.gs/descuentos/Image.aspx?BS=%s",idEmpresa);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        PromocionContacto item=adapterContacto.getItem(position);
        if(item.getTipo()== EnumContacto.Telefono) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.getDetalle(), null));
            startActivity(intent);
        }
    }
}
