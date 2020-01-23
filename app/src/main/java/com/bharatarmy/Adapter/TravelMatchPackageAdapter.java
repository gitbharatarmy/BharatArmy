package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelPackageActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.TravelMatchPackageListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelMatchPackageAdapter extends RecyclerView.Adapter<TravelMatchPackageAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> popularPackageList;


    public TravelMatchPackageAdapter(Context mContext, List<TravelModel> popularPackageList) {
        this.mContext=mContext;
        this.popularPackageList=popularPackageList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelMatchPackageListItemBinding travelMatchPackageListItemBinding;
        public MyViewHolder(TravelMatchPackageListItemBinding travelMatchPackageListItemBinding) {
            super(travelMatchPackageListItemBinding.getRoot());
            this.travelMatchPackageListItemBinding=travelMatchPackageListItemBinding;
        }
    }


    @Override
    public TravelMatchPackageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelMatchPackageListItemBinding travelMatchPackageListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_package_list_item, parent, false);
        return new TravelMatchPackageAdapter.MyViewHolder(travelMatchPackageListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchPackageAdapter.MyViewHolder holder, int position) {
        final TravelModel packagedetail = popularPackageList.get(position);

        Picasso.with(mContext)
                .load(packagedetail.getTourImage())
                .into(holder.travelMatchPackageListItemBinding.travelPopularPackageBannerImg);

        holder.travelMatchPackageListItemBinding.packageplacenameTxt.setText(packagedetail.getTourCountryName());
        holder.travelMatchPackageListItemBinding.showPackageTourDescriptionTxt.setText(packagedetail.getTourDescription());

//        if (packagedetail.getbAImage().equalsIgnoreCase("true")){
//            holder.recommended_Linear.setVisibility(View.VISIBLE);
//        }else{
//            holder.recommended_Linear.setVisibility(View.GONE);
//        }

        holder.travelMatchPackageListItemBinding.packageCardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pacakgeIntent=new Intent(mContext, TravelPackageActivity.class);
                pacakgeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(pacakgeIntent);
            }
        });

        holder.travelMatchPackageListItemBinding.bookLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookIntent=new Intent(mContext, TravelBookActivity.class);
                bookIntent.putExtra("pacakgeName","Australian Double Dhamaka: Honeymoon and adventure at one shot");
                bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(bookIntent);
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
        return position;
    }

    @Override
    public int getItemCount() {
        return popularPackageList.size();
    }
}


