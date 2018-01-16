package com.photoviewer.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.R;
import com.photoviewer.Utils.Pref;
import com.photoviewer.databinding.ActivityPhotoDetailBinding;
import com.photoviewer.databinding.ActivityPhotopageBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 1. 14..
 */

public class PhotoDetailActivity extends BaseActivity {
    private static final String TAG = PhotoDetailActivity.class.getSimpleName();

    //클릭한 포토키가지고 첫화면
    //현재 앨범에 전체 포토키 가지고 있어야함
    //스와이프되면 다음키로 증가

    private PhotoViewAttacher photoViewAttacher;
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        initView();
    }

    public void initView() {

    }

    public static class PagerAdapter extends android.support.v4.view.PagerAdapter {
        List<BandAlbumModel> urlList = new ArrayList<>();

        public List<BandAlbumModel> getUrlList() {
            return urlList;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            //photoView.setImageResource(리스트[position]);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

}
