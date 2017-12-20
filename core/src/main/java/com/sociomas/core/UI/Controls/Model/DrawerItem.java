package com.sociomas.core.UI.Controls.Model;

import android.support.annotation.DrawableRes;

import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;

/**
 * Created by oemy9 on 27/07/2017.
 */

public class DrawerItem {

    private String description;
    private int icon;
    private boolean isTitle;
    private String actionName;
    private boolean visible;
    private Class<? extends BaseCoreCompactActivity> activityNavegacion;


    public DrawerItem(String description, @DrawableRes int icon, String actionName, boolean visible){
        this.description=description;
        this.icon=icon;
        this.actionName=actionName;
        this.visible=visible;
    }

    public DrawerItem(String description, int icon,Class<? extends BaseCoreCompactActivity> activityNavegacion) {
        this.description = description;
        this.icon=icon;
       // this.activityNavegacion=activityNavegacion;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public boolean isTitle() {
        return isTitle;
    }
    public void setTitle(boolean title) {
        isTitle = title;
    }
    public Class<? extends BaseCoreCompactActivity> getActivityNavegacion() {
        return activityNavegacion;
    }
    public void setActivityNavegacion(Class<? extends BaseCoreCompactActivity> activityNavegacion) {
        this.activityNavegacion = activityNavegacion;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
