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

import com.bharatarmy.Adapter.HotelFilterRatingAdapter;
import com.bharatarmy.Adapter.HotelFilterTeamAdapter;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentHotelFilterRatingBinding;
import com.bharatarmy.databinding.FragmentHotelFilterTeamBinding;

import java.util.ArrayList;


public class HotelFilterRatingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentHotelFilterRatingBinding fragmentHotelFilterRatingBinding;
    private View rootView;
    private Context mContext;
    //    List<InquiryStatusModel> matchteamList;
    HotelFilterRatingAdapter hotelFilterRatingAdapter;
    RegisterIntrestFilterDataModel tournamentotherDataModel;
    ArrayList<TravelModel> tournamnetratinglist;

    public HotelFilterRatingFragment(ArrayList<TravelModel> tournamnetratinglist) {
        this.tournamnetratinglist=tournamnetratinglist;
    }


//    public HotelFilterRatingFragment(RegisterIntrestFilterDataModel tournamentotherDataModel) {
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
        fragmentHotelFilterRatingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_filter_rating, container, false);

        rootView = fragmentHotelFilterRatingBinding.getRoot();
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

        for (int i=0;i<tournamnetratinglist.size();i++){
            if (tournamnetratinglist.get(i).getMatchteamVenues()!=null){
                if (tournamnetratinglist.get(i).getMatchteamVenues().equalsIgnoreCase("1")){
                    tournamnetratinglist.get(i).setMatchteamVenues("1");
                }else{
                    tournamnetratinglist.get(i).setMatchteamVenues("0");
                }
            }else{
                tournamnetratinglist.get(i).setMatchteamVenues("0");
            }

        }

        hotelFilterRatingAdapter = new HotelFilterRatingAdapter(mContext, tournamnetratinglist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentHotelFilterRatingBinding.ratingRcv.setLayoutManager(mLayoutManager);
        fragmentHotelFilterRatingBinding.ratingRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentHotelFilterRatingBinding.ratingRcv.setAdapter(hotelFilterRatingAdapter);

    }
}
