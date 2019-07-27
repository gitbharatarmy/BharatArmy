package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bharatarmy.Fragment.ProductTourFragment;
import com.bharatarmy.R;
import com.nineoldandroids.view.ViewHelper;

public class TimerActivity extends AppCompatActivity {

    Context mContext;
    ViewPager viewPager;
    ScreenSlidePagerAdapter pagerAdapter;
    Button btn_next, btn_skip, btn_done;
    boolean isOpaque = true;
    LinearLayout circles;
    static final int NUM_PAGES = 6;
    HorizontalScrollView scroll_view;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mContext = TimerActivity.this;


        viewPager = (ViewPager) findViewById(R.id.pager);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_skip = (Button) findViewById(R.id.btn_skip);
        btn_done = (Button) findViewById(R.id.btn_done);
        scroll_view = (HorizontalScrollView) findViewById(R.id.scroll_view);
        background = (ImageView) findViewById(R.id.background);



        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        viewPager.setPageTransformer(true, new CrossfadePageTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                int x = (int) ((viewPager.getWidth() * position + positionOffsetPixels) * computeFactor());
                scroll_view.scrollTo(x, 0);

                if (position == NUM_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        viewPager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
//                        viewPager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if (position == NUM_PAGES - 2) {
                    btn_skip.setVisibility(View.GONE);
                    btn_next.setVisibility(View.GONE);
                    btn_done.setVisibility(View.VISIBLE);
                } else if (position < NUM_PAGES - 2) {
                    btn_skip.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);
                    btn_done.setVisibility(View.GONE);
                } else if (position == NUM_PAGES - 1) {
                    endTutorial();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            private float computeFactor() {
                return (background.getWidth() - viewPager.getWidth()) /
                        (float) (viewPager.getWidth() * (viewPager.getAdapter().getCount()));
            }

        });
        viewPager.setAdapter(pagerAdapter);
        buildCircles();


        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(mContext, LoginActivity.class);
                startActivity(login);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTutorial();
            }
        });
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ProductTourFragment tp = null;
            switch (position) {
                case 0:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment1);
                    break;
                case 1:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment2);
                    break;
                case 2:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment3);
                    break;
                case 3:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment4);
                    break;
                case 4:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment5);
                    break;
                case 5:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment6);
                    break;
            }
            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
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

    private void endTutorial() {
        Intent iLogin = new Intent(mContext, LoginActivity.class);
        startActivity(iLogin);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewPager != null) {
            viewPager.clearOnPageChangeListeners();
        }
    }

    private void buildCircles() {
        circles = LinearLayout.class.cast(findViewById(R.id.circles));

        float scale = getResources().getDisplayMetrics().density;
//        int padding = (int) (5 * scale + 0.5f);
        int width = 30, height = 30;
        for (int i = 0; i < NUM_PAGES - 1; i++) {
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.ball_scroll);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
            parms.setMargins(7, 5, 0, 0);
            circle.setLayoutParams(parms);
            circle.setScaleType(ImageView.ScaleType.CENTER_CROP);
            circles.addView(circle);
        }
        setIndicator(0);
    }

    private void setIndicator(int index) {
        if (index < NUM_PAGES) {
            for (int i = 0; i < NUM_PAGES - 1; i++) {
                ImageView circle = (ImageView) circles.getChildAt(i);
                if (i == index) {
                    circle.setImageResource(R.drawable.ball_scroll_primary);
                } else {
                    circle.setImageResource(R.drawable.ball_scroll);
                }
            }
        }
    }
}
