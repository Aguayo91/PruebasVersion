package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Adapters.Ubicaciones.AdapterPlantillaUbicacion;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.CustomNumberPicker;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.IndicadorUbicacion;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;

import java.util.ArrayList;

/**
 * Created by oemy9 on 25/05/2017.
 */

public class SearchUbicacionDialog implements AdapterView.OnItemClickListener,CustomNumberPicker.NumberPickerCallBack, SearchView.OnQueryTextListener {

    private Button btnCancelar;
    private Dialog alertDialog;
    private ArrayList<Ubicaciones> itemArrayList;
    private ListView listViewItems;
    private LayoutInflater layoutInflater;
    private View rootView;
    private AdapterPlantillaUbicacion adapterUbicacion;
    private CustomNumberPicker numberPicker;
    private SearchView searchViewFloat;
    private TextView tvTotal,tvValidos,tvOtros,tvNoActualizados;


    public interface SearchUbicacionCallBack{
        void onResult(Ubicaciones resultItem);
        void OnValueNumberPickerChanged(int value);
        void onCancel();
    }

    private SearchUbicacionCallBack callBack;
    public void setCallBack(SearchUbicacionCallBack callBack) {
        this.callBack = callBack;
    }
    private Context context;
    public SearchUbicacionDialog(Context context){
        this.context=context;
        this.itemArrayList =new ArrayList<>();
        layoutInflater= LayoutInflater.from(this.context);
        rootView=layoutInflater.inflate(R.layout.search_list_ubicacion, null);
    }

    public void showAsync()  {
        if(this.alertDialog==null) {
            alertDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(rootView);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            lp.windowAnimations = R.style.DialogAnimation;
            alertDialog.getWindow().setAttributes(lp);
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            btnCancelar = (Button) rootView.findViewById(R.id.btnCancelar);
            listViewItems = (ListView) rootView.findViewById(R.id.listItems);
            numberPicker = (CustomNumberPicker) rootView.findViewById(R.id.numberPicker);
            tvOtros = (TextView) rootView.findViewById(R.id.tvOtros);
            tvTotal = (TextView) rootView.findViewById(R.id.tvTotal);
            tvValidos = (TextView) rootView.findViewById(R.id.tvValidos);
            tvNoActualizados = (TextView) rootView.findViewById(R.id.tvNoActualizados);
            searchViewFloat=(SearchView) rootView.findViewById(R.id.searchViewFloat);
            numberPicker.setCallBack(this);
            this.adapterUbicacion = new AdapterPlantillaUbicacion(this.context, itemArrayList);
            listViewItems.setAdapter(adapterUbicacion);
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        alertDialog.cancel();
                        searchViewFloat.clearFocus();
                        callBack.onCancel();
                    }
                }
            });
            searchViewFloat.setOnQueryTextListener(this);
            listViewItems.setOnItemClickListener(this);
            alertDialog.show();
            updateInterfazIndicadores(itemArrayList);
        }
        else{
            alertDialog.show();
            listViewItems.setAdapter(adapterUbicacion);
            updateInterfazIndicadores(itemArrayList);

        }
    }

    public boolean isShowing(){
        return alertDialog!=null &&  alertDialog.isShowing();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (callBack != null && alertDialog!=null) {
                Ubicaciones item = adapterUbicacion.getItem(position);
                callBack.onResult(item);
                alertDialog.dismiss();
            }

    }

    public void setItemArrayList(ArrayList<Ubicaciones> itemArrayList) {
        if(!itemArrayList.isEmpty()) {
            this.itemArrayList = itemArrayList;
            if (this.adapterUbicacion != null) {
                this.adapterUbicacion.setLisUbicaciones(this.itemArrayList);
            }
            else{
                this.adapterUbicacion=new AdapterPlantillaUbicacion(this.context,itemArrayList);
            }
        }
        updateInterfazIndicadores(itemArrayList);
    }
    private void updateInterfazIndicadores(ArrayList<Ubicaciones>itemArrayList){
        IndicadorUbicacion indicadorUbicacion=new IndicadorUbicacion();
        indicadorUbicacion.setTotalSocios(itemArrayList.size());
        for(Ubicaciones movimiento: itemArrayList){
            indicadorUbicacion.setByMovimiento(movimiento);
        }
        if(tvValidos!=null && tvTotal!=null && tvOtros!=null) {
            tvValidos.setText(String.valueOf(indicadorUbicacion.getValidos()));
            tvTotal.setText(String.valueOf(indicadorUbicacion.getTotalSocios()));
            tvOtros.setText(String.valueOf(indicadorUbicacion.getOtros()));
            tvNoActualizados.setText(String.valueOf(indicadorUbicacion.getNoActualizados()));
        }
    }

    @Override
    public void onValueChanged(int value) {
        if(callBack!=null){
            callBack.OnValueNumberPickerChanged(value);
        }
    }

    public void enabledNumberPicker(){
        this.numberPicker.enabled();
    }
    public void disabledNumberPicker(){
        this.numberPicker.disabled();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(!newText.isEmpty()) {
            adapterUbicacion.getFilter().filter(newText);
        }
        else{
            adapterUbicacion.setLisUbicaciones(itemArrayList);
        }
        return  true;
    }

}
