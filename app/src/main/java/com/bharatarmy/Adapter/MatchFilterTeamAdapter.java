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
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.MatchFilterTeamItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MatchFilterTeamAdapter extends RecyclerView.Adapter<MatchFilterTeamAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> matchTeamFlagList;
int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public MatchFilterTeamAdapter(Context mContext, ArrayList<TravelModel> matchTeamFlagList) {
        this.mContext=mContext;
        this.matchTeamFlagList=matchTeamFlagList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MatchFilterTeamItemBinding matchFilterTeamItemBinding;

        public MyViewHolder(MatchFilterTeamItemBinding matchFilterTeamItemBinding) {
            super(matchFilterTeamItemBinding.getRoot());

            this.matchFilterTeamItemBinding=matchFilterTeamItemBinding;
        }
    }


    @Override
    public MatchFilterTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MatchFilterTeamItemBinding matchFilterTeamItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.match_filter_team_item,parent,false);
        return new MatchFilterTeamAdapter.MyViewHolder(matchFilterTeamItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MatchFilterTeamAdapter.MyViewHolder holder, int position) {

        final TravelModel teamdetail = matchTeamFlagList.get(position);

        holder.matchFilterTeamItemBinding.teamFlag.setImageResource(teamdetail.getMatchteamFlag());
        holder.matchFilterTeamItemBinding.matchTeamTxt.setText(teamdetail.getMatchteamVenues());


        holder.matchFilterTeamItemBinding.teamselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.matchFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.matchFilterTeamItemBinding.selectedChk.setChecked(false);
                }else{
                    holder.matchFilterTeamItemBinding.selectedChk.setChecked(true);
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
        return matchTeamFlagList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}


