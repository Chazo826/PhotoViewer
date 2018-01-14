package com.photoviewer.Model;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by user on 2017. 12. 20..
 */

public class BandAlbumModel implements Serializable, Comparable<BandAlbumModel> {

    @SerializedName("photo_album_key") private String photo_album_key; //앨범 식별자

    @SerializedName("name") private String name;    //앨범명

    @SerializedName("photo_count") private int photo_count;    //앨범 내 사진 수

    @SerializedName("created_at") private Long created_at;    //앨범 생성일시


    public String getPhoto_album_key() {
        return photo_album_key;
    }

    public void setPhoto_album_key(String photo_album_key) {
        this.photo_album_key = photo_album_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto_count() {
        return photo_count;
    }

    public void setPhoto_count(int photo_count) {
        this.photo_count = photo_count;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    @Override
    public int compareTo(@NonNull BandAlbumModel bandAlbumModel) {
        return getCreated_at().compareTo(bandAlbumModel.getCreated_at());
    }
}
