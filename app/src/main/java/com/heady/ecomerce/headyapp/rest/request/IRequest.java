package com.heady.ecomerce.headyapp.rest.request;

import com.heady.ecomerce.headyapp.rest.response.ProductList;
import com.heady.ecomerce.headyapp.rest.utils.UrlUtils;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public interface IRequest {


    @GET(UrlUtils.GET_PRODUCT_LIST_URL)
    Call<ProductList> getProductList();
}
