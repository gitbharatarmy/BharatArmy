package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.FTPDetailsActivity;
import com.bharatarmy.Activity.RegisterInterestActivityNew;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.UpcomingTournamentListNewBinding;

import java.util.List;

public class UpcomingDashboardAdapter extends RecyclerView.Adapter<UpcomingDashboardAdapter.MyViewHolder> {
    Context mcontext;
    List<UpcommingDashboardModel> upcomingDataList;


    public UpcomingDashboardAdapter(Context mContext, List<UpcommingDashboardModel> upcomingDataList) {
        this.mcontext = mContext;
        this.upcomingDataList = upcomingDataList;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        UpcomingTournamentListNewBinding upcomingTournamentListNewBinding;


        public MyViewHolder(UpcomingTournamentListNewBinding upcomingTournamentListNewBinding) {
            super(upcomingTournamentListNewBinding.getRoot());

            this.upcomingTournamentListNewBinding = upcomingTournamentListNewBinding;

        }
    }


    @Override
    public UpcomingDashboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UpcomingTournamentListNewBinding upcomingTournamentListNewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.upcoming_tournament_list_new, parent, false);
        return new UpcomingDashboardAdapter.MyViewHolder(upcomingTournamentListNewBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        final UpcommingDashboardModel upcomingData = upcomingDataList.get(position);

        holder.upcomingTournamentListNewBinding.armyUpcomingHeaderTxt.setText(upcomingData.getTourName());
        holder.upcomingTournamentListNewBinding.armyUpcomingSubTxt.setText(upcomingData.getSubCategoryId());
        holder.upcomingTournamentListNewBinding.locationTxt.setText(upcomingData.getTourLocation());
        holder.upcomingTournamentListNewBinding.armyUpcomingPraTxt.setText(upcomingData.getTourShortDescription());


        Utils.setImageInImageView(upcomingData.getFutureTourThumbImageURL(), holder.upcomingTournamentListNewBinding.bannerImg, mcontext);

        if (!upcomingData.getStr1().equalsIgnoreCase("")) {
            holder.upcomingTournamentListNewBinding.linear1Txt.setVisibility(View.VISIBLE);
            holder.upcomingTournamentListNewBinding.linear1Txt.setText(upcomingData.getStr1());
        } else {
            holder.upcomingTournamentListNewBinding.linear1Txt.setVisibility(View.GONE);
        }

        if (!upcomingData.getStr2().equalsIgnoreCase("")) {
            if (!upcomingData.getStr2().equalsIgnoreCase("1")) {
                holder.upcomingTournamentListNewBinding.linear2Txt.setVisibility(View.VISIBLE);
                holder.upcomingTournamentListNewBinding.linear2Txt.setText(upcomingData.getStr2());
            } else {
                holder.upcomingTournamentListNewBinding.linear2Txt.setVisibility(View.GONE);
            }
        } else {
            holder.upcomingTournamentListNewBinding.linear2Txt.setVisibility(View.GONE);
        }

        if (!upcomingData.getStr3().equalsIgnoreCase("")) {
            if (!upcomingData.getStr3().equalsIgnoreCase("1")) {
                holder.upcomingTournamentListNewBinding.linear3Txt.setVisibility(View.VISIBLE);
                holder.upcomingTournamentListNewBinding.linear3Txt.setText(upcomingData.getStr3());
            } else {
                holder.upcomingTournamentListNewBinding.linear3Txt.setVisibility(View.GONE);
            }
        } else {
            holder.upcomingTournamentListNewBinding.linear3Txt.setVisibility(View.GONE);
        }
        holder.upcomingTournamentListNewBinding.upcomingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upcomingData.getPageTypeId().equals(1)) {
                    Intent ftpIntent = new Intent(mcontext, FTPDetailsActivity.class);
                    ftpIntent.putExtra("ftpmaintitle", upcomingData.getTourName());
                    ftpIntent.putExtra("ftpdate", upcomingData.getDateAdded());
                    ftpIntent.putExtra("ftpshortdesc", upcomingData.getTourShortDescription());
                    ftpIntent.putExtra("ftptourdesc", upcomingData.getTourDescription());
                    ftpIntent.putExtra("ftpbannerimg", upcomingData.getFutureTourThumbImageURL());
                    ftpIntent.putExtra("str1", upcomingData.getStr1());
                    ftpIntent.putExtra("str2", upcomingData.getStr2());
                    ftpIntent.putExtra("str3", upcomingData.getStr3());
                    ftpIntent.putExtra("ftpId", upcomingData.getFutureTourId());
                    ftpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(ftpIntent);
                } else if (upcomingData.getPageTypeId().equals(2)) {
                    Intent registerIntent = new Intent(mcontext, RegisterInterestActivityNew.class);
                    registerIntent.putExtra("tournamentId",String.valueOf(upcomingData.getFutureTourId()));
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(registerIntent);
                }else if (upcomingData.getPageTypeId().equals(3)){

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
        return upcomingDataList.size();
    }
}

