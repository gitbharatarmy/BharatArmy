package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class BharatArmyStoriesAdapter extends RecyclerView.Adapter<BharatArmyStoriesAdapter.MyViewHolder> {
    Context mcontext;
    List<StoryDashboardData> storyDashboardDataList;
    MorestoryClick morestoryClick;
    private ArrayList<String> dataCheck = new ArrayList<String>();

    public BharatArmyStoriesAdapter(Context mContext, List<StoryDashboardData> storyDashboardDataList, MorestoryClick morestoryClick) {
        this.mcontext = mContext;
        this.storyDashboardDataList = storyDashboardDataList;
        this.morestoryClick = morestoryClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView header_txt, type_txt, army_story_header_txt, army_story_sub_txt,
                date_txt, views_txt, username_txt;
        ImageView type_img, profile_image,banner_img;


        public MyViewHolder(View view) {
            super(view);

            header_txt = (TextView) view.findViewById(R.id.header_txt);
            army_story_header_txt = (TextView) view.findViewById(R.id.army_story_header_txt);
            army_story_sub_txt = (TextView) view.findViewById(R.id.army_story_sub_txt);
            date_txt = (TextView) view.findViewById(R.id.date_txt);
            views_txt = (TextView) view.findViewById(R.id.views_txt);
            type_txt = (TextView) view.findViewById(R.id.type_txt);
            username_txt = (TextView) view.findViewById(R.id.username_txt);

            type_img = (ImageView) view.findViewById(R.id.type_img);
            profile_image = (ImageView) view.findViewById(R.id.profile_image);
            banner_img=(ImageView)view.findViewById(R.id.banner_img);

        }
    }


    @Override
    public BharatArmyStoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bharat_army_stories_list, parent, false);

        return new BharatArmyStoriesAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(BharatArmyStoriesAdapter.MyViewHolder holder, int position) {

        final StoryDashboardData storiesData = storyDashboardDataList.get(position);

        holder.header_txt.setText(storiesData.getCategoryName());
        holder.type_txt.setText(storiesData.getBASubCategoryName());
        holder.army_story_header_txt.setText(storiesData.getStoryTitle());
        holder.army_story_sub_txt.setText(storiesData.getShortDescription());
        holder.date_txt.setText(storiesData.getStrStoryAdded());
        holder.views_txt.setText(storiesData.getStrViewCount());
        holder.username_txt.setText(storiesData.getAuthorName());


        Picasso.with(mcontext)
                .load(storiesData.getStrThumbImageName())
                .placeholder(R.drawable.progress_animation)
                .into(holder.banner_img);
        Picasso.with(mcontext)
                .load(storiesData.getAuthorImageURL())
                .into(holder.profile_image);

        holder.army_story_header_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent webviewIntent=new Intent(mcontext, MoreStoryActivity.class);
                  webviewIntent.putExtra("Story Heading",storiesData.getStoryTitle());
                  webviewIntent.putExtra("StroyUrl",storiesData.getStoryWebURL());
                  webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  mcontext.startActivity(webviewIntent);
//                  ((Activity)  mcontext).overridePendingTransition(R.anim.slide_in_left,0);
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
        return storyDashboardDataList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}

