package com.sociomas.core.WebService.CallBacks;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;

import java.util.ArrayList;

/**
 * Created by oemy9 on 19/05/2017.
 */

public interface CallBackPermiso extends CallBackBase {
    void OnSuccessIOS(ArrayList<ListExclusiones> responsePermiso);
}
