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

import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchFilterTeamItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MatchFilterTeamAdapter extends RecyclerView.Adapter<MatchFilterTeamAdapter.MyViewHolder> {
    Context mContext;
    List<InquiryStatusModel> matchTeamList;

    private ArrayList<String> dataCheck = new ArrayList<String>();


    public MatchFilterTeamAdapter(Context mContext, List<InquiryStatusModel> matchTeamList) {
        this.mContext=mContext;
        this.matchTeamList=matchTeamList;
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

        final InquiryStatusModel teamdetail = matchTeamList.get(position);

        Utils.setImageInImageView(teamdetail.getCountryFlagUrl(),holder.matchFilterTeamItemBinding.teamFlag,mContext);
        holder.matchFilterTeamItemBinding.matchTeamTxt.setText(teamdetail.getCountryName());


        holder.matchFilterTeamItemBinding.teamselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.matchFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.matchFilterTeamItemBinding.selectedChk.setChecked(false);
                    teamdetail.setTeamSelected("0");
                }else{
                    holder.matchFilterTeamItemBinding.selectedChk.setChecked(true);
                    teamdetail.setTeamSelected("1");

                }

            }
        });

        holder.matchFilterTeamItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.matchFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.matchFilterTeamItemBinding.selectedChk.setChecked(true);
                    teamdetail.setTeamSelected("1");
                }else{
                    holder.matchFilterTeamItemBinding.selectedChk.setChecked(false);
                    teamdetail.setTeamSelected("0");

                }

            }
        });

        if (teamdetail.getTeamSelected().equalsIgnoreCase("1")){
            holder.matchFilterTeamItemBinding.selectedChk.setChecked(true);
        }else{
            holder.matchFilterTeamItemBinding.selectedChk.setChecked(false);
        }

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
        return matchTeamList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}


