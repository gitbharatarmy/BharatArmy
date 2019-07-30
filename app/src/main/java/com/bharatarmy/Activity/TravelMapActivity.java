package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMapBinding;

import java.util.ArrayList;
import java.util.List;

public class TravelMapActivity extends AppCompatActivity {
    ActivityTravelMapBinding travelMapBinding;
    Context mContext;
    private MyHotelGalleryViewPagerAdapter myhotelViewPagerAdapter;
    private TextView[] dots;
    ArrayList<TravelModel> cityHotelGalleryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travelMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_map);

        mContext=TravelMapActivity.this;
//        hotel gallery List
                cityHotelGalleryList = new ArrayList<TravelModel>();
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery1.jpeg", "Hotel1"));
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery2.jpeg", "Hotel1"));
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery3.jpeg", "Hotel1"));
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery4.jpeg", "Hotel1"));
        cityHotelGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hotelgallery5.jpeg", "Hotel1"));
        addBottomDots(0);
        myhotelViewPagerAdapter = new MyHotelGalleryViewPagerAdapter();
        travelMapBinding.hotelGalleryViewpager.setAdapter(myhotelViewPagerAdapter);
        travelMapBinding.hotelGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);
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


        travelMapBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            travelMapBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return travelMapBinding.hotelGalleryViewpager.getCurrentItem() + i;
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


//            Picasso.with(mContext)
//                    .load(cityHotelGalleryList.get(position).getPosition())
//                    .placeholder(R.drawable.progress_animation)
//                    .into(hotel_gallery_image);
            Utils.setImageInImageView(cityHotelGalleryList.get(position).getCityHotelRoomGallery(), hotel_gallery_image, mContext);

            Log.d("HotelGalleeryAdapter : ", cityHotelGalleryList.get(position).getCityHotelRoomGallery());
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