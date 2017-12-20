package com.gruposalinas.elektra.sociomas.Utils.MediaUtils;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Base64;

import java.io.File;
import java.io.IOException;

/**
 * Created by oemy9 on 09/08/2017.
 */

public class MediaAudioUtils extends BaseMediaUtils implements MediaRecorder.OnInfoListener {

    public MediaAudioUtils(Context context,String fileExt, int duracion) {
        super(context,fileExt,duracion);
    }
    public String getFileNameWS(int count) {
            return  String.format("SOS-%s",count);
    }
    @Override
    public void iniciarRecording() {
        if(hasMicrofono(this.context)) {
            try {
                if(!alreadyRecording) {
                    filePath = getFileName();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mediaRecorder.setMaxDuration(getSegundos());
                    mediaRecorder.setOutputFile(filePath);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    mediaRecorder.setOnInfoListener(this);
                    this.alreadyRecording = true;
                }

            } catch (Exception e) {
                this.mediaRecorder.stop();
                this.mediaRecorder.release();
                this.mediaRecorder=null;
                listener.onError(e.getMessage());
            }
        }
        else{
            showToast("No cuenta con microfono");
        }

    }

    @Override
   public void pararRecording() {
            mediaRecorder.release();
            mediaRecorder.stop();
    }

    @Override
    public void onInfo(MediaRecorder mediaRecorder,  int info, int extra) {
        if(info==MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
            this.mediaRecorder.stop();
            this.mediaRecorder.release();
            this.mediaRecorder=null;
            File file=new File(filePath);
            try {
                String base64= Base64.encodeToString(loadFile(file),0);
                if(!base64.isEmpty()){
                    this.listener.onFinish(base64, filePath);
                    this.alreadyRecording=false;
                }
            } catch (IOException e) {
                listener.onError(e.getMessage());
            }
            catch (IllegalStateException e){
                listener.onError(e.getMessage());
            }
        }
    }
}
