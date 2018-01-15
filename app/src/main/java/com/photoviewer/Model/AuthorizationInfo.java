package com.photoviewer.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user on 2017. 12. 20..
 */

public class AuthorizationInfo implements Serializable {

    @SerializedName("access_token")
    private String access_token;    //접근 권한이 있는 토큰

    @SerializedName("token_type")
    private String token_type;  // 토큰 타입

    @SerializedName("refresh_token")
    private String refresh_token;   //토큰 만료 시 발급되는 재사용 토큰

    @SerializedName("expires_in")
    private Integer expires_in;    // 토큰 유효기간

    @SerializedName("user_key")
    private String user_key;    // 유저 식별키


    public AuthorizationInfo(String access_token, String token_type, String refresh_token, Integer expires_in, String user_key) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.user_key = user_key;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getUser_key() {
        return user_key;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }


    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }


}
