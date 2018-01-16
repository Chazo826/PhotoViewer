package com.photoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.NetworkManager.RequestRetrofitFactory;
import com.photoviewer.R;
import com.photoviewer.Utils.Pref;
import com.photoviewer.View.Adapter.RecyclerItemAdapter;
import com.photoviewer.ViewModel.ClickListener;
import com.photoviewer.databinding.ActivityAlbumpageBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by user on 2017. 12. 20..
 */

public class AlbumListActivity extends BaseActivity<ActivityAlbumpageBinding> {
    private static final String TAG = AlbumListActivity.class.getSimpleName();
    private Pref pref = Pref.getInstance();
    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private RecyclerView albumListRecyclerView;

    private String bandKey;
    private RecyclerItemAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();
        pref.setContext(this);
        getBandkeyIntent(getIntent());
    }

    public void getBandkeyIntent(Intent intent) {
        bandKey = intent.getStringExtra("band_key");
        requestRetrofitFactory.getBandAlbumList(consumer, bandKey);
    }

    Consumer<JsonObject> consumer = new Consumer<JsonObject>() {
        @Override
        public void accept(JsonObject jsonObject) throws Exception {
            int result = jsonObject.get("result_code").getAsInt();
            if (result == 1) {
                JsonArray jsonArray = jsonObject.get("result_data").getAsJsonObject()
                        .get("items").getAsJsonArray();
                pref.putString(Pref.BAND_ALBUM_KEY + bandKey, jsonArray.toString());
                initView();
            }
        }
    };

    public List<BandAlbumModel> parArrayAlbumList() {
        String json = pref.getString(Pref.BAND_ALBUM_KEY + bandKey, null);
        Type listType = new TypeToken<ArrayList<BandAlbumModel>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<BandAlbumModel> list = gson.fromJson(json, listType);
        return list;
    }

    public void initDataBinding() {
        setBinding(R.layout.activity_albumpage);
    }

    public void initView() {
        getBinding().toolbar.setNavigationIcon(R.drawable.ic_action_back);
        getBinding().toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        albumListRecyclerView = getBinding().albumRecyclerview;
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);

        albumListRecyclerView.setHasFixedSize(true);
        albumListRecyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerItemAdapter(getApplicationContext(), albumListListener);
        albumListRecyclerView.setAdapter(adapter);
        adapter.setAlbumSetItemList(parArrayAlbumList());
    }

    ClickListener albumListListener = new ClickListener() {
        @Override
        public void onItemClick(Object o) {
            if(o instanceof BandAlbumModel){
                Intent intent = new Intent(AlbumListActivity.this, PhotoActivity.class);
                intent.putExtra("band_key", bandKey);
                intent.putExtra("album_key", ((BandAlbumModel) o).getPhoto_album_key());
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
