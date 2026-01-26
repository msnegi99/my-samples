package com.msnegi.locationdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LatLongReceived {

    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

    private static final String TAG = "Location";
    LocManager locationManager;
    TextView txtLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLatLong = findViewById(R.id.txtLatLong);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
        } else {
            //-- permissions are already given
            checkGPSSettings();
        }
    }

    public void checkGPSSettings(){
        if (isGPSEnabled(this)) {
            Toast.makeText(this, "GPS enabled", Toast.LENGTH_SHORT).show();
            locationManager = LocManager.getInstance();
            locationManager.connectGoogleClient(this, this);
        } else {
            Toast.makeText(this, "GPS Disabled", Toast.LENGTH_SHORT).show();
            displayLocationSettingsRequest(this);
        }
    }

    @Override
    public void onLocationReceived(Location loc) {
        System.out.println("Lat : " + loc.getLatitude() + " Long : " + loc.getLongitude());
        txtLatLong.setText("Lat : " + loc.getLatitude() + " Long : " + loc.getLongitude());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                boolean permission_denied = false;
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        permission_denied = true;
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            showAlert();
                        }
                    }
                }
                if(!permission_denied){
                    //-- move on to track location
                    checkGPSSettings();
                }
            }
        }
    }

    private void showAlert() {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs all required permissions to run functionalities !!!");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "Do not Allow",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "App required all permission to work properly", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //-- no action required, move on to application
                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
            }
        });
        alertDialog.show();
    }

    //---------------------------------------------------------------

    public static boolean isGPSEnabled(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, 8576);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8576 && resultCode == RESULT_OK) {
            Toast.makeText(this, "gps enabled", Toast.LENGTH_SHORT).show();

            locationManager = LocManager.getInstance();
            locationManager.connectGoogleClient(this, this);

        } else if (requestCode == 8576 && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "gps disabled", Toast.LENGTH_SHORT).show();
            //finish();
        }
    }

}