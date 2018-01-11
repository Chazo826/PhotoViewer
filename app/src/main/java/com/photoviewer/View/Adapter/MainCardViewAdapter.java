package com.photoviewer.View.Adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by user on 2018. 1. 8..
 */

public class MainCardViewAdapter extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    public MainCardViewAdapter(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object o){
        //binding.setVariable()
        binding.executePendingBindings();
    }
}
