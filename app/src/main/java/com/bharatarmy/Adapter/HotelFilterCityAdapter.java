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
import com.bharatarmy.databinding.HotelFilterCityListItemBinding;
import com.bharatarmy.databinding.HotelFilterTeamListItemBinding;

import java.util.ArrayList;

public class HotelFilterCityAdapter extends RecyclerView.Adapter<HotelFilterCityAdapter.MyViewHolder> {
    Context mContext;
    //    List<InquiryStatusModel> matchTeamList;
    ArrayList<TravelModel> tournamnetcitylist;
    private ArrayList<String> dataCheck = new ArrayList<String>();

    public HotelFilterCityAdapter(Context mContext, ArrayList<TravelModel> tournamnetcitylist) {
        this.mContext=mContext;
        this.tournamnetcitylist=tournamnetcitylist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        HotelFilterCityListItemBinding hotelFilterCityListItemBinding;

        public MyViewHolder(HotelFilterCityListItemBinding hotelFilterCityListItemBinding) {
            super(hotelFilterCityListItemBinding.getRoot());

            this.hotelFilterCityListItemBinding=hotelFilterCityListItemBinding;
        }
    }


    @Override
    public HotelFilterCityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        HotelFilterCityListItemBinding hotelFilterCityListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hotel_filter_city_list_item,parent,false);
        return new HotelFilterCityAdapter.MyViewHolder(hotelFilterCityListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(HotelFilterCityAdapter.MyViewHolder holder, int position) {

//        final InquiryStatusModel teamdetail = matchTeamList.get(position);
        final  TravelModel citydetail = tournamnetcitylist.get(position);


        holder.hotelFilterCityListItemBinding.matchCityTxt.setText(citydetail.getCityHotelAmenitiesImage());


        holder.hotelFilterCityListItemBinding.cityselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hotelFilterCityListItemBinding.selectedChk.isChecked()){
                    holder.hotelFilterCityListItemBinding.selectedChk.setChecked(false);
                    citydetail.setCityHotelAmenitiesName("0");
                }else{
                    holder.hotelFilterCityListItemBinding.selectedChk.setChecked(true);
                    citydetail.setCityHotelAmenitiesName("1");

                }

            }
        });

        holder.hotelFilterCityListItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hotelFilterCityListItemBinding.selectedChk.isChecked()){
                    holder.hotelFilterCityListItemBinding.selectedChk.setChecked(true);
                    citydetail.setCityHotelAmenitiesName("1");
                }else{
                    holder.hotelFilterCityListItemBinding.selectedChk.setChecked(false);
                    citydetail.setCityHotelAmenitiesName("0");

                }

            }
        });

        if (citydetail.getCityHotelAmenitiesName().equalsIgnoreCase("1")){
            holder.hotelFilterCityListItemBinding.selectedChk.setChecked(true);
        }else{
            holder.hotelFilterCityListItemBinding.selectedChk.setChecked(false);
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
        return tournamnetcitylist.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}





