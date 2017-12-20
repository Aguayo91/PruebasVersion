package com.gruposalinas.elektra.sociomas.Utils.MediaUtils;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.util.Base64;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.IOException;

/**
 * Created by oemy9 on 09/08/2017.
 */

public class MediaVideoUtils extends  BaseMediaUtils implements MediaRecorder.OnInfoListener, SurfaceHolder.Callback {

    public MediaVideoUtils(Context context,String fileExt,int duracion) {
        super(context,fileExt,duracion);
    }

    @Override
   public   void iniciarRecording() {
        super.iniciarSurface();
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(this);
    }

    @Override
   public void pararRecording() {
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        camera.lock();
        camera.release();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(!alreadyRecording) {
            try {
                mediaRecorder = new MediaRecorder();
                camera = Camera.open();
                camera.unlock();
                filePath =getFileName();
                mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                mediaRecorder.setCamera(camera);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                mediaRecorder.setVideoFrameRate(27);
                mediaRecorder.setVideoSize(640, 480);
                mediaRecorder.setMaxDuration(getSegundos());
                mediaRecorder.setOutputFile(filePath);
                mediaRecorder.setOnInfoListener(this);
                mediaRecorder.prepare();
                mediaRecorder.start();
                alreadyRecording = true;
            } catch (Exception e) {
                    removeWindowView();
                    listener.onError(e.getMessage());
                }
            }
        }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    @Override
    public void onInfo(MediaRecorder mediaRecorder,  int info, int extra) {
        if(info==MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
            removeWindowView();
            File file=new File(filePath);
            try {
                String base64= Base64.encodeToString(loadFile(file),0);
                if(!base64.isEmpty()){
                    this.listener.onFinish(base64, filePath);
                    this.alreadyRecording=false;
                }
            } catch (IOException e) {
                removeWindowView();
                listener.onError(e.getMessage());
            }
            catch (IllegalStateException e){
                removeWindowView();
                listener.onError(e.getMessage());
            }
        }
    }


}
