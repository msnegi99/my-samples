package com.msnegi.animationscene;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                BasicTransitionFragment fragment = new BasicTransitionFragment();
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                CustomTransitionFragment fragment = new CustomTransitionFragment();
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InterpolatorFragment fragment = new InterpolatorFragment();
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                RevealEffectBasicFragment fragment = new RevealEffectBasicFragment();
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
