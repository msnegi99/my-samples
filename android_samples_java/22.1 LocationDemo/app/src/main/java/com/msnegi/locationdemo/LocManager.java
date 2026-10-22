package com.msnegi.locationdemo;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

public class LocManager implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LocManager.class.getSimpleName();
    private static LocManager instance;
    public GoogleApiClient mGoogleApiClient;
    private Context mContext;
    //public static final int LOCATION_UPDATE_INTERVAL = 3 * 60 * 1000;    // after 10 mints
    //public static final int LOCATION_FASTEST_UPDATE_INTERVAL = 50 * 1000;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;  //benificial in case of services only
    LocationRequest mLocationRequest;
    public Location mCurrentLocation;
    String mLastUpdateTime = "";
    LatLongReceived latlongRec;

    public static LocManager getInstance() {
        if (instance == null) {
            instance = new LocManager();
        }
        return instance;
    }

    public void connectGoogleClient(Context context, LatLongReceived latlongRec) {
        this.mContext = context;
        this.latlongRec = latlongRec;
        if (isGooglePlayServiceAvailable(context)) {
            buildGoogleApiClient(context);
        }
    }

    public static boolean isGooglePlayServiceAvailable(Context context) {
        return checkPlayServices(context);
    }

    public static boolean checkPlayServices(Context context) {
        GoogleApiAvailability gApi = GoogleApiAvailability.getInstance();
        int resultCode = gApi.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (gApi.isUserResolvableError(resultCode)) {
                gApi.getErrorDialog((Activity) context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(context, "Play Services not available", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    private synchronized void buildGoogleApiClient(Context context) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged() returned: " + location);
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        //updateUI();
        latlongRec.onLocationReceived(location);

        stopLocationUpdates();   //-- stop if needs gps only once
        disconnectGoogleClient();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    private void startLocationUpdates() throws SecurityException {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    public void disconnectGoogleClient() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended() returned: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed() returned: ");
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        Log.e(TAG, "Location update stopped .......................");
    }

}
