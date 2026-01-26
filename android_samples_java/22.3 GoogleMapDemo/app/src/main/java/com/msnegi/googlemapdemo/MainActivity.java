package com.msnegi.googlemapdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new HomeFragment(), new Bundle(), "HomeFragment");
    }

    public void replaceFragment(Fragment fragment, Bundle bundle, String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if (fragment instanceof HomeFragment) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {
        exitMessage();
    }

    private void exitMessage()
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit_app);

        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
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