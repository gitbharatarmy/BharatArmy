package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MyMediaListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MyMediaAdapter extends RecyclerView.Adapter<MyMediaAdapter.MyViewHolder> {
    Context mContext;
    List<GalleryImageModel> imageDetailModel;
    private ArrayList<String> dataCheck;
    image_click image_click;
    public MyMediaAdapter(Context mContext, List<GalleryImageModel> content, image_click image_click) {
        this.mContext = mContext;
        this.imageDetailModel = content;
        this.image_click=image_click;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyMediaListItemBinding myMediaListItemBinding;

        public MyViewHolder(MyMediaListItemBinding myMediaListItemBinding) {
            super(myMediaListItemBinding.getRoot());

            this.myMediaListItemBinding = myMediaListItemBinding;
        }
    }


    @Override
    public MyMediaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        MyMediaListItemBinding myMediaListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.my_media_list_item, parent, false);
        return new MyMediaAdapter.MyViewHolder(myMediaListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyMediaAdapter.MyViewHolder holder, final int position) {
        GalleryImageModel detailgallery = imageDetailModel.get(position);
        Utils.setImageInImageView(detailgallery.getImageUri(), holder.myMediaListItemBinding.uploadImage, mContext);

        if (detailgallery.getUploadcompelet().equalsIgnoreCase("1")) {
            holder.myMediaListItemBinding.uploadsuccesLinear.setVisibility(View.VISIBLE);
            holder.myMediaListItemBinding.uploadImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_mark));
        } else if (detailgallery.getUploadcompelet().equalsIgnoreCase("")) {
            holder.myMediaListItemBinding.uploadsuccesLinear.setVisibility(View.VISIBLE);
            holder.myMediaListItemBinding.uploadImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_upload_retry));
        }

        holder.myMediaListItemBinding.uploadsuccesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailgallery.getUploadcompelet().equalsIgnoreCase("")){
                    dataCheck=new ArrayList<>();
                    dataCheck.add(detailgallery.getImageUri());
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
        return imageDetailModel.size();
    }
    public ArrayList<String> getDatas() {
        return dataCheck;
    }

}


