package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.RelatedTicketCategoryMatchesActivity;
import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.R;
import com.bharatarmy.databinding.OtherMatchShowItemListBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OtherMatchShowAdapter extends RecyclerView.Adapter<OtherMatchShowAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> othermatchshowList;


    public OtherMatchShowAdapter(Context mContext, ArrayList<String> othermatchshowList) {
        this.mContext = mContext;
        this.othermatchshowList = othermatchshowList;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        OtherMatchShowItemListBinding otherMatchShowItemListBinding;

        public MyViewHolder(OtherMatchShowItemListBinding otherMatchShowItemListBinding) {
            super(otherMatchShowItemListBinding.getRoot());
            this.otherMatchShowItemListBinding = otherMatchShowItemListBinding;
        }
    }

    @Override
    public OtherMatchShowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OtherMatchShowItemListBinding otherMatchShowItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.other_match_show_item_list, parent, false);
        return new OtherMatchShowAdapter.MyViewHolder(otherMatchShowItemListBinding);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(OtherMatchShowAdapter.MyViewHolder holder, int position) {

        holder.otherMatchShowItemListBinding.layout1.ticketLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ticketandhospitalityIntent = new Intent(mContext, RelatedTicketCategoryMatchesActivity.class);
                ticketandhospitalityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(ticketandhospitalityIntent);

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
        return position;//== 0 ? HEADER : ITEM
    }

    @Override
    public int getItemCount() {
        return othermatchshowList.size();
    }

}









