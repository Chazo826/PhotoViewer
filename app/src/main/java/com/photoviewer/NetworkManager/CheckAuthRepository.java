package com.photoviewer.NetworkManager;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.photoviewer.Model.AuthorizationInfo;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Utils.BandListManager;
import com.photoviewer.Utils.LoginManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by user on 2018. 1. 10..
 *
 */

public class CheckAuthRepository {

    private LoginManager loginManager;
    private BandListManager bandListManager;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private BandService bandService = ApiFactory.getInstance().getBandService();

    private static final String COMPLETE = "complete";

    public void checkAuthToken(String auth_token){
        if (auth_token != null) {
            getRequestRetrofit(auth_token);
        }
    }

    private void getRequestRetrofit(String received_authorization_code) {
        mCompositeDisposable.add(
                bandService.getAuthCodeForLogin(received_authorization_code,
                        ApiFactory.getInstance().getBase64Encode())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AuthorizationInfo>() {
                            @Override
                            public void accept(AuthorizationInfo authorizationInfo) throws Exception {
                               saveAuthInfoToPref(authorizationInfo);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                //startLoginActivity
                            }
                        }));

    }

    private void saveAuthInfoToPref(AuthorizationInfo authorizationInfo) {
        loginManager.setPrefLoginAccessToken(authorizationInfo.getAccess_token());
        loginManager.setPrefLoginRefreshToken(authorizationInfo.getRefresh_token());
        loginManager.setPrefLoginUserKey(authorizationInfo.getUser_key());
        loginManager.setPrefLoginExpiresIn(authorizationInfo.getExpires_in());
        loginManager.setPrefLoginTokenType(authorizationInfo.getToken_type());

        authSaveComplete();
    }

    public String deliverAccessToken(){
        return loginManager.getPrefLoginAccessToken();
    }

    public void getBandListRetrofit(String access_token) {
        mCompositeDisposable.add(
                bandService.getUserBandList(access_token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BandListModel>() {
                            @Override
                            public void accept(BandListModel bandListModel) throws Exception {
                                saveBandListPref(bandListModel);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                //startLoginActivity
                            }
                        }));

    }

    private void saveBandListPref(BandListModel bandListModel) {
        bandListModel.setResult_code(bandListModel.getResult_code());
        bandListModel.setBand_name(bandListModel.getBand_name());
        bandListModel.setBand_key(bandListModel.getBand_key());
        bandListModel.setBand_cover(bandListModel.getBand_cover());
        bandListModel.setMember_count(bandListModel.getMember_count());

        Log.d(TAG, bandListModel.getResult_code() + bandListModel.getBand_name());
        authSaveComplete();
    }

    public String authSaveComplete(){

        return COMPLETE;
    }
}
