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
import com.bharatarmy.databinding.MatchticketsItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MatchTicketsAdapter extends RecyclerView.Adapter<MatchTicketsAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> matchTicketsArray;
    String includesNameStr;



    public MatchTicketsAdapter(Context mContext, ArrayList<String> matchTicketsArray, String includesName) {
        this.mContext=mContext;
        this.matchTicketsArray=matchTicketsArray;
        this.includesNameStr=includesName;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout ticketLinear, addtoticketscart1Linear,
//                removetoticketscart1Linear;
//        ImageView ticketscart_addimage;

MatchticketsItemBinding matchticketsItemBinding;

        public MyViewHolder(MatchticketsItemBinding matchticketsItemBinding) {
            super(matchticketsItemBinding.getRoot());

            this.matchticketsItemBinding=matchticketsItemBinding;


        }
    }


    @Override
    public MatchTicketsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MatchticketsItemBinding matchticketsItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.matchtickets_item,parent,false);
        return new MatchTicketsAdapter.MyViewHolder(matchticketsItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(MatchTicketsAdapter.MyViewHolder holder, int position) {

        if (includesNameStr.equalsIgnoreCase("tickets")) {
            holder.matchticketsItemBinding.ticketLinear.setVisibility(View.VISIBLE);

            holder.matchticketsItemBinding.addtoticketscart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.matchticketsItemBinding.ticketscartAddimage.setImageResource(R.drawable.fill_selected_checkbox);
                    holder.matchticketsItemBinding.ticketLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
                    holder.matchticketsItemBinding.removetoticketscart1Linear.setVisibility(View.VISIBLE);
                    holder.matchticketsItemBinding.addtoticketscart1Linear.setVisibility(View.GONE);
                }
            });
            holder.matchticketsItemBinding.removetoticketscart1Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.matchticketsItemBinding.ticketscartAddimage.setImageResource(R.drawable.ic_blank_check);
                    holder.matchticketsItemBinding.ticketLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_child_curveshape));
                    holder.matchticketsItemBinding.removetoticketscart1Linear.setVisibility(View.GONE);
                    holder.matchticketsItemBinding.addtoticketscart1Linear.setVisibility(View.VISIBLE);
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
        return matchTicketsArray.size();
    }


}





