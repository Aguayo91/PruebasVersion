package com.sociomas.core.WebService.Model.Response.Asistencia;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.models.ExpandableGroup;

import java.util.List;
public class ExpandableGroupAsistencia extends ExpandableGroup<ResultadoAsistencia> {
    public ExpandableGroupAsistencia(String title, List<ResultadoAsistencia> items) {
        super(title, items);
    }
}
