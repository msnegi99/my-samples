package com.msnegi.googlecheckinoutdemo.checkinout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.msnegi.googlecheckinoutdemo.R;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CheckInOutFragment extends Fragment implements LatLongReceived, OnMapReadyCallback  {

    private static final String TAG = "GPS_check";

    MapView mapViwe;
    private OnFragmentInteractionListener mListener;
    LocManager locationManager;
    GoogleMap mMap;
    float zoomLevel = 15f;
    Button checkInBtn,checkOutBtn;
    double mLatitude;
    double mLongitude;
    double officeLat = 28.5022851;
    double officeLong = 77.0857269;

    public CheckInOutFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_check_in_out, container, false);
        mapViwe = view.findViewById(R.id.mapView);
        mapViwe.onCreate(savedInstanceState);
        mapViwe.getMapAsync(this);

        checkInBtn = view.findViewById(R.id.checkInBtn);
        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLatLongDistance() > 500) {
                    checkInBtn.setEnabled(false);
                    saveProject("", 0);
                } else {
                    Toast.makeText(getActivity(),"Too distant from office!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkOutBtn = view.findViewById(R.id.checkOutBtn);
        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDayInMarked()) {
                    Toast.makeText(getActivity(),"You can not check-out without doing checkin",Toast.LENGTH_SHORT).show();
                }else{
                    if (getLatLongDistance() > 500) {
                        checkInBtn.setEnabled(false);
                        saveProject("", 0);
                    } else {
                        Toast.makeText(getActivity(),"Too distant from office!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private float getLatLongDistance(){
        Location locationA = new Location("point A");
        locationA.setLatitude(mLatitude);
        locationA.setLongitude(mLongitude);

        Location locationB = new Location("point B");
        locationB.setLatitude(Double.valueOf(officeLat));
        locationB.setLongitude(Double.valueOf(officeLong));

        float dist = locationA.distanceTo(locationB);  //-- gives a strait distance between the two lat-longs
        return dist;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*Double lat = 28.5022851;
        Double lng = 77.0857269;
        LatLng coordinates = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(coordinates));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
        mapViwe.onResume();*/

        mMap = googleMap;
        if(mMap != null){
            //init GPS location
            if(isGPSEnabled(getActivity())){
                Toast.makeText(getActivity(),"GPS enabled", Toast.LENGTH_SHORT).show();
                locationManager = LocManager.getInstance();
                locationManager.connectGoogleClient(getActivity(),this);
            }else{
                Toast.makeText(getActivity(),"GPS Disabled", Toast.LENGTH_SHORT).show();
                displayLocationSettingsRequest(getActivity());
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //---------------------------------------------------------------

    public static boolean isGPSEnabled(Context context){
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
                            status.startResolutionForResult(getActivity(), 9595);
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
        if(requestCode == 9595 && resultCode == RESULT_OK){
            Toast.makeText(getActivity(),"gps enabled",Toast.LENGTH_SHORT).show();

            locationManager = LocManager.getInstance();
            locationManager.connectGoogleClient(getActivity(),this);

        }else if(requestCode == 9595 && resultCode == RESULT_CANCELED){
            Toast.makeText(getActivity(),"gps disabled",Toast.LENGTH_SHORT).show();
            //finish();
        }
    }

    @Override
    public void onLocationReceived(Location loc) {
        System.out.println("Lat : " + loc.getLatitude() + " Long : " + loc.getLongitude() );
        mLatitude = loc.getLatitude();
        mLongitude = loc.getLongitude();
        setupMap(loc.getLatitude(), loc.getLongitude() );
    }

    private void setupMap(double mLat, double mLong){
        LatLng mLoc = new LatLng(mLat, mLong);

        mMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(mLoc);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(options);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat,mLong),5));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(mLat,mLong))
                .zoom(zoomLevel)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mapViwe.onResume();
    }

    private void saveProject(String partnerCode,int checkInType) {
        //-- call service to save attendance
        Toast.makeText(getActivity(),"Call service to save the attendance",Toast.LENGTH_SHORT).show();
    }

    /*public void onSuccess(Response response, int tag) {
        if (tag == SAVE_CHECKIN_STATUS) {
            checkInBtn.setEnabled(true);
            if (response.getIsSuccess()) {
                AlertDialogUtils.messageWithResponseDialog(this, "You are successfully check in from " + officeLocation, new AlertMagnatic(){
                    @Override
                    public void onButtonClicked(boolean value) {
                        if(value){
                            dayInMarked();
                        }
                    }
                });
            }
        }else if (tag == SAVE_CHECKOUT_STATUS) {
            checkOutBtn.setEnabled(true);
            if (response.getIsSuccess()) {
                AlertDialogUtils.messageWithResponseDialog(this, "You are successfully check out from " + workLocation, new AlertMagnatic(){
                    @Override
                    public void onButtonClicked(boolean value) {
                        if(value){
                        //-- go back to previous screen
                        }
                    }
                });
            }
        }
    }*/

    public boolean isDayInMarked(){
        /*String mCurrDate = DateUtils.getCurrentDate();
        String mLastAtt  = mySharedPreferences.getStringFromPreference("LAST_ATT_DATE");
        if(mCurrDate.equalsIgnoreCase(mLastAtt)){
            return true;
        }else{
            return false;
        }*/
        return false;
    }

    /*public void dayInMarked(){
        String mCurrDate = DateUtils.getCurrentDate();
        mySharedPreferences.saveStringToPreference("LAST_ATT_DATE", mCurrDate);
    }*/

    public void onError(String msg) {
        //checkInBtn.setEnabled(true);
       // AlertDialogUtils.registermessageDialog(this, "Error : " + msg);
    }
}