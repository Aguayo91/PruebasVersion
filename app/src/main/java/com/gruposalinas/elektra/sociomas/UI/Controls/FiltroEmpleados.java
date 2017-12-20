package com.gruposalinas.elektra.sociomas.UI.Controls;

import android.text.TextUtils;
import android.widget.Filter;

import com.gruposalinas.elektra.sociomas.UI.Adapters.AdapterEmpleadosRecyclerPicker;
import com.sociomas.core.DataBaseModel.Plantilla;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jmarquezu on 01/12/2017.
 */


public class FiltroEmpleados extends Filter {
    AdapterEmpleadosRecyclerPicker adapter;
    ArrayList<Plantilla> filterList;
    public FiltroEmpleados(ArrayList<Plantilla> filterList,AdapterEmpleadosRecyclerPicker adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }
    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        final FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(!TextUtils.isEmpty(constraint.toString()) && (constraint.length()>4))
        {
            final String query=constraint.toString().toLowerCase();
            ArrayList<Plantilla> filteredEmpleados=new ArrayList<>();
            for(Plantilla p: filterList){
                if(p.getIdEmpleado().equals(query) || p.getIdEmpleado().contains(query)
                        || p.getNombreEmpleado().toLowerCase().startsWith(query) || p.getNombreEmpleado().toLowerCase().contains(query)
                        || p.getNombreEmpleado().toLowerCase().equalsIgnoreCase(query)){
                    filteredEmpleados.add(p);
                }
            }
            results.count=filteredEmpleados.size();
            results.values=filteredEmpleados;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setPlantilla ((ArrayList<Plantilla>) results.values);
    }

}