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
import com.bharatarmy.Activity.VideoDetailVerticalActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.VideoDetailHorizontalAdapterItemBinding;
import com.bharatarmy.databinding.VideoDetailHorizontalHeaderBinding;
import com.bharatarmy.databinding.VideoDetailVerticaleAdapterItemBinding;
import com.bharatarmy.databinding.VideoDetailVerticaleHeaderBinding;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;
    String videoName, videoUserName, videoLike,videoIdStr;
    VideoDetailVerticalActivity activity;
    LoginDataModel postedDataModel;
    int likeunlikecount=0;

    public VideoDetailVerticalAdapter(Context mContext, VideoDetailVerticalActivity videoDetailActivity, List<ImageDetailModel> relatedVideoList,
                                      String videoNameStr, String videoUserNameStr, String videoLike,
                                      LoginDataModel postedDataModel, String videoIdStr) {
        this.mContext = mContext;
        this.relatedVideoList = relatedVideoList;
        this.morestoryClick = morestoryClick;
        this.videoName = videoNameStr;
        this.videoUserName = videoUserNameStr;
        this.activity = videoDetailActivity;
        this.videoLike = videoLike;
        this.postedDataModel=postedDataModel;
        this.videoIdStr=videoIdStr;
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
            final ImageDetailModel relatedVerticalVideoDetail = relatedVideoList.get(position - 1);
            videoUserName = relatedVerticalVideoDetail.getUserName();

            Utils.LikeMemberId = Utils.getAppUserId(mContext);
            Utils.LikeReferenceId = Integer.parseInt(videoIdStr);

            Utils.LikeSourceType = 2;
            Utils.setImageInImageView(relatedVerticalVideoDetail.getVideoImageURL(),
                    ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRelatedVideoImg, mContext);

            ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleVideoSizeTxt.setText(relatedVerticalVideoDetail.getVideoLength());
            ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticalShowVideoTitleTxt.setText(relatedVerticalVideoDetail.getVideoName());
            ((ItemViewHolder)holder).videoDetailVerticaleAdapterItemBinding.verticalShowVideoDescriptionTxt.setText(relatedVerticalVideoDetail.getTitleDescription());


            if (relatedVerticalVideoDetail.getIsBARecommanded().equals(1)) {
                ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRecommendedImage.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRecommendedImage.setVisibility(View.GONE);
            }

            ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRelatedVideoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.LikeMemberId = Utils.getAppUserId(mContext);
                    Utils.LikeReferenceId = Integer.parseInt(videoIdStr);
                    Utils.LikeSourceType = 2;
                    Utils.viewsMemberId=String.valueOf(Utils.getAppUserId(mContext));
                    Utils.viewsReferenceId=videoIdStr;
                    Utils.viewsSourceType="2";
                    Utils.viewsTokenId= Utils.getPref(mContext, "registration_id");
                    Utils.InsertBAViews(mContext,activity);

                    if (relatedVerticalVideoDetail.getWidth()> relatedVerticalVideoDetail.getHeight()){
                        AppConfiguration.videoType="horizontal";
                        Intent videogalleryhorizontaldetailIntent = new Intent(mContext, VideoDetailHorizontalActivity.class);
                        videogalleryhorizontaldetailIntent.putExtra("videoData", relatedVerticalVideoDetail.getVideoFileURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoName", relatedVerticalVideoDetail.getVideoName());
                        videogalleryhorizontaldetailIntent.putExtra("videoUserName", relatedVerticalVideoDetail.getUserName());
                        videogalleryhorizontaldetailIntent.putExtra("videoLike", String.valueOf(relatedVerticalVideoDetail.getIsLike()));
                        videogalleryhorizontaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryhorizontaldetailIntent.putExtra("videoThumb",relatedVerticalVideoDetail.getVideoImageURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoId",String.valueOf(relatedVerticalVideoDetail.getBAVideoGalleryId()));
                        videogalleryhorizontaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryhorizontaldetailIntent);
                    }else if(relatedVerticalVideoDetail.getWidth()<relatedVerticalVideoDetail.getHeight()){
                        AppConfiguration.videoType="vertical";
                        Intent videogalleryverticaldetailIntent = new Intent(mContext, VideoDetailVerticalActivity.class);
                        videogalleryverticaldetailIntent.putExtra("videoData", relatedVerticalVideoDetail.getVideoFileURL());
                        videogalleryverticaldetailIntent.putExtra("videoName", relatedVerticalVideoDetail.getVideoName());
                        videogalleryverticaldetailIntent.putExtra("videoUserName", relatedVerticalVideoDetail.getUserName());
                        videogalleryverticaldetailIntent.putExtra("videoLike", String.valueOf(relatedVerticalVideoDetail.getIsLike()));
                        videogalleryverticaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryverticaldetailIntent.putExtra("videoThumb",relatedVerticalVideoDetail.getVideoImageURL());
                        videogalleryverticaldetailIntent.putExtra("videoId",String.valueOf(relatedVerticalVideoDetail.getBAVideoGalleryId()));
                        videogalleryverticaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryverticaldetailIntent);
                    } else if(relatedVerticalVideoDetail.getWidth()==relatedVerticalVideoDetail.getHeight()){
                        AppConfiguration.videoType="horizontal";
                        Intent videogalleryhorizontaldetailIntent = new Intent(mContext, VideoDetailHorizontalActivity.class);
                        videogalleryhorizontaldetailIntent.putExtra("videoData", relatedVerticalVideoDetail.getVideoFileURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoName", relatedVerticalVideoDetail.getVideoName());
                        videogalleryhorizontaldetailIntent.putExtra("videoUserName", relatedVerticalVideoDetail.getUserName());
                        videogalleryhorizontaldetailIntent.putExtra("videoLike", String.valueOf(relatedVerticalVideoDetail.getIsLike()));
                        videogalleryhorizontaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryhorizontaldetailIntent.putExtra("videoThumb",relatedVerticalVideoDetail.getVideoImageURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoId",String.valueOf(relatedVerticalVideoDetail.getBAVideoGalleryId()));
                        videogalleryhorizontaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryhorizontaldetailIntent);
                    }
                }
            });

        } else if (holder.getItemViewType() == HEADER) {
            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoMainTitleTxt.setText(videoName);
            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.typeTxt.setText("Bharat Ke Saath");

            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoSharenameTxt.setVisibility(View.VISIBLE);
            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoSharenameTxt.setText("By " + videoUserName);

            if (postedDataModel!=null){
                if (postedDataModel.getLikes()!=null){
                    if (postedDataModel.getLikes().equals(0)){
                        ((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalLikeTxt.setText("");
                    }else{
                        ((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalLikeTxt
                                .setText(String.valueOf(postedDataModel.getLikes()));
                    }
                }
            }
            if (postedDataModel!=null){
                if (postedDataModel.getPosted()!=null){
                    if (postedDataModel.getPosted().equals(0)){
                        ((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalPostedTxt.setText("");
                    }else{
                        ((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalPostedTxt
                                .setText(String.valueOf(postedDataModel.getPosted()));
                    }
                }
            }
            if (postedDataModel!=null){
                if (postedDataModel.getComments()!=null){
                    if (postedDataModel.getComments().equals(0)){
                        ((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalCommentTxt.setText("");
                    }else{
                        ((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalCommentTxt
                                .setText(String.valueOf(postedDataModel.getComments()));
                    }
                }
            }
            if (postedDataModel!=null){
                if (postedDataModel.getPostView()!=null){
                    if (postedDataModel.getPostView().equals(0)){
                        ((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalVideoViewTxt.setText("");
                    }else{
                        ((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalVideoViewTxt
                                .setText(String.valueOf(postedDataModel.getPostView()));
                    }
                }
            }


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
                        likeunlikecount= Integer.parseInt(((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalLikeTxt.getText().toString());
                        notifyItemChanged(position,
                                likeunlikecount+1);
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Utils.LikeStatus = 0;
                        Utils.InsertLike(mContext, activity);
                        likeunlikecount= Integer.parseInt(((HeaderViewHolder)holder).videoDetailVerticaleHeaderBinding.totalLikeTxt.getText().toString());
                        notifyItemChanged(position,
                                likeunlikecount-1);
                    }
                }
            });

            ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Intent commentIntent = new Intent(mContext, CommentActivity.class);
                        commentIntent.putExtra("referenceId", videoIdStr);
                        commentIntent.putExtra("sourceType", "2");
                        commentIntent.putExtra("pageTitle",videoName);
                        mContext.startActivity(commentIntent);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()){
            for (final Object payload : payloads) {
                ((HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalLikeTxt.setText(payload.toString());
            }
        }else {
            super.onBindViewHolder(holder, position, payloads);
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

