package com.sociomas.core.MVP;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;

import java.util.Map;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */

public class BasePresenterImpl implements BasePresenter {

    BaseView view;
    private Map<String, Object> data;

    @Override
    public void notifyData(ViewEvent event) {
        this.view.presentEvent(event);

    }

    @Override
    public void setArguments(Intent intent) {

    }

    @Override
    public void setArguments(Bundle bundle) {

    }

    @Override
    public BaseView getView() {
        return view;
    }

    @Override
    public Object getDataObject(String key) {
        return data.get(key);
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void register(BaseView view) {
        this.view = view;
        this.view.initView();
        this.view.setListeners();
    }

    @Override
    public void unregister(BaseView view) {
        if (this.view == view)
            this.view = null;
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onShowProgress() {
        ViewEvent event=new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(event);
    }

    @Override
    public void onHideProgress() {
        ViewEvent event=new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
        notifyData(event);
    }

    @Override
    public void onShowAlert(@StringRes int res) {
        onShowAlert(getView().getActivityInstance().getString(res));
    }

    @Override
    public void onShowToast(String mensaje) {
        ViewEvent event=new ViewEvent(ViewEventType.SHOW_TOAST_MESSAGE);
        event.getModel().put(ViewEvent.MESSAGE,mensaje);
        notifyData(event);
    }

    @Override
    public void onShowToast(@StringRes int res) {
        if(getView()!=null && (getView().getActivityInstance()!=null)) {
            onShowToast(getView().getActivityInstance().getString(res));
        }
    }

    @Override
    public void registerControllerAventon(ControllerAventon controllerAventon) {

    }

    @Override
    public void onShowAlert(String mensaje) {
        ViewEvent event=new ViewEvent(ViewEventType.SHOW_SUCCESS_MESSAGE);
        event.getModel().put(ViewEvent.MESSAGE,mensaje);
        notifyData(event);
    }

}
