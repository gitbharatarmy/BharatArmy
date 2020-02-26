package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelCityHotelDetailsActivity;
import com.bharatarmy.Activity.TravelMatchHotelRoomTypeActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.Models.WatchListDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.RoundishImageView;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.CartItemShowListItemBinding;
import com.bharatarmy.databinding.TravelMatchHotelListItemBinding;
import com.whinc.widget.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class CartItemShowAdapter extends RecyclerView.Adapter<CartItemShowAdapter.MyViewHolder> {
    Context mContext;
    List<WatchListDetailModel> cartItemList;


    public CartItemShowAdapter(Context mContext, List<WatchListDetailModel> cartItemList) {
        this.mContext = mContext;
        this.cartItemList = cartItemList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        //        CartItemShowListItemBinding cartItemShowListItemBinding;
        /*public MyViewHolder(CartItemShowListItemBinding cartItemShowListItemBinding) {
            super(cartItemShowListItemBinding.getRoot());
            this.cartItemShowListItemBinding = cartItemShowListItemBinding;
        }*/
        public RelativeLayout viewBackground, viewForeground;
        public LinearLayout ticketMainCartLinear, hotelMainCartLinear, cartTicketDetailLinear, cartHotelDetailLinear;
        public RoundishImageView cartTicketImage, cartHotelImage, cartFirstCountryFlagImage, cartSecondCountryFlagImage;
        public TextView cartTicketMatchTypeTagTextView, cartTicketMatchDateTimeTextView,
                cartFirstCountryNameTextView, cartSecondCountryNameTextView,
                cartTicketMatchCategoryTextView, cartPriceTicketTextView,
                cartHotelNameTextView, cartHotelDescriptionTextView, cartHotelPriceTextView;
        public RatingBar hotelRatingBar;
        public View bottomLineView;


        public MyViewHolder(View view) {
            super(view);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            ticketMainCartLinear = view.findViewById(R.id.ticket_main_cart_linear);
            hotelMainCartLinear = view.findViewById(R.id.hotel_main_cart_linear);
            cartTicketDetailLinear = view.findViewById(R.id.cart_ticket_detail_linear);
            cartHotelDetailLinear = view.findViewById(R.id.cart_hotel_detail_linear);
            cartTicketImage = view.findViewById(R.id.cart_ticket_img);
            cartHotelImage = view.findViewById(R.id.cart_hotel_img);
            cartFirstCountryFlagImage = view.findViewById(R.id.cart_first_countryflag_image);
            cartSecondCountryFlagImage = view.findViewById(R.id.cart_second_countryflag_image);
            cartTicketMatchTypeTagTextView = view.findViewById(R.id.cart_ticket_match_type_tag_txt);
            cartTicketMatchDateTimeTextView = view.findViewById(R.id.cart_ticket_match_date_time_txt);
            cartFirstCountryNameTextView = view.findViewById(R.id.cart_first_country_Name_txt);
            cartSecondCountryNameTextView = view.findViewById(R.id.cart_second_country_Name_txt);
            cartTicketMatchCategoryTextView = view.findViewById(R.id.cart_ticket_match_category_txt);
            cartPriceTicketTextView = view.findViewById(R.id.cart_price_txt);
            cartHotelNameTextView = view.findViewById(R.id.cart_hotel_Name_txt);
            cartHotelDescriptionTextView = view.findViewById(R.id.cart_hotel_description_txt);
            cartHotelPriceTextView = view.findViewById(R.id.cart_hotel_price_txt);
            hotelRatingBar = view.findViewById(R.id.cart_ratingBar);
            bottomLineView = view.findViewById(R.id.bottom_line_view);
        }
    }


    @Override
    public CartItemShowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        CartItemShowListItemBinding cartItemShowListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
//                R.layout.cart_item_show_list_item, parent, false);
//        return new CartItemShowAdapter.MyViewHolder(cartItemShowListItemBinding);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_show_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(CartItemShowAdapter.MyViewHolder holder, int position) {
        WatchListDetailModel cartDetail = cartItemList.get(position);
        if (cartDetail.getCartItemType().equalsIgnoreCase("Ticket")) {
            holder.ticketMainCartLinear.setVisibility(View.VISIBLE);
            holder.hotelMainCartLinear.setVisibility(View.GONE);
            Utils.setImageInImageView(cartDetail.getTicketImageUrl(), holder.cartTicketImage, mContext);
            holder.cartTicketMatchTypeTagTextView.setText(cartDetail.getTicketMatchType());
            holder.cartTicketMatchDateTimeTextView.setText(cartDetail.getTicketMatchTimeDate());
            holder.cartTicketMatchCategoryTextView.setText(cartDetail.getTicketCategoryName());
            holder.cartFirstCountryNameTextView.setText(cartDetail.getTicketMatchFirstCountryName());
            holder.cartSecondCountryNameTextView.setText(cartDetail.getTicketMatchSecondCountryName());
            Utils.setImageInImageView(cartDetail.getTicketMatchFirstCountryFlag(), holder.cartFirstCountryFlagImage, mContext);
            Utils.setImageInImageView(cartDetail.getTicketMatchSecondCountryFlag(), holder.cartSecondCountryFlagImage, mContext);
            holder.cartPriceTicketTextView.setText(cartDetail.getTicketPrice());
        } else if (cartDetail.getCartItemType().equalsIgnoreCase("Hotel")) {
            holder.ticketMainCartLinear.setVisibility(View.GONE);
            holder.hotelMainCartLinear.setVisibility(View.VISIBLE);

            Utils.setImageInImageView(cartDetail.getHotelImageUrl(), holder.cartHotelImage, mContext);
            holder.cartHotelNameTextView.setText(cartDetail.getHotelName());
            holder.cartHotelDescriptionTextView.setText(cartDetail.getHotelDescription());
            holder.hotelRatingBar.setCount(Integer.parseInt(cartDetail.getHotelRating()));
            holder.cartHotelPriceTextView.setText(cartDetail.getHotelPrice());

        } else if (cartDetail.getCartItemType().equalsIgnoreCase("Hospitality")) {
            holder.ticketMainCartLinear.setVisibility(View.GONE);
            holder.hotelMainCartLinear.setVisibility(View.GONE);
            holder.viewBackground.setVisibility(View.GONE);
            holder.viewForeground.setVisibility(View.GONE);
            holder.bottomLineView.setVisibility(View.GONE);
        }

       /* if (cartDetail.getCartItemType().equalsIgnoreCase("Ticket")) {
            holder.cartItemShowListItemBinding.ticketMainCartLinear.setVisibility(View.VISIBLE);
            holder.cartItemShowListItemBinding.hotelMainCartLinear.setVisibility(View.GONE);
            holder.cartItemShowListItemBinding.bottomLineView.setVisibility(View.VISIBLE);
            Utils.setImageInImageView(cartDetail.getTicketImageUrl(), holder.cartItemShowListItemBinding.cartTicketImg, mContext);
            holder.cartItemShowListItemBinding.cartTicketMatchTypeTagTxt.setText(cartDetail.getTicketMatchType());
            holder.cartItemShowListItemBinding.cartTicketMatchDateTimeTxt.setText(cartDetail.getTicketMatchTimeDate());
            holder.cartItemShowListItemBinding.cartTicketMatchCategoryTxt.setText(cartDetail.getTicketCategoryName());
            holder.cartItemShowListItemBinding.cartFirstCountryNameTxt.setText(cartDetail.getTicketMatchFirstCountryName());
            holder.cartItemShowListItemBinding.cartSecondCountryNameTxt.setText(cartDetail.getTicketMatchSecondCountryName());
            Utils.setImageInImageView(cartDetail.getTicketMatchFirstCountryFlag(), holder.cartItemShowListItemBinding.cartFirstCountryflagImage, mContext);
            Utils.setImageInImageView(cartDetail.getTicketMatchSecondCountryFlag(), holder.cartItemShowListItemBinding.cartSecondCountryflagImage, mContext);
            holder.cartItemShowListItemBinding.cartPriceTxt.setText(cartDetail.getTicketPrice());
        } else if (cartDetail.getCartItemType().equalsIgnoreCase("Hotel")) {
            holder.cartItemShowListItemBinding.ticketMainCartLinear.setVisibility(View.GONE);
            holder.cartItemShowListItemBinding.hotelMainCartLinear.setVisibility(View.VISIBLE);
            holder.cartItemShowListItemBinding.bottomLineView.setVisibility(View.VISIBLE);

            Utils.setImageInImageView(cartDetail.getHotelImageUrl(), holder.cartItemShowListItemBinding.cartHotelImg, mContext);
            holder.cartItemShowListItemBinding.cartHotelNameTxt.setText(cartDetail.getHotelName());
            holder.cartItemShowListItemBinding.cartHotelDescriptionTxt.setText(cartDetail.getHotelDescription());
            holder.cartItemShowListItemBinding.ratingBar.setCount(Integer.parseInt(cartDetail.getHotelRating()));
            holder.cartItemShowListItemBinding.cartHotelPriceTxt.setText(cartDetail.getHotelPrice());


        } else if (cartDetail.getCartItemType().equalsIgnoreCase("Hospitality")) {
            holder.cartItemShowListItemBinding.ticketMainCartLinear.setVisibility(View.GONE);
            holder.cartItemShowListItemBinding.hotelMainCartLinear.setVisibility(View.GONE);
            holder.cartItemShowListItemBinding.bottomLineView.setVisibility(View.GONE);
        }*/
    }


    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public void removeItem(int position) {
        cartItemList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(WatchListDetailModel item, int position) {
        cartItemList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}







