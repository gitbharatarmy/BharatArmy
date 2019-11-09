package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.RoundishImageView;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.AppDisplayListItemBinding;
import com.bharatarmy.databinding.BharatArmyStoriesListNewBinding;

import java.util.List;


public class AppDisplayItemAdapter extends RecyclerView.Adapter<AppDisplayItemAdapter.MyViewHolder> {
    Context mcontext;
    List<GalleryImageModel> displayItemList;

    public AppDisplayItemAdapter(Context mContext, List<GalleryImageModel> displayItemList) {
        this.displayItemList = displayItemList;
        this.mcontext = mContext;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        AppDisplayListItemBinding appDisplayListItemBinding;

        public MyViewHolder(AppDisplayListItemBinding appDisplayListItemBinding) {
            super(appDisplayListItemBinding.getRoot());

            this.appDisplayListItemBinding = appDisplayListItemBinding;

        }
    }


    @Override
    public AppDisplayItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AppDisplayListItemBinding appDisplayListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.app_display_list_item, parent, false);
        return new AppDisplayItemAdapter.MyViewHolder(appDisplayListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(AppDisplayItemAdapter.MyViewHolder holder, int position) {

        Utils.setImageInImageView(displayItemList.get(position % displayItemList.size()).getImageStr(), holder.appDisplayListItemBinding.displayImage, mcontext);
    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return displayItemList == null ? 0 : displayItemList.size() * 100;
    }

}
