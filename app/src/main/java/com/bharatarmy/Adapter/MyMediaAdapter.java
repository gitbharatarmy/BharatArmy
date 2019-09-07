package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MyMediaListItemBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyMediaAdapter extends RecyclerView.Adapter<MyMediaAdapter.MyViewHolder> {
    Context mContext;
    List<GalleryImageModel> imageDetailModel;
    private ArrayList<String> dataCheck;
    int SelectedPosition;
    image_click image_click;
    boolean firstclick=true;
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
            if (!Utils.getPref(mContext, "image/video").equalsIgnoreCase("video")) {
                Utils.setGalleryImageInImageView(detailgallery.getImageUri(), holder.myMediaListItemBinding.uploadImage, mContext);
            } else {
                File f=new File(detailgallery.getImageUri());
                holder.myMediaListItemBinding.uploadImage.setImageBitmap(Utils.createVideoThumbNail(f.toString()));
            }
        if (detailgallery.getUploadcompelet().equalsIgnoreCase("1")) {
            holder.myMediaListItemBinding.uploadsuccesLinear.setVisibility(View.VISIBLE);
            holder.myMediaListItemBinding.uploadStatus.setText("Uploading...");
        } else if (detailgallery.getUploadcompelet().equalsIgnoreCase("2")) {
            holder.myMediaListItemBinding.uploadsuccesLinear.setVisibility(View.VISIBLE);
            holder.myMediaListItemBinding.uploadStatus.setText("Failed");
        }
        else if (detailgallery.getUploadcompelet().equalsIgnoreCase("0")){
            holder.myMediaListItemBinding.uploadsuccesLinear.setVisibility(View.VISIBLE);
            holder.myMediaListItemBinding.uploadStatus.setText("Pending...");
        }

//        holder.myMediaListItemBinding.uploadsuccesLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    if(detailgallery.getUploadcompelet().equalsIgnoreCase("2")){
//                        holder.myMediaListItemBinding.uploadStatus.setText("Pending...");
//                        SelectedPosition = position;
//                        dataCheck=new ArrayList<>();
//                        dataCheck.add(String.valueOf(position)); //+"|"+position
//                        image_click.image_more_click();
//                    }
//            }
//        });


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (!payloads.isEmpty()){
            GalleryImageModel detailgallery = imageDetailModel.get(position);
            for (final Object payload : payloads) {
                if (payload.equals("1")) {
                    // in this case only name will be updated
                    holder.myMediaListItemBinding.uploadsuccesLinear.setVisibility(View.VISIBLE);
                    holder.myMediaListItemBinding.uploadStatus.setText("Uploading...");
                } else if (payload.equals("2")) {
                    // only age will be updated
                    holder.myMediaListItemBinding.uploadsuccesLinear.setVisibility(View.VISIBLE);
                    holder.myMediaListItemBinding.uploadStatus.setText("Retry");
                }else if(payload.equals("0")){
                    holder.myMediaListItemBinding.uploadsuccesLinear.setVisibility(View.VISIBLE);
                    holder.myMediaListItemBinding.uploadStatus.setText("Pending...");
                }
            }
        } else {
            // in this case regular onBindViewHolder will be called
            super.onBindViewHolder(holder, position, payloads);
        }

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
    public int SelectedPosition(){
        return SelectedPosition;
    }

}


