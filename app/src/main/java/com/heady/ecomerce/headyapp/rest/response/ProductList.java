package com.heady.ecomerce.headyapp.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class ProductList {


    @SerializedName("categories")
    private List<Categories> categories;
    private List<Rankings> rankings;

    public List<Categories> getCategories() {
        return categories;
    }

    public List<Rankings> getRankings() {
        return rankings;
    }
}
