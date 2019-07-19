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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelPackageActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchDetailTicketsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    MatchTicketsAdapter matchTicketsAdapter;
    String includesName;
    ArrayList<String> matchTicketsArray;
    List<String> listtickets;
    Context mContext;
    String titleNameStr;


    public TravelMatchDetailTicketsRecyclerAdapter(Context mContext, List<String> listtickets, String titleNameStr) {
        this.mContext = mContext;
        this.titleNameStr = titleNameStr;
        this.listtickets = listtickets;
    }

    static class MyItemViewHolder extends RecyclerView.ViewHolder {
RecyclerView match_addRcv;
        public MyItemViewHolder(View view) {
            super(view);
            match_addRcv=(RecyclerView)view.findViewById(R.id.match_addRcv);
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.match_detailtitle_item, parent, false);
                return new TravelMatchDetailTicketsRecyclerAdapter.HeaderViewHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.match_tickets_main_item, parent, false);
                return new TravelMatchDetailTicketsRecyclerAdapter.MyItemViewHolder(v);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            matchTicketsArray = new ArrayList<>();
            matchTicketsArray.add("1");
            matchTicketsArray.add("2");
            includesName = "tickets";
            ((MyItemViewHolder) holder).match_addRcv.setVisibility(View.VISIBLE);
            matchTicketsAdapter = new MatchTicketsAdapter(mContext, matchTicketsArray, includesName);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            ((MyItemViewHolder) holder).match_addRcv.setLayoutManager(mLayoutManager);
            ((MyItemViewHolder) holder).match_addRcv.setItemAnimator(new DefaultItemAnimator());
            ((MyItemViewHolder) holder).match_addRcv.setAdapter(matchTicketsAdapter);
            matchTicketsAdapter.notifyDataSetChanged();
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
        return listtickets.size() + 1;
    }


}



