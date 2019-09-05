package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.HotelAmenitiesListItemBinding;
import com.bharatarmy.databinding.HotelDetailHeaderItemBinding;

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
        switch (viewType) {
            case HEADER:
                HotelDetailHeaderItemBinding hotelDetailHeaderItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.hotel_detail_header_item,parent,false);
                return new CityHotelAmenitiesAdapter.HeaderViewHolder(hotelDetailHeaderItemBinding);

            default:
               HotelAmenitiesListItemBinding hotelAmenitiesListItemBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                       R.layout.hotel_amenities_list_item,parent,false);
               return new CityHotelAmenitiesAdapter.ItemViewHolder(hotelAmenitiesListItemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
//        TravelModel cityplaceDetail=cityPlaceList.get(position-1);

        if (holder.getItemViewType() == ITEM) {
            cityHotelAmenitiesListAdapter = new CityHotelAmenitiesListAdapter(mContext, cityHotelAmenitiesList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
            ((ItemViewHolder) holder).hotelAmenitiesListItemBinding.amenitiesListrcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
            ((ItemViewHolder) holder).hotelAmenitiesListItemBinding.amenitiesListrcv.setAdapter(cityHotelAmenitiesListAdapter);

        } else if (holder.getItemViewType() == HEADER) {

        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HotelDetailHeaderItemBinding hotelDetailHeaderItemBinding;

        HeaderViewHolder(HotelDetailHeaderItemBinding hotelDetailHeaderItemBinding) {
            super(hotelDetailHeaderItemBinding.getRoot());

            this.hotelDetailHeaderItemBinding=hotelDetailHeaderItemBinding;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

HotelAmenitiesListItemBinding hotelAmenitiesListItemBinding;

        ItemViewHolder(HotelAmenitiesListItemBinding hotelAmenitiesListItemBinding) {
            super(hotelAmenitiesListItemBinding.getRoot());

            this.hotelAmenitiesListItemBinding=hotelAmenitiesListItemBinding;
        }

    }

}
