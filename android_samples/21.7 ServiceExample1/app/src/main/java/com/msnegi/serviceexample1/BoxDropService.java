package com.msnegi.serviceexample1;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class BoxDropService extends Service
{
    MyBroadcastReceiver receiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // The service is starting, due to a call to startService()
        //receiver = new MyBroadcastReceiver();
       // registerReceiver(receiver, new IntentFilter("com.msnegi.BOX_DROPPED"));

        Intent i = new Intent();
        i.setAction("com.msnegi.BOX_DROPPED");
        sendBroadcast(i);

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        // The service is no longer used and is being destroyed
        //unregisterReceiver(receiver);

        //if the service is tarted more than once, calling stop service will result in
        //"missing a call to unregistereReceiver()" error.
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
