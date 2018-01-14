package com.photoviewer.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.NetworkManager.RequestRetrofitFactory;
import com.photoviewer.R;
import com.photoviewer.Utils.Pref;
import com.photoviewer.View.Adapter.MainCardViewAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref.setContext(this);
        requestRetrofitFactory.getBandListRetrofit(consumer);
    }

    Consumer<JsonObject> consumer = new Consumer<JsonObject>() {
        @Override
        public void accept(JsonObject jsonObject) throws Exception {
            JsonArray jsonArray = jsonObject.get("result_data").getAsJsonObject()
                    .get("bands").getAsJsonArray();
            pref.putString(Pref.BAND_LIST_KEY, jsonArray.toString());
            initView();
        }
    };

    public void initView(){
        RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MainCardViewAdapter(parseArrayList()));
    }

    public List<BandListModel> parseArrayList(){
        String json = pref.getString(Pref.BAND_LIST_KEY, null);
        Type listType = new TypeToken<ArrayList<BandListModel>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<BandListModel> list = gson.fromJson(json, listType);
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
