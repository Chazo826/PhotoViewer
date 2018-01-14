package com.photoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.NetworkManager.RequestRetrofitFactory;
import com.photoviewer.R;
import com.photoviewer.Utils.Pref;
import com.photoviewer.View.Adapter.PhotoListAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by user on 2017. 12. 20..
 */

public class PhotoActivity extends BaseActivity {
    private static final String TAG = PhotoActivity.class.getSimpleName();

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();

    private String albumKey;
    private String bandKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref.setContext(this);
        getAlbumKeyIntent(getIntent());
    }

    public void getAlbumKeyIntent(Intent intent){
        bandKey = intent.getStringExtra("band_key");
        albumKey = intent.getStringExtra("album_key");

        requestRetrofitFactory.getBandPhotoList(consumer, bandKey, albumKey);
    }

    Consumer<JsonObject> consumer = new Consumer<JsonObject>() {
        @Override
        public void accept(JsonObject jsonObject) throws Exception {
            JsonArray jsonArray = jsonObject.get("result_data").getAsJsonObject()
                    .get("items").getAsJsonArray();
            pref.putString(Pref.BAND_PHOTO_KEY + albumKey, jsonArray.toString());
            initView();
        }
    };

    public void initView(){
        RecyclerView recyclerView = findViewById(R.id.photo_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new PhotoListAdapter(parseArrayList(), albumKey));
    }

    public List<BandPhotoModel> parseArrayList(){
        String json = pref.getString(Pref.BAND_LIST_KEY + albumKey, null);
        Type listType = new TypeToken<ArrayList<BandPhotoModel>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<BandPhotoModel> list = gson.fromJson(json, listType);
        return list;
    }

    @Override
    public void onBackPressed(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

