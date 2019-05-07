package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bharatarmy.Activity.FTPDetailsActivity;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpcomingDashboardAdapter extends RecyclerView.Adapter<UpcomingDashboardAdapter.MyViewHolder> {
    Context mcontext;
    List<UpcommingDashboardModel> upcomingDataList;
    private static final int VIEW_TYPE_PADDING = 1;
    private static final int VIEW_TYPE_ITEM = 2;


    public UpcomingDashboardAdapter(Context mContext, List<UpcommingDashboardModel> upcomingDataList) {
        this.mcontext = mContext;
        this.upcomingDataList = upcomingDataList;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView header_txt, army_upcoming_header_txt, army_upcoming_sub_txt,
                date_txt, location_txt, army_upcoming_pra_txt, linear1_txt, linear2_txt, linear3_txt;
        ImageView banner_img, header_img, date_img, location_img;
        LinearLayout lable_linear;

        public MyViewHolder(View view) {
            super(view);

            header_txt = (TextView) view.findViewById(R.id.header_txt);
            army_upcoming_header_txt = (TextView) view.findViewById(R.id.army_upcoming_header_txt);
            army_upcoming_sub_txt = (TextView) view.findViewById(R.id.army_upcoming_sub_txt);
            date_txt = (TextView) view.findViewById(R.id.date_txt);
            location_txt = (TextView) view.findViewById(R.id.location_txt);
            army_upcoming_pra_txt = (TextView) view.findViewById(R.id.army_upcoming_pra_txt);
            linear1_txt = (TextView) view.findViewById(R.id.linear1_txt);
            linear2_txt = (TextView) view.findViewById(R.id.linear2_txt);
            linear3_txt = (TextView) view.findViewById(R.id.linear3_txt);

            banner_img = (ImageView) view.findViewById(R.id.banner_img);
            header_img = (ImageView) view.findViewById(R.id.header_img);
            date_img = (ImageView) view.findViewById(R.id.date_img);
            location_img = (ImageView) view.findViewById(R.id.location_img);

            lable_linear = (LinearLayout) view.findViewById(R.id.lable_linear);





        }
    }


    @Override
    public UpcomingDashboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_tournament_list, parent, false);

        return new UpcomingDashboardAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        final UpcommingDashboardModel upcomingData = upcomingDataList.get(position);

        holder.header_txt.setText(upcomingData.getCategoryName());
        holder.army_upcoming_header_txt.setText(upcomingData.getTourName());
        holder.army_upcoming_sub_txt.setText(upcomingData.getSubCategoryId());
        holder.location_txt.setText(upcomingData.getTourLocation());
        holder.army_upcoming_pra_txt.setText(upcomingData.getTourShortDescription());

        Picasso.with(mcontext)
                .load(upcomingData.getFutureTourThumbImageURL())
                .placeholder(R.drawable.progress_animation)
                .into(holder.banner_img);


        if (!upcomingData.getStr1().equalsIgnoreCase("")) {
            holder.linear1_txt.setVisibility(View.VISIBLE);
            holder.linear1_txt.setText(upcomingData.getStr1());
        } else {
            holder.linear1_txt.setVisibility(View.GONE);
        }

        if (!upcomingData.getStr2().equalsIgnoreCase("")) {
            if (!upcomingData.getStr2().equalsIgnoreCase("1")) {
                holder.linear2_txt.setVisibility(View.VISIBLE);
                holder.linear2_txt.setText(upcomingData.getStr2());
            } else {
                holder.linear2_txt.setVisibility(View.GONE);
            }
        } else {
            holder.linear2_txt.setVisibility(View.GONE);
        }

        if (!upcomingData.getStr3().equalsIgnoreCase("")) {
            if (!upcomingData.getStr3().equalsIgnoreCase("1")) {
                holder.linear3_txt.setVisibility(View.VISIBLE);
                holder.linear3_txt.setText(upcomingData.getStr3());
            } else {
                holder.linear3_txt.setVisibility(View.GONE);
            }
        } else {
            holder.linear3_txt.setVisibility(View.GONE);
        }
  holder.army_upcoming_header_txt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Intent ftpIntent =new Intent(mcontext, FTPDetailsActivity.class);
          ftpIntent.putExtra("ftpmaintitle",upcomingData.getTourName());
          ftpIntent.putExtra("ftpdate",upcomingData.getDateAdded());
          ftpIntent.putExtra("ftpshortdesc",upcomingData.getTourShortDescription());
          ftpIntent.putExtra("ftptourdesc",upcomingData.getTourDescription());
          ftpIntent.putExtra("ftpbannerimg",upcomingData.getFutureTourThumbImageURL());
          ftpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          mcontext.startActivity(ftpIntent);
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
        return upcomingDataList.size();
    }
}

