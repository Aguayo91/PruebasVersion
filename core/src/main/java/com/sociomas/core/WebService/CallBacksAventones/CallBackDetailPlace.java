package com.sociomas.core.WebService.CallBacksAventones;

import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Response.DetailPlace.DetailResponse;

/**
 * Created by oemy9 on 28/08/2017.
 */

public interface CallBackDetailPlace extends CallBackBase {
    void onSuccess(DetailResponse response);
}
