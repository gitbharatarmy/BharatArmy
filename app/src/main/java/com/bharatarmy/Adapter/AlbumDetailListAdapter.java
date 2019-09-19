package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AlbumImageVideoShowActivity;
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



    public AlbumDetailListAdapter(Context mContext, List<ImageDetailModel> albumdetailDataList) {
        this.mContext=mContext;
        this.albumdetailDataList=albumdetailDataList;
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
                Intent showImageVideoIntent=new Intent(mContext, AlbumImageVideoShowActivity.class);
                showImageVideoIntent.putExtra("AlbumImageThumb",detail.getThumbFileUrl());
                showImageVideoIntent.putExtra("AlbumImageVideoPath",detail.getFileNameUrl());
                showImageVideoIntent.putExtra("MediaType",String.valueOf(detail.getMediaTypeId()));
                showImageVideoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(showImageVideoIntent);
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

    public void addMoreDataToList(List<ImageDetailModel> result) {
        albumdetailDataList.addAll(result);
        notifyDataSetChanged();
    }
}


