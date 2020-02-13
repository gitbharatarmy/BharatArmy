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
import com.bharatarmy.databinding.FeedbackAnsListItemBinding;
import com.bharatarmy.databinding.FeedbackViewListItemBinding;

import java.util.ArrayList;

public class FeedbackViewAdapter extends RecyclerView.Adapter<FeedbackViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> feedbackviewanslist;

    public FeedbackViewAdapter(Context mContext, ArrayList<TravelModel> feedbackviewanslist) {
        this.mContext = mContext;
        this.feedbackviewanslist = feedbackviewanslist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackViewListItemBinding feedbackViewListItemBinding;

        public MyViewHolder(FeedbackViewListItemBinding feedbackViewListItemBinding) {
            super(feedbackViewListItemBinding.getRoot());

            this.feedbackViewListItemBinding = feedbackViewListItemBinding;

        }
    }


    @Override
    public FeedbackViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackViewListItemBinding feedbackViewListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_view_list_item, parent, false);
        return new FeedbackViewAdapter.MyViewHolder(feedbackViewListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackViewAdapter.MyViewHolder holder, int position) {
        TravelModel ansviewdetail = feedbackviewanslist.get(position);


        holder.feedbackViewListItemBinding.questionTxt.setText(ansviewdetail.getPopularcity_image());
        if (!ansviewdetail.getPopularcity_name().equalsIgnoreCase("")) {
            holder.feedbackViewListItemBinding.ansTxt.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.ansTxt.setText(ansviewdetail.getPopularcity_name());
        } else {
            holder.feedbackViewListItemBinding.ansTxt.setVisibility(View.GONE);
        }


        if (ansviewdetail.getPopularcity_image_count().equalsIgnoreCase("1")) {
            holder.feedbackViewListItemBinding.ansTxt.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.ansImage.setVisibility(View.VISIBLE);
        } else {
            holder.feedbackViewListItemBinding.ansImage.setVisibility(View.GONE);
        }
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
        return feedbackviewanslist.size();
    }


}


