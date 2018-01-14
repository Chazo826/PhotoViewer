package com.photoviewer.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.List;

/**
 * Created by user on 2018. 1. 11..
 */

public class BandListModel implements Serializable {


    @SerializedName("name") private String name;   //밴드 이름

    @SerializedName("band_key") private String band_key; // 밴드 식별자

    @SerializedName("cover") private String cover; // 밴드 커버 이미지 url

    @SerializedName("member_count") private Integer member_count; // 밴드의 멤버 수


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBand_key() {
        return band_key;
    }

    public void setBand_key(String band_key) {
        this.band_key = band_key;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getMember_count() {
        return member_count;
    }

    public void setMember_count(Integer member_count) {
        this.member_count = member_count;
    }



}
