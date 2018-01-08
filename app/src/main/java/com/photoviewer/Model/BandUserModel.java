package com.photoviewer.Model;


/**
 * Created by user on 2017. 12. 20..
 */

public class BandUserModel {

    /* For Oauth token */
    private String response_type;
    private String location; //여기서 정보받아야함

    private String received_auth_code;
    private Integer client_id;

    /* For user's profile information */
    private String access_token;
    private String band_key;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getReceived_auth_code(){return received_auth_code;}

    public void setReceived_auth_code(String received_auth_code) {
        this.received_auth_code = received_auth_code;
    }

    public String getResponse_Type() {
        return response_type;
    }

    public void setResponse_Type(String response_type) {
        this.response_type = response_type;
    }


    public Integer getClientId() {
        return client_id;
    }

    public void setClientId(Integer client_id) { this.client_id = client_id; }


    public String getAccessToken() { return access_token; }

    public void setAccessToken(String access_token) { this.access_token = access_token; }


    public String getBandKey() { return band_key; }

    public void setBandKey(String band_key) { this.band_key = band_key; }

}
