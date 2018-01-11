package com.photoviewer.Model;

import java.net.Inet4Address;
import java.util.List;

/**
 * Created by user on 2018. 1. 11..
 */

public class BandListModel {

    private Integer result_code;    //결과코드

    private String band_name;   //밴드 이름

    private String band_key; // 밴드 식별자

    private String band_cover; // 밴드 커버 이미지 url

    private Integer member_count; // 밴드의 멤버 수

    public BandListModel(Integer result_code) {
        this.result_code = result_code;
    }

    public Integer getResult_code() {
        return result_code;
    }

    public void setResult_code(Integer result_code) {
        this.result_code = result_code;
    }

    public String getBand_name() {
        return band_name;
    }

    public void setBand_name(String band_name) {
        this.band_name = band_name;
    }

    public String getBand_key() {
        return band_key;
    }

    public void setBand_key(String band_key) {
        this.band_key = band_key;
    }

    public String getBand_cover() {
        return band_cover;
    }

    public void setBand_cover(String band_cover) {
        this.band_cover = band_cover;
    }

    public Integer getMember_count() {
        return member_count;
    }

    public void setMember_count(Integer member_count) {
        this.member_count = member_count;
    }



}
