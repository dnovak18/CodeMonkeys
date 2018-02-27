package com.example.pung.codemonkeys;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.database.SQLException;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
LockableViewPager view;
ViewPager viewPager;
CustomSwipe customSwipe;
DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
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

    public void scanClick(MenuItem item) {
        Intent scanClick = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(scanClick);
    }



    public void searchClick(MenuItem item) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new  SearchFragment()).addToBackStack(null).commit();

    }
    public void mapClick(MenuItem item) {
        Intent mapClick = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(mapClick);
    }

    public void profileClick(MenuItem item) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new MyProfileFragment()).addToBackStack(null).commit();
    }


    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
