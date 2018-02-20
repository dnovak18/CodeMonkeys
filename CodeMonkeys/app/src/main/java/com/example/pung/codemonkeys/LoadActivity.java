package com.example.pung.codemonkeys;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class LoadActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    AnimationDrawable animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LoadActivity.this, MainActivity.class);
                startActivity(homeIntent);
                ImageView loading = (ImageView)findViewById(R.id.loading_image);
                animation = (AnimationDrawable)loading.getDrawable();
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
