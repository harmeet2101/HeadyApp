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
import com.heady.ecomerce.headyapp.rest.response.Variant;

import java.util.List;

/**
 * Created by harmeet.singh on 1/30/2018.
 */

public class VariantsDetailRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<Variant> variantList;
    private static final int ITEM_VIEW_TYPE_LOADING = 0;
    private static final int ITEM_VIEW_TYPE_EMPTY = 1;
    private static final int ITEM_VIEW_TYPE_NORMAL = 2;


    public VariantsDetailRecyclerAdapter(Context context, List<Variant> variantList){
        this.context =context;
        this.variantList = variantList;

    }

    @Override
    public int getItemViewType(int position) {
        if(variantList == null)
            return ITEM_VIEW_TYPE_LOADING;
        else if(variantList.isEmpty())
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variants_detail,parent,false);
                viewHolder = new DataHolder(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (variantList == null)
            onBindLoadingHolder(holder);
        else if(variantList.isEmpty())
            onBindEmptyHolder(holder);
        else
            onBindDataHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        if(variantList == null)
            return 1;
        else if (variantList.isEmpty())
            return 1;
        else return variantList.size();
    }


    public void updateDataSource(List<Variant> variantList){
        this.variantList = variantList;
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


        protected TextView colorTextview,sizeTextview,priceTextview;
        public DataHolder(View view){
            super(view);

            colorTextview = (TextView)view.findViewById(R.id.colorTextview);
            sizeTextview = (TextView)view.findViewById(R.id.SizeTextview);
            priceTextview = (TextView)view.findViewById(R.id.priceTextview);
        }

        @SuppressLint("NewApi")
        public void setData(int position){
            colorTextview.setText(variantList.get(position).getColor());
            sizeTextview.setText(variantList.get(position).getSize());
            priceTextview.setText(variantList.get(position).getPrice()+"");
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
            infoTextview.setText("No Variant Details Found.");
        }
    }

}
