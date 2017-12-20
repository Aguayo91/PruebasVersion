package com.sociomas.core.MVP;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;

import java.util.Map;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */
public interface BasePresenter {
    void notifyData(ViewEvent event);

    void setArguments(Intent intent);

    void setArguments(Bundle bundle);


    BaseView getView();

    Object getDataObject(String key);

    Map<String, Object> getData();


    void onCreate(@Nullable final Bundle savedInstanceState);

    void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    void register(BaseView view);

    void unregister(BaseView view);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(@NonNull final Bundle outState);

    void onDestroy();

    void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data);

    void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults);


    void onShowProgress();

    void onHideProgress();

    void onShowAlert(String mensaje);

    void onShowAlert(@StringRes  int res);

    void onShowToast(String mensaje);

    void onShowToast(@StringRes int res);

    void registerControllerAventon(ControllerAventon controllerAventon);

    /*
    ControllerAPI getControllerApi();

    SessionManager getManager();*/

}
