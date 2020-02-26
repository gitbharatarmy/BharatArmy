package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bharatarmy.Adapter.HospitalityDetailFixturesAdapter;
import com.bharatarmy.Adapter.RelatedHospitalityDetailAdapter;
import com.bharatarmy.CallTwoAnimationCartAddItemMethod;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityTravelMatchTicketAndHospitalityDetailBinding;
import com.bharatarmy.databinding.HotelGalleryViewpageBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// remove code 24-02-2020 with backup
public class TravelMatchTicketAndHospitalityDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchTicketAndHospitalityDetailBinding activityTravelMatchTicketAndHospitalityDetailBinding;
    Context mContext;
    //    hospitality gallery list and adapter
    ArrayList<TravelModel> travelHospitalityGalleryList;
    MyHospitalityGalleryViewPagerAdapter myHospitalityGalleryViewPagerAdapter;
    private TextView[] dots;

    //    hospitality inclusion list and adapter
    ArrayList<TravelModel> travelHospitalityInclusionList;

    //    hospitality fixtures list and adapter
    List<HomeTemplateDetailModel> travelHospitalityFixturesList;
    HospitalityDetailFixturesAdapter hospitalityDetailFixturesAdapter;

    //    related hospitality list and adapter
    List<HomeTemplateDetailModel> travelHospitalityRealtedHospitalityList;
    List<HomeTemplateDetailModel> travelHospitalityRealtedHospitalityfinalList;
    RelatedHospitalityDetailAdapter relatedHospitalityDetailAdapter;


    //    use for ticket price
    int hospitalitycount = 1;
    double totalamount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchTicketAndHospitalityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_ticket_and_hospitality_detail);
        mContext = TravelMatchTicketAndHospitalityDetailActivity.this;
        EventBus.getDefault().register(this);

        init();
        setListiner();
//        setDataValue();
    }

    public void init() {
        if (getIntent().getStringExtra("categoryName") != null) {
            activityTravelMatchTicketAndHospitalityDetailBinding.matchHospitalityTypeTagTxt.setText(getIntent().getStringExtra("categoryName"));
        }
        Utils.addCartItemCount(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartCountItemTxt);

        activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartRemoveView.setVisibility(View.GONE);
        activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartAddView.setVisibility(View.VISIBLE);

        activityTravelMatchTicketAndHospitalityDetailBinding.shimmerViewContainer.startShimmerAnimation();
        activityTravelMatchTicketAndHospitalityDetailBinding.shimmerViewContainerHospitality.startShimmerAnimation();
        GetHospitalityDetailListData();
    }

    public void setListiner() {
        activityTravelMatchTicketAndHospitalityDetailBinding.backImg.setOnClickListener(this);
        activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartAddView.setOnClickListener(this);
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityMinusLayout.setOnClickListener(this);
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityPlusLayout.setOnClickListener(this);
        activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartRemoveView.setOnClickListener(this);

        activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addcartItemIntent = new Intent(mContext, CartItemShowActivity.class);
                startActivity(addcartItemIntent);
            }
        });
    }

    public void setDataValue() {
        /*Fixture list*/
        activityTravelMatchTicketAndHospitalityDetailBinding.shimmerViewContainer.stopShimmerAnimation();
        activityTravelMatchTicketAndHospitalityDetailBinding.shimmerViewContainer.setVisibility(View.GONE);
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setVisibility(View.VISIBLE);
        hospitalityDetailFixturesAdapter = new HospitalityDetailFixturesAdapter(mContext, travelHospitalityFixturesList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                int addTocartposition = hospitalityDetailFixturesAdapter.adptercartAddPosition();
                Utils.animationAdd(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartLayout,
                        activityTravelMatchTicketAndHospitalityDetailBinding.toolbar,
                        activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartImage,
                        activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartCountItemTxt, null,
                        activityTravelMatchTicketAndHospitalityDetailBinding.mainLinear,
                        null, addTocartposition, "hospitalityfixtures");
            }
        }, new image_click() {
            @Override
            public void image_more_click() {
                Utils.removeCartItemCount(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartCountItemTxt);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setLayoutManager(layoutManager);
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setAdapter(hospitalityDetailFixturesAdapter);


        /*Related hospitality list*/
        activityTravelMatchTicketAndHospitalityDetailBinding.shimmerViewContainerHospitality.stopShimmerAnimation();
        activityTravelMatchTicketAndHospitalityDetailBinding.shimmerViewContainerHospitality.setVisibility(View.GONE);
        activityTravelMatchTicketAndHospitalityDetailBinding.relatedHospitalityRcv.setVisibility(View.VISIBLE);
        travelHospitalityRealtedHospitalityfinalList = new ArrayList<>();
        for (int i = 0; i < travelHospitalityRealtedHospitalityList.size(); i++) {
            if (!travelHospitalityRealtedHospitalityList.get(i).getHospitalityName().trim().equalsIgnoreCase(activityTravelMatchTicketAndHospitalityDetailBinding.matchHospitalityTypeTagTxt.getText().toString().trim())) {
                travelHospitalityRealtedHospitalityfinalList.add(travelHospitalityRealtedHospitalityList.get(i));
            }
        }

        relatedHospitalityDetailAdapter = new RelatedHospitalityDetailAdapter(mContext, travelHospitalityRealtedHospitalityfinalList);
        RecyclerView.LayoutManager relatedlayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelMatchTicketAndHospitalityDetailBinding.relatedHospitalityRcv.setLayoutManager(relatedlayoutManager);
        activityTravelMatchTicketAndHospitalityDetailBinding.relatedHospitalityRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHospitalityDetailBinding.relatedHospitalityRcv.setAdapter(relatedHospitalityDetailAdapter);


//    viewpager fill dataList
        travelHospitalityGalleryList = new ArrayList<TravelModel>();
        travelHospitalityGalleryList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("https://i0.wp.com/www.perrygroup.com/wp-content/uploads/2016/01/service-pic3-1.jpg", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("https://media-cdn.tripadvisor.com/media/photo-s/12/33/09/4a/boardroom.jpg", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("https://4107m27y9dp1th7n63rhxul1-wpengine.netdna-ssl.com/wp-content/uploads/2016/06/collage@2x-min.png", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("http://www.horizonhotels.com/d/horizonhotels/media/Hero_Optimized/__thumbs_1598_587_crop/85364709.jpg?1418405590", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("http://www.dietzelintl.com/wp-content/uploads/2015/10/Dietzel-Test-Banner-1100x423.jpg", "Hotel1"));


        addBottomDots(0);
        myHospitalityGalleryViewPagerAdapter = new MyHospitalityGalleryViewPagerAdapter();
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityGalleryViewpager.setAdapter(myHospitalityGalleryViewPagerAdapter);
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);


//    inclusion list
        travelHospitalityInclusionList = new ArrayList<TravelModel>();
        travelHospitalityInclusionList.add(new TravelModel(1, "Live entertainment throughout the day"));
        travelHospitalityInclusionList.add(new TravelModel(1, "Extensive menu with in room live chef sation"));
        travelHospitalityInclusionList.add(new TravelModel(1, "5 hour premium beverage package"));
        travelHospitalityInclusionList.add(new TravelModel(1, "Premium seating"));
        travelHospitalityInclusionList.add(new TravelModel(1, "Access to win money can't buy experiences"));


        for (int i = 0; i < travelHospitalityInclusionList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.hospitality_inclusion_list, null);

            TextView txt = (TextView) view.findViewById(R.id.inclusion_txt);

            txt.setText(travelHospitalityInclusionList.get(i).getMatchteamVenues());

            activityTravelMatchTicketAndHospitalityDetailBinding.inclusionLinear.addView(view);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.bottom_cart_add_view:
                Utils.handleClickEvent(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartAddView);
                activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartAddView.setVisibility(View.GONE);
                activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartRemoveView.setVisibility(View.VISIBLE);
                Utils.animationAdd(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartLayout,
                        activityTravelMatchTicketAndHospitalityDetailBinding.toolbar,
                        activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartImage,
                        activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartCountItemTxt, null,
                        activityTravelMatchTicketAndHospitalityDetailBinding.mainLinear, null, 0, "noadapter");
                break;
            case R.id.bottom_cart_remove_view:
                Utils.handleClickEvent(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartRemoveView);
                activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartAddView.setVisibility(View.VISIBLE);
                activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartRemoveView.setVisibility(View.GONE);
                Utils.removeCartItemCount(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartCountItemTxt);
                break;
            case R.id.hospitality_minus_layout:
                if (hospitalitycount != 1) {
                    hospitalitycount = hospitalitycount - 1;
                    activityTravelMatchTicketAndHospitalityDetailBinding.countOfItem.setText(String.valueOf(hospitalitycount));
                    totalamount = 500 * hospitalitycount;
                    activityTravelMatchTicketAndHospitalityDetailBinding.priceTxt.setText(String.valueOf(roundTwoDecimals(totalamount)));
                } else {
                    activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityMinusLayout.setClickable(false);
                }
                break;
            case R.id.hospitality_plus_layout:
                activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityMinusLayout.setClickable(true);
                hospitalitycount = hospitalitycount + 1;
                totalamount = 500 * hospitalitycount;
                activityTravelMatchTicketAndHospitalityDetailBinding.priceTxt.setText(String.valueOf(roundTwoDecimals(totalamount)));
                activityTravelMatchTicketAndHospitalityDetailBinding.countOfItem.setText(String.valueOf(hospitalitycount));
                break;
        }
    }

    // Api calling GetHospitalityDetailListData
    public void GetHospitalityDetailListData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), TravelMatchTicketAndHospitalityDetailActivity.this);
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<TravelDataModel> call = api.getHospitalityList("http://www.mocky.io/v2/5e53b9282e00007c002dae2b");

        call.enqueue(new Callback<TravelDataModel>() {
            @Override
            public void onResponse(Call<TravelDataModel> call, retrofit2.Response<TravelDataModel> response) {

                if (response.body().getFixturesData() != null && response.body().getRelatedHospitalityData() != null) {
                    travelHospitalityFixturesList = response.body().getFixturesData();
                    travelHospitalityRealtedHospitalityList = response.body().getRelatedHospitalityData();
                    setDataValue();
                }
            }

            @Override
            public void onFailure(Call<TravelDataModel> call, Throwable t) {
                Log.d("WatchList Error:", t.getLocalizedMessage());
            }
        });


    }


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);//position

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[travelHospitalityGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < travelHospitalityGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        activityTravelMatchTicketAndHospitalityDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityTravelMatchTicketAndHospitalityDetailBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    public class MyHospitalityGalleryViewPagerAdapter extends PagerAdapter {

        public MyHospitalityGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {

            HotelGalleryViewpageBinding hotelGalleryViewpageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.hotel_gallery_viewpage, parent, false);


            Utils.setImageInImageView(travelHospitalityGalleryList.get(position).getCityHotelAmenitiesImage(), hotelGalleryViewpageBinding.hotelGalleryImage, mContext);

            Log.d("HotelGalleeryAdapter : ", travelHospitalityGalleryList.get(position).getCityHotelAmenitiesImage());
            parent.addView(hotelGalleryViewpageBinding.getRoot());

            return hotelGalleryViewpageBinding.getRoot();
        }

        @Override
        public int getCount() {
            return travelHospitalityGalleryList.size();
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

    // use for ticket price round figure
    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }


    @Override
    protected void onResume() {
        Utils.addCartItemCount(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.addcarticon.cartCountItemTxt);
        super.onResume();
    }

    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("eventBusPosition :", "" + event.getAdapterremvoePosition());
        if (event.getAdapterListName().equalsIgnoreCase("noadapter")) {
            activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartRemoveView.setVisibility(View.GONE);
            activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartAddView.setVisibility(View.VISIBLE);
        } else {
            if (travelHospitalityFixturesList != null && travelHospitalityFixturesList.size() != 0) {
                for (int i = 0; i < travelHospitalityFixturesList.size(); i++) {
                    if (event.getAdapterremvoePosition() == i) {
                        hospitalityDetailFixturesAdapter.notifyItemChanged(i, "remove");
                    }
                }
            }
        }
    }
}
