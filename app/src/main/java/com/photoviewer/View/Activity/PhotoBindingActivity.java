package com.photoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
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
import com.photoviewer.ViewModel.ItemClickListener;
import com.photoviewer.ViewModel.PhotoDetailViewModel;
import com.photoviewer.databinding.ActivityPhotoBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class PhotoBindingActivity extends BaseToolbarBindingActivity<ActivityPhotoBinding> {
    private static final String TAG = PhotoBindingActivity.class.getSimpleName();

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();

    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;

    private String albumKey;
    private String bandKey;
    private String albumName;

    private MenuItem menuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentLayout(R.layout.activity_photo);

        setToolbarBackButtonVisibility(TAG, View.VISIBLE);

        pref.setContext(this);
        getAlbumKeyIntent(getIntent());
    }

    public void getAlbumKeyIntent(Intent intent) {
        bandKey = intent.getStringExtra("band_key");
        albumKey = intent.getStringExtra("album_key");
        albumName = intent.getStringExtra("album_name");
        setToolbarTitleText(albumName);

        requestRetrofitFactory.getBandPhotoList(consumer, bandKey, albumKey);
    }

    Consumer<JsonObject> consumer = new Consumer<JsonObject>() {
        @Override
        public void accept(JsonObject jsonObject) throws Exception {
            int result = jsonObject.get("result_code").getAsInt();

            if (result == 1) {
                JsonArray jsonArray = jsonObject.get("result_data").getAsJsonObject()
                        .get("items").getAsJsonArray();
                pref.putString(Pref.BAND_PHOTO_KEY + albumKey, jsonArray.toString());
                initView();
            }

        }
    };

    public void initView() {
        recyclerView = getContentBinding().photoRecyclerview;

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerItemAdapter(getApplicationContext(), photoListListener);
        recyclerView.setAdapter(adapter);
        adapter.setPhotoItemList(parseArrayList());
    }

    ClickListener photoListListener = new ClickListener() {
        @Override
        public void onItemClick(Object o) {
            if (o instanceof BandPhotoModel) {
                Intent intent = new Intent(PhotoBindingActivity.this, PhotoDetailBindingActivity.class);
                intent.putExtra("album_key", albumKey);
                intent.putExtra("slide_show", false);
                startActivity(intent);
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
        menuItem = menu.findItem(R.id.action_photo_slide);

        menuItem.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo_slide:
                Intent intent = new Intent(PhotoBindingActivity.this, PhotoDetailBindingActivity.class);
                intent.putExtra("album_key", albumKey);
                intent.putExtra("slide_show", true);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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

