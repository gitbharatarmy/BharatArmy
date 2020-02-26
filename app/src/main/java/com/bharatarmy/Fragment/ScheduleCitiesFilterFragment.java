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

import com.bharatarmy.Adapter.ScheduleFilterCitiesAdapter;
import com.bharatarmy.Adapter.ScheduleFilterStadiumAdapter;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentScheduleCitiesFilterBinding;
import com.bharatarmy.databinding.FragmentScheduleStadiumFilterBinding;

import java.util.List;


public class ScheduleCitiesFilterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentScheduleCitiesFilterBinding fragmentScheduleCitiesFilterBinding;
    private View rootView;
    private Context mContext;

    ScheduleFilterCitiesAdapter scheduleFilterCitiesAdapter;
    RegisterIntrestFilterDataModel registerIntrestFilterDataMode;
    List<InquiryStatusModel> teamcitieslist;


    public ScheduleCitiesFilterFragment(RegisterIntrestFilterDataModel registerIntrestFilterDataMode) {
        // Required empty public constructor
        this.registerIntrestFilterDataMode=registerIntrestFilterDataMode;
    }


//    // TODO: Rename and change types and number of parameters
//    public static MatchFilterVenuesFragment newInstance(String param1, String param2) {
//        MatchFilterVenuesFragment fragment = new MatchFilterVenuesFragment(registerIntrestFilterDataMode);
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
        fragmentScheduleCitiesFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_cities_filter, container, false);

        rootView = fragmentScheduleCitiesFilterBinding.getRoot();
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

        teamcitieslist=registerIntrestFilterDataMode.getStadiums();
        for (int i=0;i<teamcitieslist.size();i++){
            if (teamcitieslist.get(i).getVenueSelected()!=null){
                if (teamcitieslist.get(i).getVenueSelected().equalsIgnoreCase("1")){
                    teamcitieslist.get(i).setVenueSelected("1");
                }else{
                    teamcitieslist.get(i).setVenueSelected("0");
                }
            }else{
                teamcitieslist.get(i).setVenueSelected("0");
            }

        }
        teamcitieslist=registerIntrestFilterDataMode.getStadiums();
        scheduleFilterCitiesAdapter = new ScheduleFilterCitiesAdapter(mContext, teamcitieslist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentScheduleCitiesFilterBinding.citiesRcv.setLayoutManager(mLayoutManager);
        fragmentScheduleCitiesFilterBinding.citiesRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentScheduleCitiesFilterBinding.citiesRcv.setAdapter(scheduleFilterCitiesAdapter);

    }
}
