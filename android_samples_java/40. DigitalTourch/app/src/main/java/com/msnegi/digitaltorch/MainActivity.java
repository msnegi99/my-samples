package com.msnegi.digitaltorch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity
{
    ImageButton btnSwitch;
    private CameraManager mCameraManager;
    private String mCameraId;
    private boolean isFlashOn = true;

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private String[] permission = new String[]{Manifest.permission.CAMERA};

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-- no permission required for this only phone should support hardware
        //ActivityCompat.requestPermissions(this, permission, PERMISSIONS_REQUEST_CODE);

        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                                        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {
            showNoFlashError();
        }

        //getting the camera manager and camera id
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        btnSwitch = (ImageButton) findViewById(R.id.btnSwitch);
        btnSwitch.setImageResource(R.drawable.off_button);
        btnSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchFlashLight(isFlashOn);
                toggleButtonImage();
                isFlashOn = !isFlashOn;
                playSound();
            }
        });
    }

    @SuppressLint("NewApi")
    public void switchFlashLight(boolean status) {
        try {
            mCameraManager.setTorchMode(mCameraId,status);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void playSound()
    {
        MediaPlayer mplayer;

        if (isFlashOn)
        {
            mplayer = MediaPlayer.create(MainActivity.this, R.raw.switch_off_sound);
        }
        else
        {
            mplayer = MediaPlayer.create(MainActivity.this, R.raw.switch_on_sound);
        }

        mplayer.setOnCompletionListener(new OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mplayer)
            {
                mplayer.release();
            }
        });
        mplayer.start();
    }

    private void toggleButtonImage()
    {
        if (isFlashOn)
        {
            btnSwitch.setImageResource(R.drawable.on_button);
        }
        else
        {
            btnSwitch.setImageResource(R.drawable.off_button);
        }
    }

    public void showNoFlashError() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Oops!");
        alert.setMessage("Flash not available in this device...");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }

    //-----------permission

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSIONS_REQUEST_CODE: {
                for (int i = 0, len = permissions.length; i < len; i++)
                {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                            showAlert();
                        } else {
                            Toast.makeText(MainActivity.this,"Move on to Application",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

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
                        Toast.makeText(MainActivity.this,"App required the permissions to work properly", Toast.LENGTH_SHORT).show();
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
