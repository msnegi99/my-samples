package com.msnegi.locationdemo

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.text.DateFormat
import java.util.Date

class LocManager : LocationListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    var mGoogleApiClient: GoogleApiClient? = null
    private var mContext: Context? = null
    var mLocationRequest: LocationRequest? = null
    var mCurrentLocation: Location? = null
    var mLastUpdateTime: String = ""
    var latlongRec: LatLongReceived? = null

    fun connectGoogleClient(context: Context, latlongRec: LatLongReceived) {
        this.mContext = context
        this.latlongRec = latlongRec
        if (isGooglePlayServiceAvailable(context)) {
            buildGoogleApiClient(context)
        }
    }

    @Synchronized
    private fun buildGoogleApiClient(context: Context) {
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.setInterval(INTERVAL)
        mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        mGoogleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        mGoogleApiClient!!.connect()
    }

    override fun onLocationChanged(location: Location) {
        Log.d(TAG, "onLocationChanged() returned: " + location)
        mCurrentLocation = location
        mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
        //updateUI();
        latlongRec!!.onLocationReceived(location)

        stopLocationUpdates() //-- stop if needs gps only once
        disconnectGoogleClient()
    }

    override fun onConnected(bundle: Bundle?) {
        startLocationUpdates()
    }

    @Throws(SecurityException::class)
    private fun startLocationUpdates() {
        if (mGoogleApiClient!!.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient!!,
                mLocationRequest!!,
                this
            )
        }
    }

    fun disconnectGoogleClient() {
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient!!, this)
            mGoogleApiClient!!.disconnect()
        }
    }

    override fun onConnectionSuspended(i: Int) {
        Log.d(TAG, "onConnectionSuspended() returned: ")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed() returned: ")
    }

    protected fun stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient!!, this)
        mGoogleApiClient!!.disconnect()
        Log.e(TAG, "Location update stopped .......................")
    }

    companion object {
        private val TAG: String = LocManager::class.java.simpleName

        @Volatile
        private var instance: LocManager? = null

        fun getInstance(): LocManager {
            return instance ?: synchronized(this) {
                instance ?: LocManager().also { instance = it }
            }
        }


        const val PLAY_SERVICES_RESOLUTION_REQUEST: Int = 9000
        private val INTERVAL = (1000 * 10).toLong()
        private val FASTEST_INTERVAL = (1000 * 5 //benificial in case of services only
                ).toLong()

        fun isGooglePlayServiceAvailable(context: Context): Boolean {
            return checkPlayServices(context)
        }

        fun checkPlayServices(context: Context): Boolean {
            val gApi = GoogleApiAvailability.getInstance()
            val resultCode = gApi.isGooglePlayServicesAvailable(context)
            if (resultCode != ConnectionResult.SUCCESS) {
                if (gApi.isUserResolvableError(resultCode)) {
                    gApi.getErrorDialog(
                        context as android.app.Activity,
                        resultCode,
                        LocManager.Companion.PLAY_SERVICES_RESOLUTION_REQUEST
                    )!!.show()
                } else {
                    Toast.makeText(context, "Play Services not available", Toast.LENGTH_SHORT)
                        .show()
                }
                return false
            }
            return true
        }
    }
}
