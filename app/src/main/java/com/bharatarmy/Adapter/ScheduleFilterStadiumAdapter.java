package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ScheduleFilterStadiumItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFilterStadiumAdapter extends RecyclerView.Adapter<ScheduleFilterStadiumAdapter.MyViewHolder> {
    Context mContext;

    int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    List<InquiryStatusModel> matchTeamVenueList;

    public ScheduleFilterStadiumAdapter(Context mContext, List<InquiryStatusModel> matchTeamVenueList) {
        this.mContext = mContext;
        this.matchTeamVenueList = matchTeamVenueList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ScheduleFilterStadiumItemBinding scheduleFilterStadiumItemBinding;

        public MyViewHolder(ScheduleFilterStadiumItemBinding scheduleFilterStadiumItemBinding) {
            super(scheduleFilterStadiumItemBinding.getRoot());
            this.scheduleFilterStadiumItemBinding = scheduleFilterStadiumItemBinding;

        }
    }


    @Override
    public ScheduleFilterStadiumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ScheduleFilterStadiumItemBinding scheduleFilterStadiumItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.schedule_filter_stadium_item, parent, false);
        return new ScheduleFilterStadiumAdapter.MyViewHolder(scheduleFilterStadiumItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ScheduleFilterStadiumAdapter.MyViewHolder holder, int position) {
        final InquiryStatusModel venuedetail = matchTeamVenueList.get(position);
        holder.scheduleFilterStadiumItemBinding.matchVenuesTxt.setText(venuedetail.getLabel());

        holder.scheduleFilterStadiumItemBinding.matchVenuesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.scheduleFilterStadiumItemBinding.selectedChk.isChecked()) {
                    holder.scheduleFilterStadiumItemBinding.selectedChk.setChecked(false);
                    venuedetail.setVenueSelected("0");
                } else {
                    holder.scheduleFilterStadiumItemBinding.selectedChk.setChecked(true);
                    venuedetail.setVenueSelected("1");
                }

            }
        });
        holder.scheduleFilterStadiumItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.scheduleFilterStadiumItemBinding.selectedChk.isChecked()) {
                    holder.scheduleFilterStadiumItemBinding.selectedChk.setChecked(true);
                    venuedetail.setVenueSelected("1");
                } else {
                    holder.scheduleFilterStadiumItemBinding.selectedChk.setChecked(false);
                    venuedetail.setVenueSelected("0");
                }

            }
        });
        if (venuedetail.getVenueSelected().equalsIgnoreCase("1")) {
            holder.scheduleFilterStadiumItemBinding.selectedChk.setChecked(true);
        } else {
            holder.scheduleFilterStadiumItemBinding.selectedChk.setChecked(false);
        }
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
        return matchTeamVenueList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}




