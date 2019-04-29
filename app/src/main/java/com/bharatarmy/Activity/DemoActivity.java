package com.bharatarmy.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.ViewSwitcher;

import com.asp.fliptimerviewlibrary.CountDownClock;
import com.bharatarmy.R;

import com.bharatarmy.databinding.ActivityDemoBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DemoActivity extends AppCompatActivity {

    ActivityDemoBinding activityDemoBinding;
    Context mContext;
    private MyImageViewPagerAdapter myViewPagerAdapter;
    private Handler imageSwitcherHandler;
    private ArrayList<Integer> layouts;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 100;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 6000;

    private int animationCounter = 0;

    CountDownTimer mCountDownTimer;
    long diffInSecond, diffInMinute, diffInHour, diffInDays;

    Handler timeHandler = new Handler();
    int delay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        mContext = DemoActivity.this;
        activityDemoBinding.timerProgramCountdown.startCountDown(99999999);


//        final ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
//
//        Date endDate=new Date();
//        final long[] diffInMilis = new long[1];
//        final Date startDate=new Date();
//
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
//        String dateToStr = format.format(startDate);
//        Log.d("Todaytime",dateToStr);
//        try {
//            SimpleDateFormat formatendDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
//          endDate=  formatendDate.parse("15/05/2019 08:20:00 AM");
//
//
//            final Date finalEndDate = endDate;
//            service.schedule(new Runnable() {
//
//                @Override
//                public void run() {
//                    // Calculate the difference in millisecond between two dates
//                   diffInMilis[0] = finalEndDate.getTime() - startDate.getTime();
//                  diffInSecond = diffInMilis[0] / 1000;
//                    diffInMinute = diffInMilis[0]/ (60 * 1000);
//                diffInHour = diffInMilis[0] / (60 * 60 * 1000);
//               diffInDays = diffInMilis[0] / (24 * 60 * 60 * 1000);
//                    if (diffInMilis[0] > 0) {
////                        Log.d("=========Days:", "" + diffInDays + "========Hours:" + diffInHour + "=======Minutes:" + diffInMinute + "=======Second:" + diffInSecond);
////                        Log.d("time", "" + service.schedule(this, 1, TimeUnit.SECONDS));
//                    }
//                }
//            }, 1, TimeUnit.SECONDS);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        timeHandler.postDelayed(new Runnable() {
//            public void run() {
//                //do something
//                Date endDate = new Date();
//                final long[] diffInMilis = new long[1];
//                final Date startDate = new Date();
//                try {
//                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
//                    String dateToStr = format.format(startDate);
//                    Log.d("Todaytime", dateToStr);
//                    SimpleDateFormat formatendDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
//
//                    endDate = formatendDate.parse("30/04/2019 12:42:00 PM");
//
//
//                    final Date finalEndDate = endDate;
////                    Calculate the difference in millisecond between two dates
//                    diffInMilis[0] = finalEndDate.getTime() - startDate.getTime();
//
//                    diffInSecond = diffInMilis[0] / 1000;
//                    diffInMinute = diffInMilis[0] / (60 * 1000);
//                    diffInHour = diffInMilis[0] / (60 * 60 * 1000);
//                    diffInDays = diffInMilis[0] / (24 * 60 * 60 * 1000);
//                    if (diffInMilis[0] > 0) {
//                        Log.d("=========Days:", "" + diffInDays + "========Hours:" + diffInHour + "=======Minutes:" + diffInMinute + "=======Second:" + diffInSecond);
//                        activityDemoBinding.daysTxt.setText(String.valueOf(diffInDays));
//                        activityDemoBinding.hoursTxt.setText(String.valueOf(diffInHour));
//                        activityDemoBinding.minutesTxt.setText(String.valueOf(diffInMinute));
//                        activityDemoBinding.secondsTxt.setText(String.valueOf(diffInSecond));
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                timeHandler.postDelayed(this, delay);
//            }
//        }, delay);

    }

//        Animation in = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
//        Animation out = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
//
//        activityDemoBinding.imageView.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                ImageView myView = new ImageView(mContext);
//                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                myView.setLayoutParams(new
//                        ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT));
//                return myView;
//            }
//        });
//        activityDemoBinding.imageView.setImageResource(R.drawable.login41);
//        activityDemoBinding.imageView.setInAnimation(in);
//        activityDemoBinding.imageView.setOutAnimation(out);
//        layouts = new ArrayList<Integer>();
//
//        layouts.add(R.drawable.login_new_1);
//        layouts.add(R.drawable.login_new_2);
//        layouts.add(R.drawable.login_new_3);
//
//        imageSwitcherHandler = new Handler();
//        Runnable runnable = new Runnable() {
//            int i = 0;
//
//            public void run() {
//                activityDemoBinding.imageView.setImageResource(layouts.get(i));
//                i++;
//                if (i > layouts.size() - 1) {
//                    activityDemoBinding.imageView.setImageResource(R.drawable.login41);
//                    i = 0;
//                }
//                imageSwitcherHandler.postDelayed(this, 7000);  //for interval...
//            }
//        };
//        imageSwitcherHandler.postDelayed(runnable, 7000); //for initial delay..


//        imageSwitcherHandler = new Handler(Looper.getMainLooper());
//        imageSwitcherHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                switch (animationCounter++) {
//                    case 1:
//                        activityDemoBinding.imageView.setImageResource(R.drawable.login1);
//                        break;
//                    case 2:
//                        activityDemoBinding.imageView.setImageResource(R.drawable.login2);
//                        break;
//                    case 3:
//                        activityDemoBinding.imageView.setImageResource(R.drawable.login3);
//                        break;
//                    case 4:
//                        activityDemoBinding.imageView.setImageResource(R.drawable.login4);
//                        break;
//                    case 5:
//                        activityDemoBinding.imageView.setImageResource(R.drawable.login5);
//                        break;
//                }
//                animationCounter %= 4;
//                if (animationCounter == 0) animationCounter = 1;
//
//                imageSwitcherHandler.postDelayed(this, 10000);
//            }
//        });

//        activityDemoBinding.viewPager.setPageTransformer(true, fadeOutTransformation);
//        ViewPagerCustomDuration vp = new ViewPagerCustomDuration(DemoActivity.this);
//        vp.setScrollDurationFactor(2);
//        activityDemoBinding.viewPager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });
//        layouts = new ArrayList<Integer>();
//
//        layouts.add(R.drawable.l1);
//        layouts.add(R.drawable.login1);
//        layouts.add(R.drawable.login_new);
//        layouts.add(R.drawable.login_new1);
//        // adding bottom dots
//        addBottomDots(0);
//        // making notification bar transparent
//        changeStatusBarColor();
//        myViewPagerAdapter = new MyImageViewPagerAdapter();
//        activityDemoBinding.viewPager.setAdapter(myViewPagerAdapter);
//        activityDemoBinding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//        /*After setting the adapter use the timer */
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == layouts.size()) {
//                    currentPage = 0;
//                }
//                activityDemoBinding.viewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//        timer = new Timer(); // This will create a new Thread
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, DELAY_MS, PERIOD_MS);
//    }
//    @SuppressLint("ResourceAsColor")
//    private void addBottomDots(int currentPage) {
//        dots = new ImageView[layouts.size()];
//
////        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
////        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
//
//        List<String> colorsActiveList = new ArrayList<>();
//        List<String> colorsInactive = new ArrayList<>();
//        for (int i = 0; i < layouts.size(); i++) {
////            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.Active_dot_color)));
////            colorsInactive.add(String.valueOf(getResources().getColor(R.color.NotActive_dot_color)));
//            colorsActiveList.add(String.valueOf(R.drawable.ball_scroll_primary));
//            colorsInactive.add(String.valueOf(R.drawable.ball_scroll));
//        }
//
//        int width = 30;
//        int height = 30;
//
//        activityDemoBinding.layoutDots.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new ImageView(this);
////            dots[i].setText(Html.fromHtml("&#8226;"));
////            dots[i].setTextSize(35);
//            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
//            parms.setMargins(5, 5, 0, 0);
//            dots[i].setLayoutParams(parms);
//            dots[i].setImageResource(Integer.parseInt(colorsInactive.get(currentPage)));//   colorsInactive[currentPage]
//
//            activityDemoBinding.layoutDots.addView(dots[i]);
//        }
//
//        if (dots.length > 0)
//            dots[currentPage].setImageResource(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
//    }
//
//    /**
//     * Making notification bar transparent
//     */
//    private void changeStatusBarColor() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//    }
//
//
//    //  viewpager change listener
//    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
//
//        @Override
//        public void onPageSelected(int position) {
//            addBottomDots(position);//position
//
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//
//        }
//    };


    /**
     * View pager adapter
     */
    public class MyImageViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        ImageSwitcher imageView;


        public MyImageViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.login_image_slider, container, false);

            imageView = (ImageSwitcher) view.findViewById(R.id.imageView);


            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.size();
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
