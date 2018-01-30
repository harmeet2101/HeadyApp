package com.heady.ecomerce.headyapp.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class Rankings {

    @SerializedName("ranking")
    private String ranking;
    @SerializedName("products")
    private List<Test> products;


    public String getRanking() {
        return ranking;
    }

    public List<Test> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Rankings{" +
                "ranking='" + ranking + '\'' +
                ", products=" + products +
                '}';
    }
}
