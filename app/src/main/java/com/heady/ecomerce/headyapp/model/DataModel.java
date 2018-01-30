package com.heady.ecomerce.headyapp.model;

import android.content.Context;

import com.heady.ecomerce.headyapp.rest.response.Categories;
import com.heady.ecomerce.headyapp.rest.response.Rankings;

import java.util.List;

/**
 * Created by harmeet.singh on 1/30/2018.
 */

public class DataModel {


    private static DataModel instance = null;
    private Context context;
    private List<Categories> categoriesList;
    private List<Rankings> rankingsList;


    private DataModel(Context context){

        this.context = context;
    }


    public static DataModel getInstance(Context context){
        if(instance == null)
            instance = new DataModel(context);
        return instance;
    }

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<Rankings> getRankingsList() {
        return rankingsList;
    }

    public void setRankingsList(List<Rankings> rankingsList) {
        this.rankingsList = rankingsList;
    }
}
