package com.photoviewer.NetworkManager;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.photoviewer.Model.AuthorizationInfo;
import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.Model.BandUserProfileModel;
import com.photoviewer.Utils.BandListManager;
import com.photoviewer.Utils.Pref;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

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

    //액세스토큰 밴드이름key 필요
    public void getBandAlbumList(Consumer<BandAlbumModel> consumer, String bandKey) {
        mCompositeDisposable.add(
                bandService.getUserBandsAlbums(deliverAccessToken(), deliverBandKey(bandKey))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer));
    }

    //    private void getBandPhotoList(int bandAlbumIndex) {
//        mCompositeDisposable.add(
//                bandService.getUserBandsPhotos(deliverAccessToken(), deliverBandKey(bandListIndex), deliverPhotoAlbumKey(bandAlbumIndex))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<BandPhotoModel>() {
//                            @Override
//                            public void accept(BandPhotoModel bandPhotoModel) throws Exception {
//                                saveJsonToPref(bandPhotoModel);
//                            }
//                        }));
//    }



    public void saveJsonToPref(Object modelObject) {
        if (modelObject != null) {
            if (modelObject instanceof AuthorizationInfo) {
                pref.putJson(Pref.ACCESS_TOKEN_KEY, modelObject);
            } else if (modelObject instanceof BandAlbumModel) {

            } else {

            }
        }
    }

    private String deliverAccessToken() {
        AuthorizationInfo authorizationInfo = pref.getObject(Pref.ACCESS_TOKEN_KEY, null, AuthorizationInfo.class);
        return authorizationInfo.getAccess_token();
    }

    private String deliverBandKey(String name) {
        return pref.getObject(Pref.BAND_LIST_KEY + name, null, BandListModel.class).getBand_key();
    }

    private String deliverPhotoAlbumKey(int bandAlbumIndex) {
        return pref.getObject(Pref.BAND_LIST_KEY + bandAlbumIndex, null, BandListModel.class).getBand_key();
    }

    private void saveJsonArrayToPref(JsonArray jsonArray) {
        for (int index = 0; index < jsonArray.size(); index++) {
            pref.putString(Pref.BAND_LIST_KEY + index, jsonArray.get(index).getAsString());
        }
    }

}
