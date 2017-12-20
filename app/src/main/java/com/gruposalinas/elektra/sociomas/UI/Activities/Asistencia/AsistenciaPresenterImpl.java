package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.WebService.CallBacks.CallBackAsistencias;
import com.sociomas.core.WebService.Model.Request.Asistencia.AsistenciaRequest;
import com.sociomas.core.WebService.Model.Response.Asistencia.ExpandableGroupAsistencia;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by oemy9 on 08/09/2017.
 */

public class AsistenciaPresenterImpl extends BasePresenterImpl implements AsistenciaPresenter, CallBackAsistencias {


    private AsistenciaInteractor interactor;
    private AsistenciaView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(AsistenciaView)getView();
        this.interactor=new AsistenciaInteractor();
    }
    @Override
    public void getAsistenciasAsync(String numeroEmpleado) {
        onShowProgress();
        AsistenciaRequest request=new AsistenciaRequest();
        request.setNumeroEmpleado(numeroEmpleado);
        this.interactor.getAsistenciasAsync(request,this);
    }



    @Override
    public void OnSuccess(ArrayList<ExpandableGroupAsistencia> listAsistencias, ArrayList<ResultadoAsistencia>listHoy) {


        view.setListAsistencia(listAsistencias);


        Observable.fromIterable(listHoy).filter(new Predicate<ResultadoAsistencia>() {
            @Override
            public boolean test(ResultadoAsistencia resultadoAsistencia) throws Exception {
               return resultadoAsistencia!=null;
            }
        }).toList().subscribe(new Consumer<List<ResultadoAsistencia>>() {
            @Override
            public void accept(List<ResultadoAsistencia> listFilterHoy) throws Exception {
                view.setListAsistenciaHoy((ArrayList<ResultadoAsistencia>)listFilterHoy);
            }
        });
        onHideProgress();
    }

    @Override
    public void OnResponseListEmpleado(ArrayList<SearchBoxItem> listEmpleados) {
        view.setListEmpleados(listEmpleados);
    }

    @Override
    public void OnError(Throwable error) {
        onShowAlert(error.getMessage());
        onHideProgress();
    }

    public interface AsistenciaView extends BaseView {
        void setListAsistencia(ArrayList<ExpandableGroupAsistencia> listAsistencia);
        void setListAsistenciaHoy(ArrayList<ResultadoAsistencia>listAsistenciaHoy);
        void setListEmpleados(ArrayList<SearchBoxItem> listEmpleados);
    }
}
