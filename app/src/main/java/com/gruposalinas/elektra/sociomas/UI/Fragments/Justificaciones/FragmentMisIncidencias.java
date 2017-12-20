package com.gruposalinas.elektra.sociomas.UI.Fragments.Justificaciones;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.sociomas.core.Utils.Enums.EnumTipo;

/**
 * Created by oemy9 on 31/07/2017.
 */

public class FragmentMisIncidencias extends FragmentBaseIncidencia  implements AdapterView.OnItemClickListener  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listado_incidencias, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        gridViewIncidencias =(GridView)rootView.findViewById(R.id.listadoIncidencias);
        gridViewIncidencias.setOnItemClickListener(this);
        callWebService(EnumTipo.mio);
        return rootView;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        AdapterUtils.navegarIncidencias(getActivity(),adapterIncidencia.getItem(position),EnumTipo.mio);
    }
}
