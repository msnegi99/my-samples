package com.msnegi.googlecheckinoutdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.msnegi.googlecheckinoutdemo.R;

import com.msnegi.googlecheckinoutdemo.firstupdate.LatLongReceived;
import com.msnegi.googlecheckinoutdemo.firstupdate.LocManager;
import com.msnegi.googlecheckinoutdemo.regularupdate.LocData;

public class HomeFragment extends Fragment implements OnMapReadyCallback, LatLongReceived
{
    private static final String TAG = "GeoFence";
    GoogleApiClient googleApiClient;
    private MapView mapView;
    private GoogleMap googleMap;
    private Marker locationMarker;
    float zoomLevel = 15f;
    LocManager locationManager;

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

        getActivity().registerReceiver(getLocationReceiver, new IntentFilter("get_location"));
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
        getActivity().unregisterReceiver(getLocationReceiver);
    }

    private final BroadcastReceiver getLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            try
            {
                Bundle bundle = intent.getExtras();
                LocData locData = (LocData) bundle.getSerializable("data");

                System.out.println("lat : " + locData.getLatitude() + " long : " + locData.getLongitude());

                markLocation(new LatLng(Double.parseDouble(locData.getLatitude()), Double.parseDouble(locData.getLongitude())),"current", locData.getTime(), locData.getAltitude(), locData.getSpeed(), locData.getAccuracy(), locData.getBattery_level());
            }
            catch (NullPointerException exception){
            }
            catch(Exception e){
            }
        }
    };

    private void markLocation(LatLng latLng, String type, String time, String altitude, String speed, String accuracy, String battery_level)
    {
        //String title = "Time: " + time + "Altitude: " + altitude + " Speed: " + speed + " Accuracy: " + accuracy + " Battery: " + battery_level ;
        Double spe = (Double.parseDouble(speed) * 3.6);
        float sp = Math.round(spe);
        String title = "Time: " + time + " Speed: " + sp  + " km/h";

        if ( googleMap!=null )
        {
            //float zoom = 18f;                     //Default zoom is 14f

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(zoomLevel)
                    .bearing(0)
                    .tilt(35)
                    .build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            if(type.equalsIgnoreCase("current"))
            {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .draggable(false)
                        .title(title);

                if ( locationMarker != null ) locationMarker.remove();
                locationMarker = googleMap.addMarker(markerOptions);
            }
            else if(type.equalsIgnoreCase("history"))
            {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latLng.latitude, latLng.longitude))
                        .title(title)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.greendot)));
            }
        }
    }
}