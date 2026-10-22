package com.sukriti.sukrititest;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OneFragment extends Fragment {

    AppCompatImageView incrementImgbtn;
    CallbackInterface callbackInterface;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        callbackInterface = (SecondActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        incrementImgbtn = view.findViewById(R.id.incrementImgbtn);
        incrementImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackInterface.increment();
            }
        });

        return view;
    }
}