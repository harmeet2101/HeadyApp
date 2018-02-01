package com.heady.ecomerce.headyapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.rest.response.ProductDetail;
import com.heady.ecomerce.headyapp.rest.response.RankedProduct;

import java.util.List;

/**
 * Created by harmeet.singh on 1/31/2018.
 */

public class RankingRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<RankedProduct> rankedProductList;
    private static final int ITEM_VIEW_TYPE_LOADING = 0;
    private static final int ITEM_VIEW_TYPE_EMPTY = 1;
    private static final int ITEM_VIEW_TYPE_NORMAL = 2;


    public RankingRecyclerAdapter(Context context, List<RankedProduct> rankedProductList){
        this.context =context;
        this.rankedProductList = rankedProductList;

    }

    @Override
    public int getItemViewType(int position) {
        if(rankedProductList == null)
            return ITEM_VIEW_TYPE_LOADING;
        else if(rankedProductList.isEmpty())
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranked_product,parent,false);
                viewHolder = new DataHolder(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (rankedProductList == null)
            onBindLoadingHolder(holder);
        else if(rankedProductList.isEmpty())
            onBindEmptyHolder(holder);
        else
            onBindDataHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        if(rankedProductList == null)
            return 1;
        else if (rankedProductList.isEmpty())
            return 1;
        else return rankedProductList.size();
    }


    public void updateDataSource(List<RankedProduct> rankedProductList){
        this.rankedProductList = rankedProductList;
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


    protected class DataHolder extends RecyclerView.ViewHolder {


        protected TextView productTextview,countTextview;
        protected View containerView;
        private int pos;
        public DataHolder(View view){
            super(view);

            productTextview = (TextView)view.findViewById(R.id.productTextview);
            countTextview = (TextView)view.findViewById(R.id.countTextview);
            containerView = view.findViewById(R.id.container);

        }

        @SuppressLint("NewApi")
        public void setData(int position){
            productTextview.setText(rankedProductList.get(position).getName());
            countTextview.setText("count: "+rankedProductList.get(position).getCount());
            this.pos = position;
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
}
