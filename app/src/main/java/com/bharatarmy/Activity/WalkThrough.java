package com.bharatarmy.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bharatarmy.Adapter.BulletAdapter;
import com.bharatarmy.Adapter.BulletSingleAdapter;
import com.bharatarmy.Models.GetWalkthroughModel;
import com.bharatarmy.Models.WalkthroughData;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.HingeTransformation;
import com.bharatarmy.Utility.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class WalkThrough extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private ArrayList<Integer> layouts;
    private List<WalkthroughData> walkthroughDataList;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;
    String token;
    Context mContext;
    ShimmerFrameLayout shimmer_view_container;
    LinearLayout mainLinear;
    HingeTransformation hingeTransformation = new HingeTransformation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = WalkThrough.this;

        // Checking for first time launch - before calling setContentView()
//        prefManager = new PrefManager(this);
//        if (!prefManager.isFirstTimeLaunch()) {
//            launchHomeScreen();
//            finish();
//        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.walkthrough);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        shimmer_view_container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        viewPager.setPageTransformer(true, hingeTransformation);
        shimmer_view_container.startShimmerAnimation();

        callWalkThroughData();
    }


    private void sendRegistrationToServer(String token, String uniqueID) {
        Utils.setPref(mContext, "deviceId", uniqueID);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new ImageView[layouts.size()];

//        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
//        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < layouts.size(); i++) {
//            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.Active_dot_color)));
//            colorsInactive.add(String.valueOf(getResources().getColor(R.color.NotActive_dot_color)));
            colorsActiveList.add(String.valueOf(R.drawable.ball_scroll_primary));
            colorsInactive.add(String.valueOf(R.drawable.ball_scroll));
        }

        int width = 30;
        int height = 30;

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
//            dots[i].setText(Html.fromHtml("&#8226;"));
//            dots[i].setTextSize(35);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
            parms.setMargins(5, 5, 0, 0);
            dots[i].setLayoutParams(parms);
            dots[i].setImageResource(Integer.parseInt(colorsInactive.get(currentPage)));//   colorsInactive[currentPage]

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setImageResource(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WalkThrough.this, Splash_Screen.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);//position


            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.size() - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    // Api calling GetWalkthrough
    public void callWalkThroughData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), WalkThrough.this);
            return;
        }


//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getWalkthrough(getwalkthrough(), new retrofit.Callback<GetWalkthroughModel>() {
            @Override
            public void success(GetWalkthroughModel getWalkthroughModel, Response response) {
//                Utils.dismissDialog();
                if (getWalkthroughModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getWalkthroughModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getWalkthroughModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (getWalkthroughModel.getIsValid() == 1) {
                    if (getWalkthroughModel.getData() != null) {
                        // layouts of all welcome sliders
                        // add few more layouts if you want
                        layouts = new ArrayList<Integer>();
                        walkthroughDataList = new ArrayList<WalkthroughData>();

                        walkthroughDataList = getWalkthroughModel.getData();
                        for (int i = 0; i < getWalkthroughModel.getData().size(); i++) {
                            layouts.add(getWalkthroughModel.getData().size());
                        }

                        // adding bottom dots
                        addBottomDots(0);

                        // making notification bar transparent
                        changeStatusBarColor();

                        myViewPagerAdapter = new MyViewPagerAdapter();
                        viewPager.setAdapter(myViewPagerAdapter);
                        //  myViewPagerAdapter.notifyDataSetChanged();
                        shimmer_view_container.stopShimmerAnimation();
                        shimmer_view_container.setVisibility(View.GONE);

                        btnNext.setVisibility(View.VISIBLE);
                        btnSkip.setVisibility(View.VISIBLE);

                        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

                        btnSkip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent login = new Intent(WalkThrough.this, LoginNewActivity.class);
                                startActivity(login);
//                                overridePendingTransition(R.anim.slide_in_left,0);
                            }
                        });

                        btnNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // checking for last page
                                // if last page home screen will be launched
                                int current = getItem(+1);
                                if (current < layouts.size()) {
                                    // move to next screen
                                    viewPager.setCurrentItem(current);
                                } else {
                                    Intent login = new Intent(WalkThrough.this, LoginNewActivity.class);
                                    startActivity(login);
//                                    overridePendingTransition(R.anim.slide_in_left,0);
                                }
                            }
                        });
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

    private Map<String, String> getwalkthrough() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private ImageView header_img, banner_img;
        private TextView header_txt, heading_txt, heading_below_txt, heading_sub_txt;
        private RecyclerView gridViewList;
        private RecyclerView recyclerViewList;
        BulletAdapter bulletAdapter;
        BulletSingleAdapter bulletSingleAdapter;


        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.welcome_slide1, container, false);

            header_img = (ImageView) view.findViewById(R.id.header_img);
            banner_img = (ImageView) view.findViewById(R.id.banner_img);

            header_txt = (TextView) view.findViewById(R.id.header_txt);
            heading_txt = (TextView) view.findViewById(R.id.heading_txt);
            heading_below_txt = (TextView) view.findViewById(R.id.heading_below_txt);
            heading_sub_txt = (TextView) view.findViewById(R.id.heading_sub_txt);

            gridViewList = (RecyclerView) view.findViewById(R.id.bullet_list);
            recyclerViewList = (RecyclerView) view.findViewById(R.id.bullet_Rlist);


            if (walkthroughDataList.get(position).getBulletLayoutType() == 2) {
                gridViewList.setVisibility(View.VISIBLE);
                recyclerViewList.setVisibility(View.GONE);
                bulletAdapter = new BulletAdapter(mContext, walkthroughDataList.get(position).getBulletsPoint());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                gridViewList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                gridViewList.setItemAnimator(null);
                gridViewList.setAdapter(bulletAdapter);
                //notifyDataSetChanged();
            } else {

                gridViewList.setVisibility(View.GONE);
                recyclerViewList.setVisibility(View.VISIBLE);
                bulletSingleAdapter = new BulletSingleAdapter(mContext, walkthroughDataList.get(position).getBulletsPoint());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewList.setLayoutManager(mLayoutManager);
                recyclerViewList.setItemAnimator(null);
//                ((SimpleItemAnimator) recyclerViewList.getItemAnimator()).setSupportsChangeAnimations(false);
                recyclerViewList.setAdapter(bulletSingleAdapter);
                //   bulletSingleAdapter.notifyDataSetChanged();
            }

            Picasso.with(mContext)
                    .load(walkthroughDataList.get(position).getCategoryImageURL())
                    .into(header_img);
            Picasso.with(mContext)
                    .load(walkthroughDataList.get(position).getBannerImageURL())
                    .placeholder(R.drawable.progress_animation)
                    .into(banner_img);


            header_txt.setText(walkthroughDataList.get(position).getCategoryName());

            heading_txt.setText(walkthroughDataList.get(position).getHeaderText());
            if (!walkthroughDataList.get(position).getParagraphText().equals("")) {
                heading_below_txt.setVisibility(View.VISIBLE);
                heading_below_txt.setText(walkthroughDataList.get(position).getParagraphText());
            } else {
                heading_below_txt.setVisibility(View.GONE);
            }

            if (!walkthroughDataList.get(position).getSubHeaderText().equals("")) {
                heading_sub_txt.setVisibility(View.VISIBLE);
                heading_sub_txt.setText(walkthroughDataList.get(position).getSubHeaderText());
            } else {
                heading_sub_txt.setVisibility(View.GONE);
            }


            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }
}