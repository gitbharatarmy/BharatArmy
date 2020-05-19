package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bharatarmy.Adapter.BAShopAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.BAShopListModel;
import com.bharatarmy.Models.BAShopMainModel;
import com.bharatarmy.Models.BAShopProductSpecification;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.FragmentBAShopBinding;
import com.bharatarmy.speeddialView.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BAShopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentBAShopBinding fragmentBAShopBinding;
    private View rootView;
    private Context mContext;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;
    BAShopAdapter baShopAdapter;
    List<BAShopListModel> baShopListModelsList;

    SpeedDialView speedDial;
    Toolbar toolbar;
    TextView cartCountItemTxt;
    RelativeLayout cartLayoutrel;
    ImageView cartImage;
    List<BAShopProductSpecification> shopProductColor;
    List<BAShopProductSpecification> shopProductSize;

    int storypagesize = 14;
    int pageIndex = 0;
    boolean isBAShopLoading = true;
    StaggeredGridLayoutManager staggeredGridLayoutManager;


    String productNamestr,productDescStr;
//    public static BAShopFragment.OnItemClick mListener;

    public BAShopFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BAShopFragment newInstance(String param1, String param2) {
        BAShopFragment fragment = new BAShopFragment();
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
        fragmentBAShopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_b_a_shop, container, false);

        rootView = fragmentBAShopBinding.getRoot();
        mContext = getActivity().getApplicationContext();


        init();
        setListiner();
        return rootView;
    }

    public void init() {
        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);
        toolbar = getActivity().findViewById(R.id.toolbar);
        cartCountItemTxt = getActivity().findViewById(R.id.cart_count_item_txt);
        cartLayoutrel = getActivity().findViewById(R.id.cartLayout_rel);
        cartImage = getActivity().findViewById(R.id.cart_image);

        fragmentBAShopBinding.shimmerViewContainer.startShimmerAnimation();
        fragmentBAShopBinding.shopRcyList.setVisibility(View.GONE);

        CallBAShopDetailData();
    }

    public void setListiner() {


        fragmentBAShopBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentBAShopBinding.refreshView.setRefreshing(false);
//                callStoryPullData();

            }
        });


        fragmentBAShopBinding.shopRcyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


//                if (isBAShopLoading == true) {
//                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == baShopListModelsList.size() - 1) {
//                        //bottom of list!
//                        isBAShopLoading = true;
//                        pageIndex++;
//                        fragmentBAShopBinding.bottomProgressbarLayout.setVisibility(View.VISIBLE);
////                        loadMore();
//
//                    }
//                }
            }
        });


    }

    // Api calling GetBAShopDetailData
    public void CallBAShopDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<BAShopMainModel> call = api.getBAShopList("http://www.mocky.io/v2/5e97025b3000004e00b6da50");

        call.enqueue(new Callback<BAShopMainModel>() {
            @Override
            public void onResponse(Call<BAShopMainModel> call, retrofit2.Response<BAShopMainModel> response) {

                if (response.body().getShopData() != null) {
                    baShopListModelsList = response.body().getShopData();
                    setDataValueInList();
                }
            }

            @Override
            public void onFailure(Call<BAShopMainModel> call, Throwable t) {
                Log.d("BAShopList Error:", t.getLocalizedMessage());
            }
        });


    }

    public void setDataValueInList() {

        fragmentBAShopBinding.shimmerViewContainer.stopShimmerAnimation();
        fragmentBAShopBinding.shimmerViewContainer.setVisibility(View.GONE);
        fragmentBAShopBinding.shopRcyList.setVisibility(View.VISIBLE);


        baShopAdapter = new BAShopAdapter(mContext, baShopListModelsList, new image_click() {
            @Override
            public void image_more_click() {
                Utils.removeCartItemCount(mContext, cartCountItemTxt);
            }
        }, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                int addTocartposition = baShopAdapter.adptercartAddPosition();
                for (int i = 0; i < baShopListModelsList.size(); i++) {
                    if (addTocartposition == i) {
                        shopProductSize = baShopListModelsList.get(i).getBAShopProductSize();
                        shopProductColor=baShopListModelsList.get(i).getbAShopProductDetailColor();
                        productNamestr=baShopListModelsList.get(i).getBAShopProductName();
                        productDescStr=baShopListModelsList.get(i).getBAShopProductDescription();
                    }
                }
                Utils.animationShopCartAdd(mContext, cartLayoutrel, toolbar, cartImage, cartCountItemTxt,
                        fragmentBAShopBinding.bashopLinear, addTocartposition, "bashopfragment",
                        productNamestr,productDescStr,shopProductSize,shopProductColor);
            }
        });
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        fragmentBAShopBinding.shopRcyList.setLayoutManager(staggeredGridLayoutManager);
        fragmentBAShopBinding.shopRcyList.setAdapter(baShopAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (baShopListModelsList != null) {
            if (baShopListModelsList.size() != 0) {
                baShopListModelsList.clear();
            }
        }


    }
}


