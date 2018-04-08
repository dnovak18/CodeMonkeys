package com.MNbeerapp.pung.codemonkeys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {
    ListView search;
    View view;
    DetailsListAdapter adapter;
    List<Details> detailProductList;
    DatabaseHelper myDbHelper;
    String beerName;
    String breweryNameResult;
    String breweryAddress;
    String City;
    String State;
    String Zip;
    String type;


    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);
       Bundle detailsBundle = new Bundle();

      breweryNameResult =  detailsBundle.getString("brewery");
      breweryAddress = detailsBundle.getString("address");
      City = detailsBundle.getString("city");
      State = detailsBundle.getString("state");
      Zip = detailsBundle.getString("zip");
      beerName = detailsBundle.getString("beerName");
      type = detailsBundle.getString("beerType");



        myDbHelper = new DatabaseHelper(getContext());
        myDbHelper.openDataBase();
        detailProductList = new ArrayList<>();


        getBreweryResult();
        search = (ListView)view.findViewById(R.id.beer_name_detail);
        adapter = new DetailsListAdapter(getActivity().getApplicationContext(), detailProductList);
        search.setAdapter(adapter);


        return view;
    }

    public void getBreweryResult(){
       String breweryNameResult;
       String breweryAddress;
       String type;
       String beerNameResult;
       String beerABVResult;
       String breweryPhone;
       String breweryEmail;
       String breweryWebsite;
       String findDetails;
       String breweryCity = null;
       String breweryState = null;
       String breweryZip = null;


//SQL statement
        findDetails = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip,brewery_phone,brewery_website,brewery_email,beer_name,beer_type,ABV FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID";



        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(findDetails,null);
        if(cursor.moveToFirst()){

            breweryNameResult = cursor.getString(0);
            breweryAddress = cursor.getString(1);
            breweryCity += " "+cursor.getString(2)+",";
            breweryState += " "+cursor.getString(3);
            breweryZip += " "+cursor.getString(4);
            beerNameResult = "Beer Name: "+cursor.getString(8);
            type = "Type: "+cursor.getString(9);
            beerABVResult = "ABV: "+cursor.getString(10);
            breweryPhone = "Phone: "+cursor.getString(5);
            breweryEmail = "Email: "+cursor.getString(7);
            breweryWebsite = "Website: "+cursor.getString(6);
            detailProductList.add(new Details(1,breweryNameResult, breweryAddress,type,beerNameResult, beerABVResult, breweryPhone, breweryEmail,breweryWebsite));

            while(cursor.moveToNext()==true){
                breweryNameResult = cursor.getString(0);
                breweryAddress = cursor.getString(1);
                breweryCity += " "+cursor.getString(2)+",";
                breweryState += " "+cursor.getString(3);
                breweryZip += " "+cursor.getString(4);
                beerNameResult = "Beer Name: "+cursor.getString(8);
                type = "Type: "+cursor.getString(9);
                beerABVResult = "ABV: "+cursor.getString(10);
                breweryPhone = "Phone: "+cursor.getString(5);
                breweryEmail = "Email: "+cursor.getString(7);
                breweryWebsite = "Website: "+cursor.getString(6);
                detailProductList.add(new Details(1,breweryNameResult, breweryAddress,type,beerNameResult, beerABVResult, breweryPhone, breweryEmail,breweryWebsite));

            }

        }

        cursor.close();
    }
}
