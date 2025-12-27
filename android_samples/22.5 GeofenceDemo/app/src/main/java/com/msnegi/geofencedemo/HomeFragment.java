package com.msnegi.geofencedemo;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.msnegi.geofencedemo.firstupdate.LatLongReceived;
import com.msnegi.geofencedemo.firstupdate.LocManager;
import com.msnegi.geofencedemo.regularupdate.LocData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements LatLongReceived, OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener
{
    private static final String TAG = "GeoFence";
    GoogleApiClient googleApiClient;
    private MapView mapView;
    private GoogleMap googleMap;
    private Marker locationMarker;
    float zoomLevel = 15f;
    LocManager locationManager;

    public static final String NEW_GEOFENCE_NUMBER = BuildConfig.APPLICATION_ID + ".NEW_GEOFENCE_NUMBER";
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 365 * 24;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 100; // 100 m
    private static final int PERMISSIONS_REQUEST = 105;
    private static final int PERMISSION_REQUEST_CODE = 110;
    protected ArrayList<Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;
    private SharedPreferences mSharedPreferences;
    public static final String SHARED_PREFERENCES_NAME = BuildConfig.APPLICATION_ID + ".SHARED_PREFERENCES_NAME";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mGeofenceList = new ArrayList<>();
        mGeofencePendingIntent = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                                             .addApi(LocationServices.API).build();
        googleApiClient.connect();
        locationManager = LocManager.getInstance();
        locationManager.connectGoogleClient(getActivity(), this);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap mMap)
    {
        googleMap = mMap;
        mapView.onResume();

        googleMap.setTrafficEnabled(true);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onLocationReceived(Location loc) {
        System.out.println("first Lat : " + loc.getLatitude() + " Long : " + loc.getLongitude());
        setupMap(loc.getLatitude(), loc.getLongitude());
    }

    private void setupMap(double mLat, double mLong) {
        LatLng mLoc = new LatLng(mLat, mLong);

        googleMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(mLoc);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(options);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLong), 5));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(mLat, mLong))
                .zoom(zoomLevel)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //----------------- Geofence setup ----------------

    private int getNewGeofenceNumber() {
        int number = mSharedPreferences.getInt(NEW_GEOFENCE_NUMBER, 0);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(NEW_GEOFENCE_NUMBER, number + 1);
        editor.commit();
        return number;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        final String key = getNewGeofenceNumber() + "";
        createGeofence(new LatLng(latLng.latitude, latLng.longitude),latLng.latitude,latLng.longitude,100,365*24*60*60*1000,key);
    }

    public void createGeofence(LatLng latLng, final double lat, final double lng, final int radius, final int expire, final String key)
    {
        //-- draw a circle on map.
        googleMap.addMarker(new MarkerOptions()
                .title("G:" + key)
                .snippet("Click here if you want delete this geofence")
                .position(latLng));

        googleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .fillColor(Color.parseColor("#80ff0000")));

        //final String key = getNewGeofenceNumber() + "";
        final long expTime = System.currentTimeMillis() + GEOFENCE_EXPIRATION_IN_MILLISECONDS;

        Geofence geofence = new Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(
                        latLng.latitude,
                        latLng.longitude,
                        GEOFENCE_RADIUS_IN_METERS)
                .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();

        try
        {
            LocationServices.GeofencingApi.addGeofences(googleApiClient, getGeofencingRequest(geofence), getGeofencePendingIntent())
                    .setResultCallback(new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(@NonNull Status status)
                        {
                            if (status.isSuccess()) {
                                //db.addGeoLocation(lat,lng,radius,expire,key);
                            } else {
                                //String errorMessage = GeofenceTrasitionService.getErrorString(MainActivity.this, status.getStatusCode());
                                //Log.e(TAG, errorMessage);
                            }
                        }
                    });

        } catch (SecurityException securityException) {
            logSecurityException(securityException);
        }
    }

    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(geofence);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(getActivity(), GeofenceTrasitionService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }else {
            return PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //System.out.println("Title : " + marker.getTitle());
        return false;
    }

     @Override
    public void onInfoWindowClick(Marker marker) {
        final String key = marker.getTitle().split(":")[1];

         try{
             //googleMap.clear();
             removeGeofenceMark(key);
         }
         catch(Exception e){
         }
    }

    public void removeGeofenceMark(final String key)
    {
        if (!googleApiClient.isConnected()) {
            Toast.makeText(getActivity(), "GeoFence Not connected!", Toast.LENGTH_SHORT).show();
            return;
        }

        try
        {
            List<String> idList = new ArrayList<>();
            idList.add(key);
            LocationServices.GeofencingApi.removeGeofences(googleApiClient, idList)
                    .setResultCallback(new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(@NonNull Status status)
                        {
                            if (status.isSuccess()) {
                                Toast.makeText(getActivity(), "GeoFence removed successfully !", Toast.LENGTH_SHORT).show();

                                googleMap.clear();
                            } else {
                                // Get the status code for the error and log it using a user-friendly message.
                                String errorMessage = GeofenceTrasitionService.getErrorString(getActivity(), status.getStatusCode());
                                Log.e(TAG, errorMessage);
                                Toast.makeText(getActivity(), "Error removing GeoFence !" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (SecurityException securityException) {
            logSecurityException(securityException);
        }
    }

    private void logSecurityException(SecurityException securityException) {
        Log.e(TAG, "Invalid location permission. " +
                "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
    }

}