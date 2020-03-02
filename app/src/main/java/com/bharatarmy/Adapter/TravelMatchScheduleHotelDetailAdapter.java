package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AddToCartActivity;
import com.bharatarmy.Activity.TravelCityHotelDetailsActivity;
import com.bharatarmy.Activity.TravelMatchHotelRoomTypeActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;

import com.bharatarmy.databinding.TravelMatchScheduleHotelDetailListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchScheduleHotelDetailAdapter extends RecyclerView.Adapter<TravelMatchScheduleHotelDetailAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> travelhotelList;

    MorestoryClick morestoryClick;
    image_click image_click;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    String audltcountstr,childcountstr;
    int adapteraddcartposition;

    /*Amenities*/
    ArrayList<TravelModel> matchHotelAmenitiesList;
    MatchHotelAmenitiesAdapter matchHotelAmenitiesAdapter;

    public TravelMatchScheduleHotelDetailAdapter(Context mContext, ArrayList<TravelModel> travelhotelList,
                                                 MorestoryClick morestoryClick, image_click image_click) {
        this.mContext = mContext;
        this.travelhotelList=travelhotelList;
        this.morestoryClick=morestoryClick;
        this.image_click = image_click;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelMatchScheduleHotelDetailListItemBinding travelMatchScheduleHotelDetailListItemBinding;

        public MyViewHolder(TravelMatchScheduleHotelDetailListItemBinding travelMatchScheduleHotelDetailListItemBinding) {
            super(travelMatchScheduleHotelDetailListItemBinding.getRoot());

            this.travelMatchScheduleHotelDetailListItemBinding = travelMatchScheduleHotelDetailListItemBinding;


        }
    }


    @Override
    public TravelMatchScheduleHotelDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TravelMatchScheduleHotelDetailListItemBinding travelMatchScheduleHotelDetailListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_schedule_hotel_detail_list_item, parent, false);
        return new TravelMatchScheduleHotelDetailAdapter.MyViewHolder(travelMatchScheduleHotelDetailListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(TravelMatchScheduleHotelDetailAdapter.MyViewHolder holder, int position) {
        TravelModel detail = travelhotelList.get(position);
        Utils.setImageInImageView(detail.getCityHotelImageStr(), holder.travelMatchScheduleHotelDetailListItemBinding.scheduleMatchHotelImg, mContext);
        holder.travelMatchScheduleHotelDetailListItemBinding.scheduleMatchHotelnameTxt.setText(detail.getCityHotelNameStr());
        holder.travelMatchScheduleHotelDetailListItemBinding.scheduleMatchHotelpriceTxt.setText(detail.getCityHotelPriceStr());
        holder.travelMatchScheduleHotelDetailListItemBinding.ratingBar.setCount(detail.getCityHotelRatingStr());
        holder.travelMatchScheduleHotelDetailListItemBinding.scheduleMatchHoteldescTxt.setText(detail.getCityHotelDescStr());

        Utils.setImageInImageView(AppConfiguration.IMAGE_URL+"d_hotelroom2.jpg",holder.travelMatchScheduleHotelDetailListItemBinding.roomImg,mContext);

        matchHotelAmenitiesList = new ArrayList<TravelModel>();
        matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "parking.png", "Parking"));
        matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "tv.png", "Tv"));
        matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "bathtub.png", "Bathtub"));

        matchHotelAmenitiesAdapter = new MatchHotelAmenitiesAdapter(mContext, matchHotelAmenitiesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.travelMatchScheduleHotelDetailListItemBinding.amenities.setLayoutManager(mLayoutManager);
        holder.travelMatchScheduleHotelDetailListItemBinding.amenities.setItemAnimator(new DefaultItemAnimator());
        holder.travelMatchScheduleHotelDetailListItemBinding.amenities.setAdapter(matchHotelAmenitiesAdapter);

        audltcountstr = holder.travelMatchScheduleHotelDetailListItemBinding.countOfAudltTxt.getText().toString();
        childcountstr=holder.travelMatchScheduleHotelDetailListItemBinding.countOfChildTxt.getText().toString();

        holder.travelMatchScheduleHotelDetailListItemBinding.selectRoomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent roomIntent = new Intent(mContext, TravelMatchHotelRoomTypeActivity.class);
                roomIntent.putExtra("clickposition",String.valueOf(position));
                roomIntent.putExtra("roomName",holder.travelMatchScheduleHotelDetailListItemBinding.roomNametxt.getText().toString());
                roomIntent.putExtra("adult",audltcountstr);
                roomIntent.putExtra("child",childcountstr);
                roomIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(roomIntent);
            }
        });

        holder.travelMatchScheduleHotelDetailListItemBinding.addCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.travelMatchScheduleHotelDetailListItemBinding.addCartLayout.setVisibility(View.GONE);
                holder.travelMatchScheduleHotelDetailListItemBinding.removeCartLayout.setVisibility(View.VISIBLE);
                adapteraddcartposition = position;
                morestoryClick.getmorestoryClick();
            }
        });
        holder.travelMatchScheduleHotelDetailListItemBinding.removeCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.travelMatchScheduleHotelDetailListItemBinding.addCartLayout.setVisibility(View.VISIBLE);
                holder.travelMatchScheduleHotelDetailListItemBinding.removeCartLayout.setVisibility(View.GONE);
                image_click.image_more_click();
            }
        });

        holder.travelMatchScheduleHotelDetailListItemBinding.scheduleMatchHotelLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityHotelDetail=new Intent(mContext, TravelCityHotelDetailsActivity.class);
                cityHotelDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(cityHotelDetail);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull TravelMatchScheduleHotelDetailAdapter.MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()){
            for (final Object payload : payloads) {
                Log.d("payloadHotelDetail:",payload.toString());
                    String payLoaddata = payload.toString();
                    String[] splitvalue = payLoaddata.split("\\|");
                    holder.travelMatchScheduleHotelDetailListItemBinding.roomNametxt.setText(splitvalue[1]);
                    Utils.setImageInImageView(splitvalue[2], holder.travelMatchScheduleHotelDetailListItemBinding.roomImg, mContext);
            }
        }else{
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
        return travelhotelList.size();
    }

    public int adptercartAddPosition() {
        return adapteraddcartposition;
    }
}






