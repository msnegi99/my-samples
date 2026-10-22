package com.msnegi.boundserviceexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText num1;
    EditText num2;
    EditText sum;
    Button calculate;

    private BoundService serviceReference;
    private boolean isBound = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, BoundService.class);
        startService(intent);

        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        sum = (EditText) findViewById(R.id.sum);
        calculate = (Button) findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBound)
                {
                    int n1 = Integer.parseInt(num1.getText().toString());
                    int n2 = Integer.parseInt(num2.getText().toString());
                    int vsum = serviceReference.calculateSum(n1,n2);
                    sum.setText(String.valueOf(vsum));
                }
            }
        });
    }

    private ServiceConnection myConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            serviceReference = ((BoundService.MyLocalBinder) service).getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            serviceReference = null;
            isBound = false;
        }
    };

    @Override
    protected void onStart()
    {
        super.onStart();
        if (!isBound)
        {
            bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unbindService(myConnection);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (isFinishing())
        {
            stopService(intent);
        }
    }
}
