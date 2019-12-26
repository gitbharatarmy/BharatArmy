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

import com.bharatarmy.Adapter.MatchFilterVenuesAdapter;
import com.bharatarmy.Adapter.ScheduleFilterStadiumAdapter;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchFilterVenuesBinding;
import com.bharatarmy.databinding.FragmentScheduleStadiumBinding;

import java.util.List;


public class ScheduleStadiumFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentScheduleStadiumBinding fragmentScheduleStadiumBinding;
    private View rootView;
    private Context mContext;

    ScheduleFilterStadiumAdapter scheduleFilterStadiumAdapter;
    RegisterIntrestFilterDataModel registerIntrestFilterDataMode;
    List<InquiryStatusModel> teamVenuelist;


    public ScheduleStadiumFragment(RegisterIntrestFilterDataModel registerIntrestFilterDataMode) {
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
        fragmentScheduleStadiumBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_stadium, container, false);

        rootView = fragmentScheduleStadiumBinding.getRoot();
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

        teamVenuelist=registerIntrestFilterDataMode.getStadiums();
        for (int i=0;i<teamVenuelist.size();i++){
            if (teamVenuelist.get(i).getVenueSelected()!=null){
                if (teamVenuelist.get(i).getVenueSelected().equalsIgnoreCase("1")){
                    teamVenuelist.get(i).setVenueSelected("1");
                }else{
                    teamVenuelist.get(i).setVenueSelected("0");
                }
            }else{
                teamVenuelist.get(i).setVenueSelected("0");
            }

        }
        teamVenuelist=registerIntrestFilterDataMode.getStadiums();
        scheduleFilterStadiumAdapter = new ScheduleFilterStadiumAdapter(mContext, teamVenuelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentScheduleStadiumBinding.venuesRcv.setLayoutManager(mLayoutManager);
        fragmentScheduleStadiumBinding.venuesRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentScheduleStadiumBinding.venuesRcv.setAdapter(scheduleFilterStadiumAdapter);

    }
}
