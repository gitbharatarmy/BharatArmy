package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelCitySightseenDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchSightseenItemBinding;

import java.util.List;

public class TravellMatchSightseenAdapter extends RecyclerView.Adapter<TravellMatchSightseenAdapter.MyViewHolder> {
    Context mcontext;
    List<TravelModel> tournamnetSightseenlist;
    int  last;

    public TravellMatchSightseenAdapter(Context mContext, List<TravelModel> tournamnetSightseenlist) {
        this.mcontext = mContext;
        this.tournamnetSightseenlist = tournamnetSightseenlist;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelMatchSightseenItemBinding travelMatchSightseenItemBinding;

        public MyViewHolder(TravelMatchSightseenItemBinding travelMatchSightseenItemBinding) {
            super(travelMatchSightseenItemBinding.getRoot());

            this.travelMatchSightseenItemBinding = travelMatchSightseenItemBinding;

        }
    }


    @Override
    public TravellMatchSightseenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchSightseenItemBinding travelMatchSightseenItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_sightseen_item, parent, false);
        return new TravellMatchSightseenAdapter.MyViewHolder(travelMatchSightseenItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravellMatchSightseenAdapter.MyViewHolder holder, int position) {
        TravelModel sightseendetail = tournamnetSightseenlist.get(position);

        last = tournamnetSightseenlist.size()-1;

        if (last == position){
            holder.travelMatchSightseenItemBinding.dividerView.setVisibility(View.GONE);
        }

        Utils.setImageInImageView(sightseendetail.getBg_iamge(),holder.travelMatchSightseenItemBinding.travelSightseenImg,mcontext);
        holder.travelMatchSightseenItemBinding.travelSightseenTitleTxt.setText(sightseendetail.getMain_titleName());
        holder.travelMatchSightseenItemBinding.travelSightseenDescriptionTxt.setText(sightseendetail.getMain_desc());
        holder.travelMatchSightseenItemBinding.sightseenLocationTxt.setText(sightseendetail.getBg_name());

        holder.travelMatchSightseenItemBinding.sightseenclickLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent citysightseenDetail=new Intent(mcontext, TravelCitySightseenDetailActivity.class);
                citysightseenDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(citysightseenDetail);
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
        return tournamnetSightseenlist.size();
    }
}



