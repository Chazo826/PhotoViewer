package com.photoviewer.View.Adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.R;
import com.photoviewer.View.Activity.PhotoActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2018. 1. 9..
 */

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder> {

    private List<BandAlbumModel> albumModels = new ArrayList<>();

    private String bandKey;

    public AlbumListAdapter(List<BandAlbumModel> bandAlbumModels, String bandKey) {
        this.albumModels = bandAlbumModels;
        this.bandKey = bandKey;
    }

    @Override
    public AlbumListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albumcardview, null);
        return new AlbumListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlbumListViewHolder holder, int position) {
        final BandAlbumModel items = albumModels.get(position);

        holder.albumTitle.setText(items.getName());
        holder.photoCountInAlbum.setText(items.getPhoto_count() +"");
        holder.albumCreatedDate.setText(getDate(items.getCreated_at()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), PhotoActivity.class);
                intent.putExtra("band_key", bandKey);
                intent.putExtra("album_key", items.getPhoto_album_key());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public String getDate(long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat showDate = new SimpleDateFormat("yyyy년 MM월");
        return showDate.format(date);
    }



    @Override
    public int getItemCount() {
        return albumModels == null ? 0 : albumModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class AlbumListViewHolder extends RecyclerView.ViewHolder {
        public TextView albumTitle;
        public TextView photoCountInAlbum;
        public TextView albumCreatedDate;
        public ImageView albumPhotoImage;


        public AlbumListViewHolder(View itemView) {
            super(itemView);

            albumTitle = itemView.findViewById(R.id.album_title);
            photoCountInAlbum = itemView.findViewById(R.id.photo_count_in_album);
            albumCreatedDate = itemView.findViewById(R.id.album_create_date);
            albumPhotoImage = itemView.findViewById(R.id.album_image);

        }
    }

}
