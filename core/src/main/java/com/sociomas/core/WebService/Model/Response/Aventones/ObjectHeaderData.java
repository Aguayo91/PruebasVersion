package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 10/10/2017.
 */

class ObjectHeaderData implements Serializable {

    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private String value;

    @SerializedName("description")
    private String description;

    @SerializedName("enabled")
    private boolean enabled;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "ObjectHeaderData{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
