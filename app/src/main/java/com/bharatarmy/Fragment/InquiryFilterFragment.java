package com.bharatarmy.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.InquiryOrderTypeAdapter;
import com.bharatarmy.Adapter.RegisterIntrestAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.InquiryOrderTypeModel;
import com.bharatarmy.Models.InquiryOtherDataModel;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.R;
import com.bharatarmy.TravelDesignModule.MultiSelectDialog;
import com.bharatarmy.Utility.AppConfiguration;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//https://github.com/nex3z/FlowLayout  use for textview layout

public class InquiryFilterFragment extends BottomSheetDialogFragment{
    View rootView;
    Context mContext;
    BottomSheetDialog bottomSheetDialog;
    String fromselecteddateinput;
    LinearLayout back_img, add_tournamentlinear, ordersub_linear, inquirystatussub_linear, daterangesub_linear,
            tournamentsub_linear, customoptionsub_linear, fromdate_linear, todate_linear,
            expand_orderLinear, expand_inquirystatusLinear, expand_daterangeLinear,
            expand_tournamentLinear, customoption_Linear, inquiry_typelinear, banktransfer_typelinear,
            payonline_typelinear, ftp_typelinear, registerinterest_typelinear, pending_statuslinear,
            assignlinear, ongoinglinear, completedlinear, rejectedlinear, notrespondlinear, apply_filter,
            inquirystatus_linear, ordertype_linear, daterange_linear, tournament_linear;
    TextView fromdatedisplay_txt, daymonth_txt, datemonth_txt, todatedisplay_txt, todatemonth_txt, todaymonth_txt,
            inquiry_txt, closetxt, banktransfer_txt, banktransferclosetxt, payonline_txt, payonlineclosetxt,
            ftp_txt, ftpclosetxt, interest_txt, registerclosetxt, pending_txt, pendingclosetxt, assign_txt,
            assignclosetxt, ongoing_txt, ongoingclosetxt, completed_txt, completedclosetxt, rejected_txt,
            rejectedclosetxt, notrespond_txt, notrespondclosetxt, clear_alltxt;
    ImageView expand_orderImg, expand_inquirystatusImg, expand_daterangeImg, expand_tournamentImg, customoption_Img;

    MultiSelectDialog multiSelectDialog;
    DatePickerDialog fromdatePickerDialog, todatePickerDialog;
    private int frommYear, frommMonth, frommDay, tomYear, tomMonth, tomDay;

    String selectedfromDateStr, selectedtoDateStr, filterfromdate, filtertodate;

    MorestoryClick morestoryClick;
    InquiryOtherDataModel filtertaglist;
    List<InquiryStatusModel> inquiryStatusModelList;
    List<InquiryOrderTypeModel> inquiryOrderTypeModelList;
    image_click image_click;


    InquiryOrderTypeAdapter inquiryOrderTypeAdapter;
    RecyclerView ordertype_rcv;

    Calendar toc;
    public InquiryFilterFragment(InquiryOtherDataModel filtertaglist, image_click image_click, MorestoryClick morestoryClick) {
        this.morestoryClick = morestoryClick;
        this.filtertaglist = filtertaglist;
        this.image_click = image_click;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(R.layout.inquiryfilter_bottom_sheet);


        mContext = getActivity().getApplicationContext();
        init();
        preSelection();
        setListiner();


    }

    public void init() {
        back_img = (LinearLayout) bottomSheetDialog.findViewById(R.id.back_img);
        add_tournamentlinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.add_tournamentlinear);
        ordersub_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.ordersub_linear);
        inquirystatussub_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.inquirystatussub_linear);
        daterangesub_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.daterangesub_linear);
        tournamentsub_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.tournamentsub_linear);
        customoptionsub_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.customoptionsub_linear);
        fromdate_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.fromdate_linear);
        todate_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.todate_linear);

        expand_orderLinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.expand_orderLinear);
        expand_inquirystatusLinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.expand_inquirystatusLinear);
        expand_daterangeLinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.expand_daterangeLinear);
        expand_tournamentLinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.expand_tournamentLinear);
        customoption_Linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.customoption_Linear);

        inquiry_typelinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.inquiry_typelinear);
        banktransfer_typelinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.banktransfer_typelinear);
        payonline_typelinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.payonline_typelinear);
        ftp_typelinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.ftp_typelinear);
        registerinterest_typelinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.registerinterest_typelinear);
        pending_statuslinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.pending_statuslinear);
        assignlinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.assignlinear);
        ongoinglinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.ongoinglinear);
        completedlinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.completedlinear);
        rejectedlinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.rejectedlinear);
        notrespondlinear = (LinearLayout) bottomSheetDialog.findViewById(R.id.notrespondlinear);
        apply_filter = (LinearLayout) bottomSheetDialog.findViewById(R.id.apply_filter);

        inquirystatus_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.inquirystatus_linear);
        ordertype_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.ordertype_linear);
        daterange_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.daterange_linear);
        tournament_linear = (LinearLayout) bottomSheetDialog.findViewById(R.id.tournament_linear);

        fromdatedisplay_txt = (TextView) bottomSheetDialog.findViewById(R.id.fromdatedisplay_txt);
        daymonth_txt = (TextView) bottomSheetDialog.findViewById(R.id.daymonth_txt);
        datemonth_txt = (TextView) bottomSheetDialog.findViewById(R.id.datemonth_txt);
        todatedisplay_txt = (TextView) bottomSheetDialog.findViewById(R.id.todatedisplay_txt);
        todatemonth_txt = (TextView) bottomSheetDialog.findViewById(R.id.todatemonth_txt);
        todaymonth_txt = (TextView) bottomSheetDialog.findViewById(R.id.todaymonth_txt);

        inquiry_txt = (TextView) bottomSheetDialog.findViewById(R.id.inquiry_txt);
        closetxt = (TextView) bottomSheetDialog.findViewById(R.id.closetxt);
        banktransfer_txt = (TextView) bottomSheetDialog.findViewById(R.id.banktransfer_txt);
        banktransferclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.banktransferclosetxt);
        payonline_txt = (TextView) bottomSheetDialog.findViewById(R.id.payonline_txt);
        payonlineclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.payonlineclosetxt);

        ftp_txt = (TextView) bottomSheetDialog.findViewById(R.id.ftp_txt);
        ftpclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.ftpclosetxt);
        interest_txt = (TextView) bottomSheetDialog.findViewById(R.id.interest_txt);
        registerclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.registerclosetxt);
        pending_txt = (TextView) bottomSheetDialog.findViewById(R.id.pending_txt);
        pendingclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.pendingclosetxt);
        assign_txt = (TextView) bottomSheetDialog.findViewById(R.id.assign_txt);
        assignclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.assignclosetxt);
        ongoing_txt = (TextView) bottomSheetDialog.findViewById(R.id.ongoing_txt);
        ongoingclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.ongoingclosetxt);
        completed_txt = (TextView) bottomSheetDialog.findViewById(R.id.completed_txt);
        completedclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.completedclosetxt);
        rejected_txt = (TextView) bottomSheetDialog.findViewById(R.id.rejected_txt);
        rejectedclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.rejectedclosetxt);
        notrespond_txt = (TextView) bottomSheetDialog.findViewById(R.id.notrespond_txt);
        notrespondclosetxt = (TextView) bottomSheetDialog.findViewById(R.id.notrespondclosetxt);
        clear_alltxt = (TextView) bottomSheetDialog.findViewById(R.id.clear_alltxt);

        expand_orderImg = (ImageView) bottomSheetDialog.findViewById(R.id.expand_orderImg);
        expand_inquirystatusImg = (ImageView) bottomSheetDialog.findViewById(R.id.expand_inquirystatusImg);
        expand_daterangeImg = (ImageView) bottomSheetDialog.findViewById(R.id.expand_daterangeImg);
        expand_tournamentImg = (ImageView) bottomSheetDialog.findViewById(R.id.expand_tournamentImg);
        customoption_Img = (ImageView) bottomSheetDialog.findViewById(R.id.customoption_Img);

        ordertype_rcv=(RecyclerView)bottomSheetDialog.findViewById(R.id.ordertype_rcv);

        inquiryStatusModelList = filtertaglist.getStatus();
        inquiryOrderTypeModelList = filtertaglist.getTypes();
        Log.d("inquiryOrderTypeModelList", "" + inquiryOrderTypeModelList.size());
        // Get Current Date
        Calendar fromc = Calendar.getInstance();
        frommYear = fromc.get(Calendar.YEAR);
        frommMonth = fromc.get(Calendar.MONTH);
        frommDay = fromc.get(Calendar.DAY_OF_MONTH);
        setFromDateLinear(frommDay, frommYear, frommMonth + 1, 1);

        toc = Calendar.getInstance();
        tomYear = toc.get(Calendar.YEAR);
        tomMonth = toc.get(Calendar.MONTH);
        tomDay = toc.get(Calendar.DAY_OF_MONTH);


        setToDateLinear(tomDay, tomYear, tomMonth + 1, 1);

        setFilterDataList();
    }


    public void setListiner() {
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ordertype_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ordersub_linear.isShown()) {
                    ordersub_linear.setVisibility(View.GONE);
                    expand_orderImg.setImageDrawable(getResources().getDrawable(R.drawable.summary_plus));
                } else {
                    ordersub_linear.setVisibility(View.VISIBLE);
                    expand_orderImg.setImageDrawable(getResources().getDrawable(R.drawable.summary_minus));
                }
            }
        });
        inquirystatus_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inquirystatussub_linear.isShown()) {
                    inquirystatussub_linear.setVisibility(View.GONE);
                    expand_inquirystatusImg.setImageDrawable(getResources().getDrawable(R.drawable.summary_plus));
                } else {
                    inquirystatussub_linear.setVisibility(View.VISIBLE);
                    expand_inquirystatusImg.setImageDrawable(getResources().getDrawable(R.drawable.summary_minus));
                }
            }
        });
        daterange_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daterangesub_linear.isShown()) {
                    daterangesub_linear.setVisibility(View.GONE);
                    expand_daterangeImg.setImageDrawable(getResources().getDrawable(R.drawable.summary_plus));
                } else {
                    daterangesub_linear.setVisibility(View.VISIBLE);
                    expand_daterangeImg.setImageDrawable(getResources().getDrawable(R.drawable.summary_minus));
                }
            }
        });
        tournament_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tournamentsub_linear.isShown()) {
                    tournamentsub_linear.setVisibility(View.GONE);
                    expand_tournamentImg.setImageDrawable(getResources().getDrawable(R.drawable.summary_plus));
                } else {
                    tournamentsub_linear.setVisibility(View.VISIBLE);
                    expand_tournamentImg.setImageDrawable(getResources().getDrawable(R.drawable.summary_minus));
                }
            }
        });
        customoption_Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customoptionsub_linear.isShown()) {
                    customoptionsub_linear.setVisibility(View.GONE);
                    customoption_Img.setImageDrawable(getResources().getDrawable(R.drawable.summary_plus));
                } else {
                    customoptionsub_linear.setVisibility(View.VISIBLE);
                    customoption_Img.setImageDrawable(getResources().getDrawable(R.drawable.summary_minus));

                }
            }
        });
        add_tournamentlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fromdate_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromdatePickerDialog = new DatePickerDialog(getActivity(), R.style.CustomDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                frommDay = dayOfMonth;
                                frommYear = year;
                                frommMonth = monthOfYear;

                                Log.d("date :", dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                setFromDateLinear(dayOfMonth, year, monthOfYear + 1, 1);
                                toc = Calendar.getInstance();
                            toc.set(Calendar.YEAR,frommYear);
                             toc.set(Calendar.MONTH,frommMonth);
                               toc.set(Calendar.DAY_OF_MONTH,frommDay);
                            }
                        }, frommYear, frommMonth, frommDay);

                fromselecteddateinput = frommDay + "-" + frommMonth + "-" + frommYear;
                fromdatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                fromdatePickerDialog.show();
            }
        });


        todate_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todatePickerDialog = new DatePickerDialog(getActivity(), R.style.CustomDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tomDay = dayOfMonth;
                                tomYear = year;
                                tomMonth = monthOfYear;

                                Log.d("date :", dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                setToDateLinear(dayOfMonth, year, monthOfYear + 1, 1);

                            }
                        }, tomYear, tomMonth, tomDay);
                todatePickerDialog.getDatePicker().setMinDate(toc.getTimeInMillis());//System.currentTimeMillis() - 1000
                todatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                todatePickerDialog.show();




            }
        });

        inquiry_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquiry_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                inquiry_txt.setTextColor(getResources().getColor(R.color.skip_color));
                closetxt.setVisibility(View.VISIBLE);
                AppConfiguration.ordertypefilterarray.add(inquiry_txt.getTag().toString());
            }
        });
        closetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquiry_typelinear.setBackground(null);
                inquiry_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                closetxt.setVisibility(View.GONE);

                for (int i = 0; i < AppConfiguration.ordertypefilterarray.size(); i++) {
                    if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("1")) {
                        AppConfiguration.ordertypefilterarray.remove(AppConfiguration.ordertypefilterarray.get(i));
                    }
                }
            }
        });
        banktransfer_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banktransfer_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                banktransfer_txt.setTextColor(getResources().getColor(R.color.skip_color));
                banktransferclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.ordertypefilterarray.add(banktransfer_txt.getTag().toString());
            }
        });
        banktransferclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banktransfer_typelinear.setBackground(null);
                banktransfer_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                banktransferclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.ordertypefilterarray.size(); i++) {
                    if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("2")) {
                        AppConfiguration.ordertypefilterarray.remove(AppConfiguration.ordertypefilterarray.get(i));
                    }
                }
            }
        });
        payonline_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payonline_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                payonline_txt.setTextColor(getResources().getColor(R.color.skip_color));
                payonlineclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.ordertypefilterarray.add(payonline_txt.getTag().toString());
            }
        });
        payonlineclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payonline_typelinear.setBackground(null);
                payonline_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                payonlineclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.ordertypefilterarray.size(); i++) {
                    if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("3")) {
                        AppConfiguration.ordertypefilterarray.remove(AppConfiguration.ordertypefilterarray.get(i));
                    }
                }
            }
        });
        ftp_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftp_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                ftp_txt.setTextColor(getResources().getColor(R.color.skip_color));
                ftpclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.ordertypefilterarray.add(ftp_txt.getTag().toString());
            }
        });
        ftpclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftp_typelinear.setBackground(null);
                ftp_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                ftpclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.ordertypefilterarray.size(); i++) {
                    if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("4")) {
                        AppConfiguration.ordertypefilterarray.remove(AppConfiguration.ordertypefilterarray.get(i));
                    }
                }
            }
        });
        interest_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerinterest_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                interest_txt.setTextColor(getResources().getColor(R.color.skip_color));
                registerclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.ordertypefilterarray.add(interest_txt.getTag().toString());
            }
        });
        registerclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerinterest_typelinear.setBackground(null);
                interest_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                registerclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.ordertypefilterarray.size(); i++) {
                    if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("5")) {
                        AppConfiguration.ordertypefilterarray.remove(AppConfiguration.ordertypefilterarray.get(i));
                    }
                }
            }
        });

        pending_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pending_statuslinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                pending_txt.setTextColor(getResources().getColor(R.color.skip_color));
                pendingclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.inquirystatusfilterarray.add(pending_txt.getTag().toString());
            }
        });
        pendingclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pending_statuslinear.setBackground(null);
                pending_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                pendingclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.inquirystatusfilterarray.size(); i++) {
                    if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("0")) {
                        AppConfiguration.inquirystatusfilterarray.remove(AppConfiguration.inquirystatusfilterarray.get(i));
                    }
                }
            }
        });
        assign_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignlinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                assign_txt.setTextColor(getResources().getColor(R.color.skip_color));
                assignclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.inquirystatusfilterarray.add(assign_txt.getTag().toString());
            }
        });
        assignclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignlinear.setBackground(null);
                assign_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                assignclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.inquirystatusfilterarray.size(); i++) {
                    if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("1")) {
                        AppConfiguration.inquirystatusfilterarray.remove(AppConfiguration.inquirystatusfilterarray.get(i));
                    }
                }
            }
        });
        ongoing_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ongoinglinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                ongoing_txt.setTextColor(getResources().getColor(R.color.skip_color));
                ongoingclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.inquirystatusfilterarray.add(ongoing_txt.getTag().toString());
            }
        });
        ongoingclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ongoinglinear.setBackground(null);
                ongoing_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                ongoingclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.inquirystatusfilterarray.size(); i++) {
                    if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("2")) {
                        AppConfiguration.inquirystatusfilterarray.remove(AppConfiguration.inquirystatusfilterarray.get(i));
                    }
                }
            }
        });
        completed_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completedlinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                completed_txt.setTextColor(getResources().getColor(R.color.skip_color));
                completedclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.inquirystatusfilterarray.add(completed_txt.getTag().toString());
            }
        });
        completedclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completedlinear.setBackground(null);
                completed_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                completedclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.inquirystatusfilterarray.size(); i++) {
                    if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("3")) {
                        AppConfiguration.inquirystatusfilterarray.remove(AppConfiguration.inquirystatusfilterarray.get(i));
                    }
                }
            }
        });
        rejected_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectedlinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                rejected_txt.setTextColor(getResources().getColor(R.color.skip_color));
                rejectedclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.inquirystatusfilterarray.add(rejected_txt.getTag().toString());
            }
        });
        rejectedclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectedlinear.setBackground(null);
                rejected_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                rejectedclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.inquirystatusfilterarray.size(); i++) {
                    if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("4")) {
                        AppConfiguration.inquirystatusfilterarray.remove(AppConfiguration.inquirystatusfilterarray.get(i));
                    }
                }
            }
        });
        notrespond_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notrespondlinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                notrespond_txt.setTextColor(getResources().getColor(R.color.skip_color));
                notrespondclosetxt.setVisibility(View.VISIBLE);
                AppConfiguration.inquirystatusfilterarray.add(rejected_txt.getTag().toString());
            }
        });
        notrespondclosetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notrespondlinear.setBackground(null);
                notrespond_txt.setTextColor(getResources().getColor(R.color.unselected_view));
                notrespondclosetxt.setVisibility(View.GONE);
                for (int i = 0; i < AppConfiguration.inquirystatusfilterarray.size(); i++) {
                    if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("5")) {
                        AppConfiguration.inquirystatusfilterarray.remove(AppConfiguration.inquirystatusfilterarray.get(i));
                    }
                }
            }
        });

        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ordertypearray :", AppConfiguration.ordertypefilterarray.toString());
                Log.d("inquirystatusarray :", AppConfiguration.inquirystatusfilterarray.toString());
                Log.d("filterfromdate :", filterfromdate + " filtertodate :" + filtertodate);
                AppConfiguration.whereToCall = "filter";
                AppConfiguration.pageindex = 0;
                morestoryClick.getmorestoryClick();
            }
        });
        clear_alltxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.ordertypefilterarray.clear();
                AppConfiguration.inquirystatusfilterarray.clear();
                filterfromdate = "";
                filtertodate = "";
                removeSelction();
                Log.d("statusfilterarray :", "" + AppConfiguration.inquirystatusfilterarray);
                Log.d("ordertypefilterarray :", "" + AppConfiguration.ordertypefilterarray);
                AppConfiguration.whereToCall = "Clear";
                image_click.image_more_click();
            }
        });
    }

    public void setFromDateLinear(int mDay, int mYear, int mMonth, int where) {

        if (mDay <= 9) {
            selectedfromDateStr = String.valueOf("0" + mDay);
        } else {
            selectedfromDateStr = String.valueOf(mDay);
        }


        Calendar calendar = Calendar.getInstance();
        String input = mDay + "-" + mMonth + "-" + mYear;

        filterfromdate = input;
        String fromselectedmonth = "", monthStr = "";
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            fromselectedmonth = outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat inFormat1 = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;
        try {
            date1 = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("MMM");
            monthStr = outFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fromdatedisplay_txt.setText(selectedfromDateStr);
        datemonth_txt.setText(monthStr + " " + mYear);
        daymonth_txt.setText(fromselectedmonth);
    }


    public void setToDateLinear(int mDay, int mYear, int mMonth, int where) {

        if (mDay <= 9) {
            selectedtoDateStr = String.valueOf("0" + mDay);
        } else {
            selectedtoDateStr = String.valueOf(mDay);
        }


        Calendar calendar = Calendar.getInstance();
        String input = mDay + "-" + mMonth + "-" + mYear;
        filtertodate = input;
        String toselectedmonth = "", monthStr = "";
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            toselectedmonth = outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat inFormat1 = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;
        try {
            date1 = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("MMM");
            monthStr = outFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        todatedisplay_txt.setText(selectedtoDateStr);
        todatemonth_txt.setText(monthStr + " " + mYear);
        todaymonth_txt.setText(toselectedmonth);
    }


    public void removeSelction() {
        AppConfiguration.pageindex = 0;
        inquiry_typelinear.setBackground(null);
        inquiry_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        closetxt.setVisibility(View.GONE);

        banktransfer_typelinear.setBackground(null);
        banktransfer_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        banktransferclosetxt.setVisibility(View.GONE);

        payonline_typelinear.setBackground(null);
        payonline_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        payonlineclosetxt.setVisibility(View.GONE);

        ftp_typelinear.setBackground(null);
        ftp_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        ftpclosetxt.setVisibility(View.GONE);

        registerinterest_typelinear.setBackground(null);
        interest_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        registerclosetxt.setVisibility(View.GONE);

        pending_statuslinear.setBackground(null);
        pending_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        pendingclosetxt.setVisibility(View.GONE);

        assignlinear.setBackground(null);
        assign_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        assignclosetxt.setVisibility(View.GONE);

        ongoinglinear.setBackground(null);
        ongoing_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        ongoingclosetxt.setVisibility(View.GONE);

        completedlinear.setBackground(null);
        completed_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        completedclosetxt.setVisibility(View.GONE);

        rejectedlinear.setBackground(null);
        rejected_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        rejectedclosetxt.setVisibility(View.GONE);

        notrespondlinear.setBackground(null);
        notrespond_txt.setTextColor(getResources().getColor(R.color.unselected_view));
        notrespondclosetxt.setVisibility(View.GONE);

    }

    public void preSelection() {

        if (AppConfiguration.ordertypefilterarray.size() > 0) {
            for (int i = 0; i < AppConfiguration.ordertypefilterarray.size(); i++) {
                if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("1")) {
                    inquiry_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    inquiry_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    closetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("2")) {
                    banktransfer_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    banktransfer_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    banktransferclosetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("3")) {
                    payonline_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    payonline_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    payonlineclosetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("4")) {
                    ftp_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    ftp_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    ftpclosetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.ordertypefilterarray.get(i).equalsIgnoreCase("5")) {
                    registerinterest_typelinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    interest_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    registerclosetxt.setVisibility(View.VISIBLE);
                }
            }

        }

        if (AppConfiguration.inquirystatusfilterarray.size() > 0) {
            for (int i = 0; i < AppConfiguration.inquirystatusfilterarray.size(); i++) {
                if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("0")) {
                    pending_statuslinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    pending_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    pendingclosetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("1")) {
                    assignlinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    assign_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    assignclosetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("2")) {
                    ongoinglinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    ongoing_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    ongoingclosetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("3")) {
                    completedlinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    completed_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    completedclosetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("4")) {
                    rejectedlinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    rejected_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    rejectedclosetxt.setVisibility(View.VISIBLE);
                } else if (AppConfiguration.inquirystatusfilterarray.get(i).equalsIgnoreCase("5")) {
                    notrespondlinear.setBackground(getResources().getDrawable(R.drawable.inquiryfilter_selected_shape));
                    notrespond_txt.setTextColor(getResources().getColor(R.color.skip_color));
                    notrespondclosetxt.setVisibility(View.VISIBLE);
                }
            }
        }




    }


    public void setFilterDataList(){
        inquiryOrderTypeAdapter=new InquiryOrderTypeAdapter(mContext,inquiryOrderTypeModelList);
                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.BASELINE);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        ordertype_rcv .setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
        ordertype_rcv.setAdapter(inquiryOrderTypeAdapter);
    }
}