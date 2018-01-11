package com.photoviewer.View.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.photoviewer.R;
import com.photoviewer.Utils.Constant;
import com.photoviewer.Utils.LoginManager;
import com.photoviewer.databinding.ActivityLoginBinding;

import java.net.URLEncoder;

import io.reactivex.annotations.NonNull;

/**
 * Created by ssion.dev
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setLoginActivity(this);
    }

    public void startActivity(View view) {
        Intent intent = new Intent();

        intent.setData(getRedirectURL());
        intent.setAction(Intent.ACTION_VIEW);

        startActivity(intent);
        finish();
    }

    public Uri getRedirectURL(){
        return Uri.parse(
                String.format("https://auth.band.us/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                        Constant.CLIENT_ID,
                        URLEncoder.encode(Constant.REDIRECT_URL), "utf-8"));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
