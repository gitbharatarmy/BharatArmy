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
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HospitalityRelatedHospitalityListItemBinding;

import java.util.ArrayList;

public class RelatedHospitalityDetailAdapter extends RecyclerView.Adapter<RelatedHospitalityDetailAdapter.MyItemViewHolder> {

    ArrayList<TravelModel> travelHospitalityRealtedHospitalityList;
    Context mContext;
    int last;

    public RelatedHospitalityDetailAdapter(Context mContext, ArrayList<TravelModel> travelHospitalityRealtedHospitalityList) {
        this.mContext = mContext;
        this.travelHospitalityRealtedHospitalityList = travelHospitalityRealtedHospitalityList;
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
        TravelModel detail = travelHospitalityRealtedHospitalityList.get(position);
//        last = travelHospitalityRealtedHospitalityList.size();
//        Log.d("last index :",""+last);
//
//        if (last-1 == position){
//            holder.hospitalityRelatedHospitalityListItemBinding.dividerView.setVisibility(View.GONE);
//        }

        Utils.setImageInImageView(detail.getTicket_hospitality_bannerImage(), holder.hospitalityRelatedHospitalityListItemBinding.travelOtherHospitalityBannerImg, mContext);
        holder.hospitalityRelatedHospitalityListItemBinding.hospitalityNameTxt.setText(detail.getTicket_hospitality_namecategory());
        holder.hospitalityRelatedHospitalityListItemBinding.showHospitalityDescriptionTxt.setText(detail.getTicket_hospitality_desc());


        holder.hospitalityRelatedHospitalityListItemBinding.otherHospitalityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext,holder.hospitalityRelatedHospitalityListItemBinding.otherHospitalityLayout);
                Intent hospitalityCategoryIntent = new Intent(mContext, TravelMatchTicketAndHospitalityDetailActivity.class);
                hospitalityCategoryIntent.putExtra("categoryName", detail.getTicket_hospitality_namecategory());
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
        return travelHospitalityRealtedHospitalityList.size();
    }


}


