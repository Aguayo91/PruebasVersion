package com.sociomas.core.WebService.CallBacksAventones;
import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Request.Alta.ServerResponseAventones;

/**
 * Created by jromeromar on 26/09/2017.
 */

public interface CallBackEditarAuto extends CallBackBase {
    void onSuccess(ServerResponseAventones response);

}
