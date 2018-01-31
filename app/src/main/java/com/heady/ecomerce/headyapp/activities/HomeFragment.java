package com.heady.ecomerce.headyapp.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.adapters.ProductRecylerAdapter;
import com.heady.ecomerce.headyapp.application.HeadyAppApplication;
import com.heady.ecomerce.headyapp.model.DataModel;
import com.heady.ecomerce.headyapp.rest.request.IRequest;
import com.heady.ecomerce.headyapp.rest.response.Categories;
import com.heady.ecomerce.headyapp.rest.response.ProductList;
import com.heady.ecomerce.headyapp.rest.response.Rankings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet.singh on 1/31/2018.
 */

public class HomeFragment extends Fragment {

    private RecyclerView mProductRecylerView;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    private static final int GRID_SIZE = 2;
    private ProductRecylerAdapter adapter;
    private List<Categories> categoriesList;
    private List<Rankings> rankingsList;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){

        View view = inflater.inflate(R.layout.activity_main,viewGroup,false);
        mProductRecylerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        initGridLayoutManager();
        setLayoutManager();
        fetchProducts();
        return view;
    }


    public void initGridLayoutManager() {
        this.gridLayoutManager = new GridLayoutManager(getActivity(),GRID_SIZE,
                GridLayoutManager.VERTICAL,false);
    }

    public void setLayoutManager(){

        layoutManager = gridLayoutManager;
        mProductRecylerView.setLayoutManager(layoutManager);
        adapter = new ProductRecylerAdapter(getActivity(),categoriesList);
        mProductRecylerView.setAdapter(adapter);

    }

    private void fetchProducts(){

        IRequest iRequest = HeadyAppApplication.getInstance().getRetrofitGsonBuilder().
                getRetrofit().create(IRequest.class);
        Call<ProductList> stringCall = iRequest.getProductList();
        ProductListener listener = new ProductListener();
        stringCall.enqueue(listener);

    }

    private class ProductListener implements Callback<ProductList> {

        private DataModel dataModel;

        public ProductListener(){
            dataModel = HeadyAppApplication.getInstance().getDataModel();
        }
        @Override
        public void onResponse(Call<ProductList> call, Response<ProductList> response) {

            if(response.code() == 200) {
                ProductList products = response.body();
                Log.d(MainActivity.class.getSimpleName(),products.getCategories().toString());
                categoriesList = products.getCategories();
                rankingsList = products.getRankings();
                adapter.updateDataSource(categoriesList);
                updateDataModel();
            }
        }

        private void updateDataModel(){
            dataModel.setCategoriesList(categoriesList);
            dataModel.setRankingsList(rankingsList);
        }

        @Override
        public void onFailure(Call<ProductList> call, Throwable t) {
            t.printStackTrace();
            categoriesList = new ArrayList<>();
            rankingsList = new ArrayList<>();
            adapter.updateDataSource(categoriesList);
            updateDataModel();
        }
    }
}
