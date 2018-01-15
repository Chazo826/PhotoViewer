package com.photoviewer.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user on 2017. 12. 20..
 */

public class BandPhotoModel implements Serializable {

    @SerializedName("photo_key")
    private String photo_key;   //사진 식별자

    @SerializedName("url")
    private String url; //사진 URL

    @SerializedName("width")
    private int width;  //사진 넓이

    @SerializedName("height")
    private int height; //사진 높이

    @SerializedName("created_at")
    private Long created_at;    //생성 일시

    public String getPhoto_key() {
        return photo_key;
    }

    public void setPhoto_key(String photo_key) {
        this.photo_key = photo_key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }
}
