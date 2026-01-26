package com.msnegi.formdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {

    Button formDemoBtn, dyncFormDemoBtn;
    CallBackInterface callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        Bundle bundle = getArguments();
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Form Example");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        formDemoBtn = view.findViewById(R.id.formDemoBtn);
        formDemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new FormFragment(), new Bundle(),"FormFragment");
            }
        });

        dyncFormDemoBtn = view.findViewById(R.id.dyncFormDemoBtn);
        dyncFormDemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new DynamicFormFragment(), new Bundle(),"DynamicFormFragment");
            }
        });

        return view;
    }
}
