package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.Expandible;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.Utils.Enums.EnumIncidencia;
import com.sociomas.core.Utils.Security.DecryptUtils;
import com.sociomas.core.WebService.CallBacks.CallBackAprobarRechazar;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.CallBacks.CallBackIncidencia;
import com.sociomas.core.WebService.Model.Request.Incidencia.RAprobarJustificante;
import com.sociomas.core.WebService.Model.Request.Incidencia.RootIncidencia;
import com.sociomas.core.WebService.Model.Request.Incidencia.SolicitarJustificacion;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 20/10/2017.
 */

public class IncidenciaInteractor {

    public void validarJustificacion(final ListadoIncidencias selectedItem, final CallBackBaseResponse callBackBaseResponse){
        SolicitarJustificacion solicitarJustificacion = new SolicitarJustificacion();
        solicitarJustificacion.setIdCscIncid(selectedItem.getCSC());
        solicitarJustificacion.setExtension("JPEG");
        solicitarJustificacion.setVaComentarios("Autorizado por jefe");
        solicitarJustificacion.setArchivoAdjunto("");
        solicitarJustificacion.setNombreArchivo("imagenDroid");
        solicitarJustificacion.setTamanoArchivo("0");
        solicitarJustificacion.setIdJustificacion(6);
        solicitarJustificacion.setBitTempFija(false);
        solicitarJustificacion.setIdNumEmpleadoJustifica(selectedItem.getEmpleado());
        ApplicationBase.getIntance().getControllerAPI().agregarJustificacion(solicitarJustificacion).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                      callBackBaseResponse.onSuccess(response.body());
                    } else if (response.body().getMensajeError() != null) {
                        callBackBaseResponse.OnError(new Throwable(response.body().getMensajeError()));
                    }
                } else {
                    callBackBaseResponse.OnError(new Throwable(ApplicationBase.getAppContext().getString((R.string.Error_Conexion))));

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                callBackBaseResponse.OnError(new Throwable(ApplicationBase.getAppContext().getString((R.string.Error_Conexion))));
            }
        });

    }

    public void rechazarAprobarAsync(ListadoIncidencias selectedItem, boolean validar, CallBackAprobarRechazar callBackAprobarRechazar){
        RAprobarJustificante item = new RAprobarJustificante();
        item.setEmpleado_valida(selectedItem.getEmpleado());
        item.setVa_comentarios(selectedItem.getComentarioRechazo());
        item.setId_csc_incid(selectedItem.getCSC());
        item.setIdCscJustificacion(selectedItem.getIdJustificacion());
        ApplicationBase.getIntance().getControllerAPI().rechazarValidarAsync(item, validar);
        ApplicationBase.getIntance().getControllerAPI().setCallBackAprobarRechazar(callBackAprobarRechazar);
    }

    public void ListadoIncidenciasAsync(final CallBackIncidencia callBackIncidencia){
        final HashMap<String,EmpleadoIncidencia> empleadoIncidencias=new HashMap<String, EmpleadoIncidencia>();
        final HashMap<String,String>nombresDecrypts=new HashMap<String, String>();
        try {
            ApplicationBase.getIntance().getControllerAPI().ListadoIncidencias(new RootIncidencia()).enqueue(new Callback<ResponseIncidencia>() {
                @Override
                public void onResponse(Call<ResponseIncidencia> call, Response<ResponseIncidencia> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getError())
                        {
                            //Utils.checkIfFechaError(response.body().getServerUTCTime(),response.body().getServerTime());
                            callBackIncidencia.OnError(new Throwable(response.body().getMensajeError()));
                            return;
                        }

                        if (callBackIncidencia != null) {


                            ArrayList<ListadoIncidencias> items=new ArrayList<>();
                            items.addAll(response.body().getListadoPlantilla());
                            items.addAll(response.body().getListadoIncidencias());

                            //LISTA QUE ALMACENA LOS AUTORIZADOS PARA POSTERIORMENTE ELIMINARLOS
                            ArrayList<ListadoIncidencias>itemsToRemove=new ArrayList<ListadoIncidencias>();
                            EnumIncidencia incidenciaTipo=EnumIncidencia.sin_justificar;
                            //LA LISTA NO ES NULL
                            if(items!=null) {

                                //FOREACH ITEM EN EL LISTADO
                                for (ListadoIncidencias item : items)
                                {
                                    if(!nombresDecrypts.containsKey(item.getNombre())) {
                                        item.setEmpleado(DecryptUtils.decryptAES(item.getEmpleado()));
                                        nombresDecrypts.put(item.getNombre(),item.getEmpleado());
                                    }
                                    else{
                                        item.setEmpleado(nombresDecrypts.get(item.getNombre()));
                                    }
                                    incidenciaTipo=incidenciaTipo.getFromSting(item.getEstatusJustificacion());

                                    if(incidenciaTipo== EnumIncidencia.autorizado){
                                        itemsToRemove.add(item);
                                    }

                                    if (!empleadoIncidencias.containsKey(item.getEmpleado()))
                                    {
                                        empleadoIncidencias.put(item.getEmpleado(),new EmpleadoIncidencia(
                                                item.getEmpleado()+" -"+ item.getNombre(),item.getEstatusJustificacion()
                                        ));
                                    }
                                    else{
                                        empleadoIncidencias.get(item.getEmpleado()).
                                                setByStatus(item.getEstatusJustificacion());
                                    }
                                }
                                items.removeAll(itemsToRemove);
                            }

                            callBackIncidencia.OnSuccess(response.body(),empleadoIncidencias);
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseIncidencia> call, Throwable t) {
                    if(t!=null){
                        callBackIncidencia.OnError(new Throwable(ApplicationBase.getAppContext().getString((R.string.Error_Conexion))));
                    }
                    //     callBackIncidenciaResponse.OnError( context.getString(R.string.Error_Conexion));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
