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
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.VideoDetailHorizontalAdapterItemBinding;
import com.bharatarmy.databinding.VideoDetailHorizontalHeaderBinding;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailHorizontalVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;
    String videoName, videoUserName, videoLike,videoIdStr;
    VideoDetailHorizontalActivity activity;
    String referenceId;
    LoginDataModel postedDataList;
    int likeunlikecount=0;

    public VideoDetailHorizontalVideoAdapter(Context mContext, VideoDetailHorizontalActivity videoDetailActivity, List<ImageDetailModel> relatedVideoList,
                                             String videoNameStr, String videoUserNameStr, String videoLike,
                                             LoginDataModel postedDataList, String videoIdStr) {
        this.mContext = mContext;
        this.relatedVideoList = relatedVideoList;
        this.videoName = videoNameStr;
        this.videoUserName = videoUserNameStr;
        this.activity = videoDetailActivity;
        this.videoLike = videoLike;
        this.postedDataList=postedDataList;
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
            Utils.LikeReferenceId = Integer.parseInt(videoIdStr);
            Utils.LikeSourceType = 2;
            Utils.setImageInImageView(relatedHorizontalVideoDetail.getVideoImageURL(),
                    ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalRelatedVideoImg, mContext);

            ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalVideoSizeTxt.setText(relatedHorizontalVideoDetail.getVideoLength());
            ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalShowVideoTitleTxt.setText(relatedHorizontalVideoDetail.getVideoName());
            ((ItemViewHolder)holder).videoDetailHorizontalAdapterItemBinding.horizontalShowVideoDescriptionTxt.setText(relatedHorizontalVideoDetail.getTitleDescription());


            if (relatedHorizontalVideoDetail.getIsBARecommanded().equals(1)) {
                ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalRecommendedImage.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalRecommendedImage.setVisibility(View.GONE);
            }

            ((ItemViewHolder) holder).videoDetailHorizontalAdapterItemBinding.horizontalRelatedVideoImg.setOnClickListener(new View.OnClickListener() {
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

                    if (relatedHorizontalVideoDetail.getWidth()> relatedHorizontalVideoDetail.getHeight()){
                        AppConfiguration.videoType="horizontal";
                        Intent videogalleryhorizontaldetailIntent = new Intent(mContext, VideoDetailHorizontalActivity.class);
                        videogalleryhorizontaldetailIntent.putExtra("videoData", relatedHorizontalVideoDetail.getVideoFileURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoName", relatedHorizontalVideoDetail.getVideoName());
                        videogalleryhorizontaldetailIntent.putExtra("videoUserName", relatedHorizontalVideoDetail.getUserName());
                        videogalleryhorizontaldetailIntent.putExtra("videoLike", String.valueOf(relatedHorizontalVideoDetail.getIsLike()));
                        videogalleryhorizontaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryhorizontaldetailIntent.putExtra("videoThumb",relatedHorizontalVideoDetail.getVideoImageURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoId",String.valueOf(relatedHorizontalVideoDetail.getBAVideoGalleryId()));
                        videogalleryhorizontaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryhorizontaldetailIntent);
                    }else if(relatedHorizontalVideoDetail.getWidth()<relatedHorizontalVideoDetail.getHeight()){
                        AppConfiguration.videoType="vertical";
                        Intent videogalleryverticaldetailIntent = new Intent(mContext, VideoDetailVerticalActivity.class);
                        videogalleryverticaldetailIntent.putExtra("videoData", relatedHorizontalVideoDetail.getVideoFileURL());
                        videogalleryverticaldetailIntent.putExtra("videoName", relatedHorizontalVideoDetail.getVideoName());
                        videogalleryverticaldetailIntent.putExtra("videoUserName", relatedHorizontalVideoDetail.getUserName());
                        videogalleryverticaldetailIntent.putExtra("videoLike", String.valueOf(relatedHorizontalVideoDetail.getIsLike()));
                        videogalleryverticaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryverticaldetailIntent.putExtra("videoThumb",relatedHorizontalVideoDetail.getVideoImageURL());
                        videogalleryverticaldetailIntent.putExtra("videoId",String.valueOf(relatedHorizontalVideoDetail.getBAVideoGalleryId()));
                        videogalleryverticaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryverticaldetailIntent);
                    } else if(relatedHorizontalVideoDetail.getWidth()==relatedHorizontalVideoDetail.getHeight()){
                        AppConfiguration.videoType="horizontal";
                        Intent videogalleryhorizontaldetailIntent = new Intent(mContext, VideoDetailHorizontalActivity.class);
                        videogalleryhorizontaldetailIntent.putExtra("videoData", relatedHorizontalVideoDetail.getVideoFileURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoName", relatedHorizontalVideoDetail.getVideoName());
                        videogalleryhorizontaldetailIntent.putExtra("videoUserName", relatedHorizontalVideoDetail.getUserName());
                        videogalleryhorizontaldetailIntent.putExtra("videoLike", String.valueOf(relatedHorizontalVideoDetail.getIsLike()));
                        videogalleryhorizontaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryhorizontaldetailIntent.putExtra("videoThumb",relatedHorizontalVideoDetail.getVideoImageURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoId",String.valueOf(relatedHorizontalVideoDetail.getBAVideoGalleryId()));
                        videogalleryhorizontaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryhorizontaldetailIntent);
                    }
                }
            });

        } else if (holder.getItemViewType() == HEADER) {
            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.showVideoMainTitleTxt.setText(videoName);
            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.typeTxt.setText("Bharat Ke Saath");

            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.showVideoSharenameTxt.setVisibility(View.VISIBLE);
            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.showVideoSharenameTxt.setText("By " + videoUserName);

            if (postedDataList!=null){
                if (postedDataList.getLikes()!=null){
                    if (postedDataList.getLikes().equals(0)){
                        ((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalLikeTxt.setText("");
                    }else{
                        ((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalLikeTxt
                                .setText(String.valueOf(postedDataList.getLikes()));
                    }
                }
            }
            if (postedDataList!=null){
                if (postedDataList.getPosted()!=null){
                    if (postedDataList.getPosted().equals(0)){
                        ((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalPostedTxt.setText("");
                    }else{
                        ((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalPostedTxt
                                .setText(String.valueOf(postedDataList.getPosted()));
                    }
                }
            }
            if (postedDataList!=null){
                if (postedDataList.getComments()!=null){
                    if (postedDataList.getComments().equals(0)){
                        ((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalCommentTxt.setText("");
                    }else{
                        ((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalCommentTxt
                                .setText(String.valueOf(postedDataList.getComments()));
                    }
                }
            }
            if (postedDataList!=null){
                if (postedDataList.getPostView()!=null){
                    if (postedDataList.getPostView().equals(0)){
                        ((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalVideoViewTxt.setText("");
                    }else{
                        ((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalVideoViewTxt
                                .setText(String.valueOf(postedDataList.getPostView()));
                    }
                }
            }


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
                        likeunlikecount= Integer.parseInt(((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalLikeTxt.getText().toString());
                        notifyItemChanged(position,
                                likeunlikecount+1);

                        EventBus.getDefault().post(new MyScreenChnagesModel(videoIdStr));
                    }else{
                        ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.videoLikeBtn.setLiked(false);
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Utils.LikeStatus = 0;
                        Utils.InsertLike(mContext, activity);
                        likeunlikecount= Integer.parseInt(((HeaderViewHolder)holder).videoDetailHorizontalHeaderBinding.totalLikeTxt.getText().toString());
                        notifyItemChanged(position,
                                likeunlikecount-1);

                        EventBus.getDefault().post(new MyScreenChnagesModel(videoIdStr));
                    }else{
                        ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.videoLikeBtn.setLiked(true);
                    }
                }
            });

            ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.videoComment.setOnClickListener(new View.OnClickListener() {
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
                ((HeaderViewHolder) holder).videoDetailHorizontalHeaderBinding.totalLikeTxt.setText(payload.toString());
            }
        }else {
            super.onBindViewHolder(holder, position, payloads);
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
