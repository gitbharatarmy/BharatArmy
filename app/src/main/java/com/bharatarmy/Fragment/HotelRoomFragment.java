package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.CityHotelRoomTypeAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FragmentHotelRoomBinding;

import java.util.ArrayList;


public class HotelRoomFragment extends Fragment {

    FragmentHotelRoomBinding fragmentHotelRoomBinding;
    Context mContext;
    View rootView;
    ArrayList<TravelModel> cityHotelRoomList;

    CityHotelRoomTypeAdapter cityHotelRoomTypeAdapter;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    public HotelRoomFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HotelRoomFragment newInstance(String param1, String param2) {
        HotelRoomFragment fragment = new HotelRoomFragment();
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
        fragmentHotelRoomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_room, container, false);

        rootView = fragmentHotelRoomBinding.getRoot();
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


    public void setDataList() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        fragmentHotelRoomBinding.roomtypeRcv.setLayoutManager(staggeredGridLayoutManager);
        // room type List
        cityHotelRoomList=new ArrayList<TravelModel>();
        cityHotelRoomList.add(new TravelModel(AppConfiguration.IMAGE_URL+"d_hotelroom1.jpg","Luxury Room",2448,3264));
        cityHotelRoomList.add(new TravelModel(AppConfiguration.IMAGE_URL+"d_hotelroom2.jpg","Luxury Grande Room",1000,750));
        cityHotelRoomList.add(new TravelModel(AppConfiguration.IMAGE_URL+"d_hotelroom3.jpg","Luxury Sea View Room",720,1102));
        cityHotelRoomList.add(new TravelModel(AppConfiguration.IMAGE_URL+"d_hotelroom4.jpg","Taj Club Room",691,750));
        cityHotelRoomList.add(new TravelModel(AppConfiguration.IMAGE_URL+"d_hotelroom5.jpg","Luxury Suite 1 Bedroom City View",1200,1600));
        cityHotelRoomList.add(new TravelModel(AppConfiguration.IMAGE_URL+"d_hotelroom6.jpg","Signature Suite",464,743));

        cityHotelRoomTypeAdapter = new CityHotelRoomTypeAdapter(mContext, cityHotelRoomList);
        fragmentHotelRoomBinding.roomtypeRcv.setAdapter(cityHotelRoomTypeAdapter);
    }
}
