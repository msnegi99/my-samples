package com.msnegi.searchontoolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CallBackInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportFragmentManager().beginTransaction().add(R.id.container, new ToolbarMenuFragment(), "ToolbarMenuFragment").commit();
    }

    public void setTitle(String title) {}
    public void onFragmentRemoved(){}
}