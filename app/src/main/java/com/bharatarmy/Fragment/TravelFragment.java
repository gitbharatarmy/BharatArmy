package com.bharatarmy.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.TravelListAdapter;
import com.bharatarmy.Adapter.TravelMainPageAdapter;
import com.bharatarmy.Adapter.TravelPopularCItyAdapter;
import com.bharatarmy.Adapter.UltraPagerAdapter;
import com.bharatarmy.Adapter.UpcomingDashboardAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.TravelDesignModule.MultiSelectDialog;
import com.bharatarmy.TravelDesignModule.MultiSelectModel;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.BottomSheetListBinding;
import com.bharatarmy.databinding.FragmentTravelBinding;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialView;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

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

    UltraPagerAdapter ultraPagerAdapter;
    TravelPopularCItyAdapter popularCItyAdapter;
    TravelMainPageAdapter travelMainPageAdapter;


    ScrollView    main_page_scrollview;

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

        main_page_scrollview=getActivity().findViewById(R.id.main_page_scrollview);


        main_page_scrollview.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                return true;
            }
        });

        content = new ArrayList<TravelModel>();
        content.add(new TravelModel("https://www.bharatarmy.com//Docs/ee5d232c-9.jpg",
                "Bharat Army Tour to West Indies", "The Bharat Army head to the Caribbean in 2019 after the Cricket World Cup t"));
        content.add(new TravelModel("https://www.bharatarmy.com//Docs/ef4df304-b.jpg",
                "South Africa tour of India, October 2019", "Catch all the action from the stands as two top test teams compete against"));
        content.add(new TravelModel("https://www.bharatarmy.com//Docs/e35eee60-7.jpg",
                "Bangladesh Tour of India, November 2019", "A full series involving the modern day rivalry  - India and Bangladesh. Wit"));
        content.add(new TravelModel("https://www.bharatarmy.com//Docs/76b0e612-9.jpg",
                "Windies Tour of India, December 2019", "Be a part of the Windies tour of India involving 3 ODIs and 3 T20s and chee"));
        content.add(new TravelModel("https://www.bharatarmy.com//Docs/71210037-f.jpg",
                "T20 World Cup 2020", "Australia is going to host T20 Cricket World Cup in 2020. Register your int"));



        popularcityarrayList=new ArrayList<TravelModel>();

        popularcityarrayList.add(new TravelModel(R.drawable.ahmedabad,
                "Ahemedabad", "140 photos"));
        popularcityarrayList.add(new TravelModel(R.drawable.indore,
                "Indore", "200 photos"));
        popularcityarrayList.add(new TravelModel(R.drawable.mumbai,
                "Mumbai", "80 photos"));
        popularcityarrayList.add(new TravelModel(R.drawable.dehli,
                "Delhi", "150 photos"));
        popularcityarrayList.add(new TravelModel(R.drawable.dehradun,
                "Dehradun", "100 photos"));

        setDataValue();
        return rootView;
    }

    public void setDataValue() {

        Utils.setImageInImageView("https://www.bharatarmy.com/Docs/back-testimonial.png",travelBinding.backgroundImage,mContext);

//        travelBinding.ultraViewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//        ultraPagerAdapter = new UltraPagerAdapter(true, mContext,content);
//        travelBinding.ultraViewpager.setAdapter(ultraPagerAdapter);
//        travelBinding.ultraViewpager.setCurrentItem(1);
//        travelBinding.ultraViewpager.setMultiScreen(0.77f);
//        travelBinding.ultraViewpager.setItemRatio(1.0f);
//        travelBinding.ultraViewpager.setRatio(1.5f);
//        travelBinding.ultraViewpager.setMaxHeight(600);
//        travelBinding.ultraViewpager.setAutoMeasureHeight(false);
//        travelBinding.ultraViewpager.setPageTransformer(false, new UltraScaleTransformer());
//        travelBinding.ultraViewpager.initIndicator();
//          //set style of indicators
//        travelBinding.ultraViewpager.getIndicator()
//                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
//                .setFocusIcon(Utils.DrawableToBitMap(R.drawable.selected_new,mContext))
//                .setNormalIcon(Utils.DrawableToBitMap(R.drawable.unselected_new,mContext))
//                .setRadius((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
//                //set the alignment
//        travelBinding.ultraViewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//         //construct built-in indicator, and add it to  UltraViewPager
//        travelBinding.ultraViewpager.getIndicator().build();
//
//
//
        travelMainPageAdapter = new TravelMainPageAdapter(mContext, content,popularcityarrayList);
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
