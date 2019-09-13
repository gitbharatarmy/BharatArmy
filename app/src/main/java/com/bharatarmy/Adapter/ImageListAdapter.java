package com.bharatarmy.Adapter;

import android.content.Context;
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
import com.bharatarmy.databinding.ImageListBinding;


import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ItemViewHolder> {

    public List<ImageDetailModel> mItemList;
    Context mContext;
    image_click image_click;
    private ArrayList<String> dataCheck;


    public ImageListAdapter(Context mContext, List<ImageDetailModel> itemList, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click = image_click;
    }

    @NonNull
    @Override
    public ImageListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ImageListBinding imageListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.image_list, parent, false);
        return new ImageListAdapter.ItemViewHolder(imageListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageListAdapter.ItemViewHolder viewHolder, int position) {

        final ImageDetailModel detail = mItemList.get(position);

        Utils.setImageInImageView(detail.getGalleryThumbURL(), viewHolder.imageListBinding.fansImage, mContext);

        viewHolder.imageListBinding.fansImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck = new ArrayList<String>();
                dataCheck.add(String.valueOf(detail.getGalleryURL()));
                image_click.image_more_click();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
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

        ImageListBinding imageListBinding;

        public ItemViewHolder(@NonNull ImageListBinding imageListBinding) {
            super(imageListBinding.getRoot());
            this.imageListBinding = imageListBinding;

        }
    }


    public ArrayList<String> getData() {
        return dataCheck;
    }


    public void addMoreDataToList(List<ImageDetailModel> result) {
        mItemList.addAll(result);
        notifyDataSetChanged();
    }
}

