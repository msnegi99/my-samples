package com.sukriti.sukrititest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SecondActivity extends AppCompatActivity implements CallbackInterface {

    LinearLayout firstcontainer, secondcontainer, thirdcontainer;
    OneFragment oneFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firstcontainer = findViewById(R.id.firstcontainer);
        secondcontainer = findViewById(R.id.secondcontainer);
        thirdcontainer = findViewById(R.id.thirdcontainer);

        Bundle bundle = new Bundle();
        bundle.putInt("initvalue", Integer.parseInt(getIntent().getStringExtra("initvalue")));

        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();

        replaceFragment(oneFragment, firstcontainer, null, "OneFragment");
        replaceFragment(twoFragment, secondcontainer, bundle, "TwoFragment");
        replaceFragment(threeFragment, thirdcontainer, null, "ThreeFragment");
    }

    public void replaceFragment(Fragment fragment, LinearLayout ll, Bundle bundle, String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(ll.getId(), fragment, tag).commit();
    }

    @Override
    public void increment() {
        twoFragment.incrementNumber();
    }

    @Override
    public void decrement() {
        twoFragment.decrementNumber();
    }

}