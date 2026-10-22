package com.sukriti.sukrititest;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThreeFragment extends Fragment {

    AppCompatImageView decrementImgbtn;
    CallbackInterface callbackInterface;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        callbackInterface = (SecondActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        decrementImgbtn = view.findViewById(R.id.decrementImgbtn);
        decrementImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackInterface.decrement();
            }
        });

        return view;
    }
}

