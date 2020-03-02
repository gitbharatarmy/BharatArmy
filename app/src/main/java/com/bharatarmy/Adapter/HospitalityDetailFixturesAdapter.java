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
import com.bharatarmy.Activity.TravelMatchScheduleDetailActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HospitalityDetailFixturesItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class HospitalityDetailFixturesAdapter extends RecyclerView.Adapter<HospitalityDetailFixturesAdapter.MyItemViewHolder> {

    HospitalitySubAdapter hospitalitySubAdapter;
    List<HomeTemplateDetailModel> travelHospitalityFixturesList;
    ArrayList<TravelModel> hospitalitySubList;

    Context mContext;
    int adapteraddcartposition;

    public HospitalityDetailFixturesAdapter(Context mContext, List<HomeTemplateDetailModel> travelHospitalityFixturesList) {
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
        HomeTemplateDetailModel detail = travelHospitalityFixturesList.get(position);

        holder.hospitalityDetailFixturesItemListBinding.matchTypeTagTxt.setText(detail.getTicketMatchType());
        holder.hospitalityDetailFixturesItemListBinding.firstCountryNameTxt.setText(detail.getTicketMatchFirstCountryName());
        Utils.setImageInImageView(detail.getTicketMatchFirstCountryFlag(), holder.hospitalityDetailFixturesItemListBinding.firstCountryflagImage, mContext);
        holder.hospitalityDetailFixturesItemListBinding.secondCountryNameTxt.setText(detail.getTicketMatchSecondCountryName());
        Utils.setImageInImageView(detail.getTicketMatchSecondCountryFlag(), holder.hospitalityDetailFixturesItemListBinding.secondCountryflagImage, mContext);
        holder.hospitalityDetailFixturesItemListBinding.matchGroundLocationTxt.setText(detail.getTicketMatchGroundName());
        holder.hospitalityDetailFixturesItemListBinding.matchDateTimeTxt.setText(detail.getTicketMatchTimeDate());

        holder.hospitalityDetailFixturesItemListBinding.fixtureCardLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduleDetailIntent = new Intent(mContext, TravelMatchScheduleDetailActivity.class);
                scheduleDetailIntent.putExtra("firstcountryName", holder.hospitalityDetailFixturesItemListBinding.firstCountryNameTxt.getText().toString());
                scheduleDetailIntent.putExtra("secondcountryName", holder.hospitalityDetailFixturesItemListBinding.secondCountryNameTxt.getText().toString());
                scheduleDetailIntent.putExtra("firstscountryFlag", detail.getTicketMatchFirstCountryFlag());
                scheduleDetailIntent.putExtra("secondcountryFlag", detail.getTicketMatchSecondCountryFlag());
                scheduleDetailIntent.putExtra("groundLocation", holder.hospitalityDetailFixturesItemListBinding.matchGroundLocationTxt.getText().toString());
                scheduleDetailIntent.putExtra("matchdatetime", holder.hospitalityDetailFixturesItemListBinding.matchDateTimeTxt.getText().toString());
                scheduleDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(scheduleDetailIntent);
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

    public int adptercartAddPosition() {
        return adapteraddcartposition;
    }
}


