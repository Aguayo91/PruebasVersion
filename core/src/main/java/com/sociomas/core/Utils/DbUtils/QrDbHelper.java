package com.sociomas.core.Utils.DbUtils;

import android.content.Context;
import android.util.Log;

import com.sociomas.core.DataBaseModel.QrPendientes;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.Utils.OrmLite.DBHelper;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;
import com.sociomas.core.WebService.Model.Request.Alta.ServerResponseAventones;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 24/10/2017.
 */

public class QrDbHelper extends DBUtils {
    public interface QrDbListener {
        void onSuccessEnviadoQR(String mensajeSuccess);
        void onCancelEnviadoQR();
    }

    private QrDbListener listener;

    public QrDbHelper(Context context) {
        super(context);
    }

    public void setListener(QrDbListener listener) {
        this.listener = listener;
    }

    public void agregarTagQR(QrPendientes qrPendientes){
        try {
            dao=DBHelper.getHelper(this.context,mDBHelper).getQrDao();
            dao.create(qrPendientes);
            Log.i(TAG, "QR agregado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*REVISA SI HAY PENDIENTES POR ENVIAR EN QR*/
    private List<QrPendientes> getPendientesQR(){
        List<QrPendientes>listPendientes=new ArrayList<>();
        try{
            dao= DBHelper.getHelper(this.context,mDBHelper).getQrDao();
            listPendientes=dao.queryForEq(QrPendientes.PENDIENTE,true);
            return  listPendientes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPendientes;
    }

    private void updateEnviado(QrPendientes qrPendiente,String mensajeSuccess){
        try {
            dao=DBHelper.getHelper(this.context,mDBHelper).getQrDao();
            dao.update(qrPendiente);
            Log.i(TAG,qrPendiente.isPendiente()?"Qr no fue enviado": "Qr enviado correctamente");
            if(listener!=null){
                listener.onSuccessEnviadoQR(mensajeSuccess);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*ENVÍA LOS PENDIENTES AL WEB SERVICE*/
    private void enviarQrWebservice(final QrPendientes qrPendiente){
        ControllerAventon ctrlAventon=new ControllerAventon(this.context);
        ctrlAventon.insertarQR(qrPendiente.toAlta()).enqueue(new Callback<ServerResponseAventones>() {
            @Override
            public void onResponse(Call<ServerResponseAventones> call, Response<ServerResponseAventones> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()) {
                        qrPendiente.setPendiente(false);
                        updateEnviado(qrPendiente,response.body().getMensaje());
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponseAventones> call, Throwable t) {
                qrPendiente.setPendiente(true);
                updateEnviado(qrPendiente,null);
            }
        });

    }
    /*PENDIENTE ENVÍA PENDIENTES DE QR*/
    public void checkIfPendientesQr(){
        List<QrPendientes>listPendientes=getPendientesQR();
        if(listPendientes!=null && (!listPendientes.isEmpty())){
            for(QrPendientes p: listPendientes){
                enviarQrWebservice(p);
            }
        }
    }


}
