package com.msnegi.boundserviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BoundService extends Service
{
    private final IBinder myBinder = new MyLocalBinder();

    public class MyLocalBinder extends Binder
    {
        BoundService getService()
        {
            return BoundService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return myBinder;
    }

    public int calculateSum(int num1, int num2)
    {
        return (num1+num2);
    }
}
