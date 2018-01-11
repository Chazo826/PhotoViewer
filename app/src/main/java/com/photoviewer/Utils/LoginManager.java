package com.photoviewer.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.photoviewer.View.Activity.BaseActivity;
import com.photoviewer.View.Activity.LoginActivity;
import com.photoviewer.View.Activity.MainActivity;


/**
 * Created by user on 2018. 1. 6..
 */

public class LoginManager {
    private static String PREF_LOGIN_ACCESS_TOKEN = "Login";

    private static LoginManager loginManager;

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    private LoginManager(Context mContext){
        pref = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
    }

    public static LoginManager getInstance(Context context) {
        if(loginManager == null){
            loginManager = new LoginManager(context.getApplicationContext());
        }
        return loginManager;
    }

    public static LoginManager getInstance(){
        if(loginManager != null){
            return loginManager;
        }
        throw new IllegalArgumentException("Should user getInstance at least once");
    }

    public void putString(String key, String val){
        editor = pref.edit();
        editor.putString(key,val);
        editor.apply();
    }

    public void putInteger(String key, Integer val){
        editor = pref.edit();
        editor.putInt(key,val);
        editor.apply();
    }

    public void putBoolean(String key, Boolean val){
        editor = pref.edit();
        editor.putBoolean(key,val);
        editor.apply();
    }

    public String getString(String key, String dfv){
        return pref.getString(key, null);
    }

    public int getInt(String key, int dfv){
        return pref.getInt(key, 0);
    }

    public boolean getInt(String key, boolean dfv){
        return pref.getBoolean(key, dfv);
    }
}
