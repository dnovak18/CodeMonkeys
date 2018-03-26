package com.example.pung.codemonkeys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScanActivity extends Fragment implements ZXingScannerView.ResultHandler {

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


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        scannerView = new ZXingScannerView(getActivity());
        View view = inflater.inflate(R.layout.activity_scan, container, false);

        //setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            if (checkPermission()) {
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }


        }

        return scannerView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
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
    //Zxing library class. The variable result is the upc that is scanned
    public void handleResult(Result result){

        myDbHelper = new DatabaseHelper(getActivity());
        myDbHelper.openDataBase();
//SQL statement

        //String find = "SELECT * FROM beer_table JOIN brewery_table ON beer_table.beer_ID = beer_table.beer_ID WHERE beer_barcode = " + result.toString();
        String find = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID where beer_barcode = " + result.toString();

        Cursor cursor = myDbHelper.rawQuery(find, null);

        //String beerName = null;
        //String beerType = null;
        String brewery = null;
        String address = null;
        //String city = null;
        //String state = null;
        //String zip = null;

        if(cursor.moveToFirst()) {

            brewery = cursor.getString(0);
            address = cursor.getString(1);
            address += cursor.getString(2);
            address += cursor.getString(3);
            address += cursor.getString(4);
           // beerName = cursor.getString(5);
            //beerType = cursor.getString(6);

        }
if(brewery != null) {

    breweryNameText = brewery.toString();
    breweryAddressText = address.toString();
    //breweryZipText = zip.toString();
    //breweryCityText = city.toString();
    //breweryStateText = state.toString();
    //beerTypeText = beerType.toString();
    //beerNameText = beerName.toString();
    Toast.makeText(getActivity(), breweryAddressText, Toast.LENGTH_LONG).show();
    Fragment DetailsFragment = new Fragment();

    Bundle detailsBundle = new Bundle();

    detailsBundle.putString("breweryName", breweryNameText);
    detailsBundle.putString("breweryAddress", breweryAddressText);
    detailsBundle.putString("breweryPhone", breweryCityText);
    detailsBundle.putString("breweryEmail", breweryStateText);
    detailsBundle.putString("breweryWebsite", breweryZipText);
    //detailsBundle.putString("beerName", beerNameText);
    //detailsBundle.putString("beerType", beerTypeText);

    /* reference
    Intent myintent=new Intent(Info.this, GraphDiag.class).putExtra("<StringName>", value);
    startActivity(myintent);
    use the below code in child activity

    String s= getIntent().getStringExtra(<StringName>);

     */


    //DetailsFragment.setArguments(detailsBundle);
    //getSupportFragmentManager().beginTransaction().replace(R.id.activity_scanner, new DetailViewFragment()).addToBackStack(null).commit();

   // Intent scanClick = new Intent(ScanActivity.this, DetailViewFragment.class);
    //startActivity(scanClick);
    //FragmentManager mFragmentManager=this.getSupportFragmentManager();
    //FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    //DetailViewFragment detailViewFragment = new DetailViewFragment();
    //fragmentTransaction.replace(R.id.activity_scanner, detailViewFragment);
    //fragmentTransaction.commit();


}else {
//pop up after scan error if the beer is not in database
     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("Uh Oh!!!");
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
     @Override
    public void onClick(DialogInterface dialog, int which) {
     scannerView.resumeCameraPreview(ScanActivity.this);
    }

    });
//pop up message to display the error.
    builder.setMessage("Look's like something went wrong. Try again or another UPC" + "\n\nSorry for the inconvenience");
    AlertDialog alert1 = builder.create();
    alert1.show();
}
    }

}
