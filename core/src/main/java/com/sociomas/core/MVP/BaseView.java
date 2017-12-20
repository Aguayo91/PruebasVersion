package com.sociomas.core.MVP;

import android.app.Activity;

import com.sociomas.core.MVP.ViewEvent;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */

public interface BaseView {
    void initView();
    void setListeners();
    void setPresenter();
    void presentEvent(ViewEvent event);
    Activity getActivityInstance();
}
