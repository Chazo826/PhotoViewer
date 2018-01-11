package com.photoviewer.View.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.photoviewer.Model.BandListModel;
import com.photoviewer.NetworkManager.CheckAuthRepository;
import com.photoviewer.R;
import com.photoviewer.Utils.LoginManager;
import com.photoviewer.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding activityMainBinding;

    private CheckAuthRepository checkAuthRepository = new CheckAuthRepository();
    private BandListModel bandListModel;
    private LoginManager loginManager = LoginManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginManager.setContext(this);

        initDataBinding();
        initView();

    }

    private void initDataBinding(){
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.setBandListModel(bandListModel);
        activityMainBinding.setMainActivity(this);

        checkAuthRepository.getBandListRetrofit();
    }

    public void initView(){
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(activityMainBinding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.act_login_button) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
