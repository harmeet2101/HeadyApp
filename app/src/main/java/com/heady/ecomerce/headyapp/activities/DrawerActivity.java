package com.heady.ecomerce.headyapp.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.heady.ecomerce.headyapp.R;
import com.heady.ecomerce.headyapp.adapters.DrawerListviewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harmeet.singh on 1/31/2018.
 */

public class DrawerActivity extends Activity {

    private DrawerLayout drawerLayout;
    private ListView drawerListview;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView drawerImageview;
    protected TextView toolbarTitleTextview;
    private DrawerListviewAdapter drawerListviewAdapter;
    protected Toolbar toolbar;
    private FrameLayout frameLayout;

    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.home_page);
        frameLayout = (FrameLayout)findViewById(R.id.frame);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        drawerListview = (ListView)findViewById(R.id.listview);
        drawerImageview = (ImageView)findViewById(R.id.drawerImageview);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbarTitleTextview = (TextView)findViewById(R.id.toolbar_title);
        setDrawer();
        setToolbarTitle("HeadyApp");
        Fragment fragment = new HomeFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
    }

    private void setDrawer(){

        List<String> drawerListItems = new ArrayList<>();
        drawerListItems.add("Most Viewed Products");
        drawerListItems.add("Most OrdeRed Products");
        drawerListItems.add("Most ShaRed Products");
        drawerListviewAdapter = new DrawerListviewAdapter(this,drawerListItems);
        drawerListview.setAdapter(drawerListviewAdapter);

        drawerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                closeDrawer();
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen,R.string.drawerClose) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.syncState();

        drawerImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawers();
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
    }

    private void setToolbarTitle(String title){

        toolbarTitleTextview.setText(title);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
    }
}
