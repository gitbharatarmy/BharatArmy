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
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityDetailActivity;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HospitalityRelatedHospitalityListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RelatedHospitalityDetailAdapter extends RecyclerView.Adapter<RelatedHospitalityDetailAdapter.MyItemViewHolder> {

    List<HomeTemplateDetailModel> travelHospitalityRealtedHospitalityfinalList;
    Context mContext;
    int last;



    public RelatedHospitalityDetailAdapter(Context mContext, List<HomeTemplateDetailModel> travelHospitalityRealtedHospitalityfinalList) {
        this.mContext=mContext;
        this.travelHospitalityRealtedHospitalityfinalList = travelHospitalityRealtedHospitalityfinalList;
    }


    static class MyItemViewHolder extends RecyclerView.ViewHolder {
        HospitalityRelatedHospitalityListItemBinding hospitalityRelatedHospitalityListItemBinding;


        public MyItemViewHolder(HospitalityRelatedHospitalityListItemBinding hospitalityRelatedHospitalityListItemBinding) {
            super(hospitalityRelatedHospitalityListItemBinding.getRoot());
            this.hospitalityRelatedHospitalityListItemBinding = hospitalityRelatedHospitalityListItemBinding;
        }
    }


    @NonNull
    @Override
    public RelatedHospitalityDetailAdapter.MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HospitalityRelatedHospitalityListItemBinding hospitalityRelatedHospitalityListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hospitality_related_hospitality_list_item, parent, false);
        return new RelatedHospitalityDetailAdapter.MyItemViewHolder(hospitalityRelatedHospitalityListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RelatedHospitalityDetailAdapter.MyItemViewHolder holder, int position) {
        HomeTemplateDetailModel detail = travelHospitalityRealtedHospitalityfinalList.get(position);

        Utils.setImageInImageView(detail.getHospitalityBannerImageUrl(), holder.hospitalityRelatedHospitalityListItemBinding.travelOtherHospitalityBannerImg, mContext);
        holder.hospitalityRelatedHospitalityListItemBinding.hospitalityNameTxt.setText(detail.getHospitalityName());
        holder.hospitalityRelatedHospitalityListItemBinding.showHospitalityDescriptionTxt.setText(detail.getHospitalityDescription());
        holder.hospitalityRelatedHospitalityListItemBinding.tickethospitalitypriceTxt.priceTxt.setText(detail.getHospitalityPrice());
        holder.hospitalityRelatedHospitalityListItemBinding.offersTxt.setText(detail.getHospitalityOffers());

        holder.hospitalityRelatedHospitalityListItemBinding.otherHospitalityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext,holder.hospitalityRelatedHospitalityListItemBinding.otherHospitalityLayout);
                Intent hospitalityCategoryIntent = new Intent(mContext, TravelMatchTicketAndHospitalityDetailActivity.class);
                hospitalityCategoryIntent.putExtra("categoryName", detail.getHospitalityName());
                hospitalityCategoryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(hospitalityCategoryIntent);
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
        return travelHospitalityRealtedHospitalityfinalList.size();
    }


}


