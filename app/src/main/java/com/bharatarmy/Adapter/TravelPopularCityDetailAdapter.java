package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelCityAllHotelsActivity;
import com.bharatarmy.Activity.TravelCityAllRestaurantsActivity;
import com.bharatarmy.Activity.TravelpopularCityHotelMapViewActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;

import java.util.ArrayList;

public class TravelPopularCityDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    Context mContext;

    ArrayList<TravelModel> cityPlaceList;

    public TravelPopularCityDetailAdapter(Context mContext, ArrayList<TravelModel> cityPlaceList) {
        this.mContext = mContext;
        this.cityPlaceList = cityPlaceList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return ITEM;
        }

//        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return cityPlaceList.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.travel_popular_city_headerdetail_item, parent, false);
                return new TravelPopularCityDetailAdapter.HeaderViewHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.travel_popularcity_placedetail_item, parent, false);
                return new TravelPopularCityDetailAdapter.ItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
//        TravelModel cityplaceDetail=cityPlaceList.get(position-1);

        if (holder.getItemViewType() == ITEM) {
            Utils.setImageInImageView(AppConfiguration.IMAGE_URL+"mumbai.jpg",((ItemViewHolder) holder).travel_popular_package_banner_img,mContext);

        } else if (holder.getItemViewType() == HEADER) {
            ((HeaderViewHolder) holder).hotel_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cityAllHotelIntent=new Intent(mContext, TravelCityAllHotelsActivity.class);
                    cityAllHotelIntent.putExtra("cityName",cityPlaceList.get(position).getPopularcity_name());
                    cityAllHotelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(cityAllHotelIntent);
                }
            });
            ((HeaderViewHolder) holder).restaurant_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cityAllHotelIntent=new Intent(mContext, TravelCityAllRestaurantsActivity.class);
                    cityAllHotelIntent.putExtra("cityName",cityPlaceList.get(position).getPopularcity_name());
                    cityAllHotelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(cityAllHotelIntent);
                }
            });
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        LinearLayout hotel_linear,restaurant_linear;

        HeaderViewHolder(View itemView) {
            super(itemView);
            hotel_linear = (LinearLayout) itemView.findViewById(R.id.hotel_linear);
            restaurant_linear=(LinearLayout)itemView.findViewById(R.id.restaurant_linear);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

ImageView travel_popular_package_banner_img;
        ItemViewHolder(View itemView) {
            super(itemView);
            travel_popular_package_banner_img=(ImageView)itemView.findViewById(R.id.travel_popular_package_banner_img);
        }

    }

}
