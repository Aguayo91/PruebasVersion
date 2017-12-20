package com.sociomas.core.WebService.Model.Response.Configuracion;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 14/07/2017.
 */

public class EmpleadoSettingResponse extends ServerResponse {
    private Configuraciones configuraciones;

    public Configuraciones getConfiguraciones() { return this.configuraciones; }

    public void setConfiguraciones(Configuraciones configuraciones) { this.configuraciones = configuraciones; }

    private ArrayList<ExclusionCount> exclusiones;

    public ArrayList<ExclusionCount> getExclusiones() { return this.exclusiones; }

    public void setExclusiones(ArrayList<ExclusionCount> exclusiones) { this.exclusiones = exclusiones; }

    private ArrayList<RMonitoreoList> rangosMonitoreo;

    public ArrayList<RMonitoreoList> getRangosMonitoreo() { return this.rangosMonitoreo; }

    public void setRangosMonitoreo(ArrayList<RMonitoreoList> rangosMonitoreo) { this.rangosMonitoreo = rangosMonitoreo; }
}
