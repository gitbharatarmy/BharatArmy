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

public class TravelTransferListAdapter extends RecyclerView.Adapter<TravelTransferListAdapter.MyViewHolder> {
    Context mContext;
    List<TravelDetailModel> transferArrayList;


    public TravelTransferListAdapter(Context mContext, List<TravelDetailModel> transferArray) {
        this.mContext=mContext;
        this.transferArrayList=transferArray;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
      TextView transfer_txt;

        public MyViewHolder(View view) {
            super(view);
            transfer_txt=(TextView)view.findViewById(R.id.transfer_txt);

        }
    }


    @Override
    public TravelTransferListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transfer_item, parent, false);

        return new TravelTransferListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelTransferListAdapter.MyViewHolder holder, int position) {
        final TravelDetailModel transferArrayDetail = transferArrayList.get(position);



        holder.transfer_txt.setText(transferArrayDetail.getTransfer_name());
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
        return transferArrayList.size();
    }
}

