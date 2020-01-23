package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.SightseenFilterSightseenItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SightseenFilterSightseenAdapter extends RecyclerView.Adapter<SightseenFilterSightseenAdapter.MyViewHolder> {
    Context mContext;

    int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    List<InquiryStatusModel> matchTeamVenueList;

    ArrayList<TravelModel> tournamentcitylist;

//    public SightseenFilterSightseenAdapter(Context mContext, List<InquiryStatusModel> matchTeamVenueList) {
//        this.mContext = mContext;
//        this.matchTeamVenueList = matchTeamVenueList;
//    }

    public SightseenFilterSightseenAdapter(Context mContext, ArrayList<TravelModel> tournamentcitylist) {
        this.mContext=mContext;
        this.tournamentcitylist=tournamentcitylist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        SightseenFilterSightseenItemBinding sightseenFilterSightseenItemBinding;

        public MyViewHolder(SightseenFilterSightseenItemBinding sightseenFilterSightseenItemBinding) {
            super(sightseenFilterSightseenItemBinding.getRoot());
            this.sightseenFilterSightseenItemBinding = sightseenFilterSightseenItemBinding;

        }
    }


    @Override
    public SightseenFilterSightseenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SightseenFilterSightseenItemBinding sightseenFilterSightseenItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.sightseen_filter_sightseen_item, parent, false);
        return new SightseenFilterSightseenAdapter.MyViewHolder(sightseenFilterSightseenItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SightseenFilterSightseenAdapter.MyViewHolder holder, int position) {

        final  TravelModel citydetail = tournamentcitylist.get(position);


        holder.sightseenFilterSightseenItemBinding.matchCityTxt.setText(citydetail.getCityHotelAmenitiesImage());


        holder.sightseenFilterSightseenItemBinding.cityselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.sightseenFilterSightseenItemBinding.selectedChk.isChecked()){
                    holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(false);
                    citydetail.setCityHotelAmenitiesName("0");
                }else{
                    holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(true);
                    citydetail.setCityHotelAmenitiesName("1");

                }

            }
        });

        holder.sightseenFilterSightseenItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.sightseenFilterSightseenItemBinding.selectedChk.isChecked()){
                    holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(true);
                    citydetail.setCityHotelAmenitiesName("1");
                }else{
                    holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(false);
                    citydetail.setCityHotelAmenitiesName("0");

                }

            }
        });

        if (citydetail.getCityHotelAmenitiesName().equalsIgnoreCase("1")){
            holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(true);
        }else{
            holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(false);
        }
        
//        final InquiryStatusModel venuedetail = matchTeamVenueList.get(position);
//        holder.sightseenFilterSightseenItemBinding.matchVenuesTxt.setText(venuedetail.getLabel());
//
//        holder.sightseenFilterSightseenItemBinding.matchVenuesLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.sightseenFilterSightseenItemBinding.selectedChk.isChecked()) {
//                    holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(false);
//                    venuedetail.setVenueSelected("0");
//                } else {
//                    holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(true);
//                    venuedetail.setVenueSelected("1");
//                }
//
//            }
//        });
//        holder.sightseenFilterSightseenItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.sightseenFilterSightseenItemBinding.selectedChk.isChecked()) {
//                    holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(true);
//                    venuedetail.setVenueSelected("1");
//                } else {
//                    holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(false);
//                    venuedetail.setVenueSelected("0");
//                }
//
//            }
//        });
//        if (venuedetail.getVenueSelected().equalsIgnoreCase("1")) {
//            holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(true);
//        } else {
//            holder.sightseenFilterSightseenItemBinding.selectedChk.setChecked(false);
//        }
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
        return tournamentcitylist.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}




