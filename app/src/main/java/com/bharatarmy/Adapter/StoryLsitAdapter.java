package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StoryLsitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    public List<ImageDetailModel> mItemList;
    Context mContext;
    image_click image_click;
    private ArrayList<String> dataCheck;


    public StoryLsitAdapter(Context mContext, List<ImageDetailModel> itemList, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click=image_click;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item_list, parent, false);
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

        public TextView header_txt, type_txt, army_story_header_txt, army_story_sub_txt,
                date_txt, views_txt, username_txt;
        ImageView type_img, profile_image,banner_img;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            header_txt = (TextView) itemView.findViewById(R.id.header_txt);
            army_story_header_txt = (TextView) itemView.findViewById(R.id.army_story_header_txt);
            army_story_sub_txt = (TextView) itemView.findViewById(R.id.army_story_sub_txt);
            date_txt = (TextView) itemView.findViewById(R.id.date_txt);
            views_txt = (TextView) itemView.findViewById(R.id.views_txt);
            type_txt = (TextView) itemView.findViewById(R.id.type_txt);
            username_txt = (TextView) itemView.findViewById(R.id.username_txt);

            type_img = (ImageView) itemView.findViewById(R.id.type_img);
            profile_image = (ImageView) itemView.findViewById(R.id.profile_image);
            banner_img=(ImageView)itemView.findViewById(R.id.banner_img);


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
        viewHolder.type_txt.setText(detail.getBASubCategoryName());
        viewHolder.army_story_header_txt.setText(detail.getStoryTitle());
        viewHolder.army_story_sub_txt.setText(detail.getShortDescription());
        viewHolder.date_txt.setText(detail.getStrStoryAdded());
        viewHolder.views_txt.setText(detail.getStrViewCount());
        viewHolder.username_txt.setText(detail.getAuthorName());


        Picasso.with(mContext)
                .load(detail.getStrThumbImageName())
                .placeholder(R.drawable.progress_animation)
                .into(viewHolder.banner_img);
        Picasso.with(mContext)
                .load(detail.getAuthorImageURL())
                .into(viewHolder.profile_image);

        viewHolder.army_story_header_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webviewIntent=new Intent(mContext, MoreStoryActivity.class);
                webviewIntent.putExtra("Story Heading",detail.getStoryTitle());
                webviewIntent.putExtra("StroyUrl",detail.getStoryWebURL());
                webviewIntent.putExtra("whereTocome","storylistadp");
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);
//                  ((Activity)  mcontext).overridePendingTransition(R.anim.slide_in_left,0);
            }
        });


    }

    public ArrayList<String> getData() {
        return dataCheck;
    }


    public void addMoreDataToList(List<ImageDetailModel> result){
        mItemList.addAll(result);
        notifyDataSetChanged();
    }
}


