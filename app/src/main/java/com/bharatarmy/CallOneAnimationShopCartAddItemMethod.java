package com.bharatarmy;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bharatarmy.Models.BAShopProductSpecification;
import com.bharatarmy.Utility.Utils;

import java.util.List;

public class CallOneAnimationShopCartAddItemMethod {
    /*add Item to cart animation */
    CartDetailViewOneAnimation mCartsDetailView;
    private int[] mShoppingBagCoordinate = new int[2];
    int itemCounter = 0;
    String cartItemCount;


    public void defineCartControl(Context mContext, Toolbar toolbar, ImageView cartImage, TextView textView, int position, String adapterListName, String productName, String adapterlistname, List<BAShopProductSpecification> shopProductSize, List<BAShopProductSpecification> shopProductColor) {
        /*cart cantrol */
        mCartsDetailView = (CartDetailViewOneAnimation) View.inflate(mContext, R.layout.cart_main_shop_one_animation_view, null);
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
    public void startAnimationOnClick(RelativeLayout mainLinear) {
        mCartsDetailView.addWithAnimation(mainLinear);
    }
    public void startShopAnimationOnClick(LinearLayout mainLinear) {
        mCartsDetailView.addWithAnimation(mainLinear);
    }
    public void startAnimationOnClick(CoordinatorLayout htabMaincontent) {
        mCartsDetailView.addWithAnimation(htabMaincontent);
    }
}
