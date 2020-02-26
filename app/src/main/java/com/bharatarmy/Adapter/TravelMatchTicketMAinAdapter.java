package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchTicketItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchTicketMAinAdapter extends RecyclerView.Adapter<TravelMatchTicketMAinAdapter.MyViewHolder> {
    Context mContext;
    List<HomeTemplateDetailModel> matchticketList;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public TravelMatchTicketMAinAdapter(Context mContext, List<HomeTemplateDetailModel> matchticketList) {
        this.mContext = mContext;
        this.matchticketList = matchticketList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelMatchTicketItemListBinding travelMatchTicketItemListBinding;

        public MyViewHolder(TravelMatchTicketItemListBinding travelMatchTicketItemListBinding) {
            super(travelMatchTicketItemListBinding.getRoot());

            this.travelMatchTicketItemListBinding = travelMatchTicketItemListBinding;
        }
    }


    @Override
    public TravelMatchTicketMAinAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TravelMatchTicketItemListBinding travelMatchTicketItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_ticket_item_list, parent, false);
        return new TravelMatchTicketMAinAdapter.MyViewHolder(travelMatchTicketItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchTicketMAinAdapter.MyViewHolder holder, int position) {

        final HomeTemplateDetailModel ticketdetail = matchticketList.get(position);

        if (ticketdetail.getDbFromCountryName().equalsIgnoreCase("")) {
            holder.travelMatchTicketItemListBinding.layout1.firstCountryNameTxt.setText(
                    ticketdetail.getObjFromCountry().getCountryName());
        } else {
            holder.travelMatchTicketItemListBinding.layout1.firstCountryNameTxt.setText(
                    ticketdetail.getDbFromCountryName());
        }
        if (ticketdetail.getDbToCountryName().equalsIgnoreCase("")) {
            holder.travelMatchTicketItemListBinding.layout1.secondCountryNameTxt.setText(
                    ticketdetail.getObjToCountry().getCountryName());
        } else {
            holder.travelMatchTicketItemListBinding.layout1.secondCountryNameTxt.setText(
                    ticketdetail.getDbToCountryName());
        }

        Utils.setImageInImageView(ticketdetail.getObjFromCountry().getCountryFlagUrl(), holder.travelMatchTicketItemListBinding.layout1.firstCountryflagImage, mContext);
        Utils.setImageInImageView(ticketdetail.getObjToCountry().getCountryFlagUrl(), holder.travelMatchTicketItemListBinding.layout1.secondCountryflagImage, mContext);

        holder.travelMatchTicketItemListBinding.layout1.matchTypeTagTxt.setText(ticketdetail.getStrMatchType());
        holder.travelMatchTicketItemListBinding.layout1.matchDateTimeTxt.setText(ticketdetail.getStrMatchDateTime());

        holder.travelMatchTicketItemListBinding.layout1.matchGroundLocationTxt.setText(ticketdetail.getStadiumName());




        holder.travelMatchTicketItemListBinding.layout1.ticketLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ticketandhospitalityIntent = new Intent(mContext, TravelMatchTicketAndHospitalityActivity.class);
                ticketandhospitalityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(ticketandhospitalityIntent);
            }
        });

//        holder.travelMatchTicketItemListBinding.layout1.ticketDetailLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ticketandhospitalityIntent = new Intent(mContext, TravelMatchTicketAndHospitalityActivity.class);
//                ticketandhospitalityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                mContext.startActivity(ticketandhospitalityIntent);
//            }
//        });

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
        return matchticketList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}


