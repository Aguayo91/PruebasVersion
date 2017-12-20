package com.sociomas.core.WebService.CallBacks;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;

import java.util.ArrayList;

/**
 * Created by oemy9 on 01/07/2017.
 */

public interface CallBackListadoPlantilla extends CallBackBase {
    void OnSuccess(ArrayList<SearchBoxItem> listadoPlantilla);
}
