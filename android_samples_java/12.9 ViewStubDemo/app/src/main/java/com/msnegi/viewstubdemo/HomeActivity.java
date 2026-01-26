package com.msnegi.viewstubdemo;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements CallBackInterface {

    Toolbar toolbar;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home Activity");
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);
//        ab.setHomeAsUpIndicator(R.drawable.ic_baseline);

        replaceFragment(new HomeFragment(), new Bundle(), "HomeFragment");
    }

    @Override
    public void loadFragment(Fragment fragment, Bundle bundle, String tag) {
        replaceFragment(fragment, bundle, tag);
    }

    public void replaceFragment(Fragment fragment, Bundle bundle, String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if (fragment instanceof HomeFragment) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(null).commit();
            onFragmentLoaded();   //-- add back button in place of hamburger icon
        }
    }

    public void onFragmentLoaded(){
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
   }

    @Override
    public void onFragmentRemoved() {
        ab.setHomeButtonEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(null);
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count>0){
            getSupportFragmentManager().popBackStackImmediate();
            int count1 = getSupportFragmentManager().getBackStackEntryCount();
            if(count1 == 0){
                onFragmentRemoved();
            }
        }else{
            exitMessage();
        }
    }

    public boolean onSupportNavigateUp(){
        getSupportFragmentManager().popBackStackImmediate();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count == 0){
            onFragmentRemoved();
        }
        return true;
    }

    private int doubleBackPressedToExit = 0;

    private void backPressedToExit(){
        doubleBackPressedToExit++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressedToExit = 0;
            }
        },3000);

        if(doubleBackPressedToExit == 2){
            finish();
        }else{
            Toast.makeText(this,"Press again to exit App",Toast.LENGTH_SHORT).show();
        }
    }

    private void exitMessage()
    {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit_app);

        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                /*if(!session.isRated().equalsIgnoreCase("true")){
                    rateAppDialog();
                }else{
                    finish();
                }*/
                //rateAppDialog();
                //dialog.dismiss();
                finish();
            }
        });

        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
