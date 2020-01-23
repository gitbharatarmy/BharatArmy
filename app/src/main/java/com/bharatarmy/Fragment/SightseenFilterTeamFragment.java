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

import com.bharatarmy.Adapter.SightseenFilterTeamAdapter;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentSightseenFilterTeamBinding;

import java.util.List;


public class SightseenFilterTeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentSightseenFilterTeamBinding fragmentSightseenFilterTeamBinding;
    private View rootView;
    private Context mContext;
    List<InquiryStatusModel> matchteamList;
    SightseenFilterTeamAdapter sightseenFilterTeamAdapter;
    RegisterIntrestFilterDataModel tournamentotherDataModel;



    public SightseenFilterTeamFragment(RegisterIntrestFilterDataModel tournamentotherDataModel) {
        // Required empty public constructor

        this.tournamentotherDataModel= tournamentotherDataModel;
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
        fragmentSightseenFilterTeamBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sightseen_filter_team, container, false);

        rootView = fragmentSightseenFilterTeamBinding.getRoot();
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
        sightseenFilterTeamAdapter = new SightseenFilterTeamAdapter(mContext, matchteamList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentSightseenFilterTeamBinding.teamsRcv.setLayoutManager(mLayoutManager);
        fragmentSightseenFilterTeamBinding.teamsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentSightseenFilterTeamBinding.teamsRcv.setAdapter(sightseenFilterTeamAdapter);

    }
}
