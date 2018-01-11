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

    private static final String PREF_IS_LOGINED = "IsLogined";

    private static String PREF_LOGIN_ACCESS_TOKEN = "Login";
    private static Integer PREF_EXPIRES_IN = 0;

    private Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public LoginManager(Context context) {
        this.mContext = context;
    }

    public void setPrefLoginAccessToken(String prefLoginAccessToken) {
        pref = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(PREF_LOGIN_ACCESS_TOKEN, prefLoginAccessToken);
        editor.apply();
    }

    public String getPrefLoginAccessToken() {
        SharedPreferences prefAccessToken = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        return prefAccessToken.getString(PREF_LOGIN_ACCESS_TOKEN, null);
    }

    public void setPrefLoginUserKey(String userKey){
        pref = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(PREF_LOGIN_ACCESS_TOKEN, userKey);
        editor.apply();
    }

    public String getPrefLoginUserKey(){
        SharedPreferences getRefreshToken = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        return getRefreshToken.getString(PREF_LOGIN_ACCESS_TOKEN, null);
    }

    public void setPrefLoginRefreshToken(String prefLoginRefreshToken) {
        pref = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(PREF_LOGIN_ACCESS_TOKEN, prefLoginRefreshToken);
        editor.apply();
    }

    public String getPrefLoginRefreshToken() {
        SharedPreferences getRefreshToken = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        return getRefreshToken.getString(PREF_LOGIN_ACCESS_TOKEN, null);
    }

    public void setPrefLoginTokenType(String prefLoginTokenType) {
        pref = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(PREF_LOGIN_ACCESS_TOKEN, prefLoginTokenType);
        editor.apply();
    }

    public String getPrefLoginTokenType() {
        SharedPreferences getRefreshToken = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        return getRefreshToken.getString(PREF_LOGIN_ACCESS_TOKEN, null);
    }

    public void setPrefLoginExpiresIn(Integer prefLoginExpiresIn) {
        editor.putInt(PREF_LOGIN_ACCESS_TOKEN, prefLoginExpiresIn);
        editor.apply();
    }

    public Integer getPrefLoginExpiresIn() {
        SharedPreferences getRefreshToken = mContext.getSharedPreferences(PREF_LOGIN_ACCESS_TOKEN, Context.MODE_PRIVATE);
        return getRefreshToken.getInt(PREF_LOGIN_ACCESS_TOKEN, 0);
    }

    public void setPrefIsLogined(String isLogined){
        pref = mContext.getSharedPreferences(PREF_IS_LOGINED, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(PREF_IS_LOGINED, true);
        editor.apply();
    }

    public boolean isLogined(){
        SharedPreferences isLogined = mContext.getSharedPreferences(PREF_IS_LOGINED, Context.MODE_PRIVATE);
        return isLogined.getBoolean(PREF_IS_LOGINED, false);
    }

}
