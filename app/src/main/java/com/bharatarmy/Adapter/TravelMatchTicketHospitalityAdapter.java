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
import com.bharatarmy.Activity.TravelMatchTicketAndHosipitalityticketdetailActivity;
import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchTicketHospitalityItemListBinding;

import java.util.ArrayList;

public class TravelMatchTicketHospitalityAdapter extends RecyclerView.Adapter<TravelMatchTicketHospitalityAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> tickethospitalityList;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    int selectedposition;

    public TravelMatchTicketHospitalityAdapter(Context mContext, ArrayList<TravelModel> tickethospitalityList, int selectedposition) {
        this.mContext = mContext;
        this.tickethospitalityList = tickethospitalityList;
        this.selectedposition=selectedposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelMatchTicketHospitalityItemListBinding travelMatchTicketHospitalityItemListBinding;

        public MyViewHolder(TravelMatchTicketHospitalityItemListBinding travelMatchTicketHospitalityItemListBinding) {
            super(travelMatchTicketHospitalityItemListBinding.getRoot());

            this.travelMatchTicketHospitalityItemListBinding = travelMatchTicketHospitalityItemListBinding;

        }
    }


    @Override
    public TravelMatchTicketHospitalityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchTicketHospitalityItemListBinding travelMatchTicketHospitalityItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_ticket_hospitality_item_list, parent, false);
        return new TravelMatchTicketHospitalityAdapter.MyViewHolder(travelMatchTicketHospitalityItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchTicketHospitalityAdapter.MyViewHolder holder, int position) {
        TravelModel detail = tickethospitalityList.get(position);

        if (position != 0 && position != 1) {
            holder.travelMatchTicketHospitalityItemListBinding.headerTitleRel.setVisibility(View.GONE);
        } else {
            holder.travelMatchTicketHospitalityItemListBinding.headerTitleRel.setVisibility(View.VISIBLE);
            holder.travelMatchTicketHospitalityItemListBinding.ticketTypeTxt.setText(detail.getTicket_hospitality_mainheader_title());
        }
        holder.travelMatchTicketHospitalityItemListBinding.tickethospitalitypriceTxt.setText(detail.getTicket_hospitality_price());
        Utils.setImageInImageView(detail.getTicket_hospitality_bannerImage(), holder.travelMatchTicketHospitalityItemListBinding.travelTicketHospitalityBannerImg, mContext);
        holder.travelMatchTicketHospitalityItemListBinding.categoryNameTxt.setText(detail.getTicket_hospitality_namecategory());
        holder.travelMatchTicketHospitalityItemListBinding.showPackageTourDescriptionTxt.setText(detail.getTicket_hospitality_desc());

        if (!detail.getTicket_hospitality_offers().equalsIgnoreCase("")) {
            holder.travelMatchTicketHospitalityItemListBinding.offersLinear.setVisibility(View.VISIBLE);
            holder.travelMatchTicketHospitalityItemListBinding.offersTxt.setText(detail.getTicket_hospitality_offers());
        } else {
            holder.travelMatchTicketHospitalityItemListBinding.offersLinear.setVisibility(View.GONE);
        }

        holder.travelMatchTicketHospitalityItemListBinding.offersLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext,holder.travelMatchTicketHospitalityItemListBinding.offersLinear);
                   Intent selecthotelIntent=new Intent(mContext, TravelMatchSelectHotelActivity.class);
                   selecthotelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   mContext.startActivity(selecthotelIntent);
            }
        });

//        if (detail.getTicket_hospitality_inclusion().equalsIgnoreCase("1")) {
//            holder.travelMatchTicketHospitalityItemListBinding.hotelLinear.setVisibility(View.VISIBLE);
//            holder.travelMatchTicketHospitalityItemListBinding.sightseenLinear.setVisibility(View.GONE);
//            holder.travelMatchTicketHospitalityItemListBinding.restaurantLinear.setVisibility(View.GONE);
//        } else {
//            holder.travelMatchTicketHospitalityItemListBinding.hotelLinear.setVisibility(View.VISIBLE);
//            holder.travelMatchTicketHospitalityItemListBinding.sightseenLinear.setVisibility(View.VISIBLE);
//            holder.travelMatchTicketHospitalityItemListBinding.restaurantLinear.setVisibility(View.VISIBLE);
//        }

        if (selectedposition == position){
            holder.travelMatchTicketHospitalityItemListBinding.ticketHospitalitysCartAddimage.setImageResource(R.drawable.fill_selected_checkbox);
            holder.travelMatchTicketHospitalityItemListBinding.ticketHospitalityLayout.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
        }else{
            holder.travelMatchTicketHospitalityItemListBinding.ticketHospitalitysCartAddimage.setImageResource(R.drawable.ic_blank_check);
            holder.travelMatchTicketHospitalityItemListBinding.ticketHospitalityLayout.setBackground(mContext.getResources().getDrawable(R.drawable.match_detail_curveshape));
        }

        holder.travelMatchTicketHospitalityItemListBinding.ticketHospitalityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectedposition=position;
               notifyDataSetChanged();

               if (detail.getTicket_hospitality_clickhere().equalsIgnoreCase("ticket")){
                   Intent ticketCategoryIntent=new Intent(mContext, TravelMatchTicketAndHosipitalityticketdetailActivity.class);
                   ticketCategoryIntent.putExtra("categoryName",detail.getTicket_hospitality_namecategory());
                   ticketCategoryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mContext.startActivity(ticketCategoryIntent);
               }else{
                   Intent hospitalityCategoryIntent=new Intent(mContext, TravelMatchTicketAndHospitalityDetailActivity.class);
                   hospitalityCategoryIntent.putExtra("categoryName",detail.getTicket_hospitality_namecategory());
                   hospitalityCategoryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mContext.startActivity(hospitalityCategoryIntent);
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
        return tickethospitalityList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}








