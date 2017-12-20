package com.sociomas.core.WebService.CallBacks;
import com.sociomas.core.DataBaseModel.PanicoLog;
import com.sociomas.core.WebService.Model.Request.SOS.SosRequest;

/**
 * Created by oemy9 on 11/08/2017.
 */
public interface CallBackSOSWebService {
    void onResponse(PanicoLog panicoLog, SosRequest currentRequest);
    //void onResponse(PanicoLog panicoLog, SosRequest currentRequest);
}
