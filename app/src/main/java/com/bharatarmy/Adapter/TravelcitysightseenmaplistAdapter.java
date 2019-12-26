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

import com.bharatarmy.Activity.TravelCitySightseenDetailActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class TravelcitysightseenmaplistAdapter extends RecyclerView.Adapter<TravelcitysightseenmaplistAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> citySightseenList;
    image_click image_click;
    private ArrayList<String> dataCheck;



    public TravelcitysightseenmaplistAdapter(Context mContext, ArrayList<TravelModel> citySightseenList, image_click image_click) {
        this.mContext=mContext;
        this.citySightseenList=citySightseenList;
        this.image_click=image_click;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView city_sightseen_image;
        TextView city_sightseenname_txt, city_sightseendesc_txt,viewdetail_btn;
        CardView citycard_click;

        public MyViewHolder(View view) {
            super(view);
            city_sightseen_image = (ImageView) view.findViewById(R.id.city_sightseen_image);
            city_sightseenname_txt = (TextView) view.findViewById(R.id.city_sightseenname_txt);
            city_sightseendesc_txt = (TextView) view.findViewById(R.id.city_sightseendesc_txt);
            citycard_click = (CardView) view.findViewById(R.id.citycard_click);
            viewdetail_btn=(TextView)view.findViewById(R.id.viewdetail_btn);
        }
    }


    @Override
    public TravelcitysightseenmaplistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_citysightseenmap_item_list, parent, false);


        return new TravelcitysightseenmaplistAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelcitysightseenmaplistAdapter.MyViewHolder holder, int position) {
        final TravelModel cityhoteldetail = citySightseenList.get(position);

        Utils.setImageInImageView(cityhoteldetail.getPopularCityPlaceImage(),holder.city_sightseen_image,mContext);

        holder.city_sightseenname_txt.setText(cityhoteldetail.getPopularCityPlaceName());
        holder.city_sightseendesc_txt.setText(cityhoteldetail.getPopularCityPlaceDescription());

        holder.citycard_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataCheck=new ArrayList<>();
                dataCheck.add(String.valueOf(position));
                image_click.image_more_click();
            }
        });

        holder.viewdetail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent citysightseenDetail=new Intent(mContext, TravelCitySightseenDetailActivity.class);
                citysightseenDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(citysightseenDetail);
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
        return citySightseenList.size();
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}


