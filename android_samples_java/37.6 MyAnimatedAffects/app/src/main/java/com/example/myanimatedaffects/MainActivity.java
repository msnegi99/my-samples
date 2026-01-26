package com.example.myanimatedaffects;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimationDetailFragment fragment = new AnimationDetailFragment();
        getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

}
