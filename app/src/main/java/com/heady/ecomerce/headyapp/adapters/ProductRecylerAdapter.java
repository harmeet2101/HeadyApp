package com.heady.ecomerce.headyapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.rest.response.Categories;

import java.util.List;

/**
 * Created by harmeet.singh on 1/30/2018.
 */

public class ProductRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<Categories> categoriesList;
    private static final int ITEM_VIEW_TYPE_LOADING = 0;
    private static final int ITEM_VIEW_TYPE_EMPTY = 1;
    private static final int ITEM_VIEW_TYPE_NORMAL = 2;
    private IProductSelect iProductSelect;


    public ProductRecylerAdapter(Context context, List<Categories> categoriesList){
        this.context =context;
        this.categoriesList = categoriesList;
        try{
            iProductSelect = (IProductSelect)context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(categoriesList == null)
            return ITEM_VIEW_TYPE_LOADING;
        else if(categoriesList.isEmpty())
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_grid,parent,false);
                viewHolder = new DataHolder(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (categoriesList == null)
            onBindLoadingHolder(holder);
        else if(categoriesList.isEmpty())
            onBindEmptyHolder(holder);
        else
            onBindDataHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        if(categoriesList == null)
            return 1;
        else if (categoriesList.isEmpty())
            return 1;
        else return categoriesList.size();
    }


    public void updateDataSource(List<Categories> categoriesList){
        this.categoriesList = categoriesList;
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
        public DataHolder(View view){
            super(view);
            productTextview = (TextView)view.findViewById(R.id.productTextview);
            containerView = view.findViewById(R.id.container);
            initListeners();
        }

        public void setData(int position){
            productTextview.setText(categoriesList.get(position).getName());

        }


        private void initListeners(){
            containerView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.container:
                    iProductSelect.onProductSelected(getAdapterPosition());
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
            infoTextview.setText("No Products Found.");
        }
    }

    public interface IProductSelect{

        void onProductSelected(int position);
    }
}
