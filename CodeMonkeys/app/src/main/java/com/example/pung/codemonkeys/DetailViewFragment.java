package com.example.pung.codemonkeys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prath on 3/24/2018.
 */

public class DetailViewFragment extends Fragment {
    String breweryName;
    String breweryAddress;
    String breweryPhone;
    String breweryEmail;
    String breweryWebsite;
    List<Search> deatilBeerList;
    ListView beerDetailList;
    SearchDetailAdapter adapterBeer;
    DatabaseHelper myDbHelper;
    String breweryZip;
    String breweryCity;
    String beerType;
    String beerName;

    public DetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);

        myDbHelper = new DatabaseHelper(getContext());
        myDbHelper.openDataBase();

        Bundle bundle = getArguments();

        breweryName = bundle.getString("breweryName");
        breweryAddress = bundle.getString("breweryAddress");
        breweryPhone = bundle.getString("breweryPhone");
        breweryEmail = bundle.getString("breweryEmail");
        breweryWebsite = bundle.getString("breweryWebsite");

        deatilBeerList = new ArrayList<>();
        getBreweryResult();
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        beerDetailList = (ListView)view.findViewById(R.id.beer_name_detail);
        adapterBeer = new SearchDetailAdapter(getActivity().getApplicationContext(), deatilBeerList,fragmentManager);
       // Toast.makeText(getActivity(), "good", Toast.LENGTH_LONG).show();
        beerDetailList.setAdapter(adapterBeer);

        TextView breweryNameChange = (TextView)view.findViewById(R.id.brewery_name_detail_textView);
        breweryNameChange.setText(breweryName);

        TextView breweryAddressChange = (TextView)view.findViewById(R.id.brewery_address_detail_textView);
        breweryAddressChange.setText(breweryAddress);

        TextView breweryPhoneChange = (TextView)view.findViewById(R.id.brewery_phone_detail_textView_change);
        breweryPhoneChange.setText(breweryPhone);

        TextView breweryEmailChange = (TextView)view.findViewById(R.id.brewery_email_detail_textView_change);
        breweryEmailChange.setText(breweryEmail);

        TextView breweryWebsiteChange = (TextView)view.findViewById(R.id.brewery_website_detail_textView_change);
        breweryWebsiteChange.setText(breweryWebsite);


        return view;

    }

    public void getBreweryResult(){
        String beerName = null;
        String beerType = null;
        String ABV = null;


        String find = "SELECT beer_name, beer_type, ABV FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID where brewery_name= '" +breweryName+"'";


        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(find,null);
        if(cursor.moveToFirst()){

            beerName = "Beer Name: "+cursor.getString(0);
            beerType = "Beer Type: "+ cursor.getString(1);
            ABV = "Beer Type: "+cursor.getString(2);
            deatilBeerList.add(new Search(1,beerName, beerType, ABV));

            while(cursor.moveToNext()==true){
                beerName = "Beer Name: "+ cursor.getString(0);
                beerType = "Beer Type: "+ cursor.getString(1);
                ABV = "Beer ABV: "+cursor.getString(2);
                //Toast.makeText(getActivity(), ABV, Toast.LENGTH_LONG).show();
                deatilBeerList.add(new Search(1,beerName, beerType, ABV));
            }

        }

        cursor.close();
    }

}
