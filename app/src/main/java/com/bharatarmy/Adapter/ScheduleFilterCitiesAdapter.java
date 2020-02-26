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
import com.bharatarmy.databinding.ScheduleFilterCitiesItemBinding;
import com.bharatarmy.databinding.ScheduleFilterStadiumItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFilterCitiesAdapter extends RecyclerView.Adapter<ScheduleFilterCitiesAdapter.MyViewHolder> {
    Context mContext;

    int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    List<InquiryStatusModel> matchTeamCitiesList;

    public ScheduleFilterCitiesAdapter(Context mContext, List<InquiryStatusModel> matchTeamCitiesList) {
        this.mContext = mContext;
        this.matchTeamCitiesList = matchTeamCitiesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ScheduleFilterCitiesItemBinding scheduleFilterCitiesItemBinding;

        public MyViewHolder(ScheduleFilterCitiesItemBinding scheduleFilterCitiesItemBinding) {
            super(scheduleFilterCitiesItemBinding.getRoot());
            this.scheduleFilterCitiesItemBinding = scheduleFilterCitiesItemBinding;

        }
    }


    @Override
    public ScheduleFilterCitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ScheduleFilterCitiesItemBinding scheduleFilterCitiesItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.schedule_filter_cities_item, parent, false);
        return new ScheduleFilterCitiesAdapter.MyViewHolder(scheduleFilterCitiesItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ScheduleFilterCitiesAdapter.MyViewHolder holder, int position) {
        final InquiryStatusModel citiesdetail = matchTeamCitiesList.get(position);
        holder.scheduleFilterCitiesItemBinding.matchCitiesTxt.setText(citiesdetail.getLabel());

        holder.scheduleFilterCitiesItemBinding.matchCitiesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.scheduleFilterCitiesItemBinding.selectedChk.isChecked()) {
                    holder.scheduleFilterCitiesItemBinding.selectedChk.setChecked(false);
                    citiesdetail.setVenueSelected("0");
                } else {
                    holder.scheduleFilterCitiesItemBinding.selectedChk.setChecked(true);
                    citiesdetail.setVenueSelected("1");
                }

            }
        });
        holder.scheduleFilterCitiesItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.scheduleFilterCitiesItemBinding.selectedChk.isChecked()) {
                    holder.scheduleFilterCitiesItemBinding.selectedChk.setChecked(true);
                    citiesdetail.setVenueSelected("1");
                } else {
                    holder.scheduleFilterCitiesItemBinding.selectedChk.setChecked(false);
                    citiesdetail.setVenueSelected("0");
                }

            }
        });
        if (citiesdetail.getVenueSelected().equalsIgnoreCase("1")) {
            holder.scheduleFilterCitiesItemBinding.selectedChk.setChecked(true);
        } else {
            holder.scheduleFilterCitiesItemBinding.selectedChk.setChecked(false);
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
        return matchTeamCitiesList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}





