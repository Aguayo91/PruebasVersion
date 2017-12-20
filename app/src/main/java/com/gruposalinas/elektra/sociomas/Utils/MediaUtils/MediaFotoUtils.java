package com.gruposalinas.elektra.sociomas.Utils.MediaUtils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Base64;
import android.view.SurfaceHolder;


import com.gruposalinas.elektra.sociomas.Utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by oemy9 on 09/08/2017.
 */

public class MediaFotoUtils extends BaseMediaUtils implements SurfaceHolder.Callback {


    public static  int COUNT=0;

    public static final int MAX_FOTOS=2;

    private SurfaceHolder surfaceHolder;

    public MediaFotoUtils(Context context, String fileExt, int duracion) {
        super(context, fileExt,duracion);
    }

    @Override
    public void iniciarRecording() {
        super.iniciarSurface();
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void pararRecording() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setJpegQuality(60);
            parameters.setPictureFormat(PixelFormat.JPEG);
            parameters.setPictureSize(640, 480);
            parameters.setAutoWhiteBalanceLock(true);
            parameters.setRotation(90);
            camera.setParameters(parameters);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            camera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] bytes, Camera camera) {
                    onPictureTakenFinal(bytes,COUNT,camera);
                }
            });
        } catch (Exception e) {
            removeWindowView();
            listener.onError(e.getMessage());
        }


    }

    private void onPictureTakenFinal(byte[] bytes, final int count,Camera camera){
        filePath=getFileName();
        File filePicture = new File(filePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePicture);
            fos.write(bytes);
            fos.close();
            String base64= Base64.encodeToString(Utils.loadFile(filePicture),0);
            if(!base64.isEmpty()){
                listener.onFinish(base64, filePath);
                if(COUNT<MAX_FOTOS) {
                    try {
                        camera.startPreview();
                        camera.takePicture(null, null, new Camera.PictureCallback() {
                            @Override
                            public void onPictureTaken(byte[] bytes, Camera camera) {
                                onPictureTakenFinal(bytes, count, camera);
                            }
                        });
                        COUNT++;
                    }
                    catch (Exception ex) {
                        removeWindowView();
                        listener.onError("Exception en cámara");
                    }
                }
                else{
                   removeWindowView();
                }
            }

        } catch (FileNotFoundException e) {
            removeWindowView();
            this.listener.onError("FileNotfound en imagen");

        } catch (IOException e) {
            removeWindowView();
            listener.onError("IOException en imagen");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
