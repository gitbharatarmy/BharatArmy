package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchSelectHotelActivity;
import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchScheduleHospitalityDetailListItemBinding;

import java.util.ArrayList;

public class TravelMatchScheduleHospitalityDetailAdapter extends RecyclerView.Adapter<TravelMatchScheduleHospitalityDetailAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> travelhospitalityList;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public TravelMatchScheduleHospitalityDetailAdapter(Context mContext, ArrayList<TravelModel> travelhospitalityList) {
        this.mContext = mContext;
        this.travelhospitalityList=travelhospitalityList;
    }
    


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelMatchScheduleHospitalityDetailListItemBinding travelMatchScheduleHospitalityDetailListItemBinding;

        public MyViewHolder(TravelMatchScheduleHospitalityDetailListItemBinding travelMatchScheduleHospitalityDetailListItemBinding) {
            super(travelMatchScheduleHospitalityDetailListItemBinding.getRoot());

            this.travelMatchScheduleHospitalityDetailListItemBinding = travelMatchScheduleHospitalityDetailListItemBinding;

        }
    }


    @Override
    public TravelMatchScheduleHospitalityDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchScheduleHospitalityDetailListItemBinding travelMatchScheduleHospitalityDetailListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_schedule_hospitality_detail_list_item, parent, false);
        return new TravelMatchScheduleHospitalityDetailAdapter.MyViewHolder(travelMatchScheduleHospitalityDetailListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchScheduleHospitalityDetailAdapter.MyViewHolder holder, int position) {
        TravelModel detail = travelhospitalityList.get(position);

        holder.travelMatchScheduleHospitalityDetailListItemBinding.scheduleMatchHospitalityPriceTxt.priceTxt.setText(detail.getTicket_hospitality_price());
//        holder.travelMatchScheduleHospitalityDetailListItemBinding.scheduleMatchHospitalityPriceTxt.setText(detail.getTicket_hospitality_price());
        Utils.setImageInImageView(detail.getTicket_hospitality_bannerImage(), holder.travelMatchScheduleHospitalityDetailListItemBinding.scheduleMatchHospitalityBannerImg, mContext);
        holder.travelMatchScheduleHospitalityDetailListItemBinding.scheduleMatchHospitalityCategoryNameTxt.setText(detail.getTicket_hospitality_namecategory());
        holder.travelMatchScheduleHospitalityDetailListItemBinding.scheduleMatchHospitalityDescriptionTxt.setText(detail.getTicket_hospitality_desc());

        if (!detail.getTicket_hospitality_offers().equalsIgnoreCase("")) {
            holder.travelMatchScheduleHospitalityDetailListItemBinding.offersLinear.setVisibility(View.VISIBLE);
            holder.travelMatchScheduleHospitalityDetailListItemBinding.offersTxt.setText(detail.getTicket_hospitality_offers());
        } else {
            holder.travelMatchScheduleHospitalityDetailListItemBinding.offersLinear.setVisibility(View.GONE);
        }

        holder.travelMatchScheduleHospitalityDetailListItemBinding.offersLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext,holder.travelMatchScheduleHospitalityDetailListItemBinding.offersLinear);
                Intent selecthotelIntent=new Intent(mContext, TravelMatchSelectHotelActivity.class);
                selecthotelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(selecthotelIntent);
            }
        });

        if (detail.getTicket_hospitality_inclusion().equalsIgnoreCase("1")) {
            holder.travelMatchScheduleHospitalityDetailListItemBinding.hotelLinear.setVisibility(View.VISIBLE);
            holder.travelMatchScheduleHospitalityDetailListItemBinding.sightseenLinear.setVisibility(View.GONE);
            holder.travelMatchScheduleHospitalityDetailListItemBinding.restaurantLinear.setVisibility(View.GONE);
        } else if (detail.getTicket_hospitality_inclusion().equalsIgnoreCase("2")){
            holder.travelMatchScheduleHospitalityDetailListItemBinding.hotelLinear.setVisibility(View.VISIBLE);
            holder.travelMatchScheduleHospitalityDetailListItemBinding.sightseenLinear.setVisibility(View.VISIBLE);
            holder.travelMatchScheduleHospitalityDetailListItemBinding.restaurantLinear.setVisibility(View.GONE);
        }else if(detail.getTicket_hospitality_inclusion().equalsIgnoreCase("3")){
            holder.travelMatchScheduleHospitalityDetailListItemBinding.hotelLinear.setVisibility(View.VISIBLE);
            holder.travelMatchScheduleHospitalityDetailListItemBinding.sightseenLinear.setVisibility(View.VISIBLE);
            holder.travelMatchScheduleHospitalityDetailListItemBinding.restaurantLinear.setVisibility(View.VISIBLE);
        }



        holder.travelMatchScheduleHospitalityDetailListItemBinding.hospitalityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hospitalityCategoryIntent=new Intent(mContext, TravelMatchTicketAndHospitalityDetailActivity.class);
                hospitalityCategoryIntent.putExtra("categoryName",detail.getTicket_hospitality_namecategory());
                hospitalityCategoryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        return travelhospitalityList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}









