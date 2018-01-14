package com.photoviewer.NetworkManager;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.photoviewer.Model.AuthorizationInfo;
import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.Utils.Pref;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by user on 2018. 1. 10..
 */

public class RequestRetrofitFactory {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private BandService tokenService = ApiFactory.getInstance().getAuthToken();
    private BandService bandService = ApiFactory.getInstance().getBandService();

    private Pref pref = Pref.getInstance();


    public void getRequestRetrofit(String received_authorization_code, Consumer<AuthorizationInfo> consumer) {
        mCompositeDisposable.add(
                tokenService.getAuthCodeForLogin(received_authorization_code,
                        ApiFactory.getInstance().getBase64Encode())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer));

    }

    public void getBandListRetrofit(Consumer<JsonObject> consumer) {
        mCompositeDisposable.add(
                bandService.getUserBandList(deliverAccessToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer));

    }

    public void getBandAlbumList(Consumer<JsonObject> consumer, String bandKey) {
        mCompositeDisposable.add(
                bandService.getUserBandsAlbums(deliverAccessToken(), bandKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer));
    }


    public void getBandPhotoList(Consumer<JsonObject> consumer, String bandKey,String albumKey) {
        mCompositeDisposable.add(
                bandService.getUserBandsPhotos(deliverAccessToken(), bandKey, albumKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer));
    }

    public void saveJsonToPref(Object modelObject) {
        if (modelObject != null) {
            if (modelObject instanceof AuthorizationInfo) {
                pref.putJson(Pref.ACCESS_TOKEN_KEY, modelObject);
            } else if (modelObject instanceof BandAlbumModel) {
                pref.putJson(Pref.BAND_ALBUM_KEY, modelObject);
            } else {
                pref.putJson(Pref.BAND_PHOTO_KEY, modelObject);
            }
        }
    }

    private String deliverAccessToken() {
        AuthorizationInfo authorizationInfo = pref.getObject(Pref.ACCESS_TOKEN_KEY, null, AuthorizationInfo.class);
        return authorizationInfo.getAccess_token();
    }

    private String delieverBandKey(){
        BandListModel bandListModel = pref.getObject(Pref.BAND_LIST_KEY, null, BandListModel.class);
        return bandListModel.getBand_key();
    }

}
