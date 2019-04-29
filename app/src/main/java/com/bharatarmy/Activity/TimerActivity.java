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
        Date endDate = new Date();
                final long[] diffInMilis = new long[1];
                final Date startDate = new Date();
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                    String dateToStr = format.format(startDate);
                    Log.d("Todaytime", dateToStr);
                    SimpleDateFormat formatendDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");

                    endDate = formatendDate.parse("15/05/2019 04:15:00 PM");


                    final Date finalEndDate = endDate;
//                    Calculate the difference in millisecond between two dates
                    diffInMilis[0] = finalEndDate.getTime() - startDate.getTime();
                }catch (ParseException ex){

                }
        timerBinding.timerProgramCountdown.startCountDown(diffInMilis[0]);

        timerBinding.timerProgramCountdown.setCountdownListener(new CountDownClock.CountdownCallBack() {
            @Override
            public void countdownAboutToFinish() {

            }

            @Override
            public void countdownFinished() {
                timerBinding.timerProgramCountdown.resetCountdownTimer();
            }
        });
    }


}

