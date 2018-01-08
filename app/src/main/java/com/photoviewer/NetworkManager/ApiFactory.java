package com.photoviewer.NetworkManager;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.photoviewer.Store.Constant;
import com.photoviewer.Store.PreferenceFactory;

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
    private PreferenceFactory preferenceFactory;

    public BandService getBandService() {
        Retrofit retrofit = createRetroFit();
        return retrofit.create(BandService.class);
    }

    private Gson createGsonBuilder(){
        return new GsonBuilder().setLenient().create();
    }

    private Retrofit createRetroFit(){
        Log.d(this.getClass().getSimpleName(),"createRetrofit 들어옴");

        okHttpClient = createOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.AUTH_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGsonBuilder()))
                .build();
    }


    private OkHttpClient createOkHttpClient() {
        Log.d(this.getClass().getSimpleName(),"createOkHttpClient 들어옴");

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

    public String getBase64Encode(){
        Log.d(this.getClass().getSimpleName(),"base64Encode 들어옴");

        String combine_for_encode = Constant.CLIENT_ID + ":" + Constant.CLIENT_SECRET;
        return "BASIC " + Base64.encodeToString(combine_for_encode.getBytes(), 0);
    }

}