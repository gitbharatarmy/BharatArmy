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
import com.bharatarmy.databinding.TicketFilterStadiumItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TicketFilterStadiumAdapter extends RecyclerView.Adapter<TicketFilterStadiumAdapter.MyViewHolder> {
    Context mContext;

    int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    List<InquiryStatusModel> matchTeamVenueList;

    public TicketFilterStadiumAdapter(Context mContext, List<InquiryStatusModel> matchTeamVenueList) {
        this.mContext = mContext;
        this.matchTeamVenueList = matchTeamVenueList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TicketFilterStadiumItemBinding ticketFilterStadiumItemBinding;

        public MyViewHolder(TicketFilterStadiumItemBinding ticketFilterStadiumItemBinding) {
            super(ticketFilterStadiumItemBinding.getRoot());
            this.ticketFilterStadiumItemBinding = ticketFilterStadiumItemBinding;

        }
    }


    @Override
    public TicketFilterStadiumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TicketFilterStadiumItemBinding ticketFilterStadiumItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ticket_filter_stadium_item, parent, false);
        return new TicketFilterStadiumAdapter.MyViewHolder(ticketFilterStadiumItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TicketFilterStadiumAdapter.MyViewHolder holder, int position) {
        final InquiryStatusModel venuedetail = matchTeamVenueList.get(position);
        holder.ticketFilterStadiumItemBinding.matchVenuesTxt.setText(venuedetail.getLabel());

        holder.ticketFilterStadiumItemBinding.matchVenuesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ticketFilterStadiumItemBinding.selectedChk.isChecked()) {
                    holder.ticketFilterStadiumItemBinding.selectedChk.setChecked(false);
                    venuedetail.setVenueSelected("0");
                } else {
                    holder.ticketFilterStadiumItemBinding.selectedChk.setChecked(true);
                    venuedetail.setVenueSelected("1");
                }

            }
        });
        holder.ticketFilterStadiumItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ticketFilterStadiumItemBinding.selectedChk.isChecked()) {
                    holder.ticketFilterStadiumItemBinding.selectedChk.setChecked(true);
                    venuedetail.setVenueSelected("1");
                } else {
                    holder.ticketFilterStadiumItemBinding.selectedChk.setChecked(false);
                    venuedetail.setVenueSelected("0");
                }

            }
        });
        if (venuedetail.getVenueSelected().equalsIgnoreCase("1")) {
            holder.ticketFilterStadiumItemBinding.selectedChk.setChecked(true);
        } else {
            holder.ticketFilterStadiumItemBinding.selectedChk.setChecked(false);
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
        return matchTeamVenueList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}




