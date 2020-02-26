package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchScheduleDetailActivity;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchScheduleItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchScheduleAdapter extends RecyclerView.Adapter<TravelMatchScheduleAdapter.MyViewHolder> {
    Context mContext;
    List<HomeTemplateDetailModel> matchscheduleList;
    String firstcountryFlagStr, secondcountryFlagStr;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public TravelMatchScheduleAdapter(Context mContext, List<HomeTemplateDetailModel> matchscheduleList) {
        this.mContext = mContext;
        this.matchscheduleList = matchscheduleList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelMatchScheduleItemListBinding travelMatchScheduleItemListBinding;

        public MyViewHolder(TravelMatchScheduleItemListBinding travelMatchScheduleItemListBinding) {
            super(travelMatchScheduleItemListBinding.getRoot());

            this.travelMatchScheduleItemListBinding = travelMatchScheduleItemListBinding;
        }
    }


    @Override
    public TravelMatchScheduleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TravelMatchScheduleItemListBinding travelMatchScheduleItemListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.travel_match_schedule_item_list, parent, false);
        return new TravelMatchScheduleAdapter.MyViewHolder(travelMatchScheduleItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchScheduleAdapter.MyViewHolder holder, int position) {

        HomeTemplateDetailModel scheduledetail = matchscheduleList.get(position);

        if (scheduledetail.getDbFromCountryName().equalsIgnoreCase("")) {
            holder.travelMatchScheduleItemListBinding.firstCountryNameTxt.setText(
                    scheduledetail.getObjFromCountry().getCountryName());
        } else {
            holder.travelMatchScheduleItemListBinding.firstCountryNameTxt.setText(
                    scheduledetail.getDbFromCountryName());
        }
        if (scheduledetail.getDbToCountryName().equalsIgnoreCase("")) {
            holder.travelMatchScheduleItemListBinding.secondCountryNameTxt.setText(
                    scheduledetail.getObjToCountry().getCountryName());
        } else {
            holder.travelMatchScheduleItemListBinding.secondCountryNameTxt.setText(
                    scheduledetail.getDbToCountryName());
        }

        Utils.setImageInImageView(scheduledetail.getObjFromCountry().getCountryFlagUrl(), holder.travelMatchScheduleItemListBinding.firstCountryflagImage, mContext);
        Utils.setImageInImageView(scheduledetail.getObjToCountry().getCountryFlagUrl(), holder.travelMatchScheduleItemListBinding.secondCountryflagImage, mContext);


        holder.travelMatchScheduleItemListBinding.matchTypeTagTxt.setText(scheduledetail.getStrMatchType());
        holder.travelMatchScheduleItemListBinding.matchDateTimeTxt.setText(scheduledetail.getStrMatchDateTime());

        holder.travelMatchScheduleItemListBinding.matchGroundLocationTxt.setText(scheduledetail.getStadiumName());

        holder.travelMatchScheduleItemListBinding.scheduleCardLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduledetailIntent = new Intent(mContext, TravelMatchScheduleDetailActivity.class);
                scheduledetailIntent.putExtra("firstcountryName", holder.travelMatchScheduleItemListBinding.firstCountryNameTxt.getText().toString());
                scheduledetailIntent.putExtra("secondcountryName", holder.travelMatchScheduleItemListBinding.secondCountryNameTxt.getText().toString());
                scheduledetailIntent.putExtra("firstscountryFlag", scheduledetail.getObjFromCountry().getCountryFlagUrl());
                scheduledetailIntent.putExtra("secondcountryFlag", scheduledetail.getObjToCountry().getCountryFlagUrl());
                scheduledetailIntent.putExtra("groundLocation", holder.travelMatchScheduleItemListBinding.matchGroundLocationTxt.getText().toString());
                scheduledetailIntent.putExtra("matchdatetime", holder.travelMatchScheduleItemListBinding.matchDateTimeTxt.getText().toString());
                scheduledetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(scheduledetailIntent);
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
        return matchscheduleList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}


