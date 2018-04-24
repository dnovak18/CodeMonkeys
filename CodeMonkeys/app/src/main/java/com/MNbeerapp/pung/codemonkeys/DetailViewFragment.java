package com.MNbeerapp.pung.codemonkeys;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import android.support.v7.widget.Toolbar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.location.places.*;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.nio.file.AccessDeniedException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by prath on 3/24/2018.
 */


//public class DetailViewFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback,  GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener {
public class DetailViewFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback,  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    String breweryName;
    String breweryAddress;
    String breweryPhone;
    String breweryEmail;
    String breweryWebsite;
    List<Search> detailBeerList;
    ListView beerDetailList;
    SearchDetailAdapter adapterBeer;
    DatabaseHelper myDbHelper;
    MapView mapView;
    GoogleMap mMap;
    private int MY_LOCATION_REQUEST_CODE;
    private int ACCESS_FINE_LOCATION;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    RatingBar ratingBar;
    public DetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            getActivity().finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }

    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getActivity());
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        0).show();
            }
            return false;
        }
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);

        myDbHelper = new DatabaseHelper(getContext());
        myDbHelper.openDataBase();


        //map = mapView.getMap();

       // map.getUiSettings().setMyLocationButtonEnabled(false);
       //mMap.setMyLocationEnabled(true);

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

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

       // ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
         //   @Override
           // public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
             //   ratingBar.setNumStars((int) ratingBar.getRating());
            //}
        //});

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
        breweryName = breweryName.trim();
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

    /*
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng sydney = new LatLng(-44.94, 151.211);
        LatLng msp = new LatLng(44.95, -93.2);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(msp, 5));
    }
    */

    //@Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

      //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();

            }
        }
        else {
            buildGoogleApiClient();

        }

            mMap.setOnMyLocationClickListener(this);
            enableMyLocation();

        LatLng address = getLocationFromAddress(getActivity(), breweryAddress);
        mMap.addMarker(new MarkerOptions().position(address).title(breweryName));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, 10));


    }//End onMapReady

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }

    private void enableMyLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getContext(),
               android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override

    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f",latitude,longitude));
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean onMyLocationButtonClick() {

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
           // enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }


}
