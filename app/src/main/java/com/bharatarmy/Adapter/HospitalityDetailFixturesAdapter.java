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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchSelectHotelActivity;
import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HospitalityDetailFixturesItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class HospitalityDetailFixturesAdapter extends RecyclerView.Adapter<HospitalityDetailFixturesAdapter.MyItemViewHolder> {

    HospitalitySubAdapter hospitalitySubAdapter;
    ArrayList<TravelModel> travelHospitalityFixturesList;
    ArrayList<TravelModel> hospitalitySubList;
    Context mContext;

    public HospitalityDetailFixturesAdapter(Context mContext, ArrayList<TravelModel> travelHospitalityFixturesList) {
        this.mContext = mContext;
        this.travelHospitalityFixturesList = travelHospitalityFixturesList;
    }


    static class MyItemViewHolder extends RecyclerView.ViewHolder {
        HospitalityDetailFixturesItemListBinding hospitalityDetailFixturesItemListBinding;


        public MyItemViewHolder(HospitalityDetailFixturesItemListBinding hospitalityDetailFixturesItemListBinding) {
            super(hospitalityDetailFixturesItemListBinding.getRoot());
            this.hospitalityDetailFixturesItemListBinding = hospitalityDetailFixturesItemListBinding;
        }
    }


    @NonNull
    @Override
    public HospitalityDetailFixturesAdapter.MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HospitalityDetailFixturesItemListBinding hospitalityDetailFixturesItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hospitality_detail_fixtures_item_list, parent, false);
        return new HospitalityDetailFixturesAdapter.MyItemViewHolder(hospitalityDetailFixturesItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final HospitalityDetailFixturesAdapter.MyItemViewHolder holder, int position) {
        TravelModel detail = travelHospitalityFixturesList.get(position);

        Utils.setImageInImageView(detail.getTicket_hospitality_bannerImage(), holder.hospitalityDetailFixturesItemListBinding.travelHospitalityBannerImg, mContext);
        holder.hospitalityDetailFixturesItemListBinding.categoryNameTxt.setText(detail.getTicket_hospitality_namecategory());
        holder.hospitalityDetailFixturesItemListBinding.showPackageTourDescriptionTxt.setText(detail.getTicket_hospitality_desc());

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
        return travelHospitalityFixturesList.size();
    }


}


