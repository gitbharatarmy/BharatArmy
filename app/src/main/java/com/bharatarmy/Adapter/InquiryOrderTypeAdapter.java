package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;

import java.util.ArrayList;

public class InquiryOrderTypeAdapter extends RecyclerView.Adapter<InquiryOrderTypeAdapter.ViewHolder> {

    private int mCount = 0;
Context mContext;
ArrayList<String> ordertypelist;
    public InquiryOrderTypeAdapter(Context mContext, ArrayList<String> ordertypelist) {
        this.mContext=mContext;
        this.ordertypelist=ordertypelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.order_item_list, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.inquiry_txt.setText(ordertypelist.get(position));
    }

    @Override
    public int getItemCount() {
        return ordertypelist.size();
    }

//    public void setCount(ArrayList<String> count) {
//        ordertypelist = count;
//        notifyDataSetChanged();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView inquiry_txt;
        public ViewHolder(final View itemView) {
            super(itemView);
//            inquiry_txt = itemView.findViewById(R.id.inquiry_txt);
        }
    }
}