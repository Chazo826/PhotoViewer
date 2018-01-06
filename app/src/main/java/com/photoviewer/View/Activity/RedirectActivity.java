package com.photoviewer.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.photoviewer.NetworkManager.ApiFactory;
import com.photoviewer.NetworkManager.BandService;
import com.photoviewer.Utils.Constant;
import com.photoviewer.Utils.Preference;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2017. 12. 28..
 */

public class RedirectActivity extends AppCompatActivity {

    private final String TAG = RedirectActivity.class.getSimpleName();
    private final String AUTH_CODE_URL = "code";
    private BandService bandService;

    private Preference pref = new Preference(this);

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        bandService = ApiFactory.getInstance().getBandService();
    }

    private void handleIntent(Intent intent){
        String appLinkAction = intent.getAction();
        if(Intent.ACTION_VIEW.equals(appLinkAction)){
            Uri appLinkData = intent.getData();
            String auth_token_uri = appLinkData.toString();

                if(auth_token_uri.contains(Constant.AUTH_URL)){
                    //인증 전이면 인증페이지로
                    startActivity(new Intent(appLinkAction,appLinkData));
                } else {
                    //인증코드있으면 메인으로
                    if(getAuthCode(appLinkData)){
                        startActivity(new Intent(RedirectActivity.this, MainActivity.class));
                    }

                }
        }
    }

    public boolean getAuthCode(Uri uri){
        String auth_token = uri.getQueryParameter("code");
        if(auth_token != null){
            pref.put(Preference.PREF_LOGIN_AUTH_CODE, auth_token);
            urlParseTask(auth_token);
        }
        return true;
    }

    public Uri urlParseTask(String auth_url){
        return Uri.parse(
                String.format("https://auth.band.us/oauth2/token?grant_type=authorization_code&code=%s",
                        pref.getValue(Preference.PREF_LOGIN_AUTH_CODE, auth_url), "utf-8"));
    }

}
