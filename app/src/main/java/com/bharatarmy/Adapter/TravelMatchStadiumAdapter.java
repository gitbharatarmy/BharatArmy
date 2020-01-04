package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.StoryDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchStadiumItemBinding;
import com.bharatarmy.databinding.TravelNewUpdateItemBinding;

import java.util.List;

public class TravelMatchStadiumAdapter extends RecyclerView.Adapter<TravelMatchStadiumAdapter.MyViewHolder> {
    Context mcontext;
    List<TravelModel> tournamnetStadiumlist;

    public TravelMatchStadiumAdapter(Context mContext, List<TravelModel> tournamnetStadiumlist) {
        this.mcontext = mContext;
        this.tournamnetStadiumlist = tournamnetStadiumlist;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelMatchStadiumItemBinding travelMatchStadiumItemBinding;

        public MyViewHolder(TravelMatchStadiumItemBinding travelMatchStadiumItemBinding) {
            super(travelMatchStadiumItemBinding.getRoot());

            this.travelMatchStadiumItemBinding = travelMatchStadiumItemBinding;

        }
    }


    @Override
    public TravelMatchStadiumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchStadiumItemBinding travelMatchStadiumItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_stadium_item, parent, false);
        return new TravelMatchStadiumAdapter.MyViewHolder(travelMatchStadiumItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchStadiumAdapter.MyViewHolder holder, int position) {
        TravelModel stadiumdetail = tournamnetStadiumlist.get(position);

        Utils.setImageInImageView(stadiumdetail.getBg_iamge(),holder.travelMatchStadiumItemBinding.travelStadiumImg,mcontext);
        holder.travelMatchStadiumItemBinding.travelStadiumTitleTxt.setText(stadiumdetail.getMain_titleName());
        holder.travelMatchStadiumItemBinding.travelStadiumDescriptionTxt.setText(stadiumdetail.getMain_desc());
        holder.travelMatchStadiumItemBinding.matchGroundLocationTxt.setText(stadiumdetail.getBg_name());

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
        return tournamnetStadiumlist.size();
    }
}


