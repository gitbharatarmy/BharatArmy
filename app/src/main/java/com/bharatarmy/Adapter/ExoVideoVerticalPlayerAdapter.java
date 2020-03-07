package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.CommentActivity;
import com.bharatarmy.Activity.ExoVideoHorizontalPlayerActivity;
import com.bharatarmy.Activity.ExoVideoVerticalPlayerActivity;
import com.bharatarmy.Activity.VideoDetailHorizontalActivity;
import com.bharatarmy.Activity.VideoDetailVerticalActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.TargetCallback;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.VideoDetailVerticaleAdapterItemBinding;
import com.bharatarmy.databinding.VideoDetailVerticaleHeaderBinding;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ExoVideoVerticalPlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;
    String videoName, videoUserName, videoLike, videoIdStr;
    ExoVideoVerticalPlayerActivity activity;
    LoginDataModel postedDataModel;
    int likeunlikecount = 0;

    public ExoVideoVerticalPlayerAdapter(Context mContext, ExoVideoVerticalPlayerActivity exoVideoPlayerActivity, List<ImageDetailModel> relatedVideoList,
                                         String videoNameStr, String videoUserNameStr, String videoLike,
                                         LoginDataModel postedDataModel, String videoIdStr) {
        this.mContext = mContext;
        this.relatedVideoList = relatedVideoList;
        this.morestoryClick = morestoryClick;
        this.videoName = videoNameStr;
        this.videoUserName = videoUserNameStr;
        this.activity = exoVideoPlayerActivity;
        this.videoLike = videoLike;
        this.postedDataModel = postedDataModel;
        this.videoIdStr = videoIdStr;
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
                return new ExoVideoVerticalPlayerAdapter.HeaderViewHolder(videoDetailVerticaleHeaderBinding);
            default:

                VideoDetailVerticaleAdapterItemBinding videoDetailVerticaleAdapterItemBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.video_detail_verticale_adapter_item, parent, false);
                return new ExoVideoVerticalPlayerAdapter.ItemViewHolder(videoDetailVerticaleAdapterItemBinding);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            final ImageDetailModel relatedVerticalVideoDetail = relatedVideoList.get(position - 1);
            videoUserName = relatedVerticalVideoDetail.getUserName();
            Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
            Utils.LikeReferenceId = videoIdStr;

            Utils.LikeSourceType = "2";
//            Utils.setImageInImageView(relatedVerticalVideoDetail.getVideoImageURL(),
//                    ((ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRelatedVideoImg, mContext);
            ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.videoPrivacyLinear.setVisibility(View.GONE);
            ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.privateImgLinear.setVisibility(View.GONE);

            ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleVideoSizeTxt.setText(relatedVerticalVideoDetail.getVideoLength());
            ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticalShowVideoTitleTxt.setText(relatedVerticalVideoDetail.getVideoName());
            ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticalShowVideoDescriptionTxt.setText(relatedVerticalVideoDetail.getTitleDescription());

            Picasso.with(mContext).load(relatedVerticalVideoDetail.getVideoImageURL()).placeholder(R.drawable.loader_new)
                    .into(((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRelatedVideoImg,
                            new TargetCallback(((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRelatedVideoImg) {
                                @Override
                                public void onSuccess(ImageView target) {
                                    if (target != null) {
                                        ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.videoPrivacyLinear.setVisibility(View.VISIBLE);
                                        if (relatedVerticalVideoDetail.getIsPrivate().equals(1)) {
                                            ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.privateImgLinear.setVisibility(View.VISIBLE);
                                        } else {
                                            ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.privateImgLinear.setVisibility(View.GONE);
                                        }
                                    }else{
                                        ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.videoPrivacyLinear.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onError(ImageView target) {
                                    ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.videoPrivacyLinear.setVisibility(View.GONE);
                                }
                            });


            if (relatedVerticalVideoDetail.getIsBARecommanded().equals(1)) {
                ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRecommendedImage.setVisibility(View.VISIBLE);
            } else {
                ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleRecommendedImage.setVisibility(View.GONE);
            }
//            verticaleRelatedVideoImg
            ((ExoVideoVerticalPlayerAdapter.ItemViewHolder) holder).videoDetailVerticaleAdapterItemBinding.verticaleVideoLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
                    Utils.LikeReferenceId = videoIdStr;
                    Utils.LikeSourceType = "2";
                    Utils.viewsMemberId = String.valueOf(Utils.getAppUserId(mContext));
                    Utils.viewsReferenceId = videoIdStr;
                    Utils.viewsSourceType = "2";
                    Utils.viewsTokenId = Utils.getPref(mContext, "registration_id");

                    Utils.InsertBAViews(mContext, activity);

                    if (relatedVerticalVideoDetail.getWidth() > relatedVerticalVideoDetail.getHeight()) {
                        AppConfiguration.videoType = "horizontal";
                        Intent videogalleryhorizontaldetailIntent = new Intent(mContext, ExoVideoHorizontalPlayerActivity.class);
                        videogalleryhorizontaldetailIntent.putExtra("videoData", relatedVerticalVideoDetail.getVideoFileURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoName", relatedVerticalVideoDetail.getVideoName());
                        videogalleryhorizontaldetailIntent.putExtra("videoUserName", relatedVerticalVideoDetail.getUserName());
                        videogalleryhorizontaldetailIntent.putExtra("videoLike", String.valueOf(relatedVerticalVideoDetail.getIsLike()));
                        videogalleryhorizontaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryhorizontaldetailIntent.putExtra("videoThumb", relatedVerticalVideoDetail.getVideoImageURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoId", String.valueOf(relatedVerticalVideoDetail.getBAVideoGalleryId()));
                        videogalleryhorizontaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryhorizontaldetailIntent);
                    } else if (relatedVerticalVideoDetail.getWidth() < relatedVerticalVideoDetail.getHeight()) {
                        AppConfiguration.videoType = "vertical";
                        Intent videogalleryverticaldetailIntent = new Intent(mContext, ExoVideoVerticalPlayerActivity.class);
                        videogalleryverticaldetailIntent.putExtra("videoData", relatedVerticalVideoDetail.getVideoFileURL());
                        videogalleryverticaldetailIntent.putExtra("videoName", relatedVerticalVideoDetail.getVideoName());
                        videogalleryverticaldetailIntent.putExtra("videoUserName", relatedVerticalVideoDetail.getUserName());
                        videogalleryverticaldetailIntent.putExtra("videoLike", String.valueOf(relatedVerticalVideoDetail.getIsLike()));
                        videogalleryverticaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryverticaldetailIntent.putExtra("videoThumb", relatedVerticalVideoDetail.getVideoImageURL());
                        videogalleryverticaldetailIntent.putExtra("videoId", String.valueOf(relatedVerticalVideoDetail.getBAVideoGalleryId()));
                        videogalleryverticaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryverticaldetailIntent);
                    } else if (relatedVerticalVideoDetail.getWidth() == relatedVerticalVideoDetail.getHeight()) {
                        AppConfiguration.videoType = "horizontal";
                        Intent videogalleryhorizontaldetailIntent = new Intent(mContext, ExoVideoHorizontalPlayerActivity.class);
                        videogalleryhorizontaldetailIntent.putExtra("videoData", relatedVerticalVideoDetail.getVideoFileURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoName", relatedVerticalVideoDetail.getVideoName());
                        videogalleryhorizontaldetailIntent.putExtra("videoUserName", relatedVerticalVideoDetail.getUserName());
                        videogalleryhorizontaldetailIntent.putExtra("videoLike", String.valueOf(relatedVerticalVideoDetail.getIsLike()));
                        videogalleryhorizontaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                        videogalleryhorizontaldetailIntent.putExtra("videoThumb", relatedVerticalVideoDetail.getVideoImageURL());
                        videogalleryhorizontaldetailIntent.putExtra("videoId", String.valueOf(relatedVerticalVideoDetail.getBAVideoGalleryId()));
                        videogalleryhorizontaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(videogalleryhorizontaldetailIntent);
                    }
                }
            });

        } else if (holder.getItemViewType() == HEADER) {
            ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoMainTitleTxt.setText(videoName);
            ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.typeTxt.setText("Bharat Ke Saath");

            ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoSharenameTxt.setVisibility(View.VISIBLE);
            ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.showVideoSharenameTxt.setText("By " + videoUserName);

            if (postedDataModel != null) {
                if (postedDataModel.getLikes() != null) {
                    if (postedDataModel.getLikes().equals(0)) {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalLikeTxt.setText("");
                    } else {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalLikeTxt
                                .setText(String.valueOf(postedDataModel.getLikes()));
                    }
                }
            }
            if (postedDataModel != null) {
                if (postedDataModel.getPosted() != null) {
                    if (postedDataModel.getPosted().equals(0)) {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalPostedTxt.setText("");
                    } else {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalPostedTxt
                                .setText(String.valueOf(postedDataModel.getPosted()));
                    }
                }
            }
            if (postedDataModel != null) {
                if (postedDataModel.getComments() != null) {
                    if (postedDataModel.getComments().equals(0)) {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalCommentTxt.setText("");
                    } else {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalCommentTxt
                                .setText(String.valueOf(postedDataModel.getComments()));
                    }
                }
            }
            if (postedDataModel != null) {
                if (postedDataModel.getPostView() != null) {
                    if (postedDataModel.getPostView().equals(0)) {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalVideoViewTxt.setText("");
                    } else {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalVideoViewTxt
                                .setText(String.valueOf(postedDataModel.getPostView()));
                    }
                }
            }


            if (videoLike.equalsIgnoreCase("1")) {
                ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoLikeBtn.setLiked(true);
            } else {
                ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoLikeBtn.setLiked(false);
            }
            ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoLikeBtn.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Utils.LikeStatus = "1";
                        Utils.InsertLike(mContext, activity);
                        likeunlikecount = Integer.parseInt(((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalLikeTxt.getText().toString());
                        notifyItemChanged(position,
                                likeunlikecount + 1);
                        EventBus.getDefault().post(new MyScreenChnagesModel(videoIdStr));
                    } else {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoLikeBtn.setLiked(false);
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Utils.LikeStatus = "0";
                        Utils.InsertLike(mContext, activity);
                        likeunlikecount = Integer.parseInt(((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalLikeTxt.getText().toString());
                        notifyItemChanged(position,
                                likeunlikecount - 1);
                        EventBus.getDefault().post(new MyScreenChnagesModel(videoIdStr));
                    } else {
                        ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoLikeBtn.setLiked(true);
                    }
                }
            });

            ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.videoComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isMember(mContext, "galleryDetail")) {
                        Intent commentIntent = new Intent(mContext, CommentActivity.class);
                        commentIntent.putExtra("referenceId", videoIdStr);
                        commentIntent.putExtra("sourceType", "2");
                        commentIntent.putExtra("pageTitle", videoName);
                        mContext.startActivity(commentIntent);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            for (final Object payload : payloads) {
                ((ExoVideoVerticalPlayerAdapter.HeaderViewHolder) holder).videoDetailVerticaleHeaderBinding.totalLikeTxt.setText(payload.toString());
            }
        } else {
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


