package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelPopularCityDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelPopularCItyAdapter extends RecyclerView.Adapter<TravelPopularCItyAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> popularcityList;


    public TravelPopularCItyAdapter(Context mContext, List<TravelModel> popularcityList) {
        this.mContext = mContext;
        this.popularcityList = popularcityList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView travel_popular_city_banner_img;
        TextView travel_popular_city_name_txt, travel_popularcity_pic_count_txt;
        CardView citycard_click;

        public MyViewHolder(View view) {
            super(view);
            travel_popular_city_banner_img = (ImageView) view.findViewById(R.id.travel_popular_city_banner_img);
            travel_popular_city_name_txt = (TextView) view.findViewById(R.id.travel_popular_city_name_txt);
            travel_popularcity_pic_count_txt = (TextView) view.findViewById(R.id.travel_popularcity_pic_count_txt);
            citycard_click = (CardView) view.findViewById(R.id.citycard_click);
        }
    }


    @Override
    public TravelPopularCItyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popularcity_item_list, parent, false);


        return new TravelPopularCItyAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelPopularCItyAdapter.MyViewHolder holder, int position) {
        final TravelModel citydetail = popularcityList.get(position);
//        Utils.setImageInImageView(String.valueOf(citydetail.getPopularcity_image()),holder.travel_popular_city_banner_img,mContext);

        Picasso.with(mContext)
                .load(citydetail.getPopularcity_image())
                .into(holder.travel_popular_city_banner_img);



        holder.travel_popular_city_name_txt.setText(citydetail.getPopularcity_name());
        holder.travel_popularcity_pic_count_txt.setText(citydetail.getPopularcity_image_count());

        holder.citycard_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityIntent =new Intent(mContext, TravelPopularCityDetailActivity.class);
                cityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(cityIntent);
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
        return popularcityList.size();
    }
}

