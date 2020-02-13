package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.AlbumDetailListBinding;


import java.util.ArrayList;
import java.util.List;

public class AlbumDetailListAdapter extends RecyclerView.Adapter<AlbumDetailListAdapter.ItemViewHolder> {

    public List<ImageDetailModel> albumdetailDataList;
    Context mContext;
     image_click imageClick;
    private ArrayList<String> dataCheck;

    public AlbumDetailListAdapter(Context mContext, List<ImageDetailModel> albumdetailDataList,image_click imageClick) {
        this.mContext=mContext;
        this.albumdetailDataList=albumdetailDataList;
        this.imageClick=imageClick;
    }

    @NonNull
    @Override
    public AlbumDetailListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AlbumDetailListBinding albumDetailListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.album_detail_list, parent, false);
        return new AlbumDetailListAdapter.ItemViewHolder(albumDetailListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDetailListAdapter.ItemViewHolder viewHolder, int position) {

        final ImageDetailModel detail = albumdetailDataList.get(position);

        if (detail.getMediaTypeId().equals(1)){
            viewHolder.albumDetailListBinding.videoLinear.setVisibility(View.GONE);
        }else{
            viewHolder.albumDetailListBinding.videoLinear.setVisibility(View.VISIBLE);
        }
        Utils.setImageInImageView(detail.getThumbFileUrl(), viewHolder.albumDetailListBinding.albumImage, mContext);



        viewHolder.albumDetailListBinding.albumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck = new ArrayList<String>();
                dataCheck.add(detail.getFileNameUrl());
                imageClick.image_more_click();
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumdetailDataList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        AlbumDetailListBinding albumDetailListBinding;

        public ItemViewHolder(@NonNull AlbumDetailListBinding albumDetailListBinding) {
            super(albumDetailListBinding.getRoot());
            this.albumDetailListBinding = albumDetailListBinding;

        }
    }
    public ArrayList<String> getData() {
        return dataCheck;
    }

    // Clean all elements of the recycler
    public void clear() {
        albumdetailDataList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addMoreDataToList(List<ImageDetailModel> result) {
        albumdetailDataList.addAll(result);
        notifyDataSetChanged();
    }
}


