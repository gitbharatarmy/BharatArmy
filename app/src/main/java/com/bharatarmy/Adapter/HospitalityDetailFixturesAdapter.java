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

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
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

        holder.hospitalityDetailFixturesItemListBinding.matchTagTxt.setText(detail.getMatchtype());
        holder.hospitalityDetailFixturesItemListBinding.firstcountryflagTxt.setText(detail.getFirstcountryname());
        holder.hospitalityDetailFixturesItemListBinding.firstcountryflagImg.setImageResource(detail.getFirstcountryflag());
        holder.hospitalityDetailFixturesItemListBinding.secondcountryflagTxt.setText(detail.getSecondcountryname());
        holder.hospitalityDetailFixturesItemListBinding.secondcountryflagImg.setImageResource(detail.getSecondcountryflag());
        holder.hospitalityDetailFixturesItemListBinding.matchgroundTxt.setText(detail.getGroundname());


        holder.hospitalityDetailFixturesItemListBinding.mainGroupLiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hospitalityDetailFixturesItemListBinding.subHospitalityRcv.isShown()) {
                    holder.hospitalityDetailFixturesItemListBinding.subHospitalityRcv.setVisibility(View.GONE);
                } else {
                    holder.hospitalityDetailFixturesItemListBinding.subHospitalityRcv.setVisibility(View.VISIBLE);
                }
                hospitalitySubList = new ArrayList<TravelModel>();
                hospitalitySubList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg",
                        "Private Suites", "₹ 600.00"));
                hospitalitySubList.add(new TravelModel("https://i0.wp.com/www.perrygroup.com/wp-content/uploads/2016/01/service-pic3-1.jpg", "The Pavillion",
                        "₹ 550.00"));
                hospitalitySubList.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg",
                        "club 20 20", "₹ 500.00"));

                hospitalitySubAdapter = new HospitalitySubAdapter(mContext, hospitalitySubList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                holder.hospitalityDetailFixturesItemListBinding.subHospitalityRcv.setLayoutManager(mLayoutManager);
                holder.hospitalityDetailFixturesItemListBinding.subHospitalityRcv.setItemAnimator(new DefaultItemAnimator());
                holder.hospitalityDetailFixturesItemListBinding.subHospitalityRcv.setAdapter(hospitalitySubAdapter);

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
        return travelHospitalityFixturesList.size();
    }


}


