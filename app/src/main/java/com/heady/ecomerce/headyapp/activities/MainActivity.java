package com.heady.ecomerce.headyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.application.HeadyAppApplication;
import com.heady.ecomerce.headyapp.rest.request.IRequest;
import com.heady.ecomerce.headyapp.rest.response.ProductList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button clickMeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickMeButton = (Button)findViewById(R.id.startButton);

        clickMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchProducts();
            }
        });
    }


    private void fetchProducts(){

        IRequest iRequest = HeadyAppApplication.getInstance().getRetrofitGsonBuilder().
                getRetrofit().create(IRequest.class);
        Call<ProductList> stringCall = iRequest.getProductList();
        ProductListener listener = new ProductListener();
        stringCall.enqueue(listener);

    }


    private class ProductListener implements Callback<ProductList> {

        @Override
        public void onResponse(Call<ProductList> call, Response<ProductList> response) {

            if(response.code() == 200) {
                ProductList products = response.body();
                Log.d(MainActivity.class.getSimpleName(),products.getCategories().toString());
            }
        }

        @Override
        public void onFailure(Call<ProductList> call, Throwable t) {
            t.printStackTrace();
        }
    }
}
