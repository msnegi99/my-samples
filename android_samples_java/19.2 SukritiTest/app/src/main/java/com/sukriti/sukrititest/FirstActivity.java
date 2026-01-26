package com.sukriti.sukrititest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    AppCompatEditText initvaluetxt;
    AppCompatButton startbtn;
    int m = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initvaluetxt = findViewById(R.id.initvaluetxt);
        startbtn = findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(initvaluetxt.getText().toString().trim());
                if(n >= 0 && n <= 100) {
                    Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                    intent.putExtra("initvalue", initvaluetxt.getText().toString().trim());
                    startActivityForResult(intent, 1003);
                }else{
                    showDialog(getResources().getString(R.string.allowd_range));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1003)
        {
            if(resultCode == 1004)
            {
                m = data.getIntExtra("n",-1);

                initvaluetxt.setText(String.valueOf(m));

                if (m < 40){
                    showDialog(getResources().getString(R.string.failded));
                }else if(m < 70) {
                    showDialog(getResources().getString(R.string.average));
                }else if (m <90){
                    showDialog(getResources().getString(R.string.good));
                }else {
                    showDialog(getResources().getString(R.string.excellent));
                }
            }
        }
    }

    public void showDialog(String msg){
        new AlertDialog.Builder(FirstActivity.this)
                .setMessage(msg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }

}