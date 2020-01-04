package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchSelectHotelActivity;
import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchHospitalityItemBinding;

import java.util.ArrayList;

public class TravelMatchHospitalityAdapter extends RecyclerView.Adapter<TravelMatchHospitalityAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> tournamenthospitalitylist;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    int selectedposition;

    public TravelMatchHospitalityAdapter(Context mContext, ArrayList<TravelModel> tournamenthospitalitylist, int selectedposition) {
        this.mContext = mContext;
        this.tournamenthospitalitylist=tournamenthospitalitylist;
        this.selectedposition=selectedposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelMatchHospitalityItemBinding travelMatchHospitalityItemBinding;

        public MyViewHolder(TravelMatchHospitalityItemBinding travelMatchHospitalityItemBinding) {
            super(travelMatchHospitalityItemBinding.getRoot());

            this.travelMatchHospitalityItemBinding = travelMatchHospitalityItemBinding;

        }
    }


    @Override
    public TravelMatchHospitalityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchHospitalityItemBinding travelMatchHospitalityItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_hospitality_item, parent, false);
        return new TravelMatchHospitalityAdapter.MyViewHolder(travelMatchHospitalityItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchHospitalityAdapter.MyViewHolder holder, int position) {
        TravelModel detail = tournamenthospitalitylist.get(position);


        holder.travelMatchHospitalityItemBinding.tickethospitalitypriceTxt.setText(detail.getTicket_hospitality_price());
        Utils.setImageInImageView(detail.getTicket_hospitality_bannerImage(), holder.travelMatchHospitalityItemBinding.travelHospitalityBannerImg, mContext);
        holder.travelMatchHospitalityItemBinding.categoryNameTxt.setText(detail.getTicket_hospitality_namecategory());
        holder.travelMatchHospitalityItemBinding.showPackageTourDescriptionTxt.setText(detail.getTicket_hospitality_desc());

        if (!detail.getTicket_hospitality_offers().equalsIgnoreCase("")) {
            holder.travelMatchHospitalityItemBinding.offersLinear.setVisibility(View.VISIBLE);
            holder.travelMatchHospitalityItemBinding.offersTxt.setText(detail.getTicket_hospitality_offers());
        } else {
            holder.travelMatchHospitalityItemBinding.offersLinear.setVisibility(View.GONE);
        }

        holder.travelMatchHospitalityItemBinding.offersLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext,holder.travelMatchHospitalityItemBinding.offersLinear);
                Intent selecthotelIntent=new Intent(mContext, TravelMatchSelectHotelActivity.class);
                selecthotelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(selecthotelIntent);
            }
        });

        if (detail.getTicket_hospitality_inclusion().equalsIgnoreCase("1")) {
            holder.travelMatchHospitalityItemBinding.hotelLinear.setVisibility(View.VISIBLE);
            holder.travelMatchHospitalityItemBinding.sightseenLinear.setVisibility(View.GONE);
            holder.travelMatchHospitalityItemBinding.restaurantLinear.setVisibility(View.GONE);
        } else if (detail.getTicket_hospitality_inclusion().equalsIgnoreCase("2")){
            holder.travelMatchHospitalityItemBinding.hotelLinear.setVisibility(View.VISIBLE);
            holder.travelMatchHospitalityItemBinding.sightseenLinear.setVisibility(View.VISIBLE);
            holder.travelMatchHospitalityItemBinding.restaurantLinear.setVisibility(View.GONE);
        }else if(detail.getTicket_hospitality_inclusion().equalsIgnoreCase("3")){
            holder.travelMatchHospitalityItemBinding.hotelLinear.setVisibility(View.VISIBLE);
            holder.travelMatchHospitalityItemBinding.sightseenLinear.setVisibility(View.VISIBLE);
            holder.travelMatchHospitalityItemBinding.restaurantLinear.setVisibility(View.VISIBLE);
        }

        if (selectedposition == position){
            holder.travelMatchHospitalityItemBinding.hospitalitysCartAddimage.setImageResource(R.drawable.fill_selected_checkbox);
            holder.travelMatchHospitalityItemBinding.hospitalityLayout.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
        }else{
            holder.travelMatchHospitalityItemBinding.hospitalitysCartAddimage.setImageResource(R.drawable.ic_blank_check);
            holder.travelMatchHospitalityItemBinding.hospitalityLayout.setBackground(mContext.getResources().getDrawable(R.drawable.match_detail_curveshape));
        }

        holder.travelMatchHospitalityItemBinding.hospitalityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedposition=position;
                notifyDataSetChanged();
                    Intent hospitalityCategoryIntent=new Intent(mContext, TravelMatchTicketAndHospitalityDetailActivity.class);
                    hospitalityCategoryIntent.putExtra("categoryName",detail.getTicket_hospitality_namecategory());
                    hospitalityCategoryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(hospitalityCategoryIntent);

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
        return tournamenthospitalitylist.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}









