package com.heady.ecomerce.headyapp.rest.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class Tax {


    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private float value;

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
