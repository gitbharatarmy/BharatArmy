package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HotelFilterRatingListItemBinding;

import java.util.ArrayList;

public class HotelFilterRatingAdapter extends RecyclerView.Adapter<HotelFilterRatingAdapter.MyViewHolder> {
    Context mContext;
    //    List<InquiryStatusModel> matchTeamList;
    ArrayList<TravelModel> matchRatingList;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public HotelFilterRatingAdapter(Context mContext, ArrayList<TravelModel> matchRatingList) {
        this.mContext = mContext;
        this.matchRatingList = matchRatingList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HotelFilterRatingListItemBinding hotelFilterRatingListItemBinding;

        public MyViewHolder(HotelFilterRatingListItemBinding hotelFilterRatingListItemBinding) {
            super(hotelFilterRatingListItemBinding.getRoot());

            this.hotelFilterRatingListItemBinding = hotelFilterRatingListItemBinding;
        }
    }


    @Override
    public HotelFilterRatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        HotelFilterRatingListItemBinding hotelFilterRatingListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hotel_filter_rating_list_item, parent, false);
        return new HotelFilterRatingAdapter.MyViewHolder(hotelFilterRatingListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(HotelFilterRatingAdapter.MyViewHolder holder, int position) {

//        final InquiryStatusModel teamdetail = matchTeamList.get(position);
        final TravelModel ratingdetail = matchRatingList.get(position);


        holder.hotelFilterRatingListItemBinding.ratingBar.setCount(ratingdetail.getMatchteamFlag());
        holder.hotelFilterRatingListItemBinding.hotelStarTxt.setText(String.valueOf(ratingdetail.getMatchteamFlag()));

        holder.hotelFilterRatingListItemBinding.ratingselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hotelFilterRatingListItemBinding.selectedChk.isChecked()) {
                    holder.hotelFilterRatingListItemBinding.selectedChk.setChecked(false);
                    ratingdetail.setMatchteamVenues("0");
                } else {
                    holder.hotelFilterRatingListItemBinding.selectedChk.setChecked(true);
                    ratingdetail.setMatchteamVenues("1");

                }

            }
        });

        holder.hotelFilterRatingListItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hotelFilterRatingListItemBinding.selectedChk.isChecked()) {
                    holder.hotelFilterRatingListItemBinding.selectedChk.setChecked(true);
                    ratingdetail.setMatchteamVenues("1");
                } else {
                    holder.hotelFilterRatingListItemBinding.selectedChk.setChecked(false);
                    ratingdetail.setMatchteamVenues("0");

                }

            }
        });

        if (ratingdetail.getMatchteamVenues().equalsIgnoreCase("1")) {
            holder.hotelFilterRatingListItemBinding.selectedChk.setChecked(true);
        } else {
            holder.hotelFilterRatingListItemBinding.selectedChk.setChecked(false);
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
        return matchRatingList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}





