package com.example.bluetoothsample;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BaseActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission =  new String[]{
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.VIBRATE
    };

    BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    public static ArrayList<String> macs = new ArrayList<String>();
    private Set<String> adds = new HashSet<String>();
    private ArrayList<Device> devices = new ArrayList<Device>();
    private Thread t = null;
    String bluetoothMacAddress = "";
    boolean serviceStarted = false;
    Button stopServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permission, PERMISSIONS_REQUEST_CODE);

        stopServiceBtn = findViewById(R.id.stopServiceBtn);
        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent("STOP_BLUETOOTH_SERVICE"));
            }
        });

        try {
            //-- request whitelist your app
            PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
            String packageName = getPackageName();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent i = new Intent();
                if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
                    i.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    i.setData(Uri.parse("package:" + packageName));
                    startActivity(i);
                }
            }

        } catch (Exception e) {}

        registerReceiver(mBroadcastReceiver1, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter2.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter2.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);

        registerReceiver(mBroadcastReceiver2, filter2);

        //-- will check for bluetooth again and again if it is disabled
        //-- check if device is not supported
        //-- request user to unable the bluetooth
        enableBluetoothDevice();
    }

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch(state) {
                    case BluetoothAdapter.STATE_OFF:
                        exitDialog("Bluetooth not enabled. ");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        enableBluetoothDevice();
                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch(mode){
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        //..discoverable
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        //..not discoverable
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        //..not discoverable, make it discoverable, but do not ask
                        //setBluetoothScanMode(BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
                        break;
                }
            }
        }
    };

    public void enableBluetoothDevice() {

        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (mBluetoothManager != null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
        } else {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        //mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            //Toast.makeText(this,"device not supported",Toast.LENGTH_SHORT).show();
            exitDialog("Bluetooth not supported. ");
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent eintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(eintent, 898);
            } else {
                //-- already enabled

                bluetoothMacAddress = mBluetoothAdapter.getAddress();
                if (bluetoothMacAddress.equalsIgnoreCase("02:00:00:00:00:00")) {
                    bluetoothMacAddress = android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address");
                    if (bluetoothMacAddress == null) bluetoothMacAddress = "02:00:00:00:00:00";
                }

                if (bluetoothMacAddress != null) {
                    try {
                        //saveBluetoothMacAddressToServer(bluetoothMacAddress, userCode);
                    } catch (Exception e) {
                    }
                }

                //-- startup the forground service and pass the mac address and user code to service
                if(!serviceStarted) {
                    Intent intent = new Intent(getApplicationContext(), BluetoothBackgroundService.class);
                    //intent.putExtra("userCode", userCode);
                    intent.putExtra("macAddress", bluetoothMacAddress);
                    startService(intent);

                    serviceStarted = true;
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 898) {
            if (resultCode == RESULT_OK) {

                //-- save bluetooth mac address to server
                //-- get mac address since bluetooth is enabled
                BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                if (mBluetoothManager != null) {
                    mBluetoothAdapter = mBluetoothManager.getAdapter();
                } else {
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                }

                bluetoothMacAddress = mBluetoothAdapter.getAddress();
                if (bluetoothMacAddress.equalsIgnoreCase("02:00:00:00:00:00")) {
                    bluetoothMacAddress = android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address");
                    if (bluetoothMacAddress == null) bluetoothMacAddress = "02:00:00:00:00:00";
                }

                if (bluetoothMacAddress != null) {
                    try {
                        //saveBluetoothMacAddressToServer(bluetoothMacAddress, userCode);
                    } catch (Exception e) {
                    }
                }

                //-- startup the forground service and pass the mac address and user code to service
                if(!serviceStarted) {
                    Intent intent = new Intent(getApplicationContext(), BluetoothBackgroundService.class);
                    //intent.putExtra("userCode", userCode);
                    intent.putExtra("macAddress", bluetoothMacAddress);
                    startService(intent);

                    serviceStarted = true;
                }

            } else {
                exitDialog("Bluetooth not enabled. ");
            }
        }
    }

    public void exitDialog(String str) {
        //-- exit the application
        Toast.makeText(this,"Bluetooth disabled do you want to exit app ?",Toast.LENGTH_SHORT).show();
        enableBluetoothDevice();
    }

    //-- make bluetooth discoverable silently for unlimited time
    private boolean setBluetoothScanMode(int scanMode){

        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        if (mBluetoothManager != null) {

            Method method = null;
            final BluetoothAdapter btAdapter = mBluetoothManager.getAdapter();

            if(!btAdapter.isEnabled()){
                btAdapter.enable();
            }

            try {
                method = btAdapter.getClass().getMethod("setScanMode", int.class);
            } catch (SecurityException e) {
                return false;
            } catch (NoSuchMethodException e) {
                return false;
            }

            try {
                method.invoke(btAdapter, scanMode);
            } catch (IllegalArgumentException e) {
                return false;
            } catch (IllegalAccessException e) {
                return false;
            } catch (InvocationTargetException e) {
                return false;
            }
            return true;
        } else{
            return false;
        }
    }

    private void getPairedDevices() {
        Set<BluetoothDevice> pairedDevice = mBluetoothAdapter.getBondedDevices();
        if(pairedDevice.size()>0)
        {
            for(BluetoothDevice device : pairedDevice)
            {
                //arrayListpaired.add(device.getName()+"\n"+device.getAddress());
                //arrayListPairedBluetoothDevices.add(device);
            }
        }
        //adapter.notifyDataSetChanged();
    }

    private void makeDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Log.i("Log", "Discoverable ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver2);
    }

}
