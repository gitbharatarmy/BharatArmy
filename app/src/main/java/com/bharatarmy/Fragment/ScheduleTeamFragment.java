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

import com.bharatarmy.Adapter.MatchFilterTeamAdapter;
import com.bharatarmy.Adapter.ScheduleFilterTeamAdapter;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchFilterTeamBinding;
import com.bharatarmy.databinding.FragmentScheduleTeamBinding;

import java.util.List;


public class ScheduleTeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentScheduleTeamBinding fragmentScheduleTeamBinding;
    private View rootView;
    private Context mContext;
    List<InquiryStatusModel> matchteamList;
    ScheduleFilterTeamAdapter scheduleFilterTeamAdapter;
    RegisterIntrestFilterDataModel tournamentotherDataModel;



    public ScheduleTeamFragment(RegisterIntrestFilterDataModel tournamentotherDataModel) {
        // Required empty public constructor

        this.tournamentotherDataModel= tournamentotherDataModel;
    }


//    // TODO: Rename and change types and number of parameters
//    public static MatchFilterTeamFragment newInstance(String param1, String param2) {
//        MatchFilterTeamFragment fragment = new MatchFilterTeamFragment(registerIntrestFilterDataMode);
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
        fragmentScheduleTeamBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_team, container, false);

        rootView = fragmentScheduleTeamBinding.getRoot();
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
        matchteamList=tournamentotherDataModel.getCountries();
        for (int i=0;i<matchteamList.size();i++){
            if (matchteamList.get(i).getTeamSelected()!=null){
                if (matchteamList.get(i).getTeamSelected().equalsIgnoreCase("1")){
                    matchteamList.get(i).setTeamSelected("1");
                }else{
                    matchteamList.get(i).setTeamSelected("0");
                }
            }else{
                matchteamList.get(i).setTeamSelected("0");
            }

        }
        matchteamList=tournamentotherDataModel.getCountries();
        scheduleFilterTeamAdapter = new ScheduleFilterTeamAdapter(mContext, matchteamList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentScheduleTeamBinding.teamsRcv.setLayoutManager(mLayoutManager);
        fragmentScheduleTeamBinding.teamsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentScheduleTeamBinding.teamsRcv.setAdapter(scheduleFilterTeamAdapter);

    }
}
