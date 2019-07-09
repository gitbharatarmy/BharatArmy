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

import com.bharatarmy.Activity.TravelCitySightseenDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class TravelCityAllSightseenAdapter extends RecyclerView.Adapter<TravelCityAllSightseenAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> cityALlSightseenList;

    public TravelCityAllSightseenAdapter(Context mContext, ArrayList<TravelModel> cityALlSightseenList) {
        this.mContext=mContext;
        this.cityALlSightseenList=cityALlSightseenList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView sightseen_img,adult_img,child_img;
        TextView sightseenname_txt, sightseen_location_txt;
        com.whinc.widget.ratingbar.RatingBar ratingBar;
        CardView sightseen_cardclick;


        public MyViewHolder(View view) {
            super(view);
            sightseen_img = (ImageView) view.findViewById(R.id.sightseen_img);
            sightseenname_txt = (TextView) view.findViewById(R.id.sightseenname_txt);
            sightseen_location_txt = (TextView) view.findViewById(R.id.sightseen_location_txt);
            ratingBar = (com.whinc.widget.ratingbar.RatingBar) view.findViewById(R.id.ratingBar);
            sightseen_cardclick = (CardView) view.findViewById(R.id.sightseen_cardclick);
            child_img=(ImageView)view.findViewById(R.id.child_img);
            adult_img=(ImageView)view.findViewById(R.id.adult_img);

        }
    }


    @Override
    public TravelCityAllSightseenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_cityallsightseen_item, parent, false);


        return new TravelCityAllSightseenAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelCityAllSightseenAdapter.MyViewHolder holder, int position) {
        final TravelModel cityallrestaurantdetail = cityALlSightseenList.get(position);

        Utils.setImageInImageView(cityallrestaurantdetail.getCityAllHotelImage(), holder.sightseen_img, mContext);

        holder.sightseenname_txt.setText(cityallrestaurantdetail.getCityAllHotelName());
        holder.sightseen_location_txt.setText(cityallrestaurantdetail.getCityAllHotelLocation());
        holder.ratingBar.setCount(cityallrestaurantdetail.getCityAllHotelRating());

        holder.sightseen_cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent citysightseenDetail=new Intent(mContext, TravelCitySightseenDetailActivity.class);
                citysightseenDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(citysightseenDetail);
            }
        });

        if (cityallrestaurantdetail.getCityAllHotelPrice().equalsIgnoreCase("1")){
            holder.adult_img.setVisibility(View.VISIBLE);
            holder.child_img.setVisibility(View.GONE);
        }else if(cityallrestaurantdetail.getCityAllHotelPrice().equalsIgnoreCase("0")){
            holder.adult_img.setVisibility(View.GONE);
            holder.child_img.setVisibility(View.VISIBLE);
        }else if(cityallrestaurantdetail.getCityAllHotelPrice().equalsIgnoreCase("10")){
            holder.adult_img.setVisibility(View.VISIBLE);
            holder.child_img.setVisibility(View.VISIBLE);
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
        return cityALlSightseenList.size();
    }


}



