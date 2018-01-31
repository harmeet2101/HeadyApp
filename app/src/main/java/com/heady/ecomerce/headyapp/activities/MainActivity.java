package com.heady.ecomerce.headyapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.heady.ecomerce.headyapp.model.DataModel;
import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.adapters.ProductRecylerAdapter;
import com.heady.ecomerce.headyapp.application.HeadyAppApplication;
import com.heady.ecomerce.headyapp.rest.request.IRequest;
import com.heady.ecomerce.headyapp.rest.response.Categories;
import com.heady.ecomerce.headyapp.rest.response.RankedProduct;
import com.heady.ecomerce.headyapp.rest.response.ProductDetail;
import com.heady.ecomerce.headyapp.rest.response.ProductList;
import com.heady.ecomerce.headyapp.rest.response.RankingDetails;
import com.heady.ecomerce.headyapp.rest.response.Rankings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements ProductRecylerAdapter.IProductSelect {

    private RecyclerView mProductRecylerView;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    private static final int GRID_SIZE = 2;
    private ProductRecylerAdapter adapter;
    private List<Categories> categoriesList;
    private List<Rankings> rankingsList;
    private Button sortButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProductRecylerView = (RecyclerView)findViewById(R.id.recyclerView);
        sortButton = (Button)findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMostViewed();
            }
        });
        initGridLayoutManager();
        setLayoutManager();
        fetchProducts();
    }

    public void initGridLayoutManager() {
        this.gridLayoutManager = new GridLayoutManager(getBaseContext(),GRID_SIZE,
                GridLayoutManager.VERTICAL,false);
    }

    public void setLayoutManager(){

        layoutManager = gridLayoutManager;
        mProductRecylerView.setLayoutManager(layoutManager);
        adapter = new ProductRecylerAdapter(MainActivity.this,categoriesList);
        mProductRecylerView.setAdapter(adapter);

    }

    private void fetchProducts(){

        IRequest iRequest = HeadyAppApplication.getInstance().getRetrofitGsonBuilder().
                getRetrofit().create(IRequest.class);
        Call<ProductList> stringCall = iRequest.getProductList();
        ProductListener listener = new ProductListener();
        stringCall.enqueue(listener);

    }

    @Override
    public void onProductSelected(int position) {

        Intent intent = new Intent(getBaseContext(),ProductDetailsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("categories",categoriesList.get(position));
        intent.putExtras(b);
        startActivity(intent);
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


    private List<RankingDetails> getRankingDetails(int position){
        return rankingsList.get(position).getRankingDetails();
    }

    private void showMostViewed(){

        List<RankingDetails> rankingDetails = getRankingDetails(0);

        Map<Integer,ProductDetail> map = new HashMap<>();

        List<RankedProduct> rankedProductList = new ArrayList<>();
        for(int i =0;i<categoriesList.size();i++){

            for(int j=0;j<categoriesList.get(i).getProducts().size();j++){
                map.put(categoriesList.get(i).getProducts().get(j).getId(),
                        categoriesList.get(i).getProducts().get(j));
            }
        }

        for(int i =0;i<rankingDetails.size();i++){
            if(map.containsKey(rankingDetails.get(i).getId())) {
                rankedProductList.add(new RankedProduct(rankingDetails.get(i).getId(),
                        map.get(rankingDetails.get(i).getId()).getName(),
                        rankingDetails.get(i).getView_count()));
              //  Log.d(MainActivity.class.getSimpleName(), mostViewedList.get(i).toString());
            }
        }

        Intent i = new Intent(getBaseContext(),RankWiseActivity.class);
        Bundle b = new Bundle();
        b.putString("name",rankingsList.get(0).getRanking());
        b.putSerializable("ranking", (Serializable) rankedProductList);
        i.putExtras(b);
        startActivity(i);
    }

    private void showMostOrdered(){

        List<RankingDetails> rankingDetails = getRankingDetails(1);

        Map<Integer,ProductDetail> map = new HashMap<>();

        List<RankedProduct> rankedProductList = new ArrayList<>();
        for(int i =0;i<categoriesList.size();i++){

            for(int j=0;j<categoriesList.get(i).getProducts().size();j++){
                map.put(categoriesList.get(i).getProducts().get(j).getId(),
                        categoriesList.get(i).getProducts().get(j));
            }
        }

        for(int i =0;i<rankingDetails.size();i++){
            if(map.containsKey(rankingDetails.get(i).getId())) {
                rankedProductList.add(new RankedProduct(rankingDetails.get(i).getId(),
                        map.get(rankingDetails.get(i).getId()).getName(),
                        rankingDetails.get(i).getOrder_count()));
                //  Log.d(MainActivity.class.getSimpleName(), mostViewedList.get(i).toString());
            }
        }

        Intent i = new Intent(getBaseContext(),RankWiseActivity.class);
        Bundle b = new Bundle();
        b.putString("name",rankingsList.get(1).getRanking());
        b.putSerializable("ranking", (Serializable) rankedProductList);
        i.putExtras(b);
        startActivity(i);
    }

    private void showMostShared(){

        List<RankingDetails> rankingDetails = getRankingDetails(2);

        Map<Integer,ProductDetail> map = new HashMap<>();

        List<RankedProduct> rankedProductList = new ArrayList<>();
        for(int i =0;i<categoriesList.size();i++){

            for(int j=0;j<categoriesList.get(i).getProducts().size();j++){
                map.put(categoriesList.get(i).getProducts().get(j).getId(),
                        categoriesList.get(i).getProducts().get(j));
            }
        }

        for(int i =0;i<rankingDetails.size();i++){
            if(map.containsKey(rankingDetails.get(i).getId())) {
                rankedProductList.add(new RankedProduct(rankingDetails.get(i).getId(),
                        map.get(rankingDetails.get(i).getId()).getName(),
                        rankingDetails.get(i).getShares()));
                //  Log.d(MainActivity.class.getSimpleName(), mostViewedList.get(i).toString());
            }
        }

        Intent i = new Intent(getBaseContext(),RankWiseActivity.class);
        Bundle b = new Bundle();
        b.putString("name",rankingsList.get(2).getRanking());
        b.putSerializable("ranking", (Serializable) rankedProductList);
        i.putExtras(b);
        startActivity(i);
    }


}
