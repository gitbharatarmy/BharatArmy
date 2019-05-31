package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;

import java.util.List;

public class HotelFacilitiesAdapter extends RecyclerView.Adapter<HotelFacilitiesAdapter.MyViewHolder> {
    Context mcontext;
    List<String> hotelfacilitiesList;

    public HotelFacilitiesAdapter(Context mContext, List<String> hotelfacilitiesList) {
        this.mcontext = mContext;
        this.hotelfacilitiesList = hotelfacilitiesList;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView historyDdetailimg;


        public MyViewHolder(View view) {
            super(view);
            historyDdetailimg = (ImageView) view.findViewById(R.id.history_detail_img);


        }
    }


    @Override
    public HotelFacilitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_facilities_list, parent, false);

        return new HotelFacilitiesAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(HotelFacilitiesAdapter.MyViewHolder holder, int position) {


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
        return hotelfacilitiesList.size();
    }


}



