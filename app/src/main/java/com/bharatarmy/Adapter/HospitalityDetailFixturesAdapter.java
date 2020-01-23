package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AddToCartActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.HospitalityDetailFixturesItemListBinding;

import java.util.ArrayList;

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

        holder.hospitalityDetailFixturesItemListBinding.layout2.matchTypeTagTxt.setText(detail.getMatchtype());
        holder.hospitalityDetailFixturesItemListBinding.layout2.firstCountryNameTxt.setText(detail.getFirstcountryname());
        holder.hospitalityDetailFixturesItemListBinding.layout2.firstCountryflagImage.setImageResource(detail.getFirstcountryflag());
        holder.hospitalityDetailFixturesItemListBinding.layout2.secondCountryNameTxt.setText(detail.getSecondcountryname());
        holder.hospitalityDetailFixturesItemListBinding.layout2.secondCountryflagImage.setImageResource(detail.getSecondcountryflag());
        holder.hospitalityDetailFixturesItemListBinding.layout2.matchGroundLocationTxt.setText(detail.getGroundname());
        holder.hospitalityDetailFixturesItemListBinding.layout2.matchDateTimeTxt.setText("Sat 24 OCt, 07:00 PM (Local)");

        holder.hospitalityDetailFixturesItemListBinding.layout2.cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartIntent=new Intent(mContext, AddToCartActivity.class);
                cartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(cartIntent);
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


