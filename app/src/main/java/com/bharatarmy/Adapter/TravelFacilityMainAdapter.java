package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchScheduleActivity;
import com.bharatarmy.Activity.TravelMatchTicketActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.TravelFacilityListItemBinding;

import java.util.List;


public class TravelFacilityMainAdapter extends RecyclerView.Adapter<TravelFacilityMainAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> facilityList;
    String activityNameStr;
    MorestoryClick morestoryClick;

    public TravelFacilityMainAdapter(Context mContext, List<TravelModel> facilityList, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.facilityList = facilityList;
        this.morestoryClick = morestoryClick;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelFacilityListItemBinding travelFacilityListItemBinding;

        public MyViewHolder(TravelFacilityListItemBinding travelFacilityListItemBinding) {
            super(travelFacilityListItemBinding.getRoot());

            this.travelFacilityListItemBinding = travelFacilityListItemBinding;
        }
    }


    @Override
    public TravelFacilityMainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelFacilityListItemBinding travelFacilityListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_facility_list_item, parent, false);

        return new TravelFacilityMainAdapter.MyViewHolder(travelFacilityListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelFacilityMainAdapter.MyViewHolder holder, int position) {
        final TravelModel facilitydetail = facilityList.get(position);


        if (position == 1 || position == 2 || position == 4 || position == 5  || position == 7) {
            holder.travelFacilityListItemBinding.leftView.setVisibility(View.GONE);
        }
        if (position == 3 || position == 4 || position == 5 || position == 7) {
            holder.travelFacilityListItemBinding.topView.setVisibility(View.GONE);
        }
//        if (position == 2) {
//            holder.travelFacilityListItemBinding.leftView.setVisibility(View.GONE);
//        }
//        if (position == 4) {
//            holder.travelFacilityListItemBinding.leftView.setVisibility(View.GONE);
//        }
//        if (position == 5) {
//            holder.travelFacilityListItemBinding.leftView.setVisibility(View.GONE);
//        }
        if (position == 6 || position == 8) {
//            holder.travelFacilityListItemBinding.rightView.setVisibility(View.GONE);
            holder.travelFacilityListItemBinding.leftView.setVisibility(View.GONE);
            holder.travelFacilityListItemBinding.topView.setVisibility(View.GONE);
            holder.travelFacilityListItemBinding.bottomView.setVisibility(View.GONE);
            holder.travelFacilityListItemBinding.facilityTxt.setVisibility(View.GONE);
            holder.travelFacilityListItemBinding.facilityImg.setVisibility(View.GONE);
            holder.travelFacilityListItemBinding.offersTxt.setVisibility(View.GONE);
        }
        holder.travelFacilityListItemBinding.facilityTxt.setText(facilitydetail.getFacilityName());
        holder.travelFacilityListItemBinding.facilityImg.setImageResource(facilitydetail.getFacilityIcon());
        if (position != 6 || position != 8) {
            if (!facilitydetail.getFacilityOffer().equalsIgnoreCase("")) {
                holder.travelFacilityListItemBinding.offersTxt.setVisibility(View.VISIBLE);
                holder.travelFacilityListItemBinding.offersTxt.setText(facilitydetail.getFacilityOffer());
            } else {
                holder.travelFacilityListItemBinding.offersTxt.setVisibility(View.GONE);
            }
        }

        holder.travelFacilityListItemBinding.facilityRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityNameStr = holder.travelFacilityListItemBinding.facilityTxt.getText().toString();
                morestoryClick.getmorestoryClick();

            }
        });


    }

    public void appguiderView() {

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
        return facilityList.size();
    }

    public String activityName() {
        return activityNameStr;
    }

}

