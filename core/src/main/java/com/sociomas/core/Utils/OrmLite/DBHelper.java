    package com.sociomas.core.Utils.OrmLite;

    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.util.Log;


    import com.j256.ormlite.android.apptools.OpenHelperManager;
    import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
    import com.j256.ormlite.dao.Dao;
    import com.j256.ormlite.support.ConnectionSource;
    import com.j256.ormlite.table.TableUtils;
    import com.sociomas.core.DataBaseModel.Actividad;
    import com.sociomas.core.DataBaseModel.Configuracion;
    import com.sociomas.core.DataBaseModel.Empleado;
    import com.sociomas.core.DataBaseModel.Legal;
    import com.sociomas.core.DataBaseModel.ObtenerEstatusAlerta;
    import com.sociomas.core.DataBaseModel.PanicoLog;
    import com.sociomas.core.DataBaseModel.PanicoT;
    import com.sociomas.core.DataBaseModel.Plantilla;
    import com.sociomas.core.DataBaseModel.QrPendientes;
    import com.sociomas.core.DataBaseModel.RangoMonitoreo;
    import com.sociomas.core.DataBaseModel.RegistroGPS;

    import java.sql.SQLException;

    @SuppressWarnings("unused")
    public class DBHelper extends OrmLiteSqliteOpenHelper {
        private static final String TAG 				= "DB_HELPER";
        private static final String DATABASE_NAME 		= "movilidad_gs.db";
        private static final int 	DATABASE_VERSION 	=17;
        private Dao<Empleado, Integer> empleadoDAO;
        private Dao<RegistroGPS, Integer> gpsDAO;
        private Dao<QrPendientes,Integer>qrPendientesDao;
        private Dao<Configuracion, Integer> configuracionDAO;
        private Dao<RangoMonitoreo, Integer> rangoMonitoreoDAO;
        private Dao<ObtenerEstatusAlerta,Integer> obtenerEstatusAlertasDAO;
        private Dao<Actividad,Integer>actividadDAO;
        private Dao<PanicoT,Integer>panicoDao;
        private Dao<PanicoLog,Integer>panicoLogDao;
        private Dao<Plantilla,Integer>plantillaDao;
        private Dao<Legal,Integer>legalDao;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
            try {

                    TableUtils.createTableIfNotExists(connectionSource, Empleado.class);
                    TableUtils.createTableIfNotExists(connectionSource, RegistroGPS.class);
                    TableUtils.createTableIfNotExists(connectionSource, Configuracion.class);
                    TableUtils.createTableIfNotExists(connectionSource, RangoMonitoreo.class);
                    TableUtils.createTableIfNotExists(connectionSource, ObtenerEstatusAlerta.class);
                    TableUtils.createTableIfNotExists(connectionSource, Actividad.class);
                    TableUtils.createTableIfNotExists(connectionSource,PanicoT.class);
                    TableUtils.createTableIfNotExists(connectionSource,PanicoLog.class);
                    TableUtils.createTableIfNotExists(connectionSource,QrPendientes.class);
                    TableUtils.createTableIfNotExists(connectionSource,Plantilla.class);
                    TableUtils.createTableIfNotExists(connectionSource,Legal.class);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
        {
                onCreate(db,connectionSource);
        }

        public Dao<PanicoLog,Integer>getPanicoLogDao() throws SQLException {
            if(panicoLogDao==null){
                panicoLogDao=getDao(PanicoLog.class);
            }
            return panicoLogDao;
        }

        public Dao<QrPendientes,Integer>getQrDao() throws  SQLException{
            if(qrPendientesDao==null){
                qrPendientesDao=getDao(QrPendientes.class);
            }
            return qrPendientesDao;
        }

        public Dao<Plantilla,Integer>getPlantillaDao() throws  SQLException{
            if(plantillaDao==null){
                plantillaDao=getDao(Plantilla.class);
            }
            return plantillaDao;
        }

        public Dao<Legal,Integer>getLegalDao() throws  SQLException{
            if(legalDao==null){
                legalDao=getDao(Legal.class);
            }
            return legalDao;
        }


        public Dao<PanicoT,Integer>getPanicoDao() throws  SQLException{
            if(panicoDao==null){
                panicoDao=getDao(PanicoT.class);
            }
            return panicoDao;
        }

        public Dao<Actividad,Integer>getActividadDAO() throws  SQLException{
            if(actividadDAO==null){
                actividadDAO=getDao(Actividad.class);
            }
            return actividadDAO;
        }

        public Dao<Empleado, Integer> getEmpleadoDAO() throws SQLException {
            if (empleadoDAO == null) {
                empleadoDAO = getDao(Empleado.class);
            }
            return empleadoDAO;
        }

        public Dao<RegistroGPS, Integer> getGpsDAO() throws SQLException {
            if (gpsDAO == null) {
                gpsDAO = getDao(RegistroGPS.class);
            }
            return gpsDAO;
        }

        public Dao<Configuracion, Integer> getConfiguracionDao() throws SQLException {
            if (configuracionDAO == null) {
                configuracionDAO = getDao(Configuracion.class);
            }
            return configuracionDAO;
        }

        public Dao<RangoMonitoreo, Integer> getRangosMonitoreoDao() throws SQLException {
            if (rangoMonitoreoDAO == null) {
                rangoMonitoreoDAO = getDao(RangoMonitoreo.class);
            }
            return rangoMonitoreoDAO;
        }
        public Dao<ObtenerEstatusAlerta,Integer> getObtenerEstatusAlertasDAO()throws SQLException{
            if(obtenerEstatusAlertasDAO==null){
                obtenerEstatusAlertasDAO=getDao(ObtenerEstatusAlerta.class);
            }
            return obtenerEstatusAlertasDAO;
        }

        @Override
        public void close() {
            super.close();
            empleadoDAO = null;
            gpsDAO 		= null;
        }

        //Borra la tabla de Empleado
        public void dropEmpleado(ConnectionSource connectionSource, int table){
            if(table == 0){
                try{
                    TableUtils.dropTable(connectionSource, Empleado.class, true);
                    Log.d(TAG, "Se ha borrado la tabla de Empleado");
                }catch(Exception e){
                    Log.e(TAG, "No se pudo borrar la tabla de Empleado");
                }
            }
        }

        //Borra la tabla de GPS
        public void dropGps(ConnectionSource connectionSource, int table){
            if(table == 0){
                try{
                    TableUtils.dropTable(connectionSource, RegistroGPS.class, true);
                    Log.d(TAG, "Se ha borrado la tabla de GPS Status");
                }catch(Exception e){
                    Log.e(TAG, "No se pudo borrar la tabla de GPS Status");
                }
            }
        }

      //Borra la tabla de Configuracion
        public void dropConfiguracion(ConnectionSource connectionSource, int table){
            if(table == 0){
                try{
                    TableUtils.dropTable(connectionSource, Configuracion.class, true);
                    Log.d(TAG, "Se ha borrado la tabla de Configuracion");
                }catch(Exception e){
                    Log.e(TAG, "No se pudo borrar la tabla de Configuracion");
                }
            }
        }

      //Borra la tabla de RangoMonitoreo
        public void dropRangoMonitoreo( int table){
            if(table == 0){
                try{
                    TableUtils.dropTable(connectionSource, RangoMonitoreo.class, true);
                    Log.d(TAG, "Se ha borrado la tabla de RangoMonitoreo");
                }catch(Exception e){
                    Log.e(TAG, "No se pudo borrar la tabla de RangoMonitoreo");
                }
            }
        }


        public static DBHelper getHelper(Context context, DBHelper dbHelper) {
            if (dbHelper == null) {
                dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
            }
            return dbHelper;
        }
    }