package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bharatarmy.Adapter.TravelMatchScheduleHospitalityDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleHotelDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchSchedulePackageDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleSightSeeingDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleStadiumDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleTicketDetailAdapter;
import com.bharatarmy.CallOneAnimationCartAddItemMethod;
import com.bharatarmy.CallTwoAnimationCartAddItemMethod;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchScheduleDetailBinding;
import com.bharatarmy.databinding.TravelScheduleBannerListItemBinding;
import com.google.android.material.appbar.AppBarLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchScheduleDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchScheduleDetailBinding activityTravelMatchScheduleDetailBinding;
    Context mContext;

    /*viewpager control*/
    private MyTravelMatchScheduleDetailGalleryViewPagerAdapter myTravelMatchScheduleDetailGalleryViewPagerAdapter;
    ArrayList<TravelModel> travelmatchscheduledetailGalleryList;
    private ImageView[] dots;

    /*Schedule detail variable*/
    String firstcounrtyNameStr, secondcountryNameStr, firstcountryFlagStr, secondcountryFlagStr, groundLocationStr, matchdatescheduleStr;

    /*Match Sub list variable*/
    ArrayList<TravelModel> travelticketList;
    ArrayList<TravelModel> travelhotelList;
    ArrayList<TravelModel> travelpackageList;
    ArrayList<TravelModel> travelhospitalityList;
    ArrayList<TravelModel> travelsightseeingList;
    ArrayList<TravelModel> travelstadiumList;

    /*Schedule detail adapter*/
    TravelMatchScheduleTicketDetailAdapter travelMatchScheduleTicketDetailAdapter;
    TravelMatchScheduleHotelDetailAdapter travelMatchScheduleHotelDetailAdapter;
    TravelMatchSchedulePackageDetailAdapter travelMatchSchedulePackageDetailAdapter;
    TravelMatchScheduleHospitalityDetailAdapter travelMatchScheduleHospitalityDetailAdapter;
    TravelMatchScheduleSightSeeingDetailAdapter travelMatchScheduleSightSeeingDetailAdapter;
    TravelMatchScheduleStadiumDetailAdapter travelMatchScheduleStadiumDetailAdapter;

    /*Adapter Layout Manger variable*/
    GridLayoutManager gridLayoutManager, gridLayoutManager1;
    StaggeredGridLayoutManager staggeredGridLayoutManager, staggeredGridLayoutManager1;
    LinearLayoutManager sightseenLayoutManager, stadiumLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchScheduleDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_schedule_detail);
        mContext = TravelMatchScheduleDetailActivity.this;

        init();
        setListiner();
        setDataValue();
        setticketsListValue();
    }

    public void init() {
        setSupportActionBar(activityTravelMatchScheduleDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        Utils.addCartItemCount(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt);

        /*Get Intent value */
        firstcounrtyNameStr = getIntent().getStringExtra("firstcountryName");
        firstcountryFlagStr = getIntent().getStringExtra("firstscountryFlag");
        secondcountryNameStr = getIntent().getStringExtra("secondcountryName");
        secondcountryFlagStr = getIntent().getStringExtra("secondcountryFlag");
        groundLocationStr = getIntent().getStringExtra("groundLocation");
        matchdatescheduleStr = getIntent().getStringExtra("matchdatetime");



        /*set the value of schedule detail*/
        activityTravelMatchScheduleDetailBinding.matchDateTimeTxt.setText(matchdatescheduleStr);
        activityTravelMatchScheduleDetailBinding.toolbarMatchDateTimeTxt.setText(matchdatescheduleStr);
        activityTravelMatchScheduleDetailBinding.matchGroundLocationTxt.setText(groundLocationStr);
        activityTravelMatchScheduleDetailBinding.toolbarMatchGroundLocationTxt.setText(groundLocationStr);
        activityTravelMatchScheduleDetailBinding.firstCountryNameTxt.setText(firstcounrtyNameStr);
        activityTravelMatchScheduleDetailBinding.toolbarFirstCountryNameTxt.setText(firstcounrtyNameStr);
        activityTravelMatchScheduleDetailBinding.secondCountryNameTxt.setText(secondcountryNameStr);
        activityTravelMatchScheduleDetailBinding.toolbarSecondCountryNameTxt.setText(secondcountryNameStr);

        if (firstcountryFlagStr != null) {
            Utils.setImageInImageView(firstcountryFlagStr, activityTravelMatchScheduleDetailBinding.firstCountryflagImage, mContext);
        }
        if (secondcountryFlagStr != null) {
            Utils.setImageInImageView(secondcountryFlagStr, activityTravelMatchScheduleDetailBinding.secondCountryflagImage, mContext);
        }


    }

    public void setListiner() {
        activityTravelMatchScheduleDetailBinding.backImg.setOnClickListener(this);

        activityTravelMatchScheduleDetailBinding.ticketsMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.hotelMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.packageMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.hospitalityMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.siteseenMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.locationDetailLinear.setOnClickListener(this);

        activityTravelMatchScheduleDetailBinding.tabAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelMatchScheduleDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));

                    activityTravelMatchScheduleDetailBinding.toolbarMatchCountryDetailLinear.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleDetailBinding.toolbarMatchDateTimeTxt.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleDetailBinding.toolbarLocationDetailLinear.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout.setVisibility(View.VISIBLE);

                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setTitle("");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setExpandedTitleGravity(Gravity.START);
                    activityTravelMatchScheduleDetailBinding.toolbarBottomView.setVisibility(View.GONE);
                    activityTravelMatchScheduleDetailBinding.toolbarBottomLeftView.setVisibility(View.GONE);

                    isShow = true;
                } else if (isShow) {
                    activityTravelMatchScheduleDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelMatchScheduleDetailBinding.toolbarMatchCountryDetailLinear.setVisibility(View.GONE);
                    activityTravelMatchScheduleDetailBinding.toolbarMatchDateTimeTxt.setVisibility(View.GONE);
                    activityTravelMatchScheduleDetailBinding.toolbarLocationDetailLinear.setVisibility(View.GONE);
                    activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout.setVisibility(View.GONE);

                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setTitle("");
                    activityTravelMatchScheduleDetailBinding.toolbarBottomLeftView.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleDetailBinding.toolbarBottomView.setVisibility(View.GONE);
                    isShow = false;
                }

            }
        });

        activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addcartItemIntent = new Intent(mContext, CartItemShowActivity.class);
                startActivity(addcartItemIntent);
            }
        });
    }

    public void setDataValue() {
        //        travel match schedule detail gallery List
        travelmatchscheduledetailGalleryList = new ArrayList<TravelModel>();
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Hotel1"));
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/banner_app_02.jpg", "Hotel2"));
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Hotel1"));
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/banner_app_02.jpg", "Hotel2"));

        addBottomDots(0);
        myTravelMatchScheduleDetailGalleryViewPagerAdapter = new MyTravelMatchScheduleDetailGalleryViewPagerAdapter();
        activityTravelMatchScheduleDetailBinding.travelMatchScheduleDetailGalleryViewpager.setAdapter(myTravelMatchScheduleDetailGalleryViewPagerAdapter);
        activityTravelMatchScheduleDetailBinding.travelMatchScheduleDetailGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tickets_main_linear:
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                setticketsListValue();
                break;
            case R.id.package_main_linear:
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                setpackageListValue();
                break;
            case R.id.hotel_main_linear:
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                sethotelListValue();
                break;
            case R.id.siteseen_main_linear:
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                setsightseensListValue();
                break;
            case R.id.hospitality_main_linear:
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                sethospitalityListValue();
                break;

            case R.id.location_detail_linear:
                Intent locationIntent = new Intent(mContext, TravelMatchStadiumViewActivity.class);
                startActivity(locationIntent);
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new ImageView[travelmatchscheduledetailGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < travelmatchscheduledetailGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.gray)));
        }

        activityTravelMatchScheduleDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);

            dots[i].setImageResource(R.drawable.unselected_new);
            dots[i].setPadding(0, 0, 10, 0);
            activityTravelMatchScheduleDetailBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setImageResource(R.drawable.selected_new);
//            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
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

    public class MyTravelMatchScheduleDetailGalleryViewPagerAdapter extends PagerAdapter {
        public MyTravelMatchScheduleDetailGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            TravelScheduleBannerListItemBinding travelScheduleBannerListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.travel_schedule_banner_list_item, parent, false);


            Utils.setImageInImageView(travelmatchscheduledetailGalleryList.get(position).getCityHotelAmenitiesImage(), travelScheduleBannerListItemBinding.backgroundImage, mContext);

            parent.addView(travelScheduleBannerListItemBinding.getRoot());

            return travelScheduleBannerListItemBinding.getRoot();
        }

        @Override
        public int getCount() {
            return travelmatchscheduledetailGalleryList.size();
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


    public void setticketsListValue() {
        travelticketList = new ArrayList<TravelModel>();
        travelticketList.add(new TravelModel("http://devenv.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Ticket",
                "Category A", "Lorem Ipsum is simply dummy text.", "Extra 10% off* with Hotel.",
                "₹ 700", "1", "ticket", "0"));


        travelticketList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/e35eee60-7.jpg", "Ticket",
                "Category B", "Lorem Ipsum is simply dummy text of the printing.", "",
                "₹ 650", "1", "ticket", "0"));


        travelticketList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/5c6783ff-d.jpg", "Ticket",
                "Category D", "Lorem Ipsum is simply dummy text of the printing.", "Extra 20% off* with Hotel.",
                "₹ 400", "3", "ticket", "0"));

        travelticketList.add(new TravelModel("http://devenv.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Ticket",
                "Category C", "Lorem Ipsum is simply dummy text of the printing.", "",
                "₹ 550", "2", "ticket", "0"));


        travelMatchScheduleTicketDetailAdapter = new TravelMatchScheduleTicketDetailAdapter(mContext, travelticketList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                int addTocartposition = travelMatchScheduleTicketDetailAdapter.adptercartAddPosition();
                Utils.animationAdd(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout,
                        activityTravelMatchScheduleDetailBinding.toolbar, activityTravelMatchScheduleDetailBinding.addcarticon.cartImage,
                        activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt,
                        activityTravelMatchScheduleDetailBinding.htabMaincontent,
                        null, null, addTocartposition, "travelmatchscheduleticket");
            }
        }, new image_click() {
            @Override
            public void image_more_click() {
                Utils.removeCartItemCount(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt);
            }
        });
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(staggeredGridLayoutManager);
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchScheduleTicketDetailAdapter);

    }

    public void sethotelListValue() {
        travelhotelList = new ArrayList<>();
        travelhotelList.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCtpFvQulc666pbmJhIdodJxCSFhPlACieZOemcK3qb5w95acjRQ&s",
                "PAN PACIFIC PERTH", "Bharat Army Experience Package with Accommodation Stay.",
                4, "₹ 10000", "Geelong", "Australia"));

        travelhotelList.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDXmTnFIhLvlcFmZzc4BXBQURhFFV5J8bKoCsIK3e6QM74BnEj&s",
                "FOUR POINTS BY SHERATON PERTH", "Bharat Army Experience Package with Accommodation Stay.",
                3, "₹ 9000", "Sydney", "India"));


        travelMatchScheduleHotelDetailAdapter = new TravelMatchScheduleHotelDetailAdapter(mContext, travelhotelList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                int addTocartposition = travelMatchScheduleHotelDetailAdapter.adptercartAddPosition();
                Utils.animationAdd(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout,
                        activityTravelMatchScheduleDetailBinding.toolbar, activityTravelMatchScheduleDetailBinding.addcarticon.cartImage,
                        activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt,
                        activityTravelMatchScheduleDetailBinding.htabMaincontent,
                        null, null, addTocartposition, "travelmatchschedulehotel");
            }
        }, new image_click() {
            @Override
            public void image_more_click() {
                Utils.removeCartItemCount(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(mLayoutManager);
        activityTravelMatchScheduleDetailBinding.detailRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchScheduleHotelDetailAdapter);
        travelMatchScheduleHotelDetailAdapter.notifyDataSetChanged();
    }

    public void setpackageListValue() {
        travelpackageList = new ArrayList<TravelModel>();
        travelpackageList.add(new TravelModel("xyz", "Australian Double Dhamaka: Honeymoon & adventure at one shot", AppConfiguration.IMAGE_URL + "aus1.jpg",
                "Jet Boat Ride from Main Beach.Bungy jumping from 165 ft distance at Cairns.Great Barrier Reef Experience",
                "1k", "900", "true"));

        travelpackageList.add(new TravelModel("xyz", "Explore the best of Australia with your soulmate", AppConfiguration.IMAGE_URL + "aus2.jpg",
                "Grand Barossa Valley Day Tour.Happy day out at the Kangaroo Island with a fun tour amidst natural highlights.Eureka Skydeck 88.Sydney Harbour Jet Boat Thrill Ride: 30 Minutes ",
                "2k", "500", "false"));

        travelpackageList.add(new TravelModel("xyz", "Celebrate love in the Australian lands", AppConfiguration.IMAGE_URL + "aus3.jpg",
                "Delicious dinner cruise during sunset at Sydney Harbour exposed to amazing vistas and views around the arena.Super Pass: Film World, Sea World & Wet'n'Wild Water World.Morning Whale Watching Cruise.Car hire for Great Ocean Road",
                "5k", "1000", "false"));

        travelMatchSchedulePackageDetailAdapter = new TravelMatchSchedulePackageDetailAdapter(mContext, travelpackageList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(layoutManager);
        activityTravelMatchScheduleDetailBinding.detailRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchSchedulePackageDetailAdapter);
        travelMatchSchedulePackageDetailAdapter.notifyDataSetChanged();
    }

    public void sethospitalityListValue() {
        travelhospitalityList = new ArrayList<TravelModel>();

        travelhospitalityList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "Hospitality Category",
                "The Pavilion", "The Pavilion is the ultimate hospitality experience that will deliver a sophisticated, yet relaxed environment to be shared with family, friends or business associates.",
                "", "₹ 475", "3", "hospitality", "0"));

        travelhospitalityList.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "",
                "Private Suites", "Private Suites provide the ultimate hospitality experience.",
                "Extra 20% off* with Hotel.", "₹ 600", "3", "hospitality", "0"));

        travelhospitalityList.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "",
                "Open Air Boxes", "Open Air Boxes are a casual entertainment option providing you and your guests everything you need for an effortless day of cricket enjoyment.",
                "Extra 20% off* with Hotel.", "₹ 650", "1", "hospitality", "0"));

        travelhospitalityList.add(new TravelModel("https://i0.wp.com/www.perrygroup.com/wp-content/uploads/2016/01/service-pic3-1.jpg", "",
                "Club 20/20", "Club 20/20 packages suit those seeking an informal entertainment experience that still provides hospitality with outstanding service.",
                "", "₹ 750", "2", "hospitality", "0"));


        travelMatchScheduleHospitalityDetailAdapter = new TravelMatchScheduleHospitalityDetailAdapter(mContext, travelhospitalityList);
        staggeredGridLayoutManager1 = new StaggeredGridLayoutManager(2, 1);
        staggeredGridLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(staggeredGridLayoutManager1);
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchScheduleHospitalityDetailAdapter);

        travelMatchScheduleHospitalityDetailAdapter.notifyDataSetChanged();
    }

    public void setsightseensListValue() {
        travelsightseeingList = new ArrayList<TravelModel>();

        travelsightseeingList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Pinnacle_Tour.jpg",
                "1 Barrack Street Jetty, Perth WA 6000, Australia", "Pinnacle Tour",
                "Discover the beauty of Western Australia with ADAMS Pinnacle Tours. We have over 35 years of experience in the industry.",
                "Perth"));

        travelsightseeingList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Kings_Park_and_Botanic_Garden_Tour.jpg",
                "Fraser Ave, Perth WA 6005, Australia", "Kings Park and Botanic Garden",
                "The park is a mixture of grassed parkland, botanical gardens and natural bushland on Mount Eliza with two-thirds of the grounds conserved as native bushland.",
                "Perth"));

        travelsightseeingList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Swan_River_Tour.jpg",
                "Western Australia, Australia", "Swan River",
                "The Swan River is a river in the south west of Western Australia. Its Aboriginal Noongar name is the Derbarl Yerrigan.",
                "Perth"));

        travelsightseeingList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Sydney_Opera_House.jpg",
                "Bennelong Point, Sydney NSW 2000, Australia", "Sydney Opera House",
                "The Sydney Opera House is a multi-venue performing arts centre at Sydney Harbour in Sydney, New South Wales, Australia.",
                "Sydney"));


        travelMatchScheduleSightSeeingDetailAdapter = new TravelMatchScheduleSightSeeingDetailAdapter(mContext, travelsightseeingList);
        sightseenLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(sightseenLayoutManager);
        activityTravelMatchScheduleDetailBinding.detailRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchScheduleSightSeeingDetailAdapter);
        travelMatchScheduleSightSeeingDetailAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        Utils.addCartItemCount(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt);
        super.onResume();
    }


}
