package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.StoryDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelNewUpdateItemBinding;

import java.util.List;


public class TravelNewandUpdatesAdapter extends RecyclerView.Adapter<TravelNewandUpdatesAdapter.MyViewHolder> {
    Context mcontext;
    List<TravelModel> travelnewsupdatesList;

    public TravelNewandUpdatesAdapter(Context mContext, List<TravelModel> travelnewsupdatesList) {
        this.mcontext = mContext;
        this.travelnewsupdatesList = travelnewsupdatesList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelNewUpdateItemBinding travelNewUpdateItemBinding;

        public MyViewHolder(TravelNewUpdateItemBinding travelNewUpdateItemBinding) {
            super(travelNewUpdateItemBinding.getRoot());

            this.travelNewUpdateItemBinding = travelNewUpdateItemBinding;

        }
    }


    @Override
    public TravelNewandUpdatesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelNewUpdateItemBinding travelNewUpdateItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_new_update_item, parent, false);
        return new TravelNewandUpdatesAdapter.MyViewHolder(travelNewUpdateItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelNewandUpdatesAdapter.MyViewHolder holder, int position) {
       TravelModel nesupdate = travelnewsupdatesList.get(position);


        Utils.setImageInImageView(nesupdate.getPopularcity_image(),holder.travelNewUpdateItemBinding.travelNewsupdateImg,mcontext);

        holder.travelNewUpdateItemBinding.travelNewsupdateTitleTxt.setText(nesupdate.getPopularcity_name());
        holder.travelNewUpdateItemBinding.travelNewsupdateDescriptionTxt.setText(nesupdate.getPopularcity_image_count());

        holder.travelNewUpdateItemBinding.newsclickLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webviewIntent = new Intent(mcontext, StoryDetailActivity.class);
                webviewIntent.putExtra("Story Heading",nesupdate.getPopularcity_name());
                webviewIntent.putExtra("StroyUrl","http://jalsaclub.net/stories/Travel/melbourne-hidden-gems");
                webviewIntent.putExtra("StoryCategorytype", "Travel");
                webviewIntent.putExtra("StoryAuthor","https://www.bharatarmy.com//Docs/16636938-9.jpg");
                webviewIntent.putExtra("StoryHeaderImg", "https://www.bharatarmy.com//Docs/e7911418-b.jpg");
                webviewIntent.putExtra("StoryId",416);
                webviewIntent.putExtra("StoryauthorId", 0);
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(webviewIntent);
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
        return travelnewsupdatesList.size();
    }
}


