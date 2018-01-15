package com.photoviewer.View.Adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photoviewer.BR;
import com.photoviewer.Model.BandListModel;
import com.photoviewer.Utils.Pref;
import com.photoviewer.ViewModel.AbstractViewModel;
import com.photoviewer.ViewModel.BandListViewModel;
import com.photoviewer.ViewModel.ClickListener;
import com.photoviewer.ViewModel.BindListViewType;
import com.photoviewer.databinding.ItemMaincardviewBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class BandListAdapter extends RecyclerView.Adapter<BandListAdapter.BindingViewHolder> {

    private Context context;
    private ClickListener clickListener;
    private Pref pref = Pref.getInstance();

    private List<AbstractViewModel> itemList = new ArrayList<>();

    public BandListAdapter(Context context, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void parseArrayList() {
        String json = pref.getString(Pref.BAND_LIST_KEY, null);
        Type listType = new TypeToken<ArrayList<BandListModel>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<BandListModel> list = gson.fromJson(json, listType);
        setItemList(list);
    }

    private void setItemList(List<BandListModel> bandlist) {
        List<AbstractViewModel> itemList = new ArrayList<>();

        for (BandListModel bandListModel : bandlist) {
            itemList.add(new BandListViewModel(bandListModel, clickListener));
        }

        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (BindListViewType.values()[viewType]) {
            case BANDLIST:
                return new BindingViewHolder<ItemMaincardviewBinding, BandListModel>(ItemMaincardviewBinding.inflate(LayoutInflater.from(context)));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.bind(itemList.get(position));
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
            this.viewmodel = viewmodel;
            binding.setVariable(BR.viewModel, viewmodel);
            binding.executePendingBindings();
        }

    }


}
