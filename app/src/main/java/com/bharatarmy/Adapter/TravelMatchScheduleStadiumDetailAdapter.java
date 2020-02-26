package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchScheduleStadiumDetailListItemBinding;


import java.util.ArrayList;
import java.util.List;

public class TravelMatchScheduleStadiumDetailAdapter extends RecyclerView.Adapter<TravelMatchScheduleStadiumDetailAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> travelstadiumList;
    int  last;


    public TravelMatchScheduleStadiumDetailAdapter(Context mContext, ArrayList<TravelModel> travelstadiumList) {
        this.mContext=mContext;
        this.travelstadiumList=travelstadiumList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelMatchScheduleStadiumDetailListItemBinding travelMatchScheduleStadiumDetailListItemBinding;

        public MyViewHolder(TravelMatchScheduleStadiumDetailListItemBinding travelMatchScheduleStadiumDetailListItemBinding) {
            super(travelMatchScheduleStadiumDetailListItemBinding.getRoot());

            this.travelMatchScheduleStadiumDetailListItemBinding = travelMatchScheduleStadiumDetailListItemBinding;

        }
    }


    @Override
    public TravelMatchScheduleStadiumDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchScheduleStadiumDetailListItemBinding travelMatchScheduleStadiumDetailListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_schedule_stadium_detail_list_item, parent, false);
        return new TravelMatchScheduleStadiumDetailAdapter.MyViewHolder(travelMatchScheduleStadiumDetailListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchScheduleStadiumDetailAdapter.MyViewHolder holder, int position) {
        TravelModel stadiumdetail = travelstadiumList.get(position);
        last = travelstadiumList.size()-1;

        if (last == position){
            holder.travelMatchScheduleStadiumDetailListItemBinding.dividerView.setVisibility(View.GONE);
        }
        Utils.setImageInImageView(stadiumdetail.getBg_iamge(),holder.travelMatchScheduleStadiumDetailListItemBinding.scheduleMatchStadiumImg,mContext);
        holder.travelMatchScheduleStadiumDetailListItemBinding.scheduleMatchStadiumTitleTxt.setText(stadiumdetail.getMain_titleName());
        holder.travelMatchScheduleStadiumDetailListItemBinding.scheduleMatchStadiumDescriptionTxt.setText(stadiumdetail.getMain_desc());
        holder.travelMatchScheduleStadiumDetailListItemBinding.scheduleMatchStadiumLocationTxt.setText(stadiumdetail.getBg_name());

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
        return travelstadiumList.size();
    }
}



