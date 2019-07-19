package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class MatchIncludesAdapter extends RecyclerView.Adapter<MatchIncludesAdapter.MyViewHolder> {
    Context mContext;
    List<String> matchIncludeArray;
    String includesNameStr;
    MatchExperienceAddAdapter matchExperienceAddAdapter;
    ArrayList<String> experienceList;

    public MatchIncludesAdapter(Context mContext, ArrayList<String> matchIncludeArray, String includesName) {
        this.mContext = mContext;
        this.matchIncludeArray = matchIncludeArray;
        this.includesNameStr = includesName;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ticketLinear, hospitalityLinear, addtoticketscart1Linear,
                removetoticketscart1Linear,addtohospitalitycart1Linear,removetohospitalitycart1Linear;
        ImageView ticketscart_addimage,hospitalityMainImage,hospitality_addImage;

        RecyclerView exprienceRcv;

        public MyViewHolder(View view) {
            super(view);

            ticketLinear = (LinearLayout) view.findViewById(R.id.ticketLinear);
            hospitalityLinear = (LinearLayout) view.findViewById(R.id.hospitalityLinear);
            addtoticketscart1Linear = (LinearLayout) view.findViewById(R.id.addtoticketscart1Linear);
            removetoticketscart1Linear = (LinearLayout) view.findViewById(R.id.removetoticketscart1Linear);
            addtohospitalitycart1Linear=(LinearLayout)view.findViewById(R.id.addtohospitalitycart1Linear);
            removetohospitalitycart1Linear=(LinearLayout)view.findViewById(R.id.removetohospitalitycart1Linear);

            exprienceRcv = (RecyclerView) view.findViewById(R.id.exprienceRcv);

            ticketscart_addimage = (ImageView) view.findViewById(R.id.ticketscart_addimage);
            hospitalityMainImage=(ImageView)view.findViewById(R.id.hospitalityMainImage);
            hospitality_addImage=(ImageView)view.findViewById(R.id.hospitality_addImage);

        }
    }


    @Override
    public MatchIncludesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_addrcy_item, parent, false);

        return new MatchIncludesAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(MatchIncludesAdapter.MyViewHolder holder, int position) {

        if (includesNameStr.equalsIgnoreCase("tickets")) {
            holder.ticketLinear.setVisibility(View.VISIBLE);
            holder.hospitalityLinear.setVisibility(View.GONE);

            holder.addtoticketscart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.ticketscart_addimage.setImageResource(R.drawable.fill_selected_checkbox);
                    holder.ticketLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
                    holder.removetoticketscart1Linear.setVisibility(View.VISIBLE);
                    holder.addtoticketscart1Linear.setVisibility(View.GONE);
                }
            });
            holder.removetoticketscart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.ticketscart_addimage.setImageResource(R.drawable.ic_blank_check);
                    holder.ticketLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_child_curveshape));
                    holder.removetoticketscart1Linear.setVisibility(View.GONE);
                    holder.addtoticketscart1Linear.setVisibility(View.VISIBLE);
                }
            });

        } else {
            holder.hospitalityLinear.setVisibility(View.VISIBLE);
            holder.ticketLinear.setVisibility(View.GONE);

            Utils.setImageInImageView("https://www.bharatarmy.com/Docs/e4acae00-e.jpg",holder.hospitalityMainImage,mContext);

            experienceList = new ArrayList<>();
            experienceList.add("1");
            matchExperienceAddAdapter = new MatchExperienceAddAdapter(mContext, experienceList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            holder.exprienceRcv.setLayoutManager(mLayoutManager);
            holder.exprienceRcv.setItemAnimator(new DefaultItemAnimator());
            holder.exprienceRcv.setAdapter(matchExperienceAddAdapter);

            holder.addtohospitalitycart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.hospitality_addImage.setImageResource(R.drawable.fill_selected_checkbox);
                    holder.hospitalityLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
                    holder.removetohospitalitycart1Linear.setVisibility(View.VISIBLE);
                    holder.addtohospitalitycart1Linear.setVisibility(View.GONE);
                }
            });
            holder.removetohospitalitycart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.hospitality_addImage.setImageResource(R.drawable.ic_blank_check);
                    holder.hospitalityLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_child_curveshape));
                    holder.removetohospitalitycart1Linear.setVisibility(View.GONE);
                    holder.addtohospitalitycart1Linear.setVisibility(View.VISIBLE);
                }
            });
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




