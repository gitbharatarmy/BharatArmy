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

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RelatedVideoAdapter extends RecyclerView.Adapter<RelatedVideoAdapter.MyViewHolder> {
    Context mcontext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;

    public RelatedVideoAdapter(Context mContext, List<ImageDetailModel> relatedVideoList, image_click morestoryClick) {
        this.mcontext = mContext;
        this.relatedVideoList = relatedVideoList;
        this.morestoryClick = morestoryClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView related_video_img;
        TextView video_size_txt, show_video_title_txt;
        LinearLayout ba_recommended_linear;

        public MyViewHolder(View view) {
            super(view);
            related_video_img = (ImageView) view.findViewById(R.id.related_video_img);
            video_size_txt = (TextView) view.findViewById(R.id.video_size_txt);
            show_video_title_txt = (TextView) view.findViewById(R.id.show_video_title_txt);
            ba_recommended_linear = (LinearLayout) view.findViewById(R.id.ba_recommended_linear);
        }
    }


    @Override
    public RelatedVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.related_video_adapter_item, parent, false);

        return new RelatedVideoAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RelatedVideoAdapter.MyViewHolder holder, int position) {

        final ImageDetailModel relatedVideoDetail = relatedVideoList.get(position);

        if (relatedVideoList.get(position).equals(0)) {
            holder.ba_recommended_linear.setVisibility(View.VISIBLE);
        }else{
            holder.ba_recommended_linear.setVisibility(View.GONE);
        }

        Utils.setImageInImageView(relatedVideoDetail.getVideoImageURL(), holder.related_video_img, mcontext);


        holder.video_size_txt.setText(relatedVideoDetail.getVideoLength());
        holder.show_video_title_txt.setText(relatedVideoDetail.getVideoName());


        holder.related_video_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck = new ArrayList<String>();
                dataCheck.add(relatedVideoDetail.getVideoFileURL() + "|" + relatedVideoDetail.getVideoName());
                morestoryClick.image_more_click();
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
        return relatedVideoList.size();
    }


    public ArrayList<String> getData() {
        return dataCheck;
    }
}


