package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.SightseenFilterTeamItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SightseenFilterTeamAdapter extends RecyclerView.Adapter<SightseenFilterTeamAdapter.MyViewHolder> {
    Context mContext;
    List<InquiryStatusModel> matchTeamList;

    private ArrayList<String> dataCheck = new ArrayList<String>();


    public SightseenFilterTeamAdapter(Context mContext, List<InquiryStatusModel> matchTeamList) {
        this.mContext=mContext;
        this.matchTeamList=matchTeamList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SightseenFilterTeamItemBinding sightseenFilterTeamItemBinding;

        public MyViewHolder(SightseenFilterTeamItemBinding sightseenFilterTeamItemBinding) {
            super(sightseenFilterTeamItemBinding.getRoot());

            this.sightseenFilterTeamItemBinding=sightseenFilterTeamItemBinding;
        }
    }


    @Override
    public SightseenFilterTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SightseenFilterTeamItemBinding sightseenFilterTeamItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.sightseen_filter_team_item,parent,false);
        return new SightseenFilterTeamAdapter.MyViewHolder(sightseenFilterTeamItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SightseenFilterTeamAdapter.MyViewHolder holder, int position) {

        final InquiryStatusModel teamdetail = matchTeamList.get(position);

        Utils.setImageInImageView(teamdetail.getCountryFlagUrl(),holder.sightseenFilterTeamItemBinding.teamFlag,mContext);
        holder.sightseenFilterTeamItemBinding.matchTeamTxt.setText(teamdetail.getCountryName());


        holder.sightseenFilterTeamItemBinding.teamselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.sightseenFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.sightseenFilterTeamItemBinding.selectedChk.setChecked(false);
                    teamdetail.setTeamSelected("0");
                }else{
                    holder.sightseenFilterTeamItemBinding.selectedChk.setChecked(true);
                    teamdetail.setTeamSelected("1");

                }

            }
        });

        holder.sightseenFilterTeamItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.sightseenFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.sightseenFilterTeamItemBinding.selectedChk.setChecked(true);
                    teamdetail.setTeamSelected("1");
                }else{
                    holder.sightseenFilterTeamItemBinding.selectedChk.setChecked(false);
                    teamdetail.setTeamSelected("0");

                }

            }
        });

        if (teamdetail.getTeamSelected().equalsIgnoreCase("1")){
            holder.sightseenFilterTeamItemBinding.selectedChk.setChecked(true);
        }else{
            holder.sightseenFilterTeamItemBinding.selectedChk.setChecked(false);
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




