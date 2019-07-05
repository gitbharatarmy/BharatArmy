package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelCityHotelDetailsActivity;
import com.bharatarmy.Activity.TravelCityRestaurantDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class TravelCityAllRestaurantAdapter extends RecyclerView.Adapter<TravelCityAllRestaurantAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> cityALlRestaurantList;

    public TravelCityAllRestaurantAdapter(Context mContext, ArrayList<TravelModel> cityALlRestaurantList) {
        this.mContext = mContext;
        this.cityALlRestaurantList = cityALlRestaurantList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurant_img,veg_img,nonveg_img;
        TextView restaurantname_txt, restaurant_location_txt;
        com.whinc.widget.ratingbar.RatingBar ratingBar;
        CardView restaurant_cardclick;


        public MyViewHolder(View view) {
            super(view);
            restaurant_img = (ImageView) view.findViewById(R.id.restaurant_img);
            restaurantname_txt = (TextView) view.findViewById(R.id.restaurantname_txt);
            restaurant_location_txt = (TextView) view.findViewById(R.id.restaurant_location_txt);
            ratingBar = (com.whinc.widget.ratingbar.RatingBar) view.findViewById(R.id.ratingBar);
            restaurant_cardclick = (CardView) view.findViewById(R.id.restaurant_cardclick);
            nonveg_img=(ImageView)view.findViewById(R.id.nonveg_img);
            veg_img=(ImageView)view.findViewById(R.id.veg_img);

        }
    }


    @Override
    public TravelCityAllRestaurantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_cityallrestaurant_item, parent, false);


        return new TravelCityAllRestaurantAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelCityAllRestaurantAdapter.MyViewHolder holder, int position) {
        final TravelModel cityallrestaurantdetail = cityALlRestaurantList.get(position);

        Utils.setImageInImageView(cityallrestaurantdetail.getCityAllHotelImage(), holder.restaurant_img, mContext);

        holder.restaurantname_txt.setText(cityallrestaurantdetail.getCityAllHotelName());
        holder.restaurant_location_txt.setText(cityallrestaurantdetail.getCityAllHotelLocation());
        holder.ratingBar.setCount(cityallrestaurantdetail.getCityAllHotelRating());

        holder.restaurant_cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityHotelDetail=new Intent(mContext, TravelCityRestaurantDetailActivity.class);
                cityHotelDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(cityHotelDetail);
            }
        });

       if (cityallrestaurantdetail.getCityAllHotelPrice().equalsIgnoreCase("1")){
           holder.veg_img.setVisibility(View.VISIBLE);
           holder.nonveg_img.setVisibility(View.GONE);
       }else if(cityallrestaurantdetail.getCityAllHotelPrice().equalsIgnoreCase("0")){
           holder.veg_img.setVisibility(View.GONE);
           holder.nonveg_img.setVisibility(View.VISIBLE);
       }else if(cityallrestaurantdetail.getCityAllHotelPrice().equalsIgnoreCase("10")){
           holder.veg_img.setVisibility(View.VISIBLE);
           holder.nonveg_img.setVisibility(View.VISIBLE);
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
        return cityALlRestaurantList.size();
    }


}



