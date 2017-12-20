package com.gruposalinas.elektra.sociomas.UI.Activities.Inicio;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Login.LoginActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.SplashScreen.SplashScreenActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.SliderInicio.AdapterOpcionesHome;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogPicture;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Exif;
import com.sociomas.core.UI.Controls.Progress.ProgressBubble;
import com.gruposalinas.elektra.sociomas.Utils.SupportUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Controls.Model.DrawerItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by GiioToledo on 16/11/17.
 */

public class InicioActivityV2 extends BaseAppCompactActivity implements BaseView, View.OnClickListener, DialogPicture.onPictureOptionSelectedListener {
    private InicioPresenter presenter;
    private CoordinatorLayout content;
    private CircleImageView imagenPerfil;
    private RelativeLayout perimetroImagen;
    private TextView tvNombre;
    private TextView tvNoEmpleado;
    private TextView tvVersion;
    private ProgressBubble progressBubble;
    private SessionManager manager;
    private RelativeLayout bottomSheet;
    private boolean changeWallpaper = false;
    private ImageView imageWallpaper, imgConfiguracion;
    private RelativeLayout imgNotificacion;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ImageView fabCamara;
    private ImageView imageEdit;
    private RecyclerView rvOpciones;
    private DialogPicture dialogPicture;
    private TextView tvNumeroNotificaciones;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_v2_fix);
        setPresenter();
    }


    @Override
    public void initView() {
        this.tvNombre = (TextView) findViewById(R.id.tvName);
        this.tvNoEmpleado = (TextView) findViewById(R.id.tvNoEmpleado);
        this.tvVersion = (TextView) findViewById(R.id.tvVersion);
        this.tvVersion.setText(getString(R.string.version_footer));
        this.imgConfiguracion = (ImageView) findViewById(R.id.imgConfiguracion);
        this.imgNotificacion = (RelativeLayout) findViewById(R.id.imgNotificacion);
        this.tvNumeroNotificaciones = (TextView) findViewById(R.id.tvNumeroNotificaciones);
        this.imagenPerfil = (CircleImageView) findViewById(R.id.imgAvatar);
        this.manager = new SessionManager(this);
        this.content = (CoordinatorLayout) findViewById(R.id.content);
        this.imageWallpaper = (ImageView) findViewById(R.id.imageWallpaper);
        this.bottomSheet = (RelativeLayout) findViewById(R.id.bottom_sheet);
        this.tvNoEmpleado.setText(getString(R.string.idEmpleadoBienvenida, manager.getString(Constants.SP_ID)));
        this.tvNombre.setText(Utils.toTitleCase(manager.getString(Constants.SP_NAME)));
        this.toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        this.appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        this.toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.fabCamara = (ImageView) findViewById(R.id.fabCamara);
        this.imageEdit = (ImageView) findViewById(R.id.imageEdit);
        this.rvOpciones = (RecyclerView) findViewById(R.id.rvOpciones);
        this.rvOpciones.setNestedScrollingEnabled(false);

    }

    public void fillListItem() {
        List<DrawerItem> list = new ArrayList<>();
        list.add(new DrawerItem(getString(com.sociomas.core.R.string.mis_asistencias), com.sociomas.core.R.drawable.ic_asistencia_outline,getString(com.sociomas.core.R.string.action_asistencias),true));
        list.add(new DrawerItem(getString(com.sociomas.core.R.string.mi_gafete), com.sociomas.core.R.drawable.ic_gafete,getString(com.sociomas.core.R.string.action_gafete),true));
        list.add(new DrawerItem(getString(com.sociomas.core.R.string.mi_lugar_trabajo), com.sociomas.core.R.drawable.ic_job_place,getString(com.sociomas.core.R.string.action_lugares),true));
        list.add(new DrawerItem(getString(com.sociomas.core.R.string.promociones_descuentos), com.sociomas.core.R.drawable.ic_promociones_outline, getString(com.sociomas.core.R.string.action_promociones),true));
        list.add(new DrawerItem(getString(com.sociomas.core.R.string.liberacion_nomina), com.sociomas.core.R.drawable.ic_nomina_outline,getString(com.sociomas.core.R.string.action_nomina),false));
        list.add(new DrawerItem(getString(com.sociomas.core.R.string.aventones), com.sociomas.core.R.drawable.ic_aventon_outline, getString(com.sociomas.core.R.string.action_aventon),false));

        Observable.fromIterable(list).filter(new Predicate<DrawerItem>() {
            @Override
            public boolean test(DrawerItem drawerItem) throws Exception {
                return drawerItem.isVisible();
            }
        }).toList().subscribe(new Consumer<List<DrawerItem>>() {
            @Override
            public void accept(List<DrawerItem> drawerItems) throws Exception {
                rvOpciones.setLayoutManager(new GridLayoutManager(InicioActivityV2.this, 2));
                AdapterOpcionesHome adapter = new AdapterOpcionesHome(InicioActivityV2.this, drawerItems, InicioActivityV2.this);
                rvOpciones.setAdapter(adapter);
            }
        });

    }

    @Override
    public void setListeners() {
        if(empleadoIsIvan()){
            eliminarShared();
        }
        else {
            fabCamara.setOnClickListener(this);
            imageEdit.setOnClickListener(this);
            rvOpciones.setNestedScrollingEnabled(true);
            dialogPicture = new DialogPicture(this);
            dialogPicture.setShowBtnDelete(true);
            dialogPicture.setOnPictureSelectedListener(this);
            imgConfiguracion.setOnClickListener(this);
            imgNotificacion.setOnClickListener(this);
            SupportUtils.ifHuaweiAlert(this);
            fillListItem();
        }
    }

    @Override
    public void setPresenter() {
        presenter = new InicioPresenterImpl();
        presenter.register(this);
        presenter.initTracking();
        presenter.checkIfUsuarioPilotoAventon();
        presenter.obtenerRangosMonitoreoEmpleado();
        presenter.obtenerConfiguracionAsync();
        presenter.sincronizarTokenFireBase();
        presenter.consultarNotificaciones();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.tvNumeroNotificaciones: {
                        int contador = (int) event.getModel().get(ViewEvent.ENTRY);
                        com.sociomas.core.Utils.Utils.setNotificacionEstatus(this,contador);
                        this.tvNumeroNotificaciones.setText(String.valueOf(contador));
                        if (contador > 0) {
                            this.tvNumeroNotificaciones.setVisibility(View.VISIBLE);
                        } else {
                            this.tvNumeroNotificaciones.setVisibility(View.GONE);
                        }

                    }
                    break;
                }
            }
            break;

        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar.setNavigationIcon(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onResume() {
        Bitmap selectedImagenPerfil = Utils.getImagenPerfilWallpaper(this, false);
        Bitmap selectedImagenWallpaper = Utils.getImagenPerfilWallpaper(this, true);
        if (selectedImagenPerfil != null && (!selectedImagenPerfil.equals(""))) {
            imagenPerfil.setImageBitmap(selectedImagenPerfil);
        }
        if (selectedImagenWallpaper != null && (!selectedImagenWallpaper.equals(""))) {
            imageWallpaper.setImageBitmap(selectedImagenWallpaper);
        }
        super.onResume();
        SessionManager manager = new SessionManager(this);
        if (this.tvNumeroNotificaciones != null) {
            int contador=manager.getInt(Constants.ContadorNotificaciones);
            if(contador>0) {
                if (!this.tvNumeroNotificaciones.getText().toString().contentEquals(String.valueOf(contador))) {
                    this.tvNumeroNotificaciones.setText(String.valueOf(contador));
                }
            }
            else{
                this.tvNumeroNotificaciones.setVisibility(View.GONE);
            }
        }
    }


    public void onCancel(View v) {
        manager.add(changeWallpaper ? Constants.WALLPAPER_PERFIL_IMAGE : Constants.USUARIO_PERFIL_IMAGE, "");
        if (!changeWallpaper) {
            imagenPerfil.setImageResource(R.drawable.ic_avatar_fondo);
            reUpdateImagenPerfil();
        } else {
            imageWallpaper.setImageResource(R.drawable.background_home);
            //cambiarLayoutColor(false,null);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                try {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    Bitmap currentBitmap = Utils.getThumbnailBitmap(this, resultUri);
                    Utils.saveImagenPerfilWallpaper(this, currentBitmap, changeWallpaper);
                    reUpdateImagenPerfil();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == Constants.RESULT_LOAD_IMAGE) {
                try {
                    Bitmap currentBitmap = Utils.getThumbnailBitmap(this, data.getData());
                    Utils.saveImagenPerfilWallpaper(this, currentBitmap, changeWallpaper);
                    if (!changeWallpaper) {

                        CropImage.activity(data.getData()).
                                setGuidelines(CropImageView.Guidelines.ON)
                                .setCropShape(CropImageView.CropShape.OVAL)
                                .setScaleType(CropImageView.ScaleType.CENTER_CROP)
                                .setAutoZoomEnabled(true)
                                .setFixAspectRatio(true).start(this);
                    } else {
                        Picasso.with(this).load(data.getData()).fit().into(imageWallpaper);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == Constants.RESULT_LOAD_IMAGE_CAMERA) {

                Uri selectedImage = imageUri;
                getActivityInstance().getContentResolver().notifyChange(selectedImage, null);
                ContentResolver cr = getContentResolver();
                Bitmap bitmap = null;
                Bitmap currentBitmap = null;
                try {
                    bitmap = android.provider.MediaStore.Images.Media
                            .getBitmap(cr, selectedImage);
                    currentBitmap = Utils.getThumbnailBitmap(this, selectedImage);
                    ExifInterface exif = new ExifInterface(selectedImage.getPath());
                    int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int rotationInDegrees = Utils.exifToDegrees(rotation);
                    Matrix matrix = new Matrix();
                    if (rotation != 0f) {matrix.preRotate(rotationInDegrees);}

                    Bitmap adjustedBitmap = Bitmap.createBitmap(currentBitmap, 0, 0, currentBitmap.getWidth(), currentBitmap.getHeight(), matrix, true);
                    Utils.saveImagenPerfilWallpaper(this, adjustedBitmap, changeWallpaper);
                    if (!changeWallpaper) {
                        imagenPerfil.setImageBitmap(adjustedBitmap);
                        reUpdateImagenPerfil();
                    } else {
                        imageWallpaper.setImageBitmap(adjustedBitmap);
                    }
//                    imagenPerfil.setImageBitmap(bitmap);
//                    Toast.makeText(this, selectedImage.toString(),
//                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                            .show();
                    Log.e("Camera", e.toString());
                } finally {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }

                    if (currentBitmap != null) {
                        currentBitmap.recycle();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        changeWallpaper = view.getId() == R.id.imageEdit;
        //  mostrarBottomSheet(wallpaper);
        int id = view.getId();
        switch (id) {
            case R.id.rlCuadro:
                DrawerItem item = (DrawerItem) view.getTag();
                if (item.getActionName() != null) {
                    try {
                        Intent intent = new Intent(item.getActionName());
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                break;

            case R.id.fabCamara:

                dialogPicture.show();
                break;
            case R.id.imageEdit:
                dialogPicture.show();
                break;
            case R.id.imgConfiguracion:
                configuracion();
                break;
            case R.id.imgNotificacion:
                notificiaciones();
                break;
        }

    }

    public void configuracion() {
        startActivity(new Intent(getString(com.sociomas.core.R.string.action_cofiguraciones)));
    }

    public void notificiaciones() {
        startActivity(new Intent(getString(com.sociomas.core.R.string.action_buzon)));
    }

    private void cambiarLayoutColor(boolean hasWallpaper, Bitmap wallpaper) {
        tvNombre.setTextColor(hasWallpaper ? Color.WHITE : ContextCompat.getColor(this, R.color.gris_info));
        tvNoEmpleado.setTextColor(hasWallpaper ? Color.WHITE : ContextCompat.getColor(this, R.color.gris_info));
        imageEdit.setImageResource(hasWallpaper ? R.drawable.ic_lapiz_edit : R.drawable.ic_lapiz_edit);
        if (wallpaper != null) {

            Palette.from(wallpaper).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    toolbarLayout.setContentScrimColor(palette.getDominantColor(ContextCompat.getColor(InicioActivityV2.this, R.color.colorPrimary)));
                    toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                }
            });

        } else {
            toolbarLayout.setContentScrimColor(ContextCompat.getColor(InicioActivityV2.this, R.color.colorPrimary));
            toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        }
    }
    private boolean empleadoIsIvan(){
        return  ApplicationBase.getIntance().getManager().getString(Constants.SP_ID).equalsIgnoreCase(Constants.IVAN_NUMERO);
    }
    private void eliminarShared (){
        SharedPreferences pref = this.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(InicioActivityV2.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onGallerySelected() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, Constants.RESULT_LOAD_IMAGE);
    }

    @Override
    public void onCameraSelected() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, Constants.RESULT_LOAD_IMAGE_CAMERA);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, Constants.RESULT_LOAD_IMAGE_CAMERA);
    }

    @Override
    public void onDeleteSelected() {
        manager.add(changeWallpaper?Constants.WALLPAPER_PERFIL_IMAGE: Constants.USUARIO_PERFIL_IMAGE,"");
        if(!changeWallpaper) {
            imagenPerfil.setImageResource(R.drawable.ic_avatar_fondo_v2);
            reUpdateImagenPerfil();
        }
        else{
            imageWallpaper.setImageResource(R.drawable.bg_home_gs);
        }
    }
}