package com.example.pung.codemonkeys;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SearchActivity extends Activity {
    //THIS CLASS WILL BE DELETED AFTER WE GET SearchFragment up and running***********
    //Cursor c;
    DatabaseHelper myDbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        myDbHelper = new DatabaseHelper(SearchActivity.this);

        myDbHelper.openDataBase();
    }

    public void search(View v) {
        TextView brewery_name_textView=(TextView)findViewById(R.id.brewery_name_textView);
        TextView brewery_address_textView=(TextView)findViewById(R.id.brewery_address_textView);
        brewery_name_textView.setText(getBreweryName());
        brewery_address_textView.setText(getBreweryAddress());
    }
    //sample query for brewery name
    public String getBreweryName(){
        String breweryName = null;
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String find = "SELECT brewery_name, brewery_address FROM brewery_table";
        Cursor cursor = db.rawQuery(find,null);
        if(cursor.moveToFirst()){
            breweryName = cursor.getString(0);
        }
        cursor.close();
        return breweryName;
    }
    //sample query for brewery address
    public String getBreweryAddress(){
        String breweryAddress = null;
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String find = "SELECT brewery_name, brewery_address FROM brewery_table";
        Cursor cursor = db.rawQuery(find,null);
        if(cursor.moveToFirst()){
            breweryAddress = cursor.getString(1);
        }
        cursor.close();
        return breweryAddress;
    }
}
