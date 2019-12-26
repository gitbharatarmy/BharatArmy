package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelPackageActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelPopularPackageAdapter extends RecyclerView.Adapter<TravelPopularPackageAdapter.MyViewHolder> {
    Context mContext;
    List<TravelModel> popularPackageList;


    public TravelPopularPackageAdapter(Context mContext, List<TravelModel> popularPackageList) {
        this.mContext=mContext;
        this.popularPackageList=popularPackageList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView travel_popular_package_banner_img;
//        LinearLayout recommended_Linear;
        LinearLayout book_linear;
        TextView packageplacename_txt,show_package_tour_description_txt;
        CardView package_cardclick;
        public MyViewHolder(View view) {
            super(view);
            travel_popular_package_banner_img=(ImageView)view.findViewById(R.id.travel_popular_package_banner_img);
            packageplacename_txt=(TextView)view.findViewById(R.id.packageplacename_txt);
//            show_package_tour_subtitle_txt=(TextView)view.findViewById(R.id.show_package_tour_subtitle_txt);
            show_package_tour_description_txt=(TextView)view.findViewById(R.id.show_package_tour_description_txt);
//            recommended_Linear=(LinearLayout)view.findViewById(R.id.recommended_Linear);
            package_cardclick=(CardView)view.findViewById(R.id.package_cardclick);

            book_linear=(LinearLayout)view.findViewById(R.id.book_linear);
        }
    }


    @Override
    public TravelPopularPackageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_popularcity_pakage_item, parent, false);//popular_package_item
        return new TravelPopularPackageAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelPopularPackageAdapter.MyViewHolder holder, int position) {
        final TravelModel packagedetail = popularPackageList.get(position);

        Picasso.with(mContext)
                .load(packagedetail.getTourImage())
                .into(holder.travel_popular_package_banner_img);

        holder.packageplacename_txt.setText(packagedetail.getTourCountryName());
        holder.show_package_tour_description_txt.setText(packagedetail.getTourDescription());

//        if (packagedetail.getbAImage().equalsIgnoreCase("true")){
//            holder.recommended_Linear.setVisibility(View.VISIBLE);
//        }else{
//            holder.recommended_Linear.setVisibility(View.GONE);
//        }

        holder.package_cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pacakgeIntent=new Intent(mContext, TravelPackageActivity.class);
                pacakgeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(pacakgeIntent);
            }
        });

        holder.book_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookIntent=new Intent(mContext, TravelBookActivity.class);
                bookIntent.putExtra("pacakgeName","Australian Double Dhamaka: Honeymoon and adventure at one shot");
                bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(bookIntent);
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
        return popularPackageList.size();
    }
}

