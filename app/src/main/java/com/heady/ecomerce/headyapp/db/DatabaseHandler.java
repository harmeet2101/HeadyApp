package com.heady.ecomerce.headyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.heady.ecomerce.headyapp.rest.response.Categories;
import com.heady.ecomerce.headyapp.rest.response.ProductDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harmeet.singh on 2/1/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final String TAG = DatabaseHandler.class.getSimpleName();

    private static final String DB_NAME = "eCommDB";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE_NAME_CATEGORIES = "categoriesTable";
    private static final String DB_TABLE_NAME_PRODUCTS = "productsTable";



    public DatabaseHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String qry1 = "create table "+DB_TABLE_NAME_CATEGORIES +" (id INTEGER PRIMARY KEY,name text);";
        String qry2= "create table "+DB_TABLE_NAME_PRODUCTS +" (id INTEGER PRIMARY KEY,name text,date_added text);";
        db.execSQL(qry1);
        db.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME_PRODUCTS);
        onCreate(db);
    }


    public long insertCategories(List<Categories> categoriesList){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = null;
        values= new ContentValues();
        long res=0l;
        for(Categories categories:categoriesList){

            values.put("id",categories.getId());
            values.put("name",categories.getName());
            res = db.insert(DB_TABLE_NAME_CATEGORIES,null,values);
        }
        return res;
    }

    public long insertProducts(List<ProductDetail> productDetailList){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = null;
        values= new ContentValues();
        long res=0l;
        for(ProductDetail productDetail:productDetailList){

            values.put("id",productDetail.getId());
            values.put("name",productDetail.getName());
            res = db.insert(DB_TABLE_NAME_PRODUCTS,null,values);
        }
        return res;
    }

    public List<Categories> getAllCategories() {
        List<Categories> categoriesList = new ArrayList<Categories>();
        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME_CATEGORIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Categories categories = new Categories();
                categories.setId(Integer.parseInt(cursor.getString(0)));
                categories.setName(cursor.getString(1));
                categoriesList.add(categories);
            } while (cursor.moveToNext());
        }
        return categoriesList;
    }

    public List<ProductDetail> getAllProductDetails() {
        List<ProductDetail> productDetailList = new ArrayList<ProductDetail>();
        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME_PRODUCTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setId(Integer.parseInt(cursor.getString(0)));
                productDetail.setName(cursor.getString(1));
                productDetailList.add(productDetail);
            } while (cursor.moveToNext());
        }
        return productDetailList;
    }
}
