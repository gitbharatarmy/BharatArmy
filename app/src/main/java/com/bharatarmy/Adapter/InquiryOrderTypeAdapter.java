package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.InquiryOrderTypeModel;
import com.bharatarmy.R;

import java.util.ArrayList;
import java.util.List;

public class InquiryOrderTypeAdapter extends RecyclerView.Adapter<InquiryOrderTypeAdapter.ViewHolder> {

    private int mCount = 0;
Context mContext;
    List<InquiryOrderTypeModel> inquiryOrderTypeModelList;
    public InquiryOrderTypeAdapter(Context mContext, List<InquiryOrderTypeModel> inquiryOrderTypeModelList) {
        this.mContext=mContext;
        this.inquiryOrderTypeModelList=inquiryOrderTypeModelList;
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
        InquiryOrderTypeModel detail=inquiryOrderTypeModelList.get(position);
        holder.inquiry_txt.setText(detail.getLabel());

        holder.pending_statuslinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.pendingclosetxt.isShown()){
                    holder.pendingclosetxt.setVisibility(View.GONE);
                    holder.inquiry_txt.setTextColor(mContext.getResources().getColor(R.color.unselected_view));
                    holder.pending_statuslinear.setBackground(null);
                    detail.setClickstatus("1");
                }else{
                    holder.pending_statuslinear.setBackground(mContext.getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    holder.inquiry_txt.setTextColor(mContext.getResources().getColor(R.color.skip_color));
                    holder.pendingclosetxt.setVisibility(View.VISIBLE);
                    detail.setClickstatus("0");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return inquiryOrderTypeModelList.size();
    }

//    public void setCount(ArrayList<String> count) {
//        ordertypelist = count;
//        notifyDataSetChanged();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView inquiry_txt,pendingclosetxt;
        LinearLayout pending_statuslinear;

        public ViewHolder(final View itemView) {
            super(itemView);
            inquiry_txt = itemView.findViewById(R.id.pending_txt);
            pending_statuslinear=(LinearLayout)itemView.findViewById(R.id.pending_statuslinear);
            pendingclosetxt=(TextView)itemView.findViewById(R.id.pendingclosetxt);
        }
    }
}