package com.photoviewer.View.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by user on 2017. 12. 31..
 */

public class DrawerAdapter {

    private int mSelectedPosition;
    private int mTouchedPosition;

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_drawer, viewGroup, false);
//        return new ViewHolder(v);
//    }

//    @Override
//    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
//      여기서 각 변수들을 동적으로 바인딩해야함
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final View ripple;
        private ImageView imageView;
        private TextView textView1;
    //    private final ImageView newIcon;

        private ViewHolder(View itemView) {
            super(itemView);
            //ripple = itemView.findViewById(R.id.ripple_action_primary);
//            textView1 = (TextView) itemView.findViewById(R.id.item_name);
//            imageView = (ImageView) itemView.findViewById(R.id.item_icon);
//            newIcon = (ImageView) itemView.findViewById(R.id.item_newicon);
        }
    }
}
