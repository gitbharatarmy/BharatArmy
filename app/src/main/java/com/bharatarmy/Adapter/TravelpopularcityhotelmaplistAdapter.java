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

import com.bharatarmy.Activity.TravelCityHotelDetailsActivity;
import com.bharatarmy.Activity.TravelPopularCityDetailActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TravelpopularcityhotelmaplistAdapter extends RecyclerView.Adapter<TravelpopularcityhotelmaplistAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> cityHotelList;
    image_click image_click;
    private ArrayList<String> dataCheck;

    public TravelpopularcityhotelmaplistAdapter(Context mContext, ArrayList<TravelModel> cityHotelList, image_click image_click) {
        this.mContext = mContext;
        this.cityHotelList = cityHotelList;
        this.image_click=image_click;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView city_hotel_image;
        TextView city_hotelname_txt, city_hoteldesc_txt,viewdetail_btn;
        CardView citycard_click;

        public MyViewHolder(View view) {
            super(view);
            city_hotel_image = (ImageView) view.findViewById(R.id.city_hotel_image);
            city_hotelname_txt = (TextView) view.findViewById(R.id.city_hotelname_txt);
            city_hoteldesc_txt = (TextView) view.findViewById(R.id.city_hoteldesc_txt);
            citycard_click = (CardView) view.findViewById(R.id.citycard_click);
            viewdetail_btn=(TextView)view.findViewById(R.id.viewdetail_btn);
        }
    }


    @Override
    public TravelpopularcityhotelmaplistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_citymap_item_list, parent, false);


        return new TravelpopularcityhotelmaplistAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelpopularcityhotelmaplistAdapter.MyViewHolder holder, int position) {
        final TravelModel cityhoteldetail = cityHotelList.get(position);

        Utils.setImageInImageView(cityhoteldetail.getPopularCityPlaceImage(),holder.city_hotel_image,mContext);

        holder.city_hotelname_txt.setText(cityhoteldetail.getPopularCityPlaceName());
        holder.city_hoteldesc_txt.setText(cityhoteldetail.getPopularCityPlaceDescription());

        holder.citycard_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent cityIntent =new Intent(mContext, TravelPopularCityDetailActivity.class);
//                cityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(cityIntent);

                dataCheck=new ArrayList<>();
                dataCheck.add(String.valueOf(position));
                image_click.image_more_click();
            }
        });

        holder.viewdetail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityHotelDetail=new Intent(mContext, TravelCityHotelDetailsActivity.class);
                cityHotelDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(cityHotelDetail);
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
        return cityHotelList.size();
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}


