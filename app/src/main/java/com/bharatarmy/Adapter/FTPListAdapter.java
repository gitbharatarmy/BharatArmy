package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.FTPDetailsActivity;
import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FTPListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    public List<ImageDetailModel> mItemList;
    Context mContext;
    com.bharatarmy.Interfaces.image_click image_click;
    private ArrayList<String> dataCheck;


    public FTPListAdapter(Context mContext, List<ImageDetailModel> itemList, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click=image_click;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ftp_item_list, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView header_txt, army_upcoming_header_txt, army_upcoming_sub_txt,
                date_txt, location_txt, army_upcoming_pra_txt, linear1_txt, linear2_txt, linear3_txt;
        ImageView banner_img, header_img, date_img, location_img;
        LinearLayout lable_linear;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            header_txt = (TextView) itemView.findViewById(R.id.header_txt);
            army_upcoming_header_txt = (TextView) itemView.findViewById(R.id.army_upcoming_header_txt);
            army_upcoming_sub_txt = (TextView) itemView.findViewById(R.id.army_upcoming_sub_txt);
            date_txt = (TextView) itemView.findViewById(R.id.date_txt);
            location_txt = (TextView) itemView.findViewById(R.id.location_txt);
            army_upcoming_pra_txt = (TextView) itemView.findViewById(R.id.army_upcoming_pra_txt);
            linear1_txt = (TextView) itemView.findViewById(R.id.linear1_txt);
            linear2_txt = (TextView) itemView.findViewById(R.id.linear2_txt);
            linear3_txt = (TextView) itemView.findViewById(R.id.linear3_txt);

            banner_img = (ImageView) itemView.findViewById(R.id.banner_img);
            header_img = (ImageView) itemView.findViewById(R.id.header_img);
            date_img = (ImageView) itemView.findViewById(R.id.date_img);
            location_img = (ImageView) itemView.findViewById(R.id.location_img);

            lable_linear = (LinearLayout) itemView.findViewById(R.id.lable_linear);


        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewHolder, final int position) {

        final ImageDetailModel detail= mItemList.get(position);
        viewHolder.header_txt.setText(detail.getCategoryName());
        viewHolder.army_upcoming_header_txt.setText(detail.getTourName());
        viewHolder.army_upcoming_sub_txt.setText(detail.getSubCategoryId());
        viewHolder.location_txt.setText(detail.getTourLocation());
        viewHolder.army_upcoming_pra_txt.setText(detail.getTourShortDescription());

        viewHolder.army_upcoming_header_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ftpIntent =new Intent(mContext, FTPDetailsActivity.class);
                ftpIntent.putExtra("ftpmaintitle",detail.getTourName());
                ftpIntent.putExtra("ftpdate",detail.getDateAdded());
                ftpIntent.putExtra("ftpshortdesc",detail.getTourShortDescription());
                ftpIntent.putExtra("ftptourdesc",detail.getTourDescription());
                ftpIntent.putExtra("ftpbannerimg",detail.getFutureTourThumbImageURL());
                mContext.startActivity(ftpIntent);
            }
        });

//        Picasso.with(mContext)
//                .load(detail.getFutureTourThumbImageURL())
//                .placeholder(R.drawable.progress_animation)
//                .into(viewHolder.banner_img);

        Utils.setImageInImageView(detail.getFutureTourImageURL(),viewHolder.banner_img,mContext);

        if (!detail.getStr1().equalsIgnoreCase("")) {
            viewHolder.linear1_txt.setVisibility(View.VISIBLE);
            viewHolder.linear1_txt.setText(detail.getStr1());
        } else {
            viewHolder.linear1_txt.setVisibility(View.GONE);
        }

        if (!detail.getStr2().equalsIgnoreCase("")) {
            if (!detail.getStr2().equalsIgnoreCase("1")) {
                viewHolder.linear2_txt.setVisibility(View.VISIBLE);
                viewHolder.linear2_txt.setText(detail.getStr2());
            } else {
                viewHolder.linear2_txt.setVisibility(View.GONE);
            }
        } else {
            viewHolder.linear2_txt.setVisibility(View.GONE);
        }

        if (!detail.getStr3().equalsIgnoreCase("")) {
            if (!detail.getStr3().equalsIgnoreCase("1")) {
                viewHolder.linear3_txt.setVisibility(View.VISIBLE);
                viewHolder.linear3_txt.setText(detail.getStr3());
            } else {
                viewHolder.linear3_txt.setVisibility(View.GONE);
            }
        } else {
            viewHolder.linear3_txt.setVisibility(View.GONE);
        }


    }

    public ArrayList<String> getData() {
        return dataCheck;
    }


    public void addMoreDataToList(List<ImageDetailModel> result){
        mItemList.addAll(result);
        notifyDataSetChanged();
    }
}



