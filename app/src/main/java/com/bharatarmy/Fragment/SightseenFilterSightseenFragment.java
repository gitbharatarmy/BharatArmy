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

import com.bharatarmy.Adapter.SightseenFilterSightseenAdapter;
import com.bharatarmy.Adapter.StadiumFilterStadiumAdapter;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentSightseenFilterSightseenBinding;
import com.bharatarmy.databinding.FragmentStadiumFilterStadiumBinding;

import java.util.ArrayList;
import java.util.List;


public class SightseenFilterSightseenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentSightseenFilterSightseenBinding fragmentSightseenFilterSightseenBinding;
    private View rootView;
    private Context mContext;

    SightseenFilterSightseenAdapter sightseenFilterSightseenAdapter;
//    RegisterIntrestFilterDataModel registerIntrestFilterDataMode;
    List<InquiryStatusModel> teamVenuelist;
    ArrayList<TravelModel> tournamentcitylist;

    public SightseenFilterSightseenFragment(ArrayList<TravelModel> tournamentcityliste) {
        // Required empty public constructor
        this.tournamentcitylist=tournamentcityliste;
    }


//    // TODO: Rename and change types and number of parameters
//    public static SightseenFilterSightseenFragment newInstance(String param1, String param2) {
//        SightseenFilterSightseenFragment fragment = new SightseenFilterSightseenFragment(registerIntrestFilterDataMode);
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
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
        fragmentSightseenFilterSightseenBinding   = DataBindingUtil.inflate(inflater, R.layout.fragment_sightseen_filter_sightseen, container, false);

        rootView = fragmentSightseenFilterSightseenBinding.getRoot();
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

//        teamVenuelist=registerIntrestFilterDataMode.getStadiums();
//        for (int i=0;i<teamVenuelist.size();i++){
//            if (teamVenuelist.get(i).getVenueSelected()!=null){
//                if (teamVenuelist.get(i).getVenueSelected().equalsIgnoreCase("1")){
//                    teamVenuelist.get(i).setVenueSelected("1");
//                }else{
//                    teamVenuelist.get(i).setVenueSelected("0");
//                }
//            }else{
//                teamVenuelist.get(i).setVenueSelected("0");
//            }
//
//        }
//        teamVenuelist=registerIntrestFilterDataMode.getStadiums();
        sightseenFilterSightseenAdapter = new SightseenFilterSightseenAdapter(mContext, tournamentcitylist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentSightseenFilterSightseenBinding.venuesRcv.setLayoutManager(mLayoutManager);
        fragmentSightseenFilterSightseenBinding.venuesRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentSightseenFilterSightseenBinding.venuesRcv.setAdapter(sightseenFilterSightseenAdapter);

    }
}
