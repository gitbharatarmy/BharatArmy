package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelCityHotelDetailsActivity;
import com.bharatarmy.Activity.TravelMatchHotelRoomTypeActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchDetailtitleItemBinding;
import com.bharatarmy.databinding.MatchHotelDetailItemBinding;


import java.util.ArrayList;
import java.util.List;

public class TravelMatchDetailHotelRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<TravelModel> matchHotelList;
    private static final int HEADER = 0;
    private static final int ITEM = 1;
    String titleNameStr,selectedroomNameStr="",selectedroomImageStr="";
    ArrayList<TravelModel> matchHotelAmenitiesList;
    MatchHotelAmenitiesAdapter matchHotelAmenitiesAdapter;



    public TravelMatchDetailHotelRecyclerAdapter(Context mContext, ArrayList<TravelModel> matchHotelList,
                                                 String titleNameStr, String selectedroomNameStr, String selectedroomImageStr) {
        this.mContext = mContext;
        this.matchHotelList = matchHotelList;
        this.titleNameStr = titleNameStr;
        this.selectedroomNameStr=selectedroomNameStr;
        this.selectedroomImageStr=selectedroomImageStr;
    }


    public class MyItemViewHolder extends RecyclerView.ViewHolder {
    MatchHotelDetailItemBinding matchHotelDetailItemBinding;

        public MyItemViewHolder(MatchHotelDetailItemBinding matchHotelDetailItemBinding) {
            super(matchHotelDetailItemBinding.getRoot());

            this.matchHotelDetailItemBinding=matchHotelDetailItemBinding;
        }
    }



    static class HeaderViewHolder extends RecyclerView.ViewHolder {
       MatchDetailtitleItemBinding matchDetailtitleItemBinding;

        HeaderViewHolder(MatchDetailtitleItemBinding matchDetailtitleItemBinding) {
            super(matchDetailtitleItemBinding.getRoot());

            this.matchDetailtitleItemBinding=matchDetailtitleItemBinding;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                MatchDetailtitleItemBinding matchDetailtitleItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.match_detailtitle_item,parent,false);
                return new TravelMatchDetailHotelRecyclerAdapter.HeaderViewHolder(matchDetailtitleItemBinding);
            default:

                 MatchHotelDetailItemBinding matchHotelDetailItemBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                         R.layout.match_hotel_detail_item,parent,false);
                return new TravelMatchDetailHotelRecyclerAdapter.MyItemViewHolder(matchHotelDetailItemBinding);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            final TravelModel cityallhoteldetail = matchHotelList.get(position - 1);

            Utils.setImageInImageView(AppConfiguration.IMAGE_URL+"d_hotelroom1.jpg",
                    ((MyItemViewHolder) holder).matchHotelDetailItemBinding.roomImg,mContext);

            Utils.setImageInImageView(cityallhoteldetail.getCityAllHotelImage(),
                    ((MyItemViewHolder) holder).matchHotelDetailItemBinding.hotelImg, mContext);

            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.hotelnameTxt.setText(cityallhoteldetail.getCityAllHotelName());
            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.hotelLocationTxt.setText(cityallhoteldetail.getCityAllHotelLocation());
            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.packagepriceTxt.setText("₹ " + cityallhoteldetail.getCityAllHotelPrice());
            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.ratingBar.setCount(cityallhoteldetail.getCityAllHotelRating());

            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.hotelCardclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cityHotelDetail = new Intent(mContext, TravelCityHotelDetailsActivity.class);
                    cityHotelDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(cityHotelDetail);
                }
            });
            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.selectRoomLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent roomIntent = new Intent(mContext, TravelMatchHotelRoomTypeActivity.class);
                    roomIntent.putExtra("clickposition",String.valueOf(position));
                    roomIntent.putExtra("roomName",((MyItemViewHolder) holder).matchHotelDetailItemBinding.roomNametxt.getText().toString());
                    roomIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(roomIntent);
                }
            });
            matchHotelAmenitiesList = new ArrayList<TravelModel>();
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "parking.png", "Parking"));
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "tv.png", "Tv"));
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "bathtub.png", "Bathtub"));


            matchHotelAmenitiesAdapter = new MatchHotelAmenitiesAdapter(mContext, matchHotelAmenitiesList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.amenities.setLayoutManager(mLayoutManager);
            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.amenities.setItemAnimator(new DefaultItemAnimator());
            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.amenities.setAdapter(matchHotelAmenitiesAdapter);

            ((MyItemViewHolder) holder).matchHotelDetailItemBinding.bookLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookIntent = new Intent(mContext, TravelBookActivity.class);
                    bookIntent.putExtra("pacakgeName", "Australian Double Dhamaka: Honeymoon and adventure at one shot");
                    bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(bookIntent);
                }
            });
        } else if (holder.getItemViewType() == HEADER) {
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png",
                    ((HeaderViewHolder) holder).matchDetailtitleItemBinding.firstCountryflagImage, mContext);
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png",
                    ((HeaderViewHolder) holder).matchDetailtitleItemBinding.secondCountryflagImage, mContext);
            ((HeaderViewHolder) holder).matchDetailtitleItemBinding.titleTxtView.setText(titleNameStr);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()){
            for (final Object payload : payloads) {
                if (holder.getItemViewType() == ITEM){
                    String payLoaddata =payload.toString();
                    String [] splitvalue=payLoaddata.split("\\|");

                    selectedroomNameStr=splitvalue[1];
                    selectedroomImageStr=splitvalue[2];

                    ((MyItemViewHolder) holder).matchHotelDetailItemBinding.roomNametxt.setText(selectedroomNameStr);
                    Utils.setImageInImageView(selectedroomImageStr,((MyItemViewHolder) holder).matchHotelDetailItemBinding.roomImg,mContext);

                }

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
        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return matchHotelList.size() + 1;
    }


}




