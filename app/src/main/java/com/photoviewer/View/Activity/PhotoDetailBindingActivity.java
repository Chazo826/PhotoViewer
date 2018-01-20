package com.photoviewer.View.Activity;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.R;
import com.photoviewer.Utils.Pref;
import com.photoviewer.ViewModel.PhotoDetailViewModel;
import com.photoviewer.databinding.ActivityPhotoDetailBinding;
import com.photoviewer.databinding.ItemPhotodetailBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2018. 1. 14..
 */

public class PhotoDetailBindingActivity extends BaseToolbarBindingActivity<ActivityPhotoDetailBinding> {
    private static final String TAG = PhotoDetailBindingActivity.class.getSimpleName();

    private ViewPager viewPager;
    private PhotoPagerAdapter photoPagerAdapter;
    private String albumKey;
    private int currentPage = 0;
    private Boolean isSlideShow;

    private List<BandPhotoModel> bandPhotoModelList = new ArrayList<>();
    private Pref pref = Pref.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentLayout(R.layout.activity_photo_detail);
        setToolbarBackButtonVisibility(TAG, View.VISIBLE);
        getIntentData(getIntent());

        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public void getIntentData(Intent intent) {
        albumKey = intent.getStringExtra("album_key");
        isSlideShow = intent.getBooleanExtra("slide_show", false);
    }

    public void initView() {
        viewPager = getContentBinding().detailViewPager;
        bandPhotoModelList = parseArrayList();

        photoPagerAdapter = new PhotoPagerAdapter();
        viewPager.setAdapter(photoPagerAdapter);

        if (isSlideShow) {
            photoPagerAdapter.setAutoPager();
        }
    }

    public List<BandPhotoModel> parseArrayList() {
        String json = pref.getString(Pref.BAND_PHOTO_KEY + albumKey, null);
        Type listType = new TypeToken<ArrayList<BandPhotoModel>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<BandPhotoModel> list = gson.fromJson(json, listType);
        return list;
    }

    public class PhotoPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bandPhotoModelList.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            ItemPhotodetailBinding itemPhotodetailBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(container.getContext()),
                            R.layout.item_photodetail,
                            container,
                            false);

            itemPhotodetailBinding.setViewModel(new PhotoDetailViewModel(bandPhotoModelList.get(position)));
            container.addView(itemPhotodetailBinding.getRoot(), RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

            return itemPhotodetailBinding.getRoot();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public void setAutoPager() {
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(currentPage, true);
                    if (currentPage == bandPhotoModelList.size()) {
                        finish();
                    } else {
                        ++currentPage;
                    }
                }
            };
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, 1500, 2000);
        }
    }


}
