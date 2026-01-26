package com.msnegi.googlemapdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment implements OnMapReadyCallback
{
    private static final String TAG = "GPS_check";
    MapView mapView;
    GoogleMap googleMap;
    float zoomLevel = 15f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap mMap)
    {
        Double lat = 28.5022851;
        Double lng = 77.0857269;

        googleMap = mMap;
        if (googleMap != null) {
            setupMap(lat, lng);
        }
    }

    private void setupMap(double mLat, double mLong) {
        LatLng mLoc = new LatLng(mLat, mLong);

        googleMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(mLoc);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
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


}