package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchTicketAndHospitalityActivity;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchDetailtitleItemBinding;
import com.bharatarmy.databinding.TravelMatchGroupdetailItemListBinding;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchDetailRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    MatchIncludesAdapter matchIncludesAdapter;
    String includesName,selectedroomNameStr,selectedroomImageStr;
    ArrayList<String> matchIncludeArray;
    Context mContext;
    List<String> listDataHeader;
    String titleNameStr;

    public TravelMatchDetailRecyclerAdapter(Context mContext, List<String> listDataHeader,
                                            String titleNameStr, String selectedroomNameStr, String selectedroomImageStr) {
        this.mContext = mContext;
        this.listDataHeader = listDataHeader;
        this.titleNameStr = titleNameStr;
        this.selectedroomNameStr=selectedroomNameStr;
        this.selectedroomImageStr=selectedroomImageStr;
    }

    static class MyItemViewHolder extends RecyclerView.ViewHolder {
        TravelMatchGroupdetailItemListBinding travelMatchGroupdetailItemListBinding;


        public MyItemViewHolder(TravelMatchGroupdetailItemListBinding travelMatchGroupdetailItemListBinding) {
            super(travelMatchGroupdetailItemListBinding.getRoot());
            this.travelMatchGroupdetailItemListBinding=travelMatchGroupdetailItemListBinding;


        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        MatchDetailtitleItemBinding matchDetailtitleItemBinding;

        HeaderViewHolder(MatchDetailtitleItemBinding matchDetailtitleItemBinding) {
            super(matchDetailtitleItemBinding.getRoot());

            this.matchDetailtitleItemBinding=matchDetailtitleItemBinding;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        EventBus.getDefault().register(this);
        switch (viewType) {
            case HEADER:
                MatchDetailtitleItemBinding matchDetailtitleItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.match_detailtitle_item,parent,false);
                return new TravelMatchDetailRecyclerAdapter.HeaderViewHolder(matchDetailtitleItemBinding);

            default:
                TravelMatchGroupdetailItemListBinding travelMatchGroupdetailItemListBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.travel_match_groupdetail_item_list,parent,false);
                return new TravelMatchDetailRecyclerAdapter.MyItemViewHolder(travelMatchGroupdetailItemListBinding);

        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
                ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.mainGroupLiner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.isShown()) {
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setVisibility(View.GONE);
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        } else {
                            matchIncludeArray = new ArrayList<>();
                            matchIncludeArray.add("1");
                            matchIncludeArray.add("2");
                            includesName = "tickets";
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setVisibility(View.VISIBLE);
                            matchIncludesAdapter = new MatchIncludesAdapter(mContext, matchIncludeArray, includesName,selectedroomNameStr,selectedroomImageStr);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setLayoutManager(mLayoutManager);
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setItemAnimator(new DefaultItemAnimator());
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setAdapter(matchIncludesAdapter);

                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        }

                    }
                });

                ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketClickLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.circle_graidant));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketImage.setColorFilter(mContext.getResources().getColor(R.color.heading_bg));
//                        matchIncludeArray = new ArrayList<>();
//                        matchIncludeArray.add("1");
//                        matchIncludeArray.add("2");
//                        includesName = "tickets";
//                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setVisibility(View.VISIBLE);
//                        matchIncludesAdapter = new MatchIncludesAdapter(mContext, matchIncludeArray, includesName,selectedroomNameStr,selectedroomImageStr);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
//                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setLayoutManager(mLayoutManager);
//                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setItemAnimator(new DefaultItemAnimator());
//                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setAdapter(matchIncludesAdapter);
//                        matchIncludesAdapter.notifyDataSetChanged();

                        Intent tickethospitalityIntent=new Intent(mContext, TravelMatchTicketAndHospitalityActivity.class);
                        tickethospitalityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(tickethospitalityIntent);
                    }
                });

            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelClickLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelClickLinear.setBackground(mContext.getDrawable(R.drawable.circle_graidant));
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelImage.setColorFilter(mContext.getResources().getColor(R.color.heading_bg));
                    matchIncludeArray = new ArrayList<>();
                    matchIncludeArray.add("1");
                    includesName = "hotel";
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setVisibility(View.VISIBLE);
                    matchIncludesAdapter = new MatchIncludesAdapter(mContext, matchIncludeArray, includesName,selectedroomNameStr,selectedroomImageStr);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setLayoutManager(mLayoutManager);
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setItemAnimator(new DefaultItemAnimator());
                    ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setAdapter(matchIncludesAdapter);
                    matchIncludesAdapter.notifyDataSetChanged();
                }
            });

            ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityClickLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.ticketImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelClickLinear.setBackground(mContext.getDrawable(R.drawable.gray_circlering));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hotelImage.setColorFilter(mContext.getResources().getColor(R.color.gray));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityClickLinear.setBackground(mContext.getDrawable(R.drawable.circle_graidant));
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.hospitalityImage.setColorFilter(mContext.getResources().getColor(R.color.heading_bg));
                        matchIncludeArray = new ArrayList<>();
                        matchIncludeArray.add("1");
                        includesName = "hospitality";
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setVisibility(View.VISIBLE);
                        matchIncludesAdapter = new MatchIncludesAdapter(mContext, matchIncludeArray, includesName,selectedroomNameStr,selectedroomImageStr);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setLayoutManager(mLayoutManager);
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setItemAnimator(new DefaultItemAnimator());
                        ((MyItemViewHolder) holder).travelMatchGroupdetailItemListBinding.matchAddRcv.setAdapter(matchIncludesAdapter);
                        matchIncludesAdapter.notifyDataSetChanged();
                    }
                });

        } else if (holder.getItemViewType() == HEADER) {
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png", ((HeaderViewHolder) holder).matchDetailtitleItemBinding.firstCountryflagImage, mContext);
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png", ((HeaderViewHolder) holder).matchDetailtitleItemBinding.secondCountryflagImage, mContext);
            ((HeaderViewHolder) holder).matchDetailtitleItemBinding.titleTxtView.setText(titleNameStr);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()){
            for (final Object payload : payloads) {
                Log.d("payloadMatchDetail:",payload.toString());

                String payLoaddata =payload.toString();
                String [] splitvalue=payLoaddata.split("\\|");
                matchIncludesAdapter.notifyItemChanged(Integer.parseInt(splitvalue[0]),payload.toString());

            }
        }else{
            super.onBindViewHolder(holder, position, payloads);
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


