package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CityHotelAmenitiesListAdapter extends RecyclerView.Adapter<CityHotelAmenitiesListAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> cityHotelAmenitiesList;


    public CityHotelAmenitiesListAdapter(Context mContext, ArrayList<TravelModel> cityHotelAmenitiesList) {
        this.mContext=mContext;
        this.cityHotelAmenitiesList=cityHotelAmenitiesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
ImageView amenities_image;
TextView amenities_nameTxt;



        public MyViewHolder(View view) {
            super(view);
            amenities_image=(ImageView)view.findViewById(R.id.amenities_image);
            amenities_nameTxt=(TextView)view.findViewById(R.id.amenities_nameTxt);
        }
    }


    @Override
    public CityHotelAmenitiesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_amenities_item, parent, false);

        return new CityHotelAmenitiesListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CityHotelAmenitiesListAdapter.MyViewHolder holder, int position) {
TravelModel amenitiesDetail=cityHotelAmenitiesList.get(position);

        Utils.setImageInImageView(amenitiesDetail.getCityHotelAmenitiesImage(),holder.amenities_image,mContext);
holder.amenities_nameTxt.setText(amenitiesDetail.getCityHotelAmenitiesName());
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


