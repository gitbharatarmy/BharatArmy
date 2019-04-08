package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bharatarmy.Interfaces.history_detail_click;
import com.bharatarmy.R;

import java.util.List;

public class HistoryMainListAdapter extends RecyclerView.Adapter<HistoryMainListAdapter.MyViewHolder> {
    Context mcontext;
    List<String> invoiceList;
    history_detail_click history_detail_click;

    public HistoryMainListAdapter(Context mContext, List<String> invoiceList, history_detail_click history_detail_click) {
        this.mcontext = mContext;
        this.invoiceList = invoiceList;
        this.history_detail_click = history_detail_click;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView historyDdetailimg;


        public MyViewHolder(View view) {
            super(view);
            historyDdetailimg = (ImageView) view.findViewById(R.id.history_detail_img);


        }
    }


    @Override
    public HistoryMainListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_main_list, parent, false);

        return new HistoryMainListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(HistoryMainListAdapter.MyViewHolder holder, int position) {

        holder.historyDdetailimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_detail_click.gethistorymoredetailClick();
            }
        });

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
        return invoiceList.size();
    }


}


