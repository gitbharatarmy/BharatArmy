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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Activity.TravelPackageActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelMatchDetailPackageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    List<TravelModel> popularPackageList;
    Context mContext;
    String titleNameStr;

    public TravelMatchDetailPackageRecyclerAdapter(Context mContext, String titleNameStr, List<TravelModel> popularPackageList) {
        this.mContext = mContext;
        this.titleNameStr=titleNameStr;
        this.popularPackageList=popularPackageList;
    }

    static class MyItemViewHolder extends RecyclerView.ViewHolder {
        ImageView travel_popular_package_banner_img;
        //        LinearLayout recommended_Linear;
        LinearLayout book_linear;
        TextView packageplacename_txt,show_package_tour_description_txt;
        CardView package_cardclick;
        public MyItemViewHolder(View view) {
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

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView first_countryflag_image,second_countryflag_image;
        TextView title_txtView;
        HeaderViewHolder(View itemView) {
            super(itemView);
            first_countryflag_image=(ImageView)itemView.findViewById(R.id.first_countryflag_image);
            second_countryflag_image=(ImageView)itemView.findViewById(R.id.second_countryflag_image);

            title_txtView=(TextView)itemView.findViewById(R.id.title_txtView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.match_detailtitle_item, parent, false);
                return new TravelMatchDetailPackageRecyclerAdapter.HeaderViewHolder(v);
            default:
                    v = layoutInflater.inflate(R.layout.match_pacakage_item, parent, false);
                return new TravelMatchDetailPackageRecyclerAdapter.MyItemViewHolder(v);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            final TravelModel packagedetail = popularPackageList.get(position-1);

            Picasso.with(mContext)
                    .load(packagedetail.getTourImage())
                    .into(((MyItemViewHolder) holder).travel_popular_package_banner_img);

            ((MyItemViewHolder) holder).packageplacename_txt.setText(packagedetail.getTourCountryName());
            ((MyItemViewHolder) holder).show_package_tour_description_txt.setText(packagedetail.getTourDescription());

//        if (packagedetail.getbAImage().equalsIgnoreCase("true")){
//            holder.recommended_Linear.setVisibility(View.VISIBLE);
//        }else{
//            holder.recommended_Linear.setVisibility(View.GONE);
//        }

            ((MyItemViewHolder) holder).package_cardclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pacakgeIntent=new Intent(mContext, TravelPackageActivity.class);
                    pacakgeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(pacakgeIntent);
                }
            });

            ((MyItemViewHolder) holder).book_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookIntent=new Intent(mContext, TravelBookActivity.class);
                    bookIntent.putExtra("pacakgeName","Australian Double Dhamaka: Honeymoon and adventure at one shot");
                    bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(bookIntent);
                }
            });
        } else if (holder.getItemViewType() == HEADER) {
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png",((HeaderViewHolder) holder).first_countryflag_image,mContext);
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png",((HeaderViewHolder) holder).second_countryflag_image,mContext);
            ((HeaderViewHolder) holder).title_txtView.setText(titleNameStr);
        }

    }

    @Override

    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return popularPackageList.size() + 1;
    }


}



