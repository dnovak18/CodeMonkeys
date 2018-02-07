package com.example.pung.codemonkeys;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.database.SQLException;
import java.io.IOException;


public class MainActivity extends Activity {
ViewPager viewPager;
CustomSwipe customSwipe;
DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        customSwipe = new CustomSwipe(this);
        viewPager.setAdapter(customSwipe);

        myDbHelper = new DatabaseHelper(MainActivity.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

    }

   // public void getName(){
   //     SQLiteDatabase db = myDbHelper.getWritableDatabase();
   //     db.rawQuery();
   // }
    public void scanClick(MenuItem item) {
        Intent scanClick = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(scanClick);
    }



    public void searchClick(MenuItem item) {
        Intent searchClick = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(searchClick);
    }
    public void mapClick(MenuItem item) {
        Intent mapClick = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(mapClick);
    }

    public void profileClick(MenuItem item) {
        Intent profileClick = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(profileClick);
    }

    //protected void mapClick (View v){
//        Intent mapClick = new Intent(MainActivity.this, MapsActivity.class);
//        startActivity(mapClick);
    //}
}
