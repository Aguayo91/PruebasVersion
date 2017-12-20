package com.sociomas.core.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by oemy9 on 01/11/2017.
 */
@DatabaseTable(tableName = "plantilla")
public class Plantilla implements Serializable {
    public static final String  IS_MIO="is_mio";
    public static final String ID_EMPLEADO="id_empleado";
    public static final String NOMBRE_EMPLEADO="numero_empleadoo";
    @DatabaseField(columnName = ID_EMPLEADO, id = true)
    private String idEmpleado;
    @DatabaseField(columnName = NOMBRE_EMPLEADO)
    private String nombreEmpleado;

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Plantilla(String idEmpleado, String nombreEmpleado) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
    }
    public Plantilla() {
    }
    @Override
    public boolean equals(Object p) {
        return   this.getNombreEmpleado().equalsIgnoreCase(((Plantilla)p).getNombreEmpleado());
    }
}
