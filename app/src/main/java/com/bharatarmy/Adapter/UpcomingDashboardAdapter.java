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

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.FTPDetailsActivity;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.UpcomingTournamentListNewBinding;
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

        UpcomingTournamentListNewBinding upcomingTournamentListNewBinding;

//        public TextView header_txt, army_upcoming_header_txt, army_upcoming_sub_txt,
//                date_txt, location_txt, army_upcoming_pra_txt, linear1_txt, linear2_txt, linear3_txt;
//        ImageView banner_img, header_img, date_img, location_img;
//        LinearLayout lable_linear;

        public MyViewHolder(UpcomingTournamentListNewBinding upcomingTournamentListNewBinding) {
            super(upcomingTournamentListNewBinding.getRoot());

            this.upcomingTournamentListNewBinding=upcomingTournamentListNewBinding;

//            header_txt = (TextView) view.findViewById(R.id.header_txt);
//            army_upcoming_header_txt = (TextView) view.findViewById(R.id.army_upcoming_header_txt);
//            army_upcoming_sub_txt = (TextView) view.findViewById(R.id.army_upcoming_sub_txt);
//            date_txt = (TextView) view.findViewById(R.id.date_txt);
//            location_txt = (TextView) view.findViewById(R.id.location_txt);
//            army_upcoming_pra_txt = (TextView) view.findViewById(R.id.army_upcoming_pra_txt);
//            linear1_txt = (TextView) view.findViewById(R.id.linear1_txt);
//            linear2_txt = (TextView) view.findViewById(R.id.linear2_txt);
//            linear3_txt = (TextView) view.findViewById(R.id.linear3_txt);
//
//            banner_img = (ImageView) view.findViewById(R.id.banner_img);
//            header_img = (ImageView) view.findViewById(R.id.header_img);
//            date_img = (ImageView) view.findViewById(R.id.date_img);
//            location_img = (ImageView) view.findViewById(R.id.location_img);
//
//            lable_linear = (LinearLayout) view.findViewById(R.id.lable_linear);





        }
    }


    @Override
    public UpcomingDashboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      UpcomingTournamentListNewBinding upcomingTournamentListNewBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.upcoming_tournament_list_new,parent,false);
      return new UpcomingDashboardAdapter.MyViewHolder(upcomingTournamentListNewBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        final UpcommingDashboardModel upcomingData = upcomingDataList.get(position);

//       Utils.setImageInImageView(upcomingData.getCategoryName(),holder.upcomingTournamentListNewBinding.headerImg,mcontext);
        holder.upcomingTournamentListNewBinding.armyUpcomingHeaderTxt.setText(upcomingData.getTourName());
        holder.upcomingTournamentListNewBinding.armyUpcomingSubTxt.setText(upcomingData.getSubCategoryId());
        holder.upcomingTournamentListNewBinding.locationTxt.setText(upcomingData.getTourLocation());
        holder.upcomingTournamentListNewBinding.armyUpcomingPraTxt.setText(upcomingData.getTourShortDescription());

//        Picasso.with(mcontext)
//                .load(upcomingData.getFutureTourThumbImageURL())
//                .placeholder(R.drawable.progress_animation)
//                .into(holder.banner_img);

        Utils.setImageInImageView(upcomingData.getFutureTourThumbImageURL(),holder.upcomingTournamentListNewBinding.bannerImg,mcontext);

        if (!upcomingData.getStr1().equalsIgnoreCase("")) {
            holder.upcomingTournamentListNewBinding.linear1Txt.setVisibility(View.VISIBLE);
            holder.upcomingTournamentListNewBinding.linear1Txt.setText(upcomingData.getStr1());
        } else {
            holder.upcomingTournamentListNewBinding.linear1Txt.setVisibility(View.GONE);
        }

        if (!upcomingData.getStr2().equalsIgnoreCase("")) {
            if (!upcomingData.getStr2().equalsIgnoreCase("1")) {
                holder.upcomingTournamentListNewBinding.linear2Txt.setVisibility(View.VISIBLE);
                holder.upcomingTournamentListNewBinding.linear2Txt.setText(upcomingData.getStr2());
            } else {
                holder.upcomingTournamentListNewBinding.linear2Txt.setVisibility(View.GONE);
            }
        } else {
            holder.upcomingTournamentListNewBinding.linear2Txt.setVisibility(View.GONE);
        }

        if (!upcomingData.getStr3().equalsIgnoreCase("")) {
            if (!upcomingData.getStr3().equalsIgnoreCase("1")) {
                holder.upcomingTournamentListNewBinding.linear3Txt.setVisibility(View.VISIBLE);
                holder.upcomingTournamentListNewBinding.linear3Txt.setText(upcomingData.getStr3());
            } else {
                holder.upcomingTournamentListNewBinding.linear3Txt.setVisibility(View.GONE);
            }
        } else {
            holder.upcomingTournamentListNewBinding.linear3Txt.setVisibility(View.GONE);
        }
  holder.upcomingTournamentListNewBinding.armyUpcomingHeaderTxt.setOnClickListener(new View.OnClickListener() {
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

