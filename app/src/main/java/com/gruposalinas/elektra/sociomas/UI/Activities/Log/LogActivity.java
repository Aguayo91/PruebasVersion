package com.gruposalinas.elektra.sociomas.UI.Activities.Log;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DbUtils.GPSBDHelper;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Log.AdapterLog;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Log.AdapterLogPanico;
import com.sociomas.core.Utils.OrmLite.DBHelper;

import com.j256.ormlite.dao.Dao;
import com.sociomas.core.DataBaseModel.PanicoLog;
import com.sociomas.core.DataBaseModel.RegistroGPS;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class LogActivity extends BaseAppCompactActivity  {

    private SwipeRefreshLayout refreshLayout,refreshLayoutPanico;
    private ListView listViewLog,listViewPanico;
    private List<RegistroGPS>listTodos;
    private List<PanicoLog>listPanico;
    private TabHost tabHost;
    private Dao dao;
    private DBHelper dbHelper = null;
    private TextView tvZonaHoraria;
    private TextView tvUltimaSincronizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        this.setToolBar("Log");
        this.refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        this.refreshLayoutPanico=(SwipeRefreshLayout)findViewById(R.id.refreshLayoutPanico);
        this.tvZonaHoraria=(TextView)findViewById(R.id.tvZonaHoraria);
        this.tvUltimaSincronizacion=(TextView)findViewById(R.id.tvUltimaSincronización);
        this.refreshLayoutPanico.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateListViewPanico();
                refreshLayoutPanico.setRefreshing(false);
            }
        });
        this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateListviewUbicaciones(new Date());
                refreshLayout.setRefreshing(false);

            }
        });

        this.listViewPanico=(ListView)findViewById(R.id.listViewPanico);
        this.listViewLog=(ListView)findViewById(R.id.listViewLog);

        this.tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec(getString(R.string.log_ubicacione));
        spec.setContent(R.id.tab1);
        spec.setIndicator(getString(R.string.log_ubicacione));
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec(getString(R.string.log_panico));
        spec.setContent(R.id.tab2);
        spec.setIndicator(getString(R.string.log_panico));
        tabHost.addTab(spec);

        this.updateListviewUbicaciones(new Date());
        this.updateListViewPanico();
    }

    private void updateListviewUbicaciones(Date fechaFiltro){
            GPSBDHelper gpsbdHelper=new GPSBDHelper(this);
            listTodos =gpsbdHelper.getByDate(fechaFiltro!=null? fechaFiltro: new Date());
            String zonaHorario=manager.getString(Constants.TIME_ZONE_NAME)!=null? manager.getString(Constants.TIME_ZONE_NAME): TimeZone.getDefault().getID();
            String ultimaSincronizacion=manager.getString(Constants.LAST_TIME_ZONE_UPDATE)!=null?manager.getString(Constants.LAST_TIME_ZONE_UPDATE):"SIN SINCRONIZACIÓN";
            tvZonaHoraria.setText(Html.fromHtml(getString(R.string.zona_horaria_formato,zonaHorario)));
            tvUltimaSincronizacion.setText(Html.fromHtml(getString(R.string.ultima_sincronizacion_formato,ultimaSincronizacion)));
            AdapterLog adapterLog=new AdapterLog(this,(ArrayList)listTodos);
            if(gpsbdHelper.getNoSincronizados().size()>0){
                Toast.makeText(this,"NO SINCRONIZADOS: "+gpsbdHelper.getNoSincronizados().size() ,Toast.LENGTH_LONG).show();
            }
            if(listTodos.size()==0){
                Toast.makeText(this, "SIN REGISTROS PARA EL DÍA "+
                        new SimpleDateFormat(Constants.DAY_FORMAT).format(fechaFiltro)
                        ,Toast.LENGTH_LONG).show();
            }
            listViewLog.setAdapter(adapterLog);

    }
    private void updateListViewPanico(){
        try{
            dao=DBHelper.getHelper(this,dbHelper).getPanicoLogDao();
            listPanico=dao.queryForAll();
            AdapterLogPanico adapterLogPanico=new AdapterLogPanico(this,(ArrayList<PanicoLog>)listPanico);
            listViewPanico.setAdapter(adapterLogPanico);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_filtro:
                showPickerAsync();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showPickerAsync(){
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendarDia=Calendar.getInstance();
                calendarDia.set(year,month,dayOfMonth);
                updateListviewUbicaciones(calendarDia.getTime());
            }
        }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
