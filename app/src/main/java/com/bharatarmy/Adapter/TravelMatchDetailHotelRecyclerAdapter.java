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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelCityHotelDetailsActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchDetailHotelRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<TravelModel> matchHotelList;
    private static final int HEADER = 0;
    private static final int ITEM = 1;
    String titleNameStr;
    ArrayList<TravelModel> matchHotelAmenitiesList;
    MatchHotelAmenitiesAdapter matchHotelAmenitiesAdapter;

    public TravelMatchDetailHotelRecyclerAdapter(Context mContext, ArrayList<TravelModel> matchHotelList, String titleNameStr) {
        this.mContext = mContext;
        this.matchHotelList = matchHotelList;
        this.titleNameStr = titleNameStr;
    }


    public class MyItemViewHolder extends RecyclerView.ViewHolder {
        ImageView hotel_img;
        TextView hotelname_txt, hotel_location_txt, packageprice_txt;
        com.whinc.widget.ratingbar.RatingBar ratingBar;
        CardView hotel_cardclick;
        RecyclerView amenities;
        LinearLayout book_linear;

        public MyItemViewHolder(View view) {
            super(view);
            hotel_img = (ImageView) view.findViewById(R.id.hotel_img);
            hotelname_txt = (TextView) view.findViewById(R.id.hotelname_txt);
            hotel_location_txt = (TextView) view.findViewById(R.id.hotel_location_txt);
            packageprice_txt = (TextView) view.findViewById(R.id.packageprice_txt);
            ratingBar = (com.whinc.widget.ratingbar.RatingBar) view.findViewById(R.id.ratingBar);
            hotel_cardclick = (CardView) view.findViewById(R.id.hotel_cardclick);
            amenities = (RecyclerView) view.findViewById(R.id.amenities);
            book_linear = (LinearLayout) view.findViewById(R.id.book_linear);
        }
    }



    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView first_countryflag_image, second_countryflag_image;
        TextView title_txtView;

        HeaderViewHolder(View itemView) {
            super(itemView);
            first_countryflag_image = (ImageView) itemView.findViewById(R.id.first_countryflag_image);
            second_countryflag_image = (ImageView) itemView.findViewById(R.id.second_countryflag_image);

            title_txtView = (TextView) itemView.findViewById(R.id.title_txtView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.match_detailtitle_item, parent, false);
                return new HeaderViewHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.match_hotel_detail_item, parent, false);
                return new MyItemViewHolder(v);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            final TravelModel cityallhoteldetail = matchHotelList.get(position - 1);

            Utils.setImageInImageView(cityallhoteldetail.getCityAllHotelImage(), ((MyItemViewHolder) holder).hotel_img, mContext);

            ((MyItemViewHolder) holder).hotelname_txt.setText(cityallhoteldetail.getCityAllHotelName());
            ((MyItemViewHolder) holder).hotel_location_txt.setText(cityallhoteldetail.getCityAllHotelLocation());
            ((MyItemViewHolder) holder).packageprice_txt.setText("₹ " + cityallhoteldetail.getCityAllHotelPrice());
            ((MyItemViewHolder) holder).ratingBar.setCount(cityallhoteldetail.getCityAllHotelRating());

            ((MyItemViewHolder) holder).hotel_cardclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cityHotelDetail = new Intent(mContext, TravelCityHotelDetailsActivity.class);
                    cityHotelDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(cityHotelDetail);
                }
            });

            matchHotelAmenitiesList = new ArrayList<TravelModel>();
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "parking.png", "Parking"));
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "tv.png", "Tv"));
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "bathtub.png", "Bathtub"));
//            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "washer.png", "Washer"));
//            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "airconditioner.png", "Air condition"));
//            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "wifi.png", "Wifi"));

            matchHotelAmenitiesAdapter = new MatchHotelAmenitiesAdapter(mContext, matchHotelAmenitiesList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            ((MyItemViewHolder) holder).amenities.setLayoutManager(mLayoutManager);
            ((MyItemViewHolder) holder).amenities.setItemAnimator(new DefaultItemAnimator());
            ((MyItemViewHolder) holder).amenities.setAdapter(matchHotelAmenitiesAdapter);

            ((MyItemViewHolder) holder).book_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookIntent = new Intent(mContext, TravelBookActivity.class);
                    bookIntent.putExtra("pacakgeName", "Australian Double Dhamaka: Honeymoon and adventure at one shot");
                    bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(bookIntent);
                }
            });
        } else if (holder.getItemViewType() == HEADER) {
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png", ((HeaderViewHolder) holder).first_countryflag_image, mContext);
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png", ((HeaderViewHolder) holder).second_countryflag_image, mContext);
            ((HeaderViewHolder) holder).title_txtView.setText(titleNameStr);
        }
    }

    @Override

    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return matchHotelList.size() + 1;
    }


}




