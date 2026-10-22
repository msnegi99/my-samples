package com.msnegi.geofencedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
//import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.msnegi.geofencedemo.regularupdate.MyLocationService;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "GeofenceDemo";
    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkGPSSettings();
        replaceFragment(new HomeFragment(), new Bundle(), "HomeFragment");
    }

    public void replaceFragment(Fragment fragment, Bundle bundle, String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if (fragment instanceof HomeFragment) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(null).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.msnegi.mapmyway.MyLocationService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void checkGPSSettings(){
        if (isGPSEnabled(this)) {
            Toast.makeText(this, "GPS enabled", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HomeActivity.this, permission, PERMISSIONS_REQUEST_CODE);
            } else {
                //-- permissions are already given
                if(!isServiceRunning()) {
                    Intent intent = new Intent(HomeActivity.this, MyLocationService.class);
                    HomeActivity.this.startService(intent);
                }
            }
        } else {
            Toast.makeText(this, "GPS Disabled", Toast.LENGTH_SHORT).show();
            displayLocationSettingsRequest(this);
        }
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
                    if(!isServiceRunning()) {
                        Intent intent = new Intent(HomeActivity.this, MyLocationService.class);
                        HomeActivity.this.startService(intent);
                    }
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
                        Toast.makeText(HomeActivity.this, "App required all permission to work properly", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //-- no action required, move on to application
                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.requestPermissions(HomeActivity.this, permission, PERMISSIONS_REQUEST_CODE);
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
                            status.startResolutionForResult(HomeActivity.this, 8576);
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
        } else if (requestCode == 8576 && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "gps disabled", Toast.LENGTH_SHORT).show();
            //finish();
        }
    }
}