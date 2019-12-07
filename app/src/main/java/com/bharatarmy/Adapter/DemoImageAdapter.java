package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.bharatarmy.databinding.SelectedImageVideoListItemBinding;

import java.io.File;
import java.util.List;

public class DemoImageAdapter extends RecyclerView.Adapter<DemoImageAdapter.MyViewHolder> {
    Context mContext;
    List<Uri> urlList;
    com.bharatarmy.Interfaces.image_click image_click;
    int selectedpositionRemove;
    String imageRemoveName;
    List<String> imageDetailModel;

    public DemoImageAdapter(Context mContext, List<String> imageDetailModel) {
        this.mContext = mContext;
        this.urlList = urlList;
        this.image_click = image_click;
        this.imageDetailModel = imageDetailModel;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        SelectedImageVideoListItemBinding selectedImageVideoListItemBinding;

        public MyViewHolder(SelectedImageVideoListItemBinding selectedImageVideoListItemBinding) {
            super(selectedImageVideoListItemBinding.getRoot());

            this.selectedImageVideoListItemBinding = selectedImageVideoListItemBinding;
        }
    }


    @Override
    public DemoImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        SelectedImageVideoListItemBinding selectedImageVideoListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.selected_image_video_list_item, parent, false);
        return new DemoImageAdapter.MyViewHolder(selectedImageVideoListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(DemoImageAdapter.MyViewHolder holder, final int position) {
        Utils.setImageInImageView(imageDetailModel.get(position), holder.selectedImageVideoListItemBinding.selectedImage, mContext);

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


}


