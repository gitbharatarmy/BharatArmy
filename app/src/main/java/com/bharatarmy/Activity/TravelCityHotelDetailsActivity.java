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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bharatarmy.Adapter.CityHotelAmenitiesAdapter;
import com.bharatarmy.Adapter.CityHotelRoomTypeAdapter;
import com.bharatarmy.Adapter.FansPageAdapter;
import com.bharatarmy.Adapter.HotelPageAdapter;
import com.bharatarmy.Adapter.TravelPopularCityDetailAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelCityHotelDetailsBinding;
import com.bharatarmy.databinding.ActivityTravelCityhotelDetailsNewBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TravelCityHotelDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityTravelCityhotelDetailsNewBinding activityTravelCityHotelDetailsBinding;
    Context mContext;
    private MyHotelGalleryViewPagerAdapter myhotelViewPagerAdapter;
    private TextView[] dots;
    ArrayList<TravelModel> cityHotelGalleryList;

    HotelPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelCityHotelDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_cityhotel_details_new);
        mContext = TravelCityHotelDetailsActivity.this;

        init();
        setListiner();
        setDataList();
        setViewPage();
    }

    public void init() {
    }

    public void setListiner() {
        setSupportActionBar(activityTravelCityHotelDetailsBinding.htabToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        activityTravelCityHotelDetailsBinding.htabAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelCityHotelDetailsBinding.htabToolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityTravelCityHotelDetailsBinding.tablayoutLinear.setBackgroundColor(ContextCompat.getColor(mContext,R.color.splash_bg_color));
                    activityTravelCityHotelDetailsBinding.htabToolbar.setTitle("Taj Mahal Hotel");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelCityHotelDetailsBinding.htabCollapseToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelCityHotelDetailsBinding.htabCollapseToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelCityHotelDetailsBinding.htabCollapseToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityTravelCityHotelDetailsBinding.htabCollapseToolbar.setExpandedTitleGravity(Gravity.START);
                    activityTravelCityHotelDetailsBinding.titleLinear.setVisibility(View.GONE);

                    activityTravelCityHotelDetailsBinding.toolbarBottomLeftView.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    activityTravelCityHotelDetailsBinding.htabToolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelCityHotelDetailsBinding.tablayoutLinear.setBackgroundColor(ContextCompat.getColor(mContext,R.color.splash_bg_color));
                    activityTravelCityHotelDetailsBinding.htabToolbar.setTitle(" ");
                    activityTravelCityHotelDetailsBinding.titleLinear.setVisibility(View.VISIBLE);

                    activityTravelCityHotelDetailsBinding.toolbarBottomLeftView.setVisibility(View.GONE);
                    isShow = false;
                }

            }
        });


        activityTravelCityHotelDetailsBinding.htabViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                activityTravelCityHotelDetailsBinding.htabTabs));
        activityTravelCityHotelDetailsBinding.htabTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                activityTravelCityHotelDetailsBinding.htabViewpager.setCurrentItem(tab.getPosition());
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void setDataList() {
//        hotel gallery List
        cityHotelGalleryList = new ArrayList<TravelModel>();
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery1.jpeg", "Hotel1"));
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery2.jpeg", "Hotel1"));
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery3.jpeg", "Hotel1"));
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery4.jpeg", "Hotel1"));
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery5.jpeg", "Hotel1"));
        addBottomDots(0);
        myhotelViewPagerAdapter = new MyHotelGalleryViewPagerAdapter();
        activityTravelCityHotelDetailsBinding.hotelGalleryViewpager.setAdapter(myhotelViewPagerAdapter);
        activityTravelCityHotelDetailsBinding.hotelGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

    }


    public void setViewPage() {
        activityTravelCityHotelDetailsBinding.htabTabs.addTab(activityTravelCityHotelDetailsBinding.htabTabs.newTab().setText("Details"), true);
        activityTravelCityHotelDetailsBinding.htabTabs.addTab(activityTravelCityHotelDetailsBinding.htabTabs.newTab().setText("Room"));
        activityTravelCityHotelDetailsBinding.htabTabs.addTab(activityTravelCityHotelDetailsBinding.htabTabs.newTab().setText("Review"));

//        activityTravelCityHotelDetailsBinding.htabTabs.getTabAt(0).setIcon(R.drawable.ic_image_icon);
//        activityTravelCityHotelDetailsBinding.htabTabs.getTabAt(1).setIcon(R.drawable.ic_video_icon);
//        activityTravelCityHotelDetailsBinding.htabTabs.getTabAt(2).setIcon(R.drawable.ic_album_icon);
        activityTravelCityHotelDetailsBinding.htabTabs.setTabMode(TabLayout.MODE_FIXED);
        activityTravelCityHotelDetailsBinding.htabTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new HotelPageAdapter(getSupportFragmentManager(), activityTravelCityHotelDetailsBinding.htabTabs.getTabCount());
//Adding adapter to pager
        activityTravelCityHotelDetailsBinding.htabViewpager.setOffscreenPageLimit(0);
        activityTravelCityHotelDetailsBinding.htabViewpager.setAdapter(adapter);

    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_linear:
                TravelCityHotelDetailsActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelCityHotelDetailsActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[cityHotelGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < cityHotelGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        activityTravelCityHotelDetailsBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityTravelCityHotelDetailsBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return activityTravelCityHotelDetailsBinding.hotelGalleryViewpager.getCurrentItem() + i;
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


    public class MyHotelGalleryViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        ImageView hotel_gallery_image;

        public MyHotelGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.hotel_gallery_viewpage, container, false);

            hotel_gallery_image = (ImageView) view.findViewById(R.id.hotel_gallery_image);

            Utils.setImageInImageView(cityHotelGalleryList.get(position).getCityHotelAmenitiesImage(), hotel_gallery_image, mContext);

            Log.d("HotelGalleeryAdapter : ", cityHotelGalleryList.get(position).getCityHotelAmenitiesImage());
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return cityHotelGalleryList.size();
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
