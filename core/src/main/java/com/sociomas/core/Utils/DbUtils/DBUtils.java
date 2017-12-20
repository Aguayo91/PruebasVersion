package com.sociomas.core.Utils.DbUtils;
import android.content.Context;
import android.util.Log;

import com.sociomas.core.DataBaseModel.Legal;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;
import com.sociomas.core.Utils.OrmLite.DBHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.sociomas.core.DataBaseModel.PanicoLog;
import com.sociomas.core.DataBaseModel.RangoMonitoreo;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Configuracion.RMonitoreoList;
import com.sociomas.core.WebService.Model.Response.Registro.Empleado;
import com.sociomas.core.WebService.Model.Response.Registro.Team;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by oemy9 on 14/07/2017.
 */

public class DBUtils {

    public static final String TAG="DB_UTILS";
    protected Context context;
    protected Dao dao;
    protected DBHelper mDBHelper;

    public DBUtils(Context context) {
        this.context = context;
    }

    public List<Legal>getListLegal(){
        List<Legal>legals= null;
        try {
            dao=DBHelper.getHelper(this.context,mDBHelper).getLegalDao();
            legals = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return legals;
    }

    /**
     * Guarda el html de los términos legales
     * @param tiposAviso
     * @param html
     */
    public void agregarTerminoLegal(EnumTiposAviso tiposAviso, String html,String titulo,String subtitulo, double version){
        try {
            dao=DBHelper.getHelper(this.context,mDBHelper).getLegalDao();
            List<Legal>legals=dao.queryForAll();
            //Ya existen los términos
            if(legals!=null && (!legals.isEmpty())){
                int id=legals.get(0).getId();
                Legal l=legals.get(0);
                l.setSubtitulo(subtitulo);
                l.setVersion(version);
                switch (tiposAviso){
                    case POLITICAS_PRIVACIDAD:
                        l.setPivacidad(html);
                        l.setTituloPrivacidad(titulo);
                        break;
                    case TERMINOS_CONDICIONES:
                        l.setTerminos(html);
                        l.setTituloTerminos(titulo);
                        break;
                }
                dao.update(l);
            }
            //Se crean los términos
            else{
                Legal l=new Legal();
                l.setVersion(version);
                l.setSubtitulo(subtitulo);
                switch (tiposAviso){
                    case POLITICAS_PRIVACIDAD:
                        l.setPivacidad(html);
                        l.setTituloPrivacidad(titulo);
                        break;
                    case TERMINOS_CONDICIONES:
                        l.setTerminos(html);
                        l.setTituloTerminos(titulo);
                        break;
                }
                dao.create(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void agregarPlantillaEmpledo(Team t){
        try {
            dao=DBHelper.getHelper(this.context,mDBHelper).getPlantillaDao();
            Plantilla p=new Plantilla(t.getNumeroEmpleado(),t.getNombreCompleto());
            dao.create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Plantilla>obtenerEmpleadoPlantilla(){
        List<Plantilla>listPlantilla=new ArrayList<>();
        try{
            dao=DBHelper.getHelper(this.context,mDBHelper).getPlantillaDao();
            listPlantilla=dao.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPlantilla;
    }


    public  List<com.sociomas.core.DataBaseModel.Empleado>obtenerEmpleado(){
        List<com.sociomas.core.DataBaseModel.Empleado>listEmpleado=new ArrayList<>();
        try{
            dao=DBHelper.getHelper(this.context,mDBHelper).getEmpleadoDAO();
            listEmpleado=dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEmpleado;
    }

    public boolean  hasPlantilla() {
        return obtenerEmpleadoPlantilla() != null && (!obtenerEmpleadoPlantilla().isEmpty());
    }



    public void agregarEmpleadoIsPiloto(String numeroEmpleado){
        try {
            dao = DBHelper.getHelper(this.context, mDBHelper).getEmpleadoDAO();
            List<com.sociomas.core.DataBaseModel.Empleado> empleados=dao.queryForEq(com.sociomas.core.DataBaseModel.Empleado.ID,numeroEmpleado);
            Log.i(TAG, "Usuario creado exitosamente");
        } catch (SQLException e) {
            Log.e(TAG, "Error creando usuario");
        }
    }




    public void agregarEmpleado(Empleado empleado){
            com.sociomas.core.DataBaseModel.Empleado empleadoBD=
                new com.sociomas.core.DataBaseModel.Empleado();
        empleadoBD.setIdEmployee(empleado.getIdNumEmpleado());
        empleadoBD.setName(empleado.getVaNombreCompleto());
        empleadoBD.setEnterprise(empleado.getIdEmpresa());
        empleadoBD.setIMEI(Utils.getDeviceID(this.context));
        empleadoBD.setPhoneNumber(empleado.getVaNumeroTelefono());
        try {
            dao = DBHelper.getHelper(this.context, mDBHelper).getEmpleadoDAO();
            dao.create(empleadoBD);
            Log.i(TAG, "Usuario creado exitosamente");
        } catch (SQLException e) {
            Log.e(TAG, "Error creando usuario");
        }

    }

    /*AGREGA NUEVO REGISTRO AL LOG DE PANICO*/
    public void agregarLogPanico(PanicoLog panicoLog){
        try {
            dao=DBHelper.getHelper(this.context,mDBHelper).getPanicoLogDao();
            dao.create(panicoLog);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*REVISA SI ES NECESARIO REALIZAR MÁS PETICIONES A PANICO O ESTÁ COMPLETADO EL PROCESO*/
    public boolean panicoCompleted(String fecha){
        boolean completado=false;
        try {
            dao=DBHelper.getHelper(this.context,mDBHelper).getPanicoLogDao();
            List<PanicoLog> logs=dao.queryForEq(PanicoLog.FECHA,fecha);
            completado=logs!=null && (logs.size()==5);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return completado;
    }


    public void agregarHorarioEmpleado(ArrayList<RMonitoreoList>listHorarios){
        ArrayList<RangoMonitoreo>rangosMonitoreo=new ArrayList<>();
        if(listHorarios!=null && (!listHorarios.isEmpty())) {
            for (RMonitoreoList item : listHorarios) {
                if (item.getTmHoraInicialString() != null && item.getTmHoraFinalString() != null) {
                    RangoMonitoreo rango = new RangoMonitoreo();
                    rango.setNumeroDia(item.getIntNumeroDia());
                    rango.setHoraFinal(item.getTmHoraFinalString());
                    rango.setHoraInicial(item.getTmHoraInicialString());
                    rango.setExclusion(false);
                    rangosMonitoreo.add(rango);
                }
            }
            try {
                dao = DBHelper.getHelper(this.context, mDBHelper).getRangosMonitoreoDao();
                List<RangoMonitoreo> rangosBd = dao.queryForAll();
            /*EXISTEN ACTUALMENTE RANGOS DE MONITOREO*/
                if (rangosBd.size() > 0) {
                    DeleteBuilder deleteBuilder = dao.deleteBuilder();
                    deleteBuilder.setWhere(deleteBuilder.where().eq(RangoMonitoreo.IS_EXCLUSION, false));
                    dao.delete(deleteBuilder.prepare());
                }
                dao.create(rangosMonitoreo);
                Log.i(TAG, "CREADO CORRECTAMENTE");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
