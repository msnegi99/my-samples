package com.msnegi.attachmentsample;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int PICK_A_FILE = 101;
    private static final int CAMERA_IMAGE_FILE = 2;

    FrameLayout frameAttachments;
    LinearLayout ll_helpsupportinflate;

    private List<File> files;
    private List<String> image_temp_file;
    private List<String> image_type;
    private String file_type = "";
    private CompressImage compressimage;
    ArrayList<Uri> selectedUriList = new ArrayList<>();
    ImageView imageView;
    private Uri data_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        files = new ArrayList<>();
        image_temp_file = new ArrayList<>();
        image_type = new ArrayList<>();
        compressimage = new CompressImage(this);

        frameAttachments = (FrameLayout) findViewById(R.id.frameAttachments);
        ll_helpsupportinflate = (LinearLayout) findViewById(R.id.ll_helpsupportinflate);

        frameAttachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                } else {
                    if (files.size() < 10) {
                        openPickerDialog();
                    } else {
                        Toast.makeText(MainActivity.this,"max 5 attachments only",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void openPickerDialog()
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_A_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_A_FILE && resultCode == MainActivity.this.RESULT_OK)
        {
            try {
                if(data != null) {
                    Uri uri = data.getData();    //-- uri :  content://com.android.providers.media.documents/document/image%3A3018
                    Uri vuri = new PathUtils().getLocalPath(this, uri);
                    if (vuri != null) {
                        String localpath = vuri.getPath();
                        if (localpath.contains(".jpg") || localpath.contains(".gif")) {
                            image_type.add("img");
                        }else if (localpath.contains(".mp4")) {
                            image_type.add("video");
                        } else {
                            image_type.add("other");
                        }
                        if (!image_temp_file.contains(localpath)) {
                            files.add(new File(localpath));
                            image_temp_file.add(localpath);
                        }else {
                            Toast.makeText(this, "You have already selected this file", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        image_type.add("doc");
                        if (!files.contains(getFileFromUri(Uri.parse("file:///" + data.getData())))) {
                            files.add(getFileFromUri(Uri.parse("file:///" + data.getData())));
                            image_temp_file.add(data.getData().toString());
                        }else {
                            Toast.makeText(this, "You have already selected this file", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (files.size() > 0) {
                data_uri = Uri.parse("file:///" + data.getData());
                renderLayout(files);
            }
        }
    }

    public void renderLayout(List<File> fileFromUri) {
        hideKeyboard(MainActivity.this);
        ll_helpsupportinflate.removeAllViews();
        for (int i = 0; i < fileFromUri.size(); i++) {

            View v = getLayoutInflater().inflate(R.layout.attachment_row, null);
            imageView = (ImageView) v.findViewById(R.id.imgAttached);
            final TextView tv = (TextView) v.findViewById(R.id.tv);
            ImageView imgdeleteIcon = (ImageView) v.findViewById(R.id.imgdeleteIcon);
            tv.setText(String.valueOf(i));
            imgdeleteIcon.setVisibility(View.VISIBLE);
            if (image_type.get(i).trim().equals("img")) {
                Bitmap mBitmap = null;
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(fileFromUri.get(i)));
                    if (mBitmap == null)
                        mBitmap = getBitmapWithAuthority(Uri.fromFile(fileFromUri.get(i)));
                    imageView.setImageBitmap(mBitmap);
                } catch (IOException ie) {
                    imageView.setImageResource(R.drawable.no_image);
                }
            } else if(image_type.get(i).trim().equals("doc")){
                imageView.setImageResource(R.drawable.no_doc);
            }else{
                imageView.setImageResource(R.drawable.no_doc_detail);
            }

            imgdeleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ll_helpsupportinflate.removeViewAt(Integer.parseInt(tv.getText().toString().trim()));
                        files.remove(Integer.parseInt(tv.getText().toString()));
                        image_temp_file.remove(Integer.parseInt(tv.getText().toString()));
                        image_type.remove(Integer.parseInt(tv.getText().toString()));
                        renderLayout(files);
                    } catch (NullPointerException ne) {
                        ll_helpsupportinflate.removeAllViews();
                    } catch (ArrayIndexOutOfBoundsException ne) {
                        ll_helpsupportinflate.removeAllViews();
                    } catch (IndexOutOfBoundsException iob) {
                        ll_helpsupportinflate.removeAllViews();
                    }
                }
            });

            ll_helpsupportinflate.addView(v);
        }
    }

    public Bitmap getBitmapWithAuthority(Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = MainActivity.this.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bmp);
                return bmp;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException ne) {
                }
            }
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private File getFileFromUri(Uri uri) {
        String imagePath = "";
        try {
            imagePath = ConvertUriToFilePath.getPathFromURI(MainActivity.this, uri);

            if (imagePath != null)
                if (!imagePath.trim().isEmpty())
                    if (new File(imagePath) != null) {
                        return new File(imagePath);
                    } else {
                        Cursor cursor = null;
                        try {
                            String[] proj = {MediaStore.Images.Media.DATA};
                            cursor = getContentResolver().query(uri, proj, null, null, null);
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            imagePath = cursor.getString(column_index);
                            return new File(imagePath);
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }

        } catch (Exception e) {
            e.printStackTrace();
            String id = DocumentsContract.getDocumentId(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                File file = new File(getCacheDir().getAbsolutePath() + "/" + id);
                writeFile(inputStream, file);
                String filePath = file.getAbsolutePath();
                return file;

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        return null;
    }

    void writeFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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
