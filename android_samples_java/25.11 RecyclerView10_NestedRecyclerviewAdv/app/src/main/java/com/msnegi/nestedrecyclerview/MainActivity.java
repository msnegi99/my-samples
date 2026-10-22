package com.msnegi.nestedrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportFragmentManager().beginTransaction().add(R.id.container, new RecyclerViewNestedFragment(), "basicViewsFragment").commit();
        
    }
    
}