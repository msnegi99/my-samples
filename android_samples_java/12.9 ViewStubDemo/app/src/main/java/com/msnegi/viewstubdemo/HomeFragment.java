package com.msnegi.viewstubdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.msnegi.viewstubdemo.webservice.Webservice;
import com.msnegi.viewstubdemo.webservice.WebserviceCallback;

import org.json.JSONException;
import org.json.JSONObject;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    Button viewStubBtn;
    CallBackInterface callback;
    View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        Bundle bundle = getArguments();
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Tabs Example");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        viewStubBtn = rootView.findViewById(R.id.viewStubBtn);
        viewStubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new ViewStubFragment(),new Bundle(),"ViewStubFragment");
            }
        });

        return rootView;
    }
}
