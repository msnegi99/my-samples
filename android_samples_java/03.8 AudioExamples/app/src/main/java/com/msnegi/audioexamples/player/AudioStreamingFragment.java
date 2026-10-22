package com.msnegi.audioexamples.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.msnegi.audioexamples.R;

import java.io.IOException;

public class AudioStreamingFragment extends Fragment {

    Button playAudioBtn, stopBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_audio_streaming, container, false);

        playAudioBtn = view.findViewById(R.id.audioBtn);
        playAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudioBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                playStreamingAudio();
            }
        });

        stopBtn = view.findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudioBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                stopStreamingAudio();
            }
        });

        return view;
    }

    MediaPlayer mPlayer;

    public void playStreamingAudio()
    {
        String audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3";

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mPlayer.setDataSource(audioUrl);
            mPlayer.prepare();
            mPlayer.start();
        }catch (IOException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getActivity(),"Audio End",Toast.LENGTH_SHORT).show();
                playAudioBtn.setEnabled(true);
            }
        });
    }

    public void stopStreamingAudio() {
        try{
            mPlayer.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopStreamingAudio();
    }
}