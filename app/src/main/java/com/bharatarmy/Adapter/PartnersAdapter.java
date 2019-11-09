package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.PartnersItemListBinding;

import java.util.List;


public class PartnersAdapter extends RecyclerView.Adapter<PartnersAdapter.MyViewHolder> {
    Context mcontext;
    List<TravelModel> partnerlist;


    public PartnersAdapter(Context mContext, List<TravelModel> partnerlist) {
        this.mcontext = mContext;
        this.partnerlist = partnerlist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        PartnersItemListBinding partnersItemListBinding;

        public MyViewHolder(PartnersItemListBinding partnersItemListBinding) {
            super(partnersItemListBinding.getRoot());

            this.partnersItemListBinding = partnersItemListBinding;

        }
    }


    @Override
    public PartnersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PartnersItemListBinding partnersItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.partners_item_list, parent, false);
        return new PartnersAdapter.MyViewHolder(partnersItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(PartnersAdapter.MyViewHolder holder, int position) {
        TravelModel partnerdetail = partnerlist.get(position);

        Utils.setImageInImageView(partnerdetail.getPopularcity_image(), holder.partnersItemListBinding.partnerImage, mcontext);
        holder.partnersItemListBinding.partnerHeaderTxt.setText(partnerdetail.getPopularcity_name());
        holder.partnersItemListBinding.partnerDescTxt.setText(partnerdetail.getPopularcity_image_count());

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
        return partnerlist.size();
    }

}


