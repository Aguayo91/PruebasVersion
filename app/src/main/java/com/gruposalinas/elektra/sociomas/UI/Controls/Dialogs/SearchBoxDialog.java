package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterSearchDialog;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oemy9 on 24/05/2017.
 */

public class SearchBoxDialog implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    private String title;
    private AlertDialog alertDialog;
    private ArrayList<SearchBoxItem> itemArrayList;
    private AdapterSearchDialog adapter;
    private ListView listViewItems;
    public static final String TODOS="Todos";

    public void setTitle(String title) {
        this.title = title;
    }
    public void setTitle(int resID){
        this.title=context.getString(resID);
    }


    public interface SearchBoxDialogCallBack{
        void onResult(SearchBoxItem resultItem);
    }
    private SearchBoxDialogCallBack callBack;
    public void setCallBack(SearchBoxDialogCallBack callBack) {
        this.callBack = callBack;
    }
    private Context context;
    public SearchBoxDialog(Context context){
        this.context=context;
        this.itemArrayList =new ArrayList<>();
    }
    public void setListEmpleados(ArrayList<ListExclusiones> listEmpleados){
        if(listEmpleados!=null){
            HashMap<String,String>listNombres=new HashMap<>();
            this.itemArrayList.clear();
            this.itemArrayList.add(new SearchBoxItem(TODOS,TODOS));
            for(ListExclusiones exclusionesEmpleado: listEmpleados){
                if(!listNombres.containsKey(exclusionesEmpleado.getVaNombreCompleto())) {
                    this.itemArrayList.add(new SearchBoxItem(exclusionesEmpleado.getIdNumEmpleado(),
                            exclusionesEmpleado.getVaNombreCompleto()));
                    listNombres.put(exclusionesEmpleado.getVaNombreCompleto(),exclusionesEmpleado.getIdNumEmpleado());
                }
            }
        }
    }
    public ArrayList<SearchBoxItem> getItemArrayList() {
        return itemArrayList;
    }

    public void setListEmpleados(HashMap<String, EmpleadoIncidencia> hashMapEmpleados){
        if(hashMapEmpleados!=null){
            this.itemArrayList.clear();
            this.itemArrayList.add(new SearchBoxItem(TODOS,TODOS));
            for(Map.Entry<String,EmpleadoIncidencia> entry: hashMapEmpleados.entrySet()){
                this.itemArrayList.add(new SearchBoxItem(entry.getKey(),entry.getValue().getDecripcionEmpleado()));
            }
        }
    }

    public void showAsync(){
        final AlertDialog.Builder builderDialogo=new AlertDialog.Builder(this.context);
        builderDialogo.setTitle(title);
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View rootView = layoutInflater.inflate(R.layout.search_list_dialog, null);
        listViewItems=(ListView)rootView.findViewById(R.id.listItems);
        adapter=new AdapterSearchDialog(this.context,this.itemArrayList);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(this);

        /*SEARCH VIEW*/
        SearchView searchView=(SearchView)rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        builderDialogo.setView(rootView);
        builderDialogo.setNegativeButton(R.string.cancelar,null);
        this.alertDialog=builderDialogo.create();
        this.alertDialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
            if(!s.isEmpty()){
                adapter.filtrar(s,itemArrayList);
            }
            else{
                adapter.resetAdapter(this.itemArrayList);
            }
            return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if(callBack!=null && alertDialog!=null){
                SearchBoxItem item=adapter.getItem(position);
                callBack.onResult(item);
                alertDialog.dismiss();
            }
    }


}
