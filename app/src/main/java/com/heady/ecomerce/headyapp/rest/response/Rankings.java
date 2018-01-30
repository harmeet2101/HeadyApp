package com.heady.ecomerce.headyapp.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harmeet.singh on 1/29/2018.
 */

public class Rankings implements Serializable{

    @SerializedName("ranking")
    private String ranking;
    @SerializedName("products")
    private List<RankingDetails> rankingDetails;


    public String getRanking() {
        return ranking;
    }

    public List<RankingDetails> getRankingDetails() {
        return rankingDetails;
    }

    @Override
    public String toString() {
        return "Rankings{" +
                "ranking='" + ranking + '\'' +
                ", rankingDetails=" + rankingDetails +
                '}';
    }
}
