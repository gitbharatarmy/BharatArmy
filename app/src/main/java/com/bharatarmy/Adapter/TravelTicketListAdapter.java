package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TravelTicketListAdapter extends RecyclerView.Adapter<TravelTicketListAdapter.MyViewHolder> {
    Context mContext;
    List<TravelDetailModel> travelTicketList;

    public TravelTicketListAdapter(Context mContext, List<TravelDetailModel> travelTicketList) {
        this.mContext = mContext;
        this.travelTicketList = travelTicketList;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       ImageView ticket_img;
       TextView ticket_name,ticket_price_txt;

        public MyViewHolder(View view) {
            super(view);

           ticket_img=(ImageView)view.findViewById(R.id.ticket_img);
            ticket_name=(TextView)view.findViewById(R.id.ticket_txt);
            ticket_price_txt=(TextView)view.findViewById(R.id.ticket_price_txt);

        }
    }


    @Override
    public TravelTicketListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_item_list, parent, false);

        return new TravelTicketListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelTicketListAdapter.MyViewHolder holder, int position) {
        final TravelDetailModel ticketListDetail = travelTicketList.get(position);

       Picasso.with(mContext)
               .load(ticketListDetail.getTicketType_img())
               .placeholder(R.drawable.progress_animation)
               .into(holder.ticket_img);

       holder.ticket_name.setText(ticketListDetail.getTicketName());
       holder.ticket_price_txt.setText("£ " + String.valueOf(ticketListDetail.getTicketprice()));
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
        return travelTicketList.size();
    }
}

