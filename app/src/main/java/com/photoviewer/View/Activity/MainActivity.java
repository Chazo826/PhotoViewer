package com.photoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.NetworkManager.RequestRetrofitFactory;
import com.photoviewer.R;
import com.photoviewer.Utils.Pref;
import com.photoviewer.View.Adapter.BandListAdapter;
import com.photoviewer.ViewModel.ClickListener;
import com.photoviewer.databinding.ActivityMainBinding;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView bandListRecyclerview;
    private BandListAdapter adapter;

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
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

    public void initBinding() {
        setBinding(R.layout.activity_main);
    }

    public void initView() {
        bandListRecyclerview = getBinding().bandListRecyclerview;
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        bandListRecyclerview.setHasFixedSize(true);
        bandListRecyclerview.setLayoutManager(layoutManager);

        adapter = new BandListAdapter(getApplicationContext(), bandlistlistener);
        bandListRecyclerview.setAdapter(adapter);
        adapter.parseArrayList();
    }

    ClickListener bandlistlistener = new ClickListener() {
        @Override
        public void onItemClick(BandListModel bandListModel) {
            Intent intent = new Intent(MainActivity.this, AlbumListActivity.class);
            intent.putExtra("band_key", bandListModel.getBand_key());
            startActivity(intent);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
