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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelCityHotelDetailsActivity;
import com.bharatarmy.Activity.TravelCitySightseenDetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchHotelAmenitiesItemBinding;
import com.bharatarmy.databinding.MatchSightseenDetailItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchDetailSightseensRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<TravelModel> matchSightseenList;
    private static final int HEADER = 0;
    private static final int ITEM = 1;
    String titleNameStr;
  


    public TravelMatchDetailSightseensRecyclerAdapter(Context mContext, ArrayList<TravelModel> matchSightseenList, String titleNameStr) {
        this.mContext=mContext;
        this.matchSightseenList=matchSightseenList;
        this.titleNameStr=titleNameStr;
    }


    public class MyItemViewHolder extends RecyclerView.ViewHolder {
    MatchSightseenDetailItemBinding matchSightseenDetailItemBinding;

        public MyItemViewHolder(MatchSightseenDetailItemBinding matchSightseenDetailItemBinding) {
            super(matchSightseenDetailItemBinding.getRoot());
this.matchSightseenDetailItemBinding=matchSightseenDetailItemBinding;
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
                return new TravelMatchDetailSightseensRecyclerAdapter.HeaderViewHolder(v);
            default:
//                v = layoutInflater.inflate(R.layout.match_sightseen_detail_item, parent, false);
//                return new TravelMatchDetailSightseensRecyclerAdapter.MyItemViewHolder(v);
                MatchSightseenDetailItemBinding matchSightseenDetailItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.match_sightseen_detail_item,parent,false);
                return new MyItemViewHolder(matchSightseenDetailItemBinding);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            final TravelModel cityallhoteldetail = matchSightseenList.get(position - 1);

            Utils.setImageInImageView(cityallhoteldetail.getCityAllHotelImage(),((MyItemViewHolder) holder).matchSightseenDetailItemBinding.siteImg, mContext);

            ((MyItemViewHolder) holder).matchSightseenDetailItemBinding.sitenameTxt.setText(cityallhoteldetail.getCityAllHotelName());
            ((MyItemViewHolder) holder).matchSightseenDetailItemBinding.siteLocationTxt.setText(cityallhoteldetail.getCityAllHotelLocation());
            ((MyItemViewHolder) holder).matchSightseenDetailItemBinding.ratingBar.setCount(cityallhoteldetail.getCityAllHotelRating());

            ((MyItemViewHolder) holder).matchSightseenDetailItemBinding.siteCardclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent citysightseenDetail=new Intent(mContext, TravelCitySightseenDetailActivity.class);
                    citysightseenDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(citysightseenDetail);
                }
            });

        } else if (holder.getItemViewType() == HEADER) {
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png", ((TravelMatchDetailSightseensRecyclerAdapter.HeaderViewHolder) holder).first_countryflag_image, mContext);
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png", ((TravelMatchDetailSightseensRecyclerAdapter.HeaderViewHolder) holder).second_countryflag_image, mContext);
            ((TravelMatchDetailSightseensRecyclerAdapter.HeaderViewHolder) holder).title_txtView.setText(titleNameStr);
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
        return matchSightseenList.size() + 1;
    }


}





