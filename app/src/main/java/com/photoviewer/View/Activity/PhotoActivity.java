package com.photoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.NetworkManager.RequestRetrofitFactory;
import com.photoviewer.R;
import com.photoviewer.Utils.Pref;
import com.photoviewer.View.Adapter.RecyclerItemAdapter;
import com.photoviewer.ViewModel.ClickListener;
import com.photoviewer.databinding.ActivityPhotoBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by user on 2017. 12. 20..
 */

public class PhotoActivity extends BaseActivity<ActivityPhotoBinding> {
    private static final String TAG = PhotoActivity.class.getSimpleName();

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();
    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private String albumKey;
    private String bandKey;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();
        pref.setContext(this);
        getAlbumKeyIntent(getIntent());
    }

    public void getAlbumKeyIntent(Intent intent) {
        bandKey = intent.getStringExtra("band_key");
        albumKey = intent.getStringExtra("album_key");
        requestRetrofitFactory.getBandPhotoList(consumer, bandKey, albumKey);
    }

    Consumer<JsonObject> consumer = new Consumer<JsonObject>() {
        @Override
        public void accept(JsonObject jsonObject) throws Exception {
            int result = jsonObject.get("result_code").getAsInt();
            if (result == 1) {
//                JsonObject test = jsonObject.get("result_data").getAsJsonObject()
//                        .get("paging").getAsJsonObject();
//                if (test.has("next_params")) {
//                    String afterValue = test.get("next_params").getAsJsonObject().get("after").getAsString();
//                   if(!afterValue.contains("null")){
//                       requestRetrofitFactory.getNextParams(consumer, afterValue, bandKey);
//                   }
//                }
                JsonArray jsonArray = jsonObject.get("result_data").getAsJsonObject()
                        .get("items").getAsJsonArray();
                pref.putString(Pref.BAND_PHOTO_KEY + albumKey, jsonArray.toString());
                initView();
            }

        }
    };

    public void initDataBinding() {
        setBinding(R.layout.activity_photo);
    }

    public void initView() {
        recyclerView = getBinding().photoRecyclerview;

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerItemAdapter(getApplicationContext(), photoListListener);
        recyclerView.setAdapter(adapter);
        adapter.setPhotoItemList(parseArrayList());

        setToolbar();

    }

    public void setToolbar() {
        getBinding().toolbar.setNavigationIcon(R.drawable.ic_action_back);
        getBinding().albumSlideBtn.setVisibility(View.VISIBLE);
        getBinding().albumSlideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //슬라이드 쇼 실행

            }
        });

        getBinding().toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    ClickListener photoListListener = new ClickListener() {

        @Override
        public void onItemClick(Object o) {
            if (o instanceof BandPhotoModel) {
                Intent intent = new Intent(PhotoActivity.this, PhotoDetailActivity.class);
                intent.putExtra("album_key", albumKey);
                startActivity(intent);
            }
        }
    };


    public List<BandPhotoModel> parseArrayList() {
        String json = pref.getString(Pref.BAND_PHOTO_KEY + albumKey, null);
        Type listType = new TypeToken<ArrayList<BandPhotoModel>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<BandPhotoModel> list = gson.fromJson(json, listType);
        return list;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

