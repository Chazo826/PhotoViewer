package com.photoviewer.View.Activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.photoviewer.R;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by user on 2018. 1. 14..
 */

public class PhotoDetailActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        initView();
    }

    public void initView(){
        PhotoView photoView = findViewById(R.id.photo_detail_viewpager);
        //Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(photoView);
    }

}
