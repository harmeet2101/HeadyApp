package com.heady.ecomerce.headyapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.adapters.ProductDetailsRecyclerAdapter;
import com.heady.ecomerce.headyapp.adapters.ProductRecylerAdapter;
import com.heady.ecomerce.headyapp.rest.response.Categories;

/**
 * Created by harmeet.singh on 1/30/2018.
 */

public class ProductDetailsActivity extends Activity implements ProductDetailsRecyclerAdapter.IProductSelect {

    private RecyclerView mRecylerView;
    private LinearLayoutManager linearLayoutManager;
    private ProductDetailsRecyclerAdapter adapter;
    private Categories categories;
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.product_details_list);
        mRecylerView = (RecyclerView)findViewById(R.id.recyclerView);

        Bundle b = getIntent().getExtras();
        categories = (Categories) b.getSerializable("categories");

        linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);;
        mRecylerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductDetailsRecyclerAdapter(ProductDetailsActivity.this,categories.getProducts());
        mRecylerView.setAdapter(adapter);
    }

    @Override
    public void onProductSelected(int position) {

       // Toast.makeText(getBaseContext(),""+position,Toast.LENGTH_SHORT).show();
    }
}