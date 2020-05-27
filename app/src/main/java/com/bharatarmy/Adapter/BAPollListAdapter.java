package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.QuizDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.BaPollListItemBinding;

import java.util.List;

public class BAPollListAdapter extends RecyclerView.Adapter<BAPollListAdapter.ItemViewHolder> {

    Context mContext;
    List<QuizDetailModel> recommendedquizList;


    public BAPollListAdapter(Context mContext, List<QuizDetailModel> recommendedquizList) {
        this.mContext = mContext;
        this.recommendedquizList = recommendedquizList;
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

    }

    @Override
    public int getItemCount() {
        return recommendedquizList.size();
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



