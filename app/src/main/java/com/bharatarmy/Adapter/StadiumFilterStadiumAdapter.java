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
import com.bharatarmy.databinding.StadiumFilterStadiumItemBinding;

import java.util.ArrayList;
import java.util.List;

public class StadiumFilterStadiumAdapter extends RecyclerView.Adapter<StadiumFilterStadiumAdapter.MyViewHolder> {
    Context mContext;

    int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    List<InquiryStatusModel> matchTeamVenueList;

    public StadiumFilterStadiumAdapter(Context mContext, List<InquiryStatusModel> matchTeamVenueList) {
        this.mContext = mContext;
        this.matchTeamVenueList = matchTeamVenueList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        StadiumFilterStadiumItemBinding stadiumFilterStadiumItemBinding;

        public MyViewHolder(StadiumFilterStadiumItemBinding stadiumFilterStadiumItemBinding) {
            super(stadiumFilterStadiumItemBinding.getRoot());
            this.stadiumFilterStadiumItemBinding = stadiumFilterStadiumItemBinding;

        }
    }


    @Override
    public StadiumFilterStadiumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StadiumFilterStadiumItemBinding stadiumFilterStadiumItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.stadium_filter_stadium_item, parent, false);
        return new StadiumFilterStadiumAdapter.MyViewHolder(stadiumFilterStadiumItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(StadiumFilterStadiumAdapter.MyViewHolder holder, int position) {
        final InquiryStatusModel venuedetail = matchTeamVenueList.get(position);
        holder.stadiumFilterStadiumItemBinding.matchVenuesTxt.setText(venuedetail.getLabel());

        holder.stadiumFilterStadiumItemBinding.matchVenuesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.stadiumFilterStadiumItemBinding.selectedChk.isChecked()) {
                    holder.stadiumFilterStadiumItemBinding.selectedChk.setChecked(false);
                    venuedetail.setVenueSelected("0");
                } else {
                    holder.stadiumFilterStadiumItemBinding.selectedChk.setChecked(true);
                    venuedetail.setVenueSelected("1");
                }

            }
        });
        holder.stadiumFilterStadiumItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.stadiumFilterStadiumItemBinding.selectedChk.isChecked()) {
                    holder.stadiumFilterStadiumItemBinding.selectedChk.setChecked(true);
                    venuedetail.setVenueSelected("1");
                } else {
                    holder.stadiumFilterStadiumItemBinding.selectedChk.setChecked(false);
                    venuedetail.setVenueSelected("0");
                }

            }
        });
        if (venuedetail.getVenueSelected().equalsIgnoreCase("1")) {
            holder.stadiumFilterStadiumItemBinding.selectedChk.setChecked(true);
        } else {
            holder.stadiumFilterStadiumItemBinding.selectedChk.setChecked(false);
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




