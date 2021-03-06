package com.heady.ecomerce.headyapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.rest.response.Categories;
import com.heady.ecomerce.headyapp.rest.response.ProductDetail;
import com.heady.ecomerce.headyapp.rest.response.Variant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by harmeet.singh on 1/30/2018.
 */

public class ProductDetailsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<ProductDetail> productDetails;
    private static final int ITEM_VIEW_TYPE_LOADING = 0;
    private static final int ITEM_VIEW_TYPE_EMPTY = 1;
    private static final int ITEM_VIEW_TYPE_NORMAL = 2;
    private IProductSelect iProductSelect;


    public ProductDetailsRecyclerAdapter(Context context, List<ProductDetail> productDetails){
        this.context =context;
        this.productDetails = productDetails;
        try{
            iProductSelect = (IProductSelect)context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(productDetails == null)
            return ITEM_VIEW_TYPE_LOADING;
        else if(productDetails.isEmpty())
            return ITEM_VIEW_TYPE_EMPTY;
        else
            return ITEM_VIEW_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        RecyclerView.ViewHolder viewHolder = null;
        View view;
        switch (viewType){

            case ITEM_VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_loading,parent,false);
                viewHolder = new LoadingHolder(view);
                break;
            case ITEM_VIEW_TYPE_EMPTY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_text_info,parent,false);
                viewHolder = new EmptyHolder(view);
                break;
            case ITEM_VIEW_TYPE_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_category_view,parent,false);
                viewHolder = new DataHolder(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (productDetails == null)
            onBindLoadingHolder(holder);
        else if(productDetails.isEmpty())
            onBindEmptyHolder(holder);
        else
            onBindDataHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        if(productDetails == null)
            return 1;
        else if (productDetails.isEmpty())
            return 1;
        else return productDetails.size();
    }


    public void updateDataSource(List<ProductDetail> categoriesList){
        this.productDetails = categoriesList;
        notifyDataSetChanged();
    }


    private void onBindEmptyHolder(RecyclerView.ViewHolder holder){

        EmptyHolder emptyHolder = (EmptyHolder)holder;
        emptyHolder.setLabel();
    }

    private void onBindDataHolder(RecyclerView.ViewHolder holder,int position){

        DataHolder dataHolder = (DataHolder)holder;
        dataHolder.setData(position);
    }

    private void onBindLoadingHolder(RecyclerView.ViewHolder holder){
        LoadingHolder loadingHolder = (LoadingHolder)holder;

    }


    protected class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        protected TextView productTextview;
        protected View containerView;
        private int pos;
        public DataHolder(View view){
            super(view);

            productTextview = (TextView)view.findViewById(R.id.productTextview);
            containerView = view.findViewById(R.id.container);
            initListeners();
        }

        @SuppressLint("NewApi")
        public void setData(int position){
            productTextview.setText(productDetails.get(position).getName());
            this.pos = position;
        }


        private void initListeners(){
            containerView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.container:
                    iProductSelect.onProductSelected(productDetails.get(pos).getName(),
                            productDetails.get(pos).getVariants());
                    break;
            }
        }
    }


    protected class LoadingHolder extends RecyclerView.ViewHolder  {


        private ProgressBar progressBar;
        public LoadingHolder(View view){
            super(view);
            progressBar = (ProgressBar)view.findViewById(R.id.progreeBar);

        }

    }


    protected class EmptyHolder extends RecyclerView.ViewHolder{


        protected TextView infoTextview;

        public EmptyHolder(View view){
            super(view);
            infoTextview = (TextView)view.findViewById(R.id.infoTextview);
        }
        public void setLabel(){
            infoTextview.setText("No Product Details Found.");
        }
    }

    public interface IProductSelect{

        void onProductSelected(String productName,List<Variant> variantList);
    }
}
