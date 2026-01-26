package com.example.videorecordplaydemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.videorecordplaydemo.R;

public class HomeFragment extends Fragment {

    Button basicCameraBtn, videoPlayerBtn;
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
        callback.setTitle("Video Record/Play Example");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        basicCameraBtn = view.findViewById(R.id.basicCameraBtn);
        basicCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment( new BasicCameraFragment(), new Bundle(), "BasicCameraFragment");
            }
        });

        videoPlayerBtn = view.findViewById(R.id.videoPlayerBtn);
        videoPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment( new BasicVideoPlayerFragment(), new Bundle(), "BasicVideoPlayerFragment");
            }
        });

        return view;
    }
}
