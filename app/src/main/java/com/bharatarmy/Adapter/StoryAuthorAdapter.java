package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.StoryDetailActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.StoryAuthorListBinding;

import java.util.ArrayList;
import java.util.List;

public class StoryAuthorAdapter extends RecyclerView.Adapter<StoryAuthorAdapter.ItemViewHolder> {

    public List<ImageDetailModel> mItemList;
    Context mContext;
    com.bharatarmy.Interfaces.image_click image_click;
    private ArrayList<String> dataCheck;


    public StoryAuthorAdapter(Context mContext, List<ImageDetailModel> itemList, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click = image_click;
    }

    @NonNull
    @Override
    public StoryAuthorAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            StoryAuthorListBinding storyAuthorListBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.story_author_list,parent,false);
            return new StoryAuthorAdapter.ItemViewHolder(storyAuthorListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAuthorAdapter.ItemViewHolder viewHolder, int position) {
        final ImageDetailModel detail = mItemList.get(position);

//        Utils.setImageInImageView(detail.getCategoryImage(),viewHolder.storyAuthorListBinding.headerImg,mContext);


        viewHolder.storyAuthorListBinding.typeTxt.setText(detail.getBASubCategoryName());
        viewHolder.storyAuthorListBinding.armyStoryHeaderTxt.setText(detail.getStoryTitle());
        viewHolder.storyAuthorListBinding.armyStorySubTxt.setText(detail.getShortDescription());
        viewHolder.storyAuthorListBinding.dateTxt.setText(detail.getStrStoryAdded());
        viewHolder.storyAuthorListBinding.viewsTxt.setText(detail.getStrViewCount());

        Utils.setImageInImageView(detail.getStrThumbImageName(),viewHolder.storyAuthorListBinding.bannerImg,mContext);

        viewHolder.storyAuthorListBinding.armyStoryHeaderTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webviewIntent = new Intent(mContext, StoryDetailActivity.class);
                webviewIntent.putExtra("Story Heading", detail.getStoryTitle());
                webviewIntent.putExtra("StroyUrl", detail.getStoryWebURL());
                webviewIntent.putExtra("StoryCategorytype",detail.getBASubCategoryName());
                webviewIntent.putExtra("StoryAuthor",detail.getAuthorImageURL());
                webviewIntent.putExtra("StoryHeaderImg",detail.getStrThumbImageName());
                webviewIntent.putExtra("StoryId", detail.getBAStoryId());
                webviewIntent.putExtra("StoryauthorId", detail.getAuthorId());
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);

            }
        });
        viewHolder.storyAuthorListBinding.headerBannerRcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webviewIntent = new Intent(mContext, StoryDetailActivity.class);
                webviewIntent.putExtra("Story Heading", detail.getStoryTitle());
                webviewIntent.putExtra("StroyUrl", detail.getStoryWebURL());
                webviewIntent.putExtra("StoryCategorytype",detail.getBASubCategoryName());
                webviewIntent.putExtra("StoryAuthor",detail.getAuthorImageURL());
                webviewIntent.putExtra("StoryHeaderImg",detail.getStrThumbImageName());
                webviewIntent.putExtra("StoryId", detail.getBAStoryId());
                webviewIntent.putExtra("StoryauthorId", detail.getAuthorId());
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);

            }
        });

        viewHolder.storyAuthorListBinding.armyStorySubTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webviewIntent = new Intent(mContext, StoryDetailActivity.class);
                webviewIntent.putExtra("Story Heading", detail.getStoryTitle());
                webviewIntent.putExtra("StroyUrl", detail.getStoryWebURL());
                webviewIntent.putExtra("StoryCategorytype",detail.getBASubCategoryName());
                webviewIntent.putExtra("StoryAuthor",detail.getAuthorImageURL());
                webviewIntent.putExtra("StoryHeaderImg",detail.getStrThumbImageName());
                webviewIntent.putExtra("StoryId", detail.getBAStoryId());
                webviewIntent.putExtra("StoryauthorId", detail.getAuthorId());
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);
            }
        });
        viewHolder.storyAuthorListBinding.bottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webviewIntent = new Intent(mContext, StoryDetailActivity.class);
                webviewIntent.putExtra("Story Heading", detail.getStoryTitle());
                webviewIntent.putExtra("StroyUrl", detail.getStoryWebURL());
                webviewIntent.putExtra("StoryCategorytype",detail.getBASubCategoryName());
                webviewIntent.putExtra("StoryAuthor",detail.getAuthorImageURL());
                webviewIntent.putExtra("StoryHeaderImg",detail.getStrThumbImageName());
                webviewIntent.putExtra("StoryId", detail.getBAStoryId());
                webviewIntent.putExtra("StoryauthorId", detail.getAuthorId());
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  mItemList.size();
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

        StoryAuthorListBinding storyAuthorListBinding;

        public ItemViewHolder(@NonNull StoryAuthorListBinding storyAuthorListBinding) {
            super(storyAuthorListBinding.getRoot());

            this.storyAuthorListBinding=storyAuthorListBinding;

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



