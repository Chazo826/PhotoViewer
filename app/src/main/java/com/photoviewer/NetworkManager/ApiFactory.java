package com.photoviewer.NetworkManager;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.photoviewer.Utils.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ssion.dev
 */

public class ApiFactory {


    private static ApiFactory instance = new ApiFactory();

    public static ApiFactory getInstance() { return  instance; }

    private BandService bandService;
    private OkHttpClient okHttpClient;


    public BandService getBandService() {
        Retrofit retrofit = createRetroFit();
        return retrofit.create(BandService.class);
    }

    private Gson createGsonBuilder(){
        return new GsonBuilder().setLenient().create();
    }

    private Retrofit createRetroFit(){
        okHttpClient = createOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.AUTH_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGsonBuilder()))
                .build();
    }


    private OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", getBase64Encode())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        okHttpClient = httpClientBuilder
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

    private String getBase64Encode(){
        int client_id = Constant.CLIENT_ID;
        String client_secret = Constant.CLIENT_SECRET;

        String before_encode = client_id + ":" + client_secret;
        return "BASIC " + Base64.encodeToString(before_encode.getBytes(), 0);
    }
}
