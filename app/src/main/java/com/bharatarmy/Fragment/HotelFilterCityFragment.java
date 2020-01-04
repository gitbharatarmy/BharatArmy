package com.bharatarmy.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.HotelFilterCityAdapter;
import com.bharatarmy.Adapter.HotelFilterTeamAdapter;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentHotelFilterCityBinding;
import com.bharatarmy.databinding.FragmentHotelFilterTeamBinding;

import java.util.ArrayList;


public class HotelFilterCityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentHotelFilterCityBinding fragmentHotelFilterCityBinding;
    private View rootView;
    private Context mContext;
    //    List<InquiryStatusModel> matchteamList;
    HotelFilterCityAdapter hotelFilterCityAdapter;
    RegisterIntrestFilterDataModel tournamentotherDataModel;
    ArrayList<TravelModel> tournamnetcitylist;

    public HotelFilterCityFragment(ArrayList<TravelModel> tournamnetcitylist) {
        this.tournamnetcitylist=tournamnetcitylist;
    }


//    public HotelFilterCityFragment(RegisterIntrestFilterDataModel tournamentotherDataModel) {
//        // Required empty public constructor
//
//        this.tournamentotherDataModel= tournamentotherDataModel;
//    }

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
        fragmentHotelFilterCityBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_filter_city, container, false);

        rootView = fragmentHotelFilterCityBinding.getRoot();
        mContext = getActivity().getApplicationContext();


        setUserVisibleHint(true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here
            setDataValue();
        }
    }

    public void setDataValue(){
//        matchteamList=tournamentotherDataModel.getCountries();
//        for (int i=0;i<matchteamList.size();i++){
//            if (matchteamList.get(i).getTeamSelected()!=null){
//                if (matchteamList.get(i).getTeamSelected().equalsIgnoreCase("1")){
//                    matchteamList.get(i).setTeamSelected("1");
//                }else{
//                    matchteamList.get(i).setTeamSelected("0");
//                }
//            }else{
//                matchteamList.get(i).setTeamSelected("0");
//            }
//
//        }

        hotelFilterCityAdapter = new HotelFilterCityAdapter(mContext, tournamnetcitylist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentHotelFilterCityBinding.cityRcv.setLayoutManager(mLayoutManager);
        fragmentHotelFilterCityBinding.cityRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentHotelFilterCityBinding.cityRcv.setAdapter(hotelFilterCityAdapter);

    }
}
