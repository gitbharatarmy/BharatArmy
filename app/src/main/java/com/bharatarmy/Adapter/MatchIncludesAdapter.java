package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchAddrcyItemBinding;

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

        MatchAddrcyItemBinding matchAddrcyItemBinding;

        public MyViewHolder(MatchAddrcyItemBinding matchAddrcyItemBinding) {
            super(matchAddrcyItemBinding.getRoot());

            this.matchAddrcyItemBinding=matchAddrcyItemBinding;


        }
    }


    @Override
    public MatchIncludesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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

        } else {
            holder.matchAddrcyItemBinding.hospitalityLinear.setVisibility(View.VISIBLE);
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




