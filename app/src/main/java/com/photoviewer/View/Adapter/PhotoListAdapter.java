package com.photoviewer.View.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.R;
import com.photoviewer.View.Activity.PhotoDetailBindingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 1. 14..
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>{

    private List<BandPhotoModel> photoModels = new ArrayList<>();
    private String albumKey;
    private String photoKey;

    public PhotoListAdapter(List<BandPhotoModel> photoModels, String albumKey) {
        this.photoModels = photoModels;
        this.albumKey = albumKey;
    }

    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photoview, parent, false);
        return new PhotoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhotoListViewHolder holder, int position) {
        final BandPhotoModel items = photoModels.get(position);
        Glide.with(holder.itemView.getContext())
                .load(items.getUrl())
                .apply(RequestOptions.fitCenterTransform())
                .into(holder.photoImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), PhotoDetailBindingActivity.class);
                intent.putExtra("photo_key", items.getPhoto_key());
                intent.putExtra("url", items.getUrl());
                holder.itemView.getContext().startActivity(intent);
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
