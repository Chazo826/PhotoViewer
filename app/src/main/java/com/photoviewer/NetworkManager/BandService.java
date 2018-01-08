package com.photoviewer.NetworkManager;

import com.google.gson.JsonObject;
import com.photoviewer.Model.BandUserModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by ssion.dev
 */

public interface BandService {

    @GET("oauth2/authorize")
    Observable<JsonObject> getAuthFromRedirectURL(@Query("response_type") String code, @Query("client_id") Integer userId, @Query("redirect_uri") String redirectURL);

    @GET("oauth2/token")
    Observable<JsonObject> getAuthCodeForLogin(@Query("code") String code, @Query("grant_type") String grantType, @Header("Authorization") String authorization);

    @GET("v2/profile")
    Observable<JsonObject> getUserProfile(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2.1/bands")
    Observable<JsonObject> getUserBandList(@Query("access_token") String accessToken);

    @GET("v2/band/albums")
    Observable<JsonObject> getUserBandsAlbums(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2/band/album/photos")
    Observable<JsonObject> getUserBandsPhotos(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @Query("photo_album_key") String photoAlbumKey);

}
