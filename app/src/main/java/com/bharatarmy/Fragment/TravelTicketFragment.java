package com.bharatarmy.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.TravelTicketListAdapter;
import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.databinding.FragmentTravelTicketBinding;

import java.util.ArrayList;
import java.util.List;

public class TravelTicketFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View rootView;
    FragmentTravelTicketBinding travelTicketBinding;
    Context mContext;
    public List<TravelDetailModel> ticketArray,hospitalityArray,transferArray;

    TravelTicketListAdapter travelTicketListAdapter;
    public TravelTicketFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TravelTicketFragment newInstance(String param1, String param2) {
        TravelTicketFragment fragment = new TravelTicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        travelTicketBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_travel_ticket, container, false);

        rootView = travelTicketBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        setUserVisibleHint(true);
        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here
//            setDataValue();

        }
    }

//    public void setDataValue() {
//        ticketArray = new ArrayList<TravelDetailModel>();
//        for (int i=0;i<10;i++){
//            ticketArray.add(new TravelDetailModel(R.drawable.gold_images));
//        }
//
//        travelTicketListAdapter = new TravelTicketListAdapter(mContext, ticketArray);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//        travelTicketBinding.ticketDetailRcvList.setLayoutManager(mLayoutManager);
//        travelTicketBinding.ticketDetailRcvList.setItemAnimator(new DefaultItemAnimator());
//        travelTicketBinding.ticketDetailRcvList.setAdapter(travelTicketListAdapter);
//    }

}
