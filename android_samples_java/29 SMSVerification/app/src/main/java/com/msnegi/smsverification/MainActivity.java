package com.msnegi.smsverification;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsMessage;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission =  new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS};
    private static final int REQUEST_READ_SMS = 20;

    TextView user;
    TextView mobile;
    TextView otp;
    Button loginBtn;
    Button verifyOtpBtn;

    String verificationCode ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(mSmsReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, permission, PERMISSIONS_REQUEST_CODE);
        }else{
            //-- call activity
            Toast.makeText(this, "permissions given", Toast.LENGTH_SHORT).show();
            startMain();
        }
    }

    public void startMain(){
        user = (TextView) findViewById(R.id.empCode);
        mobile = (TextView) findViewById(R.id.mobileNo);
        otp = (TextView) findViewById(R.id.otpCode);

        loginBtn = (Button) findViewById(R.id.submitInfologin);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+919990033445", null, "Your OTP is 2342 for registration of Mobile Application, Please do not share this Password with anyone , This OTP will expire in 5 minutes", null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            }
        });

        verifyOtpBtn = (Button) findViewById(R.id.verifyOtp);
        verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private final BroadcastReceiver mSmsReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            final Bundle bundle = intent.getExtras();

            try
            {
                if (bundle != null)
                {
                    Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (Object aPdusObj : pdusObj)
                    {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                        String senderAddress = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();

                        // if the SMS is not from our gateway, ignore the message
                        if (senderAddress.toLowerCase().contains("+918299189567") || senderAddress.toLowerCase().contains("+919990033445"))
                        {
                            // verification code from sms
                            verificationCode = getVerificationCode(message);
                            otp.setText(verificationCode);

                            LinearLayout otpSection = (LinearLayout) findViewById(R.id.otpSection);
                            otpSection.setVisibility(View.VISIBLE);
                            loginBtn.setVisibility(View.GONE);
                        }
                    }
                }
            }
            catch (Exception e) {
                Log.e("SmsReceiver", "Exception: " + e.getMessage());
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSmsReceiver);
    }

    private String getVerificationCode(String message)
    {
        //Your OTP is 2342 for registration of Mobile Application, Please do not share this Password with anyone , This OTP will expire in 5 minutes
        String code = "";
        String messageArr[] = message.split(" ");
        code = messageArr[3];
        return code;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(alertDialog != null) alertDialog.dismiss();

        switch (requestCode)
        {
            case PERMISSIONS_REQUEST_CODE: {
                for (int i = 0, len = permissions.length; i < len; i++)
                {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                            showAlert();
                        } else {
                            //Toast.makeText(this,"Move on to Application",Toast.LENGTH_SHORT).show();
                            startMain();
                        }
                    }
                }
            }
        }
    }

    AlertDialog alertDialog = null;

    private void showAlert()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs all permissions to run all functionalities !!!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Do not Allow",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                        Toast.makeText(MainActivity.this,"App required the permissions to work properly",Toast.LENGTH_SHORT).show();
                        //-- move on to application
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
                    }
                });
        alertDialog.show();
    }
}
