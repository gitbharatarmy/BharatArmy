package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Activity.StoryAuthorActivity;
import com.bharatarmy.Activity.StoryDetailActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.BharatArmyStoriesListNewBinding;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class BharatArmyStoriesAdapter extends RecyclerView.Adapter<BharatArmyStoriesAdapter.MyViewHolder> {
    Context mcontext;
    List<StoryDashboardData> storyDashboardDataList;
    image_click image_click;
    private ArrayList<String> dataCheck = new ArrayList<String>();

    public BharatArmyStoriesAdapter(Context mContext, List<StoryDashboardData> storyDashboardDataList, image_click image_click) {
        this.mcontext = mContext;
        this.storyDashboardDataList = storyDashboardDataList;
        this.image_click = image_click;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

BharatArmyStoriesListNewBinding bharatArmyStoriesListNewBinding;

        public MyViewHolder(BharatArmyStoriesListNewBinding bharatArmyStoriesListNewBinding) {
            super(bharatArmyStoriesListNewBinding.getRoot());

            this.bharatArmyStoriesListNewBinding=bharatArmyStoriesListNewBinding;

        }
    }


    @Override
    public BharatArmyStoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BharatArmyStoriesListNewBinding bharatArmyStoriesListNewBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.bharat_army_stories_list_new,parent,false);
        return new BharatArmyStoriesAdapter.MyViewHolder(bharatArmyStoriesListNewBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(BharatArmyStoriesAdapter.MyViewHolder holder, int position) {

        final StoryDashboardData storiesData = storyDashboardDataList.get(position);

//        holder.bharatArmyStoriesListNewBinding.header_txt.setText(storiesData.getCategoryName());
        holder.bharatArmyStoriesListNewBinding.typeTxt.setText(storiesData.getBASubCategoryName());
        holder.bharatArmyStoriesListNewBinding.armyStoryHeaderTxt.setText(storiesData.getStoryTitle());
        holder.bharatArmyStoriesListNewBinding.armyStorySubTxt.setText(storiesData.getShortDescription());
        holder.bharatArmyStoriesListNewBinding.dateTxt.setText(storiesData.getStrStoryAdded());
        holder.bharatArmyStoriesListNewBinding.viewsTxt.setText(storiesData.getStrViewCount());
        holder.bharatArmyStoriesListNewBinding.usernameTxt.setText(storiesData.getAuthorName());

        Utils.setImageInImageView(storiesData.getStrThumbImageName(),holder.bharatArmyStoriesListNewBinding.bannerImg,mcontext);
//        Picasso.with(mcontext)
//                .load(storiesData.getStrThumbImageName())
//                .placeholder(R.drawable.progress_animation)
//                .into(holder.bharatArmyStoriesListNewBinding.bannerImg);
        Utils.setImageInImageView(storiesData.getAuthorImageURL(),holder.bharatArmyStoriesListNewBinding.profileImage,mcontext);
//        Picasso.with(mcontext)
//                .load(storiesData.getAuthorImageURL())
//                .into(holder.bharatArmyStoriesListNewBinding.profileImage);



        holder.bharatArmyStoriesListNewBinding.storyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent webviewIntent=new Intent(mcontext, StoryDetailActivity.class);
                webviewIntent.putExtra("Story Heading", storiesData.getStoryTitle());
                webviewIntent.putExtra("StroyUrl", storiesData.getStoryWebURL());
                webviewIntent.putExtra("StoryCategorytype",storiesData.getBASubCategoryName());
                webviewIntent.putExtra("StoryAuthor",storiesData.getAuthorImageURL());
                webviewIntent.putExtra("StoryHeaderImg",storiesData.getStrThumbImageName());
                webviewIntent.putExtra("StoryId", storiesData.getBAStoryId());
                webviewIntent.putExtra("StoryauthorId", storiesData.getAuthorId());
                  webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  mcontext.startActivity(webviewIntent);
//                  ((Activity)  mcontext).overridePendingTransition(R.anim.slide_in_left,0);
            }
        });
        holder.bharatArmyStoriesListNewBinding.authorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent authorIntent=new Intent(mcontext, StoryAuthorActivity.class);
                authorIntent.putExtra("StoryauthorId", storiesData.getAuthorId());
                authorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(authorIntent);
            }
        });
        holder.bharatArmyStoriesListNewBinding.typeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck=new ArrayList<>();
                dataCheck.add(storiesData.getStrCategories()+"|"+storiesData.getBASubCategoryName());
                image_click.image_more_click();
            }
        });

        holder.bharatArmyStoriesListNewBinding.shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, AppConfiguration.SHARETEXT);
                shareIntent.putExtra(Intent.EXTRA_TEXT, storiesData.getStoryURL() + "\n\n" + AppConfiguration.SHARETEXT);
                shareIntent.setType("text/plain");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(Intent.createChooser(shareIntent, "Share It"));
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

