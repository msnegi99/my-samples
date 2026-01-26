package com.msnegi.locationdemo

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.msnegi.locationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LatLongReceived {

    private val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 101
        private const val TAG = "MainActivity"
    }

    var locationManager: LocManager? = null
    var txtLatLong: TextView? = null
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //txtLatLong = findViewById<TextView>(R.id.txtLatLong)

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                permission,
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            //-- permissions are already given
            checkGPSSettings()
        }
    }

    fun checkGPSSettings() {
        if (isGPSEnabled(this)) {
            Toast.makeText(this, "GPS enabled", Toast.LENGTH_SHORT).show()
            locationManager = LocManager.getInstance()
            locationManager!!.connectGoogleClient(this, this)
        } else {
            Toast.makeText(this, "GPS Disabled", Toast.LENGTH_SHORT).show()
            displayLocationSettingsRequest(this)
        }
    }

    override fun onLocationReceived(loc: Location?) {
        println("Lat : " + loc?.getLatitude() + " Long : " + loc?.getLongitude())
        binding.txtLatLong!!.setText("Lat : " + loc?.getLatitude() + " Long : " + loc?.getLongitude())
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
                                this,
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
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("App needs all required permissions to run functionalities !!!")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "Do not Allow",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    Toast.makeText(
                        this@MainActivity,
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
                        this@MainActivity,
                        permission,
                        PERMISSIONS_REQUEST_CODE
                    )
                }
            })
        alertDialog.show()
    }

    fun isGPSEnabled(context: Context): Boolean {
        val manager = context.getSystemService(LOCATION_SERVICE) as LocationManager
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
                        status.startResolutionForResult(this@MainActivity, 8576)
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
            Toast.makeText(this, "gps enabled", Toast.LENGTH_SHORT).show()

            locationManager = LocManager.getInstance()
            locationManager!!.connectGoogleClient(this, this)
        } else if (requestCode == 8576 && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "gps disabled", Toast.LENGTH_SHORT).show()
            //finish();
        }
    }



}