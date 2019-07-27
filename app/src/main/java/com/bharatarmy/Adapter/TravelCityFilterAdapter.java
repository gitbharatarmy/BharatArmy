package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.TravelFilterCityItemBinding;

import java.util.ArrayList;

public class TravelCityFilterAdapter extends RecyclerView.Adapter<TravelCityFilterAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> travelcityList;

    private ArrayList<String> dataCheck = new ArrayList<String>();


    public TravelCityFilterAdapter(Context mContext, ArrayList<TravelModel> travelcityList) {
        this.mContext = mContext;
        this.travelcityList = travelcityList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelFilterCityItemBinding travelFilterCityItemBinding;

        public MyViewHolder(TravelFilterCityItemBinding travelFilterCityItemBinding) {
            super(travelFilterCityItemBinding.getRoot());

            this.travelFilterCityItemBinding = travelFilterCityItemBinding;

        }
    }


    @Override
    public TravelCityFilterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelFilterCityItemBinding travelFilterCityItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.travel_filter_city_item, parent, false);
        return new TravelCityFilterAdapter.MyViewHolder(travelFilterCityItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelCityFilterAdapter.MyViewHolder holder, int position) {

        final TravelModel teamdetail = travelcityList.get(position);

        holder.travelFilterCityItemBinding.cityTxt.setText(teamdetail.getMatchteamVenues());
        holder.travelFilterCityItemBinding.cityLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.travelFilterCityItemBinding.selectedChk.isChecked()) {
                    holder.travelFilterCityItemBinding.selectedChk.setChecked(false);
                } else {
                    holder.travelFilterCityItemBinding.selectedChk.setChecked(true);
                }
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
        return travelcityList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}


