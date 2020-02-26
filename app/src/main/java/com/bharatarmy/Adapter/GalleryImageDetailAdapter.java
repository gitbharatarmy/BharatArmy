package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.method.Touch;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.CommentActivity;
import com.bharatarmy.Fragment.ImageCommentsBottomSheetFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.TargetCallback;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.GalleryImageDetailListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class GalleryImageDetailAdapter extends RecyclerView.Adapter<GalleryImageDetailAdapter.MyViewHolder> implements View.OnTouchListener {
    Context mContext;
    String TAG = "BATOUCH";
    public List<String> imageList;
    ArrayList<String> userNameList;
    ArrayList<String> imageDuration;
    ArrayList<String> imageId;
    ArrayList<String> imageLike;
    Activity activity;
    image_click image_click;
    MorestoryClick morestoryClick;
    private int lastPosition = -1;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    private Animation fadein, fadeout;
    private RecyclerViewOnTouchListener touchListener;
    LinearLayout watchlistLinear;

    public GalleryImageDetailAdapter(Context mContext, Activity activity, ArrayList<String> imageList,
                                     ArrayList<String> imageAddusername, ArrayList<String> imageDuration,
                                     ArrayList<String> imageId, ArrayList<String> imageLike, LinearLayout watchlistLinear,
                                     MorestoryClick morestoryClick,image_click image_click) {
        this.mContext = mContext;
        this.activity = activity;
        this.imageList = imageList;
        this.userNameList = imageAddusername;
        this.imageDuration = imageDuration;
        this.imageId = imageId;
        this.imageLike = imageLike;
        this.watchlistLinear=watchlistLinear;
        this.image_click = image_click;
        this.morestoryClick=morestoryClick;
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
        setAnimation(holder.itemView, position);
//        Utils.setImageInImageView(imageList.get(position), holder.galleryImageDetailListBinding.imageFull, mContext);
        fadein = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);

        holder.galleryImageDetailListBinding.uploadimageUserNametxt.setText(userNameList.get(position));
        holder.galleryImageDetailListBinding.uploadimageDurationtxt.setText(imageDuration.get(position));
        Log.d("userName :", userNameList.get(position));
        holder.galleryImageDetailListBinding.imageFull.getController()
                .getSettings()
                .setPanEnabled(false)
                .setZoomEnabled(false)
                .setDoubleTapEnabled(false);

        holder.galleryImageDetailListBinding.imageBottomLinear.setVisibility(View.GONE);
        watchlistLinear.setVisibility(View.GONE);

        Picasso.with(mContext).load(imageList.get(position)).placeholder(R.drawable.loader_new)
                .into(holder.galleryImageDetailListBinding.imageFull, new TargetCallback(holder.galleryImageDetailListBinding.imageFull) {
                    @Override
                    public void onSuccess(ImageView target) {
                        if (target != null) {
                            morestoryClick.getmorestoryClick();
                            holder.galleryImageDetailListBinding.imageBottomLinear.setVisibility(View.VISIBLE);
                            holder.galleryImageDetailListBinding.imageBottomLinear.startAnimation(fadein);
                            holder.galleryImageDetailListBinding.imageFull.getController()
                                    .getSettings()
                                    .setPanEnabled(true)
                                    .setZoomEnabled(true)
                                    .setDoubleTapEnabled(true);


                            holder.galleryImageDetailListBinding.imageFull.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (holder.galleryImageDetailListBinding.imageBottomLinear.isShown()) {
                                        holder.galleryImageDetailListBinding.imageBottomLinear.setVisibility(View.GONE);
                                        holder.galleryImageDetailListBinding.imageBottomLinear.startAnimation(fadeout);
                                    } else {
                                        holder.galleryImageDetailListBinding.imageBottomLinear.setVisibility(View.VISIBLE);
                                        holder.galleryImageDetailListBinding.imageBottomLinear.startAnimation(fadein);
                                    }
                                }
                            });
                        } else {
                            holder.galleryImageDetailListBinding.imageBottomLinear.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ImageView target) {
                        holder.galleryImageDetailListBinding.imageBottomLinear.setVisibility(View.GONE);
                    }
                });


        if (imageLike.get(position).equalsIgnoreCase("1")) {
            holder.galleryImageDetailListBinding.bottomImageLikeBtn.setLiked(true);
        } else {
            holder.galleryImageDetailListBinding.bottomImageLikeBtn.setLiked(false);
        }


        holder.galleryImageDetailListBinding.bottomImageLikeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (Utils.isMember(mContext, "ImageUpload")) {
                    AppConfiguration.imageLikeId = new ArrayList<>();
                    Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
                    Utils.LikeReferenceId = imageId.get(position);
                    Utils.LikeSourceType = "1";
                    Utils.LikeStatus = "1";
                    Utils.ImageLikeStatus = "1";
                    Utils.InsertLike(mContext, activity);
                    AppConfiguration.imageLikeId.add(Utils.LikeReferenceId);
                    EventBus.getDefault().post(new MyScreenChnagesModel(String.valueOf(position), "like"));
                } else {
                    holder.galleryImageDetailListBinding.bottomImageLikeBtn.setLiked(false);
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (Utils.isMember(mContext, "ImageUpload")) {
                    AppConfiguration.imageLikeId = new ArrayList<>();
                    Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
                    Utils.LikeReferenceId = imageId.get(position);
                    Utils.LikeSourceType = "1";
                    Utils.LikeStatus = "0";
                    Utils.ImageLikeStatus = "0";
                    Utils.InsertLike(mContext, activity);
                    AppConfiguration.imageLikeId.add(Utils.LikeReferenceId);
                    EventBus.getDefault().post(new MyScreenChnagesModel(String.valueOf(position), "like"));
                } else {
                    holder.galleryImageDetailListBinding.bottomImageLikeBtn.setLiked(true);
                }

            }
        });


        holder.galleryImageDetailListBinding.commentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isMember(mContext, "ImageUpload")) {
                    Intent commentIntent = new Intent(mContext, CommentActivity.class);
                    commentIntent.putExtra("referenceId", imageId.get(position));
                    commentIntent.putExtra("sourceType", "1");
                    commentIntent.putExtra("pageTitle", "Gallery Comments");
                    mContext.startActivity(commentIntent);
                }
            }
        });

        holder.galleryImageDetailListBinding.shareArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext, holder.galleryImageDetailListBinding.shareArticle);
                if (Utils.isMember(mContext, "ImageUpload")) {
//                    dataCheck = new ArrayList<>();
//                    dataCheck.add("Share");
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

    public List<String> getCommentsParameter() {
        return dataCheck;
    }
}
