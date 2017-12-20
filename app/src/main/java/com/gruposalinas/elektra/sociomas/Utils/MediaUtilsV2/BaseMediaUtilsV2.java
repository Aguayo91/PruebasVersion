package com.gruposalinas.elektra.sociomas.Utils.MediaUtilsV2;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oemy9 on 09/08/2017.
 */

public abstract class BaseMediaUtilsV2 {
    /*NECARAIOS PARA FOTO & VIDEO*/
    protected WindowManager windowManager;
    protected SurfaceView surfaceView;
    protected Camera camera = null;
    /*DATE FORMAT PANICO*/
    private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_PANICO);
    /*METODOS PARA INICIAR Y PARAR EL RECORDING*/
    public abstract  void iniciarRecording();
    public abstract  void pararRecording();
    /*MEDIA RECORDER BASE QUE NOS AYUDARÁ A GRABAR AUDIO & VIDEO*/
    protected MediaRecorder mediaRecorder;
    /*RUTA  FINAL DEL ARCHIVO QUE SE ALMACENA EN LA SD*/
    protected String filePath;
    /*CONTEXTO BASE DE LA APLICACIÓN*/
    protected Context context;
    /*¿ACTUALMENTE GRABANDO AUDIO O VIDEO?*/
    protected boolean alreadyRecording;
    /*EXTENSIÓN DEL ARCHIVO */
    protected String  fileExt;
    /*CALLBACK BASE CON METODOS ON FINISH Y ERROR */
    public interface MediaListener{
        void onFinish(String base64File, String audioPath);
        void onError(String mensajeError);
    }
    /*DURACIÓN EN SEGUNDOS*/
    protected int segundos;

    /*LISTENER QUE HACE REFERENCIA AL CALLBACK*/
    public MediaListener listener;
    /*SETTER DEL LISTENER*/
    public void setListener(MediaListener listener) {this.listener = listener;}
    /*GETTER PARA OBTENER EXTENSIÓN DEL ARCHIVO*/
    public String getFileExt() {return fileExt;}
    /*CONSTRUCTOR*/
    public BaseMediaUtilsV2(Context context, String fileExt, int duracion) {
        this.context = context;
        this.fileExt=fileExt;
        this.alreadyRecording = false;
        this.segundos=(Constants.SEGUNDO*duracion);
        this.mediaRecorder=new MediaRecorder();
    }

    public int getSegundos() {
        return segundos;
    }

    protected void iniciarSurface(){
        windowManager = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
        surfaceView = new SurfaceView(this.context);
        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams(
               1,1,
               WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
               WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                       WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
               WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,PixelFormat.TRANSLUCENT
       );
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManager.addView(surfaceView, layoutParams);
    }
    protected void removeWindowView(){
      try {
          if (this.windowManager != null) {
              this.windowManager.removeView(surfaceView);
          }
          if (this.camera != null) {
              this.camera.lock();
              this.camera.stopPreview();
              this.camera.release();
              this.camera = null;
          }
          if (this.mediaRecorder != null) {
              this.mediaRecorder.stop();
              this.mediaRecorder.release();
              this.mediaRecorder = null;
          }
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    /*OBTIENE EL NOMBRE DEL ARHIVO*/
    public String getFileName(){
        return  Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+String.valueOf(System.currentTimeMillis())+"."+fileExt;
    }
    public String getFileNameWS(){
       return  Utils.getNumeroEmpleado(this.context).concat(dateFormat.format(new Date()));
    }

    /*HELPERS*/
    protected void showToast(String mensaje){
        Toast.makeText(this.context,mensaje,Toast.LENGTH_LONG).show();
    }
    protected boolean hasMicrofono(Context context){
        PackageManager packageManager=context.getPackageManager();
        return  packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }
    protected byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }

}
