package com.photoviewer.NetworkManager;

import android.util.Log;

import com.google.gson.JsonObject;
import com.photoviewer.Model.AuthorizationInfo;
import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.Model.BandUserProfileModel;
import com.photoviewer.Utils.BandListManager;
import com.photoviewer.Utils.LoginManager;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2018. 1. 10..
 *
 */

public class RequestRetrofitFactory {

    private BandListManager bandListManager;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private BandService tokenService = ApiFactory.getInstance().getAuthToken();
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
                tokenService.getAuthCodeForLogin(received_authorization_code,
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
                        }));

    }

    private void saveAuthInfoToPref(AuthorizationInfo authorizationInfo) {
        loginManager.putString(authorizationInfo.getAccess_token(),null);
        Log.d("하이하이", loginManager.getString(authorizationInfo.getAccess_token(),null));
        authSaveComplete();
    }


    private void getUserProfileRetrofit(){
        mCompositeDisposable.add(
                bandService.getUserProfile(deliverAccessToken(), deliverBandKey())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BandUserProfileModel>() {
                            @Override
                            public void accept(BandUserProfileModel bandUserProfileModel) throws Exception {
                                saveUserProfilePref(bandUserProfileModel);
                            }
                        }));
    }

    private void saveUserProfilePref(BandUserProfileModel bandUserProfileModel) {
        bandUserProfileModel.setResult_code(bandUserProfileModel.getResult_code());
        bandUserProfileModel.setUser_key(bandUserProfileModel.getUser_key());
        bandUserProfileModel.setProfile_image_url(bandUserProfileModel.getProfile_image_url());
        bandUserProfileModel.setName(bandUserProfileModel.getName());
        bandUserProfileModel.setIs_app_member(bandUserProfileModel.isIs_app_member());

        authSaveComplete();
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

    private void getBandAlbumList(){
        mCompositeDisposable.add(
                bandService.getUserBandsAlbums(deliverAccessToken(),deliverBandKey())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BandAlbumModel>() {
                            @Override
                            public void accept(BandAlbumModel bandAlbumModel) throws Exception {
                                saveAlbumListPref(bandAlbumModel);
                            }
                        }));
    }

    private void saveAlbumListPref(BandAlbumModel bandAlbumModel){
        bandAlbumModel.setResult_code(bandAlbumModel.getResult_code());
        bandAlbumModel.setPhoto_album_key(bandAlbumModel.getPhoto_album_key());
        bandAlbumModel.setName(bandAlbumModel.getName());
        bandAlbumModel.setPhoto_count(bandAlbumModel.getPhoto_count());
        bandAlbumModel.setCreated_at(bandAlbumModel.getCreated_at());
        bandAlbumModel.setAuthor(bandAlbumModel.getAuthor());

        authSaveComplete();
    }

    private void getBandPhotoList(){
        mCompositeDisposable.add(
                bandService.getUserBandsPhotos(deliverAccessToken(),deliverBandKey(), deliverPhotoAlbumKey())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BandPhotoModel>() {
                            @Override
                            public void accept(BandPhotoModel bandPhotoModel) throws Exception {
                                savePhotoListPref(bandPhotoModel);
                            }
                        }));
    }

    private void savePhotoListPref(BandPhotoModel bandPhotoModel){
        bandPhotoModel.setResult_code(bandPhotoModel.getResult_code());
        bandPhotoModel.setPhoto_key(bandPhotoModel.getPhoto_key());
        bandPhotoModel.setUrl(bandPhotoModel.getUrl());
        bandPhotoModel.setWidth(bandPhotoModel.getWidth());
        bandPhotoModel.setHeight(bandPhotoModel.getHeight());
        bandPhotoModel.setCreated_at(bandPhotoModel.getCreated_at());

        authSaveComplete();
    }

    private String deliverAccessToken(){
        return loginManager.getString("access_token",null);
    }

    private String deliverBandKey(){
        return loginManager.getString("band_key",null);
    }

    private String deliverPhotoAlbumKey(){
        return loginManager.getString("photo_album_key",null);
    }

}
