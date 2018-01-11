package com.photoviewer.NetworkManager;

import com.google.gson.JsonObject;
import com.photoviewer.Model.AuthorizationInfo;
import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.Model.BandUserProfileModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by ssion.dev
 * Band Open Api를 GET 방식으로 호출하기 위한 인터페이스
 */

public interface BandService {

    @GET("oauth2/token?grant_type=authorization_code")
    Observable<AuthorizationInfo> getAuthCodeForLogin(@Query("code") String code, @Header("Authorization") String authorization);

    @GET("v2/profile")
    Observable<BandUserProfileModel> getUserProfile(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2.1/bands")
    Observable<BandListModel> getUserBandList(@Query("access_token") String accessToken);

    @GET("v2/band/albums")
    Observable<BandAlbumModel> getUserBandsAlbums(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2/band/album/photos")
    Observable<BandPhotoModel> getUserBandsPhotos(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @Query("photo_album_key") String photoAlbumKey);

}
