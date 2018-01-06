package com.photoviewer.NetworkManager;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by ssion.dev
 */

public interface BandService {

    @GET("oauth2/authorize")
    Observable<JsonObject> getUserCode(@Query("response_type") String code, @Query("client_id") Integer userId, @Query("redirect_uri") String redirectURL);

    @Headers({ "Authorization" })
    @GET("oauth2/token")
    Observable<JsonObject> getLoginResponse(@Query("code") String received_auth_code);

    @GET("v2/profile")
    Observable<JsonObject> getUserProfile(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2.1/bands")
    Observable<JsonObject> getUserBands(@Query("access_token") String accessToken);

    @GET("v2/band/albums")
    Observable<JsonObject> getUserBandsAlbums(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2/band/album/photos")
    Observable<JsonObject> getUserBandsPhotos(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @Query("photo_album_key") String photoAlbumKey);

}
