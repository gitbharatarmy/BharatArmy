package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

public class CityHotelAmenitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    Context mContext;
    ArrayList<String> valueArray;
    CityHotelAmenitiesListAdapter cityHotelAmenitiesListAdapter;

    ArrayList<TravelModel> cityHotelAmenitiesList;

    public CityHotelAmenitiesAdapter(Context mContext, ArrayList<String> valueArray, ArrayList<TravelModel> cityHotelAmenitiesList) {
        this.mContext = mContext;
        this.valueArray=valueArray;
        this.cityHotelAmenitiesList = cityHotelAmenitiesList;
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
        return valueArray.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.hotel_detail_header_item, parent, false);
                return new CityHotelAmenitiesAdapter.HeaderViewHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.hotel_amenities_list_item, parent, false);
                return new CityHotelAmenitiesAdapter.ItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
//        TravelModel cityplaceDetail=cityPlaceList.get(position-1);

        if (holder.getItemViewType() == ITEM) {
            cityHotelAmenitiesListAdapter = new CityHotelAmenitiesListAdapter(mContext, cityHotelAmenitiesList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
            ((ItemViewHolder) holder).amenitiesListrcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
            ((ItemViewHolder) holder).amenitiesListrcv.setAdapter(cityHotelAmenitiesListAdapter);

        } else if (holder.getItemViewType() == HEADER) {

        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView travel_cityhoteldesc_txt;

        HeaderViewHolder(View itemView) {
            super(itemView);
            travel_cityhoteldesc_txt = (TextView) itemView.findViewById(R.id.travel_cityhoteldesc_txt);

        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

       ShimmerRecyclerView amenitiesListrcv;

        ItemViewHolder(View itemView) {
            super(itemView);
            amenitiesListrcv=(ShimmerRecyclerView) itemView.findViewById(R.id.amenitiesListrcv);
        }

    }

}
