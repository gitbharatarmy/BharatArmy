package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.CommentActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.GalleryImageDetailListBinding;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;


public class GalleryImageDetailAdapter extends RecyclerView.Adapter<GalleryImageDetailAdapter.MyViewHolder> implements View.OnTouchListener {
    Context mContext;
    public List<String> imageList;
    ArrayList<String> userNameList;
    ArrayList<String> imageDuration;
    ArrayList<String> imageId;
    Activity activity;

    image_click image_click;

    private RecyclerViewOnTouchListener touchListener;

    public GalleryImageDetailAdapter(Context mContext, Activity activity, ArrayList<String> imageList,
                                     ArrayList<String> imageAddusername, ArrayList<String> imageDuration,
                                     ArrayList<String> imageId, image_click image_click) {
        this.mContext = mContext;
        this.imageList = imageList;
        this.userNameList = imageAddusername;
        this.imageDuration = imageDuration;
        this.imageId = imageId;
        this.activity = activity;
        this.image_click=image_click;
    }

    public interface RecyclerViewOnTouchListener {
        void onTouch(View v, MotionEvent event);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (touchListener != null) {
            touchListener.onTouch((ImageView) v, event);
        }
        return false;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        GalleryImageDetailListBinding galleryImageDetailListBinding;

        public MyViewHolder(GalleryImageDetailListBinding galleryImageDetailListBinding) {
            super(galleryImageDetailListBinding.getRoot());
            this.galleryImageDetailListBinding = galleryImageDetailListBinding;
        }
    }


    @Override
    public GalleryImageDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GalleryImageDetailListBinding galleryImageDetailListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.gallery_image_detail_list, parent, false);
        return new GalleryImageDetailAdapter.MyViewHolder(galleryImageDetailListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(GalleryImageDetailAdapter.MyViewHolder holder, int position) {
        Utils.setImageInImageView(imageList.get(position), holder.galleryImageDetailListBinding.imageFull, mContext);
//        holder.galleryImageDetailListBinding.imageFull.getPositionAnimator().enter(holder.galleryImageDetailListBinding.imageDetailImg, false);
        holder.galleryImageDetailListBinding.uploadimageUserNametxt.setText(userNameList.get(position));
        holder.galleryImageDetailListBinding.uploadimageDurationtxt.setText(imageDuration.get(position));
        Log.d("userName :", userNameList.get(position));

        Utils.LikeMemberId = Utils.getAppUserId(mContext);
        Utils.LikeReferenceId = Integer.parseInt(imageId.get(position));
        Utils.LikeSourceType = 1;
        holder.galleryImageDetailListBinding.bottomImageLikeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (Utils.isMember(mContext,"galleryDetail")){
                    Utils.LikeStatus = 1;
                    Utils.InsertLike(mContext, activity);
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (Utils.isMember(mContext,"galleryDetail")){
                    Utils.LikeStatus = 0;
                    Utils.InsertLike(mContext, activity);
                }

            }
        });

        holder.galleryImageDetailListBinding.commentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isMember(mContext,"galleryDetail")){
                    Intent commentIntent = new Intent(mContext, CommentActivity.class);
                    commentIntent.putExtra("referenceId",imageId.get(position));
                    commentIntent.putExtra("sourceType","1");
                    mContext.startActivity(commentIntent);
                }
            }
        });

        holder.galleryImageDetailListBinding.shareArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isMember(mContext,"galleryDetail")){
                    image_click.image_more_click();
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
        return imageList.size();
    }


}
