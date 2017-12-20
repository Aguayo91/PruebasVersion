package com.gruposalinas.elektra.sociomas.UI.Activities.LugaresTrabajo;


import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.DeviceUtils;
import com.gruposalinas.elektra.sociomas.Utils.Security.DecryptUtils;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.AceptarUbicacionRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.EmpleadosPlantilla;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.PlantillaResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.ExpandableGroupPosicionValida;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 18/09/2017.
 */

public class LugaresInteractor {
    //HASHMAP DE AGRUPADOS
    private HashMap<String,ArrayList<LugarTrabajo>>hashAgrupados=new HashMap<>();
    public interface LugaresCallBack extends CallBackBase {
        void onSuccess(ArrayList<ExpandableGroupPosicionValida>listLugaresTrabajo);
        void onSucessRechazarAutorizar(boolean autorizar, LugarTrabajo p);
        void onSuccessListadoEmpleados(ArrayList<SearchBoxItem>listadoPlantilla);
    }
    public void getLugaresTrabajoAsync(ServerRequest request, final LugaresCallBack callBack){
        hashAgrupados.clear();
        ApplicationBase.getIntance().getControllerAPI().getPosicionesEmpleado(request).enqueue(new Callback<ZonaResponse>() {
            @Override
            public void onResponse(Call<ZonaResponse> call, Response<ZonaResponse> response) {
                    if(response.isSuccessful()){
                        if(!response.body().getError()){

                            for(LugarTrabajo p:response.body().getPosiciones()){
                                String status=p.getIdEstatusPosic();
                                if(!hashAgrupados.containsKey(status)){
                                    ArrayList<LugarTrabajo>list=new ArrayList<>();
                                    list.add(p);
                                    hashAgrupados.put(status,list);
                                }
                                else{
                                    hashAgrupados.get(status).add(p);
                                }
                            }
                            ArrayList<ExpandableGroupPosicionValida>list=new ArrayList<>();
                            for(Map.Entry<String,ArrayList<LugarTrabajo>> item: hashAgrupados.entrySet()){
                                list.add(new ExpandableGroupPosicionValida(item.getKey(),item.getValue()));
                            }
                            callBack.onSuccess(list);
                        }
                        else{
                            callBack.OnError(new Throwable(response.body().getMensajeError()));
                        }
                    }
            }

            @Override
            public void onFailure(Call<ZonaResponse> call, Throwable t) {
                callBack.OnError(t);
            }
        });
    }
    public void autorizarRechazarPosicion(final LugarTrabajo lugarTrabajo, final boolean autorizar, final LugaresCallBack callBack){
        AceptarUbicacionRequest request=new AceptarUbicacionRequest();
        request.setIdNumEmpleado(lugarTrabajo.getIdNumEmpleado());
        request.setVaNumeroPos(lugarTrabajo.getVaNumeroPos());
        request.setVaNombrePos(lugarTrabajo.getVaNombrePos());
        request.setMotivo(DeviceUtils.getSistema());
        request.setTypeMov(autorizar?"a":"r");
        ApplicationBase.getIntance().getControllerAPI().aceptarRechazarUbicacion(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(!response.body().getError()){
                    callBack.onSucessRechazarAutorizar(autorizar, lugarTrabajo);
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                    callBack.OnError(t);
            }
        });
    }
    public void getListadoEmpleadosPlantilla(final LugaresCallBack callBack){
        final ArrayList<SearchBoxItem>listRespuesta=new ArrayList<>();
        ApplicationBase.getIntance().getControllerAPI().listadoEmpleadoUbicaciones(new ServerRequest()).enqueue(new Callback<PlantillaResponse>() {
            @Override
            public void onResponse(Call<PlantillaResponse> call, Response<PlantillaResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if(response.body().getEmpleadosPlantilla()!=null) {
                            for (EmpleadosPlantilla item : response.body().getEmpleadosPlantilla()) {
                                listRespuesta.add(new SearchBoxItem(DecryptUtils.decryptAES(item.getIdNumEmpleado()),item.getNombreEmpleado()));
                            }
                            callBack.onSuccessListadoEmpleados(listRespuesta);
                        }
                    }
                    else {
                        callBack.OnError(new Throwable(response.body().getMensajeError()));
                    }
                }
            }

            @Override
            public void onFailure(Call<PlantillaResponse> call, Throwable t) {
                callBack.OnError(t);
            }
        });
    }

}
