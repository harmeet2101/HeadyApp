package com.heady.ecomerce.headyapp.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class ProductDetail {


    @SerializedName("id")
    private int id ;
    @SerializedName("name")
    private String name;
    @SerializedName("date_added")
    private String date_added;
    @SerializedName("variants")
    private List<Variant> variants;
    @SerializedName("tax")
    private Tax tax;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate_added() {
        return date_added;
    }

    public List<Variant> getVariants() {
        return variants;
    }


    public Tax getTax() {
        return tax;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date_added='" + date_added + '\'' +
                ", variants=" + variants +
                ", tax=" + tax +
                '}';
    }
}
