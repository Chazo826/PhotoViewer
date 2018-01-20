package com.photoviewer.View.Activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.photoviewer.R;
import com.photoviewer.databinding.ActivityBaseToolbarBinding;

/**
 * Created by user on 2018. 1. 20..
 */

public abstract class BaseToolbarBindingActivity<T extends ViewDataBinding> extends BaseBindingActivity<ActivityBaseToolbarBinding> {

    private Toolbar toolbar;
    private FrameLayout contentLayout;

    public T contentBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityLayout(R.layout.activity_base_toolbar);

        toolbar = getActivityBinding().baseToolbar;
        setSupportActionBar(toolbar);

        contentLayout = getActivityBinding().baseContentLayout;
        contentBinding = DataBindingUtil.getBinding(contentLayout);
    }

    public T getContentBinding() {
        return contentBinding;
    }

    public void setContentLayout(int resId) {
        contentBinding = DataBindingUtil.inflate(getLayoutInflater(), resId, contentLayout, true);
    }

    public void setToolbarTitleText(String title) {
        getActivityBinding().toolbarTitleTextView.setText(title);
        getActivityBinding().toolbarTitleTextView.setTextColor(getResources().getColor(R.color.colorSlider));
    }

    public void setToolbarBackButtonVisibility(String tag, int visibility) {
        if(visibility == View.VISIBLE) {
            if(tag.contains("PhotoDetailBindingActivity")){
                getActivityBinding().baseToolbar.setNavigationIcon(R.drawable.ic_action_close);
            } else {
                getActivityBinding().baseToolbar.setNavigationIcon(R.drawable.ic_action_back);
            }
            getActivityBinding().baseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
