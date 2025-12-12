package com.msnegi.websearchretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ItemOnClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportFragmentManager().beginTransaction().add(R.id.container, new WebSearchRetrofitFragment(), "WebSearchRetrofitFragment").commit();
    }

    @Override
    public void OnItemClick(Object obj, int pos, int type) {

    }
}