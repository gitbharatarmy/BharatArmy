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
import com.bharatarmy.Activity.TravelCityRestaurantDetailActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class TravelcityrestaurantmaplistAdapter extends RecyclerView.Adapter<TravelcityrestaurantmaplistAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> cityRestaurantList;
     image_click image_click;
    private ArrayList<String> dataCheck;

    public TravelcityrestaurantmaplistAdapter(Context mContext, ArrayList<TravelModel> cityRestaurantList, image_click image_click) {
        this.mContext=mContext;
        this.cityRestaurantList=cityRestaurantList;
        this.image_click=image_click;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView city_restaurant_image;
        TextView city_restaurantname_txt, city_restaurantdesc_txt,viewdetail_btn;
        CardView citycard_click;

        public MyViewHolder(View view) {
            super(view);
            city_restaurant_image = (ImageView) view.findViewById(R.id.city_restaurant_image);
            city_restaurantname_txt = (TextView) view.findViewById(R.id.city_restaurantname_txt);
            city_restaurantdesc_txt = (TextView) view.findViewById(R.id.city_restaurantdesc_txt);
            citycard_click = (CardView) view.findViewById(R.id.citycard_click);
            viewdetail_btn=(TextView)view.findViewById(R.id.viewdetail_btn);
        }
    }


    @Override
    public TravelcityrestaurantmaplistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_cityrestaurantmap_item_list, parent, false);


        return new TravelcityrestaurantmaplistAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelcityrestaurantmaplistAdapter.MyViewHolder holder, int position) {
        final TravelModel cityhoteldetail = cityRestaurantList.get(position);

        Utils.setImageInImageView(cityhoteldetail.getPopularCityPlaceImage(),holder.city_restaurant_image,mContext);

        holder.city_restaurantname_txt.setText(cityhoteldetail.getPopularCityPlaceName());
        holder.city_restaurantdesc_txt.setText(cityhoteldetail.getPopularCityPlaceDescription());

        holder.citycard_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataCheck=new ArrayList<>();
                dataCheck.add(String.valueOf(position));
                image_click.image_more_click();
            }
        });

        holder.viewdetail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityHotelDetail=new Intent(mContext, TravelCityRestaurantDetailActivity.class);
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
        return cityRestaurantList.size();
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}


