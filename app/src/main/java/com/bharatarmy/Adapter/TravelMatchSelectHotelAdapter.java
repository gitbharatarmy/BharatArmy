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

import com.bharatarmy.Activity.TravelMatchHotelRoomTypeActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelMatchSelectHotelListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchSelectHotelAdapter extends RecyclerView.Adapter<TravelMatchSelectHotelAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> hotelList;
    MorestoryClick morestoryClick;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    ArrayList<TravelModel> matchHotelAmenitiesList;
    MatchHotelAmenitiesAdapter matchHotelAmenitiesAdapter;


    public TravelMatchSelectHotelAdapter(Context mContext, ArrayList<TravelModel> hotelList) {
        this.mContext = mContext;
        this.hotelList = hotelList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelMatchSelectHotelListItemBinding travelMatchSelectHotelListItemBinding;

        public MyViewHolder(TravelMatchSelectHotelListItemBinding travelMatchSelectHotelListItemBinding) {
            super(travelMatchSelectHotelListItemBinding.getRoot());

            this.travelMatchSelectHotelListItemBinding = travelMatchSelectHotelListItemBinding;


        }
    }


    @Override
    public TravelMatchSelectHotelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TravelMatchSelectHotelListItemBinding travelMatchSelectHotelListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_select_hotel_list_item, parent, false);
        return new TravelMatchSelectHotelAdapter.MyViewHolder(travelMatchSelectHotelListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(TravelMatchSelectHotelAdapter.MyViewHolder holder, int position) {
        TravelModel detail = hotelList.get(position);
        Utils.setImageInImageView(detail.getCityAllHotelImage(), holder.travelMatchSelectHotelListItemBinding.hotelImg, mContext);
        holder.travelMatchSelectHotelListItemBinding.hotelnameTxt.setText(detail.getCityAllHotelName());
        holder.travelMatchSelectHotelListItemBinding.hotelpriceTxt.setText(detail.getCityAllHotelPrice());
        holder.travelMatchSelectHotelListItemBinding.ratingBar.setCount(detail.getCityAllHotelRating());
        holder.travelMatchSelectHotelListItemBinding.hoteldescTxt.setText(detail.getCityAllHotelLocation());

        Utils.setImageInImageView(AppConfiguration.IMAGE_URL+"d_hotelroom2.jpg",holder.travelMatchSelectHotelListItemBinding.roomImg,mContext);

        matchHotelAmenitiesList = new ArrayList<TravelModel>();
        matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "parking.png", "Parking"));
        matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "tv.png", "Tv"));
        matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "bathtub.png", "Bathtub"));

        matchHotelAmenitiesAdapter = new MatchHotelAmenitiesAdapter(mContext, matchHotelAmenitiesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.travelMatchSelectHotelListItemBinding.amenities.setLayoutManager(mLayoutManager);
        holder.travelMatchSelectHotelListItemBinding.amenities.setItemAnimator(new DefaultItemAnimator());
        holder.travelMatchSelectHotelListItemBinding.amenities.setAdapter(matchHotelAmenitiesAdapter);

        holder.travelMatchSelectHotelListItemBinding.selectRoomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent roomIntent = new Intent(mContext, TravelMatchHotelRoomTypeActivity.class);
                roomIntent.putExtra("clickposition",String.valueOf(position));
                roomIntent.putExtra("roomName",holder.travelMatchSelectHotelListItemBinding.roomNametxt.getText().toString());

                roomIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(roomIntent);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()){
            for (final Object payload : payloads) {
                Log.d("payloadHotelDetail:",payload.toString());

                String payLoaddata =payload.toString();
                String [] splitvalue=payLoaddata.split("\\|");
                holder.travelMatchSelectHotelListItemBinding.roomNametxt.setText(splitvalue[1]);
                Utils.setImageInImageView(splitvalue[2],holder.travelMatchSelectHotelListItemBinding.roomImg,mContext);

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
        return hotelList.size();
    }

    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}





