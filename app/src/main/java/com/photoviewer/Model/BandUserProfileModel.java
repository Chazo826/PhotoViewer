package com.photoviewer.Model;

/**
 * Created by user on 2018. 1. 11..
 */

public class BandUserProfileModel {

    private int result_code;

    private String user_key;    //사용자 식별자

    private String profile_image_url;   //프로필 이미지 url

    private String name;    //사용자 이름

    private boolean is_app_member;  //앱 연동 여부


    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_app_member() {
        return is_app_member;
    }

    public void setIs_app_member(boolean is_app_member) {
        this.is_app_member = is_app_member;
    }
}
