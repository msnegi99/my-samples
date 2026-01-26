package com.msnegi.permissionsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission =  new String[]{Manifest.permission.CAMERA,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permission, PERMISSIONS_REQUEST_CODE);

        /*if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE);
        }else{
            //-- call activity
            Toast.makeText(this, "permissions given", Toast.LENGTH_SHORT).show();
        }*/
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
                            Toast.makeText(this,"Move on to Application",Toast.LENGTH_SHORT).show();
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