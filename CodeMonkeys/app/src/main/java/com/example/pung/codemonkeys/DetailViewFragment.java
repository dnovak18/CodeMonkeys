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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prath on 3/24/2018.
 */

public class DetailViewFragment extends Fragment implements OnMapReadyCallback{
    String breweryName;
    String breweryAddress;
    String breweryPhone;
    String breweryEmail;
    String breweryWebsite;
    List<Search> detailBeerList;
    ListView beerDetailList;
    SearchDetailAdapter adapterBeer;
    DatabaseHelper myDbHelper;
    String breweryZip;
    String breweryCity;
    String beerType;
    String beerName;
    MapView mapView;
    GoogleMap map;
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


        //map = mapView.getMap();

       // map.getUiSettings().setMyLocationButtonEnabled(false);
        //map.setMyLocationEnabled(true);

        Bundle bundle = getArguments();

        breweryName = bundle.getString("breweryName");
        breweryAddress = bundle.getString("breweryAddress");
        breweryPhone = bundle.getString("breweryPhone");
        breweryEmail = bundle.getString("breweryEmail");
        breweryWebsite = bundle.getString("breweryWebsite");

        detailBeerList = new ArrayList<>();
        getBreweryResult();
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        beerDetailList = (ListView)view.findViewById(R.id.beer_name_detail);
        adapterBeer = new SearchDetailAdapter(getActivity().getApplicationContext(), detailBeerList,fragmentManager);
       // Toast.makeText(getActivity(), "good", Toast.LENGTH_LONG).show();
        beerDetailList.setAdapter(adapterBeer);

        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.detail_header, null);
        beerDetailList.addHeaderView(headerView);

        TextView breweryNameChange = (TextView)headerView.findViewById(R.id.brewery_name_detail_textView);
        breweryNameChange.setText(breweryName);

        TextView breweryAddressChange = (TextView)headerView.findViewById(R.id.brewery_address_detail_textView);
        breweryAddressChange.setText(breweryAddress);

        TextView breweryPhoneChange = (TextView)headerView.findViewById(R.id.brewery_phone_detail_textView_change);
        breweryPhoneChange.setText(breweryPhone);

        TextView breweryEmailChange = (TextView)headerView.findViewById(R.id.brewery_email_detail_textView_change);
        breweryEmailChange.setText(breweryEmail);

        TextView breweryWebsiteChange = (TextView)headerView.findViewById(R.id.brewery_website_detail_textView_change);
        breweryWebsiteChange.setText(breweryWebsite);

        mapView = (MapView) headerView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return view;

    }

    public void getBreweryResult(){
        String beerName = null;
        String beerType = null;
        String ABV = null;
        breweryName = breweryName.replace("'","''");
        String find = "SELECT beer_name, beer_type, ABV FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID where brewery_name= '" +breweryName+"'";


        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(find,null);
        if(cursor.moveToFirst()){

            beerName = "Beer Name: "+cursor.getString(0);
            beerType = "Beer Type: "+ cursor.getString(1);
            ABV = "Beer Type: "+cursor.getString(2);
            detailBeerList.add(new Search(1,beerName, beerType, ABV));

            while(cursor.moveToNext()==true){
                beerName = "Beer Name: "+ cursor.getString(0);
                beerType = "Beer Type: "+ cursor.getString(1);
                ABV = "Beer ABV: "+cursor.getString(2);
                //Toast.makeText(getActivity(), ABV, Toast.LENGTH_LONG).show();
                detailBeerList.add(new Search(1,beerName, beerType, ABV));
            }

        }

        cursor.close();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        LatLng sydney = new LatLng(-33.852, 151.211);
        //map.addMarker(new MarkerOptions().position(/*some location*/));
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(/*some location*/, 10));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
