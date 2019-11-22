package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AlbumImageVideoShowActivity;
import com.bharatarmy.Activity.CommentActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.AlbumImageVideoListItemBinding;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


public class AlbumImageVideoAdapter extends RecyclerView.Adapter<AlbumImageVideoAdapter.MyViewHolder> {
    Context mContext;

    private int lastPosition = -1;

    ArrayList<String> albumImageUrl;
    ArrayList<String> albumImageThumbUrl;
    ArrayList<String> albumMediaType;
    MediaController mediaController;
    ArrayList<String> albumLike;
    ArrayList<String> albumDuration;
    ArrayList<String> albumAddUser;
    ArrayList<String> albumId;
    Activity activity;
    image_click imageClick;

    public AlbumImageVideoAdapter(Context mContext, Activity activity, ArrayList<String> albumImageUrl, ArrayList<String> albumImageThumbUrl,
                                  ArrayList<String> albumMediaType, ArrayList<String> albumLike, ArrayList<String> albumDuration,
                                  ArrayList<String> albumAddUser, ArrayList<String> albumId, image_click imageClick) {

        this.mContext = mContext;
        this.albumImageUrl = albumImageUrl;
        this.albumImageThumbUrl = albumImageThumbUrl;
        this.albumMediaType = albumMediaType;
        this.albumLike=albumLike;
        this.albumDuration=albumDuration;
        this.albumAddUser=albumAddUser;
        this.albumId=albumId;
        this.activity=activity;
        this.imageClick=imageClick;
    }

    


    public class MyViewHolder extends RecyclerView.ViewHolder {

        AlbumImageVideoListItemBinding albumImageVideoListItemBinding;

        public MyViewHolder(AlbumImageVideoListItemBinding albumImageVideoListItemBinding) {
            super(albumImageVideoListItemBinding.getRoot());
            this.albumImageVideoListItemBinding = albumImageVideoListItemBinding;
        }
    }


    @Override
    public AlbumImageVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlbumImageVideoListItemBinding albumImageVideoListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.album_image_video_list_item, parent, false);
        return new AlbumImageVideoAdapter.MyViewHolder(albumImageVideoListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(AlbumImageVideoAdapter.MyViewHolder holder, int position) {
        setAnimation(holder.itemView, position);

        if (albumMediaType.get(position).equalsIgnoreCase("1")) {
            holder.albumImageVideoListItemBinding.showAlbumImage.setVisibility(View.VISIBLE);
            holder.albumImageVideoListItemBinding.playAlbumvideo.setVisibility(View.GONE);
            holder.albumImageVideoListItemBinding.videoViewThumbnail.setVisibility(View.GONE);
            holder.albumImageVideoListItemBinding.imageProgress.setVisibility(View.GONE);
            Utils.setImageInImageView(albumImageUrl.get(position), holder.albumImageVideoListItemBinding.showAlbumImage, mContext);
        }else if (albumMediaType.get(position).equalsIgnoreCase("2")){
            holder.albumImageVideoListItemBinding.imageProgress.setVisibility(View.VISIBLE);
            holder.albumImageVideoListItemBinding.playAlbumvideo.setVisibility(View.VISIBLE);
            holder.albumImageVideoListItemBinding.showAlbumImage.setVisibility(View.GONE);
            holder.albumImageVideoListItemBinding.videoViewThumbnail.setVisibility(View.VISIBLE);

            Utils.setImageInImageView(albumImageThumbUrl.get(position), holder.albumImageVideoListItemBinding.videoViewThumbnail, mContext);

            mediaController = new MediaController(mContext);
            mediaController.setAnchorView(holder.albumImageVideoListItemBinding.playAlbumvideo);
            holder.albumImageVideoListItemBinding.playAlbumvideo.setMediaController(mediaController);
            holder.albumImageVideoListItemBinding.playAlbumvideo.requestFocus();

            holder.albumImageVideoListItemBinding.playAlbumvideo.setVideoPath(albumImageUrl.get(position));

            holder.albumImageVideoListItemBinding.playAlbumvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    holder.albumImageVideoListItemBinding.videoViewThumbnail.setVisibility(View.GONE);
                    holder.albumImageVideoListItemBinding.imageProgress.setVisibility(View.GONE);
                    holder.albumImageVideoListItemBinding.playAlbumvideo.start();

                }
            });
        }


        holder.albumImageVideoListItemBinding.uploadimageUserNametxt.setText(albumAddUser.get(position));
        holder.albumImageVideoListItemBinding.uploadimageDurationtxt.setText(albumDuration.get(position));
        Log.d("userName :", albumAddUser.get(position));


        if (albumLike.get(position).equalsIgnoreCase("1")){
            holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setLiked(true);
        }else {
            holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setLiked(false);
        }
        holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (Utils.isMember(mContext,"ImageUpload")){
                    Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
                    Utils.LikeReferenceId = albumId.get(position);
                    Utils.LikeSourceType = albumMediaType.get(position);
                    Utils.LikeStatus = "1";
                    Utils.InsertLike(mContext, activity);

                    EventBus.getDefault().post(new MyScreenChnagesModel(position));
                }else{
                    holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setLiked(false);
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (Utils.isMember(mContext,"ImageUpload")){
                    Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
                    Utils.LikeReferenceId = albumId.get(position);
                    Utils.LikeSourceType = albumMediaType.get(position);
                    Utils.LikeStatus = "0";
                    Utils.InsertLike(mContext, activity);
                    EventBus.getDefault().post(new MyScreenChnagesModel(position));
                }else{
                    holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setLiked(true);
                }

            }
        });


        holder.albumImageVideoListItemBinding.commentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isMember(mContext,"ImageUpload")){
                    Intent commentIntent = new Intent(mContext, CommentActivity.class);
                    commentIntent.putExtra("referenceId",albumId.get(position));
                    commentIntent.putExtra("sourceType",albumMediaType.get(position));
                    commentIntent.putExtra("pageTitle","Album Comments");
                    mContext.startActivity(commentIntent);
                }
            }
        });

        holder.albumImageVideoListItemBinding.shareArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext,holder.albumImageVideoListItemBinding.shareArticle);
                if (Utils.isMember(mContext,"ImageUpload")){
                    imageClick.image_more_click();
                }

            }
        });
    }


    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return albumImageUrl.size();
    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
            animation.setDuration(500);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        } else if (position < lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left_new);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
