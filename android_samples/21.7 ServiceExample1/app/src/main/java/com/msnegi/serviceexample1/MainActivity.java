package com.msnegi.serviceexample1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, new IntentFilter("com.msnegi.BOX_DROPPED"));

        BackResults backresult = new BackResults();
        backresult.id = 1;
        backresult.name = "mahender";

        i = new Intent(getApplicationContext(), BoxDropService.class);
       // i.putExtra("backresult",backresult);

    }

    public void startServiceClick(View v)
    {
        startService(i);
    }

    public void stopServiceClick(View v)
    {
        stopService(i);
    }

    public void boxDropped(View v)
    {
       // Intent intent = new Intent();
       //  intent.setAction("com.msnegi.BOX_DROPPED");
       // sendBroadcast(intent);
    }

}
