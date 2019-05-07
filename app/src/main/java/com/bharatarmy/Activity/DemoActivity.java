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

import com.andraskindler.parallaxviewpager.ParallaxViewPager;
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
//        final ParallaxViewPager parallaxViewPager = new ParallaxViewPager(this);
//        parallaxViewPager.setAdapter(new MyPagerAdapter());
//        parallaxViewPager.setBackgroundResource(R.drawable.background);
//        setContentView(parallaxViewPager);


    }


}
