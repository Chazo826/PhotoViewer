package com.photoviewer.Model;

/**
 * Created by user on 2017. 12. 20..
 */

public class AuthorizationInfo {

    private String access_token;    //접근 권한이 있는 토큰

    private String user_key;    // 유저 식별키

    private String refresh_token;   //토큰 만료 시 발급되는 재사용 토큰

    private String token_type;  // 토큰 타입

    private Integer expires_in;    // 토큰 유효기간

    public AuthorizationInfo(String access_token, String user_key, String refresh_token, String token_type, Integer expires_in) {
        this.access_token = access_token;
        this.user_key = user_key;
        this.refresh_token = refresh_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
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
