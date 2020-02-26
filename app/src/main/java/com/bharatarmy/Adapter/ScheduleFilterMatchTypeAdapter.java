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
import com.bharatarmy.databinding.ScheduleFilterMatchTypeItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFilterMatchTypeAdapter extends RecyclerView.Adapter<ScheduleFilterMatchTypeAdapter.MyViewHolder> {
    Context mContext;

    int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    List<InquiryStatusModel> teamMatchTypelist;

    public ScheduleFilterMatchTypeAdapter(Context mContext, List<InquiryStatusModel> teamMatchTypelist) {
        this.mContext = mContext;
        this.teamMatchTypelist = teamMatchTypelist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ScheduleFilterMatchTypeItemBinding scheduleFilterMatchTypeItemBinding;

        public MyViewHolder(ScheduleFilterMatchTypeItemBinding scheduleFilterMatchTypeItemBinding) {
            super(scheduleFilterMatchTypeItemBinding.getRoot());
            this.scheduleFilterMatchTypeItemBinding = scheduleFilterMatchTypeItemBinding;

        }
    }


    @Override
    public ScheduleFilterMatchTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ScheduleFilterMatchTypeItemBinding scheduleFilterMatchTypeItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.schedule_filter_match_type_item, parent, false);
        return new ScheduleFilterMatchTypeAdapter.MyViewHolder(scheduleFilterMatchTypeItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ScheduleFilterMatchTypeAdapter.MyViewHolder holder, int position) {
        final InquiryStatusModel matchtypedetail = teamMatchTypelist.get(position);
        holder.scheduleFilterMatchTypeItemBinding.matchMatchTypeTxt.setText(matchtypedetail.getLabel());

        holder.scheduleFilterMatchTypeItemBinding.matchMatchTypeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.scheduleFilterMatchTypeItemBinding.selectedChk.isChecked()) {
                    holder.scheduleFilterMatchTypeItemBinding.selectedChk.setChecked(false);
                    matchtypedetail.setVenueSelected("0");
                } else {
                    holder.scheduleFilterMatchTypeItemBinding.selectedChk.setChecked(true);
                    matchtypedetail.setVenueSelected("1");
                }

            }
        });
        holder.scheduleFilterMatchTypeItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.scheduleFilterMatchTypeItemBinding.selectedChk.isChecked()) {
                    holder.scheduleFilterMatchTypeItemBinding.selectedChk.setChecked(true);
                    matchtypedetail.setVenueSelected("1");
                } else {
                    holder.scheduleFilterMatchTypeItemBinding.selectedChk.setChecked(false);
                    matchtypedetail.setVenueSelected("0");
                }

            }
        });
        if (matchtypedetail.getVenueSelected().equalsIgnoreCase("1")) {
            holder.scheduleFilterMatchTypeItemBinding.selectedChk.setChecked(true);
        } else {
            holder.scheduleFilterMatchTypeItemBinding.selectedChk.setChecked(false);
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
        return teamMatchTypelist.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}






