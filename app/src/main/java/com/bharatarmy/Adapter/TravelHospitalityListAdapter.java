package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelHospitalityListAdapter extends RecyclerView.Adapter<TravelHospitalityListAdapter.MyViewHolder> {
    Context mContext;
    List<TravelDetailModel> hospitalityArrayList;
    int count = 0;
    int addremoveValue = 0;


    public TravelHospitalityListAdapter(Context mContext, List<TravelDetailModel> hospitalityArray) {
        this.mContext = mContext;
        this.hospitalityArrayList = hospitalityArray;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView hospitality_img, inclusion_icon;
        TextView hopitality_txt, hopitality_stdium_txt, view_more_txt, hopitality_price_txt, quantity_txt, hopitality_inclusion_txt, inclusion_txt;
        Button cart_minus_btn, cart_plus_btn, add_to_cart_btn;
        LinearLayout cart_plus_linear,cart_minus_linear;

        public MyViewHolder(View view) {
            super(view);

            hospitality_img = (ImageView) view.findViewById(R.id.hospitality_img);
            inclusion_icon = (ImageView) view.findViewById(R.id.inclusion_icon);

            hopitality_txt = (TextView) view.findViewById(R.id.hopitality_txt);
            hopitality_stdium_txt = (TextView) view.findViewById(R.id.hopitality_stdium_txt);
            view_more_txt = (TextView) view.findViewById(R.id.view_more_txt);
            hopitality_price_txt = (TextView) view.findViewById(R.id.hopitality_price_txt);
            quantity_txt = (TextView) view.findViewById(R.id.quantity_txt);
            hopitality_inclusion_txt = (TextView) view.findViewById(R.id.hopitality_inclusion_txt);
            inclusion_txt = (TextView) view.findViewById(R.id.inclusion_txt);


            add_to_cart_btn = (Button) view.findViewById(R.id.add_to_cart_btn);

            cart_minus_linear=(LinearLayout)view.findViewById(R.id.cart_minus_linear);
            cart_plus_linear=(LinearLayout)view.findViewById(R.id.cart_plus_linear);
        }
    }


    @Override
    public TravelHospitalityListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospitality_item, parent, false);

        return new TravelHospitalityListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final TravelHospitalityListAdapter.MyViewHolder holder, int position) {
        final TravelDetailModel hospitalityArrayDetail = hospitalityArrayList.get(position);

        Picasso.with(mContext)
                .load(hospitalityArrayDetail.getHospitality_img())
                .placeholder(R.drawable.progress_animation)
                .into(holder.hospitality_img);

        holder.hopitality_txt.setText(hospitalityArrayDetail.getHospitality_name());

        Picasso.with(mContext)
                .load(hospitalityArrayDetail.getHospitality_inclusion_icon())
                .placeholder(R.drawable.progress_animation)
                .into(holder.inclusion_icon);

        holder.hopitality_stdium_txt.setText(hospitalityArrayDetail.getHospitality_stdium());
        holder.hopitality_price_txt.setText("£ " + String.valueOf(hospitalityArrayDetail.getHospitality_price()));
        holder.hopitality_inclusion_txt.setText(hospitalityArrayDetail.getHospitality_inclusion());
        holder.inclusion_txt.setText(hospitalityArrayDetail.getHospitality_inclusion_type());


        holder.cart_plus_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addremoveValue = addremoveValue + 1;
                Log.d("LinearplusValue",""+addremoveValue);
                holder.quantity_txt.setText(String.valueOf(addremoveValue));
            }
        });
        holder.cart_minus_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addremoveValue = addremoveValue - 1;
                Log.d("LinearminusValue",""+addremoveValue);
                if (addremoveValue!=0) {
                    holder.quantity_txt.setText(String.valueOf(addremoveValue));
                }else{
                    addremoveValue=0;
                    holder.quantity_txt.setText("");
                }
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
        return hospitalityArrayList.size();
    }
}

