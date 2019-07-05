package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelCityHotelDetailsActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.like.LikeButton;
import com.whinc.widget.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class TravelCityAllHotelAdapter extends RecyclerView.Adapter<TravelCityAllHotelAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> cityALlHotelList;

    public TravelCityAllHotelAdapter(Context mContext, ArrayList<TravelModel> cityALlHotelList) {
        this.mContext = mContext;
        this.cityALlHotelList = cityALlHotelList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView hotel_img;
        TextView hotelname_txt, hotel_location_txt, hotel_price_txt;
        com.whinc.widget.ratingbar.RatingBar ratingBar;
        CardView hotel_cardclick;

        public MyViewHolder(View view) {
            super(view);
            hotel_img = (ImageView) view.findViewById(R.id.hotel_img);
            hotelname_txt = (TextView) view.findViewById(R.id.hotelname_txt);
            hotel_location_txt = (TextView) view.findViewById(R.id.hotel_location_txt);
            hotel_price_txt = (TextView) view.findViewById(R.id.hotel_price_txt);
            ratingBar = (com.whinc.widget.ratingbar.RatingBar) view.findViewById(R.id.ratingBar);
            hotel_cardclick = (CardView) view.findViewById(R.id.hotel_cardclick);
        }
    }


    @Override
    public TravelCityAllHotelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_cityallhotel_item, parent, false);


        return new TravelCityAllHotelAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelCityAllHotelAdapter.MyViewHolder holder, int position) {
        final TravelModel cityallhoteldetail = cityALlHotelList.get(position);

        Utils.setImageInImageView(cityallhoteldetail.getCityAllHotelImage(), holder.hotel_img, mContext);

        holder.hotelname_txt.setText(cityallhoteldetail.getCityAllHotelName());
        holder.hotel_location_txt.setText(cityallhoteldetail.getCityAllHotelLocation());
        holder.hotel_price_txt.setText("₹ " + cityallhoteldetail.getCityAllHotelPrice());
        holder.ratingBar.setCount(cityallhoteldetail.getCityAllHotelRating());

        holder.hotel_cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityHotelDetail=new Intent(mContext, TravelCityHotelDetailsActivity.class);
                cityHotelDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(cityHotelDetail);
            }
        });

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
        return cityALlHotelList.size();
    }


}



