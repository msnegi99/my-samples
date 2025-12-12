package com.example.imagecompresstest;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Size;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int PICK_IMAGE_FILE = 1;
    private static final int CAMERA_IMAGE_FILE = 2;

    public Uri uri, uurl, selectedUri;
    CompressImage compressImage;
    ImageView imgView;
    TextView pathtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        pathtxt = findViewById(R.id.pathtxt);

        compressImage = new CompressImage(this);

        findViewById(R.id.imageLoadbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
                } else {
                    captureAnImage();
                }
            }
        });
    }

    public void loadAnImage(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);  // Optionally, specify a URI for the file that should appear in the system file picker when it loads.
        startActivityForResult(intent, PICK_IMAGE_FILE);
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
                    System.out.println(ex.getMessage());
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
    File image;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "File_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory().getPath(), "/Android/data/" + this.getPackageName() + "/");
        image = File.createTempFile(
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

        if (requestCode == PICK_IMAGE_FILE && resultCode == Activity.RESULT_OK && data != null) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            /*Uri uri = null;
            if (data != null) {
                uri = data.getData();    //-- uri :  content://com.android.providers.media.documents/document/image%3A3018

                *//*Picasso.with(this)
                        .load(uri)
                        .into(imgView);*//*

                Uri vuri = new PathUtils().getLocalPath(this, uri);
                String localpath = vuri.getPath();                   //-- localpath :  /storage/emulated/0/Pictures/TestFolder/Image_ (8).jpg
                System.out.println("Local path : " + localpath);
                imgView.setImageURI(Uri.parse(localpath));

                pathtxt.setText(localpath);
            }*/

            if(data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                //ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                //ImageRepository imageRepository = new ImageRepository(a.getApplication());

                for (int f = 0; f < mClipData.getItemCount(); f++) {
                    ClipData.Item item = mClipData.getItemAt(f);
                    Uri uri = item.getUri();
                    //mArrayUri.add(uri);

                    //Uri selectedImage = data.getData();
                    //Log.d("FILE", selectedImage.getPath());
                    File source = new File(uri.getPath());
                    Log.d("source", source.toString());
                    try {
                        //File file1 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + "image_"+3333+"_"+uri.getLastPathSegment()+".jpg");
                        File file1 = new File(Environment.getExternalStorageDirectory().getPath(), "/Android/data/" + this.getPackageName() + "/" + "image_"+ Math.random() +".jpg");
                        Log.d("dest", file1.toString());
                        InputStream in = getContentResolver().openInputStream(uri);
                        FileOutputStream out = new FileOutputStream(file1);

                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        in.close();
                        out.close();
//                copy(inputStream, fileOutputStream);

                        if (file1 == null) {
                            return;
                        }

                        Uri imagePath = compressImage.compressImage(file1.getPath(),file1.getName());
                        File file = new File(imagePath.getPath());

                        imgView.setImageURI(imagePath);
                        pathtxt.setText(imagePath.getPath());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }else if (data != null){
                Uri uri = data.getData();

                //ImageRepository imageRepository = new ImageRepository(a.getApplication());

                File source = new File(uri.getPath());
                Log.d("source", source.toString());
                try {
                    File file1 = new File(Environment.getExternalStorageDirectory().getPath(), "/Android/data/" + this.getPackageName() + "/" + "image_"+ Math.random() +".jpg");
                    Log.d("dest", file1.toString());
                    InputStream in = this.getContentResolver().openInputStream(uri);
                    FileOutputStream out = new FileOutputStream(file1);

                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();
//                copy(inputStream, fileOutputStream);

                    if (file1 == null) {
                        return;
                    }

                    Uri imagePath = compressImage.compressImage(file1.getPath(),file1.getName());
                    File file = new File(imagePath.getPath());

                    imgView.setImageURI(imagePath);
                    pathtxt.setText(imagePath.getPath());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(MainActivity.this, "No file selected this way", Toast.LENGTH_LONG).show();
                //dialog.dismiss();
            }

        } else if (requestCode == CAMERA_IMAGE_FILE && resultCode == Activity.RESULT_OK) {
            //if (data != null) {     //-- data always return null in camera capture
                /*Picasso.with(this)
                        .load(selectedUri)
                        .into(imgView);*/

            //--selectedUri : content://com.example.mediapickersample.provider/provider/Android/data/com.example.mediapickersample/files/Pictures/QRGMitra_20210826_155704_6169420723202930685.jpg
            //imgView.setImageURI(selectedUri);

            //--currentPhotoPath : /storage/emulated/0/Android/data/com.example.mediapickersample/files/Pictures/QRGMitra_20210826_155704_6169420723202930685.jpg

            File file1 = new File(image.getPath());

            if (file1 == null) {
                return;
            }

            Uri imagePath = compressImage.compressImage(file1.getPath(),file1.getName());
            File file = new File(imagePath.getPath());

            Log.d("NOTICE", Uri.fromFile(file).getPath());
            Log.d("IMAGE NAME", file.getName());


            imgView.setImageURI(imagePath);
            pathtxt.setText(imagePath.getPath());
            //}
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(dialog1 != null) dialog1.dismiss();
        switch (requestCode){
            case PERMISSIONS_REQUEST_CODE: {
                for(int i=0; i<permissions.length; i++){
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])){
                            showAlert();
                            break;
                        }
                    }
                }
            }
        }
    }

    Dialog dialog1 = null;
    private void showAlert(){
        dialog1 = new Dialog(this);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_permissions);

        Window window = dialog1.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        Button btnDonotAllow = dialog1.findViewById(R.id.btnDonotAllow);
        btnDonotAllow.setText("Do not allow");
        btnDonotAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "App required all permission to work properly", Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
            }
        });
        Button btnAllow = dialog1.findViewById(R.id.btnAllow);
        btnAllow.setText("Allow");
        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
            }
        });

        dialog1.show();
    }
}