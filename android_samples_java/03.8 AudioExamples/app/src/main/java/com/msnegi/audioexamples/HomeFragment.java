package com.msnegi.audioexamples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.msnegi.audioexamples.player.AudioPlayerFragment;
import com.msnegi.audioexamples.player.AudioStreamingFragment;
import com.msnegi.audioexamples.recorder.AudioRecorderFragment;

public class HomeFragment extends Fragment {

    Button audioRecorderBtn,audioPlayerBtn,streamingAudioBtn;
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
        callback.setTitle("Audio Examples");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        audioRecorderBtn = view.findViewById(R.id.audioRecorderBtn);
        audioRecorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new AudioRecorderFragment(),new Bundle(),"AudioRecorderFragment");
            }
        });

        audioPlayerBtn = view.findViewById(R.id.audioPlayerBtn);
        audioPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new AudioPlayerFragment(),new Bundle(),"AudioPlayerFragment");
            }
        });

        streamingAudioBtn = view.findViewById(R.id.streamingAudioBtn);
        streamingAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new AudioStreamingFragment(),new Bundle(),"AudioStreamingFragment");
            }
        });

        return view;
    }
}
