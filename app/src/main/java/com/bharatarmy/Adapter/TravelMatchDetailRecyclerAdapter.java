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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchDetailRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    MatchIncludesAdapter matchIncludesAdapter;
    String includesName;
    ArrayList<String> matchIncludeArray;
    Context mContext;
    List<String> listDataHeader;
    String titleNameStr;

    public TravelMatchDetailRecyclerAdapter(Context mContext, List<String> listDataHeader, String titleNameStr) {
        this.mContext = mContext;
        this.listDataHeader = listDataHeader;
        this.titleNameStr = titleNameStr;

    }

    static class MyItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout main_groupLiner, ticketClickLinear, hospitalityClickLinear;
        ShimmerRecyclerView match_addRcv;
        ImageView hospitalityImage, ticketImage;

        public MyItemViewHolder(View view) {
            super(view);
            main_groupLiner = (LinearLayout) view.findViewById(R.id.main_groupLiner);
            ticketClickLinear = (LinearLayout) view.findViewById(R.id.ticketClickLinear);
            hospitalityClickLinear = (LinearLayout) view.findViewById(R.id.hospitalityClickLinear);
            match_addRcv = (ShimmerRecyclerView) view.findViewById(R.id.match_addRcv);

            hospitalityImage = (ImageView) view.findViewById(R.id.hospitalityImage);
            ticketImage = (ImageView) view.findViewById(R.id.ticketImage);
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
                return new TravelMatchDetailRecyclerAdapter.HeaderViewHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.travel_match_groupdetail_item_list, parent, false);
                return new TravelMatchDetailRecyclerAdapter.MyItemViewHolder(v);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
                ((MyItemViewHolder) holder).main_groupLiner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((MyItemViewHolder) holder).match_addRcv.isShown()) {
                            ((MyItemViewHolder) holder).match_addRcv.setVisibility(View.GONE);
                            ((MyItemViewHolder) holder).ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).ticketImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                            ((MyItemViewHolder) holder).hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        } else {
                            matchIncludeArray = new ArrayList<>();
                            matchIncludeArray.add("1");
                            matchIncludeArray.add("2");
                            includesName = "tickets";
                            ((MyItemViewHolder) holder).match_addRcv.setVisibility(View.VISIBLE);
                            matchIncludesAdapter = new MatchIncludesAdapter(mContext, matchIncludeArray, includesName);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                            ((MyItemViewHolder) holder).match_addRcv.setLayoutManager(mLayoutManager);
                            ((MyItemViewHolder) holder).match_addRcv.setItemAnimator(new DefaultItemAnimator());
                            ((MyItemViewHolder) holder).match_addRcv.setAdapter(matchIncludesAdapter);

                            ((MyItemViewHolder) holder).ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).ticketImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                            ((MyItemViewHolder) holder).hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        }

                    }
                });

                ((MyItemViewHolder) holder).ticketClickLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MyItemViewHolder) holder).hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                        ((MyItemViewHolder) holder).hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        ((MyItemViewHolder) holder).ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.circle_graidant));
                        ((MyItemViewHolder) holder).ticketImage.setColorFilter(mContext.getResources().getColor(R.color.heading_bg));
                        matchIncludeArray = new ArrayList<>();
                        matchIncludeArray.add("1");
                        matchIncludeArray.add("2");
                        includesName = "tickets";
                        ((MyItemViewHolder) holder).match_addRcv.setVisibility(View.VISIBLE);
                        matchIncludesAdapter = new MatchIncludesAdapter(mContext, matchIncludeArray, includesName);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                        ((MyItemViewHolder) holder).match_addRcv.setLayoutManager(mLayoutManager);
                        ((MyItemViewHolder) holder).match_addRcv.setItemAnimator(new DefaultItemAnimator());
                        ((MyItemViewHolder) holder).match_addRcv.setAdapter(matchIncludesAdapter);
                        matchIncludesAdapter.notifyDataSetChanged();
                    }
                });
                ((MyItemViewHolder) holder).hospitalityClickLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MyItemViewHolder) holder).ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                        ((MyItemViewHolder) holder).ticketImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        ((MyItemViewHolder) holder).hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.circle_graidant));
                        ((MyItemViewHolder) holder).hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.heading_bg));
                        matchIncludeArray = new ArrayList<>();
                        matchIncludeArray.add("1");
                        includesName = "hospitality";
                        ((MyItemViewHolder) holder).match_addRcv.setVisibility(View.VISIBLE);
                        matchIncludesAdapter = new MatchIncludesAdapter(mContext, matchIncludeArray, includesName);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                        ((MyItemViewHolder) holder).match_addRcv.setLayoutManager(mLayoutManager);
                        ((MyItemViewHolder) holder).match_addRcv.setItemAnimator(new DefaultItemAnimator());
                        ((MyItemViewHolder) holder).match_addRcv.setAdapter(matchIncludesAdapter);
                        matchIncludesAdapter.notifyDataSetChanged();
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
        return listDataHeader.size() + 1;
    }


}


