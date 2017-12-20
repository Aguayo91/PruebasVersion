package com.sociomas.core.Listeners;
import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Response.AutoComplete.PredictionSections;

import java.util.ArrayList;

/**
 * Created by oemy9 on 05/09/2017.
 */

public interface OnPredictionListener extends CallBackBase {
    void onPredictionResponse(ArrayList<PredictionSections> listPrediction);
    void onInputClean(boolean clean);
}
