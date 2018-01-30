package com.heady.ecomerce.headyapp.rest.response;

import java.io.Serializable;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class RankingDetails implements Serializable{

    private int id;
    private int view_count;
    private int order_count;
    private int shares;

    public int getId() {
        return id;
    }


    public int getView_count() {
        return view_count;
    }

    public int getOrder_count() {
        return order_count;
    }

    public int getShares() {
        return shares;
    }

    @Override
    public String toString() {
        return "RankingDetails{" +
                "id=" + id +
                ", view_count=" + view_count +
                ", order_count=" + order_count +
                ", shares=" + shares +
                '}';
    }
}
