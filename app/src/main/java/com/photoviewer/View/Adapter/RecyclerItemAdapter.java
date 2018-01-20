package com.photoviewer.View.Adapter;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photoviewer.BR;
import com.photoviewer.Model.AbstractModel;
import com.photoviewer.Model.BandAlbumModel;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Model.BandPhotoModel;
import com.photoviewer.Utils.Pref;
import com.photoviewer.ViewModel.AbstractViewModel;
import com.photoviewer.ViewModel.AlbumListViewModel;
import com.photoviewer.ViewModel.BandListViewModel;
import com.photoviewer.ViewModel.ClickListener;
import com.photoviewer.ViewModel.BindListViewType;
import com.photoviewer.ViewModel.ItemClickListener;
import com.photoviewer.ViewModel.PhotoListViewModel;
import com.photoviewer.databinding.ItemAlbumcardviewBinding;
import com.photoviewer.databinding.ItemMaincardviewBinding;
import com.photoviewer.databinding.ItemPhotoviewBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.BindingViewHolder> {

    private Context context;
    private ClickListener clickListener;
    private Pref pref = Pref.getInstance();

    private List<AbstractViewModel> itemList = new ArrayList<>();

    public RecyclerItemAdapter(Context context, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public List<BandListModel> parseArrayList() {
        String json = pref.getString(Pref.BAND_LIST_KEY, null);
        Type listType = new TypeToken<ArrayList<BandListModel>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<BandListModel> list = gson.fromJson(json, listType);
        return list;
    }

    public void setBandItemList(List<BandListModel> bandListModels) {
        List<AbstractViewModel> itemList = new ArrayList<>();

        for (BandListModel bandListModel : bandListModels) {
            itemList.add(new BandListViewModel(bandListModel, clickListener));
        }

        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void setAlbumItemList(List<BandAlbumModel> bandAlbumModels) {
        List<AbstractViewModel> itemList = new ArrayList<>();

        for (BandAlbumModel bandAlbumModel : bandAlbumModels) {
            itemList.add(new AlbumListViewModel(bandAlbumModel, clickListener));
        }
        this.itemList = itemList;
        Collections.reverse(itemList);
        notifyDataSetChanged();
    }

    public void setPhotoItemList(List<BandPhotoModel> bandPhotoModels) {
        List<AbstractViewModel> itemList = new ArrayList<>();

        for (BandPhotoModel model : bandPhotoModels) {
            itemList.add(new PhotoListViewModel(model, clickListener));
        }
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (BindListViewType.values()[viewType]) {
            case BANDLIST:
                return new BindingViewHolder<ItemMaincardviewBinding, BandListModel>(ItemMaincardviewBinding.inflate(LayoutInflater.from(context)));
            case ALBUMLIST:
                return new BindingViewHolder<ItemAlbumcardviewBinding, BandAlbumModel>(ItemAlbumcardviewBinding.inflate(LayoutInflater.from(context)));
            case PHOTOLIST:
                return new BindingViewHolder<ItemPhotoviewBinding, BandPhotoModel>(ItemPhotoviewBinding.inflate(LayoutInflater.from(context)));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class BindingViewHolder<T extends ViewDataBinding, VM> extends RecyclerView.ViewHolder {
        private T binding;
        private VM viewmodel;

        private BindingViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(VM viewmodel) {
            binding.setVariable(BR.viewModel, viewmodel);
            binding.executePendingBindings();
        }

    }


}
