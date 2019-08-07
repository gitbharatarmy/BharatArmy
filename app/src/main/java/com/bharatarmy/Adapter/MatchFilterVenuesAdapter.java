package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.MatchFilterVenuesItemBinding;

import java.util.ArrayList;

public class MatchFilterVenuesAdapter extends RecyclerView.Adapter<MatchFilterVenuesAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> matchTeamVenueList;
    int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public MatchFilterVenuesAdapter(Context mContext, ArrayList<TravelModel> matchTeamVenueList) {
        this.mContext=mContext;
        this.matchTeamVenueList=matchTeamVenueList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        MatchFilterVenuesItemBinding matchFilterVenuesItemBinding;

        public MyViewHolder(MatchFilterVenuesItemBinding matchFilterVenuesItemBinding) {
            super(matchFilterVenuesItemBinding.getRoot());
            this.matchFilterVenuesItemBinding=matchFilterVenuesItemBinding;

        }
    }


    @Override
    public MatchFilterVenuesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 MatchFilterVenuesItemBinding matchFilterVenuesItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
         R.layout.match_filter_venues_item,parent,false);
 return new MatchFilterVenuesAdapter.MyViewHolder(matchFilterVenuesItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MatchFilterVenuesAdapter.MyViewHolder holder, int position) {

        final TravelModel venuesdetail = matchTeamVenueList.get(position);


        holder.matchFilterVenuesItemBinding.matchVenuesTxt.setText(venuesdetail.getMatchteamVenues());

        holder.matchFilterVenuesItemBinding.matchVenuesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.matchFilterVenuesItemBinding.selectedChk.isChecked()){
                    holder.matchFilterVenuesItemBinding.selectedChk.setChecked(false);
                }else{
                    holder.matchFilterVenuesItemBinding.selectedChk.setChecked(true);
                }

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
        return matchTeamVenueList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}



