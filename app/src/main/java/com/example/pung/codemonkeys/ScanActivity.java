package com.example.pung.codemonkeys;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camID = Camera.CameraInfo.CAMERA_FACING_BACK;
    DatabaseHelper myDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(ScanActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }


        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(ScanActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();


        int currentapiVersion = Build.VERSION.SDK_INT;
        if(currentapiVersion >=android.os.Build.VERSION_CODES.M){
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
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
                        Toast.makeText(ScanActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(ScanActivity.this, "Access Denied", Toast.LENGTH_LONG).show();
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

        new android.support.v7.app.AlertDialog.Builder(ScanActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .create()
                .show();


    }
    //Zxing library class. The variable result is the upc that is scanned
    public void handleResult(Result result){

        myDbHelper = new DatabaseHelper(ScanActivity.this);
        myDbHelper.openDataBase();
//SQL statement
        String find = "SELECT * FROM beer_table JOIN brewery_table ON beer_table.beer_ID = beer_table.beer_ID WHERE beer_barcode = " + result.toString();


        Cursor cursor = myDbHelper.rawQuery(find, null);


        String beerName = null;
        String beerType = null;
        String brewery = null;
        String address = null;


        String city = null;
        String state = null;
        String zip = null;

        if(cursor.moveToFirst()) {

            brewery = cursor.getString(6);
            address = cursor.getString(7);
            city = cursor.getString(8);
            state = cursor.getString(9);
            zip = cursor.getString(10);
            beerName = cursor.getString(1);
            beerType = cursor.getString(2);



        }

//pop up after scan should return the query result
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(ScanActivity.this);
            }

        });
//pop up message to display the results.
        builder.setMessage("Brewery: "+ brewery + "\nAddress: "+ address + "\n" + city + ", " + state + "\n" + zip + "\nBeer: " + beerName + " \nType: " + beerType);
        AlertDialog alert1 = builder.create();
        alert1.show();

    }
}
