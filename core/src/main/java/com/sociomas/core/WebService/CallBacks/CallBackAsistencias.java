package com.sociomas.core.WebService.CallBacks;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.WebService.Model.Response.Asistencia.ExpandableGroupAsistencia;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;

import java.util.ArrayList;

/**
 * Created by oemy9 on 14/08/2017.
 */
public interface CallBackAsistencias extends CallBackBase {
    void OnSuccess(ArrayList<ExpandableGroupAsistencia> listAsistencias, ArrayList<ResultadoAsistencia> listHoy);
    void OnResponseListEmpleado(ArrayList<SearchBoxItem> listEmpleados);
}
