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
import com.bharatarmy.databinding.ItemLoadingBinding;
import com.bharatarmy.databinding.StoryItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class StoryLsitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    public List<ImageDetailModel> mItemList;
    Context mContext;
    image_click image_click;
    private ArrayList<String> dataCheck;


    public StoryLsitAdapter(Context mContext, List<ImageDetailModel> itemList, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click = image_click;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            StoryItemListBinding storyItemListBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.story_item_list,parent,false);
            return new StoryLsitAdapter.ItemViewHolder(storyItemListBinding);
        } else {
            ItemLoadingBinding itemLoadingBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_loading,parent,false);
            return new StoryLsitAdapter.LoadingViewHolder(itemLoadingBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        StoryItemListBinding storyItemListBinding;


        public ItemViewHolder(@NonNull StoryItemListBinding storyItemListBinding) {
            super(storyItemListBinding.getRoot());
            this.storyItemListBinding=storyItemListBinding;

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ItemLoadingBinding itemLoadingBinding;
        public LoadingViewHolder(@NonNull ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());

            this.itemLoadingBinding=itemLoadingBinding;
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewHolder, final int position) {

        final ImageDetailModel detail = mItemList.get(position);

//        Utils.setImageInImageView(detail.getCategoryImage(),viewHolder.storyItemListBinding.headerImg,mContext);


        viewHolder.storyItemListBinding.typeTxt.setText(detail.getBASubCategoryName());
        viewHolder.storyItemListBinding.armyStoryHeaderTxt.setText(detail.getStoryTitle());
        viewHolder.storyItemListBinding.armyStorySubTxt.setText(detail.getShortDescription());
        viewHolder.storyItemListBinding.dateTxt.setText(detail.getStrStoryAdded());
        viewHolder.storyItemListBinding.viewsTxt.setText(detail.getStrViewCount());
        viewHolder.storyItemListBinding.usernameTxt.setText(detail.getAuthorName());


        Utils.setImageInImageView(detail.getStrThumbImageName(),viewHolder.storyItemListBinding.bannerImg,mContext);
        Utils.setImageInImageView(detail.getAuthorImageURL(),viewHolder.storyItemListBinding.profileImage,mContext);


         viewHolder.storyItemListBinding.typeTxt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dataCheck=new ArrayList<>();
                 dataCheck.add(detail.getStrCategories()+"|"+detail.getBASubCategoryName());
                image_click.image_more_click();
             }
         });
        viewHolder.storyItemListBinding.armyStoryHeaderTxt.setOnClickListener(new View.OnClickListener() {
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
        viewHolder.storyItemListBinding.headerBannerRcv.setOnClickListener(new View.OnClickListener() {
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

        viewHolder.storyItemListBinding.armyStorySubTxt.setOnClickListener(new View.OnClickListener() {
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
        viewHolder.storyItemListBinding.bottomLinear.setOnClickListener(new View.OnClickListener() {
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
        viewHolder.storyItemListBinding.authorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent authorIntent=new Intent(mContext, StoryAuthorActivity.class);
                authorIntent.putExtra("StoryauthorId", detail.getAuthorId());
                authorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(authorIntent);
            }
        });

    }

    public ArrayList<String> getData() {
        return dataCheck;
    }


    public void addMoreDataToList(List<ImageDetailModel> result) {
        mItemList.addAll(result);
        notifyDataSetChanged();
    }
}


