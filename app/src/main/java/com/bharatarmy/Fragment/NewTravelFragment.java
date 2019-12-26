package com.bharatarmy.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Activity.TravelMatchScheduleActivity;
import com.bharatarmy.Adapter.TravelPopularPackageAdapter;
import com.bharatarmy.Adapter.TravelFacilityMainAdapter;
import com.bharatarmy.Adapter.TravelPopularCItyAdapter;
import com.bharatarmy.Adapter.UltraPagerAdapter;
import com.bharatarmy.Appguide.ShowCaseBuilder;
import com.bharatarmy.Appguide.ShowCaseContentPosition;
import com.bharatarmy.Appguide.ShowCaseDialog;
import com.bharatarmy.Appguide.ShowCaseObject;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentNewTravelBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leinardi.android.speeddial.SpeedDialView;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

import java.util.ArrayList;
import java.util.List;

// code travel page change to newtravelfragment 13-12-2019
public class NewTravelFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    public static Context mContext;
    public static FragmentNewTravelBinding travelnewbinding;


    SpeedDialView speedDial;
    /*list object of travel different section*/
    public static List<TravelModel> traveltourList;
    public static List<TravelModel> popularcityarrayList;
    public static List<TravelModel> popularPackageList;
    public static List<TravelModel> travelfacilityList;

    /*Travel adapter list*/
    UltraPagerAdapter ultraPagerAdapter;
    TravelFacilityMainAdapter travelFacilityAdapter;
    TravelPopularCItyAdapter popularCityAdapter;
    TravelPopularPackageAdapter popularPackageAdapter;
    GridLayoutManager gridLayoutManager;

    /*filter city and package list*/
    BottomSheetDialogFragment bottomSheetDialogFragment;

    /*App Guider*/
    private ShowCaseDialog showCaseDialog;
    public static final String SHOWCASE_TAG = "sample_showcase_tag";


    public NewTravelFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewTravelFragment newInstance(String param1, String param2) {
        NewTravelFragment fragment = new NewTravelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        travelnewbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_travel, container, false);
        rootView = travelnewbinding.getRoot();
        mContext = getActivity().getApplicationContext();


        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);

        init();
        setDataValue();
        setListiner();

        return rootView;
    }

    public void init() {
        /*Travel match schedule*/
        traveltourList = new ArrayList<TravelModel>();
        traveltourList.add(new TravelModel(AppConfiguration.IMAGE_URL + "ee5d232c-9.jpg",
                "Bharat Army Tour to West Indies", "The Bharat Army head to the Caribbean in 2019 after the Cricket World Cup t"));
        traveltourList.add(new TravelModel(AppConfiguration.IMAGE_URL + "ef4df304-b.jpg",
                "South Africa tour of India, October 2019", "Catch all the action from the stands as two top test teams compete against"));
        traveltourList.add(new TravelModel(AppConfiguration.IMAGE_URL + "e35eee60-7.jpg",
                "Bangladesh Tour of India, November 2019", "A full series involving the modern day rivalry  - India and Bangladesh. Wit"));
        traveltourList.add(new TravelModel(AppConfiguration.IMAGE_URL + "76b0e612-9.jpg",
                "Windies Tour of India, December 2019", "Be a part of the Windies tour of India involving 3 ODIs and 3 T20s and chee"));
        traveltourList.add(new TravelModel(AppConfiguration.IMAGE_URL + "71210037-f.jpg",
                "T20 World Cup 2020", "Australia is going to host T20 Cricket World Cup in 2020. Register your int"));


        /*Popularcity List*/
        popularcityarrayList = new ArrayList<TravelModel>();
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL + "mumbai.jpg",
                "Mumbai", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL + "dehli.jpg",
                "Delhi", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL + "ahmedabad.jpg",
                "Ahemedabad", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL + "indore.jpg",
                "Indore", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL + "dehradun.jpg",
                "Dehradun", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));


        /*PopularPackage List*/
        popularPackageList = new ArrayList<TravelModel>();
        popularPackageList.add(new TravelModel("xyz", "Australian Double Dhamaka: Honeymoon & adventure at one shot", AppConfiguration.IMAGE_URL + "aus1.jpg",
                "Jet Boat Ride from Main Beach.Bungy jumping from 165 ft distance at Cairns.Great Barrier Reef Experience",
                "1k", "900", "true"));

        popularPackageList.add(new TravelModel("xyz", "Explore the best of Australia with your soulmate", AppConfiguration.IMAGE_URL + "aus2.jpg",
                "Grand Barossa Valley Day Tour.Happy day out at the Kangaroo Island with a fun tour amidst natural highlights.Eureka Skydeck 88.Sydney Harbour Jet Boat Thrill Ride: 30 Minutes ",
                "2k", "500", "false"));

        popularPackageList.add(new TravelModel("xyz", "Celebrate love in the Australian lands", AppConfiguration.IMAGE_URL + "aus3.jpg",
                "Delicious dinner cruise during sunset at Sydney Harbour exposed to amazing vistas and views around the arena.Super Pass: Film World, Sea World & Wet'n'Wild Water World.Morning Whale Watching Cruise.Car hire for Great Ocean Road",
                "5k", "1000", "false"));

        /*Travel Facility List*/
        travelfacilityList = new ArrayList<TravelModel>();

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_schedule,
                "Schedule", ""));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_tickets,
                "Ticket", "offer"));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_hotel,
                "Hotel", ""));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_package,
                "Package", "offer"));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_hospitality,
                "Hospitality", ""));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_sightseeing,
                "Sightseeing", "offer"));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_transport,
                "Transport", ""));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_flight,
                "Flight", "offer"));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_stadium,
                "Stadium", ""));


//        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_more,
//                "More", ""));


        /*showCaseDialog = new ShowCaseBuilder()
                .setPackageName(getActivity().getPackageName())
                .titleTextColorRes(android.R.color.white)
                .textColorRes(android.R.color.white)
                .shadowColorRes(R.color.shadow)
                .titleTextSizeRes(R.dimen.text_title)
                .textSizeRes(R.dimen.text_normal)
                .spacingRes(R.dimen.spacing_normal)
                .backgroundContentColorRes(R.color.blue)
                .circleIndicatorBackgroundDrawableRes(R.drawable.showcaseview_indicator)
                .prevStringRes(R.string.previous)
                .nextStringRes(R.string.next)
                .finishStringRes(R.string.finish)
                .useCircleIndicator(true)
                .clickable(true)
                .useArrow(true)
                .useSkipWord(true)
                .build();*/
    }

    public void setListiner() {
        travelnewbinding.travelCityFilterImage.setOnClickListener(this);
        travelnewbinding.travelPacakageFilterImage.setOnClickListener(this);

        travelnewbinding.travelFacilityHeadingTxt.setOnClickListener(this);


    }

    public void setDataValue() {
        /*fill travel tour list */
        travelnewbinding.ultraViewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraPagerAdapter = new UltraPagerAdapter(true, mContext, traveltourList);
        travelnewbinding.ultraViewpager.setAdapter(ultraPagerAdapter);
        travelnewbinding.ultraViewpager.setCurrentItem(1);
        travelnewbinding.ultraViewpager.setMultiScreen(0.77f);
        travelnewbinding.ultraViewpager.setItemRatio(1.0f);
        travelnewbinding.ultraViewpager.setRatio(1.5f);
        travelnewbinding.ultraViewpager.setMaxHeight(600);
        travelnewbinding.ultraViewpager.setAutoMeasureHeight(false);
        travelnewbinding.ultraViewpager.setPageTransformer(false, new UltraScaleTransformer());
        travelnewbinding.ultraViewpager.initIndicator();
        //set style of indicators
        travelnewbinding.ultraViewpager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusIcon(Utils.DrawableToBitMap(R.drawable.selected_new, mContext))
                .setNormalIcon(Utils.DrawableToBitMap(R.drawable.unselected_new, mContext))
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics()));
        //set the alignment
        travelnewbinding.ultraViewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //construct built-in indicator, and add it to  UltraViewPager
        travelnewbinding.ultraViewpager.getIndicator().build();


        /*fill facility list*/
        travelnewbinding.travelFacilityRcv.setHasFixedSize(true);
        travelnewbinding.travelFacilityRcv.setNestedScrollingEnabled(false);
        travelFacilityAdapter = new TravelFacilityMainAdapter(mContext, travelfacilityList);
        gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        travelnewbinding.travelFacilityRcv.setLayoutManager(gridLayoutManager);
        travelnewbinding.travelFacilityRcv.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration Hdivider = new DividerItemDecoration(travelnewbinding.travelFacilityRcv.getContext(), DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration Vdivider = new DividerItemDecoration(travelnewbinding.travelFacilityRcv.getContext(), DividerItemDecoration.VERTICAL);
        Hdivider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider));
        Vdivider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider));
        travelnewbinding.travelFacilityRcv.addItemDecoration(Hdivider);
        travelnewbinding.travelFacilityRcv.addItemDecoration(Vdivider);
        travelnewbinding.travelFacilityRcv.setAdapter(travelFacilityAdapter);

        /*fill travel popularcity*/
        popularCityAdapter = new TravelPopularCItyAdapter(mContext, popularcityarrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        travelnewbinding.tourCityListRcv.setLayoutManager(mLayoutManager);
        travelnewbinding.tourCityListRcv.setItemAnimator(new DefaultItemAnimator());
        travelnewbinding.tourCityListRcv.setAdapter(popularCityAdapter);

        /*fill travel popularpackage*/
        travelnewbinding.tourPackageListRcv.setHasFixedSize(true);
        travelnewbinding.tourPackageListRcv.setNestedScrollingEnabled(false);
        popularPackageAdapter = new TravelPopularPackageAdapter(mContext, popularPackageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelnewbinding.tourPackageListRcv.setLayoutManager(layoutManager);
        travelnewbinding.tourPackageListRcv.setItemAnimator(new DefaultItemAnimator());
        travelnewbinding.tourPackageListRcv.setAdapter(popularPackageAdapter);
       /* new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                travelnewbinding.travelFacilityHeadingTxt.performClick();
            }
        }, 400);*/

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.travel_city_filterImage:
                bottomSheetDialogFragment = new TravelCityFilterFragment();
                //show it
                bottomSheetDialogFragment.show(getChildFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
            case R.id.travel_pacakage_filterImage:
                bottomSheetDialogFragment = new TravelPacakgeFilterFragment();
                //show it
                bottomSheetDialogFragment.show(getChildFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
            case R.id.travel_facility_heading_txt:
         /*       final ArrayList<ShowCaseObject> showCaseList = new ArrayList<>();

                int completelyVisiblePosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                View itemView = gridLayoutManager.findViewByPosition(completelyVisiblePosition);

                if (itemView != null) {
                    // use background white
                    showCaseList.add(new ShowCaseObject(
                            itemView,
                            "Normal Filter",
                            "Filter schedule according team",
                            ShowCaseContentPosition.UNDEFINED,
                            Color.WHITE));

                    showCaseList.add(new ShowCaseObject(
                            itemView.findViewById(R.id.match_image),
                            null,
                            "Match Image"));

                    showCaseList.add(new ShowCaseObject(
                            itemView.findViewById(R.id.match_image),
                            null,
                            "Match Image"));
                }
                // make the dialog show
                showCaseDialog.show(getActivity(), SHOWCASE_TAG, showCaseList);*/

                break;
        }
    }
}
