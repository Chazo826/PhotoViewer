package com.photoviewer.View.Adapter;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 1. 14..
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>{

    private List<BandPhotoModel> photoModels = new ArrayList<>();
    private String albumKey;

    public PhotoListAdapter(List<BandPhotoModel> photoModels, String albumKey) {
        this.photoModels = photoModels;
        this.albumKey = albumKey;
    }

    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photoview, null);
        return new PhotoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoListViewHolder holder, int position) {
        BandPhotoModel items = photoModels.get(position);
        Glide.with(holder.itemView.getContext()).load(items.getUrl()).into(holder.photoImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //개별 사진 화면 띄우게 함
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoModels.size();
    }

    public class PhotoListViewHolder extends RecyclerView.ViewHolder {
        public ImageView photoImage;

        public PhotoListViewHolder(View itemView) {
            super(itemView);
            photoImage = itemView.findViewById(R.id.item_grid_photo);
        }
    }
}
