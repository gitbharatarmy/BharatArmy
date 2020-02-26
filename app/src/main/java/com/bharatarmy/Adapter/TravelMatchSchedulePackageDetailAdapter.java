package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelPackageActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.TravelMatchPackageListItemBinding;
import com.bharatarmy.databinding.TravelMatchSchedulePackageDetailListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchSchedulePackageDetailAdapter extends RecyclerView.Adapter<TravelMatchSchedulePackageDetailAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> travelpackageList;
    MorestoryClick morestoryClick;


    public TravelMatchSchedulePackageDetailAdapter(Context mContext, ArrayList<TravelModel> travelpackageList, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.travelpackageList = travelpackageList;
        this.morestoryClick = morestoryClick;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelMatchSchedulePackageDetailListItemBinding travelMatchSchedulePackageDetailListItemBinding;

        public MyViewHolder(TravelMatchSchedulePackageDetailListItemBinding travelMatchSchedulePackageDetailListItemBinding) {
            super(travelMatchSchedulePackageDetailListItemBinding.getRoot());
            this.travelMatchSchedulePackageDetailListItemBinding = travelMatchSchedulePackageDetailListItemBinding;
        }
    }


    @Override
    public TravelMatchSchedulePackageDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchSchedulePackageDetailListItemBinding travelMatchSchedulePackageDetailListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_schedule_package_detail_list_item, parent, false);
        return new TravelMatchSchedulePackageDetailAdapter.MyViewHolder(travelMatchSchedulePackageDetailListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchSchedulePackageDetailAdapter.MyViewHolder holder, int position) {
        final TravelModel packagedetail = travelpackageList.get(position);

        Picasso.with(mContext)
                .load(packagedetail.getTourImage())
                .into(holder.travelMatchSchedulePackageDetailListItemBinding.scheduleMatchPackageBannerImg);

       holder.travelMatchSchedulePackageDetailListItemBinding.scheduleMatchPackagePriceTxt.priceTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,mContext.getResources().getDimension(R.dimen._15sdp));
//       holder.travelMatchSchedulePackageDetailListItemBinding.scheduleMatchPackagePriceTxt.priceTxt.setText("");
        holder.travelMatchSchedulePackageDetailListItemBinding.scheduleMatchPackagePlacenameTxt.setText(packagedetail.getTourCountryName());
        holder.travelMatchSchedulePackageDetailListItemBinding.scheduleMatchPackageDescriptionTxt.setText(packagedetail.getTourDescription());

//        if (packagedetail.getbAImage().equalsIgnoreCase("true")){
//            holder.recommended_Linear.setVisibility(View.VISIBLE);
//        }else{
//            holder.recommended_Linear.setVisibility(View.GONE);
//        }

        holder.travelMatchSchedulePackageDetailListItemBinding.scheduleMatchPackageCardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pacakgeIntent = new Intent(mContext, TravelPackageActivity.class);
                pacakgeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(pacakgeIntent);
            }
        });

        holder.travelMatchSchedulePackageDetailListItemBinding.bookLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookIntent = new Intent(mContext, TravelBookActivity.class);
                bookIntent.putExtra("pacakgeName", "Australian Double Dhamaka: Honeymoon and adventure at one shot");
                bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(bookIntent);

//                morestoryClick.getmorestoryClick();
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
        return travelpackageList.size();
    }
}



