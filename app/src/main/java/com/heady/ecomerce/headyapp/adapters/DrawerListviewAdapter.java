package com.heady.ecomerce.headyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heady.ecomerce.headyapp.R;

import java.util.List;

/**
 * Created by harmeet.singh on 9/26/2017.
 */

public class DrawerListviewAdapter extends BaseAdapter {


    private Context context;
    private List<String> drawerListItems;


    public DrawerListviewAdapter(Context context, List<String> drawerListItems){
        this.context = context;
        this.drawerListItems = drawerListItems;
    }
    @Override
    public int getCount() {
        return drawerListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DataHolder dataHolder;

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_list_items,null);
            dataHolder = new DataHolder(convertView);
            dataHolder.setData(drawerListItems.get(position));
            convertView.setTag(dataHolder);
        }else {
            dataHolder = (DataHolder)convertView.getTag();
            dataHolder.setData(drawerListItems.get(position));
        }
        return convertView;
    }

        class DataHolder{

        public TextView itemTextview;
        public ImageView navigationImageview;
        public DataHolder(View view){

            itemTextview = (TextView)view.findViewById(R.id.itemTextview);
            navigationImageview = (ImageView) view.findViewById(R.id.rightArrowImageview);
        }

        public void setData(String item){

            itemTextview.setText(item);

        }
    }
}
