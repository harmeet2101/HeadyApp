package com.heady.ecomerce.headyapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.heady.ecomerce.headyapp.db.DatabaseHandler;
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
    private View sortView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProductRecylerView = (RecyclerView)findViewById(R.id.recyclerView);
        sortView = findViewById(R.id.sortView);
        sortView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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
        private DatabaseHandler databaseHandler;
        public ProductListener(){
            dataModel = HeadyAppApplication.getInstance().getDataModel();
            databaseHandler = new DatabaseHandler(getApplicationContext());
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
                long res =saveData(categoriesList);
                saveProductData(categoriesList);
                if(res > 0 )Toast.makeText(getBaseContext(),"record added successfully: "+res,Toast.LENGTH_SHORT).show();
                Log.d("Db",databaseHandler.getAllCategories().size()+"");
                Log.d("Db",databaseHandler.getAllProductDetails().size()+"");
            }
        }

        private void updateDataModel(){
            dataModel.setCategoriesList(categoriesList);
            dataModel.setRankingsList(rankingsList);
        }

        private long saveData(List<Categories> categoriesList){
             return databaseHandler.insertCategories(categoriesList);
        }

        private void saveProductData(List<Categories> categoriesList){
            for (int i =0;i<categoriesList.size();i++){

                databaseHandler.insertProducts(categoriesList.get(i).getProducts());
            }
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
            }
        }

        Intent i = new Intent(getBaseContext(),RankWiseActivity.class);
        Bundle b = new Bundle();
        b.putString("name",rankingsList.get(2).getRanking());
        b.putSerializable("ranking", (Serializable) rankedProductList);
        i.putExtras(b);
        startActivity(i);
    }


    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sort_dialog,null);
        TextView mostViewTextview = (TextView)view.findViewById(R.id.mvpTextview);
        TextView mostOrderedTextview = (TextView)view.findViewById(R.id.mopTextview);
        TextView mostSharedTextview = (TextView)view.findViewById(R.id.mspTextview);

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        alertDialog.show();
        mostViewTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMostViewed();
                alertDialog.dismiss();
            }
        });
        mostOrderedTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMostOrdered();
                alertDialog.dismiss();
            }
        });

        mostSharedTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMostShared();
                alertDialog.dismiss();
            }
        });
    }
}
