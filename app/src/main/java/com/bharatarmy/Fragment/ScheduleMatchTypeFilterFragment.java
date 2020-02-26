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

import com.bharatarmy.Adapter.ScheduleFilterMatchTypeAdapter;
import com.bharatarmy.Adapter.ScheduleFilterStadiumAdapter;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentScheduleMatchTypeFilterBinding;
import com.bharatarmy.databinding.FragmentScheduleStadiumFilterBinding;

import java.util.List;


public class ScheduleMatchTypeFilterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentScheduleMatchTypeFilterBinding fragmentScheduleMatchTypeFilterBinding;
    private View rootView;
    private Context mContext;

    ScheduleFilterMatchTypeAdapter scheduleFilterMatchTypeAdapter;
    RegisterIntrestFilterDataModel registerIntrestFilterDataMode;
    List<InquiryStatusModel> teamMatchTypelist;


    public ScheduleMatchTypeFilterFragment(RegisterIntrestFilterDataModel registerIntrestFilterDataMode) {
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
        fragmentScheduleMatchTypeFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_match_type_filter, container, false);

        rootView = fragmentScheduleMatchTypeFilterBinding.getRoot();
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

        teamMatchTypelist=registerIntrestFilterDataMode.getStadiums();
        for (int i=0;i<teamMatchTypelist.size();i++){
            if (teamMatchTypelist.get(i).getVenueSelected()!=null){
                if (teamMatchTypelist.get(i).getVenueSelected().equalsIgnoreCase("1")){
                    teamMatchTypelist.get(i).setVenueSelected("1");
                }else{
                    teamMatchTypelist.get(i).setVenueSelected("0");
                }
            }else{
                teamMatchTypelist.get(i).setVenueSelected("0");
            }

        }
        teamMatchTypelist=registerIntrestFilterDataMode.getStadiums();
        scheduleFilterMatchTypeAdapter = new ScheduleFilterMatchTypeAdapter(mContext, teamMatchTypelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentScheduleMatchTypeFilterBinding.matchTypeRcv.setLayoutManager(mLayoutManager);
        fragmentScheduleMatchTypeFilterBinding.matchTypeRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentScheduleMatchTypeFilterBinding.matchTypeRcv.setAdapter(scheduleFilterMatchTypeAdapter);

    }
}
