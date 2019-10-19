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

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelMatchHotelRoomTypeActivity;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchAddrcyItemBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MatchIncludesAdapter extends RecyclerView.Adapter<MatchIncludesAdapter.MyViewHolder> {
    Context mContext;
    List<String> matchIncludeArray;
    String includesNameStr,selectedroomNameStr,selectedroomImageStr;
    MatchExperienceAddAdapter matchExperienceAddAdapter;
    ArrayList<String> experienceList;
    ArrayList<TravelModel> matchHotelAmenitiesList;
    MatchHotelAmenitiesAdapter matchHotelAmenitiesAdapter;

    public MatchIncludesAdapter(Context mContext, ArrayList<String> matchIncludeArray, String includesName,
                                String selectedroomNameStr, String selectedroomImageStr) {
        this.mContext = mContext;
        this.matchIncludeArray = matchIncludeArray;
        this.includesNameStr = includesName;
        this.selectedroomNameStr=selectedroomNameStr;
        this.selectedroomImageStr=selectedroomImageStr;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MatchAddrcyItemBinding matchAddrcyItemBinding;

        public MyViewHolder(MatchAddrcyItemBinding matchAddrcyItemBinding) {
            super(matchAddrcyItemBinding.getRoot());

            this.matchAddrcyItemBinding=matchAddrcyItemBinding;


        }
    }


    @Override
    public MatchIncludesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        EventBus.getDefault().register(this);
        MatchAddrcyItemBinding matchAddrcyItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.match_addrcy_item,parent,false);
        return new MatchIncludesAdapter.MyViewHolder(matchAddrcyItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MatchIncludesAdapter.MyViewHolder holder, int position) {


        if (includesNameStr.equalsIgnoreCase("tickets")) {
            holder.matchAddrcyItemBinding.ticketLinear.setVisibility(View.VISIBLE);
            holder.matchAddrcyItemBinding.hospitalityLinear.setVisibility(View.GONE);
            holder.matchAddrcyItemBinding.hotelLinear.setVisibility(View.GONE);

            holder.matchAddrcyItemBinding.addtoticketscart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.matchAddrcyItemBinding.ticketscartAddimage.setImageResource(R.drawable.fill_selected_checkbox);
                    holder.matchAddrcyItemBinding.ticketLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
                    holder.matchAddrcyItemBinding.removetoticketscart1Linear.setVisibility(View.VISIBLE);
                    holder.matchAddrcyItemBinding.addtoticketscart1Linear.setVisibility(View.GONE);
                }
            });
            holder.matchAddrcyItemBinding.removetoticketscart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.matchAddrcyItemBinding.ticketscartAddimage.setImageResource(R.drawable.ic_blank_check);
                    holder.matchAddrcyItemBinding.ticketLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_child_curveshape));
                    holder.matchAddrcyItemBinding.removetoticketscart1Linear.setVisibility(View.GONE);
                    holder.matchAddrcyItemBinding.addtoticketscart1Linear.setVisibility(View.VISIBLE);
                }
            });

        } else if(includesNameStr.equalsIgnoreCase("hotel")){
            holder.matchAddrcyItemBinding.hotelLinear.setVisibility(View.VISIBLE);
            holder.matchAddrcyItemBinding.hospitalityLinear.setVisibility(View.GONE);
            holder.matchAddrcyItemBinding.ticketLinear.setVisibility(View.GONE);
            Utils.setImageInImageView("https://www.bharatarmy.com/Docs/e4acae00-e.jpg",holder.matchAddrcyItemBinding.hotelImg,mContext);

           Utils.setImageInImageView(AppConfiguration.IMAGE_URL + "hotelgallery1.jpeg",holder.matchAddrcyItemBinding.ticketImg,mContext);
            matchHotelAmenitiesList = new ArrayList<TravelModel>();
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "parking.png", "Parking"));
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "tv.png", "Tv"));
            matchHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL + "bathtub.png", "Bathtub"));


            Utils.setImageInImageView(AppConfiguration.IMAGE_URL+"d_hotelroom1.jpg",holder.matchAddrcyItemBinding.roomImg,mContext);

            if (!selectedroomNameStr.equalsIgnoreCase("")){
                holder.matchAddrcyItemBinding.roomNametxt.setText(selectedroomNameStr);
            }
            holder.matchAddrcyItemBinding.selectRoomLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent roomIntent = new Intent(mContext, TravelMatchHotelRoomTypeActivity.class);
                    roomIntent.putExtra("clickposition",String.valueOf(position));
                    roomIntent.putExtra("roomName",holder.matchAddrcyItemBinding.roomNametxt.getText().toString());
                    roomIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(roomIntent);
                }
            });

            matchHotelAmenitiesAdapter = new MatchHotelAmenitiesAdapter(mContext, matchHotelAmenitiesList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            holder.matchAddrcyItemBinding.amenities.setLayoutManager(mLayoutManager);
            holder.matchAddrcyItemBinding.amenities.setItemAnimator(new DefaultItemAnimator());
            holder.matchAddrcyItemBinding.amenities.setAdapter(matchHotelAmenitiesAdapter);
            holder.matchAddrcyItemBinding.bookLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookIntent = new Intent(mContext, TravelBookActivity.class);
                    bookIntent.putExtra("pacakgeName", "Australian Double Dhamaka: Honeymoon and adventure at one shot");
                    bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(bookIntent);
                }
            });
        }
        else {
            holder.matchAddrcyItemBinding.hospitalityLinear.setVisibility(View.VISIBLE);
            holder.matchAddrcyItemBinding.hotelLinear.setVisibility(View.GONE);
            holder.matchAddrcyItemBinding.ticketLinear.setVisibility(View.GONE);

            Utils.setImageInImageView("https://www.bharatarmy.com/Docs/e4acae00-e.jpg",holder.matchAddrcyItemBinding.hospitalityMainImage,mContext);

            experienceList = new ArrayList<>();
            experienceList.add("1");
            matchExperienceAddAdapter = new MatchExperienceAddAdapter(mContext, experienceList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            holder.matchAddrcyItemBinding.exprienceRcv.setLayoutManager(mLayoutManager);
            holder.matchAddrcyItemBinding.exprienceRcv.setItemAnimator(new DefaultItemAnimator());
            holder.matchAddrcyItemBinding.exprienceRcv.setAdapter(matchExperienceAddAdapter);

            holder.matchAddrcyItemBinding.addtohospitalitycart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.matchAddrcyItemBinding.hospitalityAddImage.setImageResource(R.drawable.fill_selected_checkbox);
                    holder.matchAddrcyItemBinding.hospitalityLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
                    holder.matchAddrcyItemBinding.removetohospitalitycart1Linear.setVisibility(View.VISIBLE);
                    holder.matchAddrcyItemBinding.addtohospitalitycart1Linear.setVisibility(View.GONE);
                }
            });
            holder.matchAddrcyItemBinding.removetohospitalitycart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.matchAddrcyItemBinding.hospitalityAddImage.setImageResource(R.drawable.ic_blank_check);
                    holder.matchAddrcyItemBinding.hospitalityLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_child_curveshape));
                    holder.matchAddrcyItemBinding.removetohospitalitycart1Linear.setVisibility(View.GONE);
                    holder.matchAddrcyItemBinding.addtohospitalitycart1Linear.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()){
            for (final Object payload : payloads) {
                Log.d("payloadMatchInclude :",payload.toString());
                String payLoaddata =payload.toString();
                String [] splitvalue=payLoaddata.split("\\|");

                    selectedroomNameStr=splitvalue[1];
                    selectedroomImageStr=splitvalue[2];

                   holder.matchAddrcyItemBinding.roomNametxt.setText(selectedroomNameStr);
                   Utils.setImageInImageView(selectedroomImageStr,holder.matchAddrcyItemBinding.roomImg,mContext);

            }
        } else {
            // in this case regular onBindViewHolder will be called
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
        return position;
    }

    @Override
    public int getItemCount() {
        return matchIncludeArray.size();
    }


}




