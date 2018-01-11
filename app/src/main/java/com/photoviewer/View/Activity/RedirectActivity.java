package com.photoviewer.View.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.photoviewer.NetworkManager.CheckAuthRepository;
import com.photoviewer.Utils.LoginManager;


/**
 * Created by ssion.dev on 2017. 12. 28..
 *
 */

public class RedirectActivity extends BaseActivity {
    private final String TAG = RedirectActivity.class.getSimpleName();

    private CheckAuthRepository checkAuthRepository = new CheckAuthRepository();
    private LoginManager loginManager = LoginManager.getInstance(getApplicationContext());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if(appLinkData.toString().contains("authorize")){
            startActivity(new Intent(appLinkAction, appLinkData));
            finish();
        } else {
            //로그인 토큰이 있는 경우
            if(appLinkData.toString().contains("code")){
                String auth_token = appLinkData.getQueryParameter("code");
                checkAuthRepository.checkAuthToken(auth_token);

                if(checkAuthRepository.authSaveComplete().contains("complete")){
                    startActivity(new Intent(RedirectActivity.this, MainActivity.class));
                    loginManager.putBoolean("isLogined", true);
                    finish();
                } else {
                    startActivity(new Intent(RedirectActivity.this, LoginActivity.class));
                    finish();
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
