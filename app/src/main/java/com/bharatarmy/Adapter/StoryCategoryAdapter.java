package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.StoryAuthorActivity;
import com.bharatarmy.Activity.StoryDetailActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.StoryCategoryItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class StoryCategoryAdapter extends RecyclerView.Adapter<StoryCategoryAdapter.ItemViewHolder> {

    public List<ImageDetailModel> mItemList;
    Context mContext;
    image_click image_click;
    private ArrayList<String> dataCheck;


    public StoryCategoryAdapter(Context mContext, String categoryNameStr,List<ImageDetailModel> itemList, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click = image_click;
    }

    @NonNull
    @Override
    public StoryCategoryAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        StoryCategoryItemListBinding storyCategoryItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
               R.layout.story_category_item_list, parent, false);

            return new StoryCategoryAdapter.ItemViewHolder(storyCategoryItemListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull StoryCategoryAdapter.ItemViewHolder viewHolder, int position) {
        final ImageDetailModel detail = mItemList.get(position);
        viewHolder.storyCategoryItemListBinding.headerTxt.setText(detail.getCategoryName());
        viewHolder.storyCategoryItemListBinding.armyStoryHeaderTxt.setText(detail.getStoryTitle());
        viewHolder.storyCategoryItemListBinding.armyStorySubTxt.setText(detail.getShortDescription());
        viewHolder.storyCategoryItemListBinding.dateTxt.setText(detail.getStrStoryAdded());
        viewHolder.storyCategoryItemListBinding.viewsTxt.setText(detail.getStrViewCount());
        viewHolder.storyCategoryItemListBinding.usernameTxt.setText(detail.getAuthorName());


        Utils.setImageInImageView(detail.getStrThumbImageName(),viewHolder.storyCategoryItemListBinding.bannerImg,mContext);
        Utils.setImageInImageView(detail.getAuthorImageURL(),viewHolder.storyCategoryItemListBinding.profileImage,mContext);




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
       StoryCategoryItemListBinding storyCategoryItemListBinding;



        public ItemViewHolder(@NonNull StoryCategoryItemListBinding storyCategoryItemListBinding) {
            super(storyCategoryItemListBinding.getRoot());
this.storyCategoryItemListBinding=storyCategoryItemListBinding;

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