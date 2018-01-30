package com.heady.ecomerce.headyapp.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class Variant implements Serializable{


    @SerializedName("id")
    private int id;
    @SerializedName("color")
    private String color;
    @SerializedName("size")
    private String size;
    @SerializedName("price")
    private long price;

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }
}
