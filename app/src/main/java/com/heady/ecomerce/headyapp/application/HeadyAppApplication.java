package com.heady.ecomerce.headyapp.application;

import android.app.Application;

import com.heady.ecomerce.headyapp.model.DataModel;
import com.heady.ecomerce.headyapp.rest.api.RetrofitGsonBuilder;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class HeadyAppApplication extends Application {



    private RetrofitGsonBuilder retrofitGsonBuilder;
    private static HeadyAppApplication instance = null;
    private DataModel dataModel;

    public void onCreate(){
        super.onCreate();
        instance = this;
        initRestClient();
        dataModel = DataModel.getInstance(this);
    }

    public static HeadyAppApplication getInstance(){
        return instance;
    }

    private void initRestClient(){
        retrofitGsonBuilder = new RetrofitGsonBuilder(this);
    }

    public RetrofitGsonBuilder getRetrofitGsonBuilder() {
        return retrofitGsonBuilder;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

}
