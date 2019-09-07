package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.GalleryImageDetailListBinding;

import java.util.ArrayList;
import java.util.List;



public class GalleryImageDetailAdapter extends RecyclerView.Adapter<GalleryImageDetailAdapter.MyViewHolder> implements View.OnTouchListener {
    Context mContext;
    public List<String> imageList;
    ArrayList<String> userNameList;


    private RecyclerViewOnTouchListener touchListener;

    public GalleryImageDetailAdapter(Context mContext, ArrayList<String> imageList, ArrayList<String> imageAddusername) {
        this.mContext = mContext;
        this.imageList = imageList;
    this.userNameList=imageAddusername;
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
            this.galleryImageDetailListBinding=galleryImageDetailListBinding;
        }
    }


    @Override
    public GalleryImageDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GalleryImageDetailListBinding galleryImageDetailListBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.gallery_image_detail_list,parent,false);
        return new GalleryImageDetailAdapter.MyViewHolder(galleryImageDetailListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(GalleryImageDetailAdapter.MyViewHolder holder, int position) {
        Utils.setImageInImageView(imageList.get(position),holder.galleryImageDetailListBinding.imageFull,mContext);
        holder.galleryImageDetailListBinding.imageFull.getPositionAnimator().enter(holder.galleryImageDetailListBinding.imageDetailImg, false);
holder.galleryImageDetailListBinding.uploadimageUserNametxt.setText(userNameList.get(position));
Log.d("userName :",userNameList.get(position));
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
