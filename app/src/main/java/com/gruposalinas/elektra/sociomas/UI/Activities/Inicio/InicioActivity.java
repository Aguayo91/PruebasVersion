package com.gruposalinas.elektra.sociomas.UI.Activities.Inicio;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.gruposalinas.elektra.sociomas.BuildConfig;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;

import com.gruposalinas.elektra.sociomas.Utils.SupportUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Model.Request.Registro.RegistroRequest;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class InicioActivity extends BaseAppCompactActivity implements BaseView, View.OnClickListener{
    private InicioPresenter presenter;
    private CircleImageView imagenPerfil;
    private RelativeLayout perimetroImagen;
    private TextView tvNombre;
    private TextView tvNoEmpleado;
    private SessionManager manager;
    private RelativeLayout bottomSheet;
    private BottomSheetBehavior bsb;
    private boolean changeWallpaper=false;
    private ImageView imageWallpaper;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private FloatingActionButton fabCamara;
    private ImageButton imageEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        setPresenter();
    }


    @Override
    public void initView() {
        this.tvNombre=(TextView)findViewById(R.id.tvName);
        this.tvNoEmpleado=(TextView)findViewById(R.id.tvNoEmpleado);
        this.imagenPerfil=(CircleImageView)findViewById(R.id.imgAvatar);
        this.manager=new SessionManager(this);
        this.imageWallpaper=(ImageView)findViewById(R.id.imageWallpaper);
        this.bottomSheet = (RelativeLayout)findViewById(R.id.bottom_sheet);
        this.bsb = BottomSheetBehavior.from(bottomSheet);
        this.bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
        this.tvNoEmpleado.setText(manager.getString(Constants.SP_ID));
        tvNombre.setText(Utils.toTitleCase(manager.getString(Constants.SP_NAME)));
        this.toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        this.appBarLayout=(AppBarLayout)findViewById(R.id.appBarLayout);
        this.toolbarLayout.setTitle(Utils.toTitleCase(manager.getString(Constants.SP_NAME)));
        this.toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        this.toolbar=(Toolbar)findViewById(R.id.toolbar);
        this.fabCamara=(FloatingActionButton)findViewById(R.id.fabCamara);
        this.imageEdit=(ImageButton) findViewById(R.id.imageEdit);
    }

    @Override
    public void setListeners() {
        fabCamara.setOnClickListener(this);
        imageEdit.setOnClickListener(this);
        SupportUtils.ifHuaweiAlert(this);

    }

    @Override
    public void setPresenter() {
        presenter=new InicioPresenterImpl();
        presenter.register(this);
        presenter.initTracking();
        presenter.checkIfUsuarioPilotoAventon();
        presenter.obtenerRangosMonitoreoEmpleado();
        presenter.obtenerConfiguracionAsync();
        presenter.sincronizarTokenFireBase();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar.setNavigationIcon(null);

    }

    @Override
    protected void onResume() {
        Bitmap selectedImagenPerfil= Utils.getImagenPerfilWallpaper(this,false);
        Bitmap selectedImagenWallpaper=Utils.getImagenPerfilWallpaper(this,true);
        if(selectedImagenPerfil!=null && (!selectedImagenPerfil.equals(""))){
            imagenPerfil.setImageBitmap(selectedImagenPerfil);
        }
        if(selectedImagenWallpaper!=null && (!selectedImagenWallpaper.equals(""))){
            imageWallpaper.setImageBitmap(selectedImagenWallpaper);
            cambiarLayoutColor(true,selectedImagenWallpaper);
        }


        super.onResume();
    }


    public void onCancel(View v){
        manager.add(changeWallpaper?Constants.WALLPAPER_PERFIL_IMAGE: Constants.USUARIO_PERFIL_IMAGE,"");
        if(!changeWallpaper) {
           imagenPerfil.setImageResource(R.drawable.ic_avatar_fondo);
           reUpdateImagenPerfil();
        }
        else{
            imageWallpaper.setImageResource(R.drawable.background_home);
            cambiarLayoutColor(false,null);
        }
        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void onCamara(View v){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Constants.RESULT_LOAD_IMAGE_CAMERA);

    }
    public void onGaleria(View v){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         startActivityForResult(i, Constants.RESULT_LOAD_IMAGE);
    }

    @Override
    public void onBackPressed() {
        if(bsb.getState()==BottomSheetBehavior.STATE_EXPANDED){
            bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        else {
            super.onBackPressed();
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                try {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    Bitmap currentBitmap = Utils.getThumbnailBitmap(this,resultUri);
                    Utils.saveImagenPerfilWallpaper(this,currentBitmap,changeWallpaper);
                    reUpdateImagenPerfil();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if(requestCode==Constants.RESULT_LOAD_IMAGE){
                try {

                    Bitmap currentBitmap=Utils.getThumbnailBitmap(this, data.getData());
                    Utils.saveImagenPerfilWallpaper(this,currentBitmap,changeWallpaper);
                    if(!changeWallpaper)
                    {

                        CropImage.activity(data.getData()).
                                setGuidelines(CropImageView.Guidelines.ON)
                                .setCropShape(CropImageView.CropShape.OVAL)
                                .setScaleType(CropImageView.ScaleType.CENTER_CROP)
                                .setAutoZoomEnabled(true)
                                .setFixAspectRatio(true).start(this);
                    }
                    else{
                            Picasso.with(this).load(data.getData()).into(imageWallpaper);
                            cambiarLayoutColor(true,currentBitmap);
                    }

                    bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode==Constants.RESULT_LOAD_IMAGE_CAMERA){
                try {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                       Utils.saveImagenPerfilWallpaper(this, imageBitmap,changeWallpaper);
                       if(!changeWallpaper) {
                            imagenPerfil.setImageBitmap(imageBitmap);
                            reUpdateImagenPerfil();
                        }
                        else{
                            imageWallpaper.setImageBitmap(imageBitmap);
                            cambiarLayoutColor(true,imageBitmap);
                        }
                        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
                } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    @Override
    public void onClick(View view) {
        boolean wallpaper=view.getId()==R.id.imageEdit;
        mostrarBottomSheet(wallpaper);
    }

    private void mostrarBottomSheet(boolean isWallpaper){
        bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
        changeWallpaper=isWallpaper;
    }
    private void cambiarLayoutColor(boolean hasWallpaper,Bitmap wallpaper){
        tvNombre.setTextColor(hasWallpaper?Color.WHITE: ContextCompat.getColor(this,R.color.gris_info));
        tvNoEmpleado.setTextColor(hasWallpaper?Color.WHITE: ContextCompat.getColor(this,R.color.gris_info));
        imageEdit.setImageResource(hasWallpaper?R.mipmap.ic_edit_white: R.mipmap.ic_edit);
        if(wallpaper!=null){

            Palette.from(wallpaper).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    toolbarLayout.setContentScrimColor(palette.getDominantColor(ContextCompat.getColor(InicioActivity.this,R.color.colorPrimary)));
                    toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                }
            });

        }
        else{
            toolbarLayout.setContentScrimColor(ContextCompat.getColor(InicioActivity.this,R.color.colorPrimary));
            toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        }
    }
}

