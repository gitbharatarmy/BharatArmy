package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchScheduleActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.TravelFacilityListItemBinding;

import java.util.List;



public class TravelFacilityMainAdapter extends RecyclerView.Adapter<TravelFacilityMainAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> facilityList;



    public TravelFacilityMainAdapter(Context mContext, List<TravelModel> facilityList) {
        this.mContext = mContext;
        this.facilityList = facilityList;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
      TravelFacilityListItemBinding travelFacilityListItemBinding;
        public MyViewHolder(TravelFacilityListItemBinding travelFacilityListItemBinding) {
            super(travelFacilityListItemBinding.getRoot());

            this.travelFacilityListItemBinding=travelFacilityListItemBinding;
        }
    }


    @Override
    public TravelFacilityMainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelFacilityListItemBinding travelFacilityListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_facility_list_item,parent,false);

        return new TravelFacilityMainAdapter.MyViewHolder(travelFacilityListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelFacilityMainAdapter.MyViewHolder holder, int position) {
        final TravelModel facilitydetail = facilityList.get(position);

      holder.travelFacilityListItemBinding.facilityTxt.setText(facilitydetail.getFacilityName());
        holder.travelFacilityListItemBinding.facilityImg.setImageResource(facilitydetail.getFacilityIcon());

        if (!facilitydetail.getFacilityOffer().equalsIgnoreCase("")){
            holder.travelFacilityListItemBinding.offersTxt.setVisibility(View.VISIBLE);
            holder.travelFacilityListItemBinding.offersTxt.setText(facilitydetail.getFacilityOffer());
        }else{
            holder.travelFacilityListItemBinding.offersTxt.setVisibility(View.GONE);
        }

        holder.travelFacilityListItemBinding.facilityRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.travelFacilityListItemBinding.facilityTxt.getText().toString().equalsIgnoreCase("Schedule")){
                    Intent travelmatchscheduleIntent =new Intent(mContext, TravelMatchScheduleActivity.class);
                    travelmatchscheduleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(travelmatchscheduleIntent);
                }
            }
        });



    }

    public void appguiderView() {

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
        return facilityList.size();
    }
}

