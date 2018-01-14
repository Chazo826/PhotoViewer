package com.photoviewer.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user on 2018. 1. 14..
 */

public class RequestCodeModel implements Serializable {

    @SerializedName("result_code")
    private String result_code;

    @SerializedName("previous_params")
    private String previous_params;

    @SerializedName("next_params")
    private String next_params;


    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getPrevious_params() {
        return previous_params;
    }

    public void setPrevious_params(String previous_params) {
        this.previous_params = previous_params;
    }

    public String getNext_params() {
        return next_params;
    }

    public void setNext_params(String next_params) {
        this.next_params = next_params;
    }
}
