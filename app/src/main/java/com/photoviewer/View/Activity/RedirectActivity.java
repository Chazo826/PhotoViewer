package com.photoviewer.View.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.gson.JsonObject;
import com.photoviewer.NetworkManager.ApiFactory;
import com.photoviewer.NetworkManager.BandService;
import com.photoviewer.Store.Constant;
import com.photoviewer.Store.PreferenceFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2017. 12. 28..
 */

public class RedirectActivity extends AppCompatActivity {

    private final String TAG = RedirectActivity.class.getSimpleName();
    private BandService bandService;

    private PreferenceFactory pref = new PreferenceFactory(this);

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bandService = ApiFactory.getInstance().getBandService();
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        if (Intent.ACTION_VIEW.equals(appLinkAction)) {
            Uri appLinkData = intent.getData();
            String auth_token_uri = appLinkData.toString();

            if (checkAuthToken(appLinkData) != false) {
                startActivity(new Intent(RedirectActivity.this, MainActivity.class));
                Log.d(TAG,"메인엑티비티 부름");
            } else {
                if (auth_token_uri.contains(Constant.AUTH_URL)) {
                    startActivity(new Intent(appLinkAction, appLinkData));
                    Log.d(TAG,"인증페이지 다시 부름");
                    finish();
                }
            }
        }
    }

    public boolean checkAuthToken(Uri uri){
        String auth_token = uri.getQueryParameter("code");
        if(auth_token != null){
            pref.put(PreferenceFactory.PREF_LOGIN_AUTH_CODE, auth_token);
            getTokenRequestURL(auth_token);
            Log.d(TAG,"getTokenReqiestURL 부름");
            return true;
        } else {
            Log.d(TAG,"This user doesn't have auth code.");
        }
        return false;
    }

    public Uri getTokenRequestURL(String received_authorization_code){
        Log.d(TAG,"getTokenReqiestURL 들어왓음");
        Log.d(TAG,"레트로핏연결 부름");

        getRequestRetrofit(received_authorization_code);
        return Uri.parse(
                String.format("https://auth.band.us/oauth2/token?grant_type=%s&code=%s",
                        Constant.GRANT_TYPE_FOR_API_REQUEST,
                        pref.getValue(PreferenceFactory.PREF_LOGIN_AUTH_CODE, received_authorization_code), "utf-8"));
    }

    public void getRequestRetrofit(String received_authorization_code){
        Log.d(TAG,"레트로핏연결 들어왓음");

        Observable<JsonObject> observable =
                bandService.getAuthCodeForLogin(received_authorization_code,
                        Constant.GRANT_TYPE_FOR_API_REQUEST,
                        ApiFactory.getInstance().getBase64Encode());

        mCompositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>() {
                            @Override
                            public void onNext(JsonObject token) {
                                Log.d(TAG,"onNext");
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.getMessage();
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG,"connect complete");
                            }
                        }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
