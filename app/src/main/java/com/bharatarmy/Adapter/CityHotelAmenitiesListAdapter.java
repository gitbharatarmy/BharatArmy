package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HotelAmenitiesItemBinding;

import java.util.ArrayList;


public class CityHotelAmenitiesListAdapter extends RecyclerView.Adapter<CityHotelAmenitiesListAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> cityHotelAmenitiesList;


    public CityHotelAmenitiesListAdapter(Context mContext, ArrayList<TravelModel> cityHotelAmenitiesList) {
        this.mContext=mContext;
        this.cityHotelAmenitiesList=cityHotelAmenitiesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        HotelAmenitiesItemBinding hotelAmenitiesItemBinding;


        public MyViewHolder(HotelAmenitiesItemBinding hotelAmenitiesItemBinding) {
            super(hotelAmenitiesItemBinding.getRoot());

            this.hotelAmenitiesItemBinding=hotelAmenitiesItemBinding;
        }
    }


    @Override
    public CityHotelAmenitiesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       HotelAmenitiesItemBinding hotelAmenitiesItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
               R.layout.hotel_amenities_item,parent,false);
       return new CityHotelAmenitiesListAdapter.MyViewHolder(hotelAmenitiesItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CityHotelAmenitiesListAdapter.MyViewHolder holder, int position) {
TravelModel amenitiesDetail=cityHotelAmenitiesList.get(position);

        Utils.setImageInImageView(amenitiesDetail.getCityHotelAmenitiesImage(),holder.hotelAmenitiesItemBinding.amenitiesImage,mContext);
holder.hotelAmenitiesItemBinding.amenitiesNameTxt.setText(amenitiesDetail.getCityHotelAmenitiesName());
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
        return cityHotelAmenitiesList.size();
    }

}


