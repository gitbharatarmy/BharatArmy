package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.CartItemShowAdapter;
import com.bharatarmy.DividerItemDecoration;
import com.bharatarmy.Models.WatchListDetailModel;
import com.bharatarmy.Models.WatchListModelDemo;
import com.bharatarmy.R;
import com.bharatarmy.RecyclerItemTouchHelper;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityCartItemShowBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartItemShowActivity extends AppCompatActivity implements View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    ActivityCartItemShowBinding activityCartItemShowBinding;
    Context mContext;


    CartItemShowAdapter cartItemShowAdapter;
    List<WatchListDetailModel> cartItemList;
    LinearLayoutManager cartlinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartItemShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart_item_show);

        mContext = CartItemShowActivity.this;

        init();
        setListiner();

    }


    public void init() {
        activityCartItemShowBinding.shimmerViewContainer.startShimmerAnimation();
        activityCartItemShowBinding.cartItemRcv.setVisibility(View.GONE);
        GetCartListDetailData();

    }

    public void setListiner() {
        activityCartItemShowBinding.backImg.setOnClickListener(this);
        activityCartItemShowBinding.nextBtn.setOnClickListener(this);
    }

    public void setDataValueInList() {
        activityCartItemShowBinding.shimmerViewContainer.stopShimmerAnimation();
        activityCartItemShowBinding.shimmerViewContainer.setVisibility(View.GONE);
        activityCartItemShowBinding.cartItemRcv.setVisibility(View.VISIBLE);
        activityCartItemShowBinding.bottomGradiantView.setVisibility(View.VISIBLE);
        activityCartItemShowBinding.bottomLinear.setVisibility(View.VISIBLE);
        Log.d("cartItemList :", "" + cartItemList.size());
        cartItemShowAdapter = new CartItemShowAdapter(mContext, cartItemList);
        cartlinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityCartItemShowBinding.cartItemRcv.setLayoutManager(cartlinearLayoutManager);
        activityCartItemShowBinding.cartItemRcv.setItemAnimator(new DefaultItemAnimator());
        activityCartItemShowBinding.cartItemRcv.addItemDecoration(new DividerItemDecoration(mContext));

        activityCartItemShowBinding.cartItemRcv.setAdapter(cartItemShowAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(activityCartItemShowBinding.cartItemRcv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_img:
                if (cartItemList.size() != 0) {
                    finish();
                } else {
                    cartEmptyRedirect();
                }

                break;
            case R.id.next_btn:
                cartEmptyRedirect();
                break;
        }
    }

    // Api calling GetCartListDetailData
    public void GetCartListDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), CartItemShowActivity.this);
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<WatchListModelDemo> call = api.getCartList("http://www.mocky.io/v2/5e578dfd300000067bfd3b09");

        call.enqueue(new Callback<WatchListModelDemo>() {
            @Override
            public void onResponse(Call<WatchListModelDemo> call, retrofit2.Response<WatchListModelDemo> response) {

                if (response.body().getData() != null) {
                    cartItemList = response.body().getData();
                    setDataValueInList();
                }
            }

            @Override
            public void onFailure(Call<WatchListModelDemo> call, Throwable t) {
                Log.d("WatchList Error:", t.getLocalizedMessage());
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (cartItemList.size() != 0) {
            finish();
        } else {
            cartEmptyRedirect();
        }

        super.onBackPressed();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof CartItemShowAdapter.MyViewHolder) {
            // backup of removed item for undo purpose
            final WatchListDetailModel deletedItem = cartItemList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            cartItemShowAdapter.removeItem(viewHolder.getAdapterPosition());
            Log.d("cartListsize :", "" + cartItemList.size());
            if (cartItemList.size() == 0) {
                activityCartItemShowBinding.noCartdatarel.setVisibility(View.VISIBLE);
                activityCartItemShowBinding.bottomLinear.setVisibility(View.GONE);
                activityCartItemShowBinding.bottomGradiantView.setVisibility(View.GONE);
            } else {
                activityCartItemShowBinding.noCartdatarel.setVisibility(View.GONE);
                activityCartItemShowBinding.bottomLinear.setVisibility(View.VISIBLE);
                activityCartItemShowBinding.bottomGradiantView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void cartEmptyRedirect() {
        Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
        DashboardIntent.putExtra("whichPageRun", "3");
        startActivity(DashboardIntent);
//        finish();
    }
}
