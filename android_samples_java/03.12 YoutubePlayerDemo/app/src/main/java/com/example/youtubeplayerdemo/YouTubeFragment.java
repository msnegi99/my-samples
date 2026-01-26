package com.example.youtubeplayerdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.youtubeplayerdemo.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

public class YouTubeFragment extends Fragment {

    Button serializableBtn, paracelableBtn;
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
        callback.setTitle("Youtube Example");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_you_tube, container, false);

        ImageView tvc_image = view.findViewById(R.id.tvc_image);
        tvc_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xkZ6Bq8s2Bk"));
                intent.putExtra("force_fullscreen",true);
                startActivity(intent);
            }
        });

        Picasso.with(getActivity())
                .load( Uri.parse("https://img.youtube.com/vi/xkZ6Bq8s2Bk/0.jpg"))   //https://img.youtube.com/vi/............/0.jpg  1.jpg   2.jpg   3.jpg
                .into(tvc_image, new Callback(){
                    @Override
                    public void onSuccess() {
                        tvc_image.setScaleType(ImageView.ScaleType.CENTER_CROP);//Or ScaleType.FIT_CENTER
                    }

                    @Override
                    public void onError() {

                    }
                });

        return view;
    }

}