package com.msnegi.permissionsdemo

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.msnegi.permissionsdemo.databinding.ActivityMainBinding
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.common.api.GoogleApiClient

import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity() {

    private val permission = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CALL_PHONE
    )

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        enableEdgeToEdge()

        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkGPSSettings()
        ActivityCompat.requestPermissions(this, permission, PERMISSIONS_REQUEST_CODE)

        /*if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE);
        }else{
            //-- call activity
            Toast.makeText(this, "permissions given", Toast.LENGTH_SHORT).show();
        }*/

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                var i = 0
                while (i < permissions.size) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[i])) {
                            showAlert()
                        }
                    }
                    i++
                }
            }
        }
    }

    private fun errorMessage(str: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_message_box)
        val txtMessage = dialog.findViewById<TextView>(R.id.txtMessage)
        txtMessage.text = str
        val okBtn = dialog.findViewById<View>(R.id.okBtn) as Button
        okBtn.setOnClickListener {
            dialog.dismiss()
            ActivityCompat.requestPermissions(this,permission,PERMISSIONS_REQUEST_CODE)
        }
        dialog.show()
    }

    private fun showAlert() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("App needs all required permissions to run functionalities !!!")
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Do not Allow")
        { dialog, which ->
            errorMessage("App required all permission to work properly")
            dialog.dismiss()
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow")
        { dialog, which ->
            dialog.dismiss()
            ActivityCompat.requestPermissions(this,permission,PERMISSIONS_REQUEST_CODE)
        }

        alertDialog.show()
    }

    fun checkGPSSettings() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,permission,PERMISSIONS_REQUEST_CODE)
        }

        if (isGPSEnabled(this)) {
            Toast.makeText(this, "GPS enabled", Toast.LENGTH_SHORT).show();
            //val intent = Intent(this, MyLocationService::class.java)
            //startService(intent)
        } else {
            Toast.makeText(this, "GPS Disabled", Toast.LENGTH_SHORT).show();
            displayLocationSettingsRequest(this)
        }
    }

    fun isGPSEnabled(context: Context): Boolean {
        val manager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context).addApi(LocationServices.API).build()
        googleApiClient.connect()
        val locationRequest = com.google.android.gms.location.LocationRequest.create()
        locationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(10000)
        locationRequest.setFastestInterval((10000 / 2).toLong())
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())

        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(this, 8576)
                } catch (e: IntentSender.SendIntentException) {
                    errorMessage("Unable to execute location request.")
                }
            }
        }
    }




}