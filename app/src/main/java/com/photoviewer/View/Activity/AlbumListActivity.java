package com.photoviewer.View.Activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private BandAlbumModel bandAlbumModel;

    private String bandKey;
    private String bandName;

    private RecyclerItemAdapter adapter;
    private String albumKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding(R.layout.activity_albumpage);

        pref.setContext(this);
        getBandkeyIntent(getIntent());
    }

    public void getBandkeyIntent(Intent intent) {
        bandKey = intent.getStringExtra("band_key");
        bandName = intent.getStringExtra("name");

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
//            JsonObject test = jsonObject.get("result_data").getAsJsonObject()
//                    .get("paging").getAsJsonObject();
//            Log.d(TAG+"테스트", test.toString());
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

    public void initView() {
        albumListRecyclerView = getBinding().albumRecyclerview;
        getBinding().albumListTitle.setText(R.string.main_album_title);
        getBinding().bandName.setText(bandName);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);

        albumListRecyclerView.setHasFixedSize(true);
        albumListRecyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerItemAdapter(getApplicationContext(), albumListListener);
        albumListRecyclerView.setAdapter(adapter);
        adapter.setAlbumItemList(parArrayAlbumList());
        setToolbar();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(albumListRecyclerView);
    }

    public void setToolbar() {
        getBinding().toolbar.setNavigationIcon(R.drawable.ic_action_back);
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

    ClickListener albumListListener = new ClickListener() {

        @Override
        public void onItemClick(Object object) {
            Intent intent = new Intent(AlbumListActivity.this, PhotoActivity.class);
            intent.putExtra("band_key", bandKey);
            intent.putExtra("album_key", ((BandAlbumModel) object).getPhoto_album_key());
            pref.putString(Pref.BAND_ALBUM_KEY, ((BandAlbumModel) object).getPhoto_album_key());
            startActivity(intent);
        }
    };

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.RIGHT) {
                requestRetrofitFactory.getBandPhotoList(consumer, bandKey, pref.getString(Pref.BAND_ALBUM_KEY, bandAlbumModel.getPhoto_album_key()));

//                Intent intent = new Intent(AlbumListActivity.this, PhotoDetailActivity.class);
//                intent.putExtra("band_key", bandKey);
//                intent.putExtra("album_key", pref.getString(Pref.BAND_ALBUM_KEY, bandAlbumModel.getPhoto_album_key()));
//
//                startActivity(intent);
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                final View itemView = viewHolder.itemView;

                Paint paint = new Paint();
                if (dX > 0) {
                    //왼쪽에서 오른쪽 스와이프
                    paint.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSlider));
                    RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop() + 5, dX, (float) itemView.getBottom() - 5);
                    c.drawRect(background, paint);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
