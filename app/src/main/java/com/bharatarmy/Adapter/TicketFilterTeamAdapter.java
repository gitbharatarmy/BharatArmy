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
import com.bharatarmy.databinding.TicketFilterTeamItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TicketFilterTeamAdapter extends RecyclerView.Adapter<TicketFilterTeamAdapter.MyViewHolder> {
    Context mContext;
    List<InquiryStatusModel> matchTeamList;

    private ArrayList<String> dataCheck = new ArrayList<String>();


    public TicketFilterTeamAdapter(Context mContext, List<InquiryStatusModel> matchTeamList) {
        this.mContext=mContext;
        this.matchTeamList=matchTeamList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TicketFilterTeamItemBinding ticketFilterTeamItemBinding;

        public MyViewHolder(TicketFilterTeamItemBinding ticketFilterTeamItemBinding) {
            super(ticketFilterTeamItemBinding.getRoot());

            this.ticketFilterTeamItemBinding=ticketFilterTeamItemBinding;
        }
    }


    @Override
    public TicketFilterTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TicketFilterTeamItemBinding ticketFilterTeamItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ticket_filter_team_item,parent,false);
        return new TicketFilterTeamAdapter.MyViewHolder(ticketFilterTeamItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TicketFilterTeamAdapter.MyViewHolder holder, int position) {

        final InquiryStatusModel teamdetail = matchTeamList.get(position);

        Utils.setImageInImageView(teamdetail.getCountryFlagUrl(),holder.ticketFilterTeamItemBinding.teamFlag,mContext);
        holder.ticketFilterTeamItemBinding.matchTeamTxt.setText(teamdetail.getCountryName());


        holder.ticketFilterTeamItemBinding.teamselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ticketFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.ticketFilterTeamItemBinding.selectedChk.setChecked(false);
                    teamdetail.setTeamSelected("0");
                }else{
                    holder.ticketFilterTeamItemBinding.selectedChk.setChecked(true);
                    teamdetail.setTeamSelected("1");

                }

            }
        });

        holder.ticketFilterTeamItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ticketFilterTeamItemBinding.selectedChk.isChecked()){
                    holder.ticketFilterTeamItemBinding.selectedChk.setChecked(true);
                    teamdetail.setTeamSelected("1");
                }else{
                    holder.ticketFilterTeamItemBinding.selectedChk.setChecked(false);
                    teamdetail.setTeamSelected("0");

                }

            }
        });

        if (teamdetail.getTeamSelected().equalsIgnoreCase("1")){
            holder.ticketFilterTeamItemBinding.selectedChk.setChecked(true);
        }else{
            holder.ticketFilterTeamItemBinding.selectedChk.setChecked(false);
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



