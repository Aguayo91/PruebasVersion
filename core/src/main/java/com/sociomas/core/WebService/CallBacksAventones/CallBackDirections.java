package com.sociomas.core.WebService.CallBacksAventones;

import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Response.Directions.DirectionResponse;

/**
 * Created by oemy9 on 29/08/2017.
 */

public interface CallBackDirections extends CallBackBase {
    void OnSuccess(DirectionResponse response);
}
