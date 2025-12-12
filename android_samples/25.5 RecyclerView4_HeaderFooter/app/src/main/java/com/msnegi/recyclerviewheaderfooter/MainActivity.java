package com.msnegi.recyclerviewheaderfooter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null)
        {
            if (savedInstanceState != null)
            {
                return;
            }

            RecyclerViewFragment frtFragment = new RecyclerViewFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_container,frtFragment).commit();
        }
    }
}
