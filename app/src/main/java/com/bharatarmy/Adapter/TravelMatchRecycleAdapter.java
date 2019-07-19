package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelPopularCityDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TravelMatchRecycleAdapter extends RecyclerView.Adapter<TravelMatchRecycleAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> popularcityList;
    TravelMatchDetailAdapter travelMatchDetailAdapter;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<String>> listDataChild = new HashMap<>();

    public TravelMatchRecycleAdapter(Context mContext, List<TravelModel> popularcityList) {
        this.mContext = mContext;
        this.popularcityList = popularcityList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ExpandableListView lvExpmatch;
        public MyViewHolder(View view) {
            super(view);
            lvExpmatch=(ExpandableListView)view.findViewById(R.id.lvExpmatch);
        }
    }


    @Override
    public TravelMatchRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_match_recycle_item, parent, false);


        return new TravelMatchRecycleAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchRecycleAdapter.MyViewHolder holder, int position) {
        final TravelModel citydetail = popularcityList.get(position);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (int j = 0; j < 2; j++) {
            listDataHeader.add(String.valueOf(j));

            ArrayList<String> rows = new ArrayList<String>();
            rows.add(String.valueOf(j));
            listDataChild.put(listDataHeader.get(j), rows);
        }

        travelMatchDetailAdapter= new TravelMatchDetailAdapter(mContext,listDataHeader,listDataChild);
        holder.lvExpmatch.setAdapter(travelMatchDetailAdapter);
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
        return popularcityList.size();
    }
}

