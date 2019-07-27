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
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MainPageChildItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MainPageChildAdapter extends RecyclerView.Adapter<MainPageChildAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> mainPageChildArrayList;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public MainPageChildAdapter(Context mContext, ArrayList<TravelModel> mainPageChildArrayList) {
        this.mContext = mContext;
        this.mainPageChildArrayList = mainPageChildArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MainPageChildItemBinding mainPageChildItemBinding;

        public MyViewHolder(MainPageChildItemBinding mainPageChildItemBinding) {
            super(mainPageChildItemBinding.getRoot());

            this.mainPageChildItemBinding = mainPageChildItemBinding;

        }
    }


    @Override
    public MainPageChildAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainPageChildItemBinding mainPageChildItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.main_page_child_item, parent, false);
        return new MainPageChildAdapter.MyViewHolder(mainPageChildItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MainPageChildAdapter.MyViewHolder holder, int position) {
        TravelModel detail = mainPageChildArrayList.get(position);

        holder.mainPageChildItemBinding.itemHeadingTxt.setText(detail.getMain_titleName());
        holder.mainPageChildItemBinding.itemDescTxt.setText(detail.getMain_desc());
        holder.mainPageChildItemBinding.bookTxt.setText(detail.getButton_name());

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
        return mainPageChildArrayList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}

