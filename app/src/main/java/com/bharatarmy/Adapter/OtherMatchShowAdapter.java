package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

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
        if (position == 0) {
            holder.otherMatchShowItemListBinding.headerLinear.setVisibility(View.VISIBLE);
            holder.otherMatchShowItemListBinding.mainGroupLiner.setVisibility(View.GONE);
        } else {
            holder.otherMatchShowItemListBinding.headerLinear.setVisibility(View.GONE);
            holder.otherMatchShowItemListBinding.mainGroupLiner.setVisibility(View.VISIBLE);
        }

        Picasso.with(mContext)
                .load("http://devenv.bharatarmy.com/docs/stadium_map.jpg")
                .placeholder(R.drawable.loader)
                .resize(Resources.getSystem().getDisplayMetrics().widthPixels,holder.otherMatchShowItemListBinding.webView.getHeight())
       .into(holder.otherMatchShowItemListBinding.webView);

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
        return othermatchshowList.size()+1;
    }

}









