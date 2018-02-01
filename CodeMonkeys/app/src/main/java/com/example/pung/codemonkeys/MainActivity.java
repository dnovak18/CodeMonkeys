package com.example.pung.codemonkeys;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
ViewPager viewPager;
CustomSwipe customSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        customSwipe = new CustomSwipe(this);
        viewPager.setAdapter(customSwipe);

    }
    public void scanClick(MenuItem item) {
        Intent scanClick = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(scanClick);
    }



    public void searchClick(MenuItem item) {
        Intent searchClick = new Intent(MainActivity.this, MapsActivity.class);
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
