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
import com.bharatarmy.databinding.TravelPartnerItemBinding;

import java.util.List;

public class TravelPartnersAdapter extends RecyclerView.Adapter<TravelPartnersAdapter.MyViewHolder> {
    Context mcontext;
    List<TravelModel> partnerlist;


    public TravelPartnersAdapter(Context mContext, List<TravelModel> partnerlist) {
        this.mcontext = mContext;
        this.partnerlist = partnerlist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelPartnerItemBinding travelPartnerItemBinding;

        public MyViewHolder(TravelPartnerItemBinding travelPartnerItemBinding) {
            super(travelPartnerItemBinding.getRoot());

            this.travelPartnerItemBinding = travelPartnerItemBinding;

        }
    }


    @Override
    public TravelPartnersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelPartnerItemBinding travelPartnerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_partner_item, parent, false);
        return new TravelPartnersAdapter.MyViewHolder(travelPartnerItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelPartnersAdapter.MyViewHolder holder, int position) {
        TravelModel partnerdetail = partnerlist.get(position);

        Utils.setImageInImageView(partnerdetail.getPopularcity_image(), holder.travelPartnerItemBinding.partnerImage, mcontext);
        holder.travelPartnerItemBinding.partnerHeaderTxt.setText(partnerdetail.getPopularcity_name());
        holder.travelPartnerItemBinding.partnerDescTxt.setText(partnerdetail.getPopularcity_image_count());

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


