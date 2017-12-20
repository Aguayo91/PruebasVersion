package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.CrearGafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.UI.Controls.Vision.BlinkTracker;
import com.gruposalinas.elektra.sociomas.UI.Controls.Vision.CallBackBlink;
import com.gruposalinas.elektra.sociomas.UI.Controls.Vision.CameraSourcePreview;
import com.gruposalinas.elektra.sociomas.UI.Controls.Vision.ViewPortHole;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.FragmentPreliminarGafete;
import com.gruposalinas.elektra.sociomas.Utils.Exif;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oemy9 on 21/08/2017.
 */

public class FragmentSurfaceGafete extends Fragment implements CallBackBlink, View.OnClickListener, Consumer<Long>, CropImageView.OnCropImageCompleteListener {

    public static final String TAG = "FRAGMENT_GAFETE";
    private boolean isShowing = false;
    private boolean fotoTomada = false;
    private boolean ojosActualmenteDetectados = false;
    private CameraSourcePreview preview;
    private Button btnCapturar;
    private ImageView imgCapturar;
    private SessionManager manager;
    private ImageView imageMarco;
    private ViewPortHole viewPorteHole;
    private BlinkTracker tracker;
    private CameraSource cameraSource;
    private FrameLayout frameSurface;
    private CropImageView cropImageView;
    private Button btnContinuar;
    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public boolean iniciarCamara = false;


    public void setIniciarCamara(boolean iniciarCamara) {
        this.iniciarCamara = iniciarCamara;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_surface_gafete, container, false);
        btnCapturar = (Button) rootView.findViewById(R.id.btnCapturar);
        btnContinuar=(Button)rootView.findViewById(R.id.btnContinuar);
        imgCapturar = (ImageView) rootView.findViewById(R.id.imgCapturar);
        preview = (CameraSourcePreview) rootView.findViewById(R.id.preview);
        imageMarco = (ImageView) rootView.findViewById(R.id.imageMarco);
        viewPorteHole = (ViewPortHole) rootView.findViewById(R.id.viewPorteHole);
        frameSurface=(FrameLayout)rootView.findViewById(R.id.frameSurface);
        cropImageView=(CropImageView)rootView.findViewById(R.id.cropImageView);
        if (iniciarCamara) {
            iniciarCamaraView();
        }
        Observable.timer(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
        return rootView;
    }


    public void iniciarCamaraView() {
        FaceDetector detector = new FaceDetector.Builder(getContext())
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();
        imageMarco.setImageResource(R.drawable.marco_credencial);
        fotoTomada = false;
        ojosActualmenteDetectados = false;
        tracker = new BlinkTracker();
        tracker.setCallback(this);
        detector.setProcessor(new LargestFaceFocusingProcessor(detector, tracker));
        this.cameraSource = new CameraSource.Builder(getContext(), detector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f).build();
        try {
            preview.start(cameraSource);
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.no_fue_posible_cargar_camara,
                    Toast.LENGTH_SHORT).show();
        }
        btnCapturar.setOnClickListener(this);
        imgCapturar.setOnClickListener(this);
        btnContinuar.setOnClickListener(this);
        manager = new SessionManager(getContext());
    }

    public static FragmentSurfaceGafete newInstance(int sectionNumber) {
        FragmentSurfaceGafete fragment = new FragmentSurfaceGafete();
        return fragment;
    }

    public boolean isIniciarCamara() {
        return iniciarCamara;
    }

    public void hidePreview() {
        if (preview != null) {
            preview.setVisibility(View.GONE);
            viewPorteHole.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        if (preview != null) {
            preview.stop();
            preview.release();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnCapturar.getId() || view.getId()==imgCapturar.getId()) {
            tomarFotoAsyncVision();
        }
        else if(view.getId()==btnContinuar.getId()){
            cropImageView.getCroppedImageAsync();
        }
    }


    private void tomarFotoAsyncVision() {
        if (preview != null && (preview.getmCameraSource() != null)) {
            try {
                final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.sonido_camara);
                mp.start();
                preview.getmCameraSource().takePicture(null, new CameraSource.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data) {
                        int screenWidth = getResources().getDisplayMetrics().widthPixels;
                        int screenHeight = getResources().getDisplayMetrics().heightPixels;
                        Bitmap bm = BitmapFactory.decodeByteArray(data, 0, (data != null) ? data.length : 0);
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            int orientation = Exif.getOrientation(data);
//                           Bitmap   bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            switch (orientation) {
                                case 90:
                                    bm = com.sociomas.core.Utils.Utils.rotateImage(bm, 90);
                                    break;
                                case 180:
                                    bm = com.sociomas.core.Utils.Utils.rotateImage(bm, 180);
                                    break;
                                case 270:
                                    bm = com.sociomas.core.Utils.Utils.rotateImage(bm, 270);
                                    break;
                                case 0:
                                    // if orientation is zero we don't need to rotate this
                                default:
                                    break;
                            }
                            Bitmap scaled = Bitmap
                                    .createScaledBitmap(
                                            bm,
                                            viewPorteHole.getWidth(),
                                            viewPorteHole.getHeight() - viewPorteHole.viewportMargin,
                                            true);
                            Matrix mtx = new Matrix();
                            mtx.postScale(-1, 1, viewPorteHole.rect.centerX(), viewPorteHole.rect.centerY());


                            bm = Bitmap.createBitmap(scaled,
                                    (int) viewPorteHole.rect.left,
                                    (int) viewPorteHole.rect.top,
                                    (int) viewPorteHole.rect.right,
                                    (int) viewPorteHole.rect.bottom,
                                    mtx,
                                    false);

                            cropImageView.setVisibility(View.VISIBLE);
                            frameSurface.setVisibility(View.GONE);
                            btnContinuar.setVisibility(View.VISIBLE);
                            cropImageView.setImageBitmap(bm);
                            cropImageView.setCropShape(CropImageView.CropShape.OVAL);
                            cropImageView.setShowProgressBar(true);
                            cropImageView.setGuidelines(CropImageView.Guidelines.ON);
                            cropImageView.setScaleType(CropImageView.ScaleType.FIT_CENTER);
                            cropImageView.setAutoZoomEnabled(true);
                            cropImageView.setFixedAspectRatio(true);
                            preview.stop();
                            preview.release();
                            mp.stop();
                        }
                    }
                });
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        if (preview != null) {
            preview.stop();
            preview.release();
        }
        super.onPause();
    }

    @Override
    public void  onStart() {
        super.onStart();
        cropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    public void onStop() {
        if (preview != null) {
            preview.stop();
            preview.release();
        }
        cropImageView.setOnCropImageCompleteListener(null);
        super.onStop();
    }

    public void onSlideoff() {
        if (preview != null) {
            Log.v("Myapp", "no es nulo");
            preview.stop();
            preview.release();
        }
    }






    @Override
    public void onBlinkCreated(boolean parpadeo) {
        if (parpadeo && fotoTomada == false && isShowing == true) {
            imageMarco.setImageResource(R.drawable.marco_yellow);
            tomarFotoAsyncVision();
            SnackBarBuilder snackBarBuilder = new SnackBarBuilder(getActivity());
            snackBarBuilder.showSuccess(getString(R.string.parpadeado));
            tracker.removeCallBacks();
            fotoTomada = true;
        }
    }

    @Override
    public void onEyesDetected(boolean ojosDetectados) {
        if (ojosDetectados && ojosActualmenteDetectados == false && isShowing == true) {
            imageMarco.setImageResource(R.drawable.marco_green);
            ojosActualmenteDetectados = true;
            SnackBarBuilder snackBarBuilder = new SnackBarBuilder(getActivity());
            snackBarBuilder.showSuccess(getString(R.string.ojos_detectados));
        }
    }

    @Override
    public void accept(Long aLong) throws Exception {
        iniciarCamaraView();
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
            if(result!=null){
                if(result.getBitmap()!=null) {
                    String base64 = Utils.encodeToBase64(result.getBitmap(), Bitmap.CompressFormat.JPEG, 90);
                    if (!base64.isEmpty()) {
                        manager.add(Constants.USUARIO_FOTO_FRONTAL, base64);
                        hidePreview();
                        ((CrearGafeteActivity) getActivity()).navegarFragmento(new FragmentPreliminarGafete(),
                                FragmentPreliminarGafete.TAG, true);

                    }
                }
            }
    }
}
