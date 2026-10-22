package com.example.mediapickersample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int PICK_IMAGE_FILE = 1;
    private static final int CAMERA_IMAGE_FILE = 2;
    private static final int PICK_VIDEO_FILE = 3;
    private static final int RECORD_VIDEO_FILE = 4;
    private static final int PICK_AUDIO_FILE = 5;
    private static final int PICK_PDF_FILE = 6;

    public Uri uri, uurl, selectedUri;
    int opCode = 1;

    private ProgressDialog pDialog;
    private static String file_url = "https://images.pexels.com/photos/658687/pexels-photo-658687.jpeg?cs=srgb&dl=pexels-cindy-gustafson-658687.jpg&fm=jpg";
    ImageView imgView;
    TextView pathtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        pathtxt = findViewById(R.id.pathtxt);

        findViewById(R.id.imageDownloadbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opCode = 1;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                } else {
                    downloadFile();
                }
            }
        });

        findViewById(R.id.imageLoadbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opCode = 2;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                } else {
                    loadAnImage();
                }
            }
        });

        findViewById(R.id.imageCamerabtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opCode = 3;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
                } else {
                    captureAnImage();
                }
            }
        });

        findViewById(R.id.imageSavebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opCode = 4;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                } else {
                    saveImage();
                }
            }
        });

        findViewById(R.id.videoPickbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opCode = 5;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                } else {
                    videoPick();
                }
            }
        });

        findViewById(R.id.videoRecordbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opCode = 6;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
                } else {
                    recordVideo();
                }
            }
        });

        findViewById(R.id.audiobtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opCode = 7;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                } else {
                    loadAudio();
                }
            }
        });

        findViewById(R.id.documentsbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opCode = 8;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                } else {
                    loadDocument();
                }
            }
        });
    }

    public void downloadFile(){
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.show();

        new DownloadFileFromURL(pDialog,imgView).execute(file_url);
    }

    public void loadAnImage(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);  // Optionally, specify a URI for the file that should appear in the system file picker when it loads.
        startActivityForResult(intent, PICK_IMAGE_FILE);
    }

    public void saveImage(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgView.getDrawable();
        if(bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            if(BitmapUtils.saveImageToGallery(this, bitmap, timeStamp)){
                Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Image Not Saved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void videoPick(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);  // Optionally, specify a URI for the file that should appear in the system file picker when it loads.
        startActivityForResult(intent, PICK_VIDEO_FILE);
    }

    public void recordVideo(){
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, RECORD_VIDEO_FILE);
        }
    }

    public void loadAudio(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*");
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);  // Optionally, specify a URI for the file that should appear in the system file picker when it loads.
        startActivityForResult(intent, PICK_AUDIO_FILE);
    }

    public void loadDocument(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);  // Optionally, specify a URI for the file that should appear in the system file picker when it loads.
        startActivityForResult(intent, PICK_PDF_FILE);
    }

    public void captureAnImage(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            photoFile);
                    selectedUri = photoURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA_IMAGE_FILE);
                }
            }

        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "QRGMitra_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();    //-- uri :  content://com.android.providers.media.documents/document/image%3A3018

                /*Picasso.with(this)
                        .load(uri)
                        .into(imgView);*/

                Uri vuri = new PathUtils().getLocalPath(this, uri);
                String localpath = vuri.getPath();                   //-- localpath :  /storage/emulated/0/Pictures/TestFolder/Image_ (8).jpg
                System.out.println("Local path : " + localpath);
                imgView.setImageURI(Uri.parse(localpath));

                pathtxt.setText(localpath);
            }
        } else if (requestCode == CAMERA_IMAGE_FILE && resultCode == Activity.RESULT_OK) {
            //if (data != null) {     //-- data always return null in camera capture
                /*Picasso.with(this)
                        .load(selectedUri)
                        .into(imgView);*/

                //--selectedUri : content://com.example.mediapickersample.provider/provider/Android/data/com.example.mediapickersample/files/Pictures/QRGMitra_20210826_155704_6169420723202930685.jpg
                //imgView.setImageURI(selectedUri);

                 //--currentPhotoPath : /storage/emulated/0/Android/data/com.example.mediapickersample/files/Pictures/QRGMitra_20210826_155704_6169420723202930685.jpg
                imgView.setImageURI(Uri.parse(currentPhotoPath));

                pathtxt.setText(currentPhotoPath);
            //}
        } else if (requestCode == PICK_VIDEO_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();   //-- uri :  content://com.android.providers.media.documents/document/video%3A2987

                String selection = "_id=?";
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                String[] selectionArgs = new String[]{split[1]};
                String[] projection = new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE};

                try (Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null)) {
                    // Cache column indices.
                    int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                    int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                    int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                    int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

                    while (cursor.moveToNext()) {
                        // Get values of columns for a given video.
                        long id = cursor.getLong(idColumn);
                        String name = cursor.getString(nameColumn);
                        int duration = cursor.getInt(durationColumn);
                        int size = cursor.getInt(sizeColumn);

                        //-- contentUri :  content://media/external/video/media/2987
                        Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

                        Bitmap thumbnail = getApplicationContext().getContentResolver().loadThumbnail(contentUri, new Size(640, 480), null);
                        imgView.setImageBitmap(thumbnail);

                        System.out.println("Video : " + name);

                        //-- localpath :  /storage/emulated/0/DCIM/Camera/VID_20210826_131507510.mp4
                        final String localpath1 = new PathUtils().getLocalPath(this, uri).toString();
                        System.out.println("Video : " + name);

                        pathtxt.setText(localpath1);
                    }
                } catch (Exception e) {
                    System.out.println("Error : " + e.getMessage());
                }
            }
        } else if (requestCode == RECORD_VIDEO_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();   //-- uri :  content://media/external_primary/video/media/3020
                //videoView.setVideoURI(uri);

                /*Glide.with(this)
                        .asBitmap()
                        .load(uri)
                        .into(imgView);*/

                try {
                    //-- localpath :  /storage/emulated/0/DCIM/Camera/VID_20210826_161103697.mp4
                    final String localpath = new PathUtils().getLocalPath(this, uri).toString();            //local video path
                    System.out.println("Local path : " + localpath);

                    Uri url = Uri.parse(localpath);

                    Bitmap bitmap = BitmapUtils.loadVideoThumbnail(localpath, getContentResolver());
                    imgView.setImageBitmap(bitmap);

                    pathtxt.setText(localpath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PICK_AUDIO_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();   //--uri :   content://com.android.providers.media.documents/document/audio%3A26
                // Perform operations on the document using its URI.

                /*MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    // mediaPlayer.setDataSource(String.valueOf(myUri));
                    mediaPlayer.setDataSource(MainActivity.this, uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();*/

                //-- localpath1 :   /storage/emulated/0/Download/file_example_MP3_700KB.mp3
                final String localpath1 = new PathUtils().getLocalPath(this, uri).toString();
                System.out.println("localpath1 : " + localpath1);

                imgView.setImageDrawable(getDrawable(R.drawable.audio));

                pathtxt.setText(localpath1);
            }
        } else if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();    //-- uri :  content://com.android.providers.media.documents/document/document%3A2988
                // Perform operations on the document using its URI.

                imgView.setImageDrawable(getDrawable(R.drawable.document));

                //-- we can not get the local path of PDF. if PDF needs to upload it has to be uploaded with InputOutputStream.

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent chooser = Intent.createChooser(intent, "Open With");
                try {
                    startActivity(chooser);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                boolean permission_denied = false;
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        permission_denied = true;
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            showAlert();
                        }
                    }
                }
                if(!permission_denied){
                    if(opCode == 1){
                        downloadFile();
                    }else if(opCode == 2){
                        loadAnImage();
                    }else if(opCode == 3){
                        captureAnImage();
                    }else if(opCode == 4){
                        saveImage();
                    }else if(opCode == 5){
                        videoPick();
                    }else if(opCode == 6){
                        recordVideo();
                    }else if(opCode == 7){
                        loadAudio();
                    }else if(opCode == 8){
                        loadDocument();
                    }
                }
            }
        }
    }

    private void showAlert() {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs all required permissions to run functionalities !!!");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "Do not Allow",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "App required all permission to work properly", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //-- no action required, move on to application
                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
            }
        });
        alertDialog.show();
    }

}