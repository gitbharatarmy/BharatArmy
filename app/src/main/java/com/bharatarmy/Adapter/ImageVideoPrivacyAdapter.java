package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.ImageVideoPrivacyListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageVideoPrivacyAdapter extends RecyclerView.Adapter<ImageVideoPrivacyAdapter.MyViewHolder> {
    Context mContext;
    List<GalleryImageModel> privacyoptionList;
    MorestoryClick morestoryClick;
    int selectedposition;
    private ArrayList<String> dataCheck = new ArrayList<String>();



    public ImageVideoPrivacyAdapter(Context mContext, List<GalleryImageModel> privacyoptionList,
                                    int selectedposition, MorestoryClick morestoryClick) {

        this.mContext=mContext;
        this.privacyoptionList=privacyoptionList;
        this.morestoryClick=morestoryClick;
        this.selectedposition=selectedposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageVideoPrivacyListItemBinding imageVideoPrivacyListItemBinding;

        public MyViewHolder(ImageVideoPrivacyListItemBinding imageVideoPrivacyListItemBinding) {
                super(imageVideoPrivacyListItemBinding.getRoot());
            this.imageVideoPrivacyListItemBinding = imageVideoPrivacyListItemBinding;
        }
    }


    @Override
    public ImageVideoPrivacyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageVideoPrivacyListItemBinding imageVideoPrivacyListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.image_video_privacy_list_item, parent, false);
        return new ImageVideoPrivacyAdapter.MyViewHolder(imageVideoPrivacyListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ImageVideoPrivacyAdapter.MyViewHolder holder, int position) {
        GalleryImageModel detailDataModel = privacyoptionList.get(position);


        holder.imageVideoPrivacyListItemBinding.privacyOptionTxt.setText(detailDataModel.getHeadertxt());
        holder.imageVideoPrivacyListItemBinding.privacySubTxt.setText(detailDataModel.getSubtxt());

        if (selectedposition==position){
            holder.imageVideoPrivacyListItemBinding.selectedChk.setChecked(true);
            AppConfiguration.selectedposition = detailDataModel.getId();
            Log.d("selectedId : ", "" + AppConfiguration.selectedposition);
        }else
        {
            holder.imageVideoPrivacyListItemBinding.selectedChk.setChecked(false);
        }

        holder.imageVideoPrivacyListItemBinding.assignListlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedposition=position;
                notifyDataSetChanged();
                dataCheck=new ArrayList<>();
                dataCheck.add(detailDataModel.getHeadertxt()+"|"+detailDataModel.getSubtxt());
                morestoryClick.getmorestoryClick();

            }
        });

        holder.imageVideoPrivacyListItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedposition=position;
                notifyDataSetChanged();
                dataCheck=new ArrayList<>();
                dataCheck.add(detailDataModel.getHeadertxt()+"|"+detailDataModel.getSubtxt());
                morestoryClick.getmorestoryClick();
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
        return privacyoptionList.size();
    }

    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}




