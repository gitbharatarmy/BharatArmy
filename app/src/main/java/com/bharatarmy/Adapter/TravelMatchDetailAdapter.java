package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchDetailAdapter extends RecyclerView.Adapter<TravelMatchDetailAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> arrayList;


    public TravelMatchDetailAdapter(Context mContext, ArrayList<TravelModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Firstcountry_flagImage, Secondcountry_flagImage, left_bg_img;
        LinearLayout left_bg_Linear;

        public MyViewHolder(View view) {
            super(view);
            Firstcountry_flagImage = (ImageView) view.findViewById(R.id.firstcountry_flagImage);
            Secondcountry_flagImage = (ImageView) view.findViewById(R.id.secondcountry_flagImage);
            left_bg_Linear = (LinearLayout) view.findViewById(R.id.left_bg_Linear);
            left_bg_img = (ImageView) view.findViewById(R.id.left_bg_img);
        }
    }


    @Override

    public TravelMatchDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_match_detail_item_list_new, parent, false);


        return new TravelMatchDetailAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelMatchDetailAdapter.MyViewHolder holder, int position) {

        TravelModel matchDetail = arrayList.get(position);
        Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png", holder.Firstcountry_flagImage, mContext);
        Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png", holder.Secondcountry_flagImage, mContext);

        if (matchDetail.getPosition() != 0) {

            ViewGroup.LayoutParams params = holder.left_bg_Linear.getLayoutParams();
// Changes the height and width to the specified *pixels*
            params.height = 210;
            params.width = 210;

            holder.left_bg_Linear.setLayoutParams(params);
            holder.left_bg_img.setImageResource(R.drawable.right_bg);
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
        return arrayList.size();
    }
}

