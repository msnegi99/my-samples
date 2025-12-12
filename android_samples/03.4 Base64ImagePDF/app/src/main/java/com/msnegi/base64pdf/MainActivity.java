package com.msnegi.base64pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import android.util.Base64;
//import java.util.Base64;
//import java.util.Base64;

public class MainActivity extends AppCompatActivity {
    private static final int FILE_SELECT_PDF = 0;
    private static final int FILE_SELECT_IMG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.load_pdf_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"),FILE_SELECT_PDF);
                } catch (android.content.ActivityNotFoundException ex) {
                    //Toast.makeText(this, "Please install a File Manager.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.load_image_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"),FILE_SELECT_IMG);
                } catch (android.content.ActivityNotFoundException ex) {
                    //Toast.makeText(this, "Please install a File Manager.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == FILE_SELECT_PDF ){
            try {
                Uri uri = data.getData();
                InputStream in = getContentResolver().openInputStream(uri);
                String ansValue = Base64.encodeToString(getBytes(in),Base64.NO_WRAP);
                System.out.println(ansValue);

                byte[] decString = Base64.decode(ansValue, Base64.NO_WRAP);

                writeByteArraysToFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "25_June_2020.pdf", decString);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                String encodedImage = encodeImage(selectedImage);

                byte[] decString = Base64.decode(encodedImage, Base64.NO_WRAP);

                writeByteArraysToFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "25_June_2020.jpg", decString);

            }catch (Exception e){}
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();

        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

        File file = new File(fileName);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        writer.write(content);
        writer.flush();
        writer.close();

    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
