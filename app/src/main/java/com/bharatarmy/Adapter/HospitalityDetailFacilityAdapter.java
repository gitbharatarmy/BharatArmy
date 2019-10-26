package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AlbumImageVideoShowActivity;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.AlbumDetailListBinding;
import com.bharatarmy.databinding.HospitalityDetailFaclitityListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HospitalityDetailFacilityAdapter  extends RecyclerView.Adapter<HospitalityDetailFacilityAdapter.ItemViewHolder> {

    ArrayList<TravelModel> travelHospitalityFacilityList;
    Context mContext;

    public HospitalityDetailFacilityAdapter(Context mContext, ArrayList<TravelModel> travelHospitalityFacilityList) {
        this.mContext=mContext;
        this.travelHospitalityFacilityList=travelHospitalityFacilityList;
    }


    @NonNull
    @Override
    public HospitalityDetailFacilityAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        HospitalityDetailFaclitityListItemBinding hospitalityDetailFaclitityListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hospitality_detail_faclitity_list_item, parent, false);
        return new HospitalityDetailFacilityAdapter.ItemViewHolder(hospitalityDetailFaclitityListItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull HospitalityDetailFacilityAdapter.ItemViewHolder viewHolder, int position) {

        final TravelModel detail = travelHospitalityFacilityList.get(position);

        Picasso.with(mContext)
                .load(detail.getMatchteamFlag())
                .placeholder(R.drawable.loader)
                .into(viewHolder.hospitalityDetailFaclitityListItemBinding.hotelFacilityImg);

    }

    @Override
    public int getItemCount() {
        return travelHospitalityFacilityList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        HospitalityDetailFaclitityListItemBinding hospitalityDetailFaclitityListItemBinding;

        public ItemViewHolder(@NonNull HospitalityDetailFaclitityListItemBinding hospitalityDetailFaclitityListItemBinding) {
            super(hospitalityDetailFaclitityListItemBinding.getRoot());
            this.hospitalityDetailFaclitityListItemBinding = hospitalityDetailFaclitityListItemBinding;

        }
    }

}



