package com.bharatarmy.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.BulletAdapter;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentImageBinding;

import java.util.ArrayList;
import java.util.List;


public class ImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentImageBinding fragmentImageBinding;
    private View rootView;
    private Context mContext;
    ImageListAdapter imageListAdapter;
    List<String> imageList = new ArrayList<>();
    boolean isLoading = false;

    public ImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(String param1, String param2) {
        ImageFragment fragment = new ImageFragment();
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
        fragmentImageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false);

        rootView = fragmentImageBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setUserVisibleHint(true);
        return rootView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
//            setTodayschedule();
//            populateData();
            setDataValue();

            initScrollListener();
        }
        // execute your data loading logic.
    }

    private void populateData() {
        int i = 0;
        while (i < 10) {
            imageList.add("Item " + i);
            i++;
        }
    }

    public void setDataValue() {
//        imageList = new ArrayList<>();
////        for (int i=0;i<10;i++){
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/1.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/2.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/3.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/4.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/5.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/6.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/7.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/8.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/9.jpeg");

        imageListAdapter = new ImageListAdapter(mContext, imageList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        fragmentImageBinding.imageRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        fragmentImageBinding.imageRcyList.setAdapter(imageListAdapter);
    }

    private void initScrollListener() {
        fragmentImageBinding.imageRcyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == imageList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        imageList.add(null);
        imageListAdapter.notifyItemInserted(imageList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageList.remove(imageList.size() - 1);
                int scrollPosition = imageList.size();
                imageListAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {
                    imageList.add("Item " + currentSize);
                    currentSize++;
                }

                imageListAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);


    }
}


