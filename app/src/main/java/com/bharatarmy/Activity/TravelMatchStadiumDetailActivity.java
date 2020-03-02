package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bharatarmy.Adapter.StadiumDetailRelatedMatchesAdapter;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityTravelMatchStadiumDetailBinding;
import com.bharatarmy.databinding.DetailPageGalleryPagerListItemBinding;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TravelMatchStadiumDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchStadiumDetailBinding activityTravelMatchStadiumDetailBinding;
    Context mContext;
    /*Stadium Detail variable*/
    String stadiumNameStr, stadiumDescriptionStr;

    /*    stadium inclusion list and adapter*/
    ArrayList<TravelModel> travelStadiumInclusionList;

    /* stadium related other matches*/
    StadiumDetailRelatedMatchesAdapter stadiumDetailRelatedMatchesAdapter;
    List<HomeTemplateDetailModel> travelStadiumOtherMatchesList;

    /*Use for stadium gallery*/
    private MyStadiumDetailGalleryPagerAdapter myStadiumDetailGalleryPagerAdapter;
    //    DetailPageGalleryPagerListItemBinding detailPageGalleryPagerListItemBinding;
    private TextView[] dots;
    ArrayList<TravelModel> stadiumDetailGalleryList;

    int videoplayposition = 0;
    String videopathStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchStadiumDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_stadium_detail);
        mContext = TravelMatchStadiumDetailActivity.this;
        init();
        setListiner();

    }

    public void init() {
        stadiumNameStr = getIntent().getStringExtra("stadiumName");
        stadiumDescriptionStr = getIntent().getStringExtra("stadiumDescription");
//        activityTravelMatchStadiumDetailBinding.shimmerViewContainerGallery.startShimmerAnimation();
        activityTravelMatchStadiumDetailBinding.shimmerViewContainerMain.startShimmerAnimation();
//        activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager.setVisibility(View.GONE);
//        activityTravelMatchStadiumDetailBinding.viewPagerDotlinear.setVisibility(View.GONE);
        activityTravelMatchStadiumDetailBinding.mainDetailLinear.setVisibility(View.GONE);


        //        stadium gallery List
        stadiumDetailGalleryList = new ArrayList<TravelModel>();
        stadiumDetailGalleryList.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-Perth-Banner.png?v=1559025136", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://www.cricket.com.au/~/-/media/Brightcove/Cricket%20Australia/2017/11/21/New-Perth-Stadium-still.ashx?w=1600?w=1600&h=1200", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://i.ytimg.com/vi/kd-qKUXcjY0/maxresdefault.jpg", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Rose_bowl3.JPG/300px-Rose_bowl3.JPG", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/Media/e83c8278-f1f8-4aa6-b618-1d2302b80416-MP4_20191010_163200.mp4", "Video"));
        stadiumDetailGalleryList.add(new TravelModel("https://images.immediate.co.uk/production/volatile/sites/3/2019/05/GettyImages-809641044-3d448c5.jpg?quality=45&resize=620,413", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTOoK5jxwSYxPaujnLqPAFoEBH2yOOpWWVxQ2Qlom1pGQqCttwq", "Image"));

        addBottomDots(0);
        myStadiumDetailGalleryPagerAdapter = new MyStadiumDetailGalleryPagerAdapter();
        activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager.setAdapter(myStadiumDetailGalleryPagerAdapter);
        activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

        GetStadiumDetailListData();
    }

    public void setListiner() {
        setSupportActionBar(activityTravelMatchStadiumDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        activityTravelMatchStadiumDetailBinding.backImg.setOnClickListener(this);
        activityTravelMatchStadiumDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    activityTravelMatchStadiumDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    activityTravelMatchStadiumDetailBinding.toolbarHeaderLinear.setVisibility(View.VISIBLE);

                    isShow = true;
                } else if (isShow) {
                    activityTravelMatchStadiumDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setTitle(" ");
                    activityTravelMatchStadiumDetailBinding.toolbarHeaderLinear.setVisibility(View.GONE);
                    isShow = false;
                }

            }
        });


    }

    // Api calling GetStadiumDetailListData
    public void GetStadiumDetailListData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), TravelMatchStadiumDetailActivity.this);
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<TravelDataModel> call = api.getHospitalityList("http://www.mocky.io/v2/5e53b9282e00007c002dae2b");

        call.enqueue(new Callback<TravelDataModel>() {
            @Override
            public void onResponse(Call<TravelDataModel> call, retrofit2.Response<TravelDataModel> response) {

                if (response.body().getFixturesData() != null && response.body().getRelatedHospitalityData() != null) {
                    travelStadiumOtherMatchesList = response.body().getFixturesData();
                    setDataList();
                }
            }

            @Override
            public void onFailure(Call<TravelDataModel> call, Throwable t) {
                Log.d("WatchList Error:", t.getLocalizedMessage());
            }
        });


    }

    public void setDataList() {
        activityTravelMatchStadiumDetailBinding.shimmerViewContainerMain.stopShimmerAnimation();
        activityTravelMatchStadiumDetailBinding.shimmerViewContainerMain.setVisibility(View.GONE);
//        activityTravelMatchStadiumDetailBinding.shimmerViewContainerGallery.stopShimmerAnimation();
//        activityTravelMatchStadiumDetailBinding.shimmerViewContainerGallery.setVisibility(View.GONE);
//        activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager.setVisibility(View.VISIBLE);
        activityTravelMatchStadiumDetailBinding.viewPagerDotlinear.setVisibility(View.VISIBLE);
        activityTravelMatchStadiumDetailBinding.mainDetailLinear.setVisibility(View.VISIBLE);
        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setVisibility(View.VISIBLE);

        activityTravelMatchStadiumDetailBinding.stadiumNameTxt.setText(stadiumNameStr);
        activityTravelMatchStadiumDetailBinding.stadiumDescTxt.setText(stadiumDescriptionStr);




        //    inclusion list
        travelStadiumInclusionList = new ArrayList<TravelModel>();
        travelStadiumInclusionList.add(new TravelModel(1, "Live entertainment throughout the day"));
        travelStadiumInclusionList.add(new TravelModel(1, "Extensive menu with in room live chef sation"));
        travelStadiumInclusionList.add(new TravelModel(1, "5 hour premium beverage package"));
        travelStadiumInclusionList.add(new TravelModel(1, "Premium seating"));
        travelStadiumInclusionList.add(new TravelModel(1, "Access to win money can't buy experiences"));


        for (int i = 0; i < travelStadiumInclusionList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.hospitality_inclusion_list, null);

            TextView txt = (TextView) view.findViewById(R.id.inclusion_txt);

            txt.setText(travelStadiumInclusionList.get(i).getMatchteamVenues());

            activityTravelMatchStadiumDetailBinding.inclusionLinear.addView(view);
        }

        /*OtherMatch list*/

        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setVisibility(View.VISIBLE);
        stadiumDetailRelatedMatchesAdapter = new StadiumDetailRelatedMatchesAdapter(mContext, travelStadiumOtherMatchesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setLayoutManager(layoutManager);
        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setAdapter(stadiumDetailRelatedMatchesAdapter);

    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);//position

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelMatchStadiumDetailActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelMatchStadiumDetailActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[stadiumDetailGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < stadiumDetailGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.heading_bg)));
        }


        activityTravelMatchStadiumDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityTravelMatchStadiumDetailBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager.getCurrentItem() + i;
    }

    public class MyStadiumDetailGalleryPagerAdapter extends PagerAdapter {

        public MyStadiumDetailGalleryPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            DetailPageGalleryPagerListItemBinding detailPageGalleryPagerListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.detail_page_gallery_pager_list_item, parent, false);

            if (stadiumDetailGalleryList.get(position).getCityHotelAmenitiesName().equalsIgnoreCase("Image")) {
                detailPageGalleryPagerListItemBinding.detailGalleryImage.setVisibility(View.VISIBLE);
                detailPageGalleryPagerListItemBinding.baVideoRlv.setVisibility(View.GONE);
                Utils.setImageInImageView(stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage(), detailPageGalleryPagerListItemBinding.detailGalleryImage, mContext);

                Log.d("HotelGalleeryAdapter : ", stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage());
            } else {
                detailPageGalleryPagerListItemBinding.detailGalleryImage.setVisibility(View.GONE);
                detailPageGalleryPagerListItemBinding.baVideoRlv.setVisibility(View.VISIBLE);

                videopathStr = stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage();
                Utils.setImageInImageView("http://devenv.bharatarmy.com//Docs/Media/Thumb/a983346f-b0ac-4a49-91c6-f7196efd4629-1570705345206.jpg", detailPageGalleryPagerListItemBinding.videoThumbnailImage, mContext);

                detailPageGalleryPagerListItemBinding.startPauseMediaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.videoPlayinSlider(mContext, detailPageGalleryPagerListItemBinding.baVideo, detailPageGalleryPagerListItemBinding.imageProgress,
                                detailPageGalleryPagerListItemBinding.startPauseMediaButton, detailPageGalleryPagerListItemBinding.volmueVideoButton,
                                detailPageGalleryPagerListItemBinding.muteVideoButton, detailPageGalleryPagerListItemBinding.videoThumbnailImage,
                                detailPageGalleryPagerListItemBinding.volmueLinear, videopathStr, videoplayposition);
                    }
                });
                detailPageGalleryPagerListItemBinding.volmueLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.voluesetting(mContext, detailPageGalleryPagerListItemBinding.volmueVideoButton, detailPageGalleryPagerListItemBinding.muteVideoButton);
                    }
                });
                detailPageGalleryPagerListItemBinding.baVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (detailPageGalleryPagerListItemBinding.baVideo.isPlaying()) {
                            videoplayposition = detailPageGalleryPagerListItemBinding.baVideo.getCurrentPosition();
                            detailPageGalleryPagerListItemBinding.baVideo.pause();
                            Log.d("videorunposition :", "" + videoplayposition);
                            detailPageGalleryPagerListItemBinding.startPauseMediaButton.setVisibility(View.VISIBLE);
                            detailPageGalleryPagerListItemBinding.volmueLinear.setVisibility(View.GONE);
                        } else {
                            Utils.videoPlayinSlider(mContext, detailPageGalleryPagerListItemBinding.baVideo, detailPageGalleryPagerListItemBinding.imageProgress,
                                    detailPageGalleryPagerListItemBinding.startPauseMediaButton, detailPageGalleryPagerListItemBinding.volmueVideoButton,
                                    detailPageGalleryPagerListItemBinding.muteVideoButton, detailPageGalleryPagerListItemBinding.videoThumbnailImage,
                                    detailPageGalleryPagerListItemBinding.volmueLinear, videopathStr, videoplayposition);
                        }
                    }
                });

            }


            parent.addView(detailPageGalleryPagerListItemBinding.getRoot());

            return detailPageGalleryPagerListItemBinding.getRoot();
        }

        @Override
        public int getCount() {
            return stadiumDetailGalleryList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }



}
