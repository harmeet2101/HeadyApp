package com.heady.ecomerce.headyapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.adapters.ProductDetailsRecyclerAdapter;
import com.heady.ecomerce.headyapp.adapters.ProductRecylerAdapter;
import com.heady.ecomerce.headyapp.adapters.RankingRecyclerAdapter;
import com.heady.ecomerce.headyapp.rest.response.ProductDetail;
import com.heady.ecomerce.headyapp.rest.response.RankedProduct;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by harmeet.singh on 1/31/2018.
 */

public class RankWiseActivity extends Activity {


    private RecyclerView mProductRecylerView;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    private static final int GRID_SIZE = 2;
    private RankingRecyclerAdapter adapter;
    private TextView titleTextview;
    private Button sortButton,sortButton2;
    private List<RankedProduct> rankedProductList;
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.ranking_based_item);
        mProductRecylerView = (RecyclerView)findViewById(R.id.recyclerView);
        titleTextview = (TextView)findViewById(R.id.productTextview);
        sortButton = (Button)findViewById(R.id.sortButton);
        sortButton2 = (Button)findViewById(R.id.sortButton2);
        Bundle b = getIntent().getExtras();
        rankedProductList = (List<RankedProduct>) b.getSerializable("ranking");
        String title = b.getString("name");
        titleTextview.setText(title);
        initGridLayoutManager();
        setLayoutManager();

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.sort(rankedProductList,new AscendingComparator());
                adapter.updateDataSource(rankedProductList);
            }
        });

        sortButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.sort(rankedProductList,new DescendingComparator());
                adapter.updateDataSource(rankedProductList);
            }
        });
    }


    public void initGridLayoutManager() {
        this.gridLayoutManager = new GridLayoutManager(getBaseContext(),GRID_SIZE,
                GridLayoutManager.VERTICAL,false);
    }

    public void setLayoutManager(){

        layoutManager = gridLayoutManager;
        mProductRecylerView.setLayoutManager(layoutManager);
        adapter = new RankingRecyclerAdapter(getBaseContext(),rankedProductList);
        mProductRecylerView.setAdapter(adapter);

    }


    private class AscendingComparator implements Comparator<RankedProduct>{

        public int compare(RankedProduct r1,RankedProduct r2){

            if (r1.getCount()<r2.getCount())
                return -1;
            else if(r1.getCount()>r2.getCount())
                return 1;
            else
                return 0;
        }
    }

    private class DescendingComparator implements Comparator<RankedProduct>{

        public int compare(RankedProduct r1,RankedProduct r2){

            if (r1.getCount()<r2.getCount())
                return 1;
            else if(r1.getCount()>r2.getCount())
                return -1;
            else
                return 0;
        }
    }
}
