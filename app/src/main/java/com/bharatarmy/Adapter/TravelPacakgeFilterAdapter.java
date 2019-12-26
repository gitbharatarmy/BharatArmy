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
import com.bharatarmy.databinding.TravelFilterPacakgeItemBinding;

import java.util.ArrayList;

public class TravelPacakgeFilterAdapter extends RecyclerView.Adapter<TravelPacakgeFilterAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> travelcityList;

    private ArrayList<String> dataCheck = new ArrayList<String>();


    public TravelPacakgeFilterAdapter(Context mContext, ArrayList<TravelModel> travelcityList) {
        this.mContext = mContext;
        this.travelcityList = travelcityList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelFilterPacakgeItemBinding travelPacakgeFilterBinding;

        public MyViewHolder(TravelFilterPacakgeItemBinding travelPacakgeFilterBinding) {
            super(travelPacakgeFilterBinding.getRoot());

            this.travelPacakgeFilterBinding = travelPacakgeFilterBinding;

        }
    }


    @Override
    public TravelPacakgeFilterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelFilterPacakgeItemBinding travelPacakgeFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_filter_pacakge_item, parent, false);
        return new TravelPacakgeFilterAdapter.MyViewHolder(travelPacakgeFilterBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelPacakgeFilterAdapter.MyViewHolder holder, int position) {

        final TravelModel teamdetail = travelcityList.get(position);

        holder.travelPacakgeFilterBinding.pacakageTxt.setText(teamdetail.getMatchteamVenues());
        holder.travelPacakgeFilterBinding.pacakageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.travelPacakgeFilterBinding.selectedChk.isChecked()) {
                    holder.travelPacakgeFilterBinding.selectedChk.setChecked(false);
                } else {
                    holder.travelPacakgeFilterBinding.selectedChk.setChecked(true);
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



