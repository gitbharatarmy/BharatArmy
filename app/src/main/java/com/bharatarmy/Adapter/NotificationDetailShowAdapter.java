package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.NotificationDetailShowListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationDetailShowAdapter extends RecyclerView.Adapter<NotificationDetailShowAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<String> notificationDetailList;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public NotificationDetailShowAdapter(Context mContext, ArrayList<String> notificationDetailList) {
        this.mcontext = mContext;
        this.notificationDetailList = notificationDetailList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        NotificationDetailShowListItemBinding notificationDetailShowListItemBinding;

        public MyViewHolder(NotificationDetailShowListItemBinding notificationDetailShowListItemBinding) {
            super(notificationDetailShowListItemBinding.getRoot());

            this.notificationDetailShowListItemBinding = notificationDetailShowListItemBinding;

        }
    }


    @Override
    public NotificationDetailShowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NotificationDetailShowListItemBinding notificationDetailShowListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.notification_detail_show_list_item, parent, false);
        return new NotificationDetailShowAdapter.MyViewHolder(notificationDetailShowListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(NotificationDetailShowAdapter.MyViewHolder holder, int position) {

        if (position == 2 || position == 4) {
            holder.notificationDetailShowListItemBinding.notificationTagTxt.setVisibility(View.GONE);
        }else{
            holder.notificationDetailShowListItemBinding.notificationTagTxt.setVisibility(View.VISIBLE);
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
        return notificationDetailList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}


