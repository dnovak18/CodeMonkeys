package com.example.pung.codemonkeys;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    ListView search;
    ViewPager viewPager;
    CustomSwipe customSwipe;
    DatabaseHelper myDbHelper;
    BottomNavigationView menuView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new AboutDeveloperFragment()).addToBackStack(null).commit();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        menuView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        // uncomment we don't want animation menu shifting
        //BottomNavigationViewHelper.removeShiftMode(menuView);
        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch (item.getItemId()){
                    case R.id.scan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new ScannerFragment()).addToBackStack(null).commit();
                        return true;

                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new  SearchFragment()).addToBackStack(null).commit();
                        return true;

                    case R.id.map:
                        Intent mapClick = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(mapClick);
                        return true;

                    case R.id.profile:
                        Intent profileClick = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(profileClick);
                        return true;
                }
                return false;
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

    public void infoClick(View view) {

        Toast toast = Toast.makeText(MainActivity.this, "Cheeeeers!", Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.RED);
        v.setTextSize(40);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
