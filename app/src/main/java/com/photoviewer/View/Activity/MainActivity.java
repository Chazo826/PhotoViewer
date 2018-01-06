package com.photoviewer.View.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.photoviewer.Model.BandAlbumModel;

import com.photoviewer.R;
import com.photoviewer.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding activityMainBinding;
    private BandAlbumModel bandAlbumModel = new BandAlbumModel();
    private ActionBar actionBar;
    private Toolbar toolbar;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    public static Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.setBandAlbumModel(bandAlbumModel);
        activityMainBinding.setMainActivity(this);
        mContext = getApplicationContext();

        initView();
    }

    public void initView(){
        toolbar = activityMainBinding.activityMainToolbar;
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }
}
