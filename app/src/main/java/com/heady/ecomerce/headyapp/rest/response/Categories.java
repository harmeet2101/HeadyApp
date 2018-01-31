package com.heady.ecomerce.headyapp.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class Categories  implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("products")
    private List<ProductDetail> products;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public List<ProductDetail> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
    }

    public boolean equals(Object obj){

        Categories categories = (Categories)obj;
        return getId()==categories.getId();
    }

}
