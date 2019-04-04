package com.bharatarmy.Utility;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class ApiHandler {

    public static String BASE_URL = AppConfiguration.BASEURL;


    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(6000);
    private static WebServices apiService;


    public static WebServices getApiService() {
        if (apiService == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(70 * 1000, TimeUnit.MILLISECONDS);
            okHttpClient.setWriteTimeout(70 * 1000, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(70 * 1000, TimeUnit.MILLISECONDS);


            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setConverter(new GsonConverter(new Gson()))
                    .build();

            apiService = restAdapter.create(WebServices.class);
            return apiService;
        } else {

            return apiService;
        }
    }


}
