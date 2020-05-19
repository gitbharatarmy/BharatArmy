package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
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
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bharatarmy.Activity.TravelMatchHospitalityActivity;
import com.bharatarmy.Activity.TravelMatchHotelActivity;
import com.bharatarmy.Activity.TravelMatchPackageActivity;
import com.bharatarmy.Activity.TravelMatchScheduleActivity;
import com.bharatarmy.Activity.TravelMatchSightseeingActivity;
import com.bharatarmy.Activity.TravelMatchStadiumActivity;
import com.bharatarmy.Activity.TravelMatchTicketActivity;
import com.bharatarmy.Adapter.TravelNewandUpdatesAdapter;
import com.bharatarmy.Adapter.TravelPartnersAdapter;
import com.bharatarmy.Adapter.TravelPopularPackageAdapter;
import com.bharatarmy.Adapter.TravelFacilityMainAdapter;
import com.bharatarmy.Adapter.TravelPopularCItyAdapter;
import com.bharatarmy.Adapter.TravelVideoAdapter;
import com.bharatarmy.Adapter.UltraPagerAdapter;
import com.bharatarmy.Appguide.ShowCaseDialog;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentNewTravelBinding;
import com.bharatarmy.speeddialView.SpeedDialView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/* code travel page change to newtravelfragment 13-12-2019
* remove extra code 18-02-2020
* remove extra code 17-03-2020 */
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
    public List<TravelModel> traveltourList;
    public List<TravelModel> popularcityarrayList;
    public List<TravelModel> popularPackageList;
    public List<TravelModel> travelfacilityList;
    public List<TravelModel> travelpartnerList;
    public List<TravelModel> travelnewsupdatesList;
    public List<TravelModel> travelvideoList;

    /*Travel adapter list*/
    UltraPagerAdapter ultraPagerAdapter;
    TravelFacilityMainAdapter travelFacilityAdapter;
    TravelPopularCItyAdapter popularCityAdapter;
    TravelPopularPackageAdapter popularPackageAdapter;
    TravelNewandUpdatesAdapter travelNewandUpdatesAdapter;
    TravelVideoAdapter travelVideoAdapter;
    TravelPartnersAdapter travelPartnersAdapter;
    GridLayoutManager gridLayoutManager;

    /*filter city and package list*/
    BottomSheetDialogFragment bottomSheetDialogFragment;

    /*App Guider*/
    private ShowCaseDialog showCaseDialog;
    public static final String SHOWCASE_TAG = "sample_showcase_tag";

    String tournameStr;

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
//        //        scrollview
        travelnewbinding.scrolltravel.post(new Runnable() {
            @Override
            public void run() {
                travelnewbinding.scrolltravel.fullScroll(View.FOCUS_UP);
            }
        });

//        travelnewbinding.scrolltravel.scrollTo(0,0);

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

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_stadium,
                "Stadium", ""));

        travelfacilityList.add(new TravelModel(R.drawable.ic_travel_page_flight,
                "Flight", ""));


        //News and updates List
        travelnewsupdatesList = new ArrayList<TravelModel>();

        travelnewsupdatesList.add(new TravelModel("https://www.bharatarmy.com//Docs/ec0dfda1-3.jpg", "IPL Auctions 2020: An Overview",
                "The IPL Auctions 2020 threw up some interesting buys and picks from the teams. The event commenced from 3:30 PM IST in Kolkata and going ahead in this piece; we look at some other interesting from the auction."));

        travelnewsupdatesList.add(new TravelModel("https://www.bharatarmy.com//Docs/baf32af5-d.jpg", "IND v WI: Team India roar back to life in Vizag",
                "Team India thrashed West Indies in the second ODI in Vizag thanks to some amazing batting and bowling displays."));

        travelnewsupdatesList.add(new TravelModel("https://www.bharatarmy.com//Docs/5aec8786-8.jpg", "The Best Day Trips from Perth",
                "Perth’s buzzy boutiques and sunny beaches will draw you in, but eerie rock formations and furry island animals will have you leaving the city behind."));

        travelnewsupdatesList.add(new TravelModel("https://www.bharatarmy.com//Docs/970afed2-5.jpg", "IND v WI: Can Team India seal the T20I series in Mumbai?",
                "Team India will look to win the final T20I against West Indies in Mumbai today and win the series 2-1. Without further ado, here we glide into a comprehensive preview of the upcoming encounter."));

        //News and updates List
        travelnewsupdatesList = new ArrayList<TravelModel>();

        travelnewsupdatesList.add(new TravelModel("https://www.bharatarmy.com//Docs/e7911418-b.jpg", "Melbourne\u0027s Hidden Gems",
                "Melbourne has so many hidden gems that it would take years to discover them all. We explored th.."));

        travelnewsupdatesList.add(new TravelModel("https://www.bharatarmy.com//Docs/baf32af5-d.jpg", "IND v WI: Team India roar back to life in Vizag",
                "Team India thrashed West Indies in the second ODI in Vizag thanks to some amazing batting and bowling displays."));

        travelnewsupdatesList.add(new TravelModel("https://www.bharatarmy.com//Docs/5aec8786-8.jpg", "The Best Day Trips from Perth",
                "Perth’s buzzy boutiques and sunny beaches will draw you in, but eerie rock formations and furry island animals will have you leaving the city behind."));

        travelnewsupdatesList.add(new TravelModel("https://www.bharatarmy.com//Docs/970afed2-5.jpg", "IND v WI: Can Team India seal the T20I series in Mumbai?",
                "Team India will look to win the final T20I against West Indies in Mumbai today and win the series 2-1. Without further ado, here we glide into a comprehensive preview of the upcoming encounter."));

        //travel videos List
        travelvideoList = new ArrayList<TravelModel>();

        travelvideoList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/Media/Thumb/a983346f-b0ac-4a49-91c6-f7196efd4629-1570705345206.jpg",
                "http://devenv.bharatarmy.com//Docs/Media/e83c8278-f1f8-4aa6-b618-1d2302b80416-MP4_20191010_163200.mp4"));

        travelvideoList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/Media/Thumb/acdb7074-8588-4059-a5f4-67d09730785a-1570441108244.jpg",
                "http://devenv.bharatarmy.com//Docs/Media/11f98532-8171-4c81-b8e1-60a33ccf193f-MP4_20191007_150748.mp4"));

        //     Partners List
        travelpartnerList = new ArrayList<>();

        travelpartnerList.add(new TravelModel("https://www.bharatarmy.com/Docs/Partner-9.png",
                "HighLights", "Bharat Army Highlights is the first of it’s kind sports fan ..."));

        travelpartnerList.add(new TravelModel("https://www.bharatarmy.com/Docs/Partner-8.png",
                "Red FM 93.5", "This World Cup; Bharat Army Travel & Red FM have a big ..."));

        travelpartnerList.add(new TravelModel("https://www.bharatarmy.com/Docs/hotstar-logo.png",
                "Hotstar", "The Bharat Army are pleased to announce that we ..."));

        travelpartnerList.add(new TravelModel("https://www.bharatarmy.com/Docs/puma_logo.png",
                "Puma", "PUMA sits at the top table of global sports brands ..."));

        travelpartnerList.add(new TravelModel("https://www.bharatarmy.com/Docs/hublot_logo.png",
                "Hublot", "When it comes to luxury watch brands, Hublot ..."));

        travelpartnerList.add(new TravelModel("https://www.bharatarmy.com/Docs/oceanone8-logo.png",
                "Ocean one8", "Ocean one8 takes the philosophy that Virat Kohli ..."));

        travelpartnerList.add(new TravelModel("https://www.bharatarmy.com/Docs/uber_logo.png",
                "Uber Eats", "Uber Eats is the top online food ordering and delivery platform in the world ..."));

        travelpartnerList.add(new TravelModel("https://www.bharatarmy.com/Docs/prideview_logo.png",
                "Prideview", "As a leading figure in the acquisition, management and sale of commercial ..."));


    }

    public void setListiner() {
        travelnewbinding.travelCityFilterImage.setOnClickListener(this);
        travelnewbinding.travelPacakageFilterImage.setOnClickListener(this);
        travelnewbinding.saveBtn.setOnClickListener(this);
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

        tournameStr = traveltourList.get(1).getPopularcity_name();
        Log.d("tournameStr :", tournameStr);

        travelnewbinding.ultraViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("pageselected", "" + position);

                for (int i = 0; i < traveltourList.size(); i++) {
                    if (position == i) {
                        tournameStr = traveltourList.get(i).getPopularcity_name();
                        Log.d("tournameStr :", tournameStr);

                    }
                }
                if (!tournameStr.equalsIgnoreCase("")) {

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /*fill facility list*/
        travelnewbinding.travelFacilityRcv.setHasFixedSize(true);
        travelnewbinding.travelFacilityRcv.setNestedScrollingEnabled(false);
        travelFacilityAdapter = new TravelFacilityMainAdapter(mContext, travelfacilityList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                String activityNameStr = travelFacilityAdapter.activityName().toString();
                if (activityNameStr.equalsIgnoreCase("Schedule")) {
                    Intent travelmatchscheduleIntent = new Intent(mContext, TravelMatchScheduleActivity.class);
                    travelmatchscheduleIntent.putExtra("tourtitle", tournameStr);
                    travelmatchscheduleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(travelmatchscheduleIntent);
                } else if (activityNameStr.equalsIgnoreCase("Ticket")) {
                    Intent travelmatchticketIntent = new Intent(mContext, TravelMatchTicketActivity.class);
                    travelmatchticketIntent.putExtra("tourtitle", tournameStr);
                    travelmatchticketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(travelmatchticketIntent);
                } else if (activityNameStr.equalsIgnoreCase("Stadium")) {
                    Intent travelmatchstadiumIntent = new Intent(mContext, TravelMatchStadiumActivity.class);
                    travelmatchstadiumIntent.putExtra("tourtitle", tournameStr);
                    travelmatchstadiumIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(travelmatchstadiumIntent);
                } else if (activityNameStr.equalsIgnoreCase("Hospitality")) {
                    Intent travelmatchhospitalityIntent = new Intent(mContext, TravelMatchHospitalityActivity.class);
                    travelmatchhospitalityIntent.putExtra("tourtitle", tournameStr);
                    travelmatchhospitalityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(travelmatchhospitalityIntent);
                } else if (activityNameStr.equalsIgnoreCase("Hotel")) {
                    Intent travelmatchhotelIntent = new Intent(mContext, TravelMatchHotelActivity.class);
                    travelmatchhotelIntent.putExtra("tourtitle", tournameStr);
                    travelmatchhotelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(travelmatchhotelIntent);
                } else if (activityNameStr.equalsIgnoreCase("Sightseeing")) {
                    Intent travelmatchsightseenIntent = new Intent(mContext, TravelMatchSightseeingActivity.class);
                    travelmatchsightseenIntent.putExtra("tourtitle", tournameStr);
                    travelmatchsightseenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(travelmatchsightseenIntent);
                } else if (activityNameStr.equalsIgnoreCase("Package")) {
                    Intent travelmatchpackageIntent = new Intent(mContext, TravelMatchPackageActivity.class);
                    travelmatchpackageIntent.putExtra("tourtitle", tournameStr);
                    travelmatchpackageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(travelmatchpackageIntent);
                }
            }
        });
        gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        travelnewbinding.travelFacilityRcv.setLayoutManager(gridLayoutManager);
        travelnewbinding.travelFacilityRcv.setItemAnimator(new DefaultItemAnimator());
//        DividerItemDecoration Hdivider = new DividerItemDecoration(travelnewbinding.travelFacilityRcv.getContext(), DividerItemDecoration.HORIZONTAL);
//        DividerItemDecoration Vdivider = new DividerItemDecoration(travelnewbinding.travelFacilityRcv.getContext(), DividerItemDecoration.VERTICAL);
//        Hdivider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider));
//        Vdivider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider));
//        travelnewbinding.travelFacilityRcv.addItemDecoration(Hdivider);
//        travelnewbinding.travelFacilityRcv.addItemDecoration(Vdivider);
        travelnewbinding.travelFacilityRcv.setAdapter(travelFacilityAdapter);


        /*fill travel partners list*/
        travelPartnersAdapter = new TravelPartnersAdapter(mContext, travelpartnerList);
        RecyclerView.LayoutManager partnermLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        travelnewbinding.travelPatnersListRcv.setLayoutManager(partnermLayoutManager);
        travelnewbinding.travelPatnersListRcv.setItemAnimator(new DefaultItemAnimator());
        travelnewbinding.travelPatnersListRcv.setAdapter(travelPartnersAdapter);

        /*fill travel news and updates list*/
        travelNewandUpdatesAdapter = new TravelNewandUpdatesAdapter(mContext, travelnewsupdatesList);
        RecyclerView.LayoutManager newsupdatemLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        travelnewbinding.travelLatestNewsListRcv.setLayoutManager(newsupdatemLayoutManager);
        travelnewbinding.travelLatestNewsListRcv.setItemAnimator(new DefaultItemAnimator());
        travelnewbinding.travelLatestNewsListRcv.setAdapter(travelNewandUpdatesAdapter);

        /*fill travel video list*/
        travelVideoAdapter = new TravelVideoAdapter(mContext, travelvideoList);
        RecyclerView.LayoutManager videomLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        travelnewbinding.travelVideosListRcv.setLayoutManager(videomLayoutManager);
        travelnewbinding.travelVideosListRcv.setItemAnimator(new DefaultItemAnimator());
        travelnewbinding.travelVideosListRcv.setAdapter(travelVideoAdapter);

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
            case R.id.save_btn:
                EventBus.getDefault().post(new MyScreenChnagesModel("viewmore", "4"));
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
