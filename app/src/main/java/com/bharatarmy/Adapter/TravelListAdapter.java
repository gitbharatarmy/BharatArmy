package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.LocationMapActivity;
import com.bharatarmy.Activity.TravelDetailActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.BulletsPoint;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TravelListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    List<TravelModel> travelModelArrayList;
    image_click image_click;
    Context mContext;

    public TravelListAdapter(Context mContext, List<TravelModel> travelModelArrayList, image_click image_click) {
        this.mContext = mContext;
        this.travelModelArrayList = travelModelArrayList;
        this.image_click = image_click;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return travelModelArrayList.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.header, parent, false);
                return new HeaderViewHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.row_recyclerview, parent, false);
                return new ItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            TravelModel travelDetail = travelModelArrayList.get(position - 1);
            ((ItemViewHolder) holder).match_type_txt.setText(travelDetail.getMatch_type());
            ((ItemViewHolder) holder).travel_date_txt.setText(travelDetail.getMatch_date());
            ((ItemViewHolder) holder).first_match_txt.setText(travelDetail.getMatch_first_Country());
            ((ItemViewHolder) holder).second_match_txt.setText(travelDetail.getMatch_Second_country());
            ((ItemViewHolder) holder).match_address_txt.setText(travelDetail.getMatch_location());

            ((ItemViewHolder) holder).first_match_flag_img.setImageResource(travelDetail.getMatch_first_country_flag());
            ((ItemViewHolder) holder).second_match_flag_img.setImageResource(travelDetail.getMatch_second_country_flag());

            ((ItemViewHolder) holder).third_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConfiguration.firstDashStr = "false";
                    Intent locationIntent = new Intent(mContext, LocationMapActivity.class);
                    mContext.startActivity(locationIntent);
                }
            });

            ((ItemViewHolder) holder).middle_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent travelIntent=new Intent(mContext, TravelDetailActivity.class);
                  mContext.startActivity(travelIntent);
                }
            });
        }else if (holder.getItemViewType()==HEADER){
            //Countdown Timer
            Date endDate = new Date();
            final long[] diffInMilis = new long[1];
            final Date startDate = new Date();
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                String dateToStr = format.format(startDate);
                Log.d("Todaytime", dateToStr);
                SimpleDateFormat formatendDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");

                endDate = formatendDate.parse("30/05/2019 04:15:00 PM");


                final Date finalEndDate = endDate;
//                    Calculate the difference in millisecond between two dates
                diffInMilis[0] = finalEndDate.getTime() - startDate.getTime();
            }catch (ParseException ex){

            }
//            ((HeaderViewHolder) holder).timerProgramCountdown.startCountDown(diffInMilis[0]);
//
//            ((HeaderViewHolder) holder).timerProgramCountdown.setCountdownListener(new CountDownClock.CountdownCallBack() {
//                @Override
//                public void countdownAboutToFinish() {
//
//                }
//
//                @Override
//                public void countdownFinished() {
//                    ((HeaderViewHolder) holder).timerProgramCountdown.resetCountdownTimer();
//                }
//            });
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
//    CountDownClock timerProgramCountdown;
        HeaderViewHolder(View itemView) {
            super(itemView);
//            timerProgramCountdown=(CountDownClock)itemView.findViewById(R.id.timerProgramCountdown);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView match_type_txt, travel_date_txt, first_match_txt, travel_vs_txt, second_match_txt, match_address_txt;
        LinearLayout header_linear, middle_linear, third_linear;
        ImageView first_match_flag_img, second_match_flag_img;

        ItemViewHolder(View itemView) {
            super(itemView);
            header_linear = (LinearLayout) itemView.findViewById(R.id.header_linear);
            middle_linear = (LinearLayout) itemView.findViewById(R.id.middle_linear);
            third_linear = (LinearLayout) itemView.findViewById(R.id.third_linear);

            match_type_txt = (TextView) itemView.findViewById(R.id.match_type_txt);
            travel_date_txt = (TextView) itemView.findViewById(R.id.travel_date_txt);
            first_match_txt = (TextView) itemView.findViewById(R.id.first_match_txt);
            travel_vs_txt = (TextView) itemView.findViewById(R.id.travel_vs_txt);
            second_match_txt = (TextView) itemView.findViewById(R.id.second_match_txt);
            match_address_txt = (TextView) itemView.findViewById(R.id.match_address_txt);

            first_match_flag_img = (ImageView) itemView.findViewById(R.id.travel_flag_img);
            second_match_flag_img = (ImageView) itemView.findViewById(R.id.travel_flag1_img);
        }

    }
}