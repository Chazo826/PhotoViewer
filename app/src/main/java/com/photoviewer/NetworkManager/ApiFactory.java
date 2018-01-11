package com.photoviewer.NetworkManager;

import android.util.Base64;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.photoviewer.Utils.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ssion.dev
 */

public class ApiFactory {
    private static ApiFactory instance = new ApiFactory();

    public static ApiFactory getInstance() { return  instance; }

    public BandService getAuthToken() {
        Retrofit retrofit = createRetrofitForToken();
        return retrofit.create(BandService.class);
    }

    public BandService getBandService() {
        Retrofit retrofit = createRetrofitForAll();
        return retrofit.create(BandService.class);
    }

    private Retrofit createRetrofitForToken(){
        OkHttpClient okHttpClient = createOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.AUTH_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Retrofit createRetrofitForAll(){
        OkHttpClient okHttpClient = createOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

    public String getBase64Encode(){
        String combine_for_encode = Constant.CLIENT_ID + ":" + Constant.CLIENT_SECRET;
        return "Basic " + Base64.encodeToString(combine_for_encode.getBytes(), Base64.URL_SAFE).trim();
    }

}