package com.sociomas.core.Listeners;

import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;

/**
 * Created by oemy9 on 31/08/2017.
 */
public interface DialogDirectionCompletedListener {
    void onDialogCompletedListener(Prediction selectedItem);
    void onPickerDirectionListener(boolean pickerDirectionLaunch);
}
