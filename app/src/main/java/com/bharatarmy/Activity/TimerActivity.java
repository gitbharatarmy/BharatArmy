package com.bharatarmy.Activity;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asp.fliptimerviewlibrary.CountDownClock;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTimerBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerActivity extends AppCompatActivity {
    ActivityTimerBinding timerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timerBinding = DataBindingUtil.setContentView(this, R.layout.activity_timer);
    }


}

