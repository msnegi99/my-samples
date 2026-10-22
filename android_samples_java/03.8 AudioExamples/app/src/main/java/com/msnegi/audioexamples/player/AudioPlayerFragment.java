package com.msnegi.audioexamples.player;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msnegi.audioexamples.R;

import java.io.File;
import java.util.ArrayList;

public class AudioPlayerFragment extends Fragment  {

    private RecyclerView recyclerViewRecordings;
    private ArrayList<Recording> recordingArraylist;
    private RecordingAdapter recordingAdapter;
    private TextView textViewNoRecordings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_audio_player, container, false);

        recyclerViewRecordings = (RecyclerView) view.findViewById(R.id.recyclerViewRecordings);
        recyclerViewRecordings.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        recyclerViewRecordings.setHasFixedSize(true);

        textViewNoRecordings = (TextView) view.findViewById(R.id.textViewNoRecordings);

        recordingArraylist = new ArrayList<Recording>();

        playLocalAudio();

        return view;
    }

    private void playLocalAudio() {

        File root = android.os.Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/Android/media/" + getActivity().getPackageName() + "/files/Audio/";

        File directory = new File(path);
        File[] files = directory.listFiles();

        if( files!=null ){
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                String recordingUri = root.getAbsolutePath() + "/Android/media/" + getActivity().getPackageName() + "/files/Audio/" + fileName;

                Recording recording = new Recording(recordingUri,fileName,false);
                recordingArraylist.add(recording);
            }

            textViewNoRecordings.setVisibility(View.GONE);
            recyclerViewRecordings.setVisibility(View.VISIBLE);
            setAdaptertoRecyclerView();
        }else{
            textViewNoRecordings.setVisibility(View.VISIBLE);
            recyclerViewRecordings.setVisibility(View.GONE);
        }
    }

    private void setAdaptertoRecyclerView() {
        recordingAdapter = new RecordingAdapter(getActivity(),recordingArraylist);
        recyclerViewRecordings.setAdapter(recordingAdapter);
    }
}