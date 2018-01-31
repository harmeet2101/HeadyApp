package com.heady.ecomerce.headyapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.adapters.VariantsDetailRecyclerAdapter;
import com.heady.ecomerce.headyapp.rest.response.Variant;

import java.util.List;

/**
 * Created by harmeet.singh on 1/30/2018.
 */

public class VariantActivity extends Activity {

    private TextView productTextview;
    private RecyclerView mRecylerView;
    private LinearLayoutManager linearLayoutManager;
    private VariantsDetailRecyclerAdapter adapter;
    private List<Variant> variantList;
    private String prodName = null;

    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.variants_recyclerview);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            variantList = (List<Variant>) b.getSerializable("variant");
            prodName = b.getString("name");
        }
        mRecylerView = (RecyclerView)findViewById(R.id.recyclerView);
        productTextview = (TextView)findViewById(R.id.productTextview);
        productTextview.setText(prodName+" variants");
        linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new VariantsDetailRecyclerAdapter(getBaseContext(),variantList);
        mRecylerView.setLayoutManager(linearLayoutManager);
        mRecylerView.setAdapter(adapter);
    }
}
