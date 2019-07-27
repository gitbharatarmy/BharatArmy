package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bharatarmy.Adapter.MainPageChildAdapter;
import com.bharatarmy.Adapter.MainPageDealsAdapter;
import com.bharatarmy.Fragment.HomeFragment;
import com.bharatarmy.Fragment.PacakgeFragment;
import com.bharatarmy.Fragment.ProductTourFragment;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.MyScrollView;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityDemoBinding;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;


//https://github.com/OCNYang/PageTransformerHelp refre
public class DemoActivity extends AppCompatActivity {

    ActivityDemoBinding activityDemoBinding;
    Context mContext;
    ArrayList<TravelModel> homedetailList;
    MainPageDealsAdapter mainPageDealsAdapter;
//    MyImageViewPagerAdapter myImageViewPagerAdapter;


    boolean isOpaque = true;
    MyPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        mContext = DemoActivity.this;

        homedetailList = new ArrayList<>();
        homedetailList.add(new TravelModel("https://d27p8o2qkwv41j.cloudfront.net/wp-content/uploads/2018/08/shutterstock_1097202095-e1535706174618.jpg",
                "Hot Deals",
                "Get ready for the twenty20 in 2020",
                "As the Bharat Army partners with the ICC for the T20 Cricket World Cup in Australia!", "View Details"));
        homedetailList.add(new TravelModel("http://realtyplusmag.com/wp-content/uploads/2018/09/Lavasa-Corporation-to-face-insolvency-750x317.jpg",
                "Hot Deals",
                "Get ready for the twenty20 in 2020",
                "As the Bharat Army partners with the ICC for the T20 Cricket World Cup in Australia!", "Book Now"));
        homedetailList.add(new TravelModel("https://d28l1wisvpjs3f.cloudfront.net/fit-in/640x640/images/orig/RMAjcdew4NZr8Hphub8q.jpg",
                "Hot Deals",
                "Get ready for the twenty20 in 2020",
                "As the Bharat Army partners with the ICC for the T20 Cricket World Cup in Australia!", "Book Now"));
        homedetailList.add(new TravelModel("http://realtyplusmag.com/wp-content/uploads/2018/09/MahaRera-orders-Lavasa-to-refund-buyers-money-750x317.jpg",
                "Hot Deals",
                "Get ready for the twenty20 in 2020",
                "As the Bharat Army partners with the ICC for the T20 Cricket World Cup in Australia!", "View Details"));
        homedetailList.add(new TravelModel("https://cdn1.goibibo.com/t_tg_fs/maharashtra-lavasa-150033085668-orijgp.jpg",
                "Hot Deals",
                "Get ready for the twenty20 in 2020",
                "As the Bharat Army partners with the ICC for the T20 Cricket World Cup in Australia!", "Book Now"));
        homedetailList.add(new TravelModel("https://images.thrillophilia.com/image/upload/s--LEMcgH_U--/c_fill,f_auto,fl_strip_profile,h_775,q_auto,w_1600/v1/images/photos/000/048/578/original/1556191795_shutterstock_1097202623.jpg.jpg?1556191795",
                "Hot Deals",
                "Get ready for the twenty20 in 2020",
                "As the Bharat Army partners with the ICC for the T20 Cricket World Cup in Australia!", "View Details"));


        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//    activityDemoBinding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            int x = (int) ((activityDemoBinding.pager.getWidth() * position + positionOffsetPixels) * computeFactor());
//            activityDemoBinding.scrollView.scrollTo(x, 0);
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//
//        private float computeFactor() {
//            return (activityDemoBinding.background.getWidth() -  activityDemoBinding.pager.getWidth()) /
//                    (float)( activityDemoBinding.pager.getWidth() * ( activityDemoBinding.pager.getAdapter().getCount() - 1));
//        }
//    });
//        activityDemoBinding.pager.setPageTransformer(true, new CrossfadePageTransformer());
//        activityDemoBinding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                int x = (int) (( activityDemoBinding.pager.getWidth() * position + positionOffsetPixels) * computeFactor());
//                activityDemoBinding.scrollView.scrollTo(x, 0);
//
//                if (position == 3 - 2 && positionOffset > 0) {
//                    if (isOpaque) {
//                        activityDemoBinding.pager.setBackgroundColor(Color.TRANSPARENT);
//                        isOpaque = false;
//                    }
//                } else {
//                    if (!isOpaque) {
////                        viewPager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
//                        isOpaque = true;
//                    }
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//
//            private float computeFactor() {
//                return (activityDemoBinding.background.getWidth() -  activityDemoBinding.pager.getWidth()) /
//                        (float) ( activityDemoBinding.pager.getWidth() * ( activityDemoBinding.pager.getAdapter().getCount()));
//            }
//
//        });
      activityDemoBinding.pager.setAdapter(pagerAdapter);

    }

//    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
//
//        public ScreenSlidePagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            PacakgeFragment tp = null;
//            switch (position) {
//                case 0:
//                    tp = PacakgeFragment.newInstance(R.layout.pacakge_fragment1);
//                    break;
//                case 1:
//                    tp = PacakgeFragment.newInstance(R.layout.pacakge_fragment2);
//                    break;
//                case 2:
//                    tp = PacakgeFragment.newInstance(R.layout.pacakge_fragment3);
//                    break;
//            }
//            return tp;
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
                    return PacakgeFragment.newInstance(R.layout.pacakge_fragment1);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page.findViewById(R.id.welcome_fragment);
            View text_head = page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View object1 = page.findViewById(R.id.a000);
            View object2 = page.findViewById(R.id.a001);

            View object3 = page.findViewById(R.id.a002);
            View object4 = page.findViewById(R.id.a003);
            View object5 = page.findViewById(R.id.a004);
            View object6 = page.findViewById(R.id.a005);
            View object7 = page.findViewById(R.id.a006);
            View object8 = page.findViewById(R.id.a008);
            View object9 = page.findViewById(R.id.a010);
            View object10 = page.findViewById(R.id.a011);
            View object11 = page.findViewById(R.id.a007);
            View object12 = page.findViewById(R.id.a012);
            View object13 = page.findViewById(R.id.a013);
            View object14  =page.findViewById(R.id.a015);

            if (0 <= position && position < 1) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }
            if (-1 < position && position < 0) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }

            if (position <= -1.0f || position >= 1.0f) {
            } else if (position == 0.0f) {
            } else {
                if (backgroundView != null) {
                    ViewHelper.setAlpha(backgroundView, 1.0f - Math.abs(position));

                }

                if (text_head != null) {
                    ViewHelper.setTranslationX(text_head, pageWidth * position);
                    ViewHelper.setAlpha(text_head, 1.0f - Math.abs(position));
                }

                if (text_content != null) {
                    ViewHelper.setTranslationX(text_content, pageWidth * position);
                    ViewHelper.setAlpha(text_content, 1.0f - Math.abs(position));
                }

                if (object1 != null) {
                    ViewHelper.setTranslationX(object1, pageWidth * position);
                }

                // parallax effect
                if (object2 != null) {
                    ViewHelper.setTranslationX(object2, pageWidth * position);
                }

                if (object4 != null) {
                    ViewHelper.setTranslationX(object4, pageWidth / 2 * position);
                }
                if (object5 != null) {
                    ViewHelper.setTranslationX(object5, pageWidth / 2 * position);
                }
                if (object6 != null) {
                    ViewHelper.setTranslationX(object6, pageWidth / 2 * position);
                }
                if (object7 != null) {
                    ViewHelper.setTranslationX(object7, pageWidth / 2 * position);
                }

                if (object8 != null) {
                    ViewHelper.setTranslationX(object8, (float) (pageWidth / 1.5 * position));
                }

                if (object9 != null) {
                    ViewHelper.setTranslationX(object9, (float) (pageWidth / 2 * position));
                }

                if (object10 != null) {
                    ViewHelper.setTranslationX(object10, pageWidth / 2 * position);
                }

                if (object11 != null) {
                    ViewHelper.setTranslationX(object11, (float) (pageWidth / 1.2 * position));
                }
                if (object14 != null) {
                    ViewHelper.setTranslationX(object14, (float) (pageWidth / 1.2 * position));
                }

                if (object12 != null) {
                    ViewHelper.setTranslationX(object12, (float) (pageWidth / 1.3 * position));
                }

                if (object13 != null) {
                    ViewHelper.setTranslationX(object13, (float) (pageWidth / 1.8 * position));
                }

                if (object3 != null) {
                    ViewHelper.setTranslationX(object3, (float) (pageWidth / 1.2 * position));
                }
            }
        }
    }



    public class Transforpage implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View view, float position) {
            int pageWidth = view.getWidth();


            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page


            } else if (position < 1) { // (0,1]



            } else if (position==1) {

            }

            else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(1);
            }
        }
    }
}
