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
import com.bharatarmy.databinding.StadiumFilterTeamItemBinding;

import java.util.ArrayList;
import java.util.List;

public class StadiumFilterTeamAdapter extends RecyclerView.Adapter<StadiumFilterTeamAdapter.MyViewHolder> {
    Context mContext;
    List<InquiryStatusModel> matchTeamList;

    private ArrayList<String> dataCheck = new ArrayList<String>();


    public StadiumFilterTeamAdapter(Context mContext, List<InquiryStatusModel> matchTeamList) {
        this.mContext=mContext;
        this.matchTeamList=matchTeamList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        StadiumFilterTeamItemBinding stadiumFilterTeamItemBinding;

        public MyViewHolder(StadiumFilterTeamItemBinding stadiumFilterTeamItemBinding) {
            super(stadiumFilterTeamItemBinding.getRoot());

            this.stadiumFilterTeamItemBinding=stadiumFilterTeamItemBinding;
        }
    }


    @Override
    public StadiumFilterTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        StadiumFilterTeamItemBinding stadiumFilterTeamItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.stadium_filter_team_item,parent,false);
        return new StadiumFilterTeamAdapter.MyViewHolder(stadiumFilterTeamItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(StadiumFilterTeamAdapter.MyViewHolder holder, int position) {

        final InquiryStatusModel teamdetail = matchTeamList.get(position);

        Utils.setImageInImageView(teamdetail.getCountryFlagUrl(),holder.stadiumFilterTeamItemBinding.teamFlag,mContext);
        holder.stadiumFilterTeamItemBinding.matchTeamTxt.setText(teamdetail.getCountryName());


        holder.stadiumFilterTeamItemBinding.teamselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.stadiumFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.stadiumFilterTeamItemBinding.selectedChk.setChecked(false);
                    teamdetail.setTeamSelected("0");
                }else{
                    holder.stadiumFilterTeamItemBinding.selectedChk.setChecked(true);
                    teamdetail.setTeamSelected("1");

                }

            }
        });

        holder.stadiumFilterTeamItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.stadiumFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.stadiumFilterTeamItemBinding.selectedChk.setChecked(true);
                    teamdetail.setTeamSelected("1");
                }else{
                    holder.stadiumFilterTeamItemBinding.selectedChk.setChecked(false);
                    teamdetail.setTeamSelected("0");

                }

            }
        });

        if (teamdetail.getTeamSelected().equalsIgnoreCase("1")){
            holder.stadiumFilterTeamItemBinding.selectedChk.setChecked(true);
        }else{
            holder.stadiumFilterTeamItemBinding.selectedChk.setChecked(false);
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



