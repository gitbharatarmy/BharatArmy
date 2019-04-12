package com.bharatarmy.Adapter;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bharatarmy.Models.HistoryModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.OrderStatus;
import com.bharatarmy.Utility.VectorDrawableUtils;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

public class HistoryMoreDetailListAdapter extends RecyclerView.Adapter {

    private ArrayList<HistoryModel> dataSet;
    Context mContext;
    int total_types;
    HotelFacilitiesAdapter hotelFacilitiesAdapter;
    MatchHistoryAdapter matchHistoryAdapter;

    public HistoryMoreDetailListAdapter(ArrayList<HistoryModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    public static class MainBannerImageViewHolder extends RecyclerView.ViewHolder {

        public MainBannerImageViewHolder(View itemView,int viewType) {
            super(itemView);

        }
    }

    public static class TicketBookingTypeViewHolder extends RecyclerView.ViewHolder {
        private final TimelineView timeline;
        private  final ImageView imageView;

        public ImageView getImageView() {
            return imageView;
        }

        public final TimelineView getTimeline() {
            return this.timeline;
        }
        public TicketBookingTypeViewHolder(View itemView,int viewType) {
            super(itemView);
            this.timeline = (TimelineView) itemView.findViewById(R.id.timeline);
            this.timeline.initLine(viewType);

            this.imageView=(ImageView)itemView.findViewById(R.id.marker_img);


        }
    }

    public static class AirportTransferTypeViewHolder extends RecyclerView.ViewHolder {
        private final TimelineView timeline;
        private  final ImageView imageView;
        public final TimelineView getTimeline() {
            return this.timeline;
        }
        public AirportTransferTypeViewHolder(View itemView,int viewType) {
            super(itemView);

            this.timeline = (TimelineView) itemView.findViewById(R.id.timeline);
            this.timeline.initLine(viewType);
            this.imageView=(ImageView)itemView.findViewById(R.id.marker_img);
        }
    }

    public static class HotelBookingTypeViewHolder extends RecyclerView.ViewHolder {
        private final TimelineView timeline;
        private final ImageView imageView;

        public final TimelineView getTimeline() {
            return this.timeline;
        }
//        ShimmerRecyclerView recyclerView;

        public HotelBookingTypeViewHolder(View itemView,int viewType) {
            super(itemView);
//            recyclerView = (ShimmerRecyclerView) itemView.findViewById(R.id.hotel_facilities_list);
            this.timeline = (TimelineView) itemView.findViewById(R.id.timeline);
            this.timeline.initLine(viewType);
            this.imageView=(ImageView)itemView.findViewById(R.id.marker_img);
        }
    }

    public static class TransferHotelToStadiumViewHolder extends RecyclerView.ViewHolder {
        private final TimelineView timeline;
        private  final ImageView imageView;
        public final TimelineView getTimeline() {
            return this.timeline;
        }
        public TransferHotelToStadiumViewHolder(View itemView,int viewType) {
            super(itemView);
            this.timeline = (TimelineView) itemView.findViewById(R.id.timeline);
            this.timeline.initLine(viewType);
            this.imageView=(ImageView)itemView.findViewById(R.id.marker_img);
        }
    }

    public static class TransferStadiumToHotelTypeViewHolder extends RecyclerView.ViewHolder {
        private final TimelineView timeline;
        private  final ImageView imageView;
        public final TimelineView getTimeline() {
            return this.timeline;
        }
        public TransferStadiumToHotelTypeViewHolder(View itemView,int viewType) {
            super(itemView);

            this.timeline = (TimelineView) itemView.findViewById(R.id.timeline);
            this.timeline.initLine(viewType);
            this.imageView=(ImageView)itemView.findViewById(R.id.marker_img);
        }
    }

    public static class HotelCheckoutTypeViewHolder extends RecyclerView.ViewHolder {
        private final TimelineView timeline;
        private  final ImageView imageView;
        public final TimelineView getTimeline() {
            return this.timeline;
        }

        public HotelCheckoutTypeViewHolder(View itemView,int viewType) {
            super(itemView);
            this.timeline = (TimelineView) itemView.findViewById(R.id.timeline);
            this.timeline.initLine(viewType);
            this.imageView=(ImageView)itemView.findViewById(R.id.marker_img);

        }
    }

    public static class MatchBookingTypeViewHolder extends RecyclerView.ViewHolder {
        ShimmerRecyclerView recyclerViewMatch;
        private final TimelineView timeline;
        private  final ImageView imageView;
        public final TimelineView getTimeline() {
            return this.timeline;
        }
        public MatchBookingTypeViewHolder(View itemView,int viewType) {
            super(itemView);

            recyclerViewMatch = (ShimmerRecyclerView) itemView.findViewById(R.id.match_history_detail_rcyList);
            this.timeline = (TimelineView) itemView.findViewById(R.id.timeline);
            this.timeline.initLine(viewType);
            this.imageView=(ImageView)itemView.findViewById(R.id.marker_img);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case HistoryModel.MAIN_BANNER_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_more_detail_list, parent, false);
                return new MainBannerImageViewHolder(view,viewType);
            case HistoryModel.TICKET_BOOKING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_booking_list, parent, false);
                return new TicketBookingTypeViewHolder(view,viewType);
            case HistoryModel.AIRPORT_TRANSFER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.airport_trasfer_list, parent, false);
                return new AirportTransferTypeViewHolder(view,viewType);
            case HistoryModel.HOTEL_BOOKING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_booking_list, parent, false);
                return new HotelBookingTypeViewHolder(view,viewType);
            case HistoryModel.TRANSFER_HOTEL_TO_STADIUM_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_hotel_to_stadium_list, parent, false);
                return new TransferHotelToStadiumViewHolder(view,viewType);
            case HistoryModel.TRANSFER_STADIUM_TO_HOTEL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trasfer_stadium_to_hotel, parent, false);
                return new TransferStadiumToHotelTypeViewHolder(view,viewType);
            case HistoryModel.HOTEL_CHECKOUT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_checkout_list, parent, false);
                return new HotelCheckoutTypeViewHolder(view,viewType);
            case HistoryModel.MATCH_BOOKING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match__show_list, parent, false);
                return new MatchBookingTypeViewHolder(view,viewType);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return HistoryModel.MAIN_BANNER_IMAGE_TYPE;
            case 1:
                return HistoryModel.TICKET_BOOKING_TYPE;
            case 2:
                return HistoryModel.AIRPORT_TRANSFER_TYPE;
            case 3:
                return HistoryModel.HOTEL_BOOKING_TYPE;
            case 4:
                return HistoryModel.TRANSFER_HOTEL_TO_STADIUM_TYPE;
            case 5:
                return HistoryModel.TRANSFER_STADIUM_TO_HOTEL;
            case 6:
                return HistoryModel.HOTEL_CHECKOUT_TYPE;
            case 7:
                return HistoryModel.MATCH_BOOKING_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        HistoryModel object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case HistoryModel.MAIN_BANNER_IMAGE_TYPE:
                    break;
                case HistoryModel.TICKET_BOOKING_TYPE:
                    if (object.getStatus() == OrderStatus.INACTIVE) {
                        ((TicketBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_inactive);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
                        ((TicketBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_active);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
                    } else {
                        ((TicketBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
                    }
                    break;
                case HistoryModel.AIRPORT_TRANSFER_TYPE:

                    if (object.getStatus() == OrderStatus.INACTIVE) {
                        ((AirportTransferTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_inactive);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
                        ((AirportTransferTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_active);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
                    } else {
                        ((AirportTransferTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
                    }

//                    if (object.getStatus() == OrderStatus.INACTIVE) {
//                        this.setMarkerAirport((AirportTransferTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
//                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
//                        this.setMarkerAirport((AirportTransferTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
//                    } else {
//                        this.setMarkerAirport((AirportTransferTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
//                    }
                    break;
                case HistoryModel.HOTEL_BOOKING_TYPE:
                    ArrayList<String> list = new ArrayList();
                    for (int i = 0; i <7; i++) {
                        list.add(String.valueOf(i));
                    }
//                    hotelFacilitiesAdapter = new HotelFacilitiesAdapter(mContext, list);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false);
//                    ((HotelBookingTypeViewHolder) holder).recyclerView.setLayoutManager(linearLayoutManager);
//                    ((HotelBookingTypeViewHolder) holder).recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    ((HotelBookingTypeViewHolder) holder).recyclerView.setAdapter(hotelFacilitiesAdapter);
//                    if (object.getStatus() == OrderStatus.INACTIVE) {
//                        this.setMarkerHotelBooking((HotelBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
//                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
//                        this.setMarkerHotelBooking((HotelBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
//                    } else {
//                        this.setMarkerHotelBooking((HotelBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
//                    }
                    if (object.getStatus() == OrderStatus.INACTIVE) {
                        ((HotelBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_inactive);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
                        ((HotelBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_active);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
                    } else {
                        ((HotelBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
                    }

                    break;
                case HistoryModel.TRANSFER_HOTEL_TO_STADIUM_TYPE:
                    if (object.getStatus() == OrderStatus.INACTIVE) {
                        ((TransferHotelToStadiumViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_inactive);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
                        ((TransferHotelToStadiumViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_active);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
                    } else {
                        ((TransferHotelToStadiumViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
                    }

//                    if (object.getStatus() == OrderStatus.INACTIVE) {
//                        this.setMarkerHotelToStadium((TransferHotelToStadiumViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
//                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
//                        this.setMarkerHotelToStadium((TransferHotelToStadiumViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
//                    } else {
//                        this.setMarkerHotelToStadium((TransferHotelToStadiumViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
//                    }
                    break;
                case HistoryModel.TRANSFER_STADIUM_TO_HOTEL:
                    if (object.getStatus() == OrderStatus.INACTIVE) {
                        ((TransferStadiumToHotelTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_inactive);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
                        ((TransferStadiumToHotelTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_active);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
                    } else {
                        ((TransferStadiumToHotelTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
                    }

//                    if (object.getStatus() == OrderStatus.INACTIVE) {
//                        this.setMarkerStadiumToHotel((TransferStadiumToHotelTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
//                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
//                        this.setMarkerStadiumToHotel((TransferStadiumToHotelTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
//                    } else {
//                        this.setMarkerStadiumToHotel((TransferStadiumToHotelTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
//                    }
                    break;
                case HistoryModel.HOTEL_CHECKOUT_TYPE:
                    if (object.getStatus() == OrderStatus.INACTIVE) {
                        ((HotelCheckoutTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_inactive);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
                        ((HotelCheckoutTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_active);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
                    } else {
                        ((HotelCheckoutTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
                    }
//                    if (object.getStatus() == OrderStatus.INACTIVE) {
//                        this.setMarkerHotelCheckout((HotelCheckoutTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
//                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
//                        this.setMarkerHotelCheckout((HotelCheckoutTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
//                    } else {
//                        this.setMarkerHotelCheckout((HotelCheckoutTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
//                    }
                    break;
                case HistoryModel.MATCH_BOOKING_TYPE:
                    if (object.getStatus() == OrderStatus.INACTIVE) {
                        ((MatchBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_inactive);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
                        ((MatchBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker_active);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
                    } else {
                        ((MatchBookingTypeViewHolder) holder).imageView.setImageResource(R.drawable.ic_marker);
//                        this.setMarkerTicket((TicketBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
                    }
//                    if (object.getStatus() == OrderStatus.INACTIVE) {
//                        this.setMarkerMatchBooking((MatchBookingTypeViewHolder) holder, R.drawable.ic_marker_inactive, R.color.heading_bg);
//                    } else if (object.getStatus() == OrderStatus.ACTIVE) {
//                        this.setMarkerMatchBooking((MatchBookingTypeViewHolder)holder, R.drawable.ic_marker_active, R.color.heading_bg);
//                    } else {
//                        this.setMarkerMatchBooking((MatchBookingTypeViewHolder)holder, R.drawable.ic_marker, R.color.heading_bg);
//                    }
                    ArrayList<String> list1 = new ArrayList();
                    for (int i = 0; i <1; i++) {
                        list1.add(String.valueOf(i));
                    }
                    matchHistoryAdapter = new MatchHistoryAdapter(mContext, list1);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false);
                    ((MatchBookingTypeViewHolder) holder).recyclerViewMatch.setLayoutManager(linearLayoutManager1);
                    ((MatchBookingTypeViewHolder) holder).recyclerViewMatch.setItemAnimator(new DefaultItemAnimator());
                    ((MatchBookingTypeViewHolder) holder).recyclerViewMatch.setAdapter(matchHistoryAdapter);
                    break;

            }
        }
    }



    private final void setMarkerTicket(HistoryMoreDetailListAdapter.TicketBookingTypeViewHolder holder, int drawableResId, int colorFilter) {
     TimelineView var10000=holder.getTimeline();
//        ImageView var10000=holder.getImageView();

        VectorDrawableUtils var10001 = VectorDrawableUtils.INSTANCE;
        View var10002 = holder.itemView;

        Context var4 = var10002.getContext();

        View var10004 = holder.itemView;

        var10000.setMarker(var10001.getDrawable(var4, drawableResId, ContextCompat.getColor(var10004.getContext(), colorFilter)));
    }
    private final void setMarkerAirport(HistoryMoreDetailListAdapter.AirportTransferTypeViewHolder holder, int drawableResId, int colorFilter) {
        TimelineView var10000=holder.getTimeline();
        VectorDrawableUtils var10001 = VectorDrawableUtils.INSTANCE;
        View var10002 = holder.itemView;

        Context var4 = var10002.getContext();

        View var10004 = holder.itemView;

        var10000.setMarker(var10001.getDrawable(var4, drawableResId, ContextCompat.getColor(var10004.getContext(), colorFilter)));
    }
    private final void setMarkerHotelBooking(HistoryMoreDetailListAdapter.HotelBookingTypeViewHolder holder, int drawableResId, int colorFilter) {
        TimelineView var10000=holder.getTimeline();
        VectorDrawableUtils var10001 = VectorDrawableUtils.INSTANCE;
        View var10002 = holder.itemView;

        Context var4 = var10002.getContext();

        View var10004 = holder.itemView;

        var10000.setMarker(var10001.getDrawable(var4, drawableResId, ContextCompat.getColor(var10004.getContext(), colorFilter)));
    }
    private final void setMarkerHotelToStadium(HistoryMoreDetailListAdapter.TransferHotelToStadiumViewHolder holder, int drawableResId, int colorFilter) {
        TimelineView var10000=holder.getTimeline();
        VectorDrawableUtils var10001 = VectorDrawableUtils.INSTANCE;
        View var10002 = holder.itemView;

        Context var4 = var10002.getContext();

        View var10004 = holder.itemView;

        var10000.setMarker(var10001.getDrawable(var4, drawableResId, ContextCompat.getColor(var10004.getContext(), colorFilter)));
    }
    private final void setMarkerStadiumToHotel(HistoryMoreDetailListAdapter.TransferStadiumToHotelTypeViewHolder holder, int drawableResId, int colorFilter) {
        TimelineView var10000=holder.getTimeline();
        VectorDrawableUtils var10001 = VectorDrawableUtils.INSTANCE;
        View var10002 = holder.itemView;

        Context var4 = var10002.getContext();

        View var10004 = holder.itemView;

        var10000.setMarker(var10001.getDrawable(var4, drawableResId, ContextCompat.getColor(var10004.getContext(), colorFilter)));
    }
    private final void setMarkerHotelCheckout(HistoryMoreDetailListAdapter.HotelCheckoutTypeViewHolder holder, int drawableResId, int colorFilter) {
        TimelineView var10000=holder.getTimeline();
        VectorDrawableUtils var10001 = VectorDrawableUtils.INSTANCE;
        View var10002 = holder.itemView;

        Context var4 = var10002.getContext();

        View var10004 = holder.itemView;

        var10000.setMarker(var10001.getDrawable(var4, drawableResId, ContextCompat.getColor(var10004.getContext(), colorFilter)));
    }
    private final void setMarkerMatchBooking(HistoryMoreDetailListAdapter.MatchBookingTypeViewHolder holder, int drawableResId, int colorFilter) {
        TimelineView var10000=holder.getTimeline();
        VectorDrawableUtils var10001 = VectorDrawableUtils.INSTANCE;
        View var10002 = holder.itemView;

        Context var4 = var10002.getContext();

        View var10004 = holder.itemView;

        var10000.setMarker(var10001.getDrawable(var4, drawableResId, ContextCompat.getColor(var10004.getContext(), colorFilter)));
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
