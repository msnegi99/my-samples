package com.msnegi.googlemapdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.msnegi.googlemapdemo.databinding.FragmentHomeBinding

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker
import com.msnegi.locationdemo.LatLongReceived
import com.msnegi.locationdemo.LocManager

class HomeFragment : Fragment(), OnMapReadyCallback, LatLongReceived {

    private val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 101
        private const val TAG = "HomeFragment"
    }

    lateinit var googleApiClient: GoogleApiClient
    //lateinit var mapView: MapView
    var googleMap: GoogleMap? = null
    //lateinit  var locationMarker: Marker
    var zoomLevel: Float = 15f
    var locationManager: LocManager? = null

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleApiClient = GoogleApiClient.Builder(requireActivity())
                                         .addApi(LocationServices.API).build();
        googleApiClient.connect();

        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                permission,
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            //-- permissions are already given
            checkGPSSettings()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding?.mapView!!.onCreate(savedInstanceState)
        _binding?.mapView!!.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(mMap: GoogleMap) {
        googleMap = mMap;
        _binding?.mapView!!.onResume();

        //if (googleMap != null) {
        //     setupMap(28.5022851, 77.0857269)
        //}
    }

    private fun setupMap(mLat: Double, mLong: Double) {
        val mLoc = LatLng(mLat, mLong)

        googleMap!!.clear()
        val options = MarkerOptions()
        options.position(mLoc)
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        googleMap!!.addMarker(options)
        googleMap!!.getUiSettings().setZoomControlsEnabled(true)
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
        googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mLat, mLong), 5f))

        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(mLat, mLong))
            .zoom(zoomLevel)
            .build()
        googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        _binding?.mapView!!.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //---- locations code

    fun checkGPSSettings() {
        if (isGPSEnabled(getContext())) {
            Toast.makeText(activity, "GPS enabled", Toast.LENGTH_SHORT).show()

            locationManager = LocManager.getInstance()
            locationManager!!.connectGoogleClient(requireContext(), this)
        } else {
            Toast.makeText(activity, "GPS Disabled", Toast.LENGTH_SHORT).show()
            displayLocationSettingsRequest(requireContext())
        }
    }

    override fun onLocationReceived(loc: Location?) {
        println("First Lat : " + loc?.getLatitude() + " Long : " + loc?.getLongitude())
        //_binding.txtLatLong!!.setText("Lat : " + loc?.getLatitude() + " Long : " + loc?.getLongitude())
        if (googleMap != null) {
            setupMap(loc?.getLatitude() ?: 0.0, loc?.getLongitude() ?: 0.0 );
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                var permission_denied = false
                var i = 0
                while (i < permissions.size) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        permission_denied = true
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                activity,
                                permissions[i]!!
                            )
                        ) {
                            showAlert()
                        }
                    }
                    i++
                }
                if (!permission_denied) {
                    //-- move on to track location
                    checkGPSSettings()
                }
            }
        }
    }

    private fun showAlert() {
        val alertDialog = AlertDialog.Builder(activity).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("App needs all required permissions to run functionalities !!!")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "Do not Allow",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    Toast.makeText(
                        activity,
                        "App required all permission to work properly",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                    //-- no action required, move on to application
                }
            })
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            "Allow",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                    ActivityCompat.requestPermissions(
                        activity,
                        permission,
                        PERMISSIONS_REQUEST_CODE
                    )
                }
            })
        alertDialog.show()
    }

    fun isGPSEnabled(context: Context?): Boolean {
        val manager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isGPSEnabled
    }

    private fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()

        val locationRequest = LocationRequest.create()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(10000)
        locationRequest.setFastestInterval((10000 / 2).toLong())

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback(object : ResultCallback<LocationSettingsResult?> {
            override fun onResult(result: LocationSettingsResult) {
                val status = result.getStatus()
                when (status.getStatusCode()) {
                    LocationSettingsStatusCodes.SUCCESS -> Log.i(
                        TAG,
                        "All location settings are satisfied."
                    )

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        status.startResolutionForResult(requireActivity(), 8576)
                    } catch (e: SendIntentException) {
                        Log.i(TAG, "PendingIntent unable to execute request.")
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                        TAG,
                        "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                    )
                }
            }
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 8576 && resultCode == RESULT_OK) {
            Toast.makeText(activity, "gps enabled", Toast.LENGTH_SHORT).show()

            locationManager = LocManager.getInstance()
            locationManager!!.connectGoogleClient(requireContext(), this)
        } else if (requestCode == 8576 && resultCode == RESULT_CANCELED) {
            Toast.makeText(activity, "gps disabled", Toast.LENGTH_SHORT).show()
            //finish();
        }
    }

    //-- regularly receive the lat-longs (implementing in next build)

}