package com.sukriti.sukrititest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TwoFragment extends Fragment {

    AppCompatEditText resultvaluetxt;
    AppCompatButton submitbtn;

    CallbackInterface callbackInterface;
    int n;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        callbackInterface = (SecondActivity) activity;
        n = getArguments().getInt("initvalue");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        resultvaluetxt = view.findViewById(R.id.resultvaluetxt);
        submitbtn = view.findViewById(R.id.submitbtn);

        resultvaluetxt.setText(String.valueOf(n));
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("n",n);
                getActivity().setResult(1004, intent);
                getActivity().finish();
            }
        });

        return view;
    }

    public void incrementNumber(){
        if(n < 100) {
            n++;
            resultvaluetxt.setText(String.valueOf(n));
        }
    }

    public void decrementNumber(){
        if(n > 0) {
            n--;
            resultvaluetxt.setText(String.valueOf(n));
        }
    }
}


