package com.gruposalinas.elektra.sociomas.Utils.Adapters;

import android.content.Context;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Security.DecryptUtils;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.RootUbicacion;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by oemy9 on 03/05/2017.
 */

@SuppressWarnings("unused")
public class MapUtils{

    /*CONSTANTES MAPAS GOOGLE ÚLTIMA UBICACIÓN*/
    public static final String NOMBRE_EMPLEADO="NOMBRE_EMPLEADO";
    public static final String ID_EMPLEADO="ID_EMPLEADO";
    public static int ZOOM_AUMENTO=6;
    public static int ZOOM_MAXIMO=16;
    public static int INTERVALO_ACTUALIZACION=300000;
    public static LatLng posicionMexico=new LatLng(19.432608,-99.133209);

    private  String[] from;
    private   int[] to;
    public SimpleCursorAdapter mAdapter;

    public MapUtils(Context context){
        from= new String[]{MapUtils.NOMBRE_EMPLEADO,MapUtils.ID_EMPLEADO};
        to = new int[]{android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(context,
                R.layout.item_query_autocomplete,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }
    public RootUbicacion decrypUbicacionResponse(RootUbicacion ubicacionResponse){
        RootUbicacion ubicacionDecrypt=new RootUbicacion();
        ArrayList<Ubicaciones> ubicacionesList=new ArrayList<Ubicaciones>();
        for(Ubicaciones ubicacioneEncrypt: ubicacionResponse.getUbicaciones()){
            Ubicaciones ubicacione=ubicacioneEncrypt;
            ubicacione.setIdNumEmpleado(DecryptUtils.decryptAES(ubicacioneEncrypt.getIdNumEmpleado()));
          //  ubicacione.setFechaHora(Utils.parseJsonDate(ubicacioneEncrypt.getFechaHora()));
            ubicacionesList.add(ubicacione);
        }
        ubicacionDecrypt.setUbicaciones(ubicacionesList);
        return ubicacionDecrypt;
    }

    public void buscarEmpleado(final String query,RootUbicacion resMovimientos) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID,MapUtils.NOMBRE_EMPLEADO,MapUtils.ID_EMPLEADO });
        if(resMovimientos!=null){
            int contador=0;
            for(Ubicaciones ubicacione : resMovimientos.getUbicaciones()) {
                if(ubicacione.getNombre().toLowerCase(Locale.ENGLISH).startsWith(query.toLowerCase(Locale.ENGLISH))
                        || ubicacione.getIdNumEmpleado().startsWith(query)
                        || ubicacione.getNombre().toLowerCase(Locale.ENGLISH).contains(query.toLowerCase(Locale.ENGLISH))) {
                    c.addRow(new Object[]{
                            contador, ubicacione.getIdNumEmpleado().concat("-").concat(ubicacione.getNombre()),
                            ubicacione.getIdNumEmpleado()});
                }
            }
            mAdapter.changeCursor(c);
        }
    }
}
