package com.sociomas.core.WebService.Model.Response.Incidencia;

import com.sociomas.core.UI.Controls.ExpandableRecyclerView.models.ExpandableGroup;

import java.util.List;

/**
 * Created by oemy9 on 23/10/2017.
 */

public class ExpandableGroupIncidencias  extends ExpandableGroup<ListadoIncidencias> {



    public ExpandableGroupIncidencias(String title, List<ListadoIncidencias> items) {
        super(title, items);
    }
}
