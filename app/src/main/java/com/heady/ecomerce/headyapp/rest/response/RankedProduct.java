package com.heady.ecomerce.headyapp.rest.response;

import java.io.Serializable;

/**
 * Created by harmeet.singh on 1/31/2018.
 */

public class RankedProduct implements Serializable{

    private int id;
    private String name;
    private int count;


    public RankedProduct(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "RankedProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
