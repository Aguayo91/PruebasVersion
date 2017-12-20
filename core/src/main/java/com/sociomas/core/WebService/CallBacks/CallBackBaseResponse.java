package com.sociomas.core.WebService.CallBacks;

import com.sociomas.core.WebService.Model.Response.ServerResponse;

/**
 * Created by oemy9 on 06/10/2017.
 */

public interface CallBackBaseResponse extends CallBackBase {
    void onSuccess(ServerResponse response);
}
