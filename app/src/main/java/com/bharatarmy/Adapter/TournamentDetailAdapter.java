package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.InquiryListItemBinding;
import com.bharatarmy.databinding.TournamentDetailItemBinding;

import java.util.List;

public class TournamentDetailAdapter extends RecyclerView.Adapter<TournamentDetailAdapter.MyViewHolder> {
    Context mContext;
    List<MoreDetailDataModel> moreInquiryDetaildataList;


    public TournamentDetailAdapter(Context mContext, List<MoreDetailDataModel> moreInquiryDetaildataList) {
        this.mContext=mContext;
        this.moreInquiryDetaildataList=moreInquiryDetaildataList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TournamentDetailItemBinding tournamentDetailItemBinding;

        public MyViewHolder(TournamentDetailItemBinding tournamentDetailItemBinding) {
            super(tournamentDetailItemBinding.getRoot());
            this.tournamentDetailItemBinding = tournamentDetailItemBinding;
        }
    }


    @Override
    public TournamentDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TournamentDetailItemBinding tournamentDetailItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.tournament_detail_item, parent, false);
        return new TournamentDetailAdapter.MyViewHolder(tournamentDetailItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TournamentDetailAdapter.MyViewHolder holder, int position) {
        MoreDetailDataModel detailDataModel = moreInquiryDetaildataList.get(position);

       holder.tournamentDetailItemBinding.tournamentNameTxt.setText(detailDataModel.getTourName() + " | " + detailDataModel.getStrMatchType());
        holder.tournamentDetailItemBinding. tournamenttimeTxt.setText(detailDataModel.getStrLocalMatchDateTime());
        holder.tournamentDetailItemBinding. firstcountryNameTxt.setText(detailDataModel.getFromCountryName());
        holder.tournamentDetailItemBinding. secondcountryNameTxt.setText(detailDataModel.getToCountryName());
        holder.tournamentDetailItemBinding.tournamentqtydisplayTxt.setText(String.valueOf(detailDataModel.getQty()));
        holder.tournamentDetailItemBinding.tournamentpricedisplayTxt.setText(String.valueOf(detailDataModel.getSalePrice()));
        holder.tournamentDetailItemBinding.tournamenttotaldisplayTxt.setText(String.valueOf(detailDataModel.getQty()*detailDataModel.getSalePrice()));

            Log.d("total :",String.valueOf(detailDataModel.getQty()*detailDataModel.getSalePrice()));
            if (detailDataModel.getMatchNo()>=1 &&detailDataModel.getMatchNo()<9){
                holder.tournamentDetailItemBinding. tournamentdescTxt.setText("Match 0"+detailDataModel.getMatchNo()+" | "+detailDataModel.getStadiumName());
            }else{
                holder.tournamentDetailItemBinding.tournamentdescTxt.setText("Match "+detailDataModel.getMatchNo()+" | "+detailDataModel.getStadiumName());
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
        return moreInquiryDetaildataList.size();
    }

   
}


