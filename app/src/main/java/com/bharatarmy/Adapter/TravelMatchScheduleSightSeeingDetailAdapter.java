package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelCitySightseenDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchScheduleSightseenDetailListItemBinding;


import java.util.ArrayList;
import java.util.List;

public class TravelMatchScheduleSightSeeingDetailAdapter extends RecyclerView.Adapter<TravelMatchScheduleSightSeeingDetailAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<TravelModel> travelsightseeingList;
    int  last;


    public TravelMatchScheduleSightSeeingDetailAdapter(Context mContext, ArrayList<TravelModel> travelsightseeingList) {
        this.mcontext = mContext;
        this.travelsightseeingList=travelsightseeingList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelMatchScheduleSightseenDetailListItemBinding travelMatchScheduleSightseenDetailListItemBinding;

        public MyViewHolder(TravelMatchScheduleSightseenDetailListItemBinding travelMatchScheduleSightseenDetailListItemBinding) {
            super(travelMatchScheduleSightseenDetailListItemBinding.getRoot());

            this.travelMatchScheduleSightseenDetailListItemBinding = travelMatchScheduleSightseenDetailListItemBinding;

        }
    }


    @Override
    public TravelMatchScheduleSightSeeingDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchScheduleSightseenDetailListItemBinding travelMatchScheduleSightseenDetailListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_schedule_sightseen_detail_list_item, parent, false);
        return new TravelMatchScheduleSightSeeingDetailAdapter.MyViewHolder(travelMatchScheduleSightseenDetailListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchScheduleSightSeeingDetailAdapter.MyViewHolder holder, int position) {
        TravelModel sightseendetail = travelsightseeingList.get(position);

        last = travelsightseeingList.size()-1;

        if (last == position){
            holder.travelMatchScheduleSightseenDetailListItemBinding.dividerView.setVisibility(View.GONE);
        }

        Utils.setImageInImageView(sightseendetail.getBg_iamge(),holder.travelMatchScheduleSightseenDetailListItemBinding.scheduleMatchSightseenImg,mcontext);
        holder.travelMatchScheduleSightseenDetailListItemBinding.scheduleMatchSightseenTitleTxt.setText(sightseendetail.getMain_titleName());
        holder.travelMatchScheduleSightseenDetailListItemBinding.scheduleMatchSightseenDescriptionTxt.setText(sightseendetail.getMain_desc());
        holder.travelMatchScheduleSightseenDetailListItemBinding.scheduleMatchSightseenLocationTxt.setText(sightseendetail.getBg_name());

        holder.travelMatchScheduleSightseenDetailListItemBinding.scheduleMatchSightseenclickLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent citysightseenDetail=new Intent(mcontext, TravelCitySightseenDetailActivity.class);
                citysightseenDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(citysightseenDetail);
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
        return travelsightseeingList.size();
    }
}



