package com.heady.ecomerce.headyapp.rest.response;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class Test <T>{

    private int id;
    private T t;

    public int getId() {
        return id;
    }

    public T getT() {
        return t;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", t=" + t +
                '}';
    }
}
