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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.StoryAuthorActivity;
import com.bharatarmy.Activity.StoryDetailActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.StoryItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class StoryLsitAdapter extends RecyclerView.Adapter<StoryLsitAdapter.ItemViewHolder> {




    public List<ImageDetailModel> mItemList;
    Context mContext;
    image_click image_click;
    private ArrayList<String> dataCheck;
FragmentActivity activity;

    public StoryLsitAdapter(Context mContext, List<ImageDetailModel> itemList, FragmentActivity activity, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click = image_click;
        this.activity=activity;
    }

    @NonNull
    @Override
    public StoryLsitAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            StoryItemListBinding storyItemListBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.story_item_list,parent,false);
            return new StoryLsitAdapter.ItemViewHolder(storyItemListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull StoryLsitAdapter.ItemViewHolder viewHolder, int position) {
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
                Utils.viewsMemberId=String.valueOf(Utils.getAppUserId(mContext));
                Utils.viewsReferenceId=String.valueOf(detail.getBAStoryId());
                Utils.viewsSourceType="3";
                Utils.viewsTokenId= Utils.getPref(mContext, "registration_id");
                Utils.InsertBAViews(mContext,activity);
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
                Utils.viewsMemberId=String.valueOf(Utils.getAppUserId(mContext));
                Utils.viewsReferenceId=String.valueOf(detail.getBAStoryId());
                Utils.viewsSourceType="3";
                Utils.viewsTokenId= Utils.getPref(mContext, "registration_id");
                Utils.InsertBAViews(mContext,activity);
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
                Utils.viewsMemberId=String.valueOf(Utils.getAppUserId(mContext));
                Utils.viewsReferenceId=String.valueOf(detail.getBAStoryId());
                Utils.viewsSourceType="3";
                Utils.viewsTokenId= Utils.getPref(mContext, "registration_id");
                Utils.InsertBAViews(mContext,activity);
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
                Utils.viewsMemberId=String.valueOf(Utils.getAppUserId(mContext));
                Utils.viewsReferenceId=String.valueOf(detail.getBAStoryId());
                Utils.viewsSourceType="3";
                Utils.viewsTokenId= Utils.getPref(mContext, "registration_id");
                Utils.InsertBAViews(mContext,activity);
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
                Utils.viewsMemberId=String.valueOf(Utils.getAppUserId(mContext));
                Utils.viewsReferenceId=String.valueOf(detail.getBAStoryId());
                Utils.viewsSourceType="3";
                Utils.viewsTokenId= Utils.getPref(mContext, "registration_id");
                Utils.InsertBAViews(mContext,activity);
                Intent authorIntent=new Intent(mContext, StoryAuthorActivity.class);
                authorIntent.putExtra("StoryauthorId", detail.getAuthorId());
                authorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(authorIntent);
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

        StoryItemListBinding storyItemListBinding;


        public ItemViewHolder(@NonNull StoryItemListBinding storyItemListBinding) {
            super(storyItemListBinding.getRoot());
            this.storyItemListBinding=storyItemListBinding;

        }
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }

    // Clean all elements of the recycler
    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addMoreDataToList(List<ImageDetailModel> result) {
        mItemList.addAll(result);
        notifyDataSetChanged();
    }
}


