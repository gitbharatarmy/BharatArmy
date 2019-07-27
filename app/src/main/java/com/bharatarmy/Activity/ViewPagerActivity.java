package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bharatarmy.AlphaPageTransformer;
import com.bharatarmy.Fragment.PacakgeFragment;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityViewPagerBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
ActivityViewPagerBinding activityViewPagerBinding;
Context mContext;
MyPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewPagerBinding= DataBindingUtil.setContentView(this,R.layout.activity_view_pager);
        mContext=ViewPagerActivity.this;


        activityViewPagerBinding.viewpagerImageswitcher.setImageResource(R.drawable.main1);

        pagerAdapter = new MyPagerAdapter();
        activityViewPagerBinding.cardViewPager.setOffscreenPageLimit(3);
        activityViewPagerBinding.cardViewPager.setPageMargin(40);
        activityViewPagerBinding.cardViewPager.setPageTransformer(true,new AlphaPageTransformer());
        activityViewPagerBinding.cardViewPager.setAdapter(pagerAdapter);

        activityViewPagerBinding.cardViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==1){
                    activityViewPagerBinding.viewpagerImageswitcher.setImageResource(R.drawable.main1);
                }else{
                    activityViewPagerBinding.viewpagerImageswitcher.setImageResource(R.drawable.main2);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class MyPagerAdapter extends PagerAdapter {

        private Context mContext;

        public MyPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.cardviewpager_item, container, false);

            return inflate;
        }

        @Override
        public int getCount() {
            return 3;
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


}
