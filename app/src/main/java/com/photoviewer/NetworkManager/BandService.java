package com.photoviewer.NetworkManager;

import com.google.gson.JsonObject;
import com.photoviewer.Model.AuthorizationInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by ssion.dev
 * Band Open Api를 GET 방식으로 호출하기 위한 인터페이스
 */

public interface BandService {
//타입 확인
    @GET("oauth2/token?grant_type=authorization_code")
    Observable<AuthorizationInfo> getAuthCodeForLogin(@Query("code") String code, @Header("Authorization") String authorization);

    @GET("v2.1/bands")
    Observable<JsonObject> getUserBandList(@Query("access_token") String accessToken);

    @GET("v2/band/albums")
    Observable<JsonObject> getUserBandsAlbums(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2/band/album/photos")
    Observable<JsonObject> getUserBandsPhotos(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @Query("photo_album_key") String photoAlbumKey);

}
