package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bharatarmy.Adapter.BAShopProductColorAdapter;
import com.bharatarmy.Adapter.BAShopProductSizeAdapter;
import com.bharatarmy.Adapter.InquiryOrderTypeAdapter;
import com.bharatarmy.Models.BAShopListModel;
import com.bharatarmy.Models.BAShopMainModel;
import com.bharatarmy.Models.BAShopProductSpecification;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityBAShopDetailBinding;
import com.bharatarmy.databinding.ActivityTravelCitySightseenDetailBinding;
import com.bharatarmy.databinding.HotelGalleryViewpageBinding;
import com.bharatarmy.databinding.ShopGalleryViewpageBinding;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.appbar.AppBarLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BAShopDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityBAShopDetailBinding activityBAShopDetailBinding;
    Context mContext;
    private MyShopProductGalleryViewPagerAdapter myShopProductGalleryViewPagerAdapter;
    BAShopProductSizeAdapter baShopProductSizeAdapter;
    BAShopProductColorAdapter baShopProductColorAdapter;
    private TextView[] dots;
    List<BAShopProductSpecification> shopproductdetaillist;
    List<BAShopProductSpecification> productsizeList;
    List<BAShopProductSpecification> productcolorList;
    List<BAShopProductSpecification> productspecificationList;
    List<BAShopListModel> shopproductList;
    String selectedProductIdStr, selectedProductNameStr;

    //    use for ticket price
    int bashopcount = 1;
    double totalamount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBAShopDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_b_a_shop_detail);
        mContext = BAShopDetailActivity.this;
        init();
        setListiner();

    }

    public void init() {
        selectedProductIdStr = getIntent().getStringExtra("selectedProductId");
        selectedProductNameStr = getIntent().getStringExtra("selectedProductName");

        activityBAShopDetailBinding.shimmerViewContainerMain.startShimmerAnimation();

        CallBAShopDetailData();
    }

    public void setListiner() {
        activityBAShopDetailBinding.removetocartLinear.setOnClickListener(this);
        activityBAShopDetailBinding.addtocartLinear.setOnClickListener(this);
        activityBAShopDetailBinding.bashopMinusLayout.setOnClickListener(this);
        activityBAShopDetailBinding.bashopPlusLayout.setOnClickListener(this);

        setSupportActionBar(activityBAShopDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        activityBAShopDetailBinding.backImg.setOnClickListener(this);
        activityBAShopDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    activityBAShopDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityBAShopDetailBinding.collapsingToolbar.setTitle(selectedProductNameStr);
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityBAShopDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityBAShopDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityBAShopDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityBAShopDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    activityBAShopDetailBinding.addcarticon.cartLayout.setVisibility(View.VISIBLE);
                    activityBAShopDetailBinding.toolbarTitleTextview.setVisibility(View.VISIBLE);
                    activityBAShopDetailBinding.toolbarTitleTextview.setText(selectedProductNameStr);
                    isShow = true;
                } else if (isShow) {
                    activityBAShopDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityBAShopDetailBinding.collapsingToolbar.setTitle(" ");
                    activityBAShopDetailBinding.addcarticon.cartLayout.setVisibility(View.GONE);
                    activityBAShopDetailBinding.toolbarTitleTextview.setVisibility(View.GONE);
                    isShow = false;
                }

            }
        });


    }

    // Api calling GetBAShopDetailData
    public void CallBAShopDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), BAShopDetailActivity.this);
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
                    shopproductList = response.body().getShopData();
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
        activityBAShopDetailBinding.shimmerViewContainerMain.stopShimmerAnimation();
        activityBAShopDetailBinding.shimmerViewContainerMain.setVisibility(View.GONE);

        for (int i = 0; i < shopproductList.size(); i++) {
            if (selectedProductIdStr.equalsIgnoreCase(String.valueOf(shopproductList.get(i).getBAShopListId()))) {
                shopproductdetaillist = shopproductList.get(i).getBAShopProductDetailImage();
                productsizeList = shopproductList.get(i).getBAShopProductSize();
                productspecificationList = shopproductList.get(i).getBAShopProductSpecification();
                productcolorList = shopproductList.get(i).getbAShopProductDetailColor();
                //        fill product name, description, price
                activityBAShopDetailBinding.productNnameTxt.setText(shopproductList.get(i).getBAShopProductName());
//                activityBAShopDetailBinding.priceTxt.setText(shopproductList.get(i).getBAShopProductPrice());
                activityBAShopDetailBinding.productDescTxt.setText(shopproductList.get(i).getBAShopProductDescription());

            }
        }

//        fill the shop product gallery
        addBottomDots(0);
        myShopProductGalleryViewPagerAdapter = new MyShopProductGalleryViewPagerAdapter();
        activityBAShopDetailBinding.shopGalleryViewpager.setAdapter(myShopProductGalleryViewPagerAdapter);
        activityBAShopDetailBinding.shopGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

//        fill product specification
        if (productspecificationList.size() != 0 && productspecificationList != null) {
            activityBAShopDetailBinding.productSpecificationViewtxt.setVisibility(View.VISIBLE);
            activityBAShopDetailBinding.productSpecificationLinear.setVisibility(View.VISIBLE);
            for (int i = 0; i < productspecificationList.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.ba_shop_specification_list, null);

                TextView txt = (TextView) view.findViewById(R.id.inclusion_txt);

                txt.setText(productspecificationList.get(i).getBAShopProductSpecification());

                activityBAShopDetailBinding.productSpecificationLinear.addView(view);
            }

        } else {
            activityBAShopDetailBinding.productSpecificationViewtxt.setVisibility(View.GONE);
            activityBAShopDetailBinding.productSpecificationLinear.setVisibility(View.GONE);
        }

//        fill product size
        if (productsizeList.size() != 0 && productsizeList != null) {
            activityBAShopDetailBinding.productSizeTxt.setVisibility(View.VISIBLE);
            activityBAShopDetailBinding.shopProductSizelist.setVisibility(View.VISIBLE);
            baShopProductSizeAdapter = new BAShopProductSizeAdapter(mContext, productsizeList);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setAlignItems(AlignItems.BASELINE);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            activityBAShopDetailBinding.shopProductSizelist.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
            activityBAShopDetailBinding.shopProductSizelist.setAdapter(baShopProductSizeAdapter);
        } else {
            activityBAShopDetailBinding.productSizeTxt.setVisibility(View.GONE);
            activityBAShopDetailBinding.shopProductSizelist.setVisibility(View.GONE);
        }
//        fill product color
        if (productcolorList.size() != 0 && productcolorList != null) {
            activityBAShopDetailBinding.productColorTxt.setVisibility(View.VISIBLE);
            activityBAShopDetailBinding.shopProductColorlist.setVisibility(View.VISIBLE);
            baShopProductColorAdapter = new BAShopProductColorAdapter(mContext, productcolorList);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setAlignItems(AlignItems.BASELINE);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            activityBAShopDetailBinding.shopProductColorlist.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
            activityBAShopDetailBinding.shopProductColorlist.setAdapter(baShopProductColorAdapter);
        } else {
            activityBAShopDetailBinding.productColorTxt.setVisibility(View.GONE);
            activityBAShopDetailBinding.shopProductColorlist.setVisibility(View.GONE);
        }


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

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                BAShopDetailActivity.this.finish();
                break;
            case R.id.addtocart_linear:
                Utils.handleClickEvent(mContext, activityBAShopDetailBinding.addtocartLinear);
                activityBAShopDetailBinding.addtocartLinear.setVisibility(View.GONE);
                activityBAShopDetailBinding.removetocartLinear.setVisibility(View.VISIBLE);
                Utils.animationAdd(mContext, activityBAShopDetailBinding.addcarticon.cartLayout,
                        activityBAShopDetailBinding.toolbar, activityBAShopDetailBinding.addcarticon.cartImage,
                        activityBAShopDetailBinding.addcarticon.cartCountItemTxt, activityBAShopDetailBinding.baShopMaincontent,
                        null, null, 0, "bashopdetail");
                break;
            case R.id.removetocart_linear:
                Utils.handleClickEvent(mContext, activityBAShopDetailBinding.removetocartLinear);
                activityBAShopDetailBinding.addtocartLinear.setVisibility(View.VISIBLE);
                activityBAShopDetailBinding.removetocartLinear.setVisibility(View.GONE);
                Utils.removeCartItemCount(mContext, activityBAShopDetailBinding.addcarticon.cartCountItemTxt);
                break;
            case R.id.bashop_minus_layout:
                if (bashopcount != 1) {
                    bashopcount = bashopcount - 1;
                    activityBAShopDetailBinding.countOfItem.setText(String.valueOf(bashopcount));
                    totalamount = 500 * bashopcount;
                    activityBAShopDetailBinding.priceTxt.setText(String.valueOf(roundTwoDecimals(totalamount)));
                } else {
                    activityBAShopDetailBinding.bashopMinusLayout.setClickable(false);
                }
                break;
            case R.id.bashop_plus_layout:
                activityBAShopDetailBinding.bashopMinusLayout.setClickable(true);
                bashopcount = bashopcount + 1;
                totalamount = 500 * bashopcount;
                activityBAShopDetailBinding.priceTxt.setText(String.valueOf(roundTwoDecimals(totalamount)));
                activityBAShopDetailBinding.countOfItem.setText(String.valueOf(bashopcount));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BAShopDetailActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[shopproductdetaillist.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < shopproductdetaillist.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        activityBAShopDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityBAShopDetailBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return activityBAShopDetailBinding.shopGalleryViewpager.getCurrentItem() + i;
    }

    public class MyShopProductGalleryViewPagerAdapter extends PagerAdapter {

        public MyShopProductGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            ShopGalleryViewpageBinding shopGalleryViewpageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.shop_gallery_viewpage, parent, false);


            Utils.setImageInImageView(shopproductdetaillist.get(position).getBAShopProductImage(), shopGalleryViewpageBinding.shopGalleryFull, mContext);

            Log.d("ShopGalleeryAdapter : ", shopproductdetaillist.get(position).getBAShopProductImage());

            parent.addView(shopGalleryViewpageBinding.getRoot());

            return shopGalleryViewpageBinding.getRoot();
        }

        @Override
        public int getCount() {
            return shopproductdetaillist.size();
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
}

