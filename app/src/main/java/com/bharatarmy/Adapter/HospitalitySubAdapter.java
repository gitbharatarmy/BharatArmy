package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelMatchHotelRoomTypeActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HospitalitySubItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class HospitalitySubAdapter extends RecyclerView.Adapter<HospitalitySubAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> hospitalitySubList;

    public HospitalitySubAdapter(Context mContext, ArrayList<TravelModel> hospitalitySubList) {
        this.mContext = mContext;
        this.hospitalitySubList=hospitalitySubList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        HospitalitySubItemListBinding hospitalitySubItemListBinding;

        public MyViewHolder(HospitalitySubItemListBinding hospitalitySubItemListBinding) {
            super(hospitalitySubItemListBinding.getRoot());

            this.hospitalitySubItemListBinding=hospitalitySubItemListBinding;


        }
    }


    @Override
    public HospitalitySubAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        HospitalitySubItemListBinding HospitalitySubItemListBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hospitality_sub_item_list,parent,false);
        return new HospitalitySubAdapter.MyViewHolder(HospitalitySubItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(HospitalitySubAdapter.MyViewHolder holder, int position) {
        TravelModel detail=hospitalitySubList.get(position);


        Utils.setImageInImageView(detail.getPopularcity_image(),holder.hospitalitySubItemListBinding.hospitalityMainImage,mContext);
        holder.hospitalitySubItemListBinding.hodpitalityTitleTxt.setText(detail.getPopularcity_name());
        holder.hospitalitySubItemListBinding.hospitalitypriceTxt.setText(detail.getPopularcity_image_count());

        holder.hospitalitySubItemListBinding.addtohospitalitycart1Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.hospitalitySubItemListBinding.hospitalityAddImage.setImageResource(R.drawable.fill_selected_checkbox);
                holder.hospitalitySubItemListBinding.hospitalityLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
                holder.hospitalitySubItemListBinding.removetohospitalitycart1Linear.setVisibility(View.VISIBLE);
                holder.hospitalitySubItemListBinding.addtohospitalitycart1Linear.setVisibility(View.GONE);
            }
        });
        holder.hospitalitySubItemListBinding.removetohospitalitycart1Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.hospitalitySubItemListBinding.hospitalityAddImage.setImageResource(R.drawable.ic_blank_check);
                holder.hospitalitySubItemListBinding.hospitalityLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_child_curveshape));
                holder.hospitalitySubItemListBinding.removetohospitalitycart1Linear.setVisibility(View.GONE);
                holder.hospitalitySubItemListBinding.addtohospitalitycart1Linear.setVisibility(View.VISIBLE);
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
        return hospitalitySubList.size();
    }


}




