package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bharatarmy.Adapter.CityHotelAmenitiesAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FragmentHotelDetailBinding;

import java.util.ArrayList;


public class HotelDetailFragment extends Fragment {
    private View rootView;
    private Context mContext;
    FragmentHotelDetailBinding fragmentHotelDetailBinding;
    ArrayList<TravelModel> cityHotelAmenitiesList;
    CityHotelAmenitiesAdapter cityHotelAmenitiesAdapter;
    ArrayList<String> valueArray;

    public HotelDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HotelDetailFragment newInstance(String param1, String param2) {
        HotelDetailFragment fragment = new HotelDetailFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHotelDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_detail, container, false);

        rootView = fragmentHotelDetailBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setUserVisibleHint(true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here
            setDataList();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void setDataList() {
//    amenities List
        cityHotelAmenitiesList=new ArrayList<TravelModel>();
        cityHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL+"parking.png","Parking"));
        cityHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL+"tv.png","Tv"));
        cityHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL+"bathtub.png","Bathtub"));
        cityHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL+"washer.png","Washer"));
        cityHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL+"airconditioner.png","Air condition"));
        cityHotelAmenitiesList.add(new TravelModel(AppConfiguration.IMAGE_URL+"wifi.png","Wifi"));

        valueArray=new ArrayList<>();
        valueArray.add("1");
        valueArray.add("2");

        cityHotelAmenitiesAdapter = new CityHotelAmenitiesAdapter(mContext,valueArray, cityHotelAmenitiesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentHotelDetailBinding.amenitiesRcv.setLayoutManager(mLayoutManager);
        fragmentHotelDetailBinding.amenitiesRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentHotelDetailBinding.amenitiesRcv.setAdapter(cityHotelAmenitiesAdapter);
    }
}
