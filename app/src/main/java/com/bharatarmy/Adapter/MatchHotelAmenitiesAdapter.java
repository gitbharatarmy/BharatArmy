package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchHotelAmenitiesItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MatchHotelAmenitiesAdapter extends RecyclerView.Adapter<MatchHotelAmenitiesAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> matchHotelAmenitiesList;



    public MatchHotelAmenitiesAdapter(Context mContext, ArrayList<TravelModel> matchHotelAmenitiesList) {
        this.mContext=mContext;
        this.matchHotelAmenitiesList=matchHotelAmenitiesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MatchHotelAmenitiesItemBinding matchHotelAmenitiesItemBinding;
//        ImageView amenities_image;
        public MyViewHolder(MatchHotelAmenitiesItemBinding matchHotelAmenitiesItemBinding) {
            super(matchHotelAmenitiesItemBinding.getRoot());
//            amenities_image=(ImageView)view.findViewById(R.id.amenities_image);

            this.matchHotelAmenitiesItemBinding=matchHotelAmenitiesItemBinding;
        }
    }


    @Override
    public MatchHotelAmenitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.match_hotel_amenities_item, parent, false);
//
//        return new MatchHotelAmenitiesAdapter.MyViewHolder(itemView);

        MatchHotelAmenitiesItemBinding matchHotelAmenitiesItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.match_hotel_amenities_item,parent,false);
        return new MyViewHolder(matchHotelAmenitiesItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(MatchHotelAmenitiesAdapter.MyViewHolder holder, int position) {
        TravelModel amenitiesdetail= matchHotelAmenitiesList.get(position);
//        Utils.setImageInImageView(amenitiesdetail.getCityHotelAmenitiesImage(),holder.amenities_image,mContext);

        Utils.setImageInImageView(amenitiesdetail.getCityHotelAmenitiesImage(),holder.matchHotelAmenitiesItemBinding.amenitiesImage,mContext);
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
        return matchHotelAmenitiesList.size();
    }


}




