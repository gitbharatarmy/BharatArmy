package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;

import java.util.ArrayList;

public class MatchFilterVenuesAdapter extends RecyclerView.Adapter<MatchFilterVenuesAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> matchTeamVenueList;
    int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public MatchFilterVenuesAdapter(Context mContext, ArrayList<TravelModel> matchTeamVenueList) {
        this.mContext=mContext;
        this.matchTeamVenueList=matchTeamVenueList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView match_venues_txt;
        CheckBox selected_chk;
        LinearLayout match_venues_Linear;
        public MyViewHolder(View view) {
            super(view);
            match_venues_txt=(TextView)view.findViewById(R.id.match_venues_txt);
            selected_chk=(CheckBox) view.findViewById(R.id.selected_chk);
            match_venues_Linear=(LinearLayout)view.findViewById(R.id.match_venues_Linear);
        }
    }


    @Override
    public MatchFilterVenuesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_filter_venues_item, parent, false);

        return new MatchFilterVenuesAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MatchFilterVenuesAdapter.MyViewHolder holder, int position) {

        final TravelModel venuesdetail = matchTeamVenueList.get(position);


        holder.match_venues_txt.setText(venuesdetail.getMatchteamVenues());

        holder.match_venues_Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.selected_chk.isChecked()){
                    holder.selected_chk.setChecked(false);
                }else{
                    holder.selected_chk.setChecked(true);
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
        return matchTeamVenueList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}



