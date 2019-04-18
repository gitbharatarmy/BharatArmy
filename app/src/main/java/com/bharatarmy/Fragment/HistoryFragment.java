package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Activity.HistoryDetailActivity;
import com.bharatarmy.Adapter.HistoryMainListAdapter;
import com.bharatarmy.Interfaces.history_detail_click;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentHistoryBinding historyBinding;
    private View rootView;
    private Context mContext;
    HistoryMainListAdapter historymainlistAdapter;
    List<String> invoiceList;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        // Inflate the layout for this fragment
        historyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);

        rootView = historyBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListiner();
        setDataValue();
        return rootView;
    }

    public void setListiner(){}

    public void setDataValue(){
        invoiceList=new ArrayList<>();
        for (int i=0;i<=15;i++){
            invoiceList.add(String.valueOf(i));
        }

        historymainlistAdapter = new HistoryMainListAdapter(mContext, invoiceList, new history_detail_click() {
            @Override
            public void gethistorymoredetailClick() {
                  Intent ihistory=new Intent(getActivity(), HistoryDetailActivity.class);
                  startActivity(ihistory);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        historyBinding.armyHistoryRcyList.setLayoutManager(mLayoutManager);
        historyBinding.armyHistoryRcyList.setItemAnimator(new DefaultItemAnimator());
        historyBinding.armyHistoryRcyList.setAdapter(historymainlistAdapter);

    }


}
