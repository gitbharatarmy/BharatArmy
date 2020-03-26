package com.bharatarmy.Utility;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit2.Response;

public class ApiHandler {

    public static String BASE_URL = AppConfiguration.BASEURL;


    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(6000);
    private static WebServices apiService;


    public static WebServices getApiService() {
        if (apiService == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(6, TimeUnit.MINUTES);
            okHttpClient.setWriteTimeout(6, TimeUnit.MINUTES);
            okHttpClient.setReadTimeout(6, TimeUnit.MINUTES);

            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("VersionId", Utils.getVersionCode());
                    request.addHeader("SessionKey","SessionKey");
                }
            };

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setConverter(new GsonConverter(new Gson()))
                    .setRequestInterceptor(requestInterceptor)
                    .build();

            apiService = restAdapter.create(WebServices.class);
            return apiService;
        } else {

            return apiService;
        }
    }


}
