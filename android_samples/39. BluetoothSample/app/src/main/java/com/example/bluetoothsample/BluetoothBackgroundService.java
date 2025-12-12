package com.example.bluetoothsample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class BluetoothBackgroundService extends Service  {

    final static int NOTIFICATION_ID = 7777;
    String CHANNEL_ID;

    BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    public static ArrayList<String> macs = new ArrayList<String>();
    private Set<String> adds = new HashSet<String>();
    private ArrayList<Device> devices = new ArrayList<Device>();

    Timer timer, timer1, timer2;
    TimerTask timerTask, timerTask1, timerTask2;
    boolean bluetoothStatus = true;
    int bTCallBackTimeInMinutes = 10;

    private class StopReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopForeground(true);
            stopSelf();
        }
    }

    StopReceiver stopReceiver = new StopReceiver();

    public void onCreate() {
        super.onCreate();
        registerReceiver(stopReceiver, new IntentFilter("STOP_BLUETOOTH_SERVICE"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForegroundService();

        registerReceiver(mBroadcastReceiver1, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        //-- search bluetooth devices after every 5 sec
        //-- if bluetooth is on
        timer1 = new Timer();
        timerTask1 = new TimerTask() {
            @Override
            public void run() {
                startDiscoveryDevices();
            }
        };
        timer1.schedule(timerTask1, 10 * 1000, 10 * 1000);
        timerTask1.run();

        return START_STICKY;
    }

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        bluetoothStatus = false;
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                        startActivity( launchIntent );
                        break;
                    case BluetoothAdapter.STATE_ON:
                        bluetoothStatus = true;
                        break;
                }
                System.out.println("bluetoothStatus : " + bluetoothStatus);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        if (stopReceiver != null) {
            unregisterReceiver(stopReceiver);
        }
        if(mBroadcastReceiver1 != null){
            unregisterReceiver(mBroadcastReceiver1);
        }
    }

    public void startDiscoveryDevices() {
        /*To start discover, simply call the startDiscovery() from bluetooth adapter.
        This process is asynchronous so it will return immediately. To catch the discovery process,
        we can register a BroadcastReceiver with ACTION_FOUND,  ACTION_DISCOVERY_STARTED,
        ACTION_DISCOVERY_STARTED. For each device found, the intent will carry extra field
        EXTRA_DEVICE containg the BluetoothDevice object.*/

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);

        try {
            BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            try {
                if (mBluetoothManager != null) {
                    mBluetoothAdapter = mBluetoothManager.getAdapter();
                } else {
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                }

                if (mBluetoothAdapter != null) {
                    mBluetoothAdapter.startDiscovery();
                    System.out.println("Started Discovery");
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("")
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice bdevice = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //Toast.makeText(getApplicationContext(),"Found device " + bdevice.getName(),Toast.LENGTH_SHORT).show();
                //String deviceDetails = "device Name : " + bdevice.getName() + " device Address : " + bdevice.getAddress() + " device Type : " + bdevice.getType() + " device BondState : " + bdevice.getBondState() + " device BluetoothClass : " + bdevice.getBluetoothClass();

                if (bdevice.getBluetoothClass().getDeviceClass() == BluetoothClass.Device.PHONE_SMART)
                {
                    Device d = new Device();
                    d.setDeviceName(bdevice.getName() == null ? "" : bdevice.getName());
                    d.setDeviceMACAddress(bdevice.getAddress());
                    devices.add(d);
                }

                //bdevice.createBond(); // pair your device with this device
            }
        }
    };

    public void startForegroundService() {
        createNotificationChannel();

        Intent startIntent = new Intent(this, BaseActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 101, startIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("BluetoothServiceSample")
                .setContentText("Stay Safe from COVID-19")
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setContentIntent(pi)
                .build();

        this.startForeground(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CHANNEL_ID = getApplicationContext().getPackageName();
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


}
