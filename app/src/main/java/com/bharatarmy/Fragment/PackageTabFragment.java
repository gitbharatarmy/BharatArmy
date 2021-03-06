package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.TravelPacakgeTabAdapter;
import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.Models.TravelMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentPackageTabBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class PackageTabFragment extends Fragment {
    FragmentPackageTabBinding fragmentPackageTabBinding;
    private View rootView;
    Context mContext;
    TravelPacakgeTabAdapter travelPacakgeTabAdapter;
    List<TravelDetailModel> travelPacakgeTabList;
    HashMap<Integer, List<TravelDetailModel>> storeData = new HashMap<>();
    int count = 0;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;
    public PackageTabFragment() {
    }

    public static PackageTabFragment newInstance() {//List<TravelDetailModel> travelPacakgeTabList
        PackageTabFragment pane = new PackageTabFragment();
        Bundle args = new Bundle();
//        args.putSerializable("List", (Serializable) travelPacakgeTabList);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPackageTabBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_package_tab, container, false);

        rootView = fragmentPackageTabBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setUserVisibleHint(true);
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            fragmentPackageTabBinding.pacakgeDayRcv.setVisibility(View.GONE);
            fragmentPackageTabBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
            fragmentPackageTabBinding.shimmerViewContainer.startShimmerAnimation();
            callTabPacakgeDetailData();
        }
    }

    // Api calling GetTabPacakgeDetailData
    public void callTabPacakgeDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getItineraryDetailByDayNo(getTabDetailData(), new retrofit.Callback<TravelMainModel>() {
            @Override
            public void success(TravelMainModel travelMainModel, Response response) {
                Utils.dismissDialog();
                if (travelMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (travelMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (travelMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (travelMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(travelMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(travelMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(travelMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
                    if (travelMainModel.getData() != null) {
                        travelPacakgeTabList = travelMainModel.getData();
                        fragmentPackageTabBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentPackageTabBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentPackageTabBinding.pacakgeDayRcv.setVisibility(View.VISIBLE);
                        setDataList();


                    }

                }
            }

            @Override

            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });


    }

    private Map<String, String> getTabDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("ItineraryId", "1");
        map.put("DayNo", AppConfiguration.tabPosition);
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void setDataList() {
        travelPacakgeTabAdapter = new TravelPacakgeTabAdapter(mContext, travelPacakgeTabList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentPackageTabBinding.pacakgeDayRcv.setLayoutManager(mLayoutManager);
        fragmentPackageTabBinding.pacakgeDayRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentPackageTabBinding.pacakgeDayRcv.setAdapter(travelPacakgeTabAdapter);
//        travelPacakgeTabAdapter.notifyDataSetChanged();

    }
}
