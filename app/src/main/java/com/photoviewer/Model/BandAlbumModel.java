package com.photoviewer.Model;

/**
 * Created by user on 2017. 12. 20..
 */

public class BandAlbumModel {

    private int result_code;    // 결과 값 코드

    private String photo_album_key; //앨범 식별자

    private String name;    //앨범명

    private int photo_count;    //앨범 내 사진 수

    private Long created_at;    //앨범 생성일시

    private Object author;  // 앨범 생성자 정보

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

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

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }
}
