
package com.sociomas.core.WebService.Model.Request.Publicar;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.PublicaAventon.CoordenadasIda;
import com.sociomas.core.WebService.Model.Response.PublicaAventon.CoordenadasVueltum;

public class PublicarRequest extends ServerRequest {


    @SerializedName("tipoAventon")
    @Expose
    private TipoAventon tipoAventon;
    @SerializedName("trayecto")
    @Expose
    private Trayecto trayecto;
    @SerializedName("recurrente_ida")
    @Expose
    private List<RecurrenteIda> recurrenteIda = null;
    @SerializedName("recurrente_vuelta")
    @Expose
    private List<RecurrenteVuelta> recurrenteVuelta = null;
    @SerializedName("coordenadas_ida")
    @Expose
    private List<CoordenadasIda> coordenadasIda = null;
    @SerializedName("coordenadas_vuelta")
    @Expose
    private List<CoordenadasVueltum> coordenadasVuelta = null;

    public TipoAventon getTipoAventon() {
        return tipoAventon;
    }

    public void setTipoAventon(TipoAventon tipoAventon) {
        this.tipoAventon = tipoAventon;
    }

    public Trayecto getTrayecto() {
        return trayecto;
    }

    public void setTrayecto(Trayecto trayecto) {
        this.trayecto = trayecto;
    }

    public List<RecurrenteIda> getRecurrenteIda() {
        return recurrenteIda;
    }

    public void setRecurrenteIda(List<RecurrenteIda> recurrenteIda) {
        this.recurrenteIda = recurrenteIda;
    }

    public List<RecurrenteVuelta> getRecurrenteVuelta() {
        return recurrenteVuelta;
    }

    public void setRecurrenteVuelta(List<RecurrenteVuelta> recurrenteVuelta) {
        this.recurrenteVuelta = recurrenteVuelta;
    }

    public List<CoordenadasIda> getCoordenadasIda() {
        return coordenadasIda;
    }

    public void setCoordenadasIda(List<CoordenadasIda> coordenadasIda) {
        this.coordenadasIda = coordenadasIda;
    }

    public List<CoordenadasVueltum> getCoordenadasVuelta() {
        return coordenadasVuelta;
    }

    public void setCoordenadasVuelta(List<CoordenadasVueltum> coordenadasVuelta) {
        this.coordenadasVuelta = coordenadasVuelta;
    }

}
