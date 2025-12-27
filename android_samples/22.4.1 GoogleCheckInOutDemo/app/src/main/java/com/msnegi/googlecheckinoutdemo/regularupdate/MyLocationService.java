package com.msnegi.googlecheckinoutdemo.regularupdate;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.msnegi.googlecheckinoutdemo.HomeActivity;

import com.msnegi.googlecheckinoutdemo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyLocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{
    private static final String TAG = MyLocationService.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;

    private final int UPDATE_INTERVAL = 3000;
    private final int FASTEST_INTERVAL = 1000;
    final static int NOTIFICATION_ID = 9990;

    int batteryLevel = 0;
    boolean firstUpdate = true;

    @Override
    public void onCreate() {
        super.onCreate();

        registerReceiver(stopLocationService, new IntentFilter("stop_location_service"));
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            googleApiClient.connect();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForegroundService();
        return START_STICKY;
    }

    /*-- Restart this service if killed by memory cleaner -- seems working only once --*/
    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceTask = new Intent(getApplicationContext(),this.getClass());
        restartServiceTask.setPackage(getPackageName());
        //PendingIntent restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT);

        PendingIntent restartPendingIntent=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            restartPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, restartServiceTask,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }else {
            restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        }

        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        myAlarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartPendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    public void startForegroundService(){

        String NOTIFICATION_CHANNEL_ID = getPackageName();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableLights(true);
            channel.setLightColor(Color.GRAY);
            manager.createNotificationChannel(channel);
        }

        Intent startIntent = new Intent(this, HomeActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(), startIntent, PendingIntent.FLAG_ONE_SHOT);

        PendingIntent pendingIntent=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(this, 0, startIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }else {
            pendingIntent = PendingIntent.getBroadcast(this, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("MapMyWay Running")
                .setContentText("")
                /*.setContentIntent(pendingIntent)*/
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);

        Notification notification = builder.setOngoing(true)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        startForeground(NOTIFICATION_ID, notification);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(stopLocationService);
        unregisterReceiver(mBatInfoReceiver);
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLastKnownLocation();
    }

    private void getLastKnownLocation()
    {
        try
        {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            // setInterval -- Set the desired interval for active location updates, in milliseconds.
            // setFastestInterval --  Explicitly set the fastest interval for location updates, in milliseconds.
            //                    --  If another app is using the location services with a higher rate of updates,
            //                    --  you will get more location updates from that application. Keep it lower than setInterval.
            //                    --  most battery saving.
            // setSmallestDisplacement -- Set the minimum displacement between location updates in meters. By default this is 0.
            locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                    .setInterval(UPDATE_INTERVAL)
                    .setFastestInterval(FASTEST_INTERVAL)
                    .setSmallestDisplacement(10);

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }catch(SecurityException se){}
    }

    private String currentTime, lastTime = "";
    private float totalDistance = 0;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onLocationChanged(Location location)
    {
        if (lastLocation == null) {
            lastLocation = location;
            lastTime = "";
        }

        currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date()).toString();
        float timeDifference = 0;
        float cal_speed = 0;

        if (lastLocation != null)
        {
            totalDistance = Float.valueOf(String.valueOf(location.distanceTo(lastLocation) / 1000));

            long objectCreatedDate = 0;
            long currentDate = 0;

            try {
                if(!lastTime.equalsIgnoreCase("")) {
                    objectCreatedDate = format.parse(lastTime).getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                currentDate = format.parse(currentTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            timeDifference = (currentDate - objectCreatedDate) / 1000;

            cal_speed = totalDistance/timeDifference;
        }

        if (location == null || location.getAccuracy() > 10)
        {
             return;
        }

        LocData data = new LocData();

        String vdate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date()).toString();
        String vtime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date()).toString();

        data.setDate(vdate);
        data.setTime(vtime);

        data.setTimeGap(String.valueOf(timeDifference));
        data.setLatitude(String.valueOf(location.getLatitude()));
        data.setLongitude(String.valueOf(location.getLongitude()));
        data.setAltitude(String.valueOf(location.getAltitude()));              // meters
        data.setSpeed(String.valueOf(location.getSpeed()));                    // meter/second
        data.setCalSpeed(String.valueOf(cal_speed));                            // meters
        data.setDistance(String.valueOf(totalDistance));
        data.setBattery_level(String.valueOf(batteryLevel));                   // percentage
        data.setAccuracy(String.valueOf(location.getAccuracy()));              // meters with 68% confidence in this radius

        lastLocation = location;
        lastTime = currentTime;

        //db.addLocation(data);

        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data);

        Intent intent = new Intent("get_location");
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()");
    }

    //The BatteryManager broadcasts all battery and charging details in a sticky Intent that includes the charging status.
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent)
        {
            batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        }
    };

    private BroadcastReceiver stopLocationService = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context ctxt, Intent intent)
        {
            stopForeground(true);
            stopSelf();
        }
    };


}
