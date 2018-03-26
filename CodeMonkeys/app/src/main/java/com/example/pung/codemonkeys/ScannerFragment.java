package com.example.pung.codemonkeys;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;


public class ScannerFragment extends Fragment implements ZXingScannerView.ResultHandler{
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camID = Camera.CameraInfo.CAMERA_FACING_BACK;
    DatabaseHelper myDbHelper;
    String breweryNameText;
    String breweryAddressText;
    String breweryZipText;
    String breweryCityText;
    String breweryStateText;
    String beerTypeText;
    String beerNameText;
    String breweryPhoneText;
    String breweryEmailText;
    String breweryWebsiteText;


    public ScannerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        scannerView = new ZXingScannerView(getActivity());
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        return scannerView;
    }

    @Override
    public void onResume() {
        super.onResume();


        int currentapiVersion = Build.VERSION.SDK_INT;
        if(currentapiVersion >= Build.VERSION_CODES.KITKAT){
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(getActivity());
                    //setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }



    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]){
        switch(requestCode){
            case REQUEST_CAMERA:
                if(grantResults.length > 0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Access Denied", Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(CAMERA)){
                                displayAlertMessage("You Need To Allow Permissions", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                        }

                                    }
                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener okListener) {

        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .create()
                .show();


    }

    public void handleResult(Result result){

        myDbHelper = new DatabaseHelper(getActivity());
        myDbHelper.openDataBase();
//SQL statement

        //String find = "SELECT * FROM beer_table JOIN brewery_table ON beer_table.beer_ID = beer_table.beer_ID WHERE beer_barcode = " + result.toString();
        String find = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip, brewery_phone, brewery_email, brewery_website FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID where beer_barcode = " + result.toString();

        Cursor cursor = myDbHelper.rawQuery(find, null);

        //String beerName = null;
        //String beerType = null;
        String brewery = null;
        String address = null;
        String phone = null;
        String email = null;
        String website = null;
        //String city = null;
        //String state = null;
        //String zip = null;

        if(cursor.moveToFirst()) {

            brewery = cursor.getString(0);
            address = cursor.getString(1);
            address += cursor.getString(2);
            address += cursor.getString(3);
            address += cursor.getString(4);
            phone = cursor.getString(5);
            email = cursor.getString(6);
            website = cursor.getString(7);

        }
        if(brewery != null) {

            breweryNameText = brewery.toString();
            breweryAddressText = address.toString();
            breweryPhoneText = phone.toString();
            breweryEmailText = email.toString();
            breweryWebsiteText = website.toString();
            //beerTypeText = beerType.toString();
            //beerNameText = beerName.toString();
            Toast.makeText(getActivity(), breweryAddressText, Toast.LENGTH_LONG).show();
            DetailViewFragment detailsFragment = new DetailViewFragment();

            Bundle detailsBundle = new Bundle();

            detailsBundle.putString("breweryName", breweryNameText);
            detailsBundle.putString("breweryAddress", breweryAddressText);
            detailsBundle.putString("breweryPhone", breweryPhoneText);
            detailsBundle.putString("breweryEmail", breweryEmailText);
            detailsBundle.putString("breweryWebsite", breweryWebsiteText);
            //detailsBundle.putString("beerName", beerNameText);
            //detailsBundle.putString("beerType", beerTypeText);

    /* reference
    Intent myintent=new Intent(Info.this, GraphDiag.class).putExtra("<StringName>", value);
    startActivity(myintent);
    use the below code in child activity

    String s= getIntent().getStringExtra(<StringName>);

     */


            detailsFragment.setArguments(detailsBundle);
            //getSupportFragmentManager().beginTransaction().replace(R.id.activity_scanner, new DetailViewFragment()).addToBackStack(null).commit();

            // Intent scanClick = new Intent(ScanActivity.this, DetailViewFragment.class);
            //startActivity(scanClick);
            FragmentManager mFragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            //DetailViewFragment detailViewFragment = new DetailViewFragment();
            fragmentTransaction.replace(R.id.activity_main, detailsFragment);
            fragmentTransaction.commit();


        }else {
//pop up after scan error if the beer is not in database
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Uh Oh!!!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    scannerView.resumeCameraPreview(ScannerFragment.this);
                }

            });
//pop up message to display the error.
            builder.setMessage("Look's like something went wrong. Try again or another UPC" + "\n\nSorry for the inconvenience");
            AlertDialog alert1 = builder.create();
            alert1.show();
        }
    }

}
