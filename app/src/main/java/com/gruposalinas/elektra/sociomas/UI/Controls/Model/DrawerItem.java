package com.gruposalinas.elektra.sociomas.UI.Controls.Model;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;

/**
 * Created by oemy9 on 27/07/2017.
 */

public class DrawerItem {

    private String description;
    private int icon;
    private boolean isTitle;
    private Class<? extends BaseAppCompactActivity> activityNavegacion;

    public DrawerItem(String description, int icon,Class<? extends BaseAppCompactActivity> activityNavegacion) {
        this.description = description;
        this.icon=icon;
        this.activityNavegacion=activityNavegacion;
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

    public Class<? extends BaseAppCompactActivity> getActivityNavegacion() {
        return activityNavegacion;
    }

    public void setActivityNavegacion(Class<? extends BaseAppCompactActivity> activityNavegacion) {
        this.activityNavegacion = activityNavegacion;
    }
}
