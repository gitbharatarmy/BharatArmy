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
        LinearLayout ticketLinear, addtoticketscart1Linear,
                removetoticketscart1Linear;
        ImageView ticketscart_addimage;



        public MyViewHolder(View view) {
            super(view);

            ticketLinear = (LinearLayout) view.findViewById(R.id.ticketLinear);
            addtoticketscart1Linear = (LinearLayout) view.findViewById(R.id.addtoticketscart1Linear);
            removetoticketscart1Linear = (LinearLayout) view.findViewById(R.id.removetoticketscart1Linear);
            ticketscart_addimage = (ImageView) view.findViewById(R.id.ticketscart_addimage);

        }
    }


    @Override
    public MatchTicketsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matchtickets_item, parent, false);

        return new MatchTicketsAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(MatchTicketsAdapter.MyViewHolder holder, int position) {

        if (includesNameStr.equalsIgnoreCase("tickets")) {
            holder.ticketLinear.setVisibility(View.VISIBLE);

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





