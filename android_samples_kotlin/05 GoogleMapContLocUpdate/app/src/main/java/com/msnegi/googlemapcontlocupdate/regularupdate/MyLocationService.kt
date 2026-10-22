package com.msnegi.googlemapcontlocupdate.regularupdate

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.location.Location
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.msnegi.googlemapcontlocupdate.MainActivity
import com.msnegi.googlemapcontlocupdate.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyLocationService : Service(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private var googleApiClient: GoogleApiClient? = null
    private var lastLocation: Location? = null
    private var locationRequest: LocationRequest? = null

    private val UPDATE_INTERVAL = 1000
    private val FASTEST_INTERVAL = 1000
    var batteryLevel: Int = 0

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(stopLocationService,
                IntentFilter("stop_location_service"),
                RECEIVER_NOT_EXPORTED)
        } else {
            LocalBroadcastManager.getInstance(this).registerReceiver(stopLocationService,
                IntentFilter("stop_location_service"))
        }

        registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

            googleApiClient!!.connect()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        return START_STICKY
    }

    /*-- Restart this service if killed by memory cleaner -- seems working only once --*/
    override fun onTaskRemoved(rootIntent: Intent?) {
        val restartServiceTask = Intent(getApplicationContext(), this.javaClass)
        restartServiceTask.setPackage(getPackageName())

        //PendingIntent restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        var restartPendingIntent: PendingIntent? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            restartPendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1, restartServiceTask,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            restartPendingIntent = PendingIntent.getService(
                getApplicationContext(),
                1,
                restartServiceTask,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val myAlarmService = getApplicationContext().getSystemService(ALARM_SERVICE) as AlarmManager
        myAlarmService.set(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 1000,
            restartPendingIntent
        )

        super.onTaskRemoved(rootIntent)
    }

    fun startForegroundService() {
        val NOTIFICATION_CHANNEL_ID = getPackageName()
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableLights(true)
            channel.setLightColor(Color.GRAY)
            manager.createNotificationChannel(channel)
        }

        val startIntent = Intent(this, MainActivity::class.java)
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        //PendingIntent pendingIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(), startIntent, PendingIntent.FLAG_ONE_SHOT);
        var pendingIntent: PendingIntent? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(
                this, 0, startIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            pendingIntent = PendingIntent.getBroadcast(this, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }


        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("MyLocationService Running")
            .setContentText("") /*.setContentIntent(pendingIntent)*/
            .setWhen(System.currentTimeMillis())
            .setOngoing(true)

        val notification = builder.setOngoing(true)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        unregisterReceiver(stopLocationService)
        unregisterReceiver(mBatInfoReceiver)
        googleApiClient!!.disconnect()
    }

    override fun onConnected(bundle: Bundle?) {
        this.lastKnownLocation
    }

    private val lastKnownLocation: Unit
        get() {
            try {
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient!!)
                if (lastLocation != null) {
                    startLocationUpdates()
                } else {
                    startLocationUpdates()
                }
            } catch (se: SecurityException) {
            }
        }

    private fun startLocationUpdates() {
        try {
            // setInterval -- Set the desired interval for active location updates, in milliseconds.
            // setFastestInterval --  Explicitly set the fastest interval for location updates, in milliseconds.
            //                    --  If another app is using the location services with a higher rate of updates,
            //                    --  you will get more location updates from that application. Keep it lower than setInterval.
            //                    --  most battery saving.
            // setSmallestDisplacement -- Set the minimum displacement between location updates in meters. By default this is 0.
            locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(UPDATE_INTERVAL.toLong())
                .setFastestInterval(FASTEST_INTERVAL.toLong())
            //.setSmallestDisplacement(10);

            LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient!!,
                locationRequest!!,
                this
            )
        } catch (se: SecurityException) {
        }
    }

    private var currentTime: String? = null
    private var lastTime = ""
    private var totalDistance = 0f
    var format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    override fun onLocationChanged(location: Location) {
        if (lastLocation == null) {
            lastLocation = location
            lastTime = ""
        }

        currentTime =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Date()).toString()
        var timeDifference = 0f
        var cal_speed = 0f

        if (lastLocation != null) {
            totalDistance = (location!!.distanceTo(lastLocation!!) / 1000).toString().toFloat()

            var objectCreatedDate: Long = 0
            var currentDate: Long = 0

            try {
                if (!lastTime.equals("", ignoreCase = true)) {
                    objectCreatedDate = format.parse(lastTime).getTime()
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            try {
                currentDate = format.parse(currentTime).getTime()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            timeDifference = ((currentDate - objectCreatedDate) / 1000).toFloat()

            cal_speed = totalDistance / timeDifference
        }

        if (location == null /*|| location.getAccuracy() > 10*/) {
            return
        }

        val data = LocData()

        val vdate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()).toString()
        val vtime = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(Date()).toString()

        data.date = vdate
        data.time = vtime

        data.timeGap = timeDifference.toString()
        data.latitude = location.getLatitude().toString()
        data.longitude = location.getLongitude().toString()
        data.altitude = location.getAltitude().toString() // meters
        data.speed = location.getSpeed().toString() // meter/second
        data.calSpeed = cal_speed.toString() // meters
        data.distance = totalDistance.toString()
        data.battery_level = batteryLevel.toString() // percentage
        data.accuracy =
            location.getAccuracy().toString() // meters with 68% confidence in this radius

        lastLocation = location
        lastTime = currentTime!!

        //db.addLocation(data);
        val bundle = Bundle()
        bundle.putSerializable("data", data)

        val intent = Intent("get_location")
        intent.putExtras(bundle)
        intent.setPackage("com.msnegi.googlemapcontlocupdate")
        sendBroadcast(intent)
    }

    override fun onConnectionSuspended(i: Int) {
        Log.w(TAG, "onConnectionSuspended()")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.w(TAG, "onConnectionFailed()")
    }

    //The BatteryManager broadcasts all battery and charging details in a sticky Intent that includes the charging status.
    private val mBatInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context?, intent: Intent) {
            batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        }
    }
    
    private val stopLocationService: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context?, intent: Intent?) {
            stopService()
        }
    }

    private fun stopService(){
        stopForeground(true)
        stopSelf()
    }

    companion object {
        private val TAG: String = MyLocationService::class.java.getSimpleName()
        const val NOTIFICATION_ID: Int = 9990
    }
}
