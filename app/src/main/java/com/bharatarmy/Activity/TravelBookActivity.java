package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityTravelBookBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TravelBookActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityTravelBookBinding activityTravelBookBinding;
    Context mContext;
    String titleNameStr;
    private int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    String selectedDateStr;
    ArrayList<TravelModel> monthdetail;
    int adultcount=0;
    int childcount=0;
    int infantscount=0;
    BottomSheetDialogFragment bottomSheetDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelBookBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_book);
        mContext = TravelBookActivity.this;

        init();
        setListiner();
        setDayDateList();

    }

    public void init() {
        titleNameStr = getIntent().getStringExtra("pacakgeName");
        activityTravelBookBinding.bookpacakgeTitletxt.setText(titleNameStr);

        // Get Current Date
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        setDateLinear(mDay, mYear, mMonth, 0);

        activityTravelBookBinding.oldpriceTxt.setPaintFlags(activityTravelBookBinding.oldpriceTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    public void setListiner() {
        activityTravelBookBinding.backImg.setOnClickListener(this);
        activityTravelBookBinding.choosedateLinear.setOnClickListener(this);
        activityTravelBookBinding.firstDayLinear.setOnClickListener(this);
        activityTravelBookBinding.secondDayLinear.setOnClickListener(this);
        activityTravelBookBinding.thirdDayLinear.setOnClickListener(this);
        activityTravelBookBinding.fourthDayLinear.setOnClickListener(this);
        activityTravelBookBinding.fiveDayLinear.setOnClickListener(this);
        activityTravelBookBinding.sixDayLinear.setOnClickListener(this);
        activityTravelBookBinding.adultaddTxt.setOnClickListener(this);
        activityTravelBookBinding.adultremoveTxt.setOnClickListener(this);
        activityTravelBookBinding.childaddTxt.setOnClickListener(this);
        activityTravelBookBinding.childremoveTxt.setOnClickListener(this);
        activityTravelBookBinding.infantsaddTxt.setOnClickListener(this);
        activityTravelBookBinding.infantsremoveTxt.setOnClickListener(this);
        activityTravelBookBinding.callLinear.setOnClickListener(this);
    }

    public void setDayDateList() {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelBookActivity.this.finish();
                break;
            case R.id.choosedate_linear:
                setDateList();
                break;
            case R.id.first_day_linear:
                firstdateLinear();
                break;
            case R.id.second_day_linear:
                seconddateLinear();
                break;
            case R.id.third_day_linear:
                thirddateLinear();
                break;
            case R.id.fourth_day_linear:
                fourthdateLinear();
                break;
            case R.id.five_day_linear:
                fivedateLinear();
                break;
            case R.id.six_day_linear:
                sixdateLinear();
                break;
            case R.id.adultremove_txt:
                if (adultcount!=1){
                    adultcount=adultcount-1;
                    if (adultcount<=9){
                        activityTravelBookBinding.adultcountTxt.setText("0"+String.valueOf(adultcount));
                    }else{
                        activityTravelBookBinding.adultcountTxt.setText(String.valueOf(adultcount));
                    }

                }else{
                    activityTravelBookBinding.adultremoveTxt.setClickable(false);
                }

                break;
            case R.id.adultadd_txt:
                activityTravelBookBinding.adultremoveTxt.setClickable(true);
                adultcount=adultcount+1;
                if (adultcount<=9){
                    activityTravelBookBinding.adultcountTxt.setText("0"+String.valueOf(adultcount));
                }else{
                    activityTravelBookBinding.adultcountTxt.setText(String.valueOf(adultcount));
                }

                break;
            case R.id.childremove_txt:
                if (childcount!=0){
                    childcount=childcount-1;
                    if (childcount<=9){
                        activityTravelBookBinding.childcountTxt.setText("0"+String.valueOf(childcount));
                    }else{
                        activityTravelBookBinding.childcountTxt.setText(String.valueOf(childcount));
                    }

                }else{
                    activityTravelBookBinding.childremoveTxt.setClickable(false);
                }
                break;
            case R.id.childadd_txt:
                activityTravelBookBinding.childremoveTxt.setClickable(true);
                childcount=childcount+1;
                if (childcount<=9){
                    activityTravelBookBinding.childcountTxt.setText("0"+String.valueOf(childcount));
                }else{
                    activityTravelBookBinding.childcountTxt.setText(String.valueOf(childcount));
                }
                break;
            case R.id.infantsremove_txt:
                if (infantscount!=0){
                    infantscount=infantscount-1;
                    if (infantscount<=9){
                        activityTravelBookBinding.infantscountTxt.setText("0"+String.valueOf(infantscount));
                    }else{
                        activityTravelBookBinding.infantscountTxt.setText(String.valueOf(infantscount));
                    }

                }else{
                    activityTravelBookBinding.infantsremoveTxt.setClickable(false);
                }
                break;
            case R.id.infantsadd_txt:
                activityTravelBookBinding.infantsremoveTxt.setClickable(true);
                infantscount=infantscount+1;
                if (infantscount<=9){
                    activityTravelBookBinding.infantscountTxt.setText("0"+String.valueOf(infantscount));
                }else{
                    activityTravelBookBinding.infantscountTxt.setText(String.valueOf(infantscount));
                }
                break;
            case R.id.call_linear:

                break;
        }
    }


    public void setDateList() {

        datePickerDialog = new DatePickerDialog(mContext, R.style.CustomDatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        mDay = dayOfMonth;
                        mYear = year;
                        mMonth = monthOfYear;

                        Log.d("date :", dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        setDateLinear(dayOfMonth, year, monthOfYear, 1);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void firstdateLinear() {
        mMonth= Integer.parseInt(monthdetail.get(0).getCityHotelAmenitiesImage())-1;
        mYear= Integer.parseInt(monthdetail.get(0).getCityHotelAmenitiesName());
        mDay = Integer.parseInt(activityTravelBookBinding.firstdayDateTxt.getText().toString());
        activityTravelBookBinding.firstDayLinear.setBackground(getResources().getDrawable(R.drawable.curvetricolorbg));
        activityTravelBookBinding.firstdayDateTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));
        activityTravelBookBinding.firstdayTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));

        activityTravelBookBinding.secondDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.seconddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.seconddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.thirdDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.thirddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.thirddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fourthDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fourthdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fourthdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fiveDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fivedayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fivedayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.sixDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.sixdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.sixdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));
    }

    public void seconddateLinear() {
        mMonth= Integer.parseInt(monthdetail.get(1).getCityHotelAmenitiesImage())-1;
        mYear= Integer.parseInt(monthdetail.get(1).getCityHotelAmenitiesName());
        mDay = Integer.parseInt(activityTravelBookBinding.seconddayDateTxt.getText().toString());
        activityTravelBookBinding.firstDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.firstdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.firstdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.secondDayLinear.setBackground(getResources().getDrawable(R.drawable.curvetricolorbg));
        activityTravelBookBinding.seconddayDateTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));
        activityTravelBookBinding.seconddayTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));

        activityTravelBookBinding.thirdDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.thirddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.thirddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fourthDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fourthdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fourthdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fiveDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fivedayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fivedayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.sixDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.sixdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.sixdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));
    }

    public void thirddateLinear() {
        mMonth= Integer.parseInt(monthdetail.get(2).getCityHotelAmenitiesImage())-1;
        mYear= Integer.parseInt(monthdetail.get(2).getCityHotelAmenitiesName());
        mDay = Integer.parseInt(activityTravelBookBinding.thirddayDateTxt.getText().toString());
        activityTravelBookBinding.firstDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.firstdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.firstdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.secondDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.seconddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.seconddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.thirdDayLinear.setBackground(getResources().getDrawable(R.drawable.curvetricolorbg));
        activityTravelBookBinding.thirddayDateTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));
        activityTravelBookBinding.thirddayTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));

        activityTravelBookBinding.fourthDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fourthdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fourthdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fiveDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fivedayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fivedayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.sixDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.sixdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.sixdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));
    }

    public void fourthdateLinear() {
        mMonth= Integer.parseInt(monthdetail.get(3).getCityHotelAmenitiesImage())-1;
        mYear= Integer.parseInt(monthdetail.get(3).getCityHotelAmenitiesName());
        mDay = Integer.parseInt(activityTravelBookBinding.fourthdayDateTxt.getText().toString());
        activityTravelBookBinding.firstDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.firstdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.firstdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.secondDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.seconddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.seconddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.thirdDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.thirddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.thirddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fourthDayLinear.setBackground(getResources().getDrawable(R.drawable.curvetricolorbg));
        activityTravelBookBinding.fourthdayDateTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));
        activityTravelBookBinding.fourthdayTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));

        activityTravelBookBinding.fiveDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fivedayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fivedayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.sixDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.sixdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.sixdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));
    }

    public void fivedateLinear() {
        mMonth= Integer.parseInt(monthdetail.get(4).getCityHotelAmenitiesImage())-1;
        mYear= Integer.parseInt(monthdetail.get(4).getCityHotelAmenitiesName());
        mDay = Integer.parseInt(activityTravelBookBinding.fivedayDateTxt.getText().toString());
        activityTravelBookBinding.firstDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.firstdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.firstdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.secondDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.seconddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.seconddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.thirdDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.thirddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.thirddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fourthDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fourthdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fourthdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fiveDayLinear.setBackground(getResources().getDrawable(R.drawable.curvetricolorbg));
        activityTravelBookBinding.fivedayDateTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));
        activityTravelBookBinding.fivedayTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));

        activityTravelBookBinding.sixDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.sixdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.sixdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));
    }

    public void sixdateLinear() {
        mMonth= Integer.parseInt(monthdetail.get(5).getCityHotelAmenitiesImage())-1;
        mYear= Integer.parseInt(monthdetail.get(5).getCityHotelAmenitiesName());
        mDay = Integer.parseInt(activityTravelBookBinding.sixdayDateTxt.getText().toString());
        activityTravelBookBinding.firstDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.firstdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.firstdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.secondDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.seconddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.seconddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.thirdDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.thirddayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.thirddayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fourthDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fourthdayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fourthdayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.fiveDayLinear.setBackground(getResources().getDrawable(R.drawable.daybg));
        activityTravelBookBinding.fivedayDateTxt.setTextColor(getResources().getColor(R.color.unselected_view));
        activityTravelBookBinding.fivedayTxt.setTextColor(getResources().getColor(R.color.unselected_view));

        activityTravelBookBinding.sixDayLinear.setBackground(getResources().getDrawable(R.drawable.curvetricolorbg));
        activityTravelBookBinding.sixdayDateTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));
        activityTravelBookBinding.sixdayTxt.setTextColor(getResources().getColor(R.color.splash_bg_color));
    }

    public void setDateLinear(int mDay, int mYear, int mMonth, int where) {

        if (mDay <= 9) {
            selectedDateStr = String.valueOf("0" + mDay);
        } else {
            selectedDateStr = String.valueOf(mDay);
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        calendar.set(mYear, mMonth, mDay);

        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            Log.d("Days" + i, "date :" + days[i]);
        }

        String[] datedays = new String[7];
        for (int k = 0; k < days.length; k++) {
            Date date = null;
            try {
                date = format.parse(days[k]);
                SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
                datedays[k] = outFormat.format(date);
                Log.d("Days" + k, "date :" + datedays[k]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        monthdetail = new ArrayList<TravelModel>();

        for (int i = 0; i < days.length; i++) {
            activityTravelBookBinding.firstdayDateTxt.setText(days[0].substring(0, 2));
            activityTravelBookBinding.seconddayDateTxt.setText(days[1].substring(0, 2));
            activityTravelBookBinding.thirddayDateTxt.setText(days[2].substring(0, 2));
            activityTravelBookBinding.fourthdayDateTxt.setText(days[3].substring(0, 2));
            activityTravelBookBinding.fivedayDateTxt.setText(days[4].substring(0, 2));
            activityTravelBookBinding.sixdayDateTxt.setText(days[5].substring(0, 2));

            monthdetail.add(new TravelModel(days[i].substring(3,5),days[i].substring(6,10)));
        }


        for (int i = 0; i < datedays.length; i++) {
            activityTravelBookBinding.firstdayTxt.setText(datedays[0]);
            activityTravelBookBinding.seconddayTxt.setText(datedays[1]);
            activityTravelBookBinding.thirddayTxt.setText(datedays[2]);
            activityTravelBookBinding.fourthdayTxt.setText(datedays[3]);
            activityTravelBookBinding.fivedayTxt.setText(datedays[4]);
            activityTravelBookBinding.sixdayTxt.setText(datedays[5]);
        }

        activityTravelBookBinding.firstDayLinear.performClick();
    }





}
