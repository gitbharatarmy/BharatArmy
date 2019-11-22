package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.CommentActivity;
import com.bharatarmy.Activity.VideoDetailActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.RelatedVideoAdapterItemBinding;
import com.bharatarmy.databinding.RelatedVideoHeaderBinding;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

public class RelatedVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;
    String videoName, videoUserName, videoLike;
    VideoDetailActivity activity;
    String referenceId;

    public RelatedVideoAdapter(Context mContext, VideoDetailActivity videoDetailActivity, List<ImageDetailModel> relatedVideoList,
                               String videoNameStr, String videoUserNameStr, String videoLike, image_click morestoryClick) {
        this.mContext = mContext;
        this.relatedVideoList = relatedVideoList;
        this.morestoryClick = morestoryClick;
        this.videoName = videoNameStr;
        this.videoUserName = videoUserNameStr;
        this.activity = videoDetailActivity;
        this.videoLike = videoLike;
    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return relatedVideoList.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:

                RelatedVideoHeaderBinding relatedVideoHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.related_video_header, parent, false);
                return new RelatedVideoAdapter.HeaderViewHolder(relatedVideoHeaderBinding);
            default:

                RelatedVideoAdapterItemBinding relatedVideoAdapterItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.related_video_adapter_item, parent, false);
                return new RelatedVideoAdapter.ItemViewHolder(relatedVideoAdapterItemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            final ImageDetailModel relatedVideoDetail = relatedVideoList.get(position - 1);
            videoUserName = relatedVideoDetail.getUserName();
            Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
            Utils.LikeReferenceId = String.valueOf(relatedVideoDetail.getBAVideoGalleryId());
            referenceId = String.valueOf(relatedVideoDetail.getBAVideoGalleryId());
            Utils.LikeSourceType = "2";
            Utils.setImageInImageView(relatedVideoDetail.getVideoImageURL(),
                    ((ItemViewHolder) holder).relatedVideoAdapterItemBinding.relatedVideoImg, mContext);

            ((ItemViewHolder) holder).relatedVideoAdapterItemBinding.videoSizeTxt.setText(relatedVideoDetail.getVideoLength());
            ((ItemViewHolder) holder).relatedVideoAdapterItemBinding.showVideoTitleTxt.setText(relatedVideoDetail.getVideoName());


            if (relatedVideoDetail.getIsBARecommanded().equals(1)) {
                ((ItemViewHolder) holder).relatedVideoAdapterItemBinding.recommendedImage.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).relatedVideoAdapterItemBinding.recommendedImage.setVisibility(View.GONE);
            }

            ((ItemViewHolder) holder).relatedVideoAdapterItemBinding.relatedVideoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.handleClickEvent(mContext,((ItemViewHolder)holder).relatedVideoAdapterItemBinding.relatedVideoImg);
                    videoName = relatedVideoDetail.getVideoName();
                    dataCheck = new ArrayList<String>();
                    Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
                    Utils.LikeReferenceId = String.valueOf(relatedVideoDetail.getBAVideoGalleryId());
                    Utils.LikeSourceType = "2";
                    dataCheck.add(relatedVideoDetail.getVideoFileURL() + "|" + relatedVideoDetail.getVideoName());
                    morestoryClick.image_more_click();

                    notifyDataSetChanged();
                }
            });

        } else if (holder.getItemViewType() == HEADER) {
            ((HeaderViewHolder) holder).relatedVideoHeaderBinding.showVideoMainTitleTxt.setText(videoName);
            ((HeaderViewHolder) holder).relatedVideoHeaderBinding.typeTxt.setText("Bharat Ke Saath");

            ((HeaderViewHolder) holder).relatedVideoHeaderBinding.showVideoSharenameTxt.setVisibility(View.VISIBLE);
            ((HeaderViewHolder) holder).relatedVideoHeaderBinding.showVideoSharenameTxt.setText("By " + videoUserName);

            if (videoLike.equalsIgnoreCase("1")) {
                ((HeaderViewHolder) holder).relatedVideoHeaderBinding.videoLikeBtn.setLiked(true);
            } else {
                ((HeaderViewHolder) holder).relatedVideoHeaderBinding.videoLikeBtn.setLiked(false);
            }
            ((HeaderViewHolder) holder).relatedVideoHeaderBinding.videoLikeBtn.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Utils.LikeStatus = "1";
                        Utils.InsertLike(mContext, activity);
                    }else{
                        ((HeaderViewHolder) holder).relatedVideoHeaderBinding.videoLikeBtn.setLiked(false);
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Utils.LikeStatus = "0";
                        Utils.InsertLike(mContext, activity);
                    }else{
                        ((HeaderViewHolder) holder).relatedVideoHeaderBinding.videoLikeBtn.setLiked(true);
                    }
                }
            });

            ((HeaderViewHolder) holder).relatedVideoHeaderBinding.videoComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Intent commentIntent = new Intent(mContext, CommentActivity.class);
                        commentIntent.putExtra("referenceId", String.valueOf(Utils.LikeReferenceId));
                        commentIntent.putExtra("sourceType", "2");
                        mContext.startActivity(commentIntent);
                    }
                }
            });
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        RelatedVideoHeaderBinding relatedVideoHeaderBinding;

        HeaderViewHolder(RelatedVideoHeaderBinding relatedVideoHeaderBinding) {
            super(relatedVideoHeaderBinding.getRoot());
            this.relatedVideoHeaderBinding = relatedVideoHeaderBinding;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {


        RelatedVideoAdapterItemBinding relatedVideoAdapterItemBinding;

        ItemViewHolder(RelatedVideoAdapterItemBinding relatedVideoAdapterItemBinding) {
            super(relatedVideoAdapterItemBinding.getRoot());
            this.relatedVideoAdapterItemBinding = relatedVideoAdapterItemBinding;
        }
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}

