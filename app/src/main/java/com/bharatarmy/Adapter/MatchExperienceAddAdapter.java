package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;

import java.util.ArrayList;
import java.util.List;

public class MatchExperienceAddAdapter extends RecyclerView.Adapter<MatchExperienceAddAdapter.MyViewHolder> {
    Context mContext;
    List<String> experienceList;



    public MatchExperienceAddAdapter(Context mContext, ArrayList<String> experienceList) {
        this.mContext=mContext;
        this.experienceList=experienceList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

        }
    }


    @Override
    public MatchExperienceAddAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_hospitalitytrip_item, parent, false);

        return new MatchExperienceAddAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MatchExperienceAddAdapter.MyViewHolder holder, int position) {


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
        return experienceList.size();
    }


}
