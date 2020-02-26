package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AddToCartActivity;
import com.bharatarmy.Activity.TravelMatchSelectHotelActivity;
import com.bharatarmy.Activity.TravelMatchTicketAndHosipitalityticketdetailActivity;
import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityDetailActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchScheduleTicketDetailListItemBinding;


import java.util.ArrayList;
import java.util.List;

public class TravelMatchScheduleTicketDetailAdapter extends RecyclerView.Adapter<TravelMatchScheduleTicketDetailAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> travelticketList;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    int selectedposition, adapteraddcartposition;
    MorestoryClick morestoryClick;
    image_click image_click;

    public TravelMatchScheduleTicketDetailAdapter(Context mContext, ArrayList<TravelModel> travelticketList,
                                                  MorestoryClick morestoryClick, image_click image_click) {
        this.mContext = mContext;
        this.travelticketList = travelticketList;
        this.morestoryClick = morestoryClick;
        this.image_click = image_click;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelMatchScheduleTicketDetailListItemBinding travelMatchScheduleTicketDetailListItemBinding;

        public MyViewHolder(TravelMatchScheduleTicketDetailListItemBinding travelMatchScheduleTicketDetailListItemBinding) {
            super(travelMatchScheduleTicketDetailListItemBinding.getRoot());

            this.travelMatchScheduleTicketDetailListItemBinding = travelMatchScheduleTicketDetailListItemBinding;

        }
    }


    @Override
    public TravelMatchScheduleTicketDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchScheduleTicketDetailListItemBinding travelMatchScheduleTicketDetailListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_schedule_ticket_detail_list_item, parent, false);
        return new TravelMatchScheduleTicketDetailAdapter.MyViewHolder(travelMatchScheduleTicketDetailListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchScheduleTicketDetailAdapter.MyViewHolder holder, int position) {
        TravelModel detail = travelticketList.get(position);


        holder.travelMatchScheduleTicketDetailListItemBinding.scheduleMatchTicketPriceTxt.priceTxt.setText(detail.getTicket_hospitality_price());
        Utils.setImageInImageView(detail.getTicket_hospitality_bannerImage(), holder.travelMatchScheduleTicketDetailListItemBinding.scheduleMatchTravelTicketBannerImg, mContext);
        holder.travelMatchScheduleTicketDetailListItemBinding.scheduleMatchTicketCategoryNameTxt.setText(detail.getTicket_hospitality_namecategory());
        holder.travelMatchScheduleTicketDetailListItemBinding.scheduleMatchTicketDescriptionTxt.setText(detail.getTicket_hospitality_desc());

        if (!detail.getTicket_hospitality_offers().equalsIgnoreCase("")) {
            holder.travelMatchScheduleTicketDetailListItemBinding.offersLinear.setVisibility(View.VISIBLE);
            holder.travelMatchScheduleTicketDetailListItemBinding.offersTxt.setText(detail.getTicket_hospitality_offers());
        } else {
            holder.travelMatchScheduleTicketDetailListItemBinding.offersLinear.setVisibility(View.GONE);
        }

        holder.travelMatchScheduleTicketDetailListItemBinding.offersLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext, holder.travelMatchScheduleTicketDetailListItemBinding.offersLinear);
                Intent selecthotelIntent = new Intent(mContext, TravelMatchSelectHotelActivity.class);
                selecthotelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(selecthotelIntent);
            }
        });

        holder.travelMatchScheduleTicketDetailListItemBinding.scheduleMatchTicketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent ticketCategoryIntent = new Intent(mContext, TravelMatchTicketAndHosipitalityticketdetailActivity.class);
                ticketCategoryIntent.putExtra("categoryName", detail.getTicket_hospitality_namecategory());
                ticketCategoryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(ticketCategoryIntent);


            }
        });
        holder.travelMatchScheduleTicketDetailListItemBinding.cartAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.travelMatchScheduleTicketDetailListItemBinding.cartAddLayout.setVisibility(View.GONE);
                holder.travelMatchScheduleTicketDetailListItemBinding.cartRemoveLayout.setVisibility(View.VISIBLE);
                adapteraddcartposition = position;
                morestoryClick.getmorestoryClick();
            }
        });
        holder.travelMatchScheduleTicketDetailListItemBinding.cartRemoveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.travelMatchScheduleTicketDetailListItemBinding.cartAddLayout.setVisibility(View.VISIBLE);
                holder.travelMatchScheduleTicketDetailListItemBinding.cartRemoveLayout.setVisibility(View.GONE);
                image_click.image_more_click();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            for (final Object payload : payloads) {
                if (payload.equals("remove")) {
                    holder.travelMatchScheduleTicketDetailListItemBinding.cartAddLayout.setVisibility(View.VISIBLE);
                    holder.travelMatchScheduleTicketDetailListItemBinding.cartRemoveLayout.setVisibility(View.GONE);
                }
            }
        } else {
            // in this case regular onBindViewHolder will be called
            super.onBindViewHolder(holder, position, payloads);
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
        return travelticketList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }

    public int adptercartAddPosition() {
        return adapteraddcartposition;
    }
}








