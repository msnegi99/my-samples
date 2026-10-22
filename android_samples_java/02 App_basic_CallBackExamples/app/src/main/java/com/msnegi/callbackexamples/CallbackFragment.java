package com.msnegi.callbackexamples;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CallbackFragment extends Fragment {

    CallBackInterface callBackInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callBackInterface = (MainActivity) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnListItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_callback, container, false);

        Button btnCallback = (Button) view.findViewById(R.id.btnCallback);
        btnCallback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callBackInterface.callbackfunction("This is a callback from Fragment !!!");
            }
        });

        return view;

    }

}
