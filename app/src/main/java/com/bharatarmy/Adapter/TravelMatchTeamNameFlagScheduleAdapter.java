package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchTeamNameFlagScheduleItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchTeamNameFlagScheduleAdapter extends RecyclerView.Adapter<TravelMatchTeamNameFlagScheduleAdapter.MyViewHolder> {
    Context mContext;
    List<InquiryStatusModel> teamnameflagList;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    MorestoryClick morestoryClick;
    String checkornot;
    int selectedposition = -1;

    public TravelMatchTeamNameFlagScheduleAdapter(Context mContext, int selectedposition, List<InquiryStatusModel> teamnameflagList, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.teamnameflagList = teamnameflagList;
        this.morestoryClick = morestoryClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MatchTeamNameFlagScheduleItemListBinding matchTeamNameFlagScheduleItemListBinding;

        public MyViewHolder(MatchTeamNameFlagScheduleItemListBinding matchTeamNameFlagScheduleItemListBinding) {
            super(matchTeamNameFlagScheduleItemListBinding.getRoot());

            this.matchTeamNameFlagScheduleItemListBinding = matchTeamNameFlagScheduleItemListBinding;
        }
    }


    @Override
    public TravelMatchTeamNameFlagScheduleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MatchTeamNameFlagScheduleItemListBinding matchTeamNameFlagScheduleItemListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.match_team_name_flag_schedule_item_list, parent, false);
        return new TravelMatchTeamNameFlagScheduleAdapter.MyViewHolder(matchTeamNameFlagScheduleItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchTeamNameFlagScheduleAdapter.MyViewHolder holder, int position) {

        final InquiryStatusModel teamdetail = teamnameflagList.get(position);

//        if (selectedposition == position) {
//            holder.matchTeamNameFlagScheduleItemListBinding.selectedLinear.setVisibility(View.VISIBLE);
//        } else {
//            holder.matchTeamNameFlagScheduleItemListBinding.selectedLinear.setVisibility(View.GONE);
//        }


        Utils.setImageInImageView(teamdetail.getCountryFlagUrl(), holder.matchTeamNameFlagScheduleItemListBinding.teamFlagImage, mContext);

        holder.matchTeamNameFlagScheduleItemListBinding.teamNameTxt.setText(teamdetail.getCountryName());


        holder.matchTeamNameFlagScheduleItemListBinding.teamNameFlagRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck = new ArrayList<>();
                if (teamdetail.getTeamSelected().equalsIgnoreCase("1")) {
                    holder.matchTeamNameFlagScheduleItemListBinding.selectedChk.setChecked(false);
                    holder.matchTeamNameFlagScheduleItemListBinding.selectedLinear.setVisibility(View.GONE);
                    teamdetail.setTeamSelected("0");
                    checkornot = "Unselected";
//                    selectedposition = -1;
//                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                } else {
                    holder.matchTeamNameFlagScheduleItemListBinding.selectedChk.setChecked(true);
                    holder.matchTeamNameFlagScheduleItemListBinding.selectedLinear.setVisibility(View.VISIBLE);
                    teamdetail.setTeamSelected("1");
                    checkornot = "selected";
//                    selectedposition = position;
//                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }
            }
        });

        if (teamdetail.getTeamSelected().equalsIgnoreCase("1")) {
            holder.matchTeamNameFlagScheduleItemListBinding.selectedChk.setChecked(true);
            holder.matchTeamNameFlagScheduleItemListBinding.selectedLinear.setVisibility(View.VISIBLE);
        } else {
            holder.matchTeamNameFlagScheduleItemListBinding.selectedChk.setChecked(false);
            holder.matchTeamNameFlagScheduleItemListBinding.selectedLinear.setVisibility(View.GONE);
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
        return teamnameflagList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }

    public String checkornot() {
        return checkornot;
    }

}


