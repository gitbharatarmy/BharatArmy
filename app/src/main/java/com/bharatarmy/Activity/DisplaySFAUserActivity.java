package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.bharatarmy.Adapter.DisplaySFAUserAdapter;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityDisplaySfauserBinding;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class DisplaySFAUserActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityDisplaySfauserBinding activityDisplaySfauserBinding;
    Context mContext;
    DisplaySFAUserAdapter displaySFAUserAdapter;
    List<ImageDetailModel> sfausermodellist;
    List<ImageDetailModel> usermodellist;
    private int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    String selectedDateStr;
    String entryTypeuserStr = "", sfatotalCountStr = "", certificateCountStr = "",
            appuserIdStr = "", selectedsearchagentIdStr = "0", selectesearchdfromdateStr = "",
            selectedsearchtodteStr = "";
int agentid;
    private HashMap<Integer, String> spinneruserMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisplaySfauserBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_sfauser);
        mContext = DisplaySFAUserActivity.this;


        init();
        setListiner();
    }

    public void init() {
        // Get Current Date
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

//        setDateLinear(mDay, mYear, mMonth + 1, 0);

        if (Utils.retriveLoginData(mContext) != null) {
            if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                activityDisplaySfauserBinding.backImg.setVisibility(View.GONE);
                activityDisplaySfauserBinding.logoutImg.setVisibility(View.VISIBLE);
                activityDisplaySfauserBinding.searchImg.setVisibility(View.GONE);
                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityDisplaySfauserBinding.shimmerViewContainerCountuser.setVisibility(View.VISIBLE);
                activityDisplaySfauserBinding.shimmerViewContainerCountuser.startShimmerAnimation();
                activityDisplaySfauserBinding.toolbarTitleTxt.setText("Entry List");
                appuserIdStr = String.valueOf(Utils.getAppUserId(mContext));
            } else {
                activityDisplaySfauserBinding.backImg.setVisibility(View.VISIBLE);
                activityDisplaySfauserBinding.logoutImg.setVisibility(View.GONE);
                activityDisplaySfauserBinding.searchImg.setVisibility(View.GONE);
                activityDisplaySfauserBinding.shimmerViewContainerCountuser.setVisibility(View.GONE);
                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityDisplaySfauserBinding.shimmerViewContainer.startShimmerAnimation();

                appuserIdStr = "0";
                if (Utils.getPref(mContext, "entryType") != null && !Utils.getPref(mContext, "entryType").equalsIgnoreCase("")) {
                    entryTypeuserStr = Utils.getPref(mContext, "entryType");
                    if (entryTypeuserStr.equalsIgnoreCase("1")) {
                        activityDisplaySfauserBinding.toolbarTitleTxt.setText("SFA Data Entry");
                    } else {
                        activityDisplaySfauserBinding.toolbarTitleTxt.setText("Certification");
                    }
                } else {
                    activityDisplaySfauserBinding.toolbarTitleTxt.setText("SFA Data Entry");
                }
            }
        }


        callDisplaySFAUserData();
    }


    public void fromDateLinear() {
        datePickerDialog = new DatePickerDialog(mContext, R.style.CustomDatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        mDay = dayOfMonth;
                        mYear = year;
                        mMonth = monthOfYear;

                        Log.d("date :", dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        setFromDateLinear(dayOfMonth, year, monthOfYear + 1, 1);

                    }
                }, mYear, mMonth, mDay);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void setFromDateLinear(int mDay, int mYear, int mMonth, int where) {
        String dayStr, monthStr, yearStr;
        if (mDay <= 9) {
            dayStr = String.valueOf("0" + mDay);
        } else {
            dayStr = String.valueOf(mDay);
        }
        if (mMonth <= 9) {
            monthStr = String.valueOf("0" + mMonth);
        } else {
            monthStr = String.valueOf(mMonth);
        }
        yearStr = String.valueOf(mYear);
        selectedDateStr = dayStr + "/" + monthStr + "/" + yearStr;
        activityDisplaySfauserBinding.fromdateEdt.setText(selectedDateStr);


    }

    public void toDateLinear() {
        datePickerDialog = new DatePickerDialog(mContext, R.style.CustomDatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        mDay = dayOfMonth;
                        mYear = year;
                        mMonth = monthOfYear;

                        Log.d("date :", dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        setToDateLinear(dayOfMonth, year, monthOfYear + 1, 1);

                    }
                }, mYear, mMonth, mDay);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void setToDateLinear(int mDay, int mYear, int mMonth, int where) {
        String dayStr, monthStr, yearStr;
        if (mDay <= 9) {
            dayStr = String.valueOf("0" + mDay);
        } else {
            dayStr = String.valueOf(mDay);
        }
        if (mMonth <= 9) {
            monthStr = String.valueOf("0" + mMonth);
        } else {
            monthStr = String.valueOf(mMonth);
        }
        yearStr = String.valueOf(mYear);
        selectedDateStr = dayStr + "/" + monthStr + "/" + yearStr;
        activityDisplaySfauserBinding.toDateEdt.setText(selectedDateStr);


    }

    public void setListiner() {
        activityDisplaySfauserBinding.backImg.setOnClickListener(this);
        activityDisplaySfauserBinding.sfanewUserfabLinear.setOnClickListener(this);
        activityDisplaySfauserBinding.backImg.setOnClickListener(this);
        activityDisplaySfauserBinding.logoutImg.setOnClickListener(this);
        activityDisplaySfauserBinding.noOfSfarecordrel.setOnClickListener(this);
        activityDisplaySfauserBinding.noOfCertificaterecordrel.setOnClickListener(this);
        activityDisplaySfauserBinding.shareLinear.setOnClickListener(this);
        activityDisplaySfauserBinding.fromdateEdt.setOnClickListener(this);
        activityDisplaySfauserBinding.toDateEdt.setOnClickListener(this);
        activityDisplaySfauserBinding.searchBtn.setOnClickListener(this);


        activityDisplaySfauserBinding.usernameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = activityDisplaySfauserBinding.usernameSpinner.getSelectedItem().toString();
                selectedsearchagentIdStr = spinneruserMap.get(activityDisplaySfauserBinding.usernameSpinner.getSelectedItemPosition());
                agentid = Integer.parseInt(spinneruserMap.get(activityDisplaySfauserBinding.usernameSpinner.getSelectedItemPosition()));
                Log.d("usersearchID : ", selectedsearchagentIdStr + "name :" + name + "agentid :"+agentid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void callDisplaySFAUserData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), DisplaySFAUserActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getDataEnter(getDisplayUserData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel userDataModel, Response response) {
                Utils.dismissDialog();

                if (userDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (userDataModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (userDataModel.getIsValid() == 0) {
                    Utils.ping(mContext, mContext.getString(R.string.false_msg));
                    return;
                }
                if (userDataModel.getIsValid() == 1) {
                    if (userDataModel.getOtherData() != null && userDataModel.getOtherValue() != null) {
                        sfatotalCountStr = String.valueOf(userDataModel.getOtherData());
                        certificateCountStr = String.valueOf(userDataModel.getOtherValue());
                        activityDisplaySfauserBinding.noOfEntryTxt.setText("No of Entry" + " ( " + sfatotalCountStr + " )");
                        activityDisplaySfauserBinding.noOfCertificateEntryTxt.setText("No of Entry" + " ( " + certificateCountStr + " )");

                        if (entryTypeuserStr.equalsIgnoreCase("1")){
                            activityDisplaySfauserBinding.totalCountTxt.setText(sfatotalCountStr);
                        }else{
                            activityDisplaySfauserBinding.totalCountTxt.setText(certificateCountStr);
                        }

                    }
//                    activityDisplaySfauserBinding.sfanewUserfabLinear.setVisibility(View.VISIBLE);
                    if (userDataModel.getData() != null && userDataModel.getData().size() > 0) {
                        if (Utils.retriveLoginData(mContext) != null) {
                            if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.searchImg.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.noRecordrel.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.totalCountLinear.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.sfanewUserfabLinear.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.shimmerViewContainerCountuser.stopShimmerAnimation();
                                activityDisplaySfauserBinding.shimmerViewContainerCountuser.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.directEntryLinear.setVisibility(View.VISIBLE);
                            } else {
                                activityDisplaySfauserBinding.shimmerViewContainer.stopShimmerAnimation();
                                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.searchImg.setVisibility(View.VISIBLE);
                                activityDisplaySfauserBinding.noRecordrel.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.shimmerViewContainerCountuser.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.directEntryLinear.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.VISIBLE);
                                activityDisplaySfauserBinding.totalCountLinear.setVisibility(View.VISIBLE);
                                activityDisplaySfauserBinding.sfanewUserfabLinear.setVisibility(View.VISIBLE);

                            }
                        }

                        sfausermodellist = userDataModel.getData();
                        usermodellist = userDataModel.getGlobalData();
                        fillSFAUserData();
                        fillUserNameSpinner();
                    } else {
                        if (Utils.retriveLoginData(mContext) != null) {
                            if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.noRecordrel.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.totalCountLinear.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.sfanewUserfabLinear.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.searchImg.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.shimmerViewContainerCountuser.stopShimmerAnimation();
                                activityDisplaySfauserBinding.shimmerViewContainerCountuser.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.directEntryLinear.setVisibility(View.VISIBLE);
                            } else {
                                activityDisplaySfauserBinding.shimmerViewContainer.stopShimmerAnimation();
                                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.shimmerViewContainerCountuser.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.noRecordrel.setVisibility(View.VISIBLE);
                                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.totalCountLinear.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.directEntryLinear.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.sfanewUserfabLinear.setVisibility(View.VISIBLE);
                                activityDisplaySfauserBinding.searchImg.setVisibility(View.GONE);

                            }
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDisplayUserData() {
        Map<String, String> map = new HashMap<>();
        map.put("AppUserId", appuserIdStr); //String.valueOf(Utils.getAppUserId(mContext))
        map.put("EntryType", entryTypeuserStr);
        map.put("Agent", selectedsearchagentIdStr);
        map.put("fromdate", selectesearchdfromdateStr);
        map.put("todate", selectedsearchtodteStr);
        return map;
    }

    public void fillSFAUserData() {

        displaySFAUserAdapter = new DisplaySFAUserAdapter(mContext, sfausermodellist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityDisplaySfauserBinding.SFAUserInfoRcv.setLayoutManager(mLayoutManager);
        activityDisplaySfauserBinding.SFAUserInfoRcv.setItemAnimator(new DefaultItemAnimator());
        activityDisplaySfauserBinding.SFAUserInfoRcv.setAdapter(displaySFAUserAdapter);
    }

    public void fillUserNameSpinner() {
        // Spinner Drop down elements
        ArrayList<Integer> userId = new ArrayList<Integer>();
        userId.add(0);
        for (int i = 0; i < usermodellist.size(); i++) {
            userId.add(usermodellist.get(i).getId());
        }
        ArrayList<String> userName = new ArrayList<String>();
        userName.add("All");
        for (int j = 0; j < usermodellist.size(); j++) {
            userName.add(usermodellist.get(j).getText());
        }

        String[] spinneruserIdArray = new String[userId.size()];

        spinneruserMap = new HashMap<Integer, String>();
        for (int i = 0; i < userId.size(); i++) {
            spinneruserMap.put(i, String.valueOf(userId.get(i)));
            spinneruserIdArray[i] = userName.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(activityDisplaySfauserBinding.usernameSpinner);

            popupWindow.setHeight(spinneruserIdArray.length > 4 ? 500 : spinneruserIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_list_item, spinneruserIdArray);
        activityDisplaySfauserBinding.usernameSpinner.setAdapter(adapterTerm);
//        selectedsearchagentIdStr = spinneruserMap.get(0);

        Log.d("agentid :",selectedsearchagentIdStr);
        if (!selectedsearchagentIdStr.equalsIgnoreCase("0")){
            for (int i=0;i<usermodellist.size();i++){
                if (usermodellist.get(i).getId().equals(agentid)){
//                    int spinnerPosition = adapterTerm.getPosition();
                    Log.d("spinnerPosition :", String.valueOf(i));
                    activityDisplaySfauserBinding.usernameSpinner.setSelection(i+1);
                    Log.d("spinnerPosition +1 :", String.valueOf(i+1));
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.share_linear:
                if (activityDisplaySfauserBinding.searchLinear.isShown()) {
                    activityDisplaySfauserBinding.searchLinear.setVisibility(View.GONE);
                } else {
                    activityDisplaySfauserBinding.searchLinear.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.fromdate_edt:
                fromDateLinear();
                break;
            case R.id.to_date_edt:
                toDateLinear();
                break;
            case R.id.no_of_sfarecordrel:
                Utils.setPref(mContext, "entryType", "1");
                Utils.handleClickEvent(mContext, activityDisplaySfauserBinding.noOfSfarecordrel);
                Intent sfasintent = new Intent(mContext, SportsInterestActivity.class);
//                sfasintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sfasintent);
                break;
            case R.id.no_of_certificaterecordrel:
                Utils.setPref(mContext, "entryType", "2");
                Utils.handleClickEvent(mContext, activityDisplaySfauserBinding.noOfCertificaterecordrel);
                Intent certificatesintent = new Intent(mContext, SportsInterestActivity.class);
//                certificatesintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(certificatesintent);
                break;
            case R.id.sfanew_userfab_linear:
                Intent userentry = new Intent(mContext, SportsInterestActivity.class);
                userentry.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(userentry);
                finish();
                break;
            case R.id.search_btn:
                filterData();
                break;
            case R.id.logout_img:
                android.app.AlertDialog.Builder alertDialog2 = new android.app.AlertDialog.Builder(this);
                alertDialog2.setTitle("Logout Confirm");
                alertDialog2.setMessage("Are you sure you want logout?");
                alertDialog2.setIcon(R.drawable.app_logo_new);
                alertDialog2.setCancelable(false);
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Utils.removeLoginData(mContext);
                                Utils.setPref(mContext, "IsSkipLogin", "");
                                Utils.setPref(mContext, "IsLoginUser", "");
                                Utils.setPref(mContext, "entryType", "");
                                Utils.ping(mContext, "You are logout suceessfully");
                                Intent ilogin = new Intent(mContext, AppLoginActivity.class);  //LoginwithEmailActivity
                                ilogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ilogin.putExtra("whereTocomeLogin", "more");
                                startActivity(ilogin);
                                finish();
                            }
                        });
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog

                                dialog.cancel();
                            }
                        });
                alertDialog2.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (Utils.retriveLoginData(mContext) != null) {
            if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                finish();
            } else {
                finish();
            }
        }
    }


    public boolean CheckDates(String d1, String d2) {
        boolean b = false;
        if (!d1.equalsIgnoreCase("") && !d2.equalsIgnoreCase("")) {
            SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
            try {
                if (dfDate.parse(d1).before(dfDate.parse(d2))) {
                    b = true;//If start date is before end date
                } else if (dfDate.parse(d1).equals(dfDate.parse(d2))) {
                    b = true;//If two dates are equal
                } else {
                    b = false; //If start date is after the end date
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {

        }
        return b;
    }

    public void filterData() {
        selectesearchdfromdateStr = activityDisplaySfauserBinding.fromdateEdt.getText().toString();
        selectedsearchtodteStr = activityDisplaySfauserBinding.toDateEdt.getText().toString();
        if (!selectesearchdfromdateStr.equalsIgnoreCase("")) {
            if (!selectedsearchtodteStr.equalsIgnoreCase("")) {
                if (CheckDates(selectesearchdfromdateStr, selectedsearchtodteStr)) {
                    activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                    activityDisplaySfauserBinding.totalCountLinear.setVisibility(View.GONE);
                    activityDisplaySfauserBinding.noRecordrel.setVisibility(View.GONE);
                    activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                    activityDisplaySfauserBinding.shimmerViewContainer.startShimmerAnimation();
                    callDisplaySFAUserData();
                } else {
                    Utils.ping(mContext, "Please check the selected date");
                }
            } else {
                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                activityDisplaySfauserBinding.totalCountLinear.setVisibility(View.GONE);
                activityDisplaySfauserBinding.noRecordrel.setVisibility(View.GONE);
                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityDisplaySfauserBinding.shimmerViewContainer.startShimmerAnimation();
                callDisplaySFAUserData();
            }
        } else {
            activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
            activityDisplaySfauserBinding.totalCountLinear.setVisibility(View.GONE);
            activityDisplaySfauserBinding.noRecordrel.setVisibility(View.GONE);
            activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
            activityDisplaySfauserBinding.shimmerViewContainer.startShimmerAnimation();
            callDisplaySFAUserData();
        }

    }
}
