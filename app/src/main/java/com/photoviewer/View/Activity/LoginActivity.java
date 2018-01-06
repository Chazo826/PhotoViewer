package com.photoviewer.View.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.photoviewer.Model.BandUserModel;
import com.photoviewer.NetworkManager.ApiFactory;
import com.photoviewer.NetworkManager.BandService;
import com.photoviewer.R;
import com.photoviewer.Utils.Constant;
import com.photoviewer.ViewModel.LoginViewModel;
import com.photoviewer.databinding.ActivityLoginBinding;

import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ssion.dev
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private BandService bandService;
    private LoginViewModel loginViewModel = new LoginViewModel();

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bandService = ApiFactory.getInstance().getBandService();

        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        BandUserModel bandUserModel = new BandUserModel();

        activityLoginBinding.setBandUserModel(bandUserModel);
        activityLoginBinding.setLoginActivity(this);

    }

    public void checkAuthorize(View view){
        Intent intent = new Intent(LoginActivity.this, RedirectActivity.class);

        intent.setData(urlParseTask());
        intent.setAction(Intent.ACTION_VIEW);

        startActivity(intent);
    }

    public Uri urlParseTask(){
        return Uri.parse(
                String.format("https://auth.band.us/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                        Constant.CLIENT_ID,
                        Constant.REDIRECT_URL, "utf-8"));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
