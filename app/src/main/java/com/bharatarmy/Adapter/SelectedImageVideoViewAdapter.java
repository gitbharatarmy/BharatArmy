package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.ImageUploadActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.SelectedImageVideoListItemBinding;

import java.io.File;
import java.util.List;

public class SelectedImageVideoViewAdapter extends RecyclerView.Adapter<SelectedImageVideoViewAdapter.MyViewHolder> {
    Context mContext;
    List<Uri> urlList;
    image_click image_click;
    int selectedpositionRemove;
    String imageRemoveName;
    List<GalleryImageModel> imageDetailModel;
    ImageUploadActivity activity;
    public SelectedImageVideoViewAdapter(Context mContext, List<GalleryImageModel> imageDetailModel, ImageUploadActivity activity, image_click image_click) {
        this.mContext = mContext;
        this.urlList = urlList;
        this.image_click = image_click;
        this.imageDetailModel = imageDetailModel;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        SelectedImageVideoListItemBinding selectedImageVideoListItemBinding;

        public MyViewHolder(SelectedImageVideoListItemBinding selectedImageVideoListItemBinding) {
            super(selectedImageVideoListItemBinding.getRoot());

            this.selectedImageVideoListItemBinding = selectedImageVideoListItemBinding;
        }
    }


    @Override
    public SelectedImageVideoViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        SelectedImageVideoListItemBinding selectedImageVideoListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.selected_image_video_list_item, parent, false);
        return new SelectedImageVideoViewAdapter.MyViewHolder(selectedImageVideoListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SelectedImageVideoViewAdapter.MyViewHolder holder, final int position) {

        if (imageDetailModel.size()-1 == position){
            holder.selectedImageVideoListItemBinding.dividerBottom.setVisibility(View.GONE);
        }else{
            holder.selectedImageVideoListItemBinding.dividerBottom.setVisibility(View.VISIBLE);
        }


        Utils.setGalleryImageInImageView(imageDetailModel.get(position).getImageUri(),holder.selectedImageVideoListItemBinding.selectedImage,mContext);

        File file = new File(imageDetailModel.get(position).getImageUri());
        String name = file.getName();

        holder.selectedImageVideoListItemBinding.selectedImageSizeTxt.setText(imageDetailModel.get(position).getImageSize());
        holder.selectedImageVideoListItemBinding.selectedImageNameTxt.setText(name);

        holder.selectedImageVideoListItemBinding.closeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.feedback_back_dialog, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                TextView dialog_headertxt = (TextView) dialogView.findViewById(R.id.dialog_headertxt);
                TextView dialog_descriptiontxt = (TextView) dialogView.findViewById(R.id.dialog_descriptiontxt);
                TextView cancel_txt =(TextView)dialogView.findViewById(R.id.no_txt);
                TextView done_txt =(TextView)dialogView.findViewById(R.id.yes_txt);

                dialog_headertxt.setText("Remove Selected Image");
                dialog_descriptiontxt.setText("Are you sure you want delete?");

                done_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageRemoveName=imageDetailModel.get(position).getImageUri();
                        selectedpositionRemove = position;
                        image_click.image_more_click();
                        alertDialog.dismiss();
                    }
                });

                cancel_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                try {
                    alertDialog.show();
                } catch (Exception e) {

                }



//                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(mContext);
//                alertDialog2.setTitle("Delete");
//                alertDialog2.setMessage("Are you sure you want delete?");
//                alertDialog2.setIcon(R.drawable.app_logo_new);
//                alertDialog2.setCancelable(false);
//                alertDialog2.setPositiveButton("YES",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Write your code here to execute after dialog
//                                imageRemoveName=imageDetailModel.get(position).getImageUri();
//                                selectedpositionRemove = position;
//                                image_click.image_more_click();
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog2.setNegativeButton("NO",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Write your code here to execute after dialog
//
//                                dialog.cancel();
//                            }
//                        });
//                alertDialog2.show();
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

    public String imageRemoveName(){
        return imageRemoveName;
    }

    public int selectedpositionRemove() {
        return selectedpositionRemove;
    }

}


