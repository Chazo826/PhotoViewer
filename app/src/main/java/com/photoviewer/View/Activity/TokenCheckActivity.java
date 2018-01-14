package com.photoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.photoviewer.Model.AuthorizationInfo;
import com.photoviewer.Utils.Pref;

/**
 * token check 후 인텐트 넘기는 역할만 수행하는 액티비티
 */


public class TokenCheckActivity extends BaseActivity {

    private static final String TAG = TokenCheckActivity.class.getSimpleName();

    private Pref pref = Pref.getInstance();
    private AuthorizationInfo authorizationInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref.setContext(this);
        checkUserTokenExist();
    }

    public void checkUserTokenExist() {
        authorizationInfo = pref.getObject(Pref.ACCESS_TOKEN_KEY, null, AuthorizationInfo.class);

        if(authorizationInfo != null){
            //로그인 토큰이 있는 경우 -> 종료 후 메인
            startActivity(new Intent(TokenCheckActivity.this, MainActivity.class));
            finish();
        } else {
            //로그인 토큰이 없는 경우 -> 로그인 화면
            startActivity(new Intent(TokenCheckActivity.this, LoginActivity.class));
            finish();
        }
    }
}
