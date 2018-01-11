package com.photoviewer.NetworkManager;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.JsonObject;
import com.photoviewer.Model.AuthorizationInfo;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Utils.BandListManager;
import com.photoviewer.Utils.LoginManager;

import io.reactivex.Observable;
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

    private BandListManager bandListManager;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private BandService bandService = ApiFactory.getInstance().getBandService();
    private LoginManager loginManager = LoginManager.getInstance();

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
                                authorizationInfo.setAccess_token(authorizationInfo.getRefresh_token());
                                authorizationInfo.setUser_key(authorizationInfo.getUser_key());
                                authorizationInfo.setRefresh_token(authorizationInfo.getRefresh_token());
                                authorizationInfo.setToken_type(authorizationInfo.getToken_type());
                                authorizationInfo.setExpires_in(authorizationInfo.getExpires_in());
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
        loginManager.putString(authorizationInfo.getAccess_token(),null);
        authSaveComplete();
    }

    private String deliverAccessToken(){
        return loginManager.getString("access_token",null);
    }

    public void getBandListRetrofit() {
        mCompositeDisposable.add(
                bandService.getUserBandList(deliverAccessToken())
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

        authSaveComplete();
    }

    public String authSaveComplete(){

        return COMPLETE;
    }
}
