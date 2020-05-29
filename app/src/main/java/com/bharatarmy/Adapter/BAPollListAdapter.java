package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.BAPollListActivity;
import com.bharatarmy.Activity.BaPollActivity;
import com.bharatarmy.Models.QuizDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.BaPollListItemBinding;

import java.util.List;

public class BAPollListAdapter extends RecyclerView.Adapter<BAPollListAdapter.ItemViewHolder> {

    Context mContext;
    List<QuizDetailModel> pollModelList;

    public BAPollListAdapter(Context mContext, List<QuizDetailModel> pollModelList) {
        this.mContext = mContext;
        this.pollModelList = pollModelList;
    }


    @NonNull
    @Override
    public BAPollListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaPollListItemBinding baPollListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_poll_list_item, parent, false);
        return new BAPollListAdapter.ItemViewHolder(baPollListItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BAPollListAdapter.ItemViewHolder viewHolder, int position) {
        QuizDetailModel polllistdetail = pollModelList.get(position);
        if (pollModelList.size() - 1 == position) {
            viewHolder.baPollListItemBinding.bottomLineView.setVisibility(View.GONE);
        } else {
            viewHolder.baPollListItemBinding.bottomLineView.setVisibility(View.VISIBLE);
        }
        viewHolder.baPollListItemBinding.baPollListTxt.setText(polllistdetail.getBAPollTitle());
        viewHolder.baPollListItemBinding.baPollVotesTxt.setText(String.valueOf(polllistdetail.getNoofVotes()));
        viewHolder.baPollListItemBinding.pollDurationtxt.setText(polllistdetail.getStrDuration());
        viewHolder.baPollListItemBinding.baPolllistLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bapollIntent = new Intent(mContext, BaPollActivity.class);
                bapollIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(bapollIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pollModelList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        BaPollListItemBinding baPollListItemBinding;

        public ItemViewHolder(@NonNull BaPollListItemBinding baPollListItemBinding) {
            super(baPollListItemBinding.getRoot());
            this.baPollListItemBinding = baPollListItemBinding;

        }
    }


}



