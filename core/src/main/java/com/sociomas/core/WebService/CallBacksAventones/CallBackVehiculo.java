package com.sociomas.core.WebService.CallBacksAventones;
import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jromeromar on 22/09/2017.
 */

public interface CallBackVehiculo extends CallBackBase {
    void onSucces(List<Vehiculo> listVehiculo);
}
