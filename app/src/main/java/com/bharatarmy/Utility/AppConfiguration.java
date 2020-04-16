package com.bharatarmy.Utility;

import android.net.Uri;

import java.util.ArrayList;

public class AppConfiguration {
//    public static final String BASEURL = "http://beta.bharatarmy.com/API/v1/";

//    public static final String BASEURL = "https://www.bharatarmy.com/API/v1/";

//    public static final String BASEURL = "http://mob.bharatarmy.com/API/v1/";

    /*Url for static data*/
    public static final String URL = "http://www.mocky.io/v2/5e43d07331000024413b01eb";

    public static final String BASEURL = "http://devenv.bharatarmy.com/API/v1/";

    public static final String IMAGE_URL = "https://www.bharatarmy.com/Docs/Mobile/";
    public static final String FLAG_URL = "https://www.bharatarmy.com/Content/images/flags-mini/small/";
    public static final String TERMSURL = "https://www.bharatarmy.com/legal/privacypolicy?isapp=1";
    public static final String MAINDASHBOARDIMAGEURL = "https://www.bharatarmy.com/Docs/";

    public static final String SHARETEXT = "Shared from Bharat Army";
    public static String IpAddress;
    public static int position;
    public static String wheretocomemobile;
    public static String currentCountryISOCode;
    public static String firstDashStr;
    public static String tabPosition;
    public static boolean tabselected = true;
    public static String pageSize = "14";
    public static String inquiryId;
    public static int selectedposition;
    public static ArrayList<String> ordertypefilterarray = new ArrayList<>();
    public static ArrayList<String> inquirystatusfilterarray = new ArrayList<>();
    public static String whereToCall;
    public static int pageindex;

    public static String notificationtitle = "Uploading";

    public static String videoType;
    public static String screenType = "";
    public static String videoPlay = "";
    public static String videoThumbStr;

    /*Feedback survey quite variable*/
    public static String lastpositionofnavigation = "";
    public static String multichoice = "not fill";
    public static String singlechoice = "not fill";
    public static String imagechoice = "not fill";
    public static String addtextchoice = "not fill";

    public static String question3 = "";
    public static String question4 = "";
    public static String question5 = "";
    public static String question6 = "";
    public static String question8 = "";
    public static String question12 = "";
    public static String question13 = "";


    /*Image watchlist list*/
    public static ArrayList<String> watchlistId = new ArrayList<>();
    public static ArrayList<String> imageLikeId = new ArrayList<>();
}
