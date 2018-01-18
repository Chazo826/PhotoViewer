package com.photoviewer.NetworkManager;

import com.google.gson.JsonObject;
import com.photoviewer.Model.AuthorizationInfo;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ssion.dev
 * Band Open Api를 GET 방식으로 호출하기 위한 인터페이스
 */

public interface BandService {

    @GET("oauth2/token?grant_type=authorization_code")
    Single<AuthorizationInfo> getAuthCodeForLogin(@Query("code") String code, @Header("Authorization") String authorization);

    @GET("v2.1/bands")
    Single<JsonObject> getUserBandList(@Query("access_token") String accessToken);

    @GET("v2/band/albums")
    Single<JsonObject> getUserBandsAlbums(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2/band/album/photos")
    Single<JsonObject> getUserBandsPhotos(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @Query("photo_album_key") String photoAlbumKey);

    @GET("v2/band")
    Observable<JsonObject> getNextParams(@Query("band_key") String bandKey, @Query("access_token") String accessToken);

}
