package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.CommentActivity;
import com.bharatarmy.Activity.VideoDetailVerticalActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.VideoDetailHorizontalAdapterItemBinding;
import com.bharatarmy.databinding.VideoDetailHorizontalHeaderBinding;
import com.bharatarmy.databinding.VideoDetailVerticaleAdapterItemBinding;
import com.bharatarmy.databinding.VideoDetailVerticaleHeaderBinding;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;
    String videoName, videoUserName, videoLike;
    VideoDetailVerticalActivity activity;
    String referenceId;

    public VideoDetailVerticalAdapter(Context mContext, VideoDetailVerticalActivity videoDetailActivity, List<ImageDetailModel> relatedVideoList,
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
        switch (viewType) {
            case HEADER:
                 VideoDetailVerticaleHeaderBinding videoDetailVerticaleHeaderBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.video_detail_verticale_header, parent, false);
                return new VideoDetailVerticalAdapter.HeaderViewHolder(videoDetailVerticaleHeaderBinding);
            default:

                VideoDetailVerticaleAdapterItemBinding videoDetailVerticaleAdapterItemBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.video_detail_verticale_adapter_item, parent, false);
                return new VideoDetailVerticalAdapter.ItemViewHolder(videoDetailVerticaleAdapterItemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            final ImageDetailModel relatedHorizontalVideoDetail = relatedVideoList.get(position - 1);
            videoUserName = relatedHorizontalVideoDetail.getUserName();

            Utils.LikeMemberId = Utils.getAppUserId(mContext);
            Utils.LikeReferenceId = relatedHorizontalVideoDetail.getBAVideoGalleryId();
            referenceId = String.valueOf(relatedHorizontalVideoDetail.getBAVideoGalleryId());
            Utils.LikeSourceType = 2;
            Utils.setImageInImageView(relatedHorizontalVideoDetail.getVideoImageURL(),
                    ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRelatedVideoImg, mContext);

            ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleVideoSizeTxt.setText(relatedHorizontalVideoDetail.getVideoLength());
            ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleShowVideoTitleTxt.setText(relatedHorizontalVideoDetail.getVideoName());


            if (relatedHorizontalVideoDetail.getIsBARecommanded().equals(1)) {
                ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRecommendedImage.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRecommendedImage.setVisibility(View.GONE);
            }

            ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRelatedVideoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoName = relatedHorizontalVideoDetail.getVideoName();
                    dataCheck = new ArrayList<String>();
                    Utils.LikeMemberId = Utils.getAppUserId(mContext);
                    Utils.LikeReferenceId = relatedHorizontalVideoDetail.getBAVideoGalleryId();
                    Utils.LikeSourceType = 2;
                    dataCheck.add(relatedHorizontalVideoDetail.getVideoFileURL() + "|" + relatedHorizontalVideoDetail.getVideoName());
                    morestoryClick.image_more_click();

                    notifyDataSetChanged();
                }
            });

        } else if (holder.getItemViewType() == HEADER) {
            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoMainTitleTxt.setText(videoName);
            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.typeTxt.setText("Bharat Ke Saath");

            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoSharenameTxt.setVisibility(View.VISIBLE);
            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoSharenameTxt.setText("By " + videoUserName);

            if (videoLike.equalsIgnoreCase("1")) {
                ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoLikeBtn.setLiked(true);
            } else {
                ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoLikeBtn.setLiked(false);
            }
            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoLikeBtn.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Utils.LikeStatus = 1;
                        Utils.InsertLike(mContext, activity);
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Utils.LikeStatus = 0;
                        Utils.InsertLike(mContext, activity);
                    }
                }
            });

            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoComment.setOnClickListener(new View.OnClickListener() {
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

        VideoDetailVerticaleHeaderBinding videoDetailVerticaleHeaderBinding;

        HeaderViewHolder(VideoDetailVerticaleHeaderBinding videoDetailVerticaleHeaderBinding) {
            super(videoDetailVerticaleHeaderBinding.getRoot());
            this.videoDetailVerticaleHeaderBinding = videoDetailVerticaleHeaderBinding;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {


        VideoDetailVerticaleAdapterItemBinding videoDetailVerticaleAdapterItemBinding;

        ItemViewHolder(VideoDetailVerticaleAdapterItemBinding videoDetailVerticaleAdapterItemBinding) {
            super(videoDetailVerticaleAdapterItemBinding.getRoot());
            this.videoDetailVerticaleAdapterItemBinding = videoDetailVerticaleAdapterItemBinding;
        }
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}

