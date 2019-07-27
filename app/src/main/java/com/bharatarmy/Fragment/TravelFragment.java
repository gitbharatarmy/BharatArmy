package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.TravelMainPageAdapter;
import com.bharatarmy.Adapter.TravelPopularCItyAdapter;
import com.bharatarmy.Adapter.UltraPagerAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FragmentTravelBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

// changes and remove the code 19/06/2019
public class TravelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    public static Context mContext;
    public static FragmentTravelBinding travelBinding;
    public static OnItemClick mListener;
    FloatingActionButton fab;
    SpeedDialView speedDial;
    public static List<TravelModel> content;
    public static List<TravelModel> popularcityarrayList;
    public static List<TravelModel> popularPackageList;

    UltraPagerAdapter ultraPagerAdapter;
    TravelPopularCItyAdapter popularCItyAdapter;
    TravelMainPageAdapter travelMainPageAdapter;
    BottomSheetDialogFragment bottomSheetDialogFragment;

//    ScrollView main_page_scrollview;

    public TravelFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TravelFragment newInstance(String param1, String param2) {
        TravelFragment fragment = new TravelFragment();
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
        travelBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel, container, false);

        rootView = travelBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        fab = getActivity().findViewById(R.id.fab);
        fab.hide();
        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);

        content = new ArrayList<TravelModel>();
        content.add(new TravelModel(AppConfiguration.IMAGE_URL+"ee5d232c-9.jpg",
                "Bharat Army Tour to West Indies", "The Bharat Army head to the Caribbean in 2019 after the Cricket World Cup t"));
        content.add(new TravelModel(AppConfiguration.IMAGE_URL+"ef4df304-b.jpg",
                "South Africa tour of India, October 2019", "Catch all the action from the stands as two top test teams compete against"));
        content.add(new TravelModel(AppConfiguration.IMAGE_URL+"e35eee60-7.jpg",
                "Bangladesh Tour of India, November 2019", "A full series involving the modern day rivalry  - India and Bangladesh. Wit"));
        content.add(new TravelModel(AppConfiguration.IMAGE_URL+"76b0e612-9.jpg",
                "Windies Tour of India, December 2019", "Be a part of the Windies tour of India involving 3 ODIs and 3 T20s and chee"));
        content.add(new TravelModel(AppConfiguration.IMAGE_URL+"71210037-f.jpg",
                "T20 World Cup 2020", "Australia is going to host T20 Cricket World Cup in 2020. Register your int"));



        popularcityarrayList=new ArrayList<TravelModel>();
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL+"mumbai.jpg",
                "Mumbai", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL+"dehli.jpg",
                "Delhi", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL+"ahmedabad.jpg",
                "Ahemedabad", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL+"indore.jpg",
                "Indore", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        popularcityarrayList.add(new TravelModel(AppConfiguration.IMAGE_URL+"dehradun.jpg",
                "Dehradun", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));


        popularPackageList=new ArrayList<TravelModel>();

        popularPackageList.add(new TravelModel("xyz","Australian Double Dhamaka: Honeymoon & adventure at one shot",AppConfiguration.IMAGE_URL+"aus1.jpg",
                "Jet Boat Ride from Main Beach.Bungy jumping from 165 ft distance at Cairns.Great Barrier Reef Experience",
                "1k","900","true"));

        popularPackageList.add(new TravelModel("xyz","Explore the best of Australia with your soulmate",AppConfiguration.IMAGE_URL+"aus2.jpg",
                "Grand Barossa Valley Day Tour.Happy day out at the Kangaroo Island with a fun tour amidst natural highlights.Eureka Skydeck 88.Sydney Harbour Jet Boat Thrill Ride: 30 Minutes ",
                "2k","500","false"));

        popularPackageList.add(new TravelModel("xyz","Celebrate love in the Australian lands",AppConfiguration.IMAGE_URL+"aus3.jpg",
                "Delicious dinner cruise during sunset at Sydney Harbour exposed to amazing vistas and views around the arena.Super Pass: Film World, Sea World & Wet'n'Wild Water World.Morning Whale Watching Cruise.Car hire for Great Ocean Road",
                "5k","1000","false"));

        setDataValue();
        return rootView;
    }

    public void setDataValue() {

//        Utils.setImageInImageView("https://www.bharatarmy.com/Docs/back-testimonial.png",travelBinding.backgroundImage,mContext);

        travelMainPageAdapter = new TravelMainPageAdapter(mContext, content,popularcityarrayList,popularPackageList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                String filterName= String.valueOf(travelMainPageAdapter.getData());
                filterName= filterName.substring(1, filterName.length()-1);
                if (filterName.equalsIgnoreCase("city")){
                    bottomSheetDialogFragment = new TravelCityFilterFragment();
                    //show it
                    bottomSheetDialogFragment.show(getChildFragmentManager(), bottomSheetDialogFragment.getTag());
                }else{
                    bottomSheetDialogFragment = new TravelPacakgeFilterFragment();
                    //show it
                    bottomSheetDialogFragment.show(getChildFragmentManager(), bottomSheetDialogFragment.getTag());
                }

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelBinding.mainScreenPageRcv.setLayoutManager(mLayoutManager);
        travelBinding.mainScreenPageRcv.setItemAnimator(new DefaultItemAnimator());
        travelBinding.mainScreenPageRcv.setAdapter(travelMainPageAdapter);



    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClick) {
            mListener = (OnItemClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnItemClick {
        void onTrave();


    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
