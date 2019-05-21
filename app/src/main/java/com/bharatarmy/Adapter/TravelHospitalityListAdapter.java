package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelHospitalityListAdapter extends RecyclerView.Adapter<TravelHospitalityListAdapter.MyViewHolder> {
    Context mContext;
    List<TravelDetailModel> hospitalityArrayList;

    public TravelHospitalityListAdapter(Context mContext, List<TravelDetailModel> hospitalityArray) {
        this.mContext=mContext;
        this.hospitalityArrayList=hospitalityArray;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView hospitality_img;
TextView hopitality_txt;

        public MyViewHolder(View view) {
            super(view);

            hospitality_img=(ImageView)view.findViewById(R.id.hospitality_img);
            hopitality_txt=(TextView)view.findViewById(R.id.hopitality_txt);

        }
    }


    @Override
    public TravelHospitalityListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospitality_item, parent, false);

        return new TravelHospitalityListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelHospitalityListAdapter.MyViewHolder holder, int position) {
        final TravelDetailModel hospitalityArrayDetail = hospitalityArrayList.get(position);

        Picasso.with(mContext)
                .load(hospitalityArrayDetail.getHospitality_img())
                .placeholder(R.drawable.progress_animation)
                .into(holder.hospitality_img);

        holder.hopitality_txt.setText(hospitalityArrayDetail.getHospitality_name());
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
        return hospitalityArrayList.size();
    }
}

