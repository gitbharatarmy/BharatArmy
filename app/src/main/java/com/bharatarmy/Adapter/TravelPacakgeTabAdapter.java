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
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelPacakagePlaceDetailActivity;
import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MainPageChildItemBinding;
import com.bharatarmy.databinding.TravelpacakgeTabItemBinding;

import java.util.List;

public class TravelPacakgeTabAdapter extends RecyclerView.Adapter<TravelPacakgeTabAdapter.MyViewHolder> {
    Context mContext;
    List<TravelDetailModel> travelPacakgeTabList;
    String tabPosition;

    public TravelPacakgeTabAdapter(Context mContext, List<TravelDetailModel> travelPacakgeTabList) {
        this.mContext = mContext;
        this.travelPacakgeTabList = travelPacakgeTabList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TravelpacakgeTabItemBinding travelpacakgeTabItemBinding;

//        CardView day1, day2, day3;
//        TextView visitPlace_title_txt, visitplace_desc_txt, time_txt, secondplaceVisit_title_txt, secondplacevisit_description_txt,
//                thirdplacevisit_title_txt, thirdplacevisit_desc_txt;
//        ImageView secondplace_visitimage;

        public MyViewHolder(TravelpacakgeTabItemBinding travelpacakgeTabItemBinding) {
            super(travelpacakgeTabItemBinding.getRoot());


            this.travelpacakgeTabItemBinding=travelpacakgeTabItemBinding;

//            day1 = (CardView) view.findViewById(R.id.day1);
//            day2 = (CardView) view.findViewById(R.id.day2);
//            day3 = (CardView) view.findViewById(R.id.day3);
//
//
//            visitPlace_title_txt = (TextView) view.findViewById(R.id.visitPlace_title_txt);
//            visitplace_desc_txt = (TextView) view.findViewById(R.id.visitplace_desc_txt);
//            time_txt = (TextView) view.findViewById(R.id.time_txt);
//            secondplaceVisit_title_txt = (TextView) view.findViewById(R.id.secondplaceVisit_title_txt);
//            secondplacevisit_description_txt = (TextView) view.findViewById(R.id.secondplacevisit_description_txt);
//            thirdplacevisit_title_txt = (TextView) view.findViewById(R.id.thirdplacevisit_title_txt);
//            thirdplacevisit_desc_txt = (TextView) view.findViewById(R.id.thirdplacevisit_desc_txt);
//
//            secondplace_visitimage = (ImageView) view.findViewById(R.id.secondplace_visitimage);
        }
    }


    @Override
    public TravelPacakgeTabAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelpacakgeTabItemBinding travelpacakgeTabItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.travelpacakge_tab_item, parent, false);
        return new TravelPacakgeTabAdapter.MyViewHolder(travelpacakgeTabItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelPacakgeTabAdapter.MyViewHolder holder, int position) {
        TravelDetailModel detailModel = travelPacakgeTabList.get(position);

        if (detailModel.getTemplateTypeId().equals(1)) {
            holder.travelpacakgeTabItemBinding.day1.setVisibility(View.VISIBLE);
            holder.travelpacakgeTabItemBinding.day2.setVisibility(View.GONE);
            holder.travelpacakgeTabItemBinding.day3.setVisibility(View.GONE);

            holder.travelpacakgeTabItemBinding.visitPlaceTitleTxt.setText(detailModel.getItemText());
            holder.travelpacakgeTabItemBinding.visitplaceDescTxt.setText(detailModel.getItemDescription());
            Utils.setImageInImageView(detailModel.getImageURL(),holder.travelpacakgeTabItemBinding.backgroundImage,mContext);

        } else if (detailModel.getTemplateTypeId().equals(2)) {
            holder.travelpacakgeTabItemBinding.day1.setVisibility(View.GONE);
            holder.travelpacakgeTabItemBinding.day2.setVisibility(View.VISIBLE);
            holder.travelpacakgeTabItemBinding.day3.setVisibility(View.GONE);

            Utils.setImageInImageView(detailModel.getImageURL(), holder.travelpacakgeTabItemBinding.secondplaceVisitimage, mContext);

            if (!detailModel.getTimetToVisit().equalsIgnoreCase("")) {
                holder.travelpacakgeTabItemBinding.timeTxt.setText(detailModel.getTimetToVisit());
            } else {
                holder.travelpacakgeTabItemBinding.timeTxt.setVisibility(View.GONE);
            }

            holder.travelpacakgeTabItemBinding.secondplaceVisitTitleTxt.setText(detailModel.getItemText());
            holder.travelpacakgeTabItemBinding.secondplacevisitDescriptionTxt.setText(detailModel.getItemDescription());

            holder.travelpacakgeTabItemBinding.secondplaceVisitTitleTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pacakgeplaceIntent = new Intent(mContext, TravelPacakagePlaceDetailActivity.class);
                    pacakgeplaceIntent.putExtra("placeName", detailModel.getItemText());
                    pacakgeplaceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(pacakgeplaceIntent);
                }
            });

        } else if (detailModel.getTemplateTypeId().equals(3)) {
            holder.travelpacakgeTabItemBinding.day1.setVisibility(View.GONE);
            holder.travelpacakgeTabItemBinding.day2.setVisibility(View.GONE);
            holder.travelpacakgeTabItemBinding.day3.setVisibility(View.VISIBLE);


            holder.travelpacakgeTabItemBinding.thirdplacevisitTitleTxt.setText(detailModel.getItemText());
            holder.travelpacakgeTabItemBinding.thirdplacevisitDescTxt.setText(detailModel.getItemDescription());
            Utils.setImageInImageView(detailModel.getImageURL(), holder.travelpacakgeTabItemBinding.card3Image, mContext);
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
        return travelPacakgeTabList.size();
    }
}

