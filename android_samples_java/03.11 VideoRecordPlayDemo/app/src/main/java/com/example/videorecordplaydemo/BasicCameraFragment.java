package com.example.videorecordplaydemo;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.videorecordplaydemo.R;
import com.example.videorecordplaydemo.HomeActivity;
import com.example.videorecordplaydemo.CallBackInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicCameraFragment extends Fragment {

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private ImageView imgPreview;
    private VideoView videoPreview;
    Button btnCapturePicture,btnRecordVideo;
    TextView imagepath = null;
    Uri fileUri;

    CallBackInterface callback;
    Context contxt;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        contxt = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Basic Camera Sample");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_basic_camera, container, false);

        imagepath = (TextView) view.findViewById(R.id.imagepath);
        imgPreview = (ImageView) view.findViewById(R.id.imgPreview);
        videoPreview = (VideoView) view.findViewById(R.id.videoPreview);

        btnCapturePicture = (Button) view.findViewById(R.id.btnCapturePicture);
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        btnRecordVideo = (Button) view.findViewById(R.id.btnRecordVideo);
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                fileUri = Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_VIDEO));

                // set video quality
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the video file name
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 500000); // set the size to no limit

                // start the video capture Intent
                startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
            }
        });

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            btnCapturePicture.setEnabled(false);
            btnRecordVideo.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        return view;
    }

    private static File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraDemo");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    imagepath.setText(fileUri.toString());
                    previewCapturedImage();
                }else if (resultCode == getActivity().RESULT_CANCELED){
                    Toast.makeText(getActivity(),"User cancelled image capture", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAMERA_CAPTURE_VIDEO_REQUEST_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    imagepath.setText(fileUri.toString());
                    previewVideo();
                } else if (resultCode == getActivity().RESULT_CANCELED) {
                    Toast.makeText(getActivity(),"User cancelled video recording", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"Sorry! Failed to record video", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void previewCapturedImage() {
        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),options);
            imgPreview.setImageBitmap(bitmap);
            imgPreview.setRotation(imgPreview.getRotation() + 90);

            videoPreview.setVisibility(View.GONE);
            imgPreview.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewVideo() {
        try {
            videoPreview.setVideoPath(fileUri.getPath());
            videoPreview.start();

            imgPreview.setVisibility(View.GONE);
            videoPreview.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnCapturePicture.setEnabled(true);
                btnRecordVideo.setEnabled(true);
            }else {
                Toast.makeText(getActivity(), "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(outState!=null)
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState!=null)
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

}
