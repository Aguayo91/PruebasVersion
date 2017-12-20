package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogPicture;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.FotoPerfilPresenter;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.FotoPerfilPresenterImpl;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by oemy9 on 13/11/2017.
 */

public class FragmentFotoPerfil extends FragmentSlideBase  implements View.OnClickListener, FotoPerfilPresenterImpl.FotoPerfilView, DialogPicture.onPictureOptionSelectedListener {
    public static final String TAG = "FragmentFotoPerfil";
    private View rootView;
    private ImageView imgCamara,imgAvatar;
    private DialogPicture dialogPicture;
    private FotoPerfilPresenter presenter;
    private Button btnOmitir;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView=inflater.inflate(R.layout.fragment_foto_perfil,container,false);
        setPresenter();
        return rootView;
    }

    @Override
    public void initView() {
        this.imgCamara=(ImageView)rootView.findViewById(R.id.imgCamara);
        this.imgAvatar=(ImageView)rootView.findViewById(R.id.imgAvatar);
        this.btnOmitir=(Button)rootView.findViewById(R.id.btnOmitir);
        this.dialogPicture=new DialogPicture(getContext());
        this.dialogPicture.setShowBtnDelete(true);
    }

    @Override
    public void setListeners() {
        this.imgCamara.setOnClickListener(this);
        this.imgAvatar.setOnClickListener(this);
        this.dialogPicture.setOnPictureSelectedListener(this);
        this.btnOmitir.setOnClickListener(this);
    }

    public static FragmentFotoPerfil getInstance(@Nullable  Bundle args) {
        FragmentFotoPerfil fragment = new FragmentFotoPerfil();
        if(args!=null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void setPresenter() {
        this.presenter=new FotoPerfilPresenterImpl();
        this.presenter.register(this);
    }

    @Override
    public Activity getActivityInstance() {
        return this.getActivity();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == imgCamara.getId()) {
            dialogPicture.show();
        }
        else if(i==btnOmitir.getId()){
            navegarFragment(FragmentUnidadNegocio.getInstance(null),
                    FragmentUnidadNegocio.TAG,true);
        }
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, Constants.RESULT_LOAD_IMAGE_CAMERA);
    }

    @Override
    public void onDeleteSelected() {
        ApplicationBase.getIntance().getManager().add(Constants.USUARIO_PERFIL_IMAGE,"");
        imgAvatar.setImageResource(R.drawable.ic_avatar_fondo_v2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                try {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (result != null) {
                        Uri resultUri = result.getUri();
                        Bitmap imageBitmap = Utils.getThumbnailBitmap(getActivity(), resultUri);
                        btnOmitir.setText(getString(R.string.siguiente));
                        presenter.guardarImagenPerfil(imageBitmap);
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == Constants.RESULT_LOAD_IMAGE) {
                try {
                    CropImage.activity(data.getData()).
                            setGuidelines(CropImageView.Guidelines.ON)
                            .setCropShape(CropImageView.CropShape.OVAL)
                            .setScaleType(CropImageView.ScaleType.CENTER_CROP)
                            .setAutoZoomEnabled(true)
                            .setFixAspectRatio(true).start(getContext(), this);
                    btnOmitir.setText(getString(R.string.siguiente));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == Constants.RESULT_LOAD_IMAGE_CAMERA) {
             /*try {
                 Bundle extras = data.getExtras();
                 Bitmap imageBitmap = (Bitmap) extras.get("data");
                 btnOmitir.setText(getString(R.string.siguiente));
                 presenter.guardarImagenPerfil(imageBitmap);
             }catch (Exception e){
                 e.printStackTrace();
             }*/
                Uri selectedImage = imageUri;
                getActivityInstance().getContentResolver().notifyChange(selectedImage, null);
                ContentResolver cr = getActivityInstance().getContentResolver();
                Bitmap bitmap = null;
                Bitmap currentBitmap = null;
                try {
                    bitmap = android.provider.MediaStore.Images.Media
                            .getBitmap(cr, selectedImage);
                    currentBitmap = Utils.getThumbnailBitmap(getActivityInstance(), selectedImage);
                    ExifInterface exif = new ExifInterface(selectedImage.getPath());
                    int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int rotationInDegrees = Utils.exifToDegrees(rotation);
                    Matrix matrix = new Matrix();
                    if (rotation != 0f) {
                        matrix.preRotate(rotationInDegrees);
                    }

                    Bitmap adjustedBitmap = Bitmap.createBitmap(currentBitmap, 0, 0, currentBitmap.getWidth(), currentBitmap.getHeight(), matrix, true);
//                Utils.saveImagenPerfilWallpaper(this, adjustedBitmap, changeWallpaper);
//                if (!changeWallpaper) {
                    btnOmitir.setText(getString(R.string.siguiente));
                    presenter.guardarImagenPerfil(adjustedBitmap);
//                } else {
//                    imageWallpaper.setImageBitmap(adjustedBitmap);
//                }
//                    imagenPerfil.setImageBitmap(bitmap);
//                    Toast.makeText(this, selectedImage.toString(),
//                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getActivityInstance(), "Failed to load", Toast.LENGTH_SHORT)
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
    public void mostrarFotoPerfil(Bitmap bmp) {
        if(bmp!=null){
            imgAvatar.setImageBitmap(bmp);
        }
    }
    @Override
    public void presentEvent(ViewEvent event) {

    }

}