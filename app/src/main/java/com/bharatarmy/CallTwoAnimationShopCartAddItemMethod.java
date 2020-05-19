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
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.BAMainShopProductColorAdapter;
import com.bharatarmy.Adapter.BAMainShopProductSizeAdapter;
import com.bharatarmy.Adapter.BAShopProductColorAdapter;
import com.bharatarmy.Adapter.BAShopProductSizeAdapter;
import com.bharatarmy.Models.BAShopProductSpecification;
import com.bharatarmy.Utility.Utils;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

public class CallTwoAnimationShopCartAddItemMethod {
    /*add Item to cart animation */
    CartDetailViewTwoAnimation mCartsDetailView;
    private int[] mShoppingBagCoordinate = new int[2];
    int itemCounter = 0;
    String cartItemCount;
    TextView productSizetxt, productColortxt, productNametxt, productDesctxt;
    RecyclerView shopProductSizelist, shopProductColorlist;
    BAMainShopProductSizeAdapter baMainShopProductSizeAdapter;
    BAMainShopProductColorAdapter baMainShopProductColorAdapter;
    Context mContext;


    public void defineCartControl(Context mContext, Toolbar toolbar, ImageView cartImage, TextView textView, int position,
                                  String adapterListName, String productName, String productdesc, List<BAShopProductSpecification> shopProductSize, List<BAShopProductSpecification> shopProductColor) {
        /*cart cantrol */
        mCartsDetailView = (CartDetailViewTwoAnimation) View.inflate(mContext, R.layout.cart_main_shop_two_animation_view, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mCartsDetailView.setLayoutParams(lp);
        this.mContext = mContext;
        setColorSizeDataList(shopProductSize, shopProductColor);
        productNametxt = (TextView) mCartsDetailView.findViewById(R.id.shop_product_Name_txt);


        productNametxt.setText(productName);


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

        /*add to cart*/
        RelativeLayout addtocartlinear = (RelativeLayout) mCartsDetailView.findViewById(R.id.addtocart_linear);
        addtocartlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });


        // Hide after some seconds
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (mCartsDetailView.getParent() != null) {
//                    mCartsDetailView.removeWithAnimation(true);
//                }
//                cartItemCount = String.valueOf(addItemCount(mContext));
//                if (Integer.valueOf(cartItemCount) <= 9) {
//                    textView.setText("0" + cartItemCount);
//                } else {
//                    textView.setText(cartItemCount);
//                }
//            }
//        };
//        handler.postDelayed(runnable, 2000);
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

    public void setColorSizeDataList(List<BAShopProductSpecification> productsizeList, List<BAShopProductSpecification> productcolorList) {
        productSizetxt = (TextView) mCartsDetailView.findViewById(R.id.product_size_txt);
        productColortxt = (TextView) mCartsDetailView.findViewById(R.id.product_color_txt);
        shopProductColorlist = (RecyclerView) mCartsDetailView.findViewById(R.id.shop_product_colorlist);
        shopProductSizelist = (RecyclerView) mCartsDetailView.findViewById(R.id.shop_product_sizelist);

        //        fill product size
        if (productsizeList.size() != 0 && productsizeList != null) {
            productSizetxt.setVisibility(View.VISIBLE);
            shopProductSizelist.setVisibility(View.VISIBLE);
            baMainShopProductSizeAdapter = new BAMainShopProductSizeAdapter(mContext, productsizeList);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setAlignItems(AlignItems.BASELINE);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            shopProductSizelist.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
            shopProductSizelist.setAdapter(baMainShopProductSizeAdapter);
        } else {
            productSizetxt.setVisibility(View.GONE);
            shopProductSizelist.setVisibility(View.GONE);
        }
//        fill product color
        if (productcolorList.size() != 0 && productcolorList != null) {
            productColortxt.setVisibility(View.VISIBLE);
            shopProductColorlist.setVisibility(View.VISIBLE);
            baMainShopProductColorAdapter = new BAMainShopProductColorAdapter(mContext, productcolorList);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setAlignItems(AlignItems.BASELINE);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            shopProductColorlist.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
            shopProductColorlist.setAdapter(baMainShopProductColorAdapter);
        } else {
            productColortxt.setVisibility(View.GONE);
            shopProductColorlist.setVisibility(View.GONE);
        }


    }

    public void startAnimationOnClick(CoordinatorLayout htabMaincontent) {
        mCartsDetailView.addWithAnimation(htabMaincontent);
    }
}
