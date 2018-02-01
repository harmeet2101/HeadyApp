package com.heady.ecomerce.headyapp.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class ProductDetail implements Serializable{


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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
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
