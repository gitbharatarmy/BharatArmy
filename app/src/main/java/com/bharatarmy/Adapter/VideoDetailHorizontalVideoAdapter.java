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
import com.bharatarmy.Activity.VideoDetailHorizontalActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.VideoDetailHorizontalAdapterItemBinding;
import com.bharatarmy.databinding.VideoDetailHorizontalHeaderBinding;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailHorizontalVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;
    String videoName, videoUserName, videoLike;
    VideoDetailHorizontalActivity activity;
    String referenceId;

    public VideoDetailHorizontalVideoAdapter(Context mContext, VideoDetailHorizontalActivity videoDetailActivity, List<ImageDetailModel> relatedVideoList,
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
                VideoDetailHorizontalHeaderBinding videoDetailHorizontalHeaderBinding = 
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.video_detail_horizontal_header, parent, false);
                return new VideoDetailHorizontalVideoAdapter.HeaderViewHolder(videoDetailHorizontalHeaderBinding);
            default:

                VideoDetailHorizontalAdapterItemBinding videoDetailHorizontalAdapterItemBinding = 
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.video_detail_horizontal_adapter_item, parent, false);
                return new VideoDetailHorizontalVideoAdapter.ItemViewHolder(videoDetailHorizontalAdapterItemBinding);
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
                    ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalRelatedVideoImg, mContext);

            ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalVideoSizeTxt.setText(relatedHorizontalVideoDetail.getVideoLength());
            ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalShowVideoTitleTxt.setText(relatedHorizontalVideoDetail.getVideoName());


            if (relatedHorizontalVideoDetail.getIsBARecommanded().equals(1)) {
                ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalRecommendedImage.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalRecommendedImage.setVisibility(View.GONE);
            }

            ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalRelatedVideoImg.setOnClickListener(new View.OnClickListener() {
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
            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.showVideoMainTitleTxt.setText(videoName);
            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.typeTxt.setText("Bharat Ke Saath");

            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.showVideoSharenameTxt.setVisibility(View.VISIBLE);
            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.showVideoSharenameTxt.setText("By " + videoUserName);

            if (videoLike.equalsIgnoreCase("1")) {
                ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.videoLikeBtn.setLiked(true);
            } else {
                ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.videoLikeBtn.setLiked(false);
            }
            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.videoLikeBtn.setOnLikeListener(new OnLikeListener() {
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

            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.videoComment.setOnClickListener(new View.OnClickListener() {
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

        VideoDetailHorizontalHeaderBinding videoDetailHorizontalHeaderBinding;

        HeaderViewHolder(VideoDetailHorizontalHeaderBinding videoDetailHorizontalHeaderBinding) {
            super(videoDetailHorizontalHeaderBinding.getRoot());
            this.videoDetailHorizontalHeaderBinding = videoDetailHorizontalHeaderBinding;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {


        VideoDetailHorizontalAdapterItemBinding videoDetailHorizontalAdapterItemBinding;

        ItemViewHolder(VideoDetailHorizontalAdapterItemBinding videoDetailHorizontalAdapterItemBinding) {
            super(videoDetailHorizontalAdapterItemBinding.getRoot());
            this.videoDetailHorizontalAdapterItemBinding = videoDetailHorizontalAdapterItemBinding;
        }
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}
