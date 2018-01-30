package com.heady.ecomerce.headyapp.rest.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heady.ecomerce.headyapp.rest.utils.UrlUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class RetrofitGsonBuilder  {

    private Gson gson;
    private Retrofit retrofit;
    private Context context;
    private static final int READ_TIMEOUT_MILLISECONDS       = 20*1000;     // 20 sec
    private static final int CONNECTION_TIMEOUT_MILLISECONDS = 20*1000;      // 20 sec

    public RetrofitGsonBuilder(Context context){
        this.context = context;
        setUpClient();                                 // first time initialization.
    }

    public void setUpClient() {

        gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(READ_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECTION_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
