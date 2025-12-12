package com.msnegi.recyclerviewpagewise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.msnegi.recyclerviewpagewise.interfaces.CallBackInterface;

public class MainActivity extends AppCompatActivity implements CallBackInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportFragmentManager().beginTransaction().add(R.id.container, new PagingListFragment(), "StateTrackingFragment").commit();
    }

    public void setTitle(String title) {}
    public void onFragmentRemoved(){}
}