package com.msnegi.callbackexamples;

import android.content.Intent;
import android.content.IntentFilter;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CallBackInterface {

    Button btn1,btn2,btn3;
    MessageReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MessageReceiver();
        receiver.setContext(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("please_call_back"));

        btn1 = (Button) findViewById(R.id.btnBroadcast);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent("please_call_back"));
            }
        });

        btn2 = (Button) findViewById(R.id.btnActivity);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CallBackActivity.class));
            }
        });

        btn3 = (Button) findViewById(R.id.btnFragment);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                CallbackFragment newFragment = new CallbackFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.llFragmentContainer, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void callbackfunction(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
