package com.bharatarmy.Models;

import com.bharatarmy.Utility.OrderStatus;

public class HistoryModel {
    public static final int MAIN_BANNER_IMAGE_TYPE=0;
    public static final int TICKET_BOOKING_TYPE=1;
    public static final int AIRPORT_TRANSFER_TYPE=2;
    public static final int HOTEL_BOOKING_TYPE=3;
    public static final int TRANSFER_HOTEL_TO_STADIUM_TYPE=4;
    public static final int TRANSFER_STADIUM_TO_HOTEL=5;
    public static final int HOTEL_CHECKOUT_TYPE=6;
    public static final int MATCH_BOOKING_TYPE=7;

    public int type;
    public int data;
    public String text;
    OrderStatus status;

    public HistoryModel(int type, String text, int data,OrderStatus status)
    {
        this.type=type;
        this.data=data;
        this.text=text;
        this.status=status;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
