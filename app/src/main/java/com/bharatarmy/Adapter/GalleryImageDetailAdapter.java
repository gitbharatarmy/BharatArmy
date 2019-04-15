package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.alexvasilkov.gestures.views.GestureImageView;
import com.bharatarmy.R;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;



public class GalleryImageDetailAdapter extends RecyclerView.Adapter<GalleryImageDetailAdapter.MyViewHolder> implements View.OnTouchListener {
    Context mContext;
    public List<String> imageList;



    private RecyclerViewOnTouchListener touchListener;

    public GalleryImageDetailAdapter(Context mContext, List<String> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
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
        private ImageView galleryimageListImg;
        private GestureImageView fullimage;

       public MyViewHolder(View view) {
            super(view);
            galleryimageListImg = (ImageView) view.findViewById(R.id.image_detail_img);
            fullimage=(GestureImageView)view.findViewById(R.id.image_full);

        }
    }


    @Override
    public GalleryImageDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_image_detail_list, parent, false);

        return new GalleryImageDetailAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(GalleryImageDetailAdapter.MyViewHolder holder, int position) {
        Glide.with(mContext)
                .load(imageList.get(position))
                .placeholder(R.drawable.progress_animation)
                .into(holder.galleryimageListImg);

        Glide.with(mContext)
                .load(imageList.get(position))
                .placeholder(R.drawable.progress_animation)
                .into(holder.fullimage);

        holder.fullimage.getPositionAnimator().enter(holder.galleryimageListImg, false);


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
