package com.msnegi.googlemapcontlocupdate

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
import com.msnegi.googlemapcontlocupdate.databinding.FragmentHomeBinding

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Context.LOCATION_SERVICE
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker
import com.msnegi.googlemapcontlocupdate.firstupdate.LatLongReceived
import com.msnegi.googlemapcontlocupdate.firstupdate.LocManager
import com.msnegi.googlemapcontlocupdate.regularupdate.LocData
import com.msnegi.googlemapcontlocupdate.regularupdate.MyLocationService

class HomeFragment : Fragment(), OnMapReadyCallback, LatLongReceived {

    private val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 101
        private const val TAG = "HomeFragment"
    }

    lateinit var googleApiClient: GoogleApiClient
    var googleMap: GoogleMap? = null
    var zoomLevel: Float = 15f
    var locationManager: LocManager? = null

    lateinit var mapView: MapView
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().registerReceiver(getLocationReceiver,
                IntentFilter("get_location"),
                RECEIVER_NOT_EXPORTED)
        } else {
            LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(getLocationReceiver,
                IntentFilter("get_location"))
        }

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

        _binding?.startbtn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (!isServiceRunning) {
                    val intent = Intent(activity, MyLocationService::class.java)
                    requireActivity().startService(intent)

                    _binding?.startbtn!!.setEnabled(false)
                    _binding?.stopbtn!!.setEnabled(true)
                }
            }
        })

        _binding?.stopbtn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent("stop_location_service")
                intent.setPackage("com.msnegi.googlemapcontlocupdate")
                requireActivity().sendBroadcast(intent)

                _binding?.startbtn!!.setEnabled(true)
                _binding?.stopbtn!!.setEnabled(false)
            }
        })

        _binding?.startbtn!!.setEnabled(false)
        _binding?.stopbtn!!.setEnabled(false)

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

        _binding?.startbtn!!.setEnabled(true)
        _binding?.stopbtn!!.setEnabled(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(getLocationReceiver)
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

    public val getLocationReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            try {
                val bundle = intent.getExtras()
                val locData = bundle!!.getSerializable("data") as LocData?

                println("Lat : " + locData!!.latitude + " Long : " + locData.longitude)
                //txtLatLong!!.setText("Lat : " + locData.latitude + " Long : " + locData.longitude)
                markLocation(LatLng(
                    locData.latitude?.toDouble() ?: 0.0,
                    locData.longitude?.toDouble() ?: 0.0
                ),
                    "current",
                    locData.time.toString(),
                    locData.altitude,
                    locData.speed.toString(),
                    locData.accuracy,
                    locData.battery_level);
            } catch (exception: NullPointerException) {
            } catch (e: Exception) {
                println(e.message + "uuuu")
            }
        }
    }

    private val isServiceRunning: Boolean
        get() {
            val manager = requireActivity().getSystemService(ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.Companion.MAX_VALUE)) {
                if ("com.msnegi.googlemapcontlocupdate.MyLocationService" == service.service.getClassName()) {
                    return true
                }
            }
            return false
        }

    private fun markLocation(
        latLng: com.google.android.gms.maps.model.LatLng,
        type: kotlin.String,
        time: kotlin.String,
        altitude: kotlin.String?,
        speed: kotlin.String,
        accuracy: kotlin.String?,
        battery_level: kotlin.String?
    ) {
        //String title = "Time: " + time + "Altitude: " + altitude + " Speed: " + speed + " Accuracy: " + accuracy + " Battery: " + battery_level ;
        val spe = (speed.toDouble() * 3.6)
        val sp = java.lang.Math.round(spe).toFloat()
        val title = "Time: " + time + " Speed: " + sp + " km/h"

        if (googleMap != null) {
            //float zoom = 18f;                     //Default zoom is 14f

            val cameraPosition: CameraPosition = CameraPosition.Builder()
                .target(latLng)
                .zoom(zoomLevel)
                .bearing(0f)
                .tilt(35f)
                .build()

            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            if (type.equals("current", ignoreCase = true)) {
                /*val markerOptions: MarkerOptions = MarkerOptions()
                    .position(latLng)
                    .draggable(false)
                    .title(title)

//                if (locationMarker != null) locationMarker!!.remove()
                var locationMarker = googleMap!!.addMarker(markerOptions)!!*/
                googleMap!!.addMarker(
                    MarkerOptions()
                        .position(
                            com.google.android.gms.maps.model.LatLng(
                                latLng.latitude,
                                latLng.longitude
                            )
                        )
                        .title(title)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.greendot))
                )
                println("test")
            } else if (type.equals("history", ignoreCase = true)) {
                googleMap!!.addMarker(
                    MarkerOptions()
                        .position(
                            com.google.android.gms.maps.model.LatLng(
                                latLng.latitude,
                                latLng.longitude
                            )
                        )
                        .title(title)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.greendot))
                )
            }
        }
    }


}