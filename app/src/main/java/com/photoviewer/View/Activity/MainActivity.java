package com.photoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.NetworkManager.RequestRetrofitFactory;
import com.photoviewer.R;
import com.photoviewer.Utils.Pref;
import com.photoviewer.View.Adapter.RecyclerItemAdapter;
import com.photoviewer.ViewModel.ClickListener;
import com.photoviewer.databinding.ActivityMainBinding;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseToolbarBindingActivity<ActivityMainBinding> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tvTitleView;
    private RecyclerView bandListRecyclerview;
    private RecyclerItemAdapter adapter;

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);

        setToolbarBackButtonVisibility(TAG, View.GONE);
        pref.setContext(this);
        requestRetrofitFactory.getBandListRetrofit(consumer);
    }

    Consumer<JsonObject> consumer = new Consumer<JsonObject>() {
        @Override
        public void accept(JsonObject jsonObject) throws Exception {
            int result = jsonObject.get("result_code").getAsInt();
            if (result == 1) {
                JsonArray jsonArray = jsonObject.get("result_data").getAsJsonObject()
                        .get("bands").getAsJsonArray();
                pref.putString(Pref.BAND_LIST_KEY, jsonArray.toString());
                initView();
            }
        }
    };

    public void initView() {
        tvTitleView = getContentBinding().mainTitle;
        tvTitleView.setText(R.string.main_toolbar_name);

        bandListRecyclerview = getContentBinding().bandListRecyclerview;

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        bandListRecyclerview.setHasFixedSize(true);
        bandListRecyclerview.setLayoutManager(layoutManager);

        adapter = new RecyclerItemAdapter(getApplicationContext(), bandListClickListener);
        bandListRecyclerview.setAdapter(adapter);
        adapter.setBandItemList(adapter.parseArrayList());
    }

    ClickListener bandListClickListener = new ClickListener() {
        @Override
        public void onItemClick(Object object) {
            Intent intent = new Intent(MainActivity.this, AlbumListBindingActivity.class);
            intent.putExtra("band_key", ((BandListModel) object).getBand_key());
            intent.putExtra("name", ((BandListModel) object).getName());
            startActivity(intent);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    @NonNull
//    private RestaurantListAdapter.OnItemClickListener startRestaurantDetailActivity() {
//        return new RestaurantListAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(Restaurant restaurant) {
//                Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
//                intent.putExtra("restaurant_id", restaurant.getId());
//                startActivity(intent);
//            }
//        };
//    }

}
