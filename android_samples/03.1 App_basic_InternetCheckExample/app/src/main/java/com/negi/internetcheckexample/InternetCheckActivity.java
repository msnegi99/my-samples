package com.negi.internetcheckexample;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.net.NetworkInfo;

public class InternetCheckActivity extends AppCompatActivity {

    InternetStatusReceiver internetStatusReceiver = new InternetStatusReceiver();
    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = (TextView) findViewById(R.id.txtStatus);
        registerReceiver(internetStatusReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(internetStatusReceiver);
    }

    class InternetStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            if(status.isEmpty()){
                status="Internet Not Available";
            }
            txtStatus.setText(status);
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        }

        public String getConnectivityStatusString(Context context) {
            String status = "";
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    status = "Wifi Enabled";
                    return status;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    status = "Mobile Data Enabled";
                    return status;
                }
            }else{
                status = "Internet Not Available";
                return status;
            }
            return status;
        }
    }
}
