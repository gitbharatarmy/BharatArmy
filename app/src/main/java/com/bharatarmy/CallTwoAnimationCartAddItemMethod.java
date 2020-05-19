package com.bharatarmy;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Utility.Utils;

import org.greenrobot.eventbus.EventBus;

public class CallTwoAnimationCartAddItemMethod {
    /*add Item to cart animation */
    CartDetailViewTwoAnimation mCartsDetailView;
    private int[] mShoppingBagCoordinate = new int[2];
    int itemCounter = 0;
    String cartItemCount;


    public void defineCartControl(Context mContext, Toolbar toolbar, ImageView cartImage, TextView textView, int position, String adapterlistname) {
        /*cart cantrol */
        if (adapterlistname.equalsIgnoreCase("bashopdetail")) {
            mCartsDetailView = (CartDetailViewTwoAnimation) View.inflate(mContext, R.layout.cart_shop_detail_two_animation_view, null);
        } else {
            mCartsDetailView = (CartDetailViewTwoAnimation) View.inflate(mContext, R.layout.cart_detail_two_animation_view, null);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mCartsDetailView.setLayoutParams(lp);
        toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if (cartImage != null) {
                    cartImage.getLocationOnScreen(mShoppingBagCoordinate);
                    mShoppingBagCoordinate[0] += cartImage.getWidth() / 2;
                    mShoppingBagCoordinate[1] += cartImage.getHeight() / 2;
                    mCartsDetailView.setShopBagCoordinate(mShoppingBagCoordinate);
                }
            }
        });

        /*close the cartView*/
        LinearLayout closelinear = (LinearLayout) mCartsDetailView.findViewById(R.id.close_dialog_linear);
        closelinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCartsDetailView.removeWithAnimation(false);
            }
        });

        // Hide after some seconds
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mCartsDetailView.getParent() != null) {
                    mCartsDetailView.removeWithAnimation(true);
                }
                cartItemCount = String.valueOf(addItemCount(mContext));
                if (Integer.valueOf(cartItemCount) <= 9) {
                    textView.setText("0" + cartItemCount);
                } else {
                    textView.setText(cartItemCount);
                }
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    public void startAnimationOnClick(RelativeLayout mainLinear) {
        mCartsDetailView.addWithAnimation(mainLinear);
    }

    public void startshopAnimationOnClick(LinearLayout mainLinear) {
        mCartsDetailView.addWithAnimation(mainLinear);
    }

    public int addItemCount(Context mContext) {
        if (Utils.getPref(mContext, "cartCounter") == null) {
            itemCounter = itemCounter + 1;
            Utils.setPref(mContext, "cartCounter", String.valueOf(itemCounter));
        } else if (Utils.getPref(mContext, "cartCounter").equalsIgnoreCase("")) {
            itemCounter = itemCounter + 1;
            Utils.setPref(mContext, "cartCounter", String.valueOf(itemCounter));
        } else {
            itemCounter = Integer.parseInt(Utils.getPref(mContext, "cartCounter"));
            itemCounter = itemCounter + 1;
            Utils.setPref(mContext, "cartCounter", String.valueOf(itemCounter));
        }


        return itemCounter;
    }

    public void startAnimationOnClick(CoordinatorLayout htabMaincontent) {
        mCartsDetailView.addWithAnimation(htabMaincontent);
    }
}
