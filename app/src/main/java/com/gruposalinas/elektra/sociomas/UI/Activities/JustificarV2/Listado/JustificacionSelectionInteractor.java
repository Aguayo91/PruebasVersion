package com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BaseInteractor;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Enums.EnumIncidencia;
import com.sociomas.core.Utils.Security.DecryptUtils;
import com.sociomas.core.WebService.CallBacks.CallBackAprobarRechazar;
import com.sociomas.core.WebService.Model.Request.Incidencia.RAprobarJustificante;
import com.sociomas.core.WebService.Model.Request.Incidencia.RootIncidencia;
import com.sociomas.core.WebService.Model.Request.Incidencia.SolicitarJustificacion;
import com.sociomas.core.WebService.Model.Request.ListaEmpleado.ActualizarSupervisorRequest;
import com.sociomas.core.WebService.Model.Request.ListaEmpleado.ListaEmpleadoRequest;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;
import com.sociomas.core.WebService.Model.Response.ListaEmpleado.ListaEmpleadoReponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import org.reactivestreams.Subscriber;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 27/11/2017.
 */

public class JustificacionSelectionInteractor extends BaseInteractor {
    public JustificacionSelectionInteractor() {
        super(ApplicationBase.getIntance().getApplicationContext(),ApplicationBase.getIntance().getControllerAPI());
    }


    public void actualizaSupervisor(String numeroEmpleado, final Subscriber<ServerResponse>subscriber){
        getControllerAPI().actualizaSupervisor(new ActualizarSupervisorRequest(numeroEmpleado)).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    subscriber.onNext(response.body());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                if(t!=null){
                    subscriber.onError(t);
                }
            }
        });
    }


    public void obtenerListaEmpleado(String query, final Subscriber<ListaEmpleadoReponse> subscriber){
        getControllerAPI().obtenerListaEmpleados(new ListaEmpleadoRequest(query)).enqueue(new Callback<ListaEmpleadoReponse>() {
            @Override
            public void onResponse(Call<ListaEmpleadoReponse> call, Response<ListaEmpleadoReponse> response) {
                if(response.isSuccessful()){
                    subscriber.onNext(response.body());
                }
                else{
                    subscriber.onError(new Throwable("Error"));
                }
            }

            @Override
            public void onFailure(Call<ListaEmpleadoReponse> call, Throwable t) {
                subscriber.onError(t);
            }
        });
    }

    public void justificarIncidencia(ListadoIncidencias selectedIncidencia, final Subscriber<ServerResponse>subscriber){
        SolicitarJustificacion sj =new SolicitarJustificacion();
        sj.setIdCscIncid(selectedIncidencia.getCSC());
        sj.setExtension("JPEG");
        sj.setVaComentarios(selectedIncidencia.getComentarios());
        sj.setNombreArchivo("imagenDroid");
        sj.setTamanoArchivo("0");
        sj.setIdJustificacion(2);
        sj.setBitTempFija(false);
        sj.setIdNumEmpleadoJustifica(selectedIncidencia.getEmpleado());
        getControllerAPI().agregarJustificacion(sj).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()) {
                    if(!response.body().getError()) {
                        subscriber.onNext(response.body());
                        subscriber.onComplete();
                    }
                    else{
                        subscriber.onError(new Throwable(response.body().getMensajeError()));
                        subscriber.onComplete();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                subscriber.onError(new Throwable(ApplicationBase.getIntance().getApplicationContext().getString(R.string.Error_Conexion)));
                subscriber.onComplete();
            }
        });
    }

    public void rechazarAprobarAsync(ListadoIncidencias selectedItem, boolean aprobar, CallBackAprobarRechazar callBackAprobarRechazar){
       //TODO PASAR ESTO A RX JAVA
        RAprobarJustificante item = new RAprobarJustificante();
        item.setEmpleado_valida(selectedItem.getEmpleado());
        item.setVa_comentarios(selectedItem.getComentarioRechazo());
        item.setId_csc_incid(selectedItem.getCSC());
        item.setIdCscJustificacion(selectedItem.getIdJustificacion());
        ApplicationBase.getIntance().getControllerAPI().rechazarValidarAsync(item, aprobar);
        ApplicationBase.getIntance().getControllerAPI().setCallBackAprobarRechazar(callBackAprobarRechazar);
    }

    public void obtenerJustificacionesAsync(final String numeroEmpleado, final boolean plantilla, final Subscriber<List<ListadoIncidencias>>subscriber){
        try {
            getControllerAPI().ListadoIncidencias(new RootIncidencia()).enqueue(new Callback<ResponseIncidencia>() {
                @Override
                public void onResponse(Call<ResponseIncidencia> call, Response<ResponseIncidencia> response) {
                    if(response.isSuccessful()) {
                        if(!response.body().getError()) {
                            Observable.fromIterable(!plantilla ? response.body().getListadoIncidencias() : response.body().getListadoPlantilla()).filter(new Predicate<ListadoIncidencias>() {
                                @Override
                                public boolean test(ListadoIncidencias item) throws Exception {
                                    EnumIncidencia incidenciaTipo = EnumIncidencia.getFromSting(item.getEstatusJustificacion());
                                    EnumAsistencia asistenciaTipo=EnumAsistencia.fromValue(item.getIdTipoIncidencia());
                                    return incidenciaTipo != EnumIncidencia.autorizado && asistenciaTipo!=EnumAsistencia.TODAVIA_NO_TERMINA_DIA;
                                }
                            }).map(new Function<ListadoIncidencias, ListadoIncidencias>() {
                                @Override
                                public ListadoIncidencias apply(ListadoIncidencias item) throws Exception {
                                    item.setEmpleado(DecryptUtils.decryptAES(item.getEmpleado()));
                                    return item;
                                }
                            }).toList().subscribe(new Consumer<List<ListadoIncidencias>>() {
                                @Override
                                public void accept(List<ListadoIncidencias> listadoIncidencias) throws Exception {
                                    Observable.fromIterable(listadoIncidencias).filter(new Predicate<ListadoIncidencias>() {
                                        @Override
                                        public boolean test(ListadoIncidencias item) throws Exception {
                                            return item.getEmpleado().equalsIgnoreCase(numeroEmpleado);
                                        }
                                    }).toList().subscribe(new Consumer<List<ListadoIncidencias>>() {
                                        @Override
                                        public void accept(List<ListadoIncidencias> list) throws Exception {
                                            subscriber.onNext(list);
                                            subscriber.onComplete();
                                        }
                                    });

                                }
                            });
                        }
                        else{
                            subscriber.onError(new Throwable(response.body().getMensajeError()));
                        }
                    }
                    else{
                        subscriber.onError(new Throwable(ApplicationBase.getIntance().getApplicationContext().getString(R.string.Error_Conexion)));
                    }
                }

                @Override
                public void onFailure(Call<ResponseIncidencia> call, Throwable t) {
                    subscriber.onError(new Throwable(ApplicationBase.getIntance().getApplicationContext().getString(R.string.Error_Conexion)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
