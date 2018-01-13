package com.photoviewer.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;


/**
 * Created by user on 2018. 1. 6..
 */

public class LoginManager {
    public static final String PREF_LOGIN_ACCESS_TOKEN = "Login";
    public static final String ACCESS_TOKEN_KEY = "access_token_key";

    private static LoginManager loginManager;

    private SharedPreferences pref;

    private LoginManager(){}

    public static LoginManager getInstance() {
        if(loginManager == null){
            loginManager = new LoginManager();
        }
        return loginManager;
    }

    private void setPref(Context context){
        pref = context.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
    }

    public void setContext(Context context){
        if(pref == null){
            setPref(context.getApplicationContext());
        }
    }

    public void putJson(String key, Object o){
        Gson gson = new Gson();
        String json = gson.toJson(o);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, json);
        editor.apply();
    }

//    public String getJson(String key, String dft){
//
//    }

    public <T> T getObject(String key, String dft, Class<T> klass){
        Gson gson = new Gson();
        String json = pref.getString(key, dft);
        return gson.fromJson(json, klass);
    }

    public void putString(String key, String val){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,val);
        editor.apply();
    }

    public void putInteger(String key, Integer val){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key,val);
        editor.apply();
    }

    public void putBoolean(String key, Boolean val){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key,val);
        editor.apply();
    }

    public String getString(String key, String dfv){
        return pref.getString(key, dfv);
    }

    public int getInt(String key, int dfv){
        return pref.getInt(key, 0);
    }

    public boolean getInt(String key, boolean dfv){
        return pref.getBoolean(key, dfv);
    }
}
