package com.example.videorecordplaydemo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videorecordplaydemo.R;
import android.app.ProgressDialog;

//import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class BasicVideoPlayerFragment extends Fragment {

    VideoView videoView;
    MediaController mediaControls;
    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_basic_video_player, container, false);

        // Find your VideoView in your video_main.xml layout
        videoView = (VideoView) view.findViewById(R.id.simpleVideoView);

        playOnlineVideo();
        //playLocalVideo();

        return view;
    }


    public void playOnlineVideo()
    {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle("Android Video Streaming Tutorial");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(getActivity());
            mediaControls.setAnchorView(videoView);
        }

        // set the media controller for video views
        videoView.setMediaController(mediaControls);

        String vid_url = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4";
        videoView.setVideoURI(Uri.parse(vid_url));

        videoView.requestFocus();

        // implement on completion listener on video view
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getActivity(), "Thank You...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // display a toast when an error is occured while playing an video
                Toast.makeText(getActivity(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        videoView.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoView.start();
            }
        });

    }

    public void playLocalVideo()
    {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle("Android Video Streaming Tutorial");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        // implement on completion listener on video view
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getActivity(), "Thank You...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // display a toast when an error is occured while playing an video
                Toast.makeText(getActivity(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        videoView.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoView.start();
            }
        });

        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(getActivity());
            mediaControls.setAnchorView(videoView);
        }

        // set the media controller for video views
        videoView.setMediaController(mediaControls);

        String uri = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.sample_mp4_file;
        videoView.setVideoURI(Uri.parse(uri));
    }
}