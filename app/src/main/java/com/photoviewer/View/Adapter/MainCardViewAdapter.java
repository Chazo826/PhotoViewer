package com.photoviewer.View.Adapter;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.NetworkManager.RequestRetrofitFactory;
import com.photoviewer.R;
import com.photoviewer.View.Activity.AlbumListActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by user on 2018. 1. 8..
 */

public class MainCardViewAdapter extends RecyclerView.Adapter<MainCardViewAdapter.CardViewViewHolder> {
    private List<BandListModel> bandListModels = new ArrayList<>();

    public MainCardViewAdapter(List<BandListModel> bandListModels) {
        this.bandListModels = bandListModels;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maincardview, null);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewViewHolder holder, int position) {
        final BandListModel bandlistitem = bandListModels.get(position);
        Glide.with(holder.itemView.getContext()).load(bandlistitem.getCover()).into(holder.bandCoverImage);
        holder.bandNameTitle.setText(bandlistitem.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), AlbumListActivity.class);
                intent.putExtra("band_name", bandlistitem.getName());
                intent.putExtra("band_key", bandlistitem.getBand_key());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bandListModels.size();
    }


    public class CardViewViewHolder extends RecyclerView.ViewHolder {
       public TextView bandNameTitle;
       public ImageView bandCoverImage;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            bandNameTitle = itemView.findViewById(R.id.bandNameTitle);
            bandCoverImage = itemView.findViewById(R.id.bandImageView);
        }
    }


}
