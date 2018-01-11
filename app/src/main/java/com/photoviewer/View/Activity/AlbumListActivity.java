package com.photoviewer.View.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.R;
import com.photoviewer.ViewModel.AlbumListViewModel;
import com.photoviewer.databinding.ActivityAlbumpageBinding;

/**
 * Created by user on 2017. 12. 20..
 */

public class AlbumListActivity extends BaseActivity {
    private static final String TAG = AlbumListActivity.class.getSimpleName();

    private ActivityAlbumpageBinding albumpageBinding;
    private BandAlbumModel albumModel;
    private AlbumListViewModel albumListViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initView();
    }

    private void initDataBinding(){
        albumpageBinding = DataBindingUtil.setContentView(this, R.layout.activity_albumpage);

        albumpageBinding.setAlbumListModel(albumModel);
        albumpageBinding.setAlbumListViewModel(albumListViewModel);
        albumpageBinding.setAlbumListActivity(this);
    }

    private void initView(){
        albumpageBinding = ActivityAlbumpageBinding.inflate(getLayoutInflater());

    }


}
