package com.sociomas.core.Utils.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.sociomas.core.Utils.Constants;


/**
 * Created by oemy9 on 13/03/2017.
 */

public class SessionManager {
    private Context ctx;
    private String currentSharedPreference;
    public SessionManager(Context ctx) {
        this.ctx = ctx;
        this.currentSharedPreference=Constants.SHARED_PREFERENCES;
    }

    public SessionManager(Context ctx,String currentSharedPreference){
        this.ctx=ctx;
        this.currentSharedPreference=currentSharedPreference;
    }

    public void removeAll(){
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public void add(String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void addString(String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void add(String key, int value) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void add(String key, Boolean value) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void add(String key,long value)
    {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key,value);
        editor.commit();
    }

    public boolean get(String key) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }
    public boolean get(String key, Boolean def){
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        return pref.getBoolean(key, def);
    }

    public int getInt(String key) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public int getInt(String key, int defaut) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        return pref.getInt(key, defaut);
    }

    public String getString(String key) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        return pref.getString(key, null);
    }
    public String getString(String key,String defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(this.currentSharedPreference, Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }
    public long getLong(String key){
        SharedPreferences pref=ctx.getSharedPreferences(this.currentSharedPreference,0);
        return  pref.getLong(key,0);
    }

}
