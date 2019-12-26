package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Activity.RegisterInterestActivityNew;
import com.bharatarmy.Activity.StoryDetailActivity;
import com.bharatarmy.Activity.TravelBookActivity;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.CardviewpagerItemBinding;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    List<HomeTemplateDetailModel> homeTemplateDetailModelList;
    private Context mContext;


    public MyPagerAdapter(List<HomeTemplateDetailModel> homeTemplateDetailModelList, Context mContext) {
        this.homeTemplateDetailModelList = homeTemplateDetailModelList;
        this.mContext = mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        CardviewpagerItemBinding cardviewpagerItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.cardviewpager_item,parent,false);

        if (homeTemplateDetailModelList.get(position).getSecondHeaderType().equals(1)) {
            cardviewpagerItemBinding.registerInterestLinear.setVisibility(View.VISIBLE);
            cardviewpagerItemBinding.bookPackageLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.bookPackagebuttonLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.bookBgPageLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.bottomGradiantView.setVisibility(View.GONE);
            cardviewpagerItemBinding.newsBgPageLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.newsPageLinear.setVisibility(View.GONE);
            Utils.setImageInImageView(homeTemplateDetailModelList.get(position).getSecondHeaderImageUrl(), cardviewpagerItemBinding.image, mContext);
            cardviewpagerItemBinding.registerInterestLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(mContext, RegisterInterestActivityNew.class);
                    registerIntent.putExtra("tournamentId", homeTemplateDetailModelList.get(position).getReferenceId());
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(registerIntent);
                }
            });
        } else if (homeTemplateDetailModelList.get(position).getSecondHeaderType().equals(2)) {
            cardviewpagerItemBinding.registerInterestLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.newsBgPageLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.newsPageLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.bookPackageLinear.setVisibility(View.VISIBLE);
            cardviewpagerItemBinding.bookBgPageLinear.setVisibility(View.VISIBLE);
            cardviewpagerItemBinding.bookPackagebuttonLinear.setVisibility(View.VISIBLE);
            cardviewpagerItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
//            Utils.setImageInImageView(homeTemplateDetailModelList.get(position),background_image,mContext);
            cardviewpagerItemBinding.bookPackageItemDescTxt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderSubText());
            cardviewpagerItemBinding.bookPackageItemHeadingTxt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderText());
            cardviewpagerItemBinding.bookPackageTxt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderButtonText());
            cardviewpagerItemBinding.bookPackageTxt.setTextColor(Color.parseColor(homeTemplateDetailModelList.get(position).getSecondHaderButtonFontColor()));
            GradientDrawable bgShape = (GradientDrawable)cardviewpagerItemBinding.bookPackagebuttonLinear.getBackground();
            bgShape.setColor(Color.parseColor(homeTemplateDetailModelList.get(position).getSecondHaderButtonColor()));
            if (homeTemplateDetailModelList.get(position).getSecondHeaderTag().equalsIgnoreCase("")) {
                cardviewpagerItemBinding.bookPackageTagTxt.setVisibility(View.GONE);
            } else {
                cardviewpagerItemBinding.bookPackageTagTxt.setVisibility(View.VISIBLE);
                cardviewpagerItemBinding.bookPackageTagTxt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderTag());

            }

            cardviewpagerItemBinding.bookPackagebuttonLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(mContext, TravelBookActivity.class);
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(registerIntent);
                }
            });
        } else if (homeTemplateDetailModelList.get(position).getSecondHeaderType().equals(3)) {

            cardviewpagerItemBinding.registerInterestLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.bookPackageLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.bookPackagebuttonLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.bookBgPageLinear.setVisibility(View.GONE);
            cardviewpagerItemBinding.bottomGradiantView.setVisibility(View.GONE);
            cardviewpagerItemBinding.newsBgPageLinear.setVisibility(View.VISIBLE);
            cardviewpagerItemBinding.newsPageLinear.setVisibility(View.VISIBLE);

            cardviewpagerItemBinding.newsItemDescTxt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderSubText());
            cardviewpagerItemBinding.newsItemHeadingTxt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderText());
            cardviewpagerItemBinding.newsViewMoreTxt.setText(Html.fromHtml("<u>"+homeTemplateDetailModelList.get(position).getSecondHeaderButtonText()+"</u>"));
            cardviewpagerItemBinding.newsViewMoreTxt.setTextColor(Color.parseColor(homeTemplateDetailModelList.get(position).getSecondHaderButtonFontColor()));

            cardviewpagerItemBinding.newsViewMoreLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webviewIntent = new Intent(mContext, StoryDetailActivity.class);
                    webviewIntent.putExtra("Story Heading", homeTemplateDetailModelList.get(position).getStoryDetails().getStoryTitle());
                    webviewIntent.putExtra("StroyUrl",  homeTemplateDetailModelList.get(position).getStoryDetails().getStoryWebURL());
                    webviewIntent.putExtra("StoryCategorytype",  homeTemplateDetailModelList.get(position).getStoryDetails().getBASubCategoryName());
                    webviewIntent.putExtra("StoryAuthor",  homeTemplateDetailModelList.get(position).getStoryDetails().getAuthorImageURL());
                    webviewIntent.putExtra("StoryHeaderImg",  homeTemplateDetailModelList.get(position).getStoryDetails().getStrThumbImageName());
                    webviewIntent.putExtra("StoryId",  homeTemplateDetailModelList.get(position).getStoryDetails().getBAStoryId());
                    webviewIntent.putExtra("StoryauthorId",  homeTemplateDetailModelList.get(position).getStoryDetails().getAuthorId());
                    webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(webviewIntent);
                }
            });
        }
        parent.addView(cardviewpagerItemBinding.getRoot());
        return cardviewpagerItemBinding.getRoot();
    }

    @Override
    public int getCount() {
        return homeTemplateDetailModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }
}


