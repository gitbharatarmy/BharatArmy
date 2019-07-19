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
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;

import java.util.ArrayList;
import java.util.List;

public class MatchFilterTeamAdapter extends RecyclerView.Adapter<MatchFilterTeamAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> matchTeamFlagList;
int row_index;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public MatchFilterTeamAdapter(Context mContext, ArrayList<TravelModel> matchTeamFlagList) {
        this.mContext=mContext;
        this.matchTeamFlagList=matchTeamFlagList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView match_team_txt;
        ImageView team_flag,selected_img;
       CheckBox selected_chk;
       LinearLayout teamselectedLinear;
        public MyViewHolder(View view) {
            super(view);
            team_flag=(ImageView)view.findViewById(R.id.team_flag);
            match_team_txt=(TextView)view.findViewById(R.id.match_team_txt);
            selected_chk=(CheckBox) view.findViewById(R.id.selected_chk);
            teamselectedLinear=(LinearLayout)view.findViewById(R.id.teamselectedLinear);
        }
    }


    @Override
    public MatchFilterTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_filter_team_item, parent, false);

        return new MatchFilterTeamAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MatchFilterTeamAdapter.MyViewHolder holder, int position) {

        final TravelModel teamdetail = matchTeamFlagList.get(position);

        holder.team_flag.setImageResource(teamdetail.getMatchteamFlag());
        holder.match_team_txt.setText(teamdetail.getMatchteamVenues());


        holder.teamselectedLinear.setOnClickListener(new View.OnClickListener() {
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
        return matchTeamFlagList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}


