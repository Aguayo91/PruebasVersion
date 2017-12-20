package com.sociomas.core.WebService.Model.Response.Notificaciones;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 08/12/17.
 */

public class NotificacionInfoResponse implements Serializable {

    @SerializedName("body")
    private String body;

    @SerializedName("title")
    private String title;

    @SerializedName("sound")
    private String sound;

    @SerializedName("click_action")
    private String action;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "NotificacionInfoResponse{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", sound='" + sound + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
