package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;
import com.bharatarmy.databinding.MatchDetailtitleItemBinding;
import com.bharatarmy.databinding.OtherMatchShowHeaderItemBinding;
import com.bharatarmy.databinding.OtherMatchShowItemListBinding;
import com.bharatarmy.databinding.TravelMatchGroupdetailItemListBinding;

import java.util.ArrayList;

public class OtherMatchShowAdapter  extends RecyclerView.Adapter<OtherMatchShowAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> othermatchshowList;
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    public OtherMatchShowAdapter(Context mContext, ArrayList<String> othermatchshowList) {
        this.mContext = mContext;
        this.othermatchshowList=othermatchshowList;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
       OtherMatchShowItemListBinding otherMatchShowItemListBinding;

        public MyViewHolder(OtherMatchShowItemListBinding otherMatchShowItemListBinding) {
            super(otherMatchShowItemListBinding.getRoot());

            this.otherMatchShowItemListBinding = otherMatchShowItemListBinding;

        }
    }
//    static class HeaderViewHolder extends RecyclerView.ViewHolder {
//      OtherMatchShowHeaderItemBinding otherMatchShowHeaderItemBinding;
//
//        HeaderViewHolder(OtherMatchShowHeaderItemBinding otherMatchShowHeaderItemBinding) {
//            super(otherMatchShowHeaderItemBinding.getRoot());
//
//            this.otherMatchShowHeaderItemBinding=otherMatchShowHeaderItemBinding;
//        }
//    }


    @Override
    public OtherMatchShowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        switch (viewType) {
//            case HEADER:
//                OtherMatchShowHeaderItemBinding otherMatchShowHeaderItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
//                        R.layout.other_match_show_header_item,parent,false);
//                return new OtherMatchShowAdapter.HeaderViewHolder(otherMatchShowHeaderItemBinding);
//
//            default:
                OtherMatchShowItemListBinding otherMatchShowItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.other_match_show_item_list, parent, false);
                return new OtherMatchShowAdapter.MyViewHolder(otherMatchShowItemListBinding);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(OtherMatchShowAdapter.MyViewHolder holder, int position) {
           if(position == 0){
               holder.otherMatchShowItemListBinding.headerLinear.setVisibility(View.VISIBLE);
           }else{
               holder.otherMatchShowItemListBinding.headerLinear.setVisibility(View.GONE);
           }
    }


    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position ;//== 0 ? HEADER : ITEM
    }

    @Override
    public int getItemCount() {
        return othermatchshowList.size()+1;
    }


}









